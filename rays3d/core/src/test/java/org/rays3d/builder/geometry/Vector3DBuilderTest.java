package org.rays3d.builder.geometry;

import static org.junit.Assert.*;

import org.junit.Test;
import org.rays3d.builder.geometry.Vector3DBuilder;
import org.rays3d.geometry.Vector3D;

public class Vector3DBuilderTest {

	@Test
	public void testBuild_components() {

		//@formatter:off
		final Vector3D vector =
				new Vector3DBuilder<>()
						.x(1)
						.y(2)
						.z(3)
						.build();
		//@formatter:on

		assertEquals("Vector (X) not as expected!", 1d, vector.getX(), 0.00001);
		assertEquals("Vector (Y) not as expected!", 2d, vector.getY(), 0.00001);
		assertEquals("Vector (Z) not as expected!", 3d, vector.getZ(), 0.00001);
	}

	@Test
	public void testBuild_coords() {

		//@formatter:off
		final Vector3D vector =
				new Vector3DBuilder<>()
						.coords(1, 2, 3)
						.build();
		//@formatter:on

		assertEquals("Vector (X) not as expected!", 1d, vector.getX(), 0.00001);
		assertEquals("Vector (Y) not as expected!", 2d, vector.getY(), 0.00001);
		assertEquals("Vector (Z) not as expected!", 3d, vector.getZ(), 0.00001);
	}

	@Test
	public void testBuild_copy() {

		//@formatter:off
		final Vector3D vector =
				new Vector3DBuilder<>()
						.vector(new Vector3D(1,2,3))
						.build();
		//@formatter:on

		assertEquals("Vector (X) not as expected!", 1d, vector.getX(), 0.00001);
		assertEquals("Vector (Y) not as expected!", 2d, vector.getY(), 0.00001);
		assertEquals("Vector (Z) not as expected!", 3d, vector.getZ(), 0.00001);
	}

}
