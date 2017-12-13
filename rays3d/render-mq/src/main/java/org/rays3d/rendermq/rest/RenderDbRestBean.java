package org.rays3d.rendermq.rest;

import java.util.List;

import org.rays3d.message.RenderRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.annotation.JsonProperty;

@Component("renderDbRest")
public class RenderDbRestBean {

	@Autowired
	@Qualifier("renderDbRestTemplate")
	private RestTemplate restTemplate;

	/**
	 * GET the first new {@link RenderRequest} found on the Render-DB
	 * 
	 * @return
	 */
	public RenderRequest getNewRenderRequest() {

		EmbeddedListOfRenderRequests response = restTemplate
				.getForObject("/renderDescriptors/search/findNewDescriptors", EmbeddedListOfRenderRequests.class);

		if (response == null || response.getList() == null || response.getList().getRequests().isEmpty())
			return null;
		else
			return response.getList().getRequests().get(0);
	}

	/**
	 * PATCH an existing {@link RenderRequest} back to the Render-DB (updating
	 * it) and returning the now-current RenderRequest instance.
	 * 
	 * @param request
	 * @return
	 */
	public RenderRequest patchRenderRequest(RenderRequest request) {

		return restTemplate.patchForObject("/renderDescriptors/{renderId}", request, RenderRequest.class,
				request.getId());
	}

	public static class EmbeddedListOfRenderRequests {

		@JsonProperty("_embedded")
		private ListOfRenderRequests list;

		public ListOfRenderRequests getList() {

			return list;
		}

		public void setList(ListOfRenderRequests list) {

			this.list = list;
		}

		public static class ListOfRenderRequests {

			@JsonProperty("renderDescriptors")
			private List<RenderRequest> requests;

			public List<RenderRequest> getRequests() {

				return requests;
			}

			public void setRequests(List<RenderRequest> requests) {

				this.requests = requests;
			}
		}
	}

}
