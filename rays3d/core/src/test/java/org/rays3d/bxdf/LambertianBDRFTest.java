package org.rays3d.bxdf;

import static org.apache.commons.math3.util.FastMath.PI;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;
import org.rays3d.Primitive;
import org.rays3d.geometry.Normal3D;
import org.rays3d.geometry.Point2D;
import org.rays3d.geometry.Point3D;
import org.rays3d.geometry.Ray;
import org.rays3d.geometry.Vector3D;
import org.rays3d.interact.Interaction;
import org.rays3d.message.sample.Sample;
import org.rays3d.shape.SphereShape;
import org.rays3d.spectrum.RGB;
import org.rays3d.spectrum.RGBSpectrum;
import org.rays3d.spectrum.Spectrum;
import org.rays3d.texture.ConstantTexture;
import org.rays3d.texture.Texture;

public class LambertianBDRFTest {

	private LambertianBRDF			bdrf;
	private Primitive				primitive;
	private Interaction<Primitive>	interaction;
	private Sample					sample;

	@Before
	public void setUp() throws Exception {

		final Texture texture = new ConstantTexture(new RGBSpectrum(RGB.RED));
		final Spectrum emissive = RGBSpectrum.BLACK;
		this.bdrf = new LambertianBRDF(texture, emissive);

		sample = new Sample(1, 1, new Point2D(8.5, 8.5), new Point2D(0.5, 0.5), null, null);
		sample.setAdditional1DSamples(Arrays.asList(0.5));
		sample.setAdditional2DSamples(Arrays.asList(new Point2D(0.5, 0.5)));

		primitive = new Primitive(new SphereShape(1.0), bdrf);

		interaction = new Interaction<Primitive>(primitive,
				new Ray(new Point3D(-1, 2, 0), new Vector3D(1, -1, 0).normalize()), new Point3D(0, 1, 0),
				Normal3D.from(Vector3D.J), new Point2D(0.5, 0.5));
	}

	@Test
	public void testF_r() {

		final Spectrum reflected = bdrf.f_r(interaction, sample, Vector3D.J);

		assertEquals("Reflected-Red was not as expected!", 1d, reflected.toRGB().getRed(), 0.00001);
		assertEquals("Reflected-Green was not as expected!", 0d, reflected.toRGB().getGreen(), 0.00001);
		assertEquals("Reflected-Blue was not as expected!", 0d, reflected.toRGB().getBlue(), 0.00001);
	}

	@Test
	public void testsampleL_e() {

		Spectrum emissive = bdrf.sampleL_e(interaction, sample);

		assertEquals("Emissive-Red was not as expected!", 0d, emissive.toRGB().getRed(), 0.00001);
		assertEquals("Emissive-Green was not as expected!", 0d, emissive.toRGB().getGreen(), 0.00001);
		assertEquals("Emissive-Blue was not as expected!", 0d, emissive.toRGB().getBlue(), 0.00001);
	}

	@Test
	public void testSampleW_i() {

		for (int i = 0; i < 32; i++) {

			final Vector3D sampledReflection = bdrf.sampleW_i(interaction, sample);
			final double dotProduct = sampledReflection
					.normalize()
						.dotProduct(Vector3D.from(interaction.getNormal()).normalize());

			assertTrue("Sampled reflection vector is not as expected!", ( dotProduct >= 0d ) && ( dotProduct <= 1d ));
		}
	}

	@Test
	public void testPdfW_i() {

		assertEquals("PDF of w_i should always be 1 / (2 * pi)", 1d / ( 2d * PI ),
				bdrf.pdfW_i(interaction, sample, null), 0.00001);
	}

}
