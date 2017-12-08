package org.rays3d.geometry.util;

/**
 * Represents a vector of 3 values.
 * 
 * @author snowjak88
 */
public class Triplet extends NVector {

	/**
	 * Create a new Triplet consisting of three 0-values.
	 */
	public Triplet() {
		super(3);
	}

	/**
	 * Create a new Triplet consisting of 3 values.
	 * 
	 * @param v1
	 * @param v2
	 * @param v3
	 */
	public Triplet(double v1, double v2, double v3) {
		super(v1, v2, v3);
	}

}
