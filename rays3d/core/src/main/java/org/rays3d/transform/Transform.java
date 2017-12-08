package org.rays3d.transform;

import org.rays3d.geometry.Normal3D;
import org.rays3d.geometry.Point3D;
import org.rays3d.geometry.Ray;
import org.rays3d.geometry.Vector3D;
import org.rays3d.geometry.util.Matrix;

/**
 * Represents a single transformation in 3-space.
 * 
 * @author snowjak88
 */
public interface Transform {

	/**
	 * Transform the given Point into local coordinates.
	 * 
	 * @param point
	 * @return
	 */
	public Point3D worldToLocal(Point3D point);

	/**
	 * Transform the given Point into world coordinates.
	 * 
	 * @param point
	 * @return
	 */
	public Point3D localToWorld(Point3D point);

	/**
	 * Transform the given Vector into local coordinates.
	 * 
	 * @param vector
	 * @return the transformed Vector
	 */
	public Vector3D worldToLocal(Vector3D vector);

	/**
	 * Transform the given Vector into world coordinates.
	 * 
	 * @param vector
	 * @return the transformed Vector
	 */
	public Vector3D localToWorld(Vector3D vector);

	/**
	 * Transform the given Ray into local coordinates.
	 * 
	 * @param ray
	 * @return the transformed Ray
	 */
	public Ray worldToLocal(Ray ray);

	/**
	 * Transform the given Ray into world coordinates.
	 * 
	 * @param ray
	 * @return the transformed Ray
	 */
	public Ray localToWorld(Ray ray);

	/**
	 * Transform the given Normal into local coordinates.
	 * 
	 * @param normal
	 * @return the transformed Normal
	 */
	public Normal3D worldToLocal(Normal3D normal);

	/**
	 * Transform the given Normal into world coordinates.
	 * 
	 * @param normal
	 * @return the transformed Normal
	 */
	public Normal3D localToWorld(Normal3D normal);

	/**
	 * Return the Matrix implementing the world-to-local form of this Transform.
	 * 
	 * @return
	 */
	public Matrix getWorldToLocal();

	/**
	 * Return the Matrix implementing the local-to-world form of this Transform.
	 * 
	 * @return
	 */
	public Matrix getLocalToWorld();

}
