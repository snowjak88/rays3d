package org.rays3d.renderdb.repository;

import java.util.Collection;

import org.rays3d.renderdb.model.RenderedImage;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository("renderedImageRepository")
public interface RenderedImageRepository extends CrudRepository<RenderedImage, Long> {

	public Collection<RenderedImage> findByRenderDescriptorId(long id);
}
