package org.rays3d.rendermq;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.language.SimpleExpression;
import org.rays3d.rendermq.rest.RenderDbRestBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class RenderRouteBuilder extends RouteBuilder {

	@Value("${rays3d.renderdb.poll.duration}")
	private String				renderDbPollInterval;

	@Autowired
	private RenderDbRestBean	renderDbRestBean;

	@Override
	public void configure() throws Exception {

		//
		// Define the REST endpoints published by :render-db and map them to
		// Camel endpoints.
		//
		//@formatter:off
		from("timer:checkForNewRenders?period=" + renderDbPollInterval)
				.bean(renderDbRestBean, "getNewRenderRequest")
				.bean(renderDbRestBean, "markAsRenderingInProgress")
				.filter(new SimpleExpression("${body} != null"))
				.to("direct:newRenderDescriptors");
		from("direct:newRenderDescriptors")
				.setBody(new SimpleExpression("Got new render-description! --\n${body}"))
					.to("log:org.rays3d.rendermq");
		//@formatter:on

		//@formatter:off
		/*restConfiguration().setBindingMode(RestBindingMode.json);
		restConfiguration().component("renderDbRestEndpoint");//.host(renderDbHost).port(renderDbPort).contextPath(renderDbRootPath);
		
		rest()
				//
				// new render descriptors get routed to direct:newRenderDescriptors
				.get("/renderDescriptors/search/findNewDescriptors?produces=application/json")
					.to("direct:newRenderDescriptors")
				//
				// individual render-descriptors may be retrieved from direct:renderDescriptor
				// either supply a RenderRequest with renderId populated,
				// or else populate the message-header "renderId"
				.get("/renderDescriptors/{renderId}?produces=application/json")
					.route()
						.choice()
							.when(new SimpleExpression("headers.renderId == null")).setHeader("renderId", bodyAs(RenderRequest.class).method("getId"))
						.endChoice()
					.endRest()
					.to("direct:renderDescriptor")
				//
				// render-descriptor updates get sent to render-db from direct:updateRenderDescriptor
				.patch("/renderDescriptors/{renderId}?consumes=application/json")
					.route()
						.setHeader("renderId", bodyAs(RenderRequest.class).method("getRenderId"))
					.endRest()
					.to("direct:updateRenderDescriptor")
				//
				// individual worlds can be queried from direct:worldFile
				// either supply a WorldFileRequest with worldId populated,
				// or else populate the message-header with "worldId"
				.get("/worlds/{worldId}?produces=application/json")
					.route()
						.choice()
							.when(new SimpleExpression("headers.worldId == null")).setHeader("worldId", bodyAs(WorldFileRequest.class).method("getId"))
						.endChoice()
					.endRest()
					.to("direct:worldFile");
		
		
		from("timer:checkForNewRenders?period=" + renderDbPollInterval)
				.process(e -> e.getContext().getEndpoint("direct:newRenderDescriptors").createExchange() )
				.setBody(new SimpleExpression("Got new render descriptor! --\n${body}"))
				.to("log:org.rays3d.rendermq");*/
		//@formatter:on
	}

}
