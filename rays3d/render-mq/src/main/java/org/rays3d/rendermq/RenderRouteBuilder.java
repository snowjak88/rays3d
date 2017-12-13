package org.rays3d.rendermq;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.language.SimpleExpression;
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
				.bean(renderRequestService, "getNewRenderRequest")
				.filter(new SimpleExpression("${body} != null"))
				.to("direct:rays3d.render.new");
		
		//
		// Transform new RenderRequests into Sampler, Integrator, and Film Requests,
		// and drop them into the appropriate queues.
		from("direct:rays3d.render.new")
			.bean(renderRequestService, "markAsRenderingInProgress")
			.multicast()
			.inOnly("direct:rays3d.transform.toSamplerRequest",
					"direct:rays3d.transform.toIntegratorRequest",
					"direct:rays3d.transform.toFilmRequest");
		
		from("direct:rays3d.transform.toSamplerRequest")
			.bean(renderRequestService, "markAsSamplingInProgress")
			.bean(renderRequestService, "toSamplerRequest")
			.to("activemq:rays3d.samples.samplerRequest");
		
		from("direct:rays3d.transform.toIntegratorRequest")
			.bean(renderRequestService, "markAsIntegrationInProgress")
			.bean(renderRequestService, "toIntegratorRequest")
			.to("activemq:rays3d.integrator.integratorRequest");
		
		from("direct:rays3d.transform.toFilmRequest")
			.bean(renderRequestService, "markAsFilmInProgress")
			.bean(renderRequestService, "toFilmRequest")
			.to("activemq:rays3d.film.filmRequest");
		//
		//@formatter:on
		//
	}

}
