package org.rays3d.integrator.integrators;

import static org.junit.Assert.*;

import java.util.Map;

import org.junit.Test;

public class AbstractIntegratorTest {

	@Test
	public void testExtraConfigurationAsMap() {

		final String extraConfiguration = "[ a:1, b:2, c:'abc' ]";

		try {
			final Map<String, Object> configMap = AbstractIntegrator.parseStringAsGroovyMap(extraConfiguration);

			assertNotNull("Inflated Map not actually present!", configMap);
			assertEquals("Map doesn't have expected number of entries", 3, configMap.size());

			assertTrue("Map doesn't have key#1", configMap.containsKey("a"));
			assertTrue("Map doesn't have key#2", configMap.containsKey("b"));
			assertTrue("Map doesn't have key#3", configMap.containsKey("c"));

			assertEquals("Map doesn't have correct value for key#1", 1, configMap.get("a"));
			assertEquals("Map doesn't have correct value for key#2", 2, configMap.get("b"));
			assertEquals("Map doesn't have correct value for key#3", "abc", configMap.get("c"));

		} catch (Throwable t) {
			t.printStackTrace(System.err);
			fail("Unexpected exception! -- " + t.getMessage());
		}
	}

}
