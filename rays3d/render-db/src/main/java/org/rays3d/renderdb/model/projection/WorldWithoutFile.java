package org.rays3d.renderdb.model.projection;

import java.util.Date;

import org.rays3d.renderdb.model.World;
import org.springframework.data.rest.core.config.Projection;

@Projection(name = "noFile", types = { World.class })
public interface WorldWithoutFile {

	public long getId();

	public int getVersion();

	public Date getCreated();

	public String getName();

	public String getDescription();
}
