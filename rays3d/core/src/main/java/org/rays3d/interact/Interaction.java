package org.rays3d.interact;

import org.rays3d.geometry.Normal3D;
import org.rays3d.geometry.Point2D;
import org.rays3d.geometry.Point3D;
import org.rays3d.geometry.Ray;

/**
 * An Interaction defines how and where a {@link Ray} interacts with a
 * {@link Primitive}.
 * 
 * @author snowjak88
 */
public class Interaction<T extends Interactable> extends SurfaceDescriptor<T> {

	private final Ray interactingRay;

	/**
	 * Create a new Interaction at the defined <code>point</code>, with the
	 * specified surface-<code>normal</code> and surface-parameterization. This
	 * Interaction is defined as taking place along the given
	 * <code>interactingRay</code> between <code>mintT</code> and
	 * <code>maxT</code>.
	 * 
	 * @param point
	 * @param normal
	 * @param param
	 */
	public Interaction(T interacted, Ray interactingRay, Point3D point, Normal3D normal, Point2D param) {

		super(interacted, point, normal, param);

		this.interactingRay = interactingRay;
	}

	/**
	 * Alias for {@link SurfaceDescriptor#getDescribed()}
	 * 
	 * @return the object which is being interacted with
	 */
	public T getInteracted() {

		return getDescribed();
	}

	/**
	 * @return the {@link Ray} which produced this {@link Interaction}
	 */
	public Ray getInteractingRay() {

		return interactingRay;
	}

}
