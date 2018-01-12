package org.rays3d.sampler.samplers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.rays3d.geometry.Point2D;
import org.rays3d.message.sample.Sample;
import org.rays3d.message.sample.SampleRequestMessage;

public class PseudorandomSamplerTest {

	@Test
	public void testPseudorandomSampler() {

		final SampleRequestMessage sampleRequestMessage = new SampleRequestMessage();
		sampleRequestMessage.setId(1);
		sampleRequestMessage.setSamplerName("pseudorandom-sampler");
		sampleRequestMessage.setFilmPoint(new Point2D(1.0, 2.0));
		sampleRequestMessage.setSamplesPerPixel(4);

		for (int i = 0; i < 5; i++) {
			final PseudorandomSampler sampler = new PseudorandomSampler(sampleRequestMessage);

			assertEquals("Sampler does not expect to generate a sufficient number of samples for this pixel!",
					sampleRequestMessage.getSamplesPerPixel(), sampler.getSamplesPerPixel());

			for (int spp = 0; spp < sampleRequestMessage.getSamplesPerPixel(); spp++) {

				assertTrue("Sampler cannot generate expected sample!", sampler.hasNext());

				final Sample sample = sampler.next();
				assertTrue("Sample film-point-X is not near pixel-X!",
						( sample.getFilmPoint().getX() >= sampleRequestMessage.getFilmPoint().getX()
								&& sample.getFilmPoint().getX() <= sampleRequestMessage.getFilmPoint().getX() + 1.0 ));
				assertTrue("Sample film-point-Y is not near pixel-Y!",
						( sample.getFilmPoint().getY() >= sampleRequestMessage.getFilmPoint().getY()
								&& sample.getFilmPoint().getY() <= sampleRequestMessage.getFilmPoint().getY() + 1.0 ));

				assertEquals("Sample does not have expected number of additional 1-D samples!",
						sampleRequestMessage.getSamplesPerPixel(), sample.getAdditional1DSamples().size());

				for (int as = 0; as < sampleRequestMessage.getSamplesPerPixel(); as++) {
					final Double additionalSample = sample.getAdditional1DSample();
					assertTrue(
							"Additional 1-D sample should be in range [0,1] -- actually " + additionalSample.toString(),
							( additionalSample >= 0.0 && additionalSample <= 1.0 ));
				}

				assertEquals("Sample does not have expected number of additional 2-D samples!",
						sampleRequestMessage.getSamplesPerPixel(), sample.getAdditional2DSamples().size());

				for (int as = 0; as < sampleRequestMessage.getSamplesPerPixel(); as++) {
					final Point2D additionalSample = sample.getAdditional2DSample();
					assertTrue(
							"Additional 2-D sample-X should be in range [0,1] -- actually "
									+ Double.toString(additionalSample.getX()),
							( additionalSample.getX() >= 0.0 && additionalSample.getX() <= 1.0 ));
					assertTrue(
							"Additional 2-D sample-Y should be in range [0,1] -- actually "
									+ Double.toString(additionalSample.getY()),
							( additionalSample.getY() >= 0.0 && additionalSample.getY() <= 1.0 ));
				}
			}

			assertFalse("Sampler should not have any samples remaining!", sampler.hasNext());
		}
	}

}
