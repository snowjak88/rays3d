package org.rays3d.spectrum;

import static org.junit.Assert.*;

import org.junit.Test;

public class RGBSpectrumBuilderTest {

	@Test
	public void testBuild() {

		//@formatter:off
		final RGBSpectrum spectrum =
				new RGBSpectrumBuilder<>()
						.rgb().copy(RGB.GREEN).end()
						.build();
		//@formatter:on

		assertEquals("Spectrum (R) not as expected!", RGB.GREEN.getRed(), spectrum.getRGB().getRed(), 0.00001);
		assertEquals("Spectrum (G) not as expected!", RGB.GREEN.getGreen(), spectrum.getRGB().getGreen(), 0.00001);
		assertEquals("Spectrum (B) not as expected!", RGB.GREEN.getBlue(), spectrum.getRGB().getBlue(), 0.00001);
	}

}
