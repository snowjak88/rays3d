package org.rays3d.geometry;

import org.rays3d.geometry.util.Triplet;

/**
 * Semantic repackaging of {@link Triplet}.
 * 
 * @author snowjak88
 */
public class Point3D extends Triplet {

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
