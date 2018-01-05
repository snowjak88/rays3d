package org.rays3d.javabuilder;

import java.util.Collection;
import java.util.LinkedList;
import java.util.stream.Collectors;

import org.rays3d.Primitive;
import org.rays3d.camera.Camera;
import org.rays3d.javabuilder.camera.CameraBuilder;
import org.rays3d.world.World;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WorldBuilder<P extends AbstractBuilder<?, ?>> implements AbstractBuilder<World, P> {

	private final static Logger														LOG			= LoggerFactory
			.getLogger(WorldBuilder.class);

	private Collection<PrimitiveBuilder<WorldBuilder<P>>>							primitives	= new LinkedList<>();

	private CameraBuilder<? extends Camera, ? extends CameraBuilder<?, ?, ?>, ?>	cameraBuilder;

	private P																		parentBuilder;

	public WorldBuilder() {
		this(null);
	}

	public WorldBuilder(P parentBuilder) {
		this.parentBuilder = parentBuilder;
	}

	/**
	 * Configure a {@link Primitive} in this {@link World}.
	 * 
	 * @return
	 */
	public PrimitiveBuilder<WorldBuilder<P>> primitive() {

		final PrimitiveBuilder<WorldBuilder<P>> builder = new PrimitiveBuilder<>(this);
		primitives.add(builder);
		return builder;
	}

	/**
	 * Configure the {@link Camera} instance to use in this {@link World}.
	 * 
	 * @param cameraBuilder
	 * @return
	 */
	public WorldBuilder<P> camera(CameraBuilder<? extends Camera, ? extends CameraBuilder<?, ?, ?>, ?> cameraBuilder) {

		this.cameraBuilder = cameraBuilder;
		return this;
	}

	@Override
	public World build() throws BuilderException {

		try {

			if (cameraBuilder == null)
				throw new BuilderException("Cannot build a World without any configured Camera!");

			if (primitives.isEmpty())
				LOG.warn("Building a World without any configured Primitives!");

			final World world = new World();

			world.setCamera(cameraBuilder.build());
			world.getPrimitives().addAll(
					primitives.stream().map(pb -> pb.build()).collect(Collectors.toCollection(LinkedList::new)));

			return world;

		} catch (BuilderException e) {
			LOG.error("Encountered exception while building a World.", e);
			throw e;
		}
	}

	@Override
	public P end() {

		return parentBuilder;
	}
}
