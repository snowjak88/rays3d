package org.rays3d.geometry;

import java.io.Serializable;

import org.rays3d.geometry.util.Pair;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Semantic repackaging of {@link Pair}.
 * 
 * @author snowjak88
 */
public class Point2D extends Pair implements Serializable {

	private static final long serialVersionUID = -7421036355210501663L;

	public static Point2D from(Pair p) {

		if (Point2D.class.isAssignableFrom(p.getClass()))
			return (Point2D) p;

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

	/**
	 * Initialize an empty Point2D.
	 */
	protected Point2D() {
		super();
	}

	@JsonProperty
	public double getX() {

		return get(0);
	}

	@JsonProperty
	protected void setX(double x) {

		getAll()[0] = x;
	}

	@JsonProperty
	public double getY() {

		return get(1);
	}

	@JsonProperty
	protected void setY(double y) {

		getAll()[1] = y;
	}

	@Override
	public Point2D negate() {

		return Point2D.from(super.negate());
	}

	@Override
	public Point2D reciprocal() {

		return Point2D.from(super.reciprocal());
	}

	@Override
	public Point2D add(Pair addend) {

		return Point2D.from(super.add(addend));
	}

	@Override
	public Point2D add(double addend) {

		return Point2D.from(super.add(addend));
	}

	@Override
	public Point2D subtract(Pair subtrahend) {

		return Point2D.from(super.subtract(subtrahend));
	}

	@Override
	public Point2D subtract(double subtrahend) {

		return Point2D.from(super.subtract(subtrahend));
	}

	@Override
	public Point2D multiply(Pair multiplicand) {

		return Point2D.from(super.multiply(multiplicand));
	}

	@Override
	public Point2D multiply(double multiplicand) {

		return Point2D.from(super.multiply(multiplicand));
	}

	@Override
	public Point2D divide(Pair divisor) {

		return Point2D.from(super.divide(divisor));
	}

	@Override
	public Point2D divide(double divisor) {

		return Point2D.from(super.divide(divisor));
	}

}
