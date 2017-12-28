package org.rays3d.texture;

import org.rays3d.interact.DescribesSurface;
import org.rays3d.interact.SurfaceDescriptor;
import org.rays3d.spectrum.Spectrum;
import org.rays3d.texture.mapping.TextureMapping;

/**
 * Represents a texture.
 * 
 * @author snowjak88
 */
public abstract class Texture {

	private TextureMapping textureMapping;

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
}
