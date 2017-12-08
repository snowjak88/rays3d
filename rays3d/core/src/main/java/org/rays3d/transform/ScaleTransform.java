package org.rays3d.transform;

import org.rays3d.geometry.Normal3D;
import org.rays3d.geometry.Point3D;
import org.rays3d.geometry.Ray;
import org.rays3d.geometry.Vector3D;
import org.rays3d.geometry.util.Matrix;

/**
 * Represents a scaling Transform in 3-space.
 * 
 * @author snowjak88
 */
public class ScaleTransform implements Transform {

	private Matrix	worldToLocal	= null, worldToLocal_inverseTranspose = null;
	private Matrix	localToWorld	= null, localToWorld_inverseTranspose = null;

	/**
	 * Create a new ScaleTransform, with the specified
	 * <strong>world-to-local</strong> scaling terms.
	 * 
	 * @param sx
	 * @param sy
	 * @param sz
	 */
	public ScaleTransform(double sx, double sy, double sz) {

		//@formatter:off
		this.localToWorld = new Matrix(new double[][] {	{ sx,    0d,    0d,    0d },
														{ 0d,    sy,    0d,    0d },
														{ 0d,    0d,    sz,    0d },
														{ 0d,    0d,    0d,    1d } });
		this.worldToLocal = new Matrix(new double[][] {	{ 1d/sx, 0d,    0d,    0d },
														{ 0d,    1d/sy, 0d,    0d },
														{ 0d,    0d,    1d/sz, 0d },
														{ 0d,    0d,    0d,    1d } });
		//@formatter:on
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

		return new Ray(worldToLocal(ray.getOrigin()), worldToLocal(ray.getDirection()), ray.getDepth(),
				ray.getWindowMinT(), ray.getWindowMaxT());
	}

	@Override
	public Ray localToWorld(Ray ray) {

		return new Ray(localToWorld(ray.getOrigin()), localToWorld(ray.getDirection()), ray.getDepth(),
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
