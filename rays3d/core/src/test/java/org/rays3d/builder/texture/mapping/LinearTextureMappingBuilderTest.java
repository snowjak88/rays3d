package org.rays3d.builder.texture.mapping;

import static org.junit.Assert.*;

import org.junit.Test;
import org.rays3d.builder.texture.mapping.LinearTextureMappingBuilder;
import org.rays3d.texture.mapping.LinearTextureMapping;

public class LinearTextureMappingBuilderTest {

	@Test
	public void testBuild() {

		//@formatter:off
		final LinearTextureMapping mapping =
				new LinearTextureMappingBuilder<>()
						.from(0, 1).to(2, 3).build();
		//@formatter:on

		assertEquals("Mapping min-U is not as expected!", 0d, mapping.getTextureMinU(), 0.00001);
		assertEquals("Mapping min-V is not as expected!", 1d, mapping.getTextureMinV(), 0.00001);
		assertEquals("Mapping max-U is not as expected!", 2d, mapping.getTextureMaxU(), 0.00001);
		assertEquals("Mapping max-V is not as expected!", 3d, mapping.getTextureMaxV(), 0.00001);
	}

}
