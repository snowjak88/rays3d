package org.rays3d.builder.java;

import org.rays3d.Primitive;
import org.rays3d.builder.java.bxdf.BSDFBuilder;
import org.rays3d.builder.java.shape.ShapeBuilder;
import org.rays3d.bxdf.BSDF;
import org.rays3d.shape.Shape;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PrimitiveBuilder<P extends AbstractBuilder<?, ?>> implements AbstractBuilder<Primitive, P> {

	private final static Logger																LOG				= LoggerFactory
			.getLogger(PrimitiveBuilder.class);

	private P																				parentBuilder;

	private ShapeBuilder<? extends Shape, ? extends ShapeBuilder<? extends Shape, ?, ?>, ?>	shapeBuilder	= null;
	private BSDFBuilder<? extends BSDF, ?>													bsdfBuilder		= null;

	public PrimitiveBuilder() {
		this(null);
	}

	public PrimitiveBuilder(P parentBuilder) {
		this.parentBuilder = parentBuilder;
	}

	/**
	 * Configure the {@link Shape} attached to this {@link Primitive}.
	 * 
	 * @param shapeBuilder
	 * @return
	 */
	public PrimitiveBuilder<P> shape(
			ShapeBuilder<? extends Shape, ? extends ShapeBuilder<? extends Shape, ?, ?>, ?> shapeBuilder) {

		this.shapeBuilder = shapeBuilder;
		return this;
	}

	/**
	 * Configure the {@link BSDF} attached to this {@link Primitive}.
	 * 
	 * @param bsdfBuilder
	 * @return
	 */
	public PrimitiveBuilder<P> bsdf(BSDFBuilder<? extends BSDF, ?> bsdfBuilder) {

		this.bsdfBuilder = bsdfBuilder;
		return this;
	}

	@Override
	public Primitive build() throws BuilderException {

		try {

			if (shapeBuilder == null)
				throw new BuilderException("Cannot build a Primitive without a configured Shape!");
			if (bsdfBuilder == null)
				throw new BuilderException("Cannot build a Primitive without a configured BSDF!");

			return new Primitive(shapeBuilder.build(), bsdfBuilder.build());

		} catch (BuilderException e) {
			LOG.error("Encountered exception while building a Primitive.", e);
			throw e;
		}
	}

	@Override
	public P end() {

		return parentBuilder;
	}

}
