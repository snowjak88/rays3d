package org.rays3d.integrator;

import org.apache.camel.CamelContext;
import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class IntegratorRouteBuilder extends RouteBuilder {

	public IntegratorRouteBuilder(CamelContext context) {
		super(context);
	}

	@Override
	public void configure() throws Exception {

		//@formatter:off
		//
		from("activemq:rays3d.integrator.integratorRequest")
			.log(LoggingLevel.DEBUG, "Received new integrator request! (for render id ${body.renderId}: ${body.integratorName} [${body.extraIntegratorConfig}])")
			.end();
		
		from("activemq:rays3d.samples")
			.log(LoggingLevel.TRACE, "Received new sample (for render id ${body.renderId}, at [${body.filmPoint.x},${body.filmPoint.y}])")
			.end();

		//
		//@formatter:on
	}

}
