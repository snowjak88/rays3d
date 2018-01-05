package org.rays3d.builder.java

import static org.junit.Assert.*
import static org.rays3d.builder.java.WorldBuilderSupport.*

import org.junit.Test
import org.rays3d.world.World
import org.rays3d.builder.java.WorldBuilderSupport.BSDFs
import org.rays3d.builder.java.shape.SphereBuilder
import org.rays3d.camera.PinholeCamera
import org.rays3d.geometry.Vector3D

class WorldBuilderTest {

	@Test
	public void testBuild() {

		//@formatter:off
		final World world =
				world()
					.camera(Cameras.pinhole()
									.eyePoint().coords(0, 0, -5).end()
									.lookAt().coords(0,0,0).end()
									.up().vector(Vector3D.J).end()
									.focalLength(8)
									.imagePlaneSize(4,4)
									.filmSize(100,100))
					.primitive()
						.shape(Shapes.sphere()
									.radius(1)
									.translate().dx(-3).dy(0).dz(0).end())
						.bsdf(BSDFs.perfectSpecular())
						.end()
				.build();
		//@formatter:on
		
		assertNotNull "World does not have a configured Camera!", world.getCamera()
		assertTrue "World's configured Camera is not the expected PinholeCamera!", world.getCamera() instanceof PinholeCamera
		
		assertNotNull "World does not have any configured Primitives!", world.getPrimitives()
		assertFalse "World does not have any configured Primitives!", world.getPrimitives().isEmpty()
		assertEquals "World does not have the expected number of Primitives!", 1, world.getPrimitives().size()
		
	}

}
