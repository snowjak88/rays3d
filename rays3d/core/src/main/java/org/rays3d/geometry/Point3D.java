package org.rays3d.geometry;

import org.rays3d.geometry.util.Triplet;

/**
 * Semantic repackaging of {@link Triplet}.
 * 
 * @author snowjak88
 */
public class Point3D extends Triplet {

	public final static Point3D ZERO = new Point3D(0, 0, 0);

	/**
	 * Convert the given {@link Triplet} into a Point3D.
	 */
	public static Point3D from(Triplet t) {

		return new Point3D(t.get(0), t.get(1), t.get(2));
	}

	public Point3D() {
		super();
	}

	public Point3D(double x, double y, double z) {
		super(x, y, z);
	}

	/**
	 * Create a new Point3D whose coordinates consist of the first 3 values from
	 * the given <code>double[]</code> array. If the given array contains fewer
	 * than 3 values, the array is 0-padded to make up a length-3 array.
	 * 
	 * @param values
	 */
	public Point3D(double... coordinates) {
		super(coordinates);
	}

	public double getX() {

		return this.get(0);
	}

	public double getY() {

		return this.get(1);
	}

	public double getZ() {

		return this.get(2);
	}

}
