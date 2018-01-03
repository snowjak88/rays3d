package org.rays3d.builder.shape;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.rays3d.builder.shape.SphereBuilder;
import org.rays3d.geometry.Vector3D;
import org.rays3d.shape.SphereShape;

public class SphereBuilderTest {

	@Test
	public void testBuild() {

		//@formatter:off
		final SphereShape sphere =
				new SphereBuilder<>()
						.radius(1)
						.translate().dx(3).dy(0).dz(0).end()
						.rotate()
							.axis()
								.vector(Vector3D.J).end()
							.degreesOfRotation(90)
							.end()
						.build();
		//@formatter:on

		assertEquals("Sphere radius is not as expected!", 1d, sphere.getRadius(), 0.00001);
		assertEquals("Not all transforms present!", 2, sphere.getWorldToLocalTransforms().size());
	}

}
