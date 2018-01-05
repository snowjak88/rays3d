package org.rays3d.builder.bxdf;

import static org.apache.commons.math3.util.FastMath.PI;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.rays3d.bxdf.LambertianBRDF;
import org.rays3d.javabuilder.bxdf.LambertianBRDFBuilder;
import org.rays3d.javabuilder.spectrum.RGBSpectrumBuilder;
import org.rays3d.javabuilder.texture.ConstantTextureBuilder;
import org.rays3d.spectrum.RGB;
import org.rays3d.texture.ConstantTexture;

public class LambertianBRDFBuilderTest {

	@Test
	public void testBuild() {

		//@formatter:off
		final LambertianBRDF brdf =
				new LambertianBRDFBuilder<>()
						.texture(new ConstantTextureBuilder<>()
								.spectrum(new RGBSpectrumBuilder<>().rgb().copy(RGB.GREEN).end()))
						.emissive(new RGBSpectrumBuilder<>().rgb().copy(RGB.WHITE).end())
						.build();
		//@formatter:on

		assertNotNull("BRDF doesn't have a surface-texture!", brdf.getTexture());
		assertNotNull("BRDF doesn't have an emissive-texture!", brdf.getEmissive());
		assertNotNull("BRDF doesn't have an emissive-power!", brdf.getTotalEmissivePower());

		assertTrue("BRDF's surface-texture is not a ConstantTexture!", brdf.getTexture() instanceof ConstantTexture);
		assertEquals("BRDF's surface-texture (R) is not as expected!", RGB.GREEN.getRed(),
				( (ConstantTexture) brdf.getTexture() ).getConstant().toRGB().getRed(), 0.00001);
		assertEquals("BRDF's surface-texture (G) is not as expected!", RGB.GREEN.getGreen(),
				( (ConstantTexture) brdf.getTexture() ).getConstant().toRGB().getGreen(), 0.00001);
		assertEquals("BRDF's surface-texture (B) is not as expected!", RGB.GREEN.getBlue(),
				( (ConstantTexture) brdf.getTexture() ).getConstant().toRGB().getBlue(), 0.00001);

		assertTrue("BRDF's emissive-texture is not a ConstantTexture!", brdf.getEmissive() instanceof ConstantTexture);
		assertEquals("BRDF's emissive-texture (R) is not as expected!", RGB.WHITE.getRed(),
				( (ConstantTexture) brdf.getEmissive() ).getConstant().toRGB().getRed(), 0.00001);
		assertEquals("BRDF's emissive-texture (G) is not as expected!", RGB.WHITE.getGreen(),
				( (ConstantTexture) brdf.getEmissive() ).getConstant().toRGB().getGreen(), 0.00001);
		assertEquals("BRDF's emissive-texture (B) is not as expected!", RGB.WHITE.getBlue(),
				( (ConstantTexture) brdf.getEmissive() ).getConstant().toRGB().getBlue(), 0.00001);

		assertEquals("BRDF's emissive-power (R) is not as expected!", RGB.WHITE.getRed() * 4d * PI,
				brdf.getTotalEmissivePower().toRGB().getRed(), 0.00001);
		assertEquals("BRDF's emissive-power (G) is not as expected!", RGB.WHITE.getGreen() * 4d * PI,
				brdf.getTotalEmissivePower().toRGB().getGreen(), 0.00001);
		assertEquals("BRDF's emissive-power (B) is not as expected!", RGB.WHITE.getBlue() * 4d * PI,
				brdf.getTotalEmissivePower().toRGB().getBlue(), 0.00001);
	}

}
