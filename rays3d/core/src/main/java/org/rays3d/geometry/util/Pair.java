package org.rays3d.geometry.util;

/**
 * Represents a vector of 2 values.
 * 
 * @author snowjak88
 */
public class Pair extends NVector {

	/**
	 * Create a new Pair consisting of two 0-values.
	 */
	public Pair() {
		super(2);
	}

	/**
	 * Create a new Pair consisting of 2 values.
	 * 
	 * @param v1
	 * @param v2
	 */
	public Pair(double v1, double v2) {
		super(v1, v2);
	}

}
