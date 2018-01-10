package org.rays3d.builder.java.shape;

import org.rays3d.builder.java.AbstractBuilder;
import org.rays3d.builder.java.BuilderException;
import org.rays3d.shape.PlaneShape;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PlaneBuilder<P extends AbstractBuilder<?, ?>> extends ShapeBuilder<PlaneShape, PlaneBuilder<P>, P> {

	private final static Logger LOG = LoggerFactory.getLogger(PlaneBuilder.class);

	public PlaneBuilder() {
		super();
	}

	@Override
	public PlaneShape build() throws BuilderException {

		try {

			return new PlaneShape(buildWorldToLocalTransforms());

		} catch (BuilderException e) {

			LOG.error("Encountered exception while building a PlaneShape", e);
			throw e;

		}
	}

}
