package org.rays3d.util;

import java.util.LinkedHashMap;

public class LRUCache<K, V> extends LinkedHashMap<K, V> {

	private static final long	serialVersionUID			= -2086574471687767957L;

	private static final int	DEFAULT_INITIAL_CAPACITY	= 16;
	private static final float	DEFAULT_LOAD_FACTOR			= 1.0f;

	private final int			maximumCapacity;

	/**
	 * Construct a new LRUCache with a maximum-capacity of
	 * {@link Integer#MAX_VALUE}
	 */
	public LRUCache() {
		super(DEFAULT_INITIAL_CAPACITY, DEFAULT_LOAD_FACTOR, true);
		this.maximumCapacity = Integer.MAX_VALUE;
	}

	/**
	 * Construct a new LRUCache with the specified maximum-capacity.
	 * 
	 * @param maximumCapacity
	 * @throws IllegalArgumentException
	 *             if the specified maximum-capacity is less than 1
	 */
	public LRUCache(int maximumCapacity) {
		super(maximumCapacity, DEFAULT_LOAD_FACTOR, true);

		if (maximumCapacity <= 0)
			throw new IllegalArgumentException("Cannot create a LRUCache with a maximum capacity of less than 1!");
		this.maximumCapacity = maximumCapacity;
	}

	@Override
	protected boolean removeEldestEntry(java.util.Map.Entry<K, V> eldest) {

		return ( this.size() > maximumCapacity );
	}

	public int getMaximumCapacity() {

		return maximumCapacity;
	}

}
