package org.rays3d.transform;

import static org.junit.Assert.*;
import static org.apache.commons.math3.util.FastMath.*;

import org.junit.Before;
import org.junit.Test;
import org.rays3d.geometry.Normal3D;
import org.rays3d.geometry.Point3D;
import org.rays3d.geometry.Ray;
import org.rays3d.geometry.Vector3D;

public class ScaleTransformTest {

	private Transform transform;

	@Before
	public void setUp() throws Exception {

		transform = new ScaleTransform(2d, 2d, 2d);
	}

	@Test
	public void testWorldToLocalPoint() {

		Point3D point = new Point3D(1, 2, 3);
		Point3D transformed = transform.worldToLocal(point);

		assertEquals("Transformed X is not as expected!", 1d / 2d, transformed.getX(), 0.00001);
		assertEquals("Transformed Y is not as expected!", 2d / 2d, transformed.getY(), 0.00001);
		assertEquals("Transformed Z is not as expected!", 3d / 2d, transformed.getZ(), 0.00001);
	}

	@Test
	public void testLocalToWorldPoint() {

		Point3D point = new Point3D(1, 2, 3);
		Point3D transformed = transform.localToWorld(point);

		assertEquals("Transformed X is not as expected!", 1d * 2d, transformed.getX(), 0.00001);
		assertEquals("Transformed Y is not as expected!", 2d * 2d, transformed.getY(), 0.00001);
		assertEquals("Transformed Z is not as expected!", 3d * 2d, transformed.getZ(), 0.00001);
	}

	@Test
	public void testWorldToLocalVector() {

		Vector3D vector = new Vector3D(1, 2, 3);
		Vector3D transformed = transform.worldToLocal(vector);

		assertEquals("Transformed X is not as expected!", 1d / 2d, transformed.getX(), 0.00001);
		assertEquals("Transformed Y is not as expected!", 2d / 2d, transformed.getY(), 0.00001);
		assertEquals("Transformed Z is not as expected!", 3d / 2d, transformed.getZ(), 0.00001);
	}

	@Test
	public void testLocalToWorldVector() {

		Vector3D vector = new Vector3D(1, 2, 3);
		Vector3D transformed = transform.localToWorld(vector);

		assertEquals("Transformed X is not as expected!", 1d * 2d, transformed.getX(), 0.00001);
		assertEquals("Transformed Y is not as expected!", 2d * 2d, transformed.getY(), 0.00001);
		assertEquals("Transformed Z is not as expected!", 3d * 2d, transformed.getZ(), 0.00001);
	}

	@Test
	public void testWorldToLocalNormal() {

		Normal3D normal = new Normal3D(1, 2, 3);
		Normal3D transformed = transform.localToWorld(normal);

		assertEquals("Transformed X is not as expected!", 1d / 2d, transformed.getX(), 0.00001);
		assertEquals("Transformed Y is not as expected!", 2d / 2d, transformed.getY(), 0.00001);
		assertEquals("Transformed Z is not as expected!", 3d / 2d, transformed.getZ(), 0.00001);
	}

	@Test
	public void testLocalToWorldNormal() {

		Normal3D normal = new Normal3D(1, 2, 3);
		Normal3D transformed = transform.worldToLocal(normal);

		assertEquals("Transformed X is not as expected!", 1d * 2d, transformed.getX(), 0.00001);
		assertEquals("Transformed Y is not as expected!", 2d * 2d, transformed.getY(), 0.00001);
		assertEquals("Transformed Z is not as expected!", 3d * 2d, transformed.getZ(), 0.00001);
	}

	@Test
	public void testLocalToWorldRay_1() {

		final Ray ray = new Ray(new Point3D(1, 2, 3), new Vector3D(-2, 4, -6).normalize(), 1.0, 1);
		final Ray transformed1 = transform.localToWorld(ray);

		assertEquals("#1-Transformed origin-X is not as expected!", 1d * 2d, transformed1.getOrigin().getX(), 0.00001);
		assertEquals("#1-Transformed origin-Y is not as expected!", 2d * 2d, transformed1.getOrigin().getY(), 0.00001);
		assertEquals("#1-Transformed origin-Z is not as expected!", 3d * 2d, transformed1.getOrigin().getZ(), 0.00001);

		assertEquals("#1-Transformed direction-X is not as expected!", -1d / sqrt(14d),
				transformed1.getDirection().getX(), 0.00001);
		assertEquals("#1-Transformed direction-Y is not as expected!", sqrt(2d / 7d),
				transformed1.getDirection().getY(), 0.00001);
		assertEquals("#1-Transformed direction-Z is not as expected!", -3d / sqrt(14d),
				transformed1.getDirection().getZ(), 0.00001);

		assertEquals("#1-T is not as expected!", 2.0, transformed1.getT(), 0.00001);
		assertEquals("#1-Depth is not as expected!", 1, transformed1.getDepth());
	}

	@Test
	public void testLocalToWorldRay_2() {

		final Ray ray = new Ray(new Point3D(1, 2, 3), new Vector3D(-2, 4, -6).normalize(), 1.0, 1);
		final Transform transform2 = new ScaleTransform(1d / 3d, -1d / 2d, 1d / 5d);
		final Ray transformed2 = transform2.localToWorld(ray);

		assertEquals("#2-Transformed origin-X is not as expected!", 1d / 3d, transformed2.getOrigin().getX(), 0.00001);
		assertEquals("#2-Transformed origin-Y is not as expected!", -1, transformed2.getOrigin().getY(), 0.00001);
		assertEquals("#2-Transformed origin-Z is not as expected!", 3d / 5d, transformed2.getOrigin().getZ(), 0.00001);

		assertEquals("#2-Transformed direction-X is not as expected!", -0.274825, transformed2.getDirection().getX(),
				0.00001);
		assertEquals("#2-Transformed direction-Y is not as expected!", -0.824475, transformed2.getDirection().getY(),
				0.00001);
		assertEquals("#2-Transformed direction-Z is not as expected!", -0.494685, transformed2.getDirection().getZ(),
				0.00001);

		assertEquals("#2-T is not as expected!", 0.324159, transformed2.getT(), 0.00001);
		assertEquals("#2-Depth is not as expected!", 1, transformed2.getDepth());
	}

	@Test
	public void testWorldToLocalRay_1() {

		final Ray ray = new Ray(new Point3D(1, 2, 3), new Vector3D(-2, 4, -6).normalize(), 1.0, 1);
		final Ray transformed1 = transform.worldToLocal(ray);

		assertEquals("Transformed X is not as expected!", 1d / 2d, transformed1.getOrigin().getX(), 0.00001);
		assertEquals("Transformed Y is not as expected!", 2d / 2d, transformed1.getOrigin().getY(), 0.00001);
		assertEquals("Transformed Z is not as expected!", 3d / 2d, transformed1.getOrigin().getZ(), 0.00001);

		assertEquals("#1-Transformed direction-X is not as expected!", -1d / sqrt(14d),
				transformed1.getDirection().getX(), 0.00001);
		assertEquals("#1-Transformed direction-Y is not as expected!", sqrt(2d / 7d),
				transformed1.getDirection().getY(), 0.00001);
		assertEquals("#1-Transformed direction-Z is not as expected!", -3d / sqrt(14d),
				transformed1.getDirection().getZ(), 0.00001);

		assertEquals("#1-T is not as expected!", 0.5, transformed1.getT(), 0.00001);
		assertEquals("#1-Depth is not as expected!", 1, transformed1.getDepth());
	}

	@Test
	public void testWorldToLocalRay_2() {

		final Ray ray = new Ray(new Point3D(1, 2, 3), new Vector3D(-2, 4, -6).normalize(), 1.0, 1);
		final Transform transform2 = new ScaleTransform(1d / 3d, -1d / 2d, 1d / 5d);
		final Ray transformed2 = transform2.worldToLocal(ray);

		assertEquals("#2-Transformed origin-X is not as expected!", 1d / ( 1d / 3d ), transformed2.getOrigin().getX(),
				0.00001);
		assertEquals("#2-Transformed origin-Y is not as expected!", 2d / ( -1d / 2d ), transformed2.getOrigin().getY(),
				0.00001);
		assertEquals("#2-Transformed origin-Z is not as expected!", 3d / ( 1d / 5d ), transformed2.getOrigin().getZ(),
				0.00001);

		assertEquals("#2-Transformed direction-X is not as expected!", -0.189737, transformed2.getDirection().getX(),
				0.00001);
		assertEquals("#2-Transformed direction-Y is not as expected!", -0.252982, transformed2.getDirection().getY(),
				0.00001);
		assertEquals("#2-Transformed direction-Z is not as expected!", -0.948683, transformed2.getDirection().getZ(),
				0.00001);

		assertEquals("#2-T is not as expected!", 4.22577, transformed2.getT(), 0.00001);
		assertEquals("#2-Depth is not as expected!", 1, transformed2.getDepth());
	}

}
