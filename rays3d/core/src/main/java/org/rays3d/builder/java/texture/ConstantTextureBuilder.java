package org.rays3d.builder.java.texture;

import org.rays3d.builder.java.AbstractBuilder;
import org.rays3d.builder.java.BuilderException;
import org.rays3d.builder.java.spectrum.SpectrumBuilder;
import org.rays3d.builder.java.texture.mapping.TextureMappingBuilder;
import org.rays3d.spectrum.Spectrum;
import org.rays3d.texture.ConstantTexture;
import org.rays3d.texture.mapping.TextureMapping;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ConstantTextureBuilder<P extends AbstractBuilder<?, ?>>
		implements TextureBuilder<ConstantTexture, ConstantTextureBuilder<P>, P> {

	private static final Logger									LOG						= LoggerFactory
			.getLogger(ConstantTextureBuilder.class);

	private P													parentBuilder;
	private SpectrumBuilder<? extends Spectrum, ?>				spectrumBuilder			= null;
	@SuppressWarnings("unused")
	private TextureMappingBuilder<? extends TextureMapping, ?>	textureMappingBuilder	= null;

	public ConstantTextureBuilder() {
		this(null);
	}

	public ConstantTextureBuilder(P parentBuilder) {
		this.parentBuilder = parentBuilder;
	}

	/**
	 * Configure this {@link ConstantTexture}'s {@link Spectrum} instance.
	 * 
	 * @param spectrumBuilder
	 * @return
	 */
	public ConstantTextureBuilder<P> spectrum(SpectrumBuilder<? extends Spectrum, ?> spectrumBuilder) {

		this.spectrumBuilder = spectrumBuilder;
		return this;
	}

	@Override
	public ConstantTextureBuilder<P> textureMapping(
			TextureMappingBuilder<? extends TextureMapping, ?> textureMappingBuilder) {

		this.textureMappingBuilder = textureMappingBuilder;
		return this;
	}

	@Override
	public ConstantTexture build() throws BuilderException {

		try {

			if (spectrumBuilder == null)
				throw new BuilderException("Cannot build a ConstantTexture without a configured Spectrum!");

			return new ConstantTexture(spectrumBuilder.build());

		} catch (BuilderException e) {
			LOG.error("Encountered exception while building a ConstantTexture.", e);
			throw e;
		}
	}

	@Override
	public P end() {

		return parentBuilder;
	}

}
