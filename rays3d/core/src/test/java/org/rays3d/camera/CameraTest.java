package org.rays3d.camera;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.rays3d.geometry.Point3D;
import org.rays3d.geometry.Ray;
import org.rays3d.geometry.Vector3D;

public class CameraTest {

	private Camera camera, raisedCamera;

	@Before
	public void setUp() throws Exception {

		camera = new Camera(100, 100, 4d, 4d, new Point3D(0d, 0d, -5d), new Point3D(0d, 0d, 0d), Vector3D.J) {

			@Override
			public Ray getRay(double imageU, double imageV, double lensU, double lensV) {

				// For these tests, we don't care about testing this method
				return null;
			}
		};

		raisedCamera = new Camera(100, 100, 4d, 4d, new Point3D(3d, -2d, -5d), new Point3D(0d, 0d, 0d), Vector3D.J) {

			@Override
			public Ray getRay(double imageU, double imageV, double lensU, double lensV) {

				// For these tests, we don't care about testing this method
				return null;
			}
		};
	}

	@Test
	public void testCameraToWorldUnraised() {

		Ray cameraRay = new Ray(new Point3D(0, 0, 0), new Vector3D(0, 0, 1));
		Ray worldRay = camera.cameraToWorld(cameraRay);

		assertEquals("Ray origin-X is not as expected!", 0d, worldRay.getOrigin().getX(), 0.00001);
		assertEquals("Ray origin-Y is not as expected!", 0d, worldRay.getOrigin().getY(), 0.00001);
		assertEquals("Ray origin-Z is not as expected!", -5d, worldRay.getOrigin().getZ(), 0.00001);

		assertEquals("Ray direction-X is not as expected!", 0d, worldRay.getDirection().getX(), 0.00001);
		assertEquals("Ray direction-Y is not as expected!", 0d, worldRay.getDirection().getY(), 0.00001);
		assertEquals("Ray direction-Z is not as expected!", 1d, worldRay.getDirection().getZ(), 0.00001);
	}

	@Test
	public void testCameraToWorldRaised() {

		Ray cameraRay = new Ray(new Point3D(0, 0, 0), new Vector3D(0, 0, 1));
		Ray worldRay = raisedCamera.cameraToWorld(cameraRay);

		assertEquals("Ray origin-X is not as expected!", 3d, worldRay.getOrigin().getX(), 0.00001);
		assertEquals("Ray origin-Y is not as expected!", -2d, worldRay.getOrigin().getY(), 0.00001);
		assertEquals("Ray origin-Z is not as expected!", -5d, worldRay.getOrigin().getZ(), 0.00001);

		assertEquals("Ray direction-X is not as expected!", -0.486664d, worldRay.getDirection().getX(), 0.00001);
		assertEquals("Ray direction-Y is not as expected!", 0.324443d, worldRay.getDirection().getY(), 0.00001);
		assertEquals("Ray direction-Z is not as expected!", 0.811107d, worldRay.getDirection().getZ(), 0.00001);
	}

}
