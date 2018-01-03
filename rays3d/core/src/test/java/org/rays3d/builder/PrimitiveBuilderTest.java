package org.rays3d.builder;

import static org.junit.Assert.*;

import org.junit.Test;
import org.rays3d.Primitive;
import org.rays3d.builder.bxdf.PerfectSpecularBRDFBuilder;
import org.rays3d.builder.shape.SphereBuilder;
import org.rays3d.bxdf.PerfectSpecularBRDF;
import org.rays3d.shape.SphereShape;

public class PrimitiveBuilderTest {

	@Test
	public void testBuild() {

		//@formatter:off
		final Primitive primitive =
				new PrimitiveBuilder<>()
						.shape(new SphereBuilder<>()
										.radius(2d)
										.translate().dx(-3).dy(0).dz(0).end())
						.bsdf(new PerfectSpecularBRDFBuilder<>())
						.build();
		//@formatter:on

		assertNotNull("Primitive does not have an attached Shape!", primitive.getShape());
		assertNotNull("Primitive does not have an attached BSDF!", primitive.getBsdf());

		assertTrue("Primitive's attached shape is not the expected Sphere!",
				primitive.getShape() instanceof SphereShape);
		assertTrue("Primitive's attached BSDF is not the expected PerfectSpecularBRDF!",
				primitive.getBsdf() instanceof PerfectSpecularBRDF);
	}

}
