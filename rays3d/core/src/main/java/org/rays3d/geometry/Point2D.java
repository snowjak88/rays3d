package org.rays3d.geometry;

import org.rays3d.geometry.util.Pair;

/**
 * Semantic repackaging of {@link Pair}.
 * 
 * @author snowjak88
 */
public class Point2D extends Pair {

	public static Point2D from(Pair p) {

		return new Point2D(p.get(0), p.get(1));
	}

	public Point2D(double x, double y) {
		super(x, y);
	}

	/**
	 * Create a new Point2D whose coordinates consist of the first 2 values from
	 * the given <code>double[]</code> array. If the given array contains fewer
	 * than 2 values, the array is 0-padded to make up a length-2 array.
	 * 
	 * @param values
	 */
	public Point2D(double... coordinates) {
		super(coordinates);
	}

	public double getX() {

		return get(0);
	}

	public double getY() {

		return get(1);
	}

}
