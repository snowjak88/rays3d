package org.rays3d.builder.camera;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.rays3d.builder.camera.PinholeCameraBuilder;
import org.rays3d.camera.PinholeCamera;
import org.rays3d.geometry.Vector3D;

public class PinholeCameraBuilderTest {

	@Test
	public void testBuild() {

		//@formatter:off
		final PinholeCamera camera =
				new PinholeCameraBuilder<>()
							.eyePoint()
								.x(-1).y(-3).z(-5).end()
							.lookAt()
								.x(1).y(2).z(3).end()
							.up()
								.vector(Vector3D.J).end()
							.filmSize(100, 100)
							.imagePlaneSize(4, 4)
							.focalLength(8)
					.build();
		//@formatter:on

		assertEquals("Eye-point (X) is not as expected!", -1d, camera.getEyePoint().getX(), 0.00001);
		assertEquals("Eye-point (Y) is not as expected!", -3d, camera.getEyePoint().getY(), 0.00001);
		assertEquals("Eye-point (Z) is not as expected!", -5d, camera.getEyePoint().getZ(), 0.00001);

		assertEquals("Look-at point (X) is not as expected!", 1d, camera.getLookAt().getX(), 0.00001);
		assertEquals("Look-at point (Y) is not as expected!", 2d, camera.getLookAt().getY(), 0.00001);
		assertEquals("Look-at point (Z) is not as expected!", 3d, camera.getLookAt().getZ(), 0.00001);

		assertEquals("Up-vector (X) is not as expected!", 0d, camera.getUp().getX(), 0.00001);
		assertEquals("Up-vector (Y) is not as expected!", 1d, camera.getUp().getY(), 0.00001);
		assertEquals("Up-vector (Z) is not as expected!", 0d, camera.getUp().getZ(), 0.00001);

		assertEquals("Film-size (X) is not as expected!", 100d, camera.getFilmSizeX(), 0.00001);
		assertEquals("Film-size (Y) is not as expected!", 100d, camera.getFilmSizeY(), 0.00001);

		assertEquals("Image-plane-size (X) is not as expected!", 4d, camera.getImagePlaneSizeX(), 0.00001);
		assertEquals("Image-plane-size (Y) is not as expected!", 4d, camera.getImagePlaneSizeY(), 0.00001);

		assertEquals("Focal-length is not as expected!", 8d, camera.getFocalLength(), 0.00001);
	}

}
