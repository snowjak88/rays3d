package org.rays3d.rendermq;

import org.apache.camel.ExchangePattern;
import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.rays3d.message.RenderDescriptorMessage;
import org.rays3d.message.ResourceDescriptorMessage;
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
		// and drop them into the appropriate queues.
		from("timer:checkForNewRenders?period=" + renderDbPollInterval)
			.bean(renderRequestService, "getNewRenderRequests")
			.setExchangePattern(ExchangePattern.InOnly)
			.log(LoggingLevel.INFO, "Found ${body.size()} new rendering request(s).")
			.split(body())
			.to("activemq:rays3d.render.new");
		
		//
		// Transform new RenderRequests into Sampler, Integrator, and Film Requests,
		// and drop them into the appropriate queues.
		from("activemq:rays3d.render.new")
			.inputType(RenderDescriptorMessage.class)
			.setExchangePattern(ExchangePattern.InOnly)
			.log(LoggingLevel.INFO, "Received new rendering request (ID: ${body.id})")
			.setHeader("completion", simple("STARTED"))
			.wireTap("activemq:rays3d.render.updateCompletion")
			.multicast()
				.to("activemq:rays3d.transform.toSamplerRequest",
					"activemq:rays3d.transform.toIntegratorRequest",
					"activemq:rays3d.transform.toFilmRequest");
		
		//
		// Pick up all completed images and forward them to the database.
		from("activemq:rays3d.render.newImages")
			.inputType(ResourceDescriptorMessage.class)
			.setExchangePattern(ExchangePattern.InOnly)
			.log(LoggingLevel.INFO, "Received new rendered-image for ${header.renderId}")
			.bean(renderRequestService, "addNewImage(${header.renderId}, ${body})");
		
		//
		// Transform a RenderDescriptorMessage to a SamplerRequestMessage, marking this render as IN-PROGRESS on the way.
		//
		from("activemq:rays3d.transform.toSamplerRequest")
			.inputType(RenderDescriptorMessage.class)
			.setHeader("completion", simple("IN_PROGRESS"))
			.wireTap("activemq:rays3d.render.updateCompletion")
			.bean(renderRequestService, "toSamplerRequest")
			.log(LoggingLevel.DEBUG,"Dispatched sampler-request for render-ID ${body.renderId}")
			.to("activemq:rays3d.samples.samplerRequest");
		
		//
		// Transform a RenderDescriptorMessage to an IntegratorDescriptorMessage and dispatch it to the appropriate queue
		//
		from("activemq:rays3d.transform.toIntegratorRequest")
			.setExchangePattern(ExchangePattern.InOnly)
			.inputType(RenderDescriptorMessage.class)
			//.bean(renderRequestService, "toIntegratorDescriptor")
			.transform(simple("${body.id}"))
			.inOut("activemq:rays3d.render.forID.integratorRequest")
			.log(LoggingLevel.DEBUG, "Dispatched integrator-request for render-ID ${body.renderId}")
			.to("activemq:rays3d.integrator.integratorRequest");
		
		//
		// Transform a RenderDescriptorMessage to a FilmDescriptorMessage and dispatch it to the appropriate queue
		//
		from("activemq:rays3d.transform.toFilmRequest")
			.setExchangePattern(ExchangePattern.InOnly)
			.inputType(RenderDescriptorMessage.class)
			//.bean(renderRequestService, "toFilmDescriptor")
			.transform(simple("${body.id}"))
			.inOut("activemq:rays3d.render.forID.filmRequest")
			.log(LoggingLevel.DEBUG, "Dispatched film-request for render-ID ${body.renderId}")
			.to("activemq:rays3d.film.filmRequest");
		
		//
		// Upon request, grab a complete RenderDescriptor from the Render-DB, by ID.
		//
		from("activemq:rays3d.render.byID")
			.setExchangePattern(ExchangePattern.InOut)
			.inputType(Long.class)
			.bean(renderRequestService, "getByID");
		
		//
		// Upon request, grab a complete IntegratorDescriptorMessage, by render-ID
		//
		from("activemq:rays3d.render.forID.integratorRequest")
			.setExchangePattern(ExchangePattern.InOut)
			.inputType(Long.class)
			.log(LoggingLevel.DEBUG, "Received request to refresh IntegratorDescriptorMessage for render-id ${body}")
			.bean(renderRequestService, "getByID")
			.bean(renderRequestService, "toIntegratorDescriptor");
		
		//
		// Upon request, grab a complete FilmDescriptorMessage, by render-ID
		//
		from("activemq:rays3d.render.forID.filmRequest")
			.setExchangePattern(ExchangePattern.InOut)
			.inputType(Long.class)
			.log(LoggingLevel.DEBUG, "Received request to refresh FilmDescriptorMessage for render-id ${body}")
			.bean(renderRequestService, "getByID")
			.bean(renderRequestService, "toFilmDescriptor");
		
		//
		// Upon request, grab a complete WorldDescriptor, by render-ID
		//
		from("activemq:rays3d.render.forID.worldDescriptor")
			.setExchangePattern(ExchangePattern.InOut)
			.inputType(Long.class)
			.log(LoggingLevel.DEBUG, "Received request to refresh WorldDescriptor for render-id ${body}")
			.bean(renderRequestService, "getByID")
			.bean(renderRequestService, "toWorldDescriptor");
		
		//
		// Upon request, update the completion-status of a given render for some phase
		// Expected headers: [completion] = NOT_STARTED | STARTED | IN_PROGRESS | COMPLETE
		//
		from("activemq:rays3d.render.updateCompletion.forID")
			.setExchangePattern(ExchangePattern.InOut)
			.inputType(Long.class)
			.log(LoggingLevel.DEBUG, "Marking completion-status of render-id ${body}: [${header.completion}]")
			.bean(renderRequestService, "getByID")
			.to("activemq:rays3d.render.updateCompletion")
			.transform(simple("${body.id}"));
		
		from("activemq:rays3d.render.updateCompletion")
			.setExchangePattern(ExchangePattern.InOut)
			.inputType(RenderDescriptorMessage.class)
			.log(LoggingLevel.DEBUG, "Marking completion-status of render-id ${body.id}: [${header.completion}]")
			.bean(renderRequestService, "updateCompletion(${body},${header.completion})");
		
		//
		//@formatter:on
		//
	}

}
