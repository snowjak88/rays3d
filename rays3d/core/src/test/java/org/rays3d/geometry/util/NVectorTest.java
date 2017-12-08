package org.rays3d.geometry.util;

import static org.junit.Assert.*;

import org.junit.Test;
import org.rays3d.geometry.util.NVector;

public class NVectorTest {

	@Test
	public void testGetN() {

		final NVector v = new NVector(1, 2, 3);

		assertEquals(3, v.getN());
	}

	@Test
	public void testGet() {

		final NVector v = new NVector(1, 2, 3);

		assertEquals("v.get(0) not as expected!", 1d, v.get(0), 0.00001);
		assertEquals("v.get(1) not as expected!", 2d, v.get(1), 0.00001);
		assertEquals("v.get(2) not as expected!", 3d, v.get(2), 0.00001);
	}

	@Test
	public void testApplyUnaryOperatorOfDouble() {

		final NVector v = new NVector(1, 2, 3);

		final NVector u = v.apply(d -> d + 2);
		assertEquals("u.get(0) not as expected!", 3d, u.get(0), 0.00001);
		assertEquals("u.get(1) not as expected!", 4d, u.get(1), 0.00001);
		assertEquals("u.get(2) not as expected!", 5d, u.get(2), 0.00001);
	}

	@Test
	public void testApplyNVectorBinaryOperatorOfDouble() {

		final NVector v = new NVector(1, 2, 3);
		final NVector u = new NVector(2, 4, 6);

		final NVector w = v.apply(u, (d1, d2) -> ( d1 > d2 ) ? d1 : d2);
		assertEquals("u.get(0) not as expected!", 2d, w.get(0), 0.00001);
		assertEquals("u.get(1) not as expected!", 4d, w.get(1), 0.00001);
		assertEquals("u.get(2) not as expected!", 6d, w.get(2), 0.00001);
	}

	@Test
	public void testNegate() {

		final NVector v = new NVector(1, 2, 3);

		final NVector u = v.negate();
		assertEquals("u.get(0) not as expected!", -1d, u.get(0), 0.00001);
		assertEquals("u.get(1) not as expected!", -2d, u.get(1), 0.00001);
		assertEquals("u.get(2) not as expected!", -3d, u.get(2), 0.00001);
	}

	@Test
	public void testReciprocal() {

		final NVector v = new NVector(1, 2, 3);

		final NVector u = v.reciprocal();
		assertEquals("u.get(0) not as expected!", 1d / 1d, u.get(0), 0.00001);
		assertEquals("u.get(1) not as expected!", 1d / 2d, u.get(1), 0.00001);
		assertEquals("u.get(2) not as expected!", 1d / 3d, u.get(2), 0.00001);
	}

	@Test
	public void testAddNVector() {

		final NVector v = new NVector(1, 2, 3);
		final NVector u = new NVector(2, 4, 6);

		final NVector w = v.add(u);
		assertEquals("u.get(0) not as expected!", 3d, w.get(0), 0.00001);
		assertEquals("u.get(1) not as expected!", 6d, w.get(1), 0.00001);
		assertEquals("u.get(2) not as expected!", 9d, w.get(2), 0.00001);
	}

	@Test
	public void testAddDouble() {

		final NVector v = new NVector(1, 2, 3);

		final NVector u = v.add(-2);
		assertEquals("u.get(0) not as expected!", -1d, u.get(0), 0.00001);
		assertEquals("u.get(1) not as expected!", 0d, u.get(1), 0.00001);
		assertEquals("u.get(2) not as expected!", 1d, u.get(2), 0.00001);
	}

	@Test
	public void testSubtractNVector() {

		final NVector v = new NVector(1, 2, 3);
		final NVector u = new NVector(2, 4, 6);

		final NVector w = v.subtract(u);
		assertEquals("w.get(0) not as expected!", -1d, w.get(0), 0.00001);
		assertEquals("w.get(1) not as expected!", -2d, w.get(1), 0.00001);
		assertEquals("w.get(2) not as expected!", -3d, w.get(2), 0.00001);
	}

	@Test
	public void testSubtractDouble() {

		final NVector v = new NVector(1, 2, 3);

		final NVector u = v.subtract(-2);
		assertEquals("u.get(0) not as expected!", 3d, u.get(0), 0.00001);
		assertEquals("u.get(1) not as expected!", 4d, u.get(1), 0.00001);
		assertEquals("u.get(2) not as expected!", 5d, u.get(2), 0.00001);
	}

	@Test
	public void testMultiplyNVector() {

		final NVector v = new NVector(1, 2, 3);
		final NVector u = new NVector(2, 4, 6);

		final NVector w = v.multiply(u);
		assertEquals("u.get(0) not as expected!", 2d, w.get(0), 0.00001);
		assertEquals("u.get(1) not as expected!", 8d, w.get(1), 0.00001);
		assertEquals("u.get(2) not as expected!", 18d, w.get(2), 0.00001);
	}

	@Test
	public void testMultiplyDouble() {

		final NVector v = new NVector(1, 2, 3);

		final NVector u = v.multiply(-2);
		assertEquals("u.get(0) not as expected!", -2d, u.get(0), 0.00001);
		assertEquals("u.get(1) not as expected!", -4d, u.get(1), 0.00001);
		assertEquals("u.get(2) not as expected!", -6d, u.get(2), 0.00001);
	}

	@Test
	public void testDivideNVector() {

		final NVector v = new NVector(1, 2, 3);
		final NVector u = new NVector(2, 4, 6);

		final NVector w = v.divide(u);
		assertEquals("u.get(0) not as expected!", 1d / 2d, w.get(0), 0.00001);
		assertEquals("u.get(1) not as expected!", 2d / 4d, w.get(1), 0.00001);
		assertEquals("u.get(2) not as expected!", 3d / 6d, w.get(2), 0.00001);
	}

	@Test
	public void testDivideDouble() {

		final NVector v = new NVector(1, 2, 3);

		final NVector u = v.divide(-2);
		assertEquals("u.get(0) not as expected!", 1d / -2d, u.get(0), 0.00001);
		assertEquals("u.get(1) not as expected!", 2d / -2d, u.get(1), 0.00001);
		assertEquals("u.get(2) not as expected!", 3d / -2d, u.get(2), 0.00001);
	}

}
