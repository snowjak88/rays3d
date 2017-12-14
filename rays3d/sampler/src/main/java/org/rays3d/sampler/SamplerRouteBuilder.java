package org.rays3d.sampler;

import org.apache.camel.CamelContext;
import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SamplerRouteBuilder extends RouteBuilder {

	@Autowired
	private SamplesRequestServiceBean samplesRequestServiceBean;

	public SamplerRouteBuilder(CamelContext context) {
		super(context);
	}

	@Override
	public void configure() throws Exception {

		//@formatter:off
		//
		from("activemq:rays3d.samples.samplerRequest")
			.log(LoggingLevel.DEBUG, "Received new sampler request! (for render id ${body.renderId}: ${body.samplerName})")
			.split()
				.method(samplesRequestServiceBean,"splitSamplerRequest")
				.stopOnException()
				.to("activemq:rays3d.samples.sampleRequest")
			.end();
		
		from("activemq:rays3d.samples.sampleRequest")
			.split()
				.method(samplesRequestServiceBean, "splitSampleRequestIntoSamples")
				.stopOnException()
				.to("activemq:rays3d.samples.sample")
			.end();
		//
		//@formatter:on
	}

}
