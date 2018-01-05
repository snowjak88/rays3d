package org.rays3d.javabuilder.geometry;

import org.rays3d.geometry.Point2D;
import org.rays3d.javabuilder.AbstractBuilder;
import org.rays3d.javabuilder.BuilderException;

public class Point2DBuilder<P extends AbstractBuilder<?, ?>> implements AbstractBuilder<Point2D, P> {

	private P		parentBuilder;

	private double	x	= 0, y = 0;

	public Point2DBuilder() {
		this(null);
	}

	public Point2DBuilder(P parentBuilder) {
		this.parentBuilder = parentBuilder;
	}

	public Point2DBuilder<P> x(double x) {

		this.x = x;
		return this;
	}

	public Point2DBuilder<P> y(double y) {

		this.y = y;
		return this;
	}

	public Point2DBuilder<P> coords(double x, double y) {

		this.x = x;
		this.y = y;
		return this;
	}

	public Point2DBuilder<P> point(Point2D point) {

		this.x = point.getX();
		this.y = point.getY();
		return this;
	}

	@Override
	public Point2D build() throws BuilderException {

		return new Point2D(x, y);
	}

	@Override
	public P end() {

		return parentBuilder;
	}

}
