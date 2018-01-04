package org.rays3d.builder.groovy.texture

import org.rays3d.texture.Texture
import org.rays3d.texture.mapping.TextureMapping

class CheckerboardTextureSpec {

	private List<Texture> textures = []
	private TextureMapping mapping = Texture.DEFAULT_MAPPING
	
	public texture(Texture texture) {
		textures << texture
	}
	
	public mapping(TextureMapping mapping) {
		this.mapping = mapping
	}
	
}
