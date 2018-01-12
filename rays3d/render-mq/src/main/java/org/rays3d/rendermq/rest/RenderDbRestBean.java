package org.rays3d.rendermq.rest;

import java.util.Arrays;
import java.util.Collection;

import org.rays3d.message.RenderDescriptorMessage;
import org.rays3d.message.ResourceDescriptorMessage;
import org.rays3d.message.WorldDescriptorMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component("renderDbRest")
public class RenderDbRestBean {

	@Autowired
	@Qualifier("renderDbRestTemplate")
	private RestTemplate restTemplate;

	/**
	 * GET a single {@link RenderDescriptorMessage} by ID.
	 * 
	 * @param id
	 * @return
	 */
	public RenderDescriptorMessage getById(Long id) {

		return restTemplate.getForObject("/renders/{renderId}", RenderDescriptorMessage.class, id);
	}

	/**
	 * GET the WorldDescriptorMessage associated with a render (by ID).
	 * 
	 * @param renderId
	 * @return
	 */
	public WorldDescriptorMessage getWorldDescriptorByRenderId(Long renderId) {

		return restTemplate.getForObject("/renders/{renderId}/world", WorldDescriptorMessage.class, renderId);
	}

	/**
	 * GET all new {@link RenderDescriptorMessage}s found on the Render-DB
	 * 
	 * @return
	 */
	public Collection<RenderDescriptorMessage> getNewRenderRequests() {

		return Arrays.asList(restTemplate.getForEntity("/renders/new", RenderDescriptorMessage[].class).getBody());
	}

	/**
	 * PATCH an existing {@link RenderDescriptorMessage} back to the Render-DB (updating
	 * it) and returning the now-current RenderDescriptorMessage instance.
	 * 
	 * @param request
	 * @return
	 */
	public RenderDescriptorMessage patchRenderRequest(RenderDescriptorMessage request) {

		return restTemplate.patchForObject("/renders/{renderId}", request, RenderDescriptorMessage.class, request.getId());
	}

	/**
	 * POST a new rendered image to the specified render-ID.
	 * 
	 * @param renderId
	 * @param newImage
	 */
	public void postNewRenderedImage(long renderId, ResourceDescriptorMessage newImage) {

		restTemplate.postForObject("/renders/{renderId}/images", newImage, Void.class, renderId);
	}

}
