package org.rays3d.builder.texture;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import org.rays3d.builder.AbstractBuilder;
import org.rays3d.builder.BuilderException;
import org.rays3d.builder.texture.mapping.TextureMappingBuilder;
import org.rays3d.texture.CheckerboardTexture;
import org.rays3d.texture.Texture;
import org.rays3d.texture.mapping.LinearTextureMapping;
import org.rays3d.texture.mapping.TextureMapping;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CheckerboardTextureBuilder<P extends AbstractBuilder<?, ?>>
		implements TextureBuilder<CheckerboardTexture, CheckerboardTextureBuilder<P>, P> {

	private static final Logger									LOG						= LoggerFactory
			.getLogger(CheckerboardTextureBuilder.class);

	private P													parentBuilder;
	private List<TextureBuilder<? extends Texture, ?, ?>>		textureBuilders			= new LinkedList<>();
	private TextureMappingBuilder<? extends TextureMapping, ?>	textureMappingBuilder	= null;

	public CheckerboardTextureBuilder() {
		this(null);
	}

	public CheckerboardTextureBuilder(P parentBuilder) {
		this.parentBuilder = parentBuilder;
	}

	/**
	 * Add a texture to this {@link CheckerboardTexture}.
	 * 
	 * @param textureBuilder
	 * @return
	 */
	public CheckerboardTextureBuilder<P> texture(TextureBuilder<? extends Texture, ?, ?> textureBuilder) {

		textureBuilders.add(textureBuilder);
		return this;
	}

	@Override
	public CheckerboardTextureBuilder<P> textureMapping(
			TextureMappingBuilder<? extends TextureMapping, ?> textureMappingBuilder) {

		this.textureMappingBuilder = textureMappingBuilder;
		return this;
	}

	@Override
	public CheckerboardTexture build() throws BuilderException {

		try {

			if (textureBuilders.isEmpty())
				throw new BuilderException("Cannot build a CheckerboardTexture without any configured Textures!");

			final TextureMapping textureMapping;

			if (textureMappingBuilder == null) {
				LOG.warn("No TextureMapping configured; using the default LinearTextureMapping.");
				textureMapping = new LinearTextureMapping();
			} else {
				textureMapping = textureMappingBuilder.build();
			}

			return new CheckerboardTexture(textureBuilders
					.stream()
						.map(tb -> tb.build())
						.collect(Collectors.toCollection(ArrayList::new))
						.toArray(new Texture[0]),
					textureMapping);

		} catch (BuilderException e) {
			LOG.error("Encountered exception while building a CheckerboardTexture.", e);
			throw e;
		}
	}

	@Override
	public P end() {

		return parentBuilder;
	}

}
