package org.rays3d.geometry.util;

import java.util.Arrays;

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

	/**
	 * Create a new Triplet consisting of the first 2 values from the given
	 * <code>double[]</code> array. If the given array contains fewer than 2
	 * values, the array is 0-padded to make up a length-2 array.
	 * 
	 * @param values
	 */
	public Triplet(double... values) {
		super(Arrays.copyOf(values, 3));
	}

	@Override
	public Triplet negate() {

		return (Triplet) super.negate();
	}

	@Override
	public Triplet reciprocal() {

		return (Triplet) super.reciprocal();
	}

	public Triplet add(Triplet addend) {

		return (Triplet) super.add(addend);
	}

	@Override
	public Triplet add(double addend) {

		return (Triplet) super.add(addend);
	}

	public Triplet subtract(Triplet subtrahend) {

		return (Triplet) super.subtract(subtrahend);
	}

	@Override
	public Triplet subtract(double subtrahend) {

		return (Triplet) super.subtract(subtrahend);
	}

	public Triplet multiply(Triplet multiplicand) {

		return (Triplet) super.multiply(multiplicand);
	}

	@Override
	public Triplet multiply(double multiplicand) {

		return (Triplet) super.multiply(multiplicand);
	}

	public Triplet divide(Triplet divisor) {

		return (Triplet) super.divide(divisor);
	}

	@Override
	public Triplet divide(double divisor) {

		return (Triplet) super.divide(divisor);
	}

}
