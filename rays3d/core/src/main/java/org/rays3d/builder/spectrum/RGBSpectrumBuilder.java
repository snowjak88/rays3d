package org.rays3d.builder.spectrum;

import org.rays3d.builder.AbstractBuilder;
import org.rays3d.builder.BuilderException;
import org.rays3d.spectrum.RGB;
import org.rays3d.spectrum.RGBSpectrum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RGBSpectrumBuilder<P extends AbstractBuilder<?, ?>> extends SpectrumBuilder<RGBSpectrum, P> {

	private static final Logger					LOG			= LoggerFactory.getLogger(RGBSpectrumBuilder.class);

	private RGBBuilder<RGBSpectrumBuilder<P>>	rgbBuilder	= null;

	public RGBSpectrumBuilder() {
		this(null);
	}

	public RGBSpectrumBuilder(P parentBuilder) {
		super(parentBuilder);
	}

	/**
	 * Configure an {@link RGB} trio for this {@link RGBSpectrum}.
	 * 
	 * @return
	 */
	public RGBBuilder<RGBSpectrumBuilder<P>> rgb() {

		this.rgbBuilder = new RGBBuilder<>(this);
		return this.rgbBuilder;
	}

	/**
	 * Configure an {@link RGB} trio for this {@link RGBSpectrum}.
	 * 
	 * @return
	 */
	public RGBSpectrumBuilder<P> rgb(RGB rgb) {

		this.rgbBuilder = new RGBBuilder<>(this).copy(rgb);
		return this;
	}

	@Override
	public RGBSpectrum build() throws BuilderException {

		try {

			if (rgbBuilder == null)
				throw new BuilderException("Cannot build an RGBSpectrum without a configured RGB trio!");

			return new RGBSpectrum(rgbBuilder.build());

		} catch (BuilderException e) {
			LOG.error("Exception encountered while building an RGBSpectrum.", e);
			throw e;
		}
	}

}
