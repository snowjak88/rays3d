package org.rays3d.builder.geometry;

import static org.junit.Assert.*;

import org.junit.Test;
import org.rays3d.builder.geometry.Point3DBuilder;
import org.rays3d.geometry.Point3D;

public class Point3DBuilderTest {

	@Test
	public void testBuild_components() {

		//@formatter:off
		final Point3D vector =
				new Point3DBuilder<>()
						.x(1)
						.y(2)
						.z(3)
						.build();
		//@formatter:on

		assertEquals("Point (X) not as expected!", 1d, vector.getX(), 0.00001);
		assertEquals("Point (Y) not as expected!", 2d, vector.getY(), 0.00001);
		assertEquals("Point (Z) not as expected!", 3d, vector.getZ(), 0.00001);
	}

	@Test
	public void testBuild_coords() {

		//@formatter:off
		final Point3D vector =
				new Point3DBuilder<>()
						.coords(1, 2,3)
						.build();
		//@formatter:on

		assertEquals("Point (X) not as expected!", 1d, vector.getX(), 0.00001);
		assertEquals("Point (Y) not as expected!", 2d, vector.getY(), 0.00001);
		assertEquals("Point (Z) not as expected!", 3d, vector.getZ(), 0.00001);
	}

	@Test
	public void testBuild_copy() {

		//@formatter:off
		final Point3D vector =
				new Point3DBuilder<>()
						.point(new Point3D(1,2,3))
						.build();
		//@formatter:on

		assertEquals("Point (X) not as expected!", 1d, vector.getX(), 0.00001);
		assertEquals("Point (Y) not as expected!", 2d, vector.getY(), 0.00001);
		assertEquals("Point (Z) not as expected!", 3d, vector.getZ(), 0.00001);
	}

}
