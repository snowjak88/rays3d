package org.rays3d.integrator;

import org.apache.camel.CamelContext;
import org.apache.camel.ExchangePattern;
import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.rays3d.integrator.holder.IntegratorCachingHolder;
import org.rays3d.message.IntegratorRequest;
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
		// Receive incoming IntegratorRequests and allocate Integrators in the cache.
		from("activemq:rays3d.integrator.integratorRequest")
			.setExchangePattern(ExchangePattern.InOnly)
			.inputType(IntegratorRequest.class)
			.log(LoggingLevel.TRACE, "Received IntegratorRequest for render-ID ${body.renderId}, integrator \"${body.integratorName}\".")
			.bean(integratorCachingHolder, "initializeIntegrator");
		//
		// Receive incoming Samples, render them, and pass them to the film-queue
		from("activemq:rays3d.samples")
			.setExchangePattern(ExchangePattern.InOnly)
			.log(LoggingLevel.TRACE, "Checking pre-requisites for incoming sample (for render-ID ${body.renderId})")
			.bean(integratorCachingHolder, "getSamplePrerequisites")
			.log(LoggingLevel.TRACE, "Rendering sample (render-ID ${body.renderId} @ [${body.filmPoint.x},${body.filmPoint.y}])")
			.bean(integratorCachingHolder, "renderSample")
			.log(LoggingLevel.TRACE, "Passing rendered sample along to film-queue")
			.to("activemq:rays3d.film.samples");

		//
		//@formatter:on
	}

}
