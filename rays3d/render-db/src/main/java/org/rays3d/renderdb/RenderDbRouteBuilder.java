package org.rays3d.renderdb;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.rest.RestBindingMode;
import org.rays3d.message.ResourceRequest;
import org.rays3d.renderdb.model.RenderDescriptor;
import org.rays3d.renderdb.repository.RenderDescriptorRepository;
import org.rays3d.renderdb.repository.ResourceRepository;
import org.rays3d.renderdb.repository.WorldDescriptorRepository;
import org.rays3d.renderdb.service.RenderDescriptorUpdateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class RenderDbRouteBuilder extends RouteBuilder {

	@Value("${renderdb.rest.host}")
	private String							serverHost;

	@Value("${renderdb.rest.context-path}")
	private String							serverContextPath;

	@Value("${renderdb.rest.port}")
	private int								serverPort;

	@Autowired
	private RenderDescriptorRepository		renderDescriptorRepository;

	@Autowired
	private WorldDescriptorRepository		worldDescriptorRepository;

	@Autowired
	private ResourceRepository				resourceRepository;

	@Autowired
	private RenderDescriptorUpdateService	renderDescriptorUpdateService;

	@Override
	public void configure() throws Exception {

		//@formatter:off
		//
		restConfiguration()
				.component("jetty")
				.host(serverHost)
				.contextPath(serverContextPath)
				.port(serverPort)
				.bindingMode(RestBindingMode.json);
		//
		//
		//
		rest("/renders")
			.produces("application/json")
			.get()
				.description("Get all render descriptors")
				.to("direct:get.renders.all")
			.get("/new")
				.description("Get all new (not in-progress) render descriptors")
				.to("direct:get.renders.new");
		
		rest("/renders/{renderId}")
			.produces("application/json")
			.get()
				.description("Get a single RenderDescriptor by ID")
				.to("direct:get.renders.byId")
			.patch()
				.description("Update a single RenderDescriptor by ID")
				.type(RenderDescriptor.class)
				.to("direct:patch.render")
			.get("/world")
				.description("Get a RenderDescriptor's associated WorldDescriptor")
				.to("direct:get.renders.byId.world")
			.get("/images")
				.description("Get a RenderDescriptor's associated ResourceRequest collection")
				.to("direct:get.renders.byId.images")
			.post("/images")
				.description("Add a new image to this RenderDescriptor")
				.type(ResourceRequest.class)
				.to("direct:render.images.addNew");
		
		rest("/resources")
			.produces("application/json")
			.get()
				.description("Get all resources")
				.to("direct:get.resources.all");
		
		rest("resources/{resourceID}")
			.produces("application/json")
			.get()
				.description("Get a single resource by ID")
				.to("direct:get.resources.byId");
		//
		//
		//
		from("direct:get.renders.all")
			.bean(renderDescriptorRepository, "findAllDescriptors");
		from("direct:get.renders.new")
			.bean(renderDescriptorRepository, "findNewDescriptors");
		//
		//
		from("direct:get.renders.byId")
			.process(e -> e.getIn().setBody(e.getIn().getHeader("renderId"), Long.class))
			.bean(renderDescriptorRepository, "findOne");
		//
		from("direct:get.renders.byId.world")
			.process(e -> e.getIn().setBody(e.getIn().getHeader("renderId"), Long.class))
			.bean(worldDescriptorRepository, "findByRenderDescriptorsId");
		//
		from("direct:get.renders.byId.images")
			.process(e -> e.getIn().setBody(e.getIn().getHeader("renderId"), Long.class))
			.bean(resourceRepository, "findByRenderDescriptorId");
		//
		//
		from("direct:patch.render")
			.filter(simple("${body.id} == ${header.renderId}"))
			.bean(renderDescriptorUpdateService, "updateRenderDescriptor");
		//
		from("direct:render.images.addNew")
			.bean(renderDescriptorUpdateService, "addNewImage(${header.renderId}, ${body})");
		//
		//
		//
		from("direct:get.resources.all")
			.bean(resourceRepository, "findAll");
		//
		from("direct:get.resources.byId")
			.process(e -> e.getIn().setBody(e.getIn().getHeader("resourceID"),Long.class))
			.bean(resourceRepository, "findOne");
		//
		//@formatter:on
	}

}
