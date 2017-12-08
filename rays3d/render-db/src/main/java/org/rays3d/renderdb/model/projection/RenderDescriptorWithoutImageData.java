package org.rays3d.renderdb.model.projection;

import java.util.Date;

import org.rays3d.renderdb.model.RenderDescriptor;
import org.rays3d.renderdb.model.RenderStatus;
import org.rays3d.renderdb.model.World;
import org.springframework.data.rest.core.config.Projection;

@Projection(name = "noImageData", types = { RenderDescriptor.class })
public interface RenderDescriptorWithoutImageData {

	public long getId();

	public int getVersion();

	public Date getCreated();

	public World getWorld();

	public int getRenderedImageWidth();

	public int getRenderedImageHeight();

	public String getSamplerName();

	public int getSamplesPerPixel();

	public RenderStatus getSamplingStatus();

	public RenderStatus getIntegrationStatus();

	public RenderStatus getFilmStatus();

	public String getImageMimeType();
}
