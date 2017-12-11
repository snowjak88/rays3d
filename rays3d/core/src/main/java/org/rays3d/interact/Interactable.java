package org.rays3d.interact;

import org.rays3d.geometry.Ray;
import org.rays3d.transform.Transformable;

/**
 * Denotes that an object may be a source of {@link Interaction}s with
 * {@link Ray}s
 * 
 * @author snowjak88
 */
public interface Interactable extends Transformable, DescribesSurface {

	public boolean isInteractable(Ray ray);

	public boolean isLocalInteractable(Ray localRay);

	public <T extends Interactable> Interaction<T> getInteraction(Ray ray);

	public <T extends Interactable> Interaction<T> getLocalInteraction(Ray localRay);
}
