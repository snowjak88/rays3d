package org.rays3d.builder.transform;

import org.rays3d.builder.AbstractBuilder;
import org.rays3d.builder.BuilderException;
import org.rays3d.transform.TranslationTransform;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TranslationTransformBuilder<P extends AbstractBuilder<?, ?>>
		extends TransformBuilder<TranslationTransform, P> {

	private static final Logger	LOG	= LoggerFactory.getLogger(TranslationTransformBuilder.class);

	private double				tx	= 0d, ty = 0d, tz = 0d;

	public TranslationTransformBuilder() {
		super();
	}

	public TranslationTransformBuilder(P parentBuilder) {
		super(parentBuilder);
	}

	/**
	 * Configure this scale-transform's scale-factor along the X-axis.
	 * 
	 * @param tx
	 * @return
	 */
	public TranslationTransformBuilder<P> dx(double tx) {

		this.tx = tx;
		return this;
	}

	/**
	 * Configure this scale-transform's scale-factor along the Y-axis.
	 * 
	 * @param ty
	 * @return
	 */
	public TranslationTransformBuilder<P> dy(double ty) {

		this.ty = ty;
		return this;
	}

	/**
	 * Configure this scale-transform's scale-factor along the Z-axis.
	 * 
	 * @param tz
	 * @return
	 */
	public TranslationTransformBuilder<P> dz(double tz) {

		this.tz = tz;
		return this;
	}

	@Override
	public TranslationTransform build() throws BuilderException {

		try {

			return new TranslationTransform(tx, ty, tz);

		} catch (BuilderException e) {
			LOG.error("Exception encountered while building a TranslationTransform.", e);
			throw e;
		}
	}

}
