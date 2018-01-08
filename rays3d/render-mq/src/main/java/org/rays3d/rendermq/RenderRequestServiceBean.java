package org.rays3d.rendermq;

import java.util.Collection;

import org.rays3d.message.FilmRequest;
import org.rays3d.message.IntegratorRequest;
import org.rays3d.message.RenderRequest;
import org.rays3d.message.RenderStatus;
import org.rays3d.message.SamplerRequest;
import org.rays3d.message.WorldDescriptorRequest;
import org.rays3d.rendermq.rest.RenderDbRestBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("renderRequestService")
public class RenderRequestServiceBean {

	@Autowired
	private RenderDbRestBean renderDb;

	/**
	 * GET a single {@link RenderRequest} by ID.
	 * 
	 * @param id
	 * @return
	 */
	public RenderRequest getByID(Long id) {

		return renderDb.getById(id);
	}

	/**
	 * GET the first new {@link RenderRequest} found on the Render-DB
	 * 
	 * @return
	 */
	public Collection<RenderRequest> getNewRenderRequests() {

		return renderDb.getNewRenderRequests();

	}

	/**
	 * Convert the given {@link RenderRequest} to a corresponding
	 * {@link SamplerRequest}.
	 * 
	 * @param render
	 * @return
	 */
	public SamplerRequest toSamplerRequest(RenderRequest render) {

		final SamplerRequest sampler = new SamplerRequest();

		sampler.setRenderId(render.getId());
		sampler.setFilmWidth(render.getFilmWidth());
		sampler.setFilmHeight(render.getFilmHeight());
		sampler.setSamplerName(render.getSamplerName());
		sampler.setSamplesPerPixel(render.getSamplesPerPixel());

		return sampler;
	}

	/**
	 * Convert the given {@link RenderRequest} to a corresponding
	 * {@link IntegratorRequest}.
	 * 
	 * @param render
	 * @return
	 */
	public IntegratorRequest toIntegratorRequest(RenderRequest render) {

		final IntegratorRequest integrator = new IntegratorRequest();

		integrator.setRenderId(render.getId());
		integrator.setFilmWidth(render.getFilmWidth());
		integrator.setFilmHeight(render.getFilmHeight());
		integrator.setSamplesPerPixel(render.getSamplesPerPixel());
		integrator.setIntegratorName(render.getIntegratorName());
		integrator.setExtraIntegratorConfig(render.getExtraIntegratorConfig());

		return integrator;
	}

	/**
	 * Convert the given {@link RenderRequest} to a corresponding
	 * {@link FilmRequest}.
	 * 
	 * @param render
	 * @return
	 */
	public FilmRequest toFilmRequest(RenderRequest render) {

		final FilmRequest film = new FilmRequest();

		film.setRenderId(render.getId());
		film.setFilmWidth(render.getFilmWidth());
		film.setFilmHeight(render.getFilmHeight());
		film.setSamplesPerPixel(render.getSamplesPerPixel());

		return film;
	}

	/**
	 * Get the {@link WorldDescriptorRequest} corresponding to the given
	 * {@link RenderRequest}.
	 * 
	 * @param render
	 * @return
	 */
	public WorldDescriptorRequest toWorldDescriptor(RenderRequest render) {

		return renderDb.getWorldDescriptorByRenderId(render.getId());

	}

	/**
	 * Mark a given {@link RenderRequest} as rendering-in-progress.
	 * 
	 * @param request
	 * @return
	 */
	public RenderRequest markAsRenderingInProgress(RenderRequest request) {

		final RenderRequest updateRequest = new RenderRequest();
		updateRequest.setId(request.getId());
		updateRequest.setVersion(request.getVersion());
		updateRequest.setRenderingStatus(RenderStatus.IN_PROGRESS);

		return renderDb.patchRenderRequest(updateRequest);
	}

	/**
	 * Mark a given {@link RenderRequest} as sampling-in-progress.
	 * 
	 * @param request
	 * @return
	 */
	public RenderRequest markAsSamplingInProgress(RenderRequest request) {

		final RenderRequest updateRequest = new RenderRequest();
		updateRequest.setId(request.getId());
		updateRequest.setVersion(request.getVersion());
		updateRequest.setSamplingStatus(RenderStatus.IN_PROGRESS);

		return renderDb.patchRenderRequest(updateRequest);
	}

	/**
	 * Mark a given {@link RenderRequest} as integration-in-progress.
	 * 
	 * @param request
	 * @return
	 */
	public RenderRequest markAsIntegrationInProgress(RenderRequest request) {

		final RenderRequest updateRequest = new RenderRequest();
		updateRequest.setId(request.getId());
		updateRequest.setVersion(request.getVersion());
		updateRequest.setIntegrationStatus(RenderStatus.IN_PROGRESS);

		return renderDb.patchRenderRequest(updateRequest);
	}

	/**
	 * Mark a given {@link RenderRequest} as film-in-progress.
	 * 
	 * @param request
	 * @return
	 */
	public RenderRequest markAsFilmInProgress(RenderRequest request) {

		final RenderRequest updateRequest = new RenderRequest();
		updateRequest.setId(request.getId());
		updateRequest.setVersion(request.getVersion());
		updateRequest.setFilmStatus(RenderStatus.IN_PROGRESS);

		return renderDb.patchRenderRequest(updateRequest);
	}

}
