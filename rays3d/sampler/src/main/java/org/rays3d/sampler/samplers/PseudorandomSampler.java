package org.rays3d.sampler.samplers;

import org.rays3d.Global;
import org.rays3d.geometry.Point2D;
import org.rays3d.message.Sample;
import org.rays3d.message.SampleRequest;

@Named("pseudorandom-sampler")
public class PseudorandomSampler extends Sampler {

	private int currentSample;

	public PseudorandomSampler(SampleRequest sampleRequest) {
		super(sampleRequest);
	}

	@Override
	public boolean hasNext() {

		return ( currentSample < getSamplesToGenerate() );
	}

	@Override
	public Sample next() {

		currentSample++;

		if (currentSample >= getSamplesToGenerate())
			throw new RuntimeException("This PseudorandomSampler has no more Samples in this domain!");

		final Sample sample = new Sample();

		final double jitterX = Global.RND.nextDouble();
		final double jitterY = Global.RND.nextDouble();

		sample.setRenderId(getRenderId());
		sample.setFilmPoint(new Point2D((double) getFilmX() + jitterX, (double) getFilmY() + jitterY));

		return sample;
	}

}
