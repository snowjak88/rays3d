package org.rays3d.spectrum;

import static org.junit.Assert.*;

import org.junit.Test;

public class RGBTest {

	@Test
	public void testFromHSL_cyan() {

		final RGB cyan = RGB.fromHSL(180d, 1d, 0.5);
		assertEquals("Expected-Red not as expected!", 0d, cyan.getRed(), 0.00001);
		assertEquals("Expected-Green not as expected!", 1d, cyan.getGreen(), 0.00001);
		assertEquals("Expected-Blue not as expected!", 1d, cyan.getBlue(), 0.00001);

	}

	@Test
	public void testFromHSL_orange() {

		final RGB orange = RGB.fromHSL(30d, 1d, 0.5);
		assertEquals("Expected-Red not as expected!", 1d, orange.getRed(), 0.00001);
		assertEquals("Expected-Green not as expected!", 0.5d, orange.getGreen(), 0.00001);
		assertEquals("Expected-Blue not as expected!", 0d, orange.getBlue(), 0.00001);

	}

	@Test
	public void testFromHSL_purple() {

		final RGB purple = RGB.fromHSL(270d, 0.5d, 0.5);
		assertEquals("Expected-Red not as expected!", 0.5d, purple.getRed(), 0.00001);
		assertEquals("Expected-green not as expected!", 0.25d, purple.getGreen(), 0.00001);
		assertEquals("Expected-Blue not as expected!", 0.75d, purple.getBlue(), 0.00001);
	}

	@Test
	public void testFromPacked_cyan() {

		final int packedRgb = 0x00FFFF;
		final RGB unpacked = RGB.fromPacked(packedRgb);
		assertEquals("Expected-Red not as expected!", 0.0, unpacked.getRed(), 1d / 256d);
		assertEquals("Expected-Green not as expected!", 1.0, unpacked.getGreen(), 1d / 256d);
		assertEquals("Expected-Blue not as expected!", 1.0, unpacked.getBlue(), 1d / 256d);
	}

	@Test
	public void testFromPacked_olive() {

		final int packedRgb = 0x808000;
		final RGB unpacked = RGB.fromPacked(packedRgb);
		assertEquals("Expected-Red not as expected!", 0.5, unpacked.getRed(), 1d / 256d);
		assertEquals("Expected-Green not as expected!", 0.5, unpacked.getGreen(), 1d / 256d);
		assertEquals("Expected-Blue not as expected!", 0.0, unpacked.getBlue(), 1d / 256d);
	}

	@Test
	public void testFromPacked_purple() {

		final int packedRgb = 0x800080;
		final RGB unpacked = RGB.fromPacked(packedRgb);
		assertEquals("Expected-Red not as expected!", 0.5, unpacked.getRed(), 1d / 256d);
		assertEquals("Expected-Green not as expected!", 0.0, unpacked.getGreen(), 1d / 256d);
		assertEquals("Expected-Blue not as expected!", 0.5, unpacked.getBlue(), 1d / 256d);
	}

}
