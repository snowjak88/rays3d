package org.rays3d.transform;

import org.rays3d.geometry.Normal3D;
import org.rays3d.geometry.Point3D;
import org.rays3d.geometry.Ray;
import org.rays3d.geometry.Vector3D;
import org.rays3d.geometry.util.Matrix;
import org.rays3d.interact.DescribesSurface;
import org.rays3d.interact.Interactable;
import org.rays3d.interact.Interaction;
import org.rays3d.interact.SurfaceDescriptor;

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
	 * Transform the given Interaction into local coordinates.
	 * 
	 * @param surface
	 * @return the transformed Normal
	 */
	public default <T extends DescribesSurface> SurfaceDescriptor<T> worldToLocal(SurfaceDescriptor<T> surface) {

		return new SurfaceDescriptor<T>(surface.getDescribed(), worldToLocal(surface.getPoint()),
				worldToLocal(surface.getNormal()), surface.getParam());
	}

	/**
	 * Transform the given Interaction into world coordinates.
	 * 
	 * @param surface
	 * @return the transformed Normal
	 */
	public default <T extends DescribesSurface> SurfaceDescriptor<T> localToWorld(SurfaceDescriptor<T> surface) {

		return new SurfaceDescriptor<T>(surface.getDescribed(), localToWorld(surface.getPoint()),
				localToWorld(surface.getNormal()), surface.getParam());
	}

	/**
	 * Transform the given Interaction into local coordinates.
	 * 
	 * @param interaction
	 * @return the transformed Normal
	 */
	public default <T extends Interactable> Interaction<T> worldToLocal(Interaction<T> interaction) {

		return new Interaction<T>(interaction.getInteracted(), worldToLocal(interaction.getInteractingRay()),
				worldToLocal(interaction.getPoint()), worldToLocal(interaction.getNormal()), interaction.getParam());
	}

	/**
	 * Transform the given Interaction into world coordinates.
	 * 
	 * @param interaction
	 * @return the transformed Normal
	 */
	public default <T extends Interactable> Interaction<T> localToWorld(Interaction<T> interaction) {

		return new Interaction<T>(interaction.getInteracted(), localToWorld(interaction.getInteractingRay()),
				localToWorld(interaction.getPoint()), localToWorld(interaction.getNormal()), interaction.getParam());
	}

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
