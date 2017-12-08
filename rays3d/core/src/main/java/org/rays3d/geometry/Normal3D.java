package org.rays3d.geometry;

import org.rays3d.geometry.util.Triplet;

/**
 * A Normal3D is distinct from a {@link Vector3D} in that a Normal3D does not
 * inherently represent magnitude, only direction.
 * 
 * @author snowjak88
 */
public class Normal3D extends Triplet {

	public static Normal3D from(Triplet t) {

		return new Normal3D(t.get(0), t.get(1), t.get(2));
	}

	public Normal3D(double x, double y, double z) {
		super(x, y, z);
	}

	/**
	 * Create a new Normal3D whose coordinates consist of the first 3 values
	 * from the given <code>double[]</code> array. If the given array contains
	 * fewer than 3 values, the array is 0-padded to make up a length-3 array.
	 * 
	 * @param values
	 */
	public Normal3D(double... coordinates) {
		super(coordinates);
	}

	public double getX() {

		return get(0);
	}

	public double getY() {

		return get(1);
	}

	public double getZ() {

		return get(2);
	}

}
