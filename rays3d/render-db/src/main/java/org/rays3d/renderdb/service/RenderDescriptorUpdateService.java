package org.rays3d.renderdb.service;

import java.util.Set;
import java.util.TreeSet;

import org.rays3d.message.RenderStatus;
import org.rays3d.renderdb.model.RenderDescriptor;
import org.rays3d.renderdb.model.Resource;
import org.rays3d.renderdb.repository.RenderDescriptorRepository;
import org.rays3d.renderdb.repository.ResourceRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RenderDescriptorUpdateService {

	private static final Logger			LOG	= LoggerFactory.getLogger(RenderDescriptorUpdateService.class);

	@Autowired
	private RenderDescriptorRepository	renderDescriptorRepository;

	@Autowired
	private ResourceRepository			resourceRepository;

	/**
	 * Given a {@link RenderDescriptor}, scan over all fields and, if any are
	 * not <code>null</code> (or <code>== 0</code>), update them on the
	 * corresponding entity in the database. When finished, return the
	 * now-updated entity (or <code>null</code> if that entity was not found to
	 * be updated in the first place).
	 * 
	 * @param renderDescriptor
	 * @return
	 */
	@Transactional
	public RenderDescriptor updateRenderDescriptor(RenderDescriptor renderDescriptor) {

		LOG.info("Updating a RenderDescriptor (ID = {}) ...", renderDescriptor.getId());

		final RenderDescriptor current = renderDescriptorRepository.findOne(renderDescriptor.getId());

		if (current == null) {
			LOG.warn("No RenderDescriptor found by ID = {} -- cannot update.", renderDescriptor.getId());
			return null;
		}

		if (renderDescriptor.getWorldDescriptor() != null)
			current.setWorldDescriptor(renderDescriptor.getWorldDescriptor());

		if (renderDescriptor.getFilmWidth() > 0) {
			LOG.trace("Updating RenderDescriptor.filmWidth = {}", renderDescriptor.getFilmWidth());
			current.setFilmWidth(renderDescriptor.getFilmWidth());
		}

		if (renderDescriptor.getFilmHeight() > 0) {
			LOG.trace("Updating RenderDescriptor.filmHeight = {}", renderDescriptor.getFilmHeight());
			current.setFilmHeight(renderDescriptor.getFilmHeight());
		}

		if (renderDescriptor.getSamplerName() != null) {
			LOG.trace("Updating RenderDescriptor.samplerName = {}", renderDescriptor.getSamplerName());
			current.setSamplerName(renderDescriptor.getSamplerName());
		}

		if (renderDescriptor.getSamplesPerPixel() > 0) {
			LOG.trace("Updating RenderDescriptor.samplesPerPixel = {}", renderDescriptor.getSamplesPerPixel());
			current.setSamplesPerPixel(renderDescriptor.getSamplesPerPixel());
		}

		if (renderDescriptor.getIntegratorName() != null) {
			LOG.trace("Updating RenderDescriptor.integratorName = {}", renderDescriptor.getIntegratorName());
			current.setIntegratorName(renderDescriptor.getIntegratorName());
		}

		if (renderDescriptor.getExtraIntegratorConfig() != null) {
			LOG.trace("Updating RenderDescriptor.extraIntegratorConfig = {}",
					LOG.isTraceEnabled()
							? renderDescriptor
									.getExtraIntegratorConfig()
										.substring(0,
												( 32 > renderDescriptor.getExtraIntegratorConfig().length() ? 32
														: renderDescriptor.getExtraIntegratorConfig().length() ))
									+ "..."
							: "");
			current.setExtraIntegratorConfig(renderDescriptor.getExtraIntegratorConfig());
		}

		if (renderDescriptor.getRenderingStatus() != null
				&& renderDescriptor.getRenderingStatus() != RenderStatus.NOT_STARTED) {
			LOG.trace("Updating RenderDescriptor.renderingStatus = {}", renderDescriptor.getRenderingStatus().name());
			current.setRenderingStatus(renderDescriptor.getRenderingStatus());
		}

		if (renderDescriptor.getRenderedImages() != null && !renderDescriptor.getRenderedImages().isEmpty()) {
			final Set<Resource> resources = new TreeSet<>((i1, i2) -> Long.compare(i1.getId(), i2.getId()));

			if (current.getRenderedImages() != null)
				resources.addAll(current.getRenderedImages());

			resources.addAll(renderDescriptor.getRenderedImages());

			LOG.trace("Updating RenderDescriptor.renderedImages <-- {} entries", resources.size());
			current.setRenderedImages(resources);
		}

		LOG.debug("Saving the updated RenderDescriptor ...");
		final RenderDescriptor result = renderDescriptorRepository.save(current);

		return result;
	}

	/**
	 * Add a new image to the given RenderDescriptor (given by its ID). Returns
	 * the updated RenderDescriptor.
	 * 
	 * @param renderId
	 * @param newImage
	 * @throws NullPointerException
	 *             if no such RenderDescriptor exists with ID =
	 *             <code>renderId</code>
	 */
	@Transactional
	public RenderDescriptor addNewImage(Long renderId, Resource newImage) {

		LOG.info("Received new image to add to render-ID {}", renderId);

		final RenderDescriptor render = renderDescriptorRepository.findOne(renderId);

		if (render == null) {
			final RuntimeException e = new NullPointerException("Cannot find RenderDescriptor with ID = " + renderId);
			LOG.error("Problem adding image to RenderDescriptor.", e);
			throw e;
		}

		LOG.debug("New image: {} bytes, of type \"{}\"", Integer.toString(newImage.getData().length),
				newImage.getMimeType());

		LOG.debug("Persisting new image as a Resource ...");
		newImage = resourceRepository.save(newImage);

		LOG.debug("Updating RenderDescriptor ...");
		LOG.trace("renderedImages << Resource ({} bytes, \"{}\")", newImage.getData().length, newImage.getMimeType());
		render.getRenderedImages().add(newImage);

		LOG.debug("Persisting updated RenderDescriptor ...");
		return renderDescriptorRepository.save(render);
	}

}
