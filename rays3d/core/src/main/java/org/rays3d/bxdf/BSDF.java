package org.rays3d.bxdf;

import java.util.HashSet;
import java.util.Set;

import org.apache.commons.math3.ode.AbstractIntegrator;
import org.rays3d.geometry.Normal3D;
import org.rays3d.geometry.Vector3D;
import org.rays3d.interact.Interactable;
import org.rays3d.interact.Interaction;
import org.rays3d.message.sample.Sample;
import org.rays3d.spectrum.Spectrum;

/**
 * Represents a Bi-Directional Scattering Function.
 * 
 * @author snowjak88
 */
public abstract class BSDF {

	private final Set<Property> properties;

	/**
	 * Create a new {@link BSDF} with the given list of {@link Property}s.
	 * 
	 * @param properties
	 */
	public BSDF(Set<Property> properties) {

		this.properties = new HashSet<>(properties);
	}

	/**
	 * @return <code>true</code> if this {@link BSDF} is configured with the
	 *         given {@link Property}
	 */
	public boolean hasProperty(Property property) {

		return this.properties.contains(property);
	}

	/**
	 * Sample the radiant energy emitted from the given interaction.
	 * 
	 * @param interaction
	 * @param sample
	 * @return
	 */
	public abstract <T extends Interactable> Spectrum sampleL_e(Interaction<T> interaction, Sample sample);

	/**
	 * Sample an incident vector from the given interaction.
	 * 
	 * @param interaction
	 * @param sample
	 * @return
	 */
	public abstract <T extends Interactable> Vector3D sampleW_i(Interaction<T> interaction, Sample sample);

	/**
	 * Given an inbound vector <code>w<sub>i</sub></code>, what is the
	 * probability that this BSDF would have chosen that vector?
	 * 
	 * @param interaction
	 * @param sample
	 * @param w_i
	 * @return
	 */
	public abstract <T extends Interactable> double pdfW_i(Interaction<T> interaction, Sample sample, Vector3D w_i);

	/**
	 * Compute the fraction of energy (for each wavelength) that's reflected
	 * from the given vector <code>w<sub>i</sub></code>.
	 * 
	 * @param interaction
	 * @param sample
	 * @param w_i
	 * @return
	 */
	public abstract <T extends Interactable> Spectrum f_r(Interaction<T> interaction, Sample sample, Vector3D w_i);

	/**
	 * Compute the cosine term for each BSDF interaction -- i.e., the
	 * dot-product of the outbound direction <code>w<sub>i</sub></code> and the
	 * surface-normal <code>n</code>
	 * 
	 * @param interaction
	 * @param w_i
	 * @return
	 */
	public <T extends Interactable> double cos_i(Interaction<T> interaction, Vector3D w_i) {

		return Vector3D.from(interaction.getNormal()).normalize().dotProduct(w_i.normalize());
	}

	/**
	 * @return <code>true</code> if this BSDF can emit radiance on its own
	 */
	public abstract boolean isEmissive();

	/**
	 * @return the total emissive power that this BSDF can emit, in all possible
	 *         directions (or <code>0</code> if this BSDF does not emit)
	 */
	public abstract Spectrum getTotalEmissivePower();

	/**
	 * Enumeration describing the various properties a {@link BSDF} instance can
	 * have (and which will affect how the active {@link AbstractIntegrator}
	 * will treat it).
	 * 
	 * @author snowjak88
	 */
	public enum Property {
		/**
		 * Indicates that the given {@link BSDF} should be sampled for specular
		 * reflection.
		 */
		REFLECT_SPECULAR,
		/**
		 * Indicates that the given {@link BSDF} should be sampled for diffuse
		 * reflection.
		 */
		REFLECT_DIFFUSE,
		/**
		 * Indicates that the given {@link BSDF} should be sampled for
		 * transmittance.
		 */
		TRANSMIT,
		/**
		 * Indicates that the given {@link BSDF} behaves like a dialetric
		 * material -- i.e., specularly-reflected light is tinted
		 */
		DIALECTRIC,
		/**
		 * Indicates that the given {@link BSDF} should be sampled for glossy
		 * reflection, when sampling for specular reflection
		 */
		GLOSSY
	}

	public enum ReflectType {
		/**
		 * Indicates specular reflection
		 */
		SPECULAR,
		/**
		 * Indicates diffuse reflection
		 */
		DIFFUSE
	}

	/**
	 * Given an surface-intersection point <code><strong>x</strong></code>, a
	 * Vector from <code>x</code> toward the eye-point
	 * <code><strong>w</strong><sub>e</sub></code>, and a surface-normal
	 * <code><strong>n</strong></code>, construct a Vector giving the direction
	 * of perfect specular reflection.
	 * 
	 * @param w_e
	 * @param n
	 * @return
	 */
	public static Vector3D getPerfectSpecularReflectionVector(Vector3D w_e, Normal3D n) {

		final Vector3D i = w_e.normalize();
		final Vector3D nv = Vector3D.from(n).normalize();

		final double cos_i = nv.dotProduct(i);
		return i.negate().add(nv.multiply(2d * cos_i)).normalize();
	}

	@Override
	public int hashCode() {

		final int prime = 31;
		int result = 1;
		result = prime * result + ( ( properties == null ) ? 0 : properties.hashCode() );
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
		BSDF other = (BSDF) obj;
		if (properties == null) {
			if (other.properties != null)
				return false;
		} else if (!properties.equals(other.properties))
			return false;
		return true;
	}

}
