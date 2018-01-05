package org.rays3d.builder.transform;

import static org.junit.Assert.*;

import org.junit.Test;
import org.rays3d.builder.java.transform.RotationTransformBuilder;
import org.rays3d.geometry.Vector3D;
import org.rays3d.transform.RotationTransform;

public class RotationTransformBuilderTest {

	@Test
	public void testBuild() {

		//@formatter:off
		final RotationTransform transform =
				new RotationTransformBuilder<>()
						.axis().vector(Vector3D.K).end()
						.degreesOfRotation(120)
						.build();
		//@formatter:on

		assertEquals("Transform axis (X) not as expected!", 0d, transform.getAxis().getX(), 0.00001);
		assertEquals("Transform axis (Y) not as expected!", 0d, transform.getAxis().getY(), 0.00001);
		assertEquals("Transform axis (Z) not as expected!", 1d, transform.getAxis().getZ(), 0.00001);
		assertEquals("Transform degrees of rotation not as expected!", 120d, transform.getDegreesOfRotation(), 0.00001);
	}

}
