package org.rays3d.util;

import static org.junit.Assert.*;

import java.util.Iterator;

import org.junit.Test;

public class LRUCacheTest {

	@Test
	public void test() {

		final LRUCache<Integer, String> cache = new LRUCache<>(4);

		assertTrue("Cache is not empty initially!", cache.isEmpty());
		assertEquals("Cache's maximum-capacity is not set correctly!", 4, cache.getMaximumCapacity());

		cache.put(1, "First string");
		cache.put(2, "Second string");
		cache.put(3, "Third string");
		cache.put(4, "Fourth string");

		assertEquals("Cache does not have the correct number of elements!", 4, cache.size());

		Iterator<Integer> keyIterator = cache.keySet().iterator();
		assertEquals("Cache's LRU+0 member is not as expected.", 1, keyIterator.next().intValue());
		assertEquals("Cache's LRU+1 member is not as expected.", 2, keyIterator.next().intValue());
		assertEquals("Cache's LRU+2 member is not as expected.", 3, keyIterator.next().intValue());
		assertEquals("Cache's LRU+3 member is not as expected.", 4, keyIterator.next().intValue());

		cache.get(3);
		cache.get(2);
		cache.get(1);

		keyIterator = cache.keySet().iterator();
		assertEquals("Cache's LRU+0 member is not as expected.", 4, keyIterator.next().intValue());
		assertEquals("Cache's LRU+1 member is not as expected.", 3, keyIterator.next().intValue());
		assertEquals("Cache's LRU+2 member is not as expected.", 2, keyIterator.next().intValue());
		assertEquals("Cache's LRU+3 member is not as expected.", 1, keyIterator.next().intValue());

		cache.put(5, "Fifth string");

		assertEquals("Cache does not have the correct number of elements!", 4, cache.size());

		keyIterator = cache.keySet().iterator();
		assertEquals("Cache's LRU+0 member is not as expected.", 3, keyIterator.next().intValue());
		assertEquals("Cache's LRU+1 member is not as expected.", 2, keyIterator.next().intValue());
		assertEquals("Cache's LRU+2 member is not as expected.", 1, keyIterator.next().intValue());
		assertEquals("Cache's LRU+3 member is not as expected.", 5, keyIterator.next().intValue());
	}

	@Test
	public void test_badSize() {

		try {
			@SuppressWarnings("unused")
			LRUCache<Integer, String> cache = new LRUCache<>(0);

			fail("Cache-creation did not fail!");

		} catch (IllegalArgumentException e) {

		}

	}

}
