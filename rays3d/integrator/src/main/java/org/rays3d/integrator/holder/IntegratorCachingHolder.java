package org.rays3d.integrator.holder;

import java.util.Map;

import org.rays3d.integrator.integrators.AbstractIntegrator;
import org.rays3d.message.IntegratorRequest;
import org.rays3d.message.WorldDescriptorRequest;
import org.rays3d.util.LRUCache;
import org.rays3d.world.World;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

	private Map<Long, IntegratorEntry>	cache;

	public IntegratorCachingHolder(@Value("${org.rays3d.integrator.maximumCacheSize}") int maximumCacheSize) {

		cache = new LRUCache<>(maximumCacheSize);
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
