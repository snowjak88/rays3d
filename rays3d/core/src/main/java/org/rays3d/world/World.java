package org.rays3d.world;

import java.util.Collection;
import java.util.HashSet;

import org.rays3d.camera.Camera;
import org.rays3d.interact.Interactable;
import org.rays3d.message.WorldDescriptorRequest;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * A World holds everything that a render is about -- the objects and the Camera
 * associated with a render.
 * <p>
 * <strong>Note</strong> that a World is distinct from a "world descriptor"
 * (c.f. {@link WorldDescriptorRequest}). A "world descriptor" is responsible
 * for storing the textual equivalent of a World instance.
 * </p>
 * 
 * @author snowjak88
 */
public class World {

	@JsonProperty
	private Collection<Interactable>	interactables	= new HashSet<>();

	@JsonProperty
	private Camera						camera;

	public Collection<Interactable> getInteractables() {

		return interactables;
	}

	protected void setInteractables(Collection<Interactable> interactables) {

		this.interactables = interactables;
	}

	public Camera getCamera() {

		return camera;
	}

	public void setCamera(Camera camera) {

		this.camera = camera;
	}

}
