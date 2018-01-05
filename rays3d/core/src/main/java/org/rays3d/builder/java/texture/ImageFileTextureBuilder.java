package org.rays3d.builder.java.texture;

import java.io.File;
import java.io.IOException;

import org.rays3d.builder.java.AbstractBuilder;
import org.rays3d.builder.java.BuilderException;
import org.rays3d.builder.java.texture.mapping.TextureMappingBuilder;
import org.rays3d.texture.ImageFileTexture;
import org.rays3d.texture.ImageFileTexture.UnknownImageFileTypeException;
import org.rays3d.texture.mapping.LinearTextureMapping;
import org.rays3d.texture.mapping.TextureMapping;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ImageFileTextureBuilder<P extends AbstractBuilder<?, ?>>
		implements TextureBuilder<ImageFileTexture, ImageFileTextureBuilder<P>, P> {

	private static final Logger									LOG						= LoggerFactory
			.getLogger(ImageFileTextureBuilder.class);

	private P													parentBuilder;

	private File												imageFile				= null;
	private TextureMappingBuilder<? extends TextureMapping, ?>	textureMappingBuilder	= null;

	public ImageFileTextureBuilder() {
		this(null);
	}

	public ImageFileTextureBuilder(P parentBuilder) {
		this.parentBuilder = parentBuilder;
	}

	@Override
	public ImageFileTextureBuilder<P> textureMapping(
			TextureMappingBuilder<? extends TextureMapping, ?> textureMappingBuilder) {

		this.textureMappingBuilder = textureMappingBuilder;
		return this;
	}

	/**
	 * Configure the image {@link File} associated with this
	 * {@link ImageFileTexture}.
	 * 
	 * @param imageFile
	 * @return
	 */
	public ImageFileTextureBuilder<P> image(File imageFile) {

		this.imageFile = imageFile;
		return this;
	}

	@Override
	public ImageFileTexture build() throws BuilderException {

		try {

			if (imageFile == null)
				throw new BuilderException("Cannot build an ImageFileTexture without a configured image File!");

			final TextureMapping textureMapping;

			if (textureMappingBuilder == null) {
				LOG.warn("Texture-mapping is not configured; using the default LinearTextureMapping.");
				textureMapping = new LinearTextureMapping();
			} else {
				textureMapping = textureMappingBuilder.build();
			}

			try {
				return new ImageFileTexture(imageFile, textureMapping);

			} catch (IOException | UnknownImageFileTypeException e) {
				throw new BuilderException(e);
			}

		} catch (BuilderException e) {
			LOG.error("Encountered exception while building an ImageFileTexture.", e);
			throw e;
		}
	}

	@Override
	public P end() {

		return parentBuilder;
	}

}
