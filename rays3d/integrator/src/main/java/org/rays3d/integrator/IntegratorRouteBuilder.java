package org.rays3d.integrator;

import org.apache.camel.CamelContext;
import org.apache.camel.ExchangePattern;
import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.rays3d.integrator.holder.IntegratorCachingHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class IntegratorRouteBuilder extends RouteBuilder {

	@Autowired
	private IntegratorCachingHolder integratorCachingHolder;

	public IntegratorRouteBuilder(CamelContext context) {
		super(context);
	}

	@Override
	public void configure() throws Exception {

		//@formatter:off
		//
		from("activemq:rays3d.integrator.integratorRequest")
			.log(LoggingLevel.DEBUG, "Received new integrator request! (for render-ID ${body.renderId}: ${body.integratorName} [${body.extraIntegratorConfig}])")
			.bean(integratorCachingHolder, "put(${header.renderId}, ${body})");
		
		from("activemq:rays3d.integrator.worldDescriptor")
			.log(LoggingLevel.DEBUG, "Received new world-descriptor! (for render-ID ${header.renderId}, world-ID ${body.id})")
			.bean(integratorCachingHolder, "put(${header.renderId}, ${body})");
		
		//
		// Receive incoming Samples ...
		from("activemq:rays3d.samples")
			.choice()
				.when(simple("${bean:integratorCachingHolder.getIntegratorRequest(${body.renderId})} == null"))
					.log(LoggingLevel.DEBUG, "Putting sample on hold while we retrieve the IntegratorRequest for render-ID ${body.renderId}")
					.wireTap("direct:rays3d.integrator.integratorRequest.request")
					.to("activemq:rays3d.integrator.waitingSamples")
				.endChoice()
				.when(simple("${bean:integratorCachingHolder.getWorldDescriptor(${body.renderId})} == null"))
					.log(LoggingLevel.DEBUG, "Putting sample on hold while we retrieve the world-descriptor for render-ID ${body.renderId}")
					.wireTap("direct:rays3d.integrator.worldDescriptor.request")
					.to("activemq:rays3d.integrator.waitingSamples")
				.endChoice()
			.end()
			.log(LoggingLevel.TRACE, "Received new sample (for render-ID ${body.renderId}, at [${body.filmPoint.x},${body.filmPoint.y}])")
			.end();
		
		//
		// Samples that have been put on hold are kept here ...
		from("activemq:rays3d.integrator.waitingSamples")
			.log(LoggingLevel.TRACE, "Putting sample for render-ID ${body.renderId} on hold for 1 second ...")
			.delayer(1000)
			.log(LoggingLevel.TRACE, "Resubmitting held sample for render-ID ${body.renderId} back into the main incoming-samples queue...")
			.to("activemq:rays3d.samples");
		
		//
		// If a given Sample references a render-ID which we do not currently have in our integrator cache,
		// we will want to refresh that cache before processing that Sample.
		//
		// Here, we request an IntegratorRequest.
		//
		from("direct:rays3d.integrator.integratorRequest.request")
			.setExchangePattern(ExchangePattern.InOnly)
			.transform(simple("${body.renderId}"))
			.log(LoggingLevel.INFO, "Refreshing integrator-request for render-ID ${body}")
			.setHeader("replyQ", simple("rays3d.integrator.integratorRequest"))
			.setHeader("renderId", simple("${body}"))
			.to("activemq:rays3d.render.byID.asIntegratorRequest");
		//
		// And here, we request a WorldDescriptor.
		//
		from("direct:rays3d.integrator.worldDescriptor.request")
			.setExchangePattern(ExchangePattern.InOnly)
			.transform(simple("${body.renderId}"))
			.log(LoggingLevel.INFO, "Requesting world-descriptor for render-ID ${body}")
			.setHeader("replyQ", simple("rays3d.integrator.worldDescriptor"))
			.setHeader("renderId", simple("${body}"))
			.to("activemq:rays3d.render.byID.worldDescriptor");
		
		//
		// Once we have
		//

		//
		//@formatter:on
	}

}
