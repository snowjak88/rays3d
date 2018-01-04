package org.rays3d.builder.bxdf;

import static org.apache.commons.math3.util.FastMath.PI;

import org.rays3d.builder.AbstractBuilder;
import org.rays3d.builder.BuilderException;
import org.rays3d.builder.spectrum.SpectrumBuilder;
import org.rays3d.builder.texture.ConstantTextureBuilder;
import org.rays3d.builder.texture.TextureBuilder;
import org.rays3d.bxdf.LambertianBRDF;
import org.rays3d.spectrum.RGBSpectrum;
import org.rays3d.spectrum.Spectrum;
import org.rays3d.texture.ConstantTexture;
import org.rays3d.texture.Texture;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LambertianBRDFBuilder<P extends AbstractBuilder<?, ?>> implements BSDFBuilder<LambertianBRDF, P> {

	private static final Logger						LOG	= LoggerFactory.getLogger(LambertianBRDFBuilder.class);

	private P										parentBuilder;

	private TextureBuilder<? extends Texture, ?, ?>	textureBuilder;
	private TextureBuilder<? extends Texture, ?, ?>	emissiveBuilder;
	private Spectrum								emissivePower;

	public LambertianBRDFBuilder() {
		this(null);
	}

	public LambertianBRDFBuilder(P parentBuilder) {
		this.parentBuilder = parentBuilder;
	}

	/**
	 * Configure the surface {@link Texture} associated with this
	 * {@link LambertianBRDF}.
	 * 
	 * @param textureBuilder
	 * @return
	 */
	public LambertianBRDFBuilder<P> texture(TextureBuilder<? extends Texture, ?, ?> textureBuilder) {

		this.textureBuilder = textureBuilder;
		return this;
	}

	/**
	 * Configure the emissive {@link Texture}, along with a {@link Spectrum}
	 * representing its total power, associated with this
	 * {@link LambertianBRDF}.
	 * 
	 * @param emissiveBuilder
	 * @param emissivePowerBuilder
	 * @return
	 */
	public LambertianBRDFBuilder<P> emissive(TextureBuilder<? extends Texture, ?, ?> emissiveBuilder,
			SpectrumBuilder<? extends Spectrum, ?> emissivePowerBuilder) {

		this.emissiveBuilder = emissiveBuilder;
		this.emissivePower = emissivePowerBuilder.build();
		return this;
	}

	/**
	 * Configure the emissive {@link Spectrum} as representing the power emitted
	 * across the unit steradian from this {@link LambertianBRDF}. Total power
	 * is assumed to be equal to <code>4 * PI * emissive</code>.
	 * 
	 * @param emissiveBuilder
	 * @return
	 */
	public LambertianBRDFBuilder<P> emissive(SpectrumBuilder<? extends Spectrum, ?> emissiveBuilder) {

		this.emissiveBuilder = new ConstantTextureBuilder<>().spectrum(emissiveBuilder);
		this.emissivePower = emissiveBuilder.build().multiply(4d * PI);
		return this;
	}

	@Override
	public LambertianBRDF build() throws BuilderException {

		try {

			if (textureBuilder == null)
				throw new BuilderException("Cannot build a LambertianBRDF without a configured surface Texture!");

			if (emissiveBuilder != null ^ emissivePower != null)
				throw new BuilderException(
						"Fatal error: somehow you have set up only one of [emissiveBuilder] and [emissivePower]!");

			final Texture emissive;
			if (emissiveBuilder == null) {
				emissive = new ConstantTexture(RGBSpectrum.BLACK);
				emissivePower = RGBSpectrum.BLACK;
			} else {
				emissive = emissiveBuilder.build();
			}

			return new LambertianBRDF(textureBuilder.build(), emissive, emissivePower);

		} catch (

		BuilderException e) {
			LOG.error("Encountered exception while building a LambertianBRDF.", e);
			throw e;
		}
	}

	@Override
	public P end() {

		return parentBuilder;
	}
}
