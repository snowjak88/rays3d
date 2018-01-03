package org.rays3d.builder.transform;

import static org.junit.Assert.*;

import org.junit.Test;
import org.rays3d.builder.transform.ScaleTransformBuilder;
import org.rays3d.transform.ScaleTransform;

public class ScaleTransformBuilderTest {

	@Test
	public void testBuild() {

		//@formatter:off
		final ScaleTransform transform =
				new ScaleTransformBuilder<>()
						.sx(1d).sy(2d).sz(3d)
						.build();
		//@formatter:on

		assertEquals("Transform scale (X) not as expected!", 1d, transform.getSx(), 0.00001);
		assertEquals("Transform scale (Y) not as expected!", 2d, transform.getSy(), 0.00001);
		assertEquals("Transform scale (Z) not as expected!", 3d, transform.getSz(), 0.00001);
	}

}
