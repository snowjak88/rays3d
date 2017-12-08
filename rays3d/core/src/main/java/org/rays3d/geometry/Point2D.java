package org.rays3d.geometry;

import org.rays3d.geometry.util.Pair;

/**
 * Semantic repackaging of {@link Pair}.
 * 
 * @author snowjak88
 */
public class Point2D extends Pair {

	public Point2D() {
	}

	public Point2D(double x, double y) {
		super(x, y);
	}

	public double getX() {

		return get(0);
	}

	public double getY() {

		return get(1);
	}

}
