package org.rays3d.transform;

import org.apache.commons.math3.util.FastMath;
import org.rays3d.geometry.Normal3D;
import org.rays3d.geometry.Point3D;
import org.rays3d.geometry.Ray;
import org.rays3d.geometry.Vector3D;
import org.rays3d.geometry.util.Matrix;

/**
 * Represent a rotating Transform in 3-space -- specifically, a measure of
 * degrees of rotation about an arbitrary axis-vector.
 * 
 * @author snowjak88
 */
public class RotationTransform implements Transform {

	private Matrix	worldToLocal	= null, worldToLocal_inverseTranspose = null;
	private Matrix	localToWorld	= null, localToWorld_inverseTranspose = null;

	/**
	 * Construct a new RotationTransform, representing a rotation about the
	 * specified axis-vector by the specified number of degrees.
	 * 
	 * @param axis
	 * @param degreesOfRotation
	 */

	public RotationTransform(Vector3D axis, double degreesOfRotation) {

		axis = axis.normalize();

		final double radians = degreesOfRotation * FastMath.PI / 180d;
		final double ax = axis.getX(), ay = axis.getY(), az = axis.getZ();
		final double ax2 = ( ax * ax ), ay2 = ( ay * ay ), az2 = ( az * az );
		final double cos = FastMath.cos(radians), sin = FastMath.sin(radians);

		//@formatter:off
		this.localToWorld = new Matrix(new double[][] {	{ cos + ax2*(1d - cos),     ax*ay*(1d - cos) - az*sin, ax*az*(1d - cos) + ay*sin, 0d },
														{ ay*ax*(1 - cos) + az*sin, cos + ay2*(1d - cos),      ay*az*(1d - cos) - ax*sin, 0d },
														{ az*ax*(1 - cos) - ay*sin, az*ay*(1d - cos) + ax*sin, cos + az2*(1d - cos),      0d },
														{ 0d,                       0d,                        0d,                        1d } });
		//@formatter:on
		this.worldToLocal = this.localToWorld.transpose();
	}

	@Override
	public Point3D worldToLocal(Point3D point) {

		return new Point3D(apply(worldToLocal, point.getX(), point.getY(), point.getZ(), 1d));
	}

	@Override
	public Point3D localToWorld(Point3D point) {

		return new Point3D(apply(localToWorld, point.getX(), point.getY(), point.getZ(), 1d));
	}

	@Override
	public Vector3D worldToLocal(Vector3D vector) {

		return new Vector3D(apply(worldToLocal, vector.getX(), vector.getY(), vector.getZ(), 1d));
	}

	@Override
	public Vector3D localToWorld(Vector3D vector) {

		return new Vector3D(apply(localToWorld, vector.getX(), vector.getY(), vector.getZ(), 1d));
	}

	@Override
	public Ray worldToLocal(Ray ray) {

		return new Ray(worldToLocal(ray.getOrigin()), worldToLocal(ray.getDirection()), ray.getT(), ray.getDepth(),
				ray.getWindowMinT(), ray.getWindowMaxT());
	}

	@Override
	public Ray localToWorld(Ray ray) {

		return new Ray(localToWorld(ray.getOrigin()), localToWorld(ray.getDirection()), ray.getT(), ray.getDepth(),
				ray.getWindowMinT(), ray.getWindowMaxT());
	}

	@Override
	public Normal3D worldToLocal(Normal3D normal) {

		if (worldToLocal_inverseTranspose == null)
			worldToLocal_inverseTranspose = worldToLocal.inverse().transpose();

		return new Normal3D(apply(worldToLocal_inverseTranspose, normal.getX(), normal.getY(), normal.getZ(), 1d));
	}

	@Override
	public Normal3D localToWorld(Normal3D normal) {

		if (localToWorld_inverseTranspose == null)
			localToWorld_inverseTranspose = localToWorld.inverse().transpose();

		return new Normal3D(apply(localToWorld_inverseTranspose, normal.getX(), normal.getY(), normal.getZ(), 1d));
	}

	private double[] apply(Matrix matrix, double... coordinates) {

		return matrix.multiply(coordinates);
	}

	@Override
	public Matrix getWorldToLocal() {

		return worldToLocal;
	}

	@Override
	public Matrix getLocalToWorld() {

		return localToWorld;
	}

}
