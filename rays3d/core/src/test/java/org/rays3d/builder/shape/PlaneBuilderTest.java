package org.rays3d.builder.shape;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.rays3d.builder.java.shape.PlaneBuilder;
import org.rays3d.shape.PlaneShape;

public class PlaneBuilderTest {

	@Test
	public void testBuild() {

		//@formatter:off
		final PlaneShape plane =
				new PlaneBuilder<>()
						.translate().dx(0).dy(-3).dz(0).end()
						.build();
		//@formatter:on

		assertEquals("Not all transforms present!", 1, plane.getWorldToLocalTransforms().size());

	}

}
