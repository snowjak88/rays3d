package org.rays3d.geometry;

import static org.junit.Assert.*;
import static org.apache.commons.math3.util.FastMath.*;
import org.junit.Test;

public class Vector3DTest {

	@Test
	public void testNormalize() {

		final Vector3D v = new Vector3D(3, 4, 5).normalize();

		assertEquals("Normalized-X not as expected!", 0.424264, v.getX(), 0.00001);
		assertEquals("Normalized-Y not as expected!", 0.565685, v.getY(), 0.00001);
		assertEquals("Normalized-Z not as expected!", 0.707107, v.getZ(), 0.00001);
	}

	@Test
	public void testDotProduct() {

		final Vector3D v1 = new Vector3D(1, 2, 3), v2 = new Vector3D(4, 5, 6);

		assertEquals(( 1 * 4 ) + ( 2 * 5 ) + ( 3 * 6 ), v1.dotProduct(v2), 0.00001);
	}

	@Test
	public void testCrossProduct() {

		final Vector3D v = Vector3D.I, u = Vector3D.J;
		final Vector3D w = v.crossProduct(u);

		assertEquals("Cross-product-X not as expected!", 0d, w.getX(), 0.00001);
		assertEquals("Cross-product-Y not as expected!", 0d, w.getY(), 0.00001);
		assertEquals("Cross-product-Z not as expected!", 1d, w.getZ(), 0.00001);
	}

	@Test
	public void testOrthogonal() {

		final Vector3D v = new Vector3D(1, 2, 3);
		final Vector3D u = v.orthogonal();

		assertEquals(0d, v.dotProduct(u), 0.00001);
	}

	@Test
	public void testGetMagnitude() {

		final Vector3D v = new Vector3D(4, 2, 7);

		assertEquals(sqrt(69d), v.getMagnitude(), 0.00001);
	}

	@Test
	public void testGetMagnitudeSq() {

		final Vector3D v = new Vector3D(3, 8, 2);

		assertEquals(77d, v.getMagnitudeSq(), 0.00001);
	}

}
