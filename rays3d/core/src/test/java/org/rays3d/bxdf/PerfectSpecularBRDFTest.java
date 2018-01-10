package org.rays3d.bxdf;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;

import org.junit.Test;
import org.rays3d.Primitive;
import org.rays3d.geometry.Normal3D;
import org.rays3d.geometry.Point2D;
import org.rays3d.geometry.Point3D;
import org.rays3d.geometry.Ray;
import org.rays3d.geometry.Vector3D;
import org.rays3d.interact.Interactable;
import org.rays3d.interact.Interaction;
import org.rays3d.message.sample.Sample;
import org.rays3d.spectrum.RGB;
import org.rays3d.spectrum.RGBSpectrum;
import org.rays3d.spectrum.Spectrum;
import org.rays3d.texture.ConstantTexture;

public class PerfectSpecularBRDFTest {

	@Test
	public void testSampleW_i() {

		final BSDF bsdf = new PerfectSpecularBRDF();

		final Interaction<Interactable> interaction = new Interaction<Interactable>(null,
				new Ray(new Point3D(-1, 1, 0), new Vector3D(1, -1, 0)), new Point3D(0, 0, 0), Normal3D.from(Vector3D.J),
				new Point2D(1, 1));

		final Sample sample = new Sample(0, 1, new Point2D(0, 0), new Point2D(0.5, 0.5),
				new Ray(new Point3D(), new Vector3D()), new RGBSpectrum());
		sample.setAdditional1DSamples(Arrays.asList(0.5));
		sample.setAdditional2DSamples(Arrays.asList(new Point2D(0.5, 0.5)));

		final Vector3D w_i = bsdf.sampleW_i(interaction, sample).normalize();
		final Vector3D expected = BSDF
				.getPerfectSpecularReflectionVector(interaction.getW_e(), interaction.getNormal())
					.normalize();

		assertEquals("Reflection-X not as expected!", expected.getX(), w_i.getX(), 0.00001);
		assertEquals("Reflection-Y not as expected!", expected.getY(), w_i.getY(), 0.00001);
		assertEquals("Reflection-Z not as expected!", expected.getZ(), w_i.getZ(), 0.00001);
	}

	@Test
	public void testPdfW_i() {

		final BSDF bsdf = new PerfectSpecularBRDF();
		final Interaction<Interactable> interaction = new Interaction<Interactable>(null,
				new Ray(new Point3D(-1, 1, 0), new Vector3D(1, -1, 0)), new Point3D(0, 0, 0), Normal3D.from(Vector3D.J),
				new Point2D(1, 1));
		final Sample sample = new Sample();

		final Vector3D w_i_perfect = new Vector3D(1, 1, 0).normalize();
		final Vector3D w_i_imperfect = new Vector3D(1, 0.333, 0).normalize();

		assertEquals("Perfect reflection should have PDF = 1!", 1.0, bsdf.pdfW_i(interaction, sample, w_i_perfect),
				0.00001);
		assertEquals("Imperfect reflection should have PDF = 0!", 0.0, bsdf.pdfW_i(interaction, sample, w_i_imperfect),
				0.00001);
	}

	@Test
	public void testF_r() {

		final BSDF bsdf = new PerfectSpecularBRDF(new ConstantTexture(new RGBSpectrum(RGB.RED)));
		final Interaction<Primitive> interaction = new Interaction<Primitive>(null,
				new Ray(new Point3D(-1, 1, 0), new Vector3D(1, -1, 0)), new Point3D(0, 0, 0), Normal3D.from(Vector3D.J),
				new Point2D(1, 1));
		final Sample sample = new Sample(1, 1, new Point2D(8, 8), new Point2D(0.5, 0.5), null, null);

		final Vector3D w_i_perfect = new Vector3D(1, 1, 0).normalize();
		final Vector3D w_i_imperfect = new Vector3D(1, 0.333, 0).normalize();

		final Spectrum f_r_perfect = bsdf.f_r(interaction, sample, w_i_perfect);
		final Spectrum f_r_imperfect = bsdf.f_r(interaction, sample, w_i_imperfect);

		assertEquals("f_r for perfect reflection (RED) not as expected!", 1.0, f_r_perfect.toRGB().getRed(), 0.00001);
		assertEquals("f_r for perfect reflection (GREEN) not as expected!", 0.0, f_r_perfect.toRGB().getGreen(),
				0.00001);
		assertEquals("f_r for perfect reflection (BLUE) not as expected!", 0.0, f_r_perfect.toRGB().getBlue(), 0.00001);

		assertEquals("f_r for imperfect reflection (RED) not as expected!", 0.0, f_r_imperfect.toRGB().getRed(),
				0.00001);
		assertEquals("f_r for imperfect reflection (GREEN) not as expected!", 0.0, f_r_imperfect.toRGB().getGreen(),
				0.00001);
		assertEquals("f_r for imperfect reflection (BLUE) not as expected!", 0.0, f_r_imperfect.toRGB().getBlue(),
				0.00001);
	}

}
