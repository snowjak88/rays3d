package org.rays3d.builder.groovy.texture

import org.rays3d.builder.groovy.WorldBuilder.Txtr
import org.rays3d.texture.Texture
import org.rays3d.texture.mapping.TextureMapping

class ImageFileTextureSpec {

	private File imageFile
	private TextureMapping mapping = Texture.DEFAULT_MAPPING
	
	public static Txtr = new Txtr()

	public image(File imageFile) {
		this.imageFile = imageFile
	}

	public mapping(TextureMapping mapping) {
		this.mapping = mapping
	}
}
