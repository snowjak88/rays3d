package org.rays3d.geometry.boundingvolume;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;

import org.junit.Test;
import org.rays3d.geometry.Point3D;
import org.rays3d.geometry.Ray;
import org.rays3d.geometry.Vector3D;
import org.rays3d.transform.RotationTransform;

public class AABBTest {

	@Test
	public void testAABBAABBListOfTransform() {

		AABB unTransformed = new AABB(new Point3D(0, 0, 0), new Point3D(1, 2, 0));
		AABB transformed = new AABB(unTransformed, Arrays.asList(new RotationTransform(Vector3D.K, 90d)));

		assertEquals("Rotated min-extent-X not as expected!", -2d, transformed.getMinExtent().getX(), 0.00001);
		assertEquals("Rotated min-extent-Y not as expected!", 0d, transformed.getMinExtent().getY(), 0.00001);
		assertEquals("Rotated min-extent-Z not as expected!", 0d, transformed.getMinExtent().getZ(), 0.00001);

		assertEquals("Rotated max-extent-X not as expected!", 0d, transformed.getMaxExtent().getX(), 0.00001);
		assertEquals("Rotated max-extent-Y not as expected!", 1d, transformed.getMaxExtent().getY(), 0.00001);
		assertEquals("Rotated max-extent-Z not as expected!", 0d, transformed.getMaxExtent().getZ(), 0.00001);
	}

	@Test
	public void testAABBCollectionOfPointListOfTransform() {

		AABB transformed = new AABB(Arrays.asList(new Point3D(0, 0, 0), new Point3D(1, 2, 0)),
				Arrays.asList(new RotationTransform(Vector3D.K, -90d)));

		assertEquals("Rotated min-extent-X not as expected!", 0d, transformed.getMinExtent().getX(), 0.00001);
		assertEquals("Rotated min-extent-Y not as expected!", -1d, transformed.getMinExtent().getY(), 0.00001);
		assertEquals("Rotated min-extent-Z not as expected!", 0d, transformed.getMinExtent().getZ(), 0.00001);

		assertEquals("Rotated max-extent-X not as expected!", 2, transformed.getMaxExtent().getX(), 0.00001);
		assertEquals("Rotated max-extent-Y not as expected!", 0, transformed.getMaxExtent().getY(), 0.00001);
		assertEquals("Rotated max-extent-Z not as expected!", 0d, transformed.getMaxExtent().getZ(), 0.00001);
	}

	@Test
	public void testAABBCollectionOfPoint() {

		AABB aabb = new AABB(Arrays.asList(new Point3D(0, 0, 0), new Point3D(-1, 0, 3), new Point3D(1, -2, 0),
				new Point3D(1, 2, -3)));

		assertEquals("Min-extent-X not as expected!", -1d, aabb.getMinExtent().getX(), 0.00001);
		assertEquals("Min-extent-Y not as expected!", -2d, aabb.getMinExtent().getY(), 0.00001);
		assertEquals("Min-extent-Z not as expected!", -3d, aabb.getMinExtent().getZ(), 0.00001);

		assertEquals("Max-extent-X not as expected!", 1d, aabb.getMaxExtent().getX(), 0.00001);
		assertEquals("Max-extent-Y not as expected!", 2d, aabb.getMaxExtent().getY(), 0.00001);
		assertEquals("Max-extent-Z not as expected!", 3d, aabb.getMaxExtent().getZ(), 0.00001);
	}

	@Test
	public void testUnion() {

		AABB aabb1 = new AABB(new Point3D(-1, -1, -1), new Point3D(0, 0, 0)),
				aabb2 = new AABB(new Point3D(-1, 0, -1), new Point3D(2, 2, 2));
		AABB union = AABB.union(Arrays.asList(aabb1, aabb2));

		assertEquals("Min-extent-X not as expected!", -1d, union.getMinExtent().getX(), 0.00001);
		assertEquals("Min-extent-Y not as expected!", -1d, union.getMinExtent().getY(), 0.00001);
		assertEquals("Min-extent-Z not as expected!", -1d, union.getMinExtent().getZ(), 0.00001);

		assertEquals("Max-extent-X not as expected!", 2d, union.getMaxExtent().getX(), 0.00001);
		assertEquals("Max-extent-Y not as expected!", 2d, union.getMaxExtent().getY(), 0.00001);
		assertEquals("Max-extent-Z not as expected!", 2d, union.getMaxExtent().getZ(), 0.00001);
	}

	@Test
	public void testIsIntersecting() {

		AABB aabb = new AABB(new Point3D(0, 0, 0), new Point3D(2, 2, 2));

		Ray rayMiss = new Ray(new Point3D(-1, -1, -5), new Vector3D(0, 0, 1));
		Ray rayHit = new Ray(new Point3D(1, 1, 1), new Vector3D(0, 0, 1));

		assertFalse("Expected miss is a hit!", aabb.isIntersecting(rayMiss));
		assertTrue("Expected hit is a miss!", aabb.isIntersecting(rayHit));
	}

}
