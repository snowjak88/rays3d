package org.rays3d.geometry;

import static org.junit.Assert.*;

import org.junit.Test;

public class Point3DTest {

	@Test
	public void testGetX() {

		final Point3D p = new Point3D(3, -7, 2);

		assertEquals(3d, p.getX(), 0.00001);
	}

	@Test
	public void testGetY() {

		final Point3D p = new Point3D(3, -7, 2);

		assertEquals(-7d, p.getY(), 0.00001);
	}

	@Test
	public void testGetZ() {

		final Point3D p = new Point3D(3, -7, 2);

		assertEquals(2d, p.getZ(), 0.00001);
	}

}
