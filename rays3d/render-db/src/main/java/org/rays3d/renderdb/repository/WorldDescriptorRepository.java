package org.rays3d.renderdb.repository;

import org.rays3d.renderdb.model.WorldDescriptor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository("worldDescriptorRepository")
public interface WorldDescriptorRepository extends CrudRepository<WorldDescriptor, Long> {

	public WorldDescriptor findByRenderDescriptorsId(Long id);

}
