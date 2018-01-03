package org.rays3d.transform;

import org.rays3d.builder.AbstractBuilder;
import org.rays3d.builder.BuilderException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ScaleTransformBuilder<P extends AbstractBuilder<?, ?>> extends TransformBuilder<ScaleTransform, P> {

	private static final Logger	LOG	= LoggerFactory.getLogger(ScaleTransformBuilder.class);

	private double				sx	= 0d, sy = 0d, sz = 0d;

	public ScaleTransformBuilder() {
		super();
	}

	public ScaleTransformBuilder(P parentBuilder) {
		super(parentBuilder);
	}

	/**
	 * Configure this scale-transform's scale-factor along the X-axis.
	 * 
	 * @param sx
	 * @return
	 */
	public ScaleTransformBuilder<P> sx(double sx) {

		this.sx = sx;
		return this;
	}

	/**
	 * Configure this scale-transform's scale-factor along the Y-axis.
	 * 
	 * @param sy
	 * @return
	 */
	public ScaleTransformBuilder<P> sy(double sy) {

		this.sy = sy;
		return this;
	}

	/**
	 * Configure this scale-transform's scale-factor along the Z-axis.
	 * 
	 * @param sz
	 * @return
	 */
	public ScaleTransformBuilder<P> sz(double sz) {

		this.sz = sz;
		return this;
	}

	@Override
	public ScaleTransform build() throws BuilderException {

		try {

			if (this.sx == 0d || sy == 0d || sz == 0d)
				LOG.warn("Building a ScaleTransform including a scale-factor of 0.0 ( sx:{}, sy:{},sz:{} )", this.sx,
						this.sy, this.sz);

			return new ScaleTransform(sx, sy, sz);

		} catch (BuilderException e) {
			LOG.error("Exception encountered while building a ScaleTransform.", e);
			throw e;
		}
	}

}
