package org.rays3d.renderdb;

import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.rest.RestBindingMode;
import org.rays3d.renderdb.model.RenderDescriptor;
import org.rays3d.renderdb.model.Resource;
import org.rays3d.renderdb.repository.RenderDescriptorRepository;
import org.rays3d.renderdb.repository.ResourceRepository;
import org.rays3d.renderdb.repository.WorldDescriptorRepository;
import org.rays3d.renderdb.service.RenderDescriptorUpdateService;
import org.rays3d.renderdb.service.ResourceUpdateService;
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

	@Autowired
	private ResourceUpdateService			resourceUpdateService;

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
					.bean(renderDescriptorUpdateService, "getDescriptors")
				.endRest()
			.get("/new")
				.description("Get all new (not in-progress) render descriptors")
				.route()
					.bean(renderDescriptorRepository, "findNewDescriptors")
				.endRest()
			.get("/page/{pageNumber}")
				.description("Get the Nth page of render-descriptors")
				.route()
					.choice()
						.when(simple("${header.pageNumber} == null"))
							.log(LoggingLevel.DEBUG, "No page-number supplied, using default page-number of 0")
							.setHeader("pageNumber", simple("0", Integer.class))
					.end()
					.bean(renderDescriptorUpdateService, "getDescriptorsNthPage(${header.pageNumber})")
				.endRest();
		
		rest("/renders/single")
			.post()
				.description("Create a new RenderDescriptor")
				.type(RenderDescriptor.class)
				.route()
					.bean(renderDescriptorUpdateService, "createNewDescriptor")
					.log(LoggingLevel.INFO, "Created a new RenderDescriptor -- new ID = ${body.id}")
				.endRest();
		
		rest("/renders/single/{renderId}")
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
					.choice()
						.when(simple("${body.id} != ${header.renderId}"))
							.throwException(IllegalArgumentException.class, "Cannot update a RenderDescriptor with contradictory IDs -- ${header.renderId} from URI, ${body.id} from message-body!")
						.otherwise()
							.bean(renderDescriptorUpdateService, "updateRenderDescriptor")
					.endChoice()
				.endRest()
			.get("/world")
				.description("Get a RenderDescriptor's associated WorldDescriptor")
				.route()
					.process(e -> e.getIn().setBody(e.getIn().getHeader("renderId"), Long.class))
					.bean(worldDescriptorRepository, "findByRenderDescriptorsId")
				.endRest()
			.get("/images")
				.description("Get a RenderDescriptor's associated ResourceDescriptorMessage collection")
				.route()
					.process(e -> e.getIn().setBody(e.getIn().getHeader("renderId"), Long.class))
					.bean(resourceRepository, "findByRenderDescriptorId")
				.endRest()
			.post("/images")
				.description("Add a new image to this RenderDescriptor")
				.type(Resource.class)
				.route()
					.bean(renderDescriptorUpdateService, "addNewImage(${header.renderId}, ${body})")
				.endRest();
		
		rest("/resources")
			.produces("application/json")
			.get()
				.description("Get all resources")
				.route()
					.bean(resourceRepository, "findAll")
				.endRest()
			.post()
				.description("Add a new Resource")
				.type(Resource.class)
				.route()
					.bean(resourceUpdateService, "postNewResource")
				.endRest()
			.put("/{resourceId}")
				.description("Update an existing Resource")
				.type(Resource.class)
				.route()
					.choice()
						.when(simple("${body.id} != ${header.resourceId}"))
							.throwException(IllegalArgumentException.class, "Cannot update a Resource with contradictory IDs -- ${header.renderId} from URI, ${body.id} from message-body!")
						.otherwise()
							.bean(resourceUpdateService, "updateResource")
					.endChoice()
				.endRest();
		
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
