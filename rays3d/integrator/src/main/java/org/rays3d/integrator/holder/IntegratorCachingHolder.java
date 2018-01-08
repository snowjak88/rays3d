package org.rays3d.integrator.holder;

import java.util.Map;

import org.apache.camel.EndpointInject;
import org.apache.camel.ProducerTemplate;
import org.rays3d.builder.groovy.WorldBuilder;
import org.rays3d.integrator.integrators.AbstractIntegrator;
import org.rays3d.integrator.integrators.NamedIntegratorScanner;
import org.rays3d.message.IntegratorRequest;
import org.rays3d.message.WorldDescriptorRequest;
import org.rays3d.message.sample.Sample;
import org.rays3d.spectrum.Spectrum;
import org.rays3d.util.LRUCache;
import org.rays3d.world.World;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Component responsible for holding integrator instances corresponding to
 * individual render-IDs.
 * 
 * @author snowjak88
 */
@Component("integratorCachingHolder")
public class IntegratorCachingHolder {

	private static final Logger			LOG	= LoggerFactory.getLogger(IntegratorCachingHolder.class);

	@EndpointInject(uri = "activemq:rays3d.render.forID.integratorRequest")
	private ProducerTemplate			integratorRequestQueue;

	@EndpointInject(uri = "activemq:rays3d.render.forID.worldDescriptor")
	private ProducerTemplate			worldDescriptorRequestQueue;

	@Autowired
	private NamedIntegratorScanner		integratorScanner;

	private Map<Long, IntegratorEntry>	cache;

	public IntegratorCachingHolder(@Value("${org.rays3d.integrator.maximumCacheSize}") int maximumCacheSize) {

		cache = new LRUCache<>(maximumCacheSize);
	}

	/**
	 * Given an incoming {@link Sample}, check that this IntegratorCachingHolder
	 * has all that Sample's prerequisites:
	 * <ul>
	 * <li>{@link IntegratorRequest}</li>
	 * <li>{@link WorldDescriptorRequest}</li>
	 * <li>{@link World}</li>
	 * <li>{@link AbstractIntegrator} implementation</li>
	 * </ul>
	 * If any of these prerequisites are missing, this method will
	 * <strong>block</strong> until such time as those prerequisites are
	 * fulfilled.
	 * 
	 * @param sample
	 */
	public void getSamplePrerequisites(Sample sample) {

		synchronized (cache) {
			final long renderId = sample.getRenderId();

			LOG.trace("Checking prerequisites for incoming Sample (render-ID {})", renderId);

			if (!cache.containsKey(renderId)) {
				LOG.trace("Creating new cache-entry for this incoming Sample.");
				cache.putIfAbsent(sample.getRenderId(), new IntegratorEntry());
			}

			LOG.trace("Checking for cache-miss on IntegratorRequest ...");
			if (cache.get(renderId).getIntegratorRequest() == null) {
				LOG.info("Cache miss for IntegratorRequest for incoming Sample (render-ID {}). Retrieving ...",
						renderId);

				final IntegratorRequest integratorRequest = integratorRequestQueue.requestBody(renderId,
						IntegratorRequest.class);
				LOG.info("Retrieved IntegratorRequest (integrator \"{}\") for incoming Sample (render-ID {}).",
						integratorRequest.getIntegratorName(), renderId);

				LOG.trace("Storing IntegratorRequest in the cache ...");
				cache.get(renderId).setIntegratorRequest(integratorRequest);
			}

			LOG.trace("Checking for cache-miss on WorldDescriptorRequest ...");
			if (cache.get(renderId).getWorldDescriptor() == null) {
				LOG.info("Cache miss for WorldDescriptorRequest for incoming Sample (render-ID {}). Retrieving ...",
						renderId);

				final WorldDescriptorRequest worldDescriptor = worldDescriptorRequestQueue.requestBody(renderId,
						WorldDescriptorRequest.class);
				LOG.info("Retrieved WorldDescriptorRequest (ID {}) for incoming Sample (render-ID {}).",
						worldDescriptor.getId(), renderId);

				LOG.trace("Storing WorldDescriptorRequest in the cache ...");
				cache.get(renderId).setWorldDescriptor(worldDescriptor);
			}

			LOG.trace("Checking for cache-miss on World ...");
			if (cache.get(renderId).getWorld() == null) {
				LOG.info(
						"Cache miss for World for incoming Sample (render-ID {}). Inflating from WorldDescriptorRequest ...",
						renderId);

				final WorldDescriptorRequest worldDescriptor = getWorldDescriptor(renderId);
				final World world = WorldBuilder.parse(worldDescriptor.getText());
				LOG.info("Inflated World instance for incoming Sample (render-ID {}).", renderId);

				final IntegratorRequest integratorRequest = cache.get(renderId).getIntegratorRequest();

				LOG.debug("Inserting supplementary properties into inflated World ...");
				LOG.trace("Inserting film-size ({}x{})", integratorRequest.getFilmWidth(),
						integratorRequest.getFilmHeight());
				world.getCamera().setFilmSizeX(integratorRequest.getFilmWidth());
				world.getCamera().setFilmSizeY(integratorRequest.getFilmHeight());

				LOG.trace("Storing World in the cache ...");
				cache.get(renderId).setWorld(world);
			}

			LOG.trace("Checking for cache-miss on AbstractIntegrator implementation ...");
			if (cache.get(renderId).getIntegrator() == null) {

				final String integratorName = getIntegratorRequest(renderId).getIntegratorName();
				final String extraConfiguration = getIntegratorRequest(renderId).getExtraIntegratorConfig();
				final World world = getWorld(renderId);

				LOG.info(
						"Cache miss for AbstractIntegrator implementation \"{}\" for incoming Sample (render-ID {}). Instantiating ...",
						integratorName, renderId);

				final AbstractIntegrator integrator = integratorScanner.getIntegratorByRenderId(integratorName, world,
						extraConfiguration);
				LOG.info("Instantiated AbstractIntegrator \"{}\" [{}] for incoming Sample (render-ID {}).",
						integratorName, integrator.getClass().getName(), renderId);

				LOG.trace("Storing AbstractIntegrator implementation in the cache.");
				cache.get(renderId).setIntegrator(integrator);
			}

			LOG.trace("Finished checking prerequisites for incoming Sample (render-ID {}).", renderId);
		}
	}

	/**
	 * Use the cached {@link AbstractIntegrator} implementation to render the
	 * given {@link Sample} into a {@link Spectrum} (storing that result in the
	 * same Sample instance as before).
	 * 
	 * @param sample
	 */
	public void renderSample(Sample sample) {

		final long renderId = sample.getRenderId();
		LOG.trace("Rendering a sample for render-ID {} at [{},{}]", renderId, sample.getFilmPoint().getX(),
				sample.getFilmPoint().getY());

		final Spectrum result = cache.get(renderId).getIntegrator().estimateRadiance(sample);

		LOG.trace("Storing render-result in sample.");
		sample.setRadiance(result);
	}

	/**
	 * Returns <code>true</code> if this IntegratorCachingHolder already has an
	 * entry corresponding to the given render-ID.
	 * 
	 * @param renderId
	 * @return
	 */
	public boolean hasRenderId(Long renderId) {

		final boolean result;
		synchronized (cache) {
			result = cache.containsKey(renderId);
			LOG.trace("Checking to see if integrator store is mapped for render-ID = {} ({})", renderId, result);
		}
		return result;
	}

	/**
	 * Returns the {@link IntegratorRequest} associated with the given
	 * render-ID, or <code>null</code> if that IntegratorRequest is not
	 * contained by this IntegratorCachingHolder.
	 * 
	 * @param renderId
	 * @return
	 */
	public IntegratorRequest getIntegratorRequest(Long renderId) {

		final IntegratorEntry result;
		synchronized (cache) {
			result = cache.get(renderId);
		}
		return ( result == null ) ? null : result.getIntegratorRequest();
	}

	/**
	 * Returns the {@link AbstractIntegrator} associated with the given
	 * render-ID, or <code>null</code> if that AbstractIntegrator is not
	 * contained by this IntegratorCachingHolder.
	 * 
	 * @param renderId
	 * @return
	 */
	public AbstractIntegrator getIntegrator(Long renderId) {

		final IntegratorEntry result;
		synchronized (cache) {
			result = cache.get(renderId);
		}
		return ( result == null ) ? null : result.getIntegrator();
	}

	/**
	 * Returns the world-descriptor associated with the given render-ID, or
	 * <code>null</code> if that world-descriptor is not contained by this
	 * IntegratorCachingHolder.
	 * 
	 * @param renderId
	 * @return
	 */
	public WorldDescriptorRequest getWorldDescriptor(Long renderId) {

		final IntegratorEntry result;
		synchronized (cache) {
			result = cache.get(renderId);
		}
		return ( result == null ) ? null : result.getWorldDescriptor();
	}

	/**
	 * Returns the {@link World} associated with the given render-ID, or
	 * <code>null</code> if that World is not contained by this
	 * IntegratorCachingHolder.
	 * 
	 * @param renderId
	 * @return
	 */
	public World getWorld(Long renderId) {

		final IntegratorEntry result;
		synchronized (cache) {
			result = cache.get(renderId);
		}
		return ( result == null ) ? null : result.getWorld();
	}

	/**
	 * Store the given {@link IntegratorRequest} in this IntegratorCachingHolder
	 * (indexed by its configured ID), overwriting any previous
	 * IntegratorRequest stored under that ID.
	 * 
	 * @param integratorRequest
	 */
	public void put(Long renderId, IntegratorRequest integratorRequest) {

		synchronized (cache) {
			LOG.trace("Putting an IntegratorRequest (for ID {}) into the store", renderId);
			cache.putIfAbsent(renderId, new IntegratorEntry());
			cache.get(renderId).setIntegratorRequest(integratorRequest);
		}
	}

	/**
	 * Store the given {@link AbstractIntegrator} in this
	 * IntegratorCachingHolder (indexed by its configured ID), overwriting any
	 * previous AbstractIntegrator stored under that ID.
	 * 
	 * @param integrator
	 */
	public void put(Long renderId, AbstractIntegrator integrator) {

		synchronized (cache) {
			LOG.trace("Putting an integrator (for ID {}) into the store", renderId);
			cache.putIfAbsent(renderId, new IntegratorEntry());
			cache.get(renderId).setIntegrator(integrator);
		}
	}

	/**
	 * Store the given {@link WorldDescriptorRequest} in this
	 * IntegratorCachingHolder (indexed by its configured ID), overwriting any
	 * previous WorldDescriptorRequest stored under that ID.
	 * 
	 * @param worldDescriptor
	 */
	public void put(Long renderId, WorldDescriptorRequest worldDescriptor) {

		synchronized (cache) {
			LOG.trace("Putting a world-descriptor (for ID {}) into the store", renderId);
			cache.putIfAbsent(renderId, new IntegratorEntry());
			cache.get(renderId).setWorldDescriptor(worldDescriptor);
		}
	}

	/**
	 * Store the given {@link World} in this IntegratorCachingHolder (indexed by
	 * its configured ID), overwriting any previous World stored under that ID.
	 * 
	 * @param world
	 */
	public void put(Long renderId, World world) {

		synchronized (cache) {
			LOG.trace("Putting a world-descriptor (for ID {}) into the store", renderId);
			cache.putIfAbsent(renderId, new IntegratorEntry());
			cache.get(renderId).setWorld(world);
		}
	}

	private static class IntegratorEntry {

		private IntegratorRequest		integratorRequest;
		private AbstractIntegrator		integrator;
		private WorldDescriptorRequest	worldDescriptor;
		private World					world;

		public IntegratorRequest getIntegratorRequest() {

			return integratorRequest;
		}

		public void setIntegratorRequest(IntegratorRequest integratorRequest) {

			this.integratorRequest = integratorRequest;
		}

		public AbstractIntegrator getIntegrator() {

			return integrator;
		}

		public void setIntegrator(AbstractIntegrator integrator) {

			this.integrator = integrator;
		}

		public WorldDescriptorRequest getWorldDescriptor() {

			return worldDescriptor;
		}

		public void setWorldDescriptor(WorldDescriptorRequest worldDescriptor) {

			this.worldDescriptor = worldDescriptor;
		}

		public World getWorld() {

			return world;
		}

		public void setWorld(World world) {

			this.world = world;
		}
	}

}
