package org.rays3d.bxdf;

import java.util.Arrays;
import java.util.HashSet;

import org.rays3d.Global;
import org.rays3d.geometry.Vector3D;
import org.rays3d.interact.Interactable;
import org.rays3d.interact.Interaction;
import org.rays3d.message.sample.Sample;
import org.rays3d.spectrum.RGBSpectrum;
import org.rays3d.spectrum.Spectrum;
import org.rays3d.texture.ConstantTexture;
import org.rays3d.texture.Texture;

/**
 * Defines a "perfect mirror" {@link BSDF}.
 * 
 * @author snowjak88
 */
public class PerfectSpecularBRDF extends BSDF {

	private final Texture tint;

	/**
	 * Create a new PerfectSpecularBRDF with no tinting applied to reflected
	 * light.
	 */
	public PerfectSpecularBRDF() {
		this(new ConstantTexture(RGBSpectrum.WHITE));
	}

	/**
	 * Create a new PerfectSpecularBRDF, specifying a {@link Texture} for
	 * reflection-tinting.
	 * 
	 * @param tint
	 *            {@link Texture} defining this mirror's "reflectivity-fraction"
	 *            at various wavelengths
	 */
	public PerfectSpecularBRDF(Texture tint) {
		super(new HashSet<>(Arrays.asList(Property.REFLECT_SPECULAR)));

		this.tint = tint;
	}

	@Override
	public <T extends Interactable> Spectrum sampleL_e(Interaction<T> interaction, Sample sample) {

		return RGBSpectrum.BLACK;
	}

	@Override
	public <T extends Interactable> Vector3D sampleW_i(Interaction<T> interaction, Sample sample) {

		//
		// A perfect mirror reflects only perfectly. No other directions are
		// possible.
		//
		return BSDF.getPerfectSpecularReflectionVector(interaction.getW_e(), interaction.getNormal());
	}

	@Override
	public <T extends Interactable> double pdfW_i(Interaction<T> interaction, Sample sample, Vector3D w_i) {

		//
		// A perfect mirror will only every reflect perfectly. Therefore, all
		// directions that are not perfect reflections are of probability 0.
		//
		return ( isPerfectReflection(interaction, sample, w_i) ) ? 1d : 0d;
	}

	@Override
	public <T extends Interactable> Spectrum f_r(Interaction<T> interaction, Sample sample, Vector3D w_i) {

		//
		//
		if (isPerfectReflection(interaction, sample, w_i))
			return tint.evaluate(interaction);
		else
			return RGBSpectrum.BLACK;
	}

	private <T extends Interactable> boolean isPerfectReflection(Interaction<T> interaction, Sample sample,
			Vector3D w_i) {

		final Vector3D perfectReflection = sampleW_i(interaction, sample).normalize();
		final Vector3D givenReflection = w_i.normalize();

		return Global.isNear(perfectReflection.getX(), givenReflection.getX())
				&& Global.isNear(perfectReflection.getY(), givenReflection.getY())
				&& Global.isNear(perfectReflection.getZ(), givenReflection.getZ());
	}

	@Override
	public boolean isEmissive() {

		return false;
	}

	@Override
	public Spectrum getTotalEmissivePower() {

		return RGBSpectrum.BLACK;
	}

	public Texture getTint() {

		return tint;
	}

}
