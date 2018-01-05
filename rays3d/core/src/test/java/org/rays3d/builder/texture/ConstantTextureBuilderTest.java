package org.rays3d.builder.texture;

import static org.junit.Assert.*;

import org.junit.Test;
import org.rays3d.builder.java.spectrum.RGBSpectrumBuilder;
import org.rays3d.builder.java.texture.ConstantTextureBuilder;
import org.rays3d.builder.java.texture.mapping.LinearTextureMappingBuilder;
import org.rays3d.spectrum.RGB;
import org.rays3d.texture.ConstantTexture;
import org.rays3d.texture.mapping.LinearTextureMapping;

public class ConstantTextureBuilderTest {

	@Test
	public void testBuild() {

		//@formatter:off
		final ConstantTexture texture =
				new ConstantTextureBuilder<>()
						.spectrum(new RGBSpectrumBuilder<>().rgb().copy(RGB.GREEN).end())
						.textureMapping(new LinearTextureMappingBuilder<>().from(0, 0).to(1, 1))
						.build();
		//@formatter:on

		assertEquals("Constant RGB (R) is not as expected!", RGB.GREEN.getRed(), texture.getConstant().toRGB().getRed(),
				0.00001);
		assertEquals("Constant RGB (G) is not as expected!", RGB.GREEN.getGreen(),
				texture.getConstant().toRGB().getGreen(), 0.00001);
		assertEquals("Constant RGB (B) is not as expected!", RGB.GREEN.getBlue(),
				texture.getConstant().toRGB().getBlue(), 0.00001);

		assertTrue("Texture mapping is supposed to be an instance of LinearTextureMapping!",
				( texture.getTextureMapping() instanceof LinearTextureMapping ));

		assertEquals("Texture mapping (min-U) is not as expected!", 0d,
				( (LinearTextureMapping) texture.getTextureMapping() ).getTextureMinU(), 0.00001);
		assertEquals("Texture mapping (min-V) is not as expected!", 0d,
				( (LinearTextureMapping) texture.getTextureMapping() ).getTextureMinV(), 0.00001);

		assertEquals("Texture mapping (max-U) is not as expected!", 1d,
				( (LinearTextureMapping) texture.getTextureMapping() ).getTextureMaxU(), 0.00001);
		assertEquals("Texture mapping (max-V) is not as expected!", 1d,
				( (LinearTextureMapping) texture.getTextureMapping() ).getTextureMaxV(), 0.00001);
	}

}
