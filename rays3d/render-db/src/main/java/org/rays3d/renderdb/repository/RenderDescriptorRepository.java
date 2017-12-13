package org.rays3d.renderdb.repository;

import java.util.Collection;

import org.rays3d.renderdb.model.RenderDescriptor;
import org.rays3d.renderdb.model.projection.RenderDescriptorWithoutImageData;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

@Repository
@RepositoryRestResource(excerptProjection = RenderDescriptorWithoutImageData.class)
public interface RenderDescriptorRepository extends PagingAndSortingRepository<RenderDescriptor, Long> {

	@Query("select d from RenderDescriptor d where renderingStatus = 'NOT_STARTED'")
	public Collection<RenderDescriptor> findNewDescriptors();
}
