package org.rays3d.texture;

import static org.junit.Assert.*;

import org.junit.Test;
import org.rays3d.spectrum.RGB;
import org.rays3d.spectrum.RGBSpectrumBuilder;

public class CheckerboardTextureBuilderTest {

	@Test
	public void testBuild() {

		//@formatter:off
		final CheckerboardTexture texture =
				new CheckerboardTextureBuilder<>()
						.texture(new ConstantTextureBuilder<>()
										.spectrum(new RGBSpectrumBuilder<>().rgb().copy(RGB.WHITE).end()))
						.texture(new ConstantTextureBuilder<>()
										.spectrum(new RGBSpectrumBuilder<>().rgb().copy(RGB.RED).end()))
						.texture(new ConstantTextureBuilder<>()
										.spectrum(new RGBSpectrumBuilder<>().rgb().copy(RGB.GREEN).end()))
						.build();
		//@formatter:on

		assertEquals("Checkerboard does not have expected number of sub-textures!", 3, texture.getTextures().length);
	}

}
