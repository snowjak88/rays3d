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
				.route()
					.bean(renderDescriptorRepository, "findAllDescriptors")
				.endRest()
			.get("/new")
				.description("Get all new (not in-progress) render descriptors")
				.route()
					.bean(renderDescriptorRepository, "findNewDescriptors")
				.endRest();
		
		rest("/renders/{renderId}")
			.produces("application/json")
			.get()
				.description("Get a single RenderDescriptor by ID")
				.route()
					.process(e -> e.getIn().setBody(e.getIn().getHeader("renderId"), Long.class))
					.bean(renderDescriptorRepository, "findOne")
				.endRest()
			.patch()
				.description("Update a single RenderDescriptor by ID")
				.type(RenderDescriptor.class)
				.route()
					.filter(simple("${body.id} == ${header.renderId}"))
					.bean(renderDescriptorUpdateService, "updateRenderDescriptor")
				.endRest()
			.get("/world")
				.description("Get a RenderDescriptor's associated WorldDescriptor")
				.route()
					.process(e -> e.getIn().setBody(e.getIn().getHeader("renderId"), Long.class))
					.bean(worldDescriptorRepository, "findByRenderDescriptorsId")
				.endRest()
			.get("/images")
				.description("Get a RenderDescriptor's associated ResourceRequest collection")
				.route()
					.process(e -> e.getIn().setBody(e.getIn().getHeader("renderId"), Long.class))
					.bean(resourceRepository, "findByRenderDescriptorId")
				.endRest()
			.post("/images")
				.description("Add a new image to this RenderDescriptor")
				.type(ResourceRequest.class)
				.route()
					.bean(renderDescriptorUpdateService, "addNewImage(${header.renderId}, ${body})")
				.endRest();
		
		rest("/resources")
			.produces("application/json")
			.get()
				.description("Get all resources")
				.route()
					.bean(resourceRepository, "findAll");
		
		rest("resources/{resourceID}")
			.produces("application/json")
			.get()
				.description("Get a single resource by ID")
				.route()
					.process(e -> e.getIn().setBody(e.getIn().getHeader("resourceID"),Long.class))
					.bean(resourceRepository, "findOne")
				.endRest()
			.get("/render")
				.produces("image/png")
				.description("Get a single resource by ID, delivered as an image")
				.route()
				.process(e -> e.getIn().setBody(e.getIn().getHeader("resourceID"),Long.class))
				.bean(resourceRepository, "findOne")
				.transform(simple("${body.data}"))
			.endRest();
		//
		//
		//
		//@formatter:on
	}

}
