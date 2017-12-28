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

	/**
	 * Given a {@link Ray} (considered to be in the global reference-frame),
	 * determine if the Ray intersects with this surface and, if it does,
	 * construct the resulting {@link Interaction}. If not, return
	 * <code>null</code>.
	 * 
	 * @param ray
	 * @return
	 */
	public <T extends Interactable> Interaction<T> getInteraction(Ray ray);
}
