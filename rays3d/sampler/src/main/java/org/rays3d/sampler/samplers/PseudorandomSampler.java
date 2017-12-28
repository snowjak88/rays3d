package org.rays3d.sampler.samplers;

import java.util.LinkedList;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.rays3d.Global;
import org.rays3d.geometry.Point2D;
import org.rays3d.message.sample.Sample;
import org.rays3d.message.sample.SampleRequest;

@Named("pseudorandom-sampler")
public class PseudorandomSampler extends Sampler {

	private int currentSample = 0;

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

		if (currentSample > getSamplesToGenerate())
			throw new RuntimeException("This PseudorandomSampler has no more Samples in this domain!");

		final Sample sample = new Sample();

		final double jitterX = Global.RND.nextDouble();
		final double jitterY = Global.RND.nextDouble();

		sample.setRenderId(getRenderId());
		sample.setFilmPoint(new Point2D((double) getFilmX() + jitterX, (double) getFilmY() + jitterY));

		sample.setAdditional1DSamples(
				IntStream.range(0, getSamplesToGenerate()).mapToObj(i -> Global.RND.nextDouble()).collect(
						Collectors.toCollection(LinkedList::new)));

		sample.setAdditional2DSamples(IntStream
				.range(0, getSamplesToGenerate())
					.mapToObj(i -> new Point2D(Global.RND.nextDouble(), Global.RND.nextDouble()))
					.collect(Collectors.toCollection(LinkedList::new)));

		return sample;
	}

}
