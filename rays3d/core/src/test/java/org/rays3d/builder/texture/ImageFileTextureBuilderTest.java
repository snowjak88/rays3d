package org.rays3d.builder.texture;

import static org.junit.Assert.*;

import java.io.File;

import org.junit.Test;
import org.rays3d.builder.java.texture.ImageFileTextureBuilder;
import org.rays3d.texture.ImageFileTexture;

public class ImageFileTextureBuilderTest {

	@Test
	public void testBuild() {

		//@formatter:off
		final ImageFileTexture texture =
				new ImageFileTextureBuilder<>()
						.image(new File("build/resources/test/2x2_test.png"))
						.build();
		//@formatter:on

		assertEquals("Image-file is not named as expected!", "2x2_test.png", texture.getImageFile().getName());
		assertTrue("Image-file does not exist as expected!", texture.getImageFile().exists());
	}

}
