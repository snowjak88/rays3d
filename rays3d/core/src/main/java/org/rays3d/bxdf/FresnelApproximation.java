package org.rays3d.bxdf;

import static org.apache.commons.math3.util.FastMath.*;

import org.rays3d.Global;
import org.rays3d.geometry.Normal3D;
import org.rays3d.geometry.Vector3D;

/**
 * Calculates Schlick's approximation to Fresnel's equations for reflection and
 * transmittance.
 * 
 * @author snowjak88
 */
public class FresnelApproximation {

	private final Vector3D	w_e;
	private final Normal3D	n;
	private final double	n1, n2;

	private Double			cos_i						= null, sin2_t = null, cos_t = null;

	private Double			reflectance					= null;
	private Vector3D		reflectedDirection			= null, transmittedDirection = null;

	private Boolean			isTotalInternalReflection	= null;

	public FresnelApproximation(Vector3D w_e, Normal3D n, double leavingIndexOfRefraction,
			double enteringIndexOfRefraction) {

		//
		// Source:
		// http://graphics.stanford.edu/courses/cs148-10-summer/docs/2006--degreve--reflection_refraction.pdf
		//
		this.w_e = w_e;
		this.n = n;
		this.n1 = leavingIndexOfRefraction;
		this.n2 = enteringIndexOfRefraction;
	}

	/**
	 * For a given eye-vector and surface-normal, compute the resulting
	 * reflected vector (assuming perfect specular reflection).
	 * 
	 * @param w_e
	 * @param n
	 * @return
	 */
	public Vector3D getReflectedDirection() {

		if (reflectedDirection != null)
			return reflectedDirection;

		final Vector3D nv = Vector3D.from(n).normalize();
		final Vector3D i = w_e.normalize();

		final double cos_i = nv.dotProduct(i);
		reflectedDirection = i.negate().add(nv.multiply(2d * cos_i)).normalize();

		return reflectedDirection;
	}

	/**
	 * For a given eye-vector, surface-normal, and pair of
	 * indices-of-refraction, compute the resulting transmitted vector (or
	 * <code>null</code> if this is a case of Total Internal Reflection).
	 * 
	 * @return
	 */
	public Vector3D getTransmittedDirection() {

		if (transmittedDirection != null)
			return transmittedDirection;

		final Vector3D nv = Vector3D.from(n).normalize();
		final Vector3D i = w_e.normalize();

		final double n0 = n1 / n2;
		final double cos_i = nv.dotProduct(i);
		final double sin2_t = n0 * n0 * ( 1d - cos_i * cos_i );

		if (sin2_t >= 1d) {
			// This is a case of Total Internal Reflection -- so nothing is
			// transmitted!
			this.isTotalInternalReflection = true;
			return null;
		} else
			this.isTotalInternalReflection = false;

		final double cos_t = sqrt(1d - sin2_t);
		transmittedDirection = i.negate().multiply(n0).add(nv.multiply(n0 * cos_i - cos_t)).normalize();

		return transmittedDirection;
	}

	public double getReflectance() {

		if (reflectance != null)
			return reflectance;

		// final Vector3D nv = Vector3D.from(n).normalize();
		// final Vector3D i = w_e.normalize();

		if (sin2_t() > 1d) {
			// This is a case of Total Internal Reflection.
			this.isTotalInternalReflection = true;
			this.reflectance = 1d;
			return reflectance;
		} else
			this.isTotalInternalReflection = false;

		final double r_pPolarized = pow(( n1 * cos_i() - n2 * cos_t() ) / ( n1 * cos_i() + n2 * cos_t() ), 2);
		final double r_sPolarized = pow(( n2 * cos_i() - n1 * cos_t() ) / ( n2 * cos_i() + n1 * cos_t() ), 2);

		reflectance = ( r_pPolarized + r_sPolarized ) / 2d;

		// final double r0_rth = ( n1 * cos_i - n2 * cos_t ) / ( n1 * cos_i + n2
		// * cos_t );
		// final double r_par = ( n2 * cos_i - n1 * cos_t ) / ( n2 * cos_i + n1
		// * cos_t );
		// reflectance = min(max(( ( r0_rth * r0_rth + r_par * r_par ) / 2d ),
		// 0d), 1d);

		return reflectance;
	}

	public double getTransmittance() {

		return 1d - getReflectance();
	}

	public boolean isTotalInternalReflection() {

		if (isTotalInternalReflection != null)
			return isTotalInternalReflection;

		if (sin2_t() > 1d) {
			// This is a case of Total Internal Reflection.
			isTotalInternalReflection = true;
		} else
			isTotalInternalReflection = false;

		return isTotalInternalReflection;
	}

	/**
	 * Calculate (and cache) the cosine of the angle between the incident- and
	 * normal-vector.
	 * 
	 * @return
	 */
	private double cos_i() {

		if (this.cos_i != null)
			return this.cos_i;

		this.cos_i = w_e.normalize().dotProduct(Vector3D.from(n).normalize());
		return this.cos_i;
	}

	/**
	 * Calculate (and cache) the square of the sine of the angle between the
	 * transmitted- and normal-vector.
	 * 
	 * @return
	 */
	private double sin2_t() {

		if (this.sin2_t != null)
			return this.sin2_t;

		final double n0 = n1 / n2;
		this.sin2_t = ( n0 * n0 ) * ( 1 - cos_i() * cos_i() );
		return this.sin2_t;
	}

	/**
	 * Calculate (and cache) the cosine of the angle between the transmitted-
	 * and normal-vector.
	 * 
	 * @return
	 */
	private double cos_t() {

		if (this.cos_t != null)
			return this.cos_t;

		this.cos_t = sqrt(1 - sin2_t());
		return this.cos_t;
	}

	@Override
	public int hashCode() {

		final int prime = 31;
		int result = 1;
		result = prime * result + ( ( n == null ) ? 0 : n.hashCode() );
		long temp;
		temp = Double.doubleToLongBits(n1);
		result = prime * result + (int) ( temp ^ ( temp >>> 32 ) );
		temp = Double.doubleToLongBits(n2);
		result = prime * result + (int) ( temp ^ ( temp >>> 32 ) );
		result = prime * result + ( ( w_e == null ) ? 0 : w_e.hashCode() );
		return result;
	}

	@Override
	public boolean equals(Object obj) {

		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		FresnelApproximation other = (FresnelApproximation) obj;
		if (n == null) {
			if (other.n != null)
				return false;
		} else if (!n.equals(other.n))
			return false;
		if (!Global.isNear(n1, other.n1))
			return false;
		if (!Global.isNear(n2, other.n2))
			return false;
		if (w_e == null) {
			if (other.w_e != null)
				return false;
		} else if (!w_e.equals(other.w_e))
			return false;
		return true;
	}

}