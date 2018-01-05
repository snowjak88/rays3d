package org.rays3d.rendermq.rest;

import java.util.Arrays;
import java.util.Collection;

import org.rays3d.message.RenderRequest;
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
	 * GET a single {@link RenderRequest} by ID.
	 * 
	 * @param id
	 * @return
	 */
	public RenderRequest getById(Long id) {

		return restTemplate.getForObject("/renders/{renderId}", RenderRequest.class, id);
	}

	/**
	 * GET all new {@link RenderRequest}s found on the Render-DB
	 * 
	 * @return
	 */
	public Collection<RenderRequest> getNewRenderRequests() {

		return Arrays.asList(restTemplate.getForEntity("/renders/new", RenderRequest[].class).getBody());
	}

	/**
	 * PATCH an existing {@link RenderRequest} back to the Render-DB (updating
	 * it) and returning the now-current RenderRequest instance.
	 * 
	 * @param request
	 * @return
	 */
	public RenderRequest patchRenderRequest(RenderRequest request) {

		return restTemplate.patchForObject("/renders/{renderId}", request, RenderRequest.class, request.getId());
	}

}
