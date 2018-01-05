package org.rays3d.javabuilder.geometry;

import org.rays3d.geometry.Point3D;
import org.rays3d.javabuilder.AbstractBuilder;
import org.rays3d.javabuilder.BuilderException;

public class Point3DBuilder<P extends AbstractBuilder<?, ?>> implements AbstractBuilder<Point3D, P> {

	private P		parentBuilder;

	private double	x	= 0, y = 0, z = 0;

	public Point3DBuilder() {
		this(null);
	}

	public Point3DBuilder(P parentBuilder) {
		this.parentBuilder = parentBuilder;
	}

	public Point3DBuilder<P> x(double x) {

		this.x = x;
		return this;
	}

	public Point3DBuilder<P> y(double y) {

		this.y = y;
		return this;
	}

	public Point3DBuilder<P> z(double z) {

		this.z = z;
		return this;
	}

	public Point3DBuilder<P> coords(double x, double y, double z) {

		this.x = x;
		this.y = y;
		this.z = z;
		return this;
	}

	public Point3DBuilder<P> point(Point3D point) {

		this.x = point.getX();
		this.y = point.getY();
		this.z = point.getZ();
		return this;
	}

	@Override
	public Point3D build() throws BuilderException {

		return new Point3D(x, y, z);
	}

	@Override
	public P end() {

		return parentBuilder;
	}

}