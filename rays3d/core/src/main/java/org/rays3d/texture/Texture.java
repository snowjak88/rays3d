package org.rays3d.texture;

import org.rays3d.interact.DescribesSurface;
import org.rays3d.interact.SurfaceDescriptor;
import org.rays3d.spectrum.Spectrum;
import org.rays3d.texture.mapping.LinearTextureMapping;
import org.rays3d.texture.mapping.TextureMapping;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Represents a texture.
 * 
 * @author snowjak88
 */
public abstract class Texture {

	public static final TextureMapping	DEFAULT_MAPPING	= new LinearTextureMapping();

	@JsonProperty
	private TextureMapping				textureMapping;

	/**
	 * Construct this Texture using the default {@link TextureMapping} to
	 * translate to/from surface-parameterization coordinates. (see
	 * {@link #DEFAULT_MAPPING})
	 * 
	 * @param textureMapping
	 */
	public Texture() {
		this(DEFAULT_MAPPING);
	}

	/**
	 * Construct this Texture using the specified {@link TextureMapping} to
	 * translate to/from surface-parameterization coordinates.
	 * 
	 * @param textureMapping
	 */
	public Texture(TextureMapping textureMapping) {
		this.textureMapping = textureMapping;
	}

	/**
	 * Given the specified {@link SurfaceDescriptor}, compute the resulting
	 * coloration of this texture.
	 * 
	 * @param surface
	 * @return
	 */
	public abstract <T extends DescribesSurface> Spectrum evaluate(SurfaceDescriptor<T> surface);

	/**
	 * @return this texture's assigned {@link TextureMapping}
	 */
	public TextureMapping getTextureMapping() {

		return textureMapping;
	}

	protected void setTextureMapping(TextureMapping textureMapping) {

		this.textureMapping = textureMapping;
	}
}
