package org.rays3d.builder.java.bxdf;

import org.rays3d.builder.java.AbstractBuilder;
import org.rays3d.builder.java.BuilderException;
import org.rays3d.builder.java.texture.TextureBuilder;
import org.rays3d.bxdf.PerfectSpecularBRDF;
import org.rays3d.spectrum.RGBSpectrum;
import org.rays3d.texture.ConstantTexture;
import org.rays3d.texture.Texture;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PerfectSpecularBRDFBuilder<P extends AbstractBuilder<?, ?>>
		implements BSDFBuilder<PerfectSpecularBRDF, P> {

	private final static Logger						LOG				= LoggerFactory
			.getLogger(PerfectSpecularBRDFBuilder.class);

	private P										parentBuilder;
	private TextureBuilder<? extends Texture, ?, ?>	textureBuilder	= null;

	public PerfectSpecularBRDFBuilder() {
		this(null);
	}

	public PerfectSpecularBRDFBuilder(P parentBuilder) {
		this.parentBuilder = parentBuilder;
	}

	/**
	 * Configure the {@link Texture} controlling the "tint" applied to all
	 * reflections from this {@link PerfectSpecularBRDF}. (by default, this
	 * "tint" is {@link RGBSpectrum#WHITE})
	 * 
	 * @param textureBuilder
	 * @return
	 */
	public PerfectSpecularBRDFBuilder<P> tint(TextureBuilder<? extends Texture, ?, ?> textureBuilder) {

		this.textureBuilder = textureBuilder;
		return this;
	}

	@Override
	public PerfectSpecularBRDF build() throws BuilderException {

		try {

			final Texture texture;
			if (textureBuilder == null) {
				LOG.warn("Texture was not supplied; using default ConstantTexture(RGBSpectrum.WHITE)");
				texture = new ConstantTexture(RGBSpectrum.WHITE);
			} else {
				texture = textureBuilder.build();
			}

			return new PerfectSpecularBRDF(texture);

		} catch (BuilderException e) {
			LOG.error("Encountered exception while building a PerfectSpecularBRDF.", e);
			throw e;
		}
	}

	@Override
	public P end() {

		return parentBuilder;
	}
}
