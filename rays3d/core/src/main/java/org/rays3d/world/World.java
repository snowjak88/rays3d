package org.rays3d.world;

import java.util.Collection;
import java.util.LinkedList;
import java.util.Optional;
import java.util.stream.Collectors;

import org.rays3d.Primitive;
import org.rays3d.camera.Camera;
import org.rays3d.geometry.Ray;
import org.rays3d.interact.Interaction;
import org.rays3d.message.WorldDescriptorMessage;
import org.rays3d.util.FlaggingCollectionDecorator;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * A World holds everything that a render is about -- the objects and the Camera
 * associated with a render.
 * <p>
 * <strong>Note</strong> that a World is distinct from a "world descriptor"
 * (c.f. {@link WorldDescriptorMessage}). A "world descriptor" is responsible
 * for storing the textual equivalent of a World instance.
 * </p>
 * 
 * @author snowjak88
 */
public class World {

	@JsonProperty
	private FlaggingCollectionDecorator<Primitive>	primitives	= new FlaggingCollectionDecorator<>(LinkedList::new);

	@JsonIgnore
	private Collection<Primitive>					emissives	= null;

	@JsonProperty
	private Camera									camera;

	public Collection<Primitive> getPrimitives() {

		return primitives;
	}

	protected void setPrimitives(Collection<Primitive> primitives) {

		this.primitives.clear();
		this.primitives.addAll(primitives);
	}

	public Camera getCamera() {

		return camera;
	}

	public void setCamera(Camera camera) {

		this.camera = camera;
	}

	public Collection<Primitive> getEmissives() {

		if (emissives == null || primitives.readAndReset())
			emissives = primitives.parallelStream().filter(p -> p.getBsdf().isEmissive()).collect(
					Collectors.toCollection(LinkedList::new));

		return emissives;
	}

	public Optional<Interaction<Primitive>> getClosestInteraction(Ray ray) {

		return getPrimitives()
				.parallelStream()
					.filter(p -> p.isIntersectableWith(ray))
					.map(p -> p.getInteraction(ray))
					.filter(i -> i != null)
					.sorted((i1, i2) -> Double.compare(i1.getInteractingRay().getT(), i2.getInteractingRay().getT()))
					.findFirst();
	}

}
