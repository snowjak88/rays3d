package org.rays3d.renderdb.repository;

import java.util.List;

import org.rays3d.renderdb.model.Resource;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository("renderedImageRepository")
public interface ResourceRepository extends CrudRepository<Resource, Long> {

	@Query("select d.renderedImages from RenderDescriptor d where d.id = ?1")
	public List<Resource> findByRenderDescriptorId(Long id);

}
