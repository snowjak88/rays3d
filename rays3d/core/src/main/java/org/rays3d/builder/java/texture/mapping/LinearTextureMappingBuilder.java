package org.rays3d.builder.java.texture.mapping;

import org.rays3d.builder.java.AbstractBuilder;
import org.rays3d.builder.java.BuilderException;
import org.rays3d.texture.mapping.LinearTextureMapping;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LinearTextureMappingBuilder<P extends AbstractBuilder<?, ?>>
		implements TextureMappingBuilder<LinearTextureMapping, P> {

	private static final Logger	LOG			= LoggerFactory.getLogger(LinearTextureMappingBuilder.class);

	private P					parentBuilder;

	private double				textureMinU	= 0d, textureMinV = 0d, textureMaxU = 1d, textureMaxV = 1d;

	public LinearTextureMappingBuilder() {
		this(null);
	}

	public LinearTextureMappingBuilder(P parentBuilder) {
		this.parentBuilder = parentBuilder;
	}

	/**
	 * Configure the texture-mapping's minimum extent (U/V).
	 * 
	 * @param textureMinU
	 * @param textureMinV
	 * @return
	 */
	public LinearTextureMappingBuilder<P> from(double textureMinU, double textureMinV) {

		this.textureMinU = textureMinU;
		this.textureMinV = textureMinV;
		return this;
	}

	/**
	 * Configure the texture-mapping's maximum extent (U/V).
	 * 
	 * @param textureMaxU
	 * @param textureMaxV
	 * @return
	 */
	public LinearTextureMappingBuilder<P> to(double textureMaxU, double textureMaxV) {

		this.textureMaxU = textureMaxU;
		this.textureMaxV = textureMaxV;
		return this;
	}

	@Override
	public LinearTextureMapping build() throws BuilderException {

		try {

			if (textureMinU > textureMaxU)
				throw new BuilderException("Minimum extent-U [" + Double.toString(textureMinU)
						+ "] is greater than maximum extent-U [" + Double.toString(textureMaxU) + "]!");
			if (textureMinV > textureMaxV)
				throw new BuilderException("Minimum extent-V [" + Double.toString(textureMinV)
						+ "] is greater than maximum extent-V [" + Double.toString(textureMaxV) + "]!");

			return new LinearTextureMapping(textureMinU, textureMinV, textureMaxU, textureMaxV);

		} catch (BuilderException e) {
			LOG.error("Encountered exception while building a LinearTextureMapping.", e);
			throw e;
		}
	}

	@Override
	public P end() {

		return parentBuilder;
	}
}
