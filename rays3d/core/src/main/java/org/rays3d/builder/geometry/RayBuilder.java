package org.rays3d.builder.geometry;

import org.rays3d.builder.BuilderException;
import org.rays3d.geometry.Point3D;
import org.rays3d.geometry.Ray;
import org.rays3d.geometry.Vector3D;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.rays3d.builder.AbstractBuilder;

public class RayBuilder<P extends AbstractBuilder<?, ?>> implements AbstractBuilder<Ray, P> {

	private static final Logger				LOG	= LoggerFactory.getLogger(RayBuilder.class);

	private P								parentBuilder;

	private Point3DBuilder<RayBuilder<P>>	originBuilder;
	private Vector3DBuilder<RayBuilder<P>>	directionBuilder;

	public RayBuilder() {
		this(null);
	}

	public RayBuilder(P parentBuilder) {
		this.parentBuilder = parentBuilder;
	}

	/**
	 * Configure the {@link Point3D} describing this Ray's origin.
	 * 
	 * @return
	 */
	public Point3DBuilder<RayBuilder<P>> origin() {

		originBuilder = new Point3DBuilder<>(this);
		return originBuilder;
	}

	/**
	 * Configure the {@link Vector3D} describing this Ray's direction.
	 * 
	 * @return
	 */
	public Vector3DBuilder<RayBuilder<P>> direction() {

		directionBuilder = new Vector3DBuilder<>(this);
		return directionBuilder;
	}

	@Override
	public Ray build() throws BuilderException {

		try {

			if (originBuilder == null)
				throw new BuilderException("Cannot build a Ray without a configured origin!");
			if (directionBuilder == null)
				throw new BuilderException("Cannot build a Ray without a configured direction!");

			return new Ray(originBuilder.build(), directionBuilder.build());

		} catch (BuilderException e) {
			LOG.error("Cannot build this Ray.", e);
			throw e;
		}
	}

	@Override
	public P end() {

		return parentBuilder;
	}

}
