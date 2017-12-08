package org.rays3d;

import static org.junit.Assert.*;

import org.junit.Test;

public class GlobalTest {

	@Test
	public void testIsNear() {

		double d1 = 0.3;
		double d2 = 0.30000001;
		double d3 = 0.3000000000000001;

		assertTrue("Close doubles ought to be isNear(...)", Global.isNear(d1, d3));
		assertFalse("Doubles not quite close out to be !isNear(...)", Global.isNear(d1, d2));
	}

}
