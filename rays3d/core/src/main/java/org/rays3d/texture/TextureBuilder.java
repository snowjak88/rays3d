package org.rays3d.texture;

import org.rays3d.builder.AbstractBuilder;
import org.rays3d.texture.mapping.TextureMapping;
import org.rays3d.texture.mapping.TextureMappingBuilder;

public interface TextureBuilder<T extends Texture, TB extends TextureBuilder<T, TB, P>, P extends AbstractBuilder<?, ?>>
		extends AbstractBuilder<T, P> {

	/**
	 * Configure the {@link TextureMapping} associated with this
	 * {@link Texture}.
	 * 
	 * @param textureMappingBuilder
	 * @return
	 */
	public TB textureMapping(TextureMappingBuilder<? extends TextureMapping, ?> textureMappingBuilder);
}
