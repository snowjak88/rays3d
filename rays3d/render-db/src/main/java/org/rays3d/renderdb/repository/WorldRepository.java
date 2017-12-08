package org.rays3d.renderdb.repository;

import org.rays3d.renderdb.model.World;
import org.rays3d.renderdb.model.projection.WorldWithoutFile;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

@Repository
@RepositoryRestResource(excerptProjection = WorldWithoutFile.class)
public interface WorldRepository extends PagingAndSortingRepository<World, Long> {

}
