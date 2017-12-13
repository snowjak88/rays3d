package org.rays3d.rendermq.rest;

import java.util.List;

import org.apache.camel.Exchange;
import org.rays3d.message.RenderRequest;
import org.rays3d.message.RenderStatus;
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
	public void getNewRenderRequest(Exchange exchange) {

		EmbeddedListOfRenderRequests response = restTemplate
				.getForObject("/renderDescriptors/search/findNewDescriptors", EmbeddedListOfRenderRequests.class);

		if (response == null || response.getList() == null || response.getList().getRequests().isEmpty())
			exchange.getIn().setBody(null);
		else
			exchange.getIn().setBody(response.getList().getRequests().get(0), RenderRequest.class);
	}

	/**
	 * Mark a given {@link RenderRequest} as in-progress (for overall
	 * render-status).
	 * 
	 * @param request
	 * @return
	 */
	public RenderRequest markAsRenderingInProgress(RenderRequest request) {

		if (request == null)
			return request;

		request.setRenderingStatus(RenderStatus.IN_PROGRESS);

		return restTemplate.patchForObject("/renderDescriptors/{renderId}", request, RenderRequest.class,
				request.getId());
	}

	private static class EmbeddedListOfRenderRequests {

		@JsonProperty("_embedded")
		private ListOfRenderRequests list;

		public ListOfRenderRequests getList() {

			return list;
		}

		@SuppressWarnings("unused")
		public void setList(ListOfRenderRequests list) {

			this.list = list;
		}

		private static class ListOfRenderRequests {

			@JsonProperty("renderDescriptors")
			private List<RenderRequest> requests;

			public List<RenderRequest> getRequests() {

				return requests;
			}

			@SuppressWarnings("unused")
			public void setRequests(List<RenderRequest> requests) {

				this.requests = requests;
			}
		}
	}

}
