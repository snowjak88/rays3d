package org.rays3d.console.controllers;

import java.util.Arrays;
import java.util.List;

import org.rays3d.message.RenderDescriptorMessage;
import org.rays3d.message.ResourceDescriptorMessage;
import org.rays3d.message.WorldDescriptorMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

@Controller
@RequestMapping("/data/render")
@ResponseBody
public class RenderController {

	private final static Logger	LOG	= LoggerFactory.getLogger(RenderController.class);

	@Autowired
	@Qualifier("renderDbRestTemplate")
	private RestTemplate		renderDbRestTemplate;

	@RequestMapping("/")
	public List<RenderDescriptorMessage> getRenderDescriptors(
			@RequestParam(name = "page", defaultValue = "0") int pageNumber) {

		LOG.debug("Getting {}th page of RenderDescriptors ...", pageNumber);
		return Arrays.asList(renderDbRestTemplate
				.getForEntity("/renders/page/{pageNumber}", RenderDescriptorMessage[].class, pageNumber)
					.getBody());

	}

	@RequestMapping("/{renderId}")
	public RenderDescriptorMessage getRenderDescriptor(@PathVariable("renderId") long renderId) {

		return renderDbRestTemplate.getForObject("/renders/single/{renderId}", RenderDescriptorMessage.class, renderId);
	}

	@RequestMapping("/{renderId}/world")
	public WorldDescriptorMessage getWorldDescriptor(@PathVariable("renderId") long renderId) {

		LOG.debug("Getting world-descriptor for render-ID = {}", renderId);
		return renderDbRestTemplate.getForObject("/renders/single/{renderId}/world", WorldDescriptorMessage.class,
				renderId);
	}

	@RequestMapping("/{renderId}/images")
	public List<ResourceDescriptorMessage> getRenderedImages(@PathVariable("renderId") long renderId) {

		LOG.debug("Getting rendered-images for render-ID = {}", renderId);
		return Arrays.asList(renderDbRestTemplate
				.getForEntity("/renders/single/{renderId}/images", ResourceDescriptorMessage[].class, renderId)
					.getBody());
	}

}
