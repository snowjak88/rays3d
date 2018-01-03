package org.rays3d.builder.spectrum;

import org.rays3d.builder.AbstractBuilder;
import org.rays3d.builder.BuilderException;
import org.rays3d.spectrum.RGB;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RGBBuilder<P extends AbstractBuilder<?, ?>> implements AbstractBuilder<RGB, P> {

	private static final Logger	LOG	= LoggerFactory.getLogger(RGBBuilder.class);

	private P					parentBuilder;

	private double				r, g, b;

	public RGBBuilder() {
		this(null);
	}

	public RGBBuilder(P parentBuilder) {
		this.parentBuilder = parentBuilder;
	}

	/**
	 * Configure this RGB-trio's "red" component.
	 * 
	 * @param red
	 * @return
	 */
	public RGBBuilder<P> red(double r) {

		this.r = r;
		return this;
	}

	/**
	 * Configure this RGB-trio's "green" component.
	 * 
	 * @param red
	 * @return
	 */
	public RGBBuilder<P> green(double g) {

		this.g = g;
		return this;
	}

	/**
	 * Configure this RGB-trio's "blue" component.
	 * 
	 * @param red
	 * @return
	 */
	public RGBBuilder<P> blue(double b) {

		this.b = b;
		return this;
	}

	/**
	 * Configure this RGB-trio as a copy of an existing {@link RGB} instance.
	 * 
	 * @param rgb
	 * @return
	 */
	public RGBBuilder<P> copy(RGB rgb) {

		this.r = rgb.getRed();
		this.g = rgb.getGreen();
		this.b = rgb.getBlue();
		return this;
	}

	@Override
	public RGB build() throws BuilderException {

		try {

			return new RGB(r, g, b);

		} catch (BuilderException e) {
			LOG.error("Encountered exception while building an RGB trio.", e);
			throw e;
		}
	}

	@Override
	public P getParentBuilder() {

		return parentBuilder;
	}

}
