package org.rays3d.renderdb.service;

import javax.persistence.EntityExistsException;

import org.rays3d.renderdb.model.Resource;
import org.rays3d.renderdb.repository.ResourceRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ResourceUpdateService {

	private final static Logger	LOG	= LoggerFactory.getLogger(ResourceUpdateService.class);

	@Autowired
	private ResourceRepository	resourceRepository;

	/**
	 * Add a new {@link Resource} to the Render-DB, and return the now-current
	 * Resource instance.
	 * 
	 * @param newResource
	 * @return
	 * @throws EntityExistsException
	 *             if the given Resource's ID already exists in the Render-DB
	 */
	@Transactional
	public Resource postNewResource(Resource newResource) {

		LOG.info("Adding new resource to the Render-DB ...");

		LOG.debug("Checking if this Resource already exists under ID = {}", newResource.getId());
		if (resourceRepository.exists(newResource.getId())) {
			final RuntimeException e = new EntityExistsException("Cannot create new Resource -- given ID ("
					+ newResource.getId() + ") already exists in Render-DB!");
			LOG.error("Cannot create new Resource.", e);
			throw e;
		}

		LOG.debug("Persisting the new Resource object ...");
		newResource = resourceRepository.save(newResource);

		LOG.debug("Added new resource to the Render-DB -- ID = {}", newResource.getId());
		return newResource;
	}

	/**
	 * Update an existing {@link Resource} on the Render-DB, and return the
	 * now-current Resource (or <code>null</code> if that Resource was not found
	 * with the given ID).
	 * 
	 * @param updatedResource
	 * @return
	 */
	@Transactional
	public Resource updateResource(Resource updatedResource) {

		LOG.info("Updating resource (ID = {})", updatedResource.getId());

		if (!resourceRepository.exists(updatedResource.getId())) {
			LOG.warn("No existing Resource found with ID = {}!", updatedResource.getId());
			return null;
		}

		Resource existingResource = resourceRepository.findOne(updatedResource.getId());

		LOG.trace("Setting Resource.data = ({} bytes)", updatedResource.getData().length);
		existingResource.setData(updatedResource.getData());
		LOG.trace("Setting Resource.mime-type = \"{}\"", updatedResource.getMimeType());
		existingResource.setMimeType(updatedResource.getMimeType());

		LOG.debug("Persisting the update Resource object ...");
		existingResource = resourceRepository.save(existingResource);

		return existingResource;
	}

}
