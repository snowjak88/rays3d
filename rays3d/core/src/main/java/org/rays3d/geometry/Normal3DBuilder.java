package org.rays3d.geometry;

import org.rays3d.builder.AbstractBuilder;
import org.rays3d.builder.BuilderException;

public class Normal3DBuilder<P extends AbstractBuilder<?, ?>> implements AbstractBuilder<Normal3D, P> {

	private P		parentBuilder;

	private double	x	= 0, y = 0, z = 0;

	public Normal3DBuilder() {
		this(null);
	}

	public Normal3DBuilder(P parentBuilder) {
		this.parentBuilder = parentBuilder;
	}

	public Normal3DBuilder<P> x(double x) {

		this.x = x;
		return this;
	}

	public Normal3DBuilder<P> y(double y) {

		this.y = y;
		return this;
	}

	public Normal3DBuilder<P> z(double z) {

		this.z = z;
		return this;
	}

	public Normal3DBuilder<P> coords(double x, double y, double z) {

		this.x = x;
		this.y = y;
		this.z = z;
		return this;
	}

	public Normal3DBuilder<P> normal(Normal3D normal) {

		this.x = normal.getX();
		this.y = normal.getY();
		this.z = normal.getZ();
		return this;
	}

	@Override
	public Normal3D build() throws BuilderException {

		return new Normal3D(x, y, z);
	}

	@Override
	public P getParentBuilder() {

		return parentBuilder;
	}

}
