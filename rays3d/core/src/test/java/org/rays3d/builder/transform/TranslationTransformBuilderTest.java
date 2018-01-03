package org.rays3d.builder.transform;

import static org.junit.Assert.*;

import org.junit.Test;
import org.rays3d.builder.transform.TranslationTransformBuilder;
import org.rays3d.transform.TranslationTransform;

public class TranslationTransformBuilderTest {

	@Test
	public void testBuild() {

		//@formatter:off
		final TranslationTransform transform =
				new TranslationTransformBuilder<>()
						.dx(1d).dy(2d).dz(3d)
						.build();
		//@formatter:on

		assertEquals("Transform displacement (X) not as expected!", 1d, transform.getDx(), 0.00001);
		assertEquals("Transform displacement (Y) not as expected!", 2d, transform.getDy(), 0.00001);
		assertEquals("Transform displacement (Z) not as expected!", 3d, transform.getDz(), 0.00001);
	}

}
