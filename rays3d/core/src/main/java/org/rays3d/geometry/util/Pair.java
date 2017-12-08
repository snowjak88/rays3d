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

	@Override
	public Pair negate() {

		return (Pair) super.negate();
	}

	@Override
	public Pair reciprocal() {

		return (Pair) super.reciprocal();
	}

	public Pair add(Pair addend) {

		return (Pair) super.add(addend);
	}

	@Override
	public Pair add(double addend) {

		return (Pair) super.add(addend);
	}

	public Pair subtract(Pair subtrahend) {

		return (Pair) super.subtract(subtrahend);
	}

	@Override
	public Pair subtract(double subtrahend) {

		return (Pair) super.subtract(subtrahend);
	}

	public Pair multiply(Pair multiplicand) {

		return (Pair) super.multiply(multiplicand);
	}

	@Override
	public Pair multiply(double multiplicand) {

		return (Pair) super.multiply(multiplicand);
	}

	public Pair divide(Pair divisor) {

		return (Pair) super.divide(divisor);
	}

	@Override
	public Pair divide(double divisor) {

		return (Pair) super.divide(divisor);
	}

}
