package org.rays3d.shape;

import static org.junit.Assert.assertEquals;

import java.util.Collections;

import org.junit.Before;
import org.junit.Test;
import org.rays3d.geometry.Point2D;
import org.rays3d.geometry.Point3D;
import org.rays3d.geometry.Ray;
import org.rays3d.geometry.Vector3D;
import org.rays3d.interact.SurfaceDescriptor;
import org.rays3d.message.sample.Sample;
import org.rays3d.transform.RotationTransform;
import org.rays3d.transform.TranslationTransform;

public class ShapeTest {

	private Shape shape;

	@Before
	public void setUp() {

		shape = new Shape(Collections.emptyList()) {

			@SuppressWarnings("unchecked")
			@Override
			public SurfaceDescriptor<Shape> getSurfaceNearestTo(Point3D point) {

				// We don't care about this method for the purposes of this
				// test.
				return null;
			}

			@SuppressWarnings("unchecked")
			@Override
			public SurfaceDescriptor<Shape> sampleSurface(Sample sample) {

				// We don't care about this method for the purposes of this
				// test.
				return null;
			}

			@SuppressWarnings("unchecked")
			@Override
			public SurfaceDescriptor<Shape> sampleSurfaceFacing(Point3D neighbor, Sample sample) {

				// We don't care about this method for the purposes of this
				// test.
				return null;
			}

			@Override
			public double computeSolidAngle(Point3D viewedFrom) {

				// We don't care about this method for the purposes of this
				// test.
				return 0;
			}

			@SuppressWarnings("unchecked")
			@Override
			public SurfaceDescriptor<Shape> getSurface(Ray ray) {

				// We don't care about this method for the purposes of this
				// test.
				return null;
			}

			@Override
			public boolean isIntersectableWith(Ray ray) {

				// We don't care about this method for the purposes of this
				// test.
				return false;
			}

			@Override
			public Point2D getParamFromLocalSurface(Point3D point) {

				// We don't care about this method for the purposes of this
				// test.
				return null;
			}
		};
	}

	@Test
	public void testAppendTransform() {

		shape.appendTransform(new TranslationTransform(3d, 0d, 0d));
		shape.appendTransform(new RotationTransform(Vector3D.J, 90d));

		//
		// Therefore, a point located at (1,0,0) relative to the object should
		// be located at (3,0,1) relative to the world-origin.
		//
		Point3D relativePoint = new Point3D(1, 0, 0);
		Point3D absolutePoint = shape.localToWorld(relativePoint);

		assertEquals("Absolute X is not as expected!", 3d, absolutePoint.getX(), 0.00001);
		assertEquals("Absolute Y is not as expected!", 0d, absolutePoint.getY(), 0.00001);
		assertEquals("Absolute Z is not as expected!", -1d, absolutePoint.getZ(), 0.00001);
	}

}
