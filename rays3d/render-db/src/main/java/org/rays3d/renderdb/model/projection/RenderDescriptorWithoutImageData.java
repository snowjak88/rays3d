package org.rays3d.renderdb.model.projection;

import java.util.Date;

import org.rays3d.message.RenderStatus;
import org.rays3d.renderdb.model.RenderDescriptor;
import org.rays3d.renderdb.model.World;
import org.springframework.data.rest.core.config.Projection;

@Projection(name = "noImageData", types = { RenderDescriptor.class })
public interface RenderDescriptorWithoutImageData {

	public long getId();

	public int getVersion();

	public Date getCreated();

	public World getWorld();

	public int getFilmWidth();

	public int getFilmHeight();

	public String getSamplerName();

	public int getSamplesPerPixel();

	public String getIntegratorName();

	public String getExtraIntegratorConfig();

	public RenderStatus getRenderingStatus();

	public RenderStatus getSamplingStatus();

	public RenderStatus getIntegrationStatus();

	public RenderStatus getFilmStatus();

	public String getImageMimeType();
}
