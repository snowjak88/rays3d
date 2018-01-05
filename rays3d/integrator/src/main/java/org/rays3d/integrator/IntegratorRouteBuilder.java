package org.rays3d.integrator;

import org.apache.camel.CamelContext;
import org.apache.camel.ExchangePattern;
import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class IntegratorRouteBuilder extends RouteBuilder {

	@Autowired
	private IntegratorRequestHolder integratorRequestHolder;

	public IntegratorRouteBuilder(CamelContext context) {
		super(context);
	}

	@Override
	public void configure() throws Exception {

		//@formatter:off
		//
		from("activemq:rays3d.integrator.integratorRequest")
			.log(LoggingLevel.DEBUG, "Received new integrator request! (for render-ID ${body.renderId}: ${body.integratorName} [${body.extraIntegratorConfig}])")
			.bean(integratorRequestHolder, "put(${body})")
			.end();
		
		//
		// Receive incoming Samples ...
		from("activemq:rays3d.samples")
			.choice()
				.when(method(integratorRequestHolder, "hasRenderId(${body.renderId})").isEqualTo(Boolean.FALSE))
					.log(LoggingLevel.TRACE, "Sample for unknown render-ID (${body.renderId}) -- putting sample on hold until we can refresh the IntegratorRequest")
					.wireTap("direct:rays3d.integrator.integratorRequest.refresh")
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
		// If a given Sample references a render-ID which we do not currently have in our cache of IntegratorRequest instances,
		// we will want to refresh that cache before processing that Sample.
		//
		from("direct:rays3d.integrator.integratorRequest.refresh")
			.setExchangePattern(ExchangePattern.InOnly)
			.transform(simple("${body.renderId}"))
			.log(LoggingLevel.INFO, "Refreshing integrator-request for render-ID ${body}")
			.to("activemq:rays3d.render.byID.asIntegratorRequest");

		//
		//@formatter:on
	}

}
