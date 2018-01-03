package org.rays3d.geometry;

import org.rays3d.builder.AbstractBuilder;
import org.rays3d.builder.BuilderException;

public class Vector3DBuilder<P extends AbstractBuilder<?, ?>> implements AbstractBuilder<Vector3D, P>

{

	private P		parentBuilder;

	private double	x	= 0, y = 0, z = 0;

	public Vector3DBuilder() {
		this(null);
	}

	public Vector3DBuilder(P parentBuilder) {
		this.parentBuilder = parentBuilder;
	}

	public Vector3DBuilder<P> x(double x) {

		this.x = x;
		return this;
	}

	public Vector3DBuilder<P> y(double y) {

		this.y = y;
		return this;
	}

	public Vector3DBuilder<P> z(double z) {

		this.z = z;
		return this;
	}

	public Vector3DBuilder<P> coords(double x, double y, double z) {

		this.x = x;
		this.y = y;
		this.z = z;
		return this;
	}

	public Vector3DBuilder<P> vector(Vector3D vect) {

		this.x = vect.getX();
		this.y = vect.getY();
		this.z = vect.getZ();
		return this;
	}

	@Override
	public Vector3D build() throws BuilderException {

		return new Vector3D(x, y, z);
	}

	@Override
	public P getParentBuilder() {

		return parentBuilder;
	}

}
