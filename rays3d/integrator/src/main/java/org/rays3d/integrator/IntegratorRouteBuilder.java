package org.rays3d.integrator;

import org.apache.camel.CamelContext;
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
			.log(LoggingLevel.TRACE, "Checking pre-requisites for incoming sample (for render-ID ${body.renderId})")
			.bean(integratorCachingHolder, "getSamplePrerequisites")
			.log(LoggingLevel.TRACE, "Received new sample (for render-ID ${body.renderId}, at [${body.filmPoint.x},${body.filmPoint.y}])")
			.end();

		//
		//@formatter:on
	}

}
