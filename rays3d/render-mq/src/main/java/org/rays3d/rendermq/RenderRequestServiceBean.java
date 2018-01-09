package org.rays3d.rendermq;

import java.util.Collection;

import org.rays3d.message.FilmRequest;
import org.rays3d.message.IntegratorRequest;
import org.rays3d.message.RenderRequest;
import org.rays3d.message.RenderStatus;
import org.rays3d.message.ResourceRequest;
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
	 * Add a new rendered-image (expressed as a {@link ResourceRequest}) to the
	 * specified render.
	 * 
	 * @param renderId
	 * @param newImage
	 */
	public void addNewImage(long renderId, ResourceRequest newImage) {

		renderDb.postNewRenderedImage(renderId, newImage);
	}

	/**
	 * Update the completion-status of the given {@link RenderRequest}.
	 * <p>
	 * <code>completion</code> is expected to be one of:
	 * <ul>
	 * <li>NOT_STARTED</li>
	 * <li>STARTED</li>
	 * <li>IN_PROGRESS</li>
	 * <li>COMPLETE</li>
	 * </ul>
	 * </p>
	 * 
	 * @param request
	 * @param phaseName
	 * @param completion
	 * @return
	 * @throws IllegalArgumentException
	 *             if <code>completion</code> takes a value not in the list of
	 *             expected values
	 */
	public RenderRequest updateCompletion(RenderRequest request, String completion) {

		RenderStatus completionStatus;
		switch (completion) {
		case "NOT_STARTED":
			completionStatus = RenderStatus.NOT_STARTED;
			break;
		case "STARTED":
			completionStatus = RenderStatus.STARTED;
			break;
		case "IN_PROGRESS":
			completionStatus = RenderStatus.IN_PROGRESS;
			break;
		case "COMPLETE":
			completionStatus = RenderStatus.COMPLETE;
			break;
		default:
			throw new IllegalArgumentException("Cannot set completion-status of \"" + completion
					+ "\" -- must be one of NOT_STARTED | STARTED | IN_PROGRESS | COMPLETE");
		}

		request.setRenderingStatus(completionStatus);

		return renderDb.patchRenderRequest(request);
	}

}
