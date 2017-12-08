package org.rays3d.geometry;

import static org.apache.commons.math3.util.FastMath.pow;
import static org.apache.commons.math3.util.FastMath.sqrt;

import java.util.Random;

import org.rays3d.geometry.util.Triplet;

/**
 * Represents a vector in 3-space -- direction plus magnitude.
 * 
 * @author snowjak88
 */
public class Vector3D extends Triplet {

	public static final Vector3D	ZERO		= new Vector3D(0, 0, 0);
	public static final Vector3D	I			= new Vector3D(1, 0, 0);
	public static final Vector3D	J			= new Vector3D(0, 1, 0);
	public static final Vector3D	K			= new Vector3D(0, 0, 1);

	private double					magnitude	= -1d, magnitudeSq = -1d;

	/**
	 * Convert the given {@link Triplet} into a Vector3D.
	 * 
	 * @param t
	 * @return
	 */
	public static Vector3D from(Triplet t) {

		return new Vector3D(t.get(0), t.get(1), t.get(2));
	}

	public Vector3D(double x, double y, double z) {
		super(x, y, z);
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
	 * @return the normalized form of this Vector3D
	 */
	public Vector3D normalize() {

		return new Vector3D(getX() / getMagnitude(), getY() / getMagnitude(), getZ() / getMagnitude(), getMagnitude(),
				getMagnitudeSq());
	}

	@Override
	public Vector3D negate() {

		return new Vector3D(-getX(), -getY(), -getZ(), getMagnitude(), getMagnitudeSq());
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

	public double getX() {

		return this.get(0);
	}

	public double getY() {

		return this.get(1);
	}

	public double getZ() {

		return this.get(2);
	}

	public double getMagnitude() {

		if (magnitude < 0d)
			magnitude = sqrt(getMagnitudeSq());
		return magnitude;
	}

	public double getMagnitudeSq() {

		if (magnitudeSq < 0d)
			magnitudeSq = pow(getX(), 2) + pow(getY(), 2) + pow(getZ(), 2);
		return magnitudeSq;
	}

}