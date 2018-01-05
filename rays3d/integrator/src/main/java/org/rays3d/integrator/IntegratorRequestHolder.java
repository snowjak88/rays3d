package org.rays3d.integrator;

import java.util.HashMap;
import java.util.Map;

import org.rays3d.message.IntegratorRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Component responsible for holding integrator instances corresponding to
 * individual render-IDs.
 * 
 * @author snowjak88
 */
@Component("integratorHolder")
public class IntegratorRequestHolder {

	private static final Logger LOG = LoggerFactory.getLogger(IntegratorRequestHolder.class);
	
	private static Map<Long, IntegratorRequest> integratorRequestStore = new HashMap<>();

	/**
	 * Returns <code>true</code> if this IntegratorRequestHolder already has a
	 * copy of the {@link IntegratorRequest} corresponding to the given
	 * render-ID.
	 * 
	 * @param renderId
	 * @return
	 */
	public boolean hasRenderId(Long renderId) {
		
		final boolean result;
		synchronized (integratorRequestStore) {
			result = integratorRequestStore.containsKey(renderId);
			LOG.trace("Checking to see if IntegratorRequest store is mapped for render-ID = {} ({})", renderId, result);
		}
		return result;
	}

	/**
	 * Returns the {@link IntegratorRequest} associated with the given
	 * render-ID, or <code>null</code> if that IntegratorRequest is not
	 * contained by this IntegratorRequestHolder.
	 * 
	 * @param renderId
	 * @return
	 */
	public IntegratorRequest getForId(Long renderId) {

		final IntegratorRequest result;
		synchronized (integratorRequestStore) {
			result = integratorRequestStore.get(renderId);
		}
		return result;
	}

	/**
	 * Store the given {@link IntegratorRequest} in this IntegratorRequestHolder
	 * (indexed by its configured ID), overwriting any previous
	 * IntegratorRequest stored under that ID.
	 * 
	 * @param integratorRequest
	 */
	public void put(IntegratorRequest integratorRequest) {

		synchronized (integratorRequestStore) {
			LOG.trace("Putting an IntegratorRequest (for ID {}) into the store", integratorRequest.getRenderId());
			integratorRequestStore.put(integratorRequest.getRenderId(), integratorRequest);
		}
	}

}
