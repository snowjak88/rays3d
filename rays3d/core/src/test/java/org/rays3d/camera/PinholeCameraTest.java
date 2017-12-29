package org.rays3d.camera;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.rays3d.geometry.Point2D;
import org.rays3d.geometry.Point3D;
import org.rays3d.geometry.Ray;
import org.rays3d.geometry.Vector3D;
import org.rays3d.message.sample.Sample;

public class PinholeCameraTest {

	@Test
	public void testGetRayDoubleDoubleDoubleDouble_center() {

		final Camera camera = new PinholeCamera(100, 100, 4d, 4d, new Point3D(0, 0, -5), new Point3D(0, 0, 0),
				Vector3D.J, 8);
		final Ray ray = camera.getRay(50d, 50d, 0.5d, 0.5d);

		assertEquals("Ray origin-X not as expected!", 0d, ray.getOrigin().getX(), 0.00001);
		assertEquals("Ray origin-Y not as expected!", 0d, ray.getOrigin().getY(), 0.00001);
		assertEquals("Ray origin-Z not as expected!", -5d, ray.getOrigin().getZ(), 0.00001);

		assertEquals("Ray direction-X not as expected!", 0d, ray.getDirection().getX(), 0.00001);
		assertEquals("Ray direction-Y not as expected!", 0d, ray.getDirection().getY(), 0.00001);
		assertEquals("Ray direction-Z not as expected!", 1d, ray.getDirection().getZ(), 0.00001);
	}

	@Test
	public void testGetRayDoubleDoubleDoubleDouble_edge() {

		final Camera camera = new PinholeCamera(100, 100, 4d, 4d, new Point3D(0, 0, -5), new Point3D(0, 0, 0),
				Vector3D.J, 8);
		Ray ray = camera.getRay(100d, 50d, 0.5d, 0.5d);

		assertEquals("Ray origin-X not as expected!", 2d, ray.getOrigin().getX(), 0.00001);
		assertEquals("Ray origin-Y not as expected!", 0d, ray.getOrigin().getY(), 0.00001);
		assertEquals("Ray origin-Z not as expected!", -5d, ray.getOrigin().getZ(), 0.00001);

		assertEquals("Ray direction-X not as expected!", 0.242536d, ray.getDirection().getX(), 0.00001);
		assertEquals("Ray direction-Y not as expected!", 0d, ray.getDirection().getY(), 0.00001);
		assertEquals("Ray direction-Z not as expected!", 0.970143d, ray.getDirection().getZ(), 0.00001);
	}

	@Test
	public void testGetRay() {

		final Camera camera = new PinholeCamera(100, 100, 4d, 4d, new Point3D(0, 0, -5), new Point3D(0, 0, 0),
				Vector3D.J, 8);

		final Sample sample = new Sample();
		sample.setRenderId(1);
		sample.setFilmPoint(new Point2D(25, 25));

		Ray ray = camera.getRay(sample);

		final double imagePointX = ( sample.getFilmPoint().getX() - ( camera.getFilmSizeX() / 2d ) )
				* ( camera.getImagePlaneSizeX() / camera.getFilmSizeX() );
		final double imagePointY = ( sample.getFilmPoint().getY() - ( camera.getFilmSizeY() / 2d ) )
				* ( camera.getImagePlaneSizeY() / camera.getFilmSizeY() );
		final double imagePointZ = camera.getEyePoint().getZ();

		assertEquals("Ray origin-X is not as expected!", imagePointX, ray.getOrigin().getX(), 0.00001);
		assertEquals("Ray origin-Y is not as expected!", imagePointY, ray.getOrigin().getY(), 0.00001);
		assertEquals("Ray origin-Z is not as expected!", imagePointZ, ray.getOrigin().getZ(), 0.00001);

	}

}
