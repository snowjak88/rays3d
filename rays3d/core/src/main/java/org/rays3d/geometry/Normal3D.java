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
