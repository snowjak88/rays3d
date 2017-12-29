package org.rays3d.transform;

import org.rays3d.geometry.Normal3D;
import org.rays3d.geometry.Point3D;
import org.rays3d.geometry.Ray;
import org.rays3d.geometry.Vector3D;
import org.rays3d.geometry.util.Matrix;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Represents a translating Transform in 3-space.
 * 
 * @author snowjak88
 */
public class TranslationTransform implements Transform {

	@JsonProperty
	private double	dx;
	@JsonProperty
	private double	dy;
	@JsonProperty
	private double	dz;

	@JsonIgnore
	private Matrix	worldToLocal;
	@JsonIgnore
	private Matrix	localToWorld;

	/**
	 * Create a new TranslationTransform, with the specified
	 * <strong>world-to-local</strong> translation terms.
	 * 
	 * @param dx
	 * @param dy
	 * @param dz
	 */
	public TranslationTransform(double dx, double dy, double dz) {

		this.dx = dx;
		this.dy = dy;
		this.dz = dz;
	}

	private void initializeMatrices() {

		//@formatter:off
		this.worldToLocal = new Matrix(new double[][] {	{ 1d, 0d, 0d, -dx },
														{ 0d, 1d, 0d, -dy },
														{ 0d, 0d, 1d, -dz },
														{ 0d, 0d, 0d,  1d } });
		this.localToWorld = new Matrix(new double[][] {	{ 1d, 0d, 0d, +dx },
														{ 0d, 1d, 0d, +dy },
														{ 0d, 0d, 1d, +dz },
														{ 0d, 0d, 0d,  1d } });
		//@formatter:on
	}

	@Override
	public Point3D worldToLocal(Point3D point) {

		if (worldToLocal == null)
			initializeMatrices();

		return new Point3D(apply(worldToLocal, point.getX(), point.getY(), point.getZ(), 1d));
	}

	@Override
	public Point3D localToWorld(Point3D point) {

		if (localToWorld == null)
			initializeMatrices();

		return new Point3D(apply(localToWorld, point.getX(), point.getY(), point.getZ(), 1d));
	}

	/**
	 * As a rule, vectors are considered to be unaffected by translations.
	 */
	@Override
	public Vector3D worldToLocal(Vector3D vector) {

		return vector;
	}

	/**
	 * As a rule, vectors are considered to be unaffected by translations.
	 */
	@Override
	public Vector3D localToWorld(Vector3D vector) {

		return vector;
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

	/**
	 * As a rule, normals are considered to be unaffected by translations.
	 */
	@Override
	public Normal3D worldToLocal(Normal3D normal) {

		return normal;
	}

	/**
	 * As a rule, normals are considered to be unaffected by translations.
	 */
	@Override
	public Normal3D localToWorld(Normal3D normal) {

		return normal;
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
