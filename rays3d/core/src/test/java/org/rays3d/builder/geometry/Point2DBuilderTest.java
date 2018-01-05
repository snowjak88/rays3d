package org.rays3d.builder.geometry;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.rays3d.builder.java.geometry.Point2DBuilder;
import org.rays3d.geometry.Point2D;

public class Point2DBuilderTest {

	@Test
	public void testBuild_components() {

		//@formatter:off
		final Point2D vector =
				new Point2DBuilder<>()
						.x(1)
						.y(2)
						.build();
		//@formatter:on

		assertEquals("Point (X) not as expected!", 1d, vector.getX(), 0.00001);
		assertEquals("Point (Y) not as expected!", 2d, vector.getY(), 0.00001);
	}

	@Test
	public void testBuild_coords() {

		//@formatter:off
		final Point2D vector =
				new Point2DBuilder<>()
						.coords(1, 2)
						.build();
		//@formatter:on

		assertEquals("Point (X) not as expected!", 1d, vector.getX(), 0.00001);
		assertEquals("Point (Y) not as expected!", 2d, vector.getY(), 0.00001);
	}

	@Test
	public void testBuild_copy() {

		//@formatter:off
		final Point2D vector =
				new Point2DBuilder<>()
						.point(new Point2D(1,2))
						.build();
		//@formatter:on

		assertEquals("Point (X) not as expected!", 1d, vector.getX(), 0.00001);
		assertEquals("Point (Y) not as expected!", 2d, vector.getY(), 0.00001);
	}

}
