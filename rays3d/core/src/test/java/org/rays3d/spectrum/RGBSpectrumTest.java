package org.rays3d.spectrum;

import static org.junit.Assert.*;

import org.junit.Test;

public class RGBSpectrumTest {

	@Test
	public void testGetAmplitude() {

		final RGB rgb = RGB.fromHSL(180d, 0.5, 0.25);
		final Spectrum spectrum = new RGBSpectrum(rgb);

		assertEquals(0.25, spectrum.getAmplitude(), 0.00001);
	}

}
