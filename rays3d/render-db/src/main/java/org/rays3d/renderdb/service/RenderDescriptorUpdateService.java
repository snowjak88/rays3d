package org.rays3d.renderdb.service;

import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.rays3d.message.RenderStatus;
import org.rays3d.renderdb.model.RenderDescriptor;
import org.rays3d.renderdb.model.Resource;
import org.rays3d.renderdb.repository.RenderDescriptorRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RenderDescriptorUpdateService {

	private final Lock					renderDescriptorUpdateLock	= new ReentrantLock();

	private static final Logger			LOG							= LoggerFactory
			.getLogger(RenderDescriptorUpdateService.class);

	@Autowired
	private RenderDescriptorRepository	renderDescriptorRepository;

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
	public RenderDescriptor updateRenderDescriptor(RenderDescriptor renderDescriptor) {

		LOG.debug("Updating a RenderDescriptor (given ID = " + renderDescriptor.getId() + ", version = "
				+ renderDescriptor.getVersion() + ")");

		renderDescriptorUpdateLock.lock();

		final RenderDescriptor current = renderDescriptorRepository.findOne(renderDescriptor.getId());

		if (current == null) {
			LOG.debug("No RenderDescriptor found by ID = " + renderDescriptor.getId() + " -- cannot update.");
			return null;
		}

		if (renderDescriptor.getWorldDescriptor() != null)
			current.setWorldDescriptor(renderDescriptor.getWorldDescriptor());

		if (renderDescriptor.getFilmWidth() > 0) {
			LOG.trace("Updating RenderDescriptor.filmWidth = " + renderDescriptor.getFilmWidth());
			current.setFilmWidth(renderDescriptor.getFilmWidth());
		}

		if (renderDescriptor.getFilmHeight() > 0) {
			LOG.trace("Updating RenderDescriptor.filmHeight = " + renderDescriptor.getFilmHeight());
			current.setFilmHeight(renderDescriptor.getFilmHeight());
		}

		if (renderDescriptor.getSamplerName() != null) {
			LOG.trace("Updating RenderDescriptor.samplerName = " + renderDescriptor.getSamplerName());
			current.setSamplerName(renderDescriptor.getSamplerName());
		}

		if (renderDescriptor.getSamplesPerPixel() > 0) {
			LOG.trace("Updating RenderDescriptor.samplesPerPixel = " + renderDescriptor.getSamplesPerPixel());
			current.setSamplesPerPixel(renderDescriptor.getSamplesPerPixel());
		}

		if (renderDescriptor.getIntegratorName() != null) {
			LOG.trace("Updating RenderDescriptor.integratorName = " + renderDescriptor.getIntegratorName());
			current.setIntegratorName(renderDescriptor.getIntegratorName());
		}

		if (renderDescriptor.getExtraIntegratorConfig() != null) {
			LOG.trace(
					"Updating RenderDescriptor.extraIntegratorConfig = "
							+ renderDescriptor
									.getExtraIntegratorConfig()
										.substring(0,
												( 32 > renderDescriptor.getExtraIntegratorConfig().length() ? 32
														: renderDescriptor.getExtraIntegratorConfig().length() ))
							+ "...");
			current.setExtraIntegratorConfig(renderDescriptor.getExtraIntegratorConfig());
		}

		if (renderDescriptor.getRenderingStatus() != null
				&& renderDescriptor.getRenderingStatus() != RenderStatus.NOT_STARTED) {
			LOG.trace("Updating RenderDescriptor.renderingStatus = " + renderDescriptor.getRenderingStatus().name());
			current.setRenderingStatus(renderDescriptor.getRenderingStatus());
		}

		if (renderDescriptor.getSamplingStatus() != null
				&& renderDescriptor.getSamplingStatus() != RenderStatus.NOT_STARTED) {
			LOG.trace("Updating RenderDescriptor.samplingStatus = " + renderDescriptor.getSamplingStatus().name());
			current.setSamplingStatus(renderDescriptor.getSamplingStatus());
		}

		if (renderDescriptor.getIntegrationStatus() != null
				&& renderDescriptor.getIntegrationStatus() != RenderStatus.NOT_STARTED) {
			LOG.trace(
					"Updating RenderDescriptor.integrationStatus = " + renderDescriptor.getIntegrationStatus().name());
			current.setIntegrationStatus(renderDescriptor.getIntegrationStatus());
		}

		if (renderDescriptor.getFilmStatus() != null && renderDescriptor.getFilmStatus() != RenderStatus.NOT_STARTED) {
			LOG.trace("Updating RenderDescriptor.filmStatus = " + renderDescriptor.getFilmStatus().name());
			current.setFilmStatus(renderDescriptor.getFilmStatus());
		}

		if (renderDescriptor.getRenderedImages() != null && !renderDescriptor.getRenderedImages().isEmpty()) {
			final Set<Resource> resources = new TreeSet<>((i1, i2) -> Long.compare(i1.getId(), i2.getId()));

			if (current.getRenderedImages() != null)
				resources.addAll(current.getRenderedImages());

			resources.addAll(renderDescriptor.getRenderedImages());

			LOG.trace("Updating RenderDescriptor.renderedImages <-- " + resources.size() + " entries");
			current.setRenderedImages(resources);
		}

		LOG.trace("Saving the updated RenderDescriptor ...");
		final RenderDescriptor result = renderDescriptorRepository.save(current);

		renderDescriptorUpdateLock.unlock();

		return result;
	}

}
