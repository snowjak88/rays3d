package org.rays3d.rendermq;

import org.apache.camel.ExchangePattern;
import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.rays3d.message.RenderRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class RenderRouteBuilder extends RouteBuilder {

	@Value("${rays3d.renderdb.poll.duration}")
	private String						renderDbPollInterval;

	@Autowired
	private RenderRequestServiceBean	renderRequestService;

	@Override
	public void configure() throws Exception {

		//
		//@formatter:off
		//
		// Poll the Render-DB for new RenderRequests, mark them as in-progress,
		// and drop them into the appropriate queue.
		from("timer:checkForNewRenders?period=" + renderDbPollInterval)
			.bean(renderRequestService, "getNewRenderRequests")
			.setExchangePattern(ExchangePattern.InOnly)
			.split(body())
			.to("activemq:rays3d.render.new");
		
		//
		// Transform new RenderRequests into Sampler, Integrator, and Film Requests,
		// and drop them into the appropriate queues.
		from("activemq:rays3d.render.new")
			.inputType(RenderRequest.class)
			.setExchangePattern(ExchangePattern.InOnly)
			.log(LoggingLevel.INFO, "Received new rendering request (ID: ${body.id})")
			.bean(renderRequestService, "markAsRenderingInProgress")
			.log(LoggingLevel.TRACE, "Marked rendering-request as in-progress (ID: ${body.id})")
			.multicast()
				.to("activemq:rays3d.transform.toSamplerRequest",
					"activemq:rays3d.transform.toIntegratorRequest",
					"activemq:rays3d.transform.toFilmRequest");
		
		from("activemq:rays3d.transform.toSamplerRequest")
			.bean(renderRequestService, "markAsSamplingInProgress")
			.log(LoggingLevel.TRACE, "Marked sampling-request as in-progress (ID: ${body.id})")
			.bean(renderRequestService, "toSamplerRequest")
			.log(LoggingLevel.DEBUG,"Dispatched sampler-request for render-ID ${body.renderId}")
			.to("activemq:rays3d.samples.samplerRequest");
		
		from("activemq:rays3d.transform.toIntegratorRequest")
			.bean(renderRequestService, "markAsIntegrationInProgress")
			.log(LoggingLevel.TRACE, "Marked integration-request as in-progress (ID: ${body.id})")
			.bean(renderRequestService, "toIntegratorRequest")
			.log(LoggingLevel.DEBUG,"Dispatched integrator-request for render-ID ${body.renderId}")
			.to("activemq:rays3d.integrator.integratorRequest");
		
		from("activemq:rays3d.transform.toFilmRequest")
			.bean(renderRequestService, "markAsFilmInProgress")
			.log(LoggingLevel.TRACE, "Marked film-request as in-progress (ID: ${body.id})")
			.bean(renderRequestService, "toFilmRequest")
			.log(LoggingLevel.DEBUG, "Dispatched film-request for render-ID ${body.renderId}")
			.to("activemq:rays3d.film.filmRequest");
		//
		//@formatter:on
		//
	}

}
