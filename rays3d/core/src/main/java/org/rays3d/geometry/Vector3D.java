package org.rays3d.geometry;

import static org.apache.commons.math3.util.FastMath.pow;
import static org.apache.commons.math3.util.FastMath.sqrt;

import java.io.Serializable;
import java.util.Random;

import org.rays3d.geometry.util.Triplet;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Represents a vector in 3-space -- direction plus magnitude.
 * 
 * @author snowjak88
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Vector3D extends Triplet implements Serializable {

	private static final long		serialVersionUID	= -997240497037355891L;

	public static final Vector3D	ZERO				= new Vector3D(0, 0, 0);
	public static final Vector3D	I					= new Vector3D(1, 0, 0);
	public static final Vector3D	J					= new Vector3D(0, 1, 0);
	public static final Vector3D	K					= new Vector3D(0, 0, 1);

	private double					magnitude			= -1d, magnitudeSq = -1d;

	/**
	 * Convert the given {@link Triplet} into a Vector3D.
	 * 
	 * @param t
	 * @return
	 */
	public static Vector3D from(Triplet t) {

		if (Vector3D.class.isAssignableFrom(t.getClass()))
			return (Vector3D) t;

		return new Vector3D(t.get(0), t.get(1), t.get(2));
	}

	public Vector3D(double x, double y, double z) {
		super(x, y, z);
	}

	/**
	 * Create a new Vector3D3D whose coordinates consist of the first 3 values
	 * from the given <code>double[]</code> array. If the given array contains
	 * fewer than 3 values, the array is 0-padded to make up a length-3 array.
	 * 
	 * @param values
	 */
	public Vector3D(double... coordinates) {
		super(coordinates);
	}

	/**
	 * Private constructor for Vector3D, for when we already know magnitude and
	 * magnitude-squared.
	 * 
	 * @param x
	 * @param y
	 * @param z
	 * @param magnitude
	 * @param magnitudeSq
	 */
	private Vector3D(double x, double y, double z, double magnitude, double magnitudeSq) {
		super(x, y, z);
		this.magnitude = magnitude;
		this.magnitudeSq = magnitudeSq;
	}

	/**
	 * Create an empty Vector3D equivalent to {@link Vector3D#ZERO}
	 */
	protected Vector3D() {
		super(3);
	}

	/**
	 * @return the normalized form of this Vector3D
	 */
	public Vector3D normalize() {

		return new Vector3D(getX() / getMagnitude(), getY() / getMagnitude(), getZ() / getMagnitude(), 1.0, 1.0);
	}

	/**
	 * Compute the dot-product of this and another Vector3D.
	 * 
	 * @param other
	 * @return
	 */
	public double dotProduct(Vector3D other) {

		return this.getX() * other.getX() + this.getY() * other.getY() + this.getZ() * other.getZ();
	}

	/**
	 * Compute the (left-handed) cross-product of this and another Vector3D.
	 * 
	 * @param other
	 * @return
	 */
	public Vector3D crossProduct(Vector3D other) {

		return new Vector3D(this.getY() * other.getZ() - this.getZ() * other.getY(),
				this.getZ() * other.getX() - this.getX() * other.getZ(),
				this.getX() * other.getY() - this.getY() * other.getX());
	}

	/**
	 * Create one possible orthogonal Vector to this Vector. The new Vector is
	 * normalized after creation.
	 */
	public Vector3D orthogonal() {

		final Random rnd = new Random(System.currentTimeMillis());
		final double newX, newY, newZ;

		if (this.getZ() == 0d) {

			if (this.getX() == 0d) {

				newX = rnd.nextGaussian();
				newZ = rnd.nextGaussian();
				newY = ( -this.getX() * newX - this.getZ() * newZ ) / this.getY();

			} else {

				newY = rnd.nextGaussian();
				newZ = rnd.nextGaussian();
				newX = ( -this.getY() * newY - this.getZ() * newZ ) / this.getX();
			}

		} else {

			newX = rnd.nextGaussian();
			newY = rnd.nextGaussian();
			newZ = ( -this.getX() * newX - this.getY() * newY ) / this.getZ();
		}

		return new Vector3D(newX, newY, newZ).normalize();
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

	@JsonProperty
	public double getZ() {

		return get(2);
	}

	@JsonProperty
	protected void setZ(double z) {

		getAll()[2] = z;
	}

	@JsonProperty
	public double getMagnitude() {

		if (magnitude < 0d)
			magnitude = sqrt(getMagnitudeSq());
		return magnitude;
	}

	@JsonProperty
	protected void setMagnitude(double magnitude) {

		this.magnitude = magnitude;
	}

	@JsonProperty
	public double getMagnitudeSq() {

		if (magnitudeSq < 0d)
			magnitudeSq = pow(getX(), 2) + pow(getY(), 2) + pow(getZ(), 2);
		return magnitudeSq;
	}

	@JsonProperty
	protected void setMagnitudeSq(double magnitudeSq) {

		this.magnitudeSq = magnitudeSq;
	}

	@Override
	public Vector3D negate() {

		return new Vector3D(-getX(), -getY(), -getZ(), getMagnitude(), getMagnitudeSq());
	}

	@Override
	public Vector3D reciprocal() {

		return Vector3D.from(super.reciprocal());
	}

	@Override
	public Vector3D add(Triplet addend) {

		return Vector3D.from(super.add(addend));
	}

	@Override
	public Vector3D add(double addend) {

		return Vector3D.from(super.add(addend));
	}

	@Override
	public Vector3D subtract(Triplet subtrahend) {

		return Vector3D.from(super.subtract(subtrahend));
	}

	@Override
	public Vector3D subtract(double subtrahend) {

		return Vector3D.from(super.subtract(subtrahend));
	}

	@Override
	public Vector3D multiply(Triplet multiplicand) {

		return Vector3D.from(super.multiply(multiplicand));
	}

	@Override
	public Vector3D multiply(double multiplicand) {

		return Vector3D.from(super.multiply(multiplicand));
	}

	@Override
	public Vector3D divide(Triplet divisor) {

		return Vector3D.from(super.divide(divisor));
	}

	@Override
	public Vector3D divide(double divisor) {

		return Vector3D.from(super.divide(divisor));
	}

}
