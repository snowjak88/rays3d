package org.rays3d.integrator.holder;

import java.util.Map;

import org.apache.camel.EndpointInject;
import org.apache.camel.ProducerTemplate;
import org.rays3d.builder.groovy.WorldBuilder;
import org.rays3d.integrator.integrators.AbstractIntegrator;
import org.rays3d.integrator.integrators.NamedIntegratorScanner;
import org.rays3d.message.IntegratorDescriptorMessage;
import org.rays3d.message.WorldDescriptorMessage;
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
	 * Given an incoming {@link IntegratorDescriptorMessage}, initialize a new
	 * {@link IntegratorEntry} in the cache.
	 * 
	 * @param request
	 */
	public void initializeIntegrator(IntegratorDescriptorMessage request) {

		synchronized (cache) {
			final long renderId = request.getRenderId();

			LOG.debug("Initializing a cache-entry for incoming IntegratorDescriptorMessage (render-ID {})", renderId);

			final IntegratorEntry entry = new IntegratorEntry();

			LOG.trace("Setting cache.IntegratorRequest ...");
			entry.setIntegratorRequest(request);

			LOG.trace("Retrieving WorldDescriptorMessage for cache ...");
			entry.setWorldDescriptor(retrieveWorldDescriptor(renderId));

			LOG.trace("Inflating World from WorldDescriptorMessage & IntegratorDescriptorMessage for cache ...");
			entry.setWorld(inflateWorld(entry.getWorldDescriptor(), entry.getIntegratorRequest()));

			LOG.trace("Instantiating AbstractIntegrator instance from IntegratorDescriptorMessage & World for cache ...");
			entry.setIntegrator(getIntegratorInstance(entry.getIntegratorRequest(), entry.getWorld()));

			cache.put(renderId, entry);
		}
	}

	/**
	 * Given an incoming {@link Sample}, check that this IntegratorCachingHolder
	 * has all that Sample's prerequisites:
	 * <ul>
	 * <li>{@link IntegratorDescriptorMessage}</li>
	 * <li>{@link WorldDescriptorMessage}</li>
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
				cache.putIfAbsent(renderId, new IntegratorEntry());
			}

			LOG.trace("Checking for cache-miss on IntegratorDescriptorMessage ...");
			if (cache.get(renderId).getIntegratorRequest() == null) {
				LOG.info("Cache miss for IntegratorDescriptorMessage for incoming Sample (render-ID {}). Retrieving ...",
						renderId);

				final IntegratorDescriptorMessage integratorDescriptorMessage = integratorRequestQueue.requestBody(renderId,
						IntegratorDescriptorMessage.class);
				LOG.info("Retrieved IntegratorDescriptorMessage (integrator \"{}\") for incoming Sample (render-ID {}).",
						integratorDescriptorMessage.getIntegratorName(), renderId);

				LOG.trace("Storing IntegratorDescriptorMessage in the cache ...");
				cache.get(renderId).setIntegratorRequest(integratorDescriptorMessage);
			}

			LOG.trace("Checking for cache-miss on WorldDescriptorMessage ...");
			if (cache.get(renderId).getWorldDescriptor() == null) {
				LOG.info("Cache miss for WorldDescriptorMessage for incoming Sample (render-ID {}). Retrieving ...",
						renderId);

				LOG.trace("Storing WorldDescriptorMessage in the cache ...");
				cache.get(renderId).setWorldDescriptor(retrieveWorldDescriptor(renderId));
			}

			LOG.trace("Checking for cache-miss on World ...");
			if (cache.get(renderId).getWorld() == null) {
				LOG.info(
						"Cache miss for World for incoming Sample (render-ID {}). Inflating from WorldDescriptorMessage & IntegratorDescriptorMessage ...",
						renderId);

				LOG.trace("Storing World in the cache ...");
				cache.get(renderId).setWorld(inflateWorld(cache.get(renderId).getWorldDescriptor(),
						cache.get(renderId).getIntegratorRequest()));
			}

			LOG.trace("Checking for cache-miss on AbstractIntegrator implementation ...");
			if (cache.get(renderId).getIntegrator() == null) {

				final String integratorName = getIntegratorRequest(renderId).getIntegratorName();

				LOG.info(
						"Cache miss for AbstractIntegrator implementation \"{}\" for incoming Sample (render-ID {}). Instantiating ...",
						integratorName, renderId);

				final AbstractIntegrator integrator = getIntegratorInstance(cache.get(renderId).getIntegratorRequest(),
						cache.get(renderId).getWorld());

				LOG.trace("Storing AbstractIntegrator implementation in the cache.");
				cache.get(renderId).setIntegrator(integrator);
			}

			LOG.trace("Finished checking prerequisites for incoming Sample (render-ID {}).", renderId);
		}
	}

	private AbstractIntegrator getIntegratorInstance(IntegratorDescriptorMessage integratorDescriptorMessage, World world) {

		final AbstractIntegrator integrator = integratorScanner.getIntegratorByRenderId(
				integratorDescriptorMessage.getIntegratorName(), world, integratorDescriptorMessage.getExtraIntegratorConfig());
		LOG.info("Instantiated AbstractIntegrator \"{}\" [{}].", integratorDescriptorMessage.getIntegratorName(),
				integrator.getClass().getName());

		return integrator;
	}

	private World inflateWorld(WorldDescriptorMessage worldDescriptor, IntegratorDescriptorMessage integratorDescriptorMessage) {

		final World world = WorldBuilder.parse(worldDescriptor.getText());
		LOG.debug("Inflated World from WorldDescriptorMessage.text");

		LOG.debug("Inserting supplementary properties into inflated World ...");
		LOG.trace("Inserting film-size ({}x{})", integratorDescriptorMessage.getFilmWidth(), integratorDescriptorMessage.getFilmHeight());
		world.getCamera().setFilmSizeX(integratorDescriptorMessage.getFilmWidth());
		world.getCamera().setFilmSizeY(integratorDescriptorMessage.getFilmHeight());

		LOG.info("Inflated World instance for WorldDescriptorMessage (ID {}).", worldDescriptor.getId());

		return world;
	}

	private WorldDescriptorMessage retrieveWorldDescriptor(long renderId) {

		final WorldDescriptorMessage worldDescriptor = worldDescriptorRequestQueue.requestBody(renderId,
				WorldDescriptorMessage.class);
		LOG.info("Retrieved WorldDescriptorMessage (ID {}) for incoming Sample (render-ID {}).",
				worldDescriptor.getId(), renderId);

		return worldDescriptor;
	}

	/**
	 * Use the cached {@link AbstractIntegrator} implementation to render the
	 * given {@link Sample} into a {@link Spectrum} (storing that result in the
	 * same Sample instance as before).
	 * 
	 * @param sample
	 */
	public Sample renderSample(Sample sample) {

		final long renderId = sample.getRenderId();
		LOG.trace("Rendering a sample for render-ID {} at [{},{}]", renderId, sample.getFilmPoint().getX(),
				sample.getFilmPoint().getY());

		final Spectrum result = cache.get(renderId).getIntegrator().estimateRadiance(sample);

		LOG.trace("Storing render-result in sample.");
		sample.setRadiance(result);

		return sample;
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
	 * Returns the {@link IntegratorDescriptorMessage} associated with the given
	 * render-ID, or <code>null</code> if that IntegratorDescriptorMessage is not
	 * contained by this IntegratorCachingHolder.
	 * 
	 * @param renderId
	 * @return
	 */
	public IntegratorDescriptorMessage getIntegratorRequest(Long renderId) {

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
	public WorldDescriptorMessage getWorldDescriptor(Long renderId) {

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
	 * Store the given {@link IntegratorDescriptorMessage} in this IntegratorCachingHolder
	 * (indexed by its configured ID), overwriting any previous
	 * IntegratorDescriptorMessage stored under that ID.
	 * 
	 * @param integratorDescriptorMessage
	 */
	public void put(Long renderId, IntegratorDescriptorMessage integratorDescriptorMessage) {

		synchronized (cache) {
			LOG.trace("Putting an IntegratorDescriptorMessage (for ID {}) into the store", renderId);
			cache.putIfAbsent(renderId, new IntegratorEntry());
			cache.get(renderId).setIntegratorRequest(integratorDescriptorMessage);
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
	 * Store the given {@link WorldDescriptorMessage} in this
	 * IntegratorCachingHolder (indexed by its configured ID), overwriting any
	 * previous WorldDescriptorMessage stored under that ID.
	 * 
	 * @param worldDescriptor
	 */
	public void put(Long renderId, WorldDescriptorMessage worldDescriptor) {

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

		private IntegratorDescriptorMessage		integratorDescriptorMessage;
		private AbstractIntegrator		integrator;
		private WorldDescriptorMessage	worldDescriptor;
		private World					world;

		public IntegratorDescriptorMessage getIntegratorRequest() {

			return integratorDescriptorMessage;
		}

		public void setIntegratorRequest(IntegratorDescriptorMessage integratorDescriptorMessage) {

			this.integratorDescriptorMessage = integratorDescriptorMessage;
		}

		public AbstractIntegrator getIntegrator() {

			return integrator;
		}

		public void setIntegrator(AbstractIntegrator integrator) {

			this.integrator = integrator;
		}

		public WorldDescriptorMessage getWorldDescriptor() {

			return worldDescriptor;
		}

		public void setWorldDescriptor(WorldDescriptorMessage worldDescriptor) {

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
