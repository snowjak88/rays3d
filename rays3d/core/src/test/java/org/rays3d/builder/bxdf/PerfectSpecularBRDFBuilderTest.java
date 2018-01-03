package org.rays3d.builder.bxdf;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.rays3d.builder.spectrum.RGBSpectrumBuilder;
import org.rays3d.builder.texture.ConstantTextureBuilder;
import org.rays3d.bxdf.PerfectSpecularBRDF;
import org.rays3d.spectrum.RGB;
import org.rays3d.texture.ConstantTexture;

public class PerfectSpecularBRDFBuilderTest {

	@Test
	public void testBuild() {

		//@formatter:off
		final PerfectSpecularBRDF brdf =
				new PerfectSpecularBRDFBuilder<>()
						.tint(new ConstantTextureBuilder<>()
								.spectrum(new RGBSpectrumBuilder<>().rgb().copy(RGB.GREEN).end()))
						.build();
		//@formatter:on

		assertNotNull("BRDF doesn't have a surface-texture!", brdf.getTint());

		assertTrue("BRDF's surface-texture is not a ConstantTexture!", brdf.getTint() instanceof ConstantTexture);
		assertEquals("BRDF's surface-texture (R) is not as expected!", RGB.GREEN.getRed(),
				( (ConstantTexture) brdf.getTint() ).getConstant().toRGB().getRed(), 0.00001);
		assertEquals("BRDF's surface-texture (G) is not as expected!", RGB.GREEN.getGreen(),
				( (ConstantTexture) brdf.getTint() ).getConstant().toRGB().getGreen(), 0.00001);
		assertEquals("BRDF's surface-texture (B) is not as expected!", RGB.GREEN.getBlue(),
				( (ConstantTexture) brdf.getTint() ).getConstant().toRGB().getBlue(), 0.00001);
	}

}
