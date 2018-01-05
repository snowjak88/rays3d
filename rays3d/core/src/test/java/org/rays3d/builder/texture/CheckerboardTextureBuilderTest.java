package org.rays3d.builder.texture;

import static org.junit.Assert.*;

import org.junit.Test;
import org.rays3d.javabuilder.spectrum.RGBSpectrumBuilder;
import org.rays3d.javabuilder.texture.CheckerboardTextureBuilder;
import org.rays3d.javabuilder.texture.ConstantTextureBuilder;
import org.rays3d.spectrum.RGB;
import org.rays3d.texture.CheckerboardTexture;

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
