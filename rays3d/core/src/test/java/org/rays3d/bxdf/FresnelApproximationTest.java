package org.rays3d.bxdf;

import static org.apache.commons.math3.util.FastMath.PI;
import static org.apache.commons.math3.util.FastMath.cos;
import static org.apache.commons.math3.util.FastMath.sin;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.rays3d.geometry.Normal3D;
import org.rays3d.geometry.Vector3D;

public class FresnelApproximationTest {

	@Test
	public void testFresnelResult() {

		final Normal3D n = Normal3D.from(Vector3D.J);
		final double n1 = 1.0, n2 = 1.3;

		final Vector3D we_1 = new Vector3D(-1, +1, 0).normalize();
		final Vector3D wr_1 = new Vector3D(-we_1.getX(), +we_1.getY(), +we_1.getZ()).normalize();
		final Vector3D wt_1 = new Vector3D(cos(( 90d - 32.95 ) * PI / 180d), -sin(( 90 - 32.95 ) * PI / 180d), 0)
				.normalize();
		final double reflectance_1 = ( 0.04556 + 0.002075 ) / 2d;
		final FresnelApproximation result1 = new FresnelApproximation(we_1, n, n1, n2);

		assertEquals("Result #1-reflectance not as expected", reflectance_1, result1.getReflectance(), 0.0001);
		assertEquals("Result #1-transmittance not as expected", 1d - reflectance_1, result1.getTransmittance(), 0.0001);
		assertFalse("Result #1 should not be flagged as TIR!", result1.isTotalInternalReflection());

		assertEquals("Result #1-reflected-X not as expected", wr_1.getX(), result1.getReflectedDirection().getX(),
				0.0001);
		assertEquals("Result #1-reflected-Y not as expected", wr_1.getY(), result1.getReflectedDirection().getY(),
				0.0001);
		assertEquals("Result #1-reflected-Z not as expected", wr_1.getZ(), result1.getReflectedDirection().getZ(),
				0.0001);

		assertEquals("Result #1-transmitted-X not as expected", wt_1.getX(), result1.getTransmittedDirection().getX(),
				0.0001);
		assertEquals("Result #1-transmitted-Y not as expected", wt_1.getY(), result1.getTransmittedDirection().getY(),
				0.0001);
		assertEquals("Result #1-transmitted-Z not as expected", wt_1.getZ(), result1.getTransmittedDirection().getZ(),
				0.0001);

		final Vector3D we_2 = new Vector3D(-cos(( 75d ) * PI / 180d), sin(( 75d ) * PI / 180d), 0).normalize();
		final Vector3D wr_2 = new Vector3D(-we_2.getX(), +we_2.getY(), +we_2.getZ()).normalize();
		final FresnelApproximation result2 = new FresnelApproximation(we_2, n, 5, 1);

		assertEquals("Result #2-reflectance not as expected", 1d, result2.getReflectance(), 0.00001);
		assertEquals("Result #2-transmittance not as expected", 0d, result2.getTransmittance(), 0.00001);
		assertTrue("Result #2 should be flagged as TIR!", result2.isTotalInternalReflection());

		assertEquals("Result #2-reflected-X not as expected", wr_2.getX(), result2.getReflectedDirection().getX(),
				0.0001);
		assertEquals("Result #2-reflected-Y not as expected", wr_2.getY(), result2.getReflectedDirection().getY(),
				0.0001);
		assertEquals("Result #2-reflected-Z not as expected", wr_2.getZ(), result2.getReflectedDirection().getZ(),
				0.0001);
	}

}
