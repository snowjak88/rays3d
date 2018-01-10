package org.rays3d.shape;

import static org.apache.commons.math3.util.FastMath.PI;
import static org.apache.commons.math3.util.FastMath.signum;

import java.util.List;

import org.rays3d.Global;
import org.rays3d.geometry.Normal3D;
import org.rays3d.geometry.Point2D;
import org.rays3d.geometry.Point3D;
import org.rays3d.geometry.Ray;
import org.rays3d.geometry.Vector3D;
import org.rays3d.interact.SurfaceDescriptor;
import org.rays3d.message.sample.Sample;
import org.rays3d.transform.Transform;

/**
 * Represents a plane -- a flat surface of 0 thickness and infinite extent.
 * Absent any {@link Transform}s, this plane's surface-normal is oriented toward
 * {@link Vector3D#J}.
 * 
 * @author snowjak88
 */
public class PlaneShape extends Shape {

	private Normal3D localNormal = Normal3D.from(Vector3D.J);

	public PlaneShape(Transform... worldToLocal) {
		super(worldToLocal);
	}

	public PlaneShape(List<Transform> worldToLocal) {
		super(worldToLocal);
	}

	@Override
	public boolean isIntersectableWith(Ray ray) {

		//
		// The only way this Ray will fail to interact with this Plane anywhere
		// is if its origin y-coordinate and direction y-coordinate are of the
		// same sign (i.e., it is pointing away from the plane), or else if its
		// direction y-coordinate is 0 (i.e., it is pointing parallel to the
		// plane).
		//
		final Ray localRay = worldToLocal(ray);
		final double rayOriginY = localRay.getOrigin().getY();
		final double rayDirectionY = localRay.getDirection().getY();

		return !( Global.isNear(signum(rayOriginY), rayDirectionY) ) && !( Global.isNear(rayDirectionY, 0d) );
	}

	@SuppressWarnings("unchecked")
	@Override
	public SurfaceDescriptor<PlaneShape> getSurface(Ray ray) {

		final Ray localRay = worldToLocal(ray);

		final double t = -localRay.getOrigin().getY() / localRay.getDirection().getY();
		if (t < Global.DOUBLE_TOLERANCE || Double.isNaN(t) || Global.isNear(t, 0d))
			return null;

		Ray intersectingRay = new Ray(localRay.getOrigin(), localRay.getDirection(), t, localRay.getDepth(), t, t);
		Point3D intersectionPoint = intersectingRay.getPointAlong();
		Point2D surfaceParam = getParamFromLocalSurface(intersectionPoint);

		return localToWorld(new SurfaceDescriptor<PlaneShape>(this, intersectionPoint, localNormal, surfaceParam));
	}

	@SuppressWarnings("unchecked")
	@Override
	public SurfaceDescriptor<PlaneShape> getSurfaceNearestTo(Point3D neighbor) {

		Point3D localNeighbor = worldToLocal(neighbor);
		Point3D surfacePoint = new Point3D(localNeighbor.getX(), 0.0, localNeighbor.getZ());
		return localToWorld(new SurfaceDescriptor<PlaneShape>(this, surfacePoint, localNormal,
				getParamFromLocalSurface(surfacePoint)));
	}

	@SuppressWarnings("unchecked")
	@Override
	public SurfaceDescriptor<PlaneShape> sampleSurface(Sample sample) {

		final Point2D samplePoint = sample.getAdditional2DSample();
		final double x = ( samplePoint.getX() - 0.5 ) * Double.MAX_VALUE;
		final double y = 0d;
		final double z = ( samplePoint.getY() - 0.5 ) * Double.MAX_VALUE;

		final Point3D surfacePoint = new Point3D(x, y, z);

		return localToWorld(new SurfaceDescriptor<PlaneShape>(this, surfacePoint, localNormal,
				getParamFromLocalSurface(surfacePoint)));
	}

	@SuppressWarnings("unchecked")
	@Override
	public SurfaceDescriptor<PlaneShape> sampleSurfaceFacing(Point3D neighbor, Sample sample) {

		return sampleSurface(sample);
	}

	@Override
	public double computeSolidAngle(Point3D viewedFrom) {

		return 2d * PI;
	}

	@Override
	public Point2D getParamFromLocalSurface(Point3D point) {

		return new Point2D(point.getX(), point.getZ());
	}

}
