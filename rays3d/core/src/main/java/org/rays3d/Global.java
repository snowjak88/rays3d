package org.rays3d;

import static org.apache.commons.math3.util.FastMath.*;

/**
 * Contains constants and other objects useful to just about everything.
 * 
 * @author snowjak88
 */
public class Global {

	/**
	 * Two {@link Double}s closer than this value will be considered as
	 * effectively the same.
	 */
	public static final double DOUBLE_TOLERANCE = 1e-12;

	/**
	 * Return <code>true</code> if <code>d1</code> and <code>d2</code> are
	 * different by no more than {@link #DOUBLE_TOLERANCE}.
	 * 
	 * @param d1
	 * @param d2
	 * @return
	 */
	public static boolean isNear(double d1, double d2) {

		return abs(d1 - d2) <= DOUBLE_TOLERANCE;
	}

}
