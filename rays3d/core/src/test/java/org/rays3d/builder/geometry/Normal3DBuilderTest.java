package org.rays3d.builder.geometry;

import static org.junit.Assert.*;

import org.junit.Test;
import org.rays3d.builder.geometry.Normal3DBuilder;
import org.rays3d.geometry.Normal3D;

public class Normal3DBuilderTest {

	@Test
	public void testBuild_components() {

		//@formatter:off
		final Normal3D normal =
				new Normal3DBuilder<>()
						.x(1)
						.y(2)
						.z(3)
						.build();
		//@formatter:on

		assertEquals("Normal (X) not as expected!", 1d, normal.getX(), 0.00001);
		assertEquals("Normal (Y) not as expected!", 2d, normal.getY(), 0.00001);
		assertEquals("Normal (Z) not as expected!", 3d, normal.getZ(), 0.00001);
	}

	@Test
	public void testBuild_coords() {

		//@formatter:off
		final Normal3D normal =
				new Normal3DBuilder<>()
						.coords(1, 2, 3)
						.build();
		//@formatter:on

		assertEquals("Normal (X) not as expected!", 1d, normal.getX(), 0.00001);
		assertEquals("Normal (Y) not as expected!", 2d, normal.getY(), 0.00001);
		assertEquals("Normal (Z) not as expected!", 3d, normal.getZ(), 0.00001);
	}

	@Test
	public void testBuild_copy() {

		//@formatter:off
		final Normal3D normal =
				new Normal3DBuilder<>()
						.normal(new Normal3D(1,2,3))
						.build();
		//@formatter:on

		assertEquals("Normal (X) not as expected!", 1d, normal.getX(), 0.00001);
		assertEquals("Normal (Y) not as expected!", 2d, normal.getY(), 0.00001);
		assertEquals("Normal (Z) not as expected!", 3d, normal.getZ(), 0.00001);
	}

}
