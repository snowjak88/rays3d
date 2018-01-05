package org.rays3d.builder.java.shape;

import org.rays3d.builder.java.AbstractBuilder;
import org.rays3d.builder.java.BuilderException;
import org.rays3d.shape.SphereShape;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SphereBuilder<P extends AbstractBuilder<?, ?>> extends ShapeBuilder<SphereShape, SphereBuilder<P>, P> {

	private static final Logger	LOG		= LoggerFactory.getLogger(SphereBuilder.class);

	private double				radius	= 0d;

	public SphereBuilder() {
		super();
	}

	public SphereBuilder(P parentBuilder) {
		super(parentBuilder);
	}

	/**
	 * Configure this sphere's radius.
	 * 
	 * @param radius
	 * @return
	 */
	public SphereBuilder<P> radius(double radius) {

		this.radius = radius;
		return this;
	}

	@Override
	public SphereShape build() throws BuilderException {

		try {

			if (radius == 0d)
				LOG.warn("Building a SphereShape with a radius of {}", radius);

			return new SphereShape(radius, buildWorldToLocalTransforms());

		} catch (BuilderException e) {
			LOG.error("Encountered exception while building a SphereShape.", e);
			throw e;
		}
	}

}
