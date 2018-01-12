package org.rays3d.rendermq;

import java.util.Collection;

import org.rays3d.message.FilmDescriptorMessage;
import org.rays3d.message.IntegratorDescriptorMessage;
import org.rays3d.message.RenderDescriptorMessage;
import org.rays3d.message.RenderStatus;
import org.rays3d.message.ResourceDescriptorMessage;
import org.rays3d.message.SamplerRequestMessage;
import org.rays3d.message.WorldDescriptorMessage;
import org.rays3d.rendermq.rest.RenderDbRestBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("renderRequestService")
public class RenderRequestServiceBean {

	@Autowired
	private RenderDbRestBean renderDb;

	/**
	 * GET a single {@link RenderDescriptorMessage} by ID.
	 * 
	 * @param id
	 * @return
	 */
	public RenderDescriptorMessage getByID(Long id) {

		return renderDb.getById(id);
	}

	/**
	 * GET the first new {@link RenderDescriptorMessage} found on the Render-DB
	 * 
	 * @return
	 */
	public Collection<RenderDescriptorMessage> getNewRenderRequests() {

		return renderDb.getNewRenderRequests();

	}

	/**
	 * Convert the given {@link RenderDescriptorMessage} to a corresponding
	 * {@link SamplerRequestMessage}.
	 * 
	 * @param render
	 * @return
	 */
	public SamplerRequestMessage toSamplerRequest(RenderDescriptorMessage render) {

		final SamplerRequestMessage sampler = new SamplerRequestMessage();

		sampler.setRenderId(render.getId());
		sampler.setFilmWidth(render.getFilmWidth());
		sampler.setFilmHeight(render.getFilmHeight());
		sampler.setSamplerName(render.getSamplerName());
		sampler.setSamplesPerPixel(render.getSamplesPerPixel());

		return sampler;
	}

	/**
	 * Convert the given {@link RenderDescriptorMessage} to a corresponding
	 * {@link IntegratorDescriptorMessage}.
	 * 
	 * @param render
	 * @return
	 */
	public IntegratorDescriptorMessage toIntegratorDescriptor(RenderDescriptorMessage render) {

		final IntegratorDescriptorMessage integrator = new IntegratorDescriptorMessage();

		integrator.setRenderId(render.getId());
		integrator.setFilmWidth(render.getFilmWidth());
		integrator.setFilmHeight(render.getFilmHeight());
		integrator.setSamplesPerPixel(render.getSamplesPerPixel());
		integrator.setIntegratorName(render.getIntegratorName());
		integrator.setExtraIntegratorConfig(render.getExtraIntegratorConfig());

		return integrator;
	}

	/**
	 * Convert the given {@link RenderDescriptorMessage} to a corresponding
	 * {@link FilmDescriptorMessage}.
	 * 
	 * @param render
	 * @return
	 */
	public FilmDescriptorMessage toFilmDescriptor(RenderDescriptorMessage render) {

		final FilmDescriptorMessage film = new FilmDescriptorMessage();

		film.setRenderId(render.getId());
		film.setFilmWidth(render.getFilmWidth());
		film.setFilmHeight(render.getFilmHeight());
		film.setSamplesPerPixel(render.getSamplesPerPixel());

		return film;
	}

	/**
	 * Get the {@link WorldDescriptorMessage} corresponding to the given
	 * {@link RenderDescriptorMessage}.
	 * 
	 * @param render
	 * @return
	 */
	public WorldDescriptorMessage toWorldDescriptor(RenderDescriptorMessage render) {

		return renderDb.getWorldDescriptorByRenderId(render.getId());

	}

	/**
	 * Add a new rendered-image (expressed as a {@link ResourceDescriptorMessage}) to the
	 * specified render.
	 * 
	 * @param renderId
	 * @param newImage
	 */
	public void addNewImage(long renderId, ResourceDescriptorMessage newImage) {

		renderDb.postNewRenderedImage(renderId, newImage);
	}

	/**
	 * Update the completion-status of the given {@link RenderDescriptorMessage}.
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
	public RenderDescriptorMessage updateCompletion(RenderDescriptorMessage request, String completion) {

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
