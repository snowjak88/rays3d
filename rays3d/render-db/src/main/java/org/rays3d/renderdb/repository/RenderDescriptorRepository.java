package org.rays3d.renderdb.repository;

import java.util.Collection;

import org.rays3d.renderdb.model.RenderDescriptor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository("renderDescriptorRepository")
public interface RenderDescriptorRepository extends CrudRepository<RenderDescriptor, Long> {

	@Query("select d from RenderDescriptor d")
	public Collection<RenderDescriptor> findAllDescriptors();

	@Query("select d from RenderDescriptor d where renderingStatus = 'NOT_STARTED'")
	public Collection<RenderDescriptor> findNewDescriptors();
}
