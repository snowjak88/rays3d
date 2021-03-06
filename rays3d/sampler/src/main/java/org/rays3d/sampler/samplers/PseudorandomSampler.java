package org.rays3d.sampler.samplers;

import java.util.LinkedList;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.rays3d.Global;
import org.rays3d.geometry.Point2D;
import org.rays3d.message.sample.Sample;
import org.rays3d.message.sample.SampleRequestMessage;

@Named("pseudorandom-sampler")
public class PseudorandomSampler extends Sampler {

	private int currentSample = 0;

	public PseudorandomSampler(SampleRequestMessage sampleRequestMessage) {
		super(sampleRequestMessage);
	}

	@Override
	public boolean hasNext() {

		return ( currentSample < getSamplesPerPixel() );
	}

	@Override
	public Sample next() {

		currentSample++;

		if (currentSample > getSamplesPerPixel())
			throw new RuntimeException("This PseudorandomSampler has no more Samples in this domain!");

		final Sample sample = new Sample();

		final double jitterX = Global.RND.nextDouble();
		final double jitterY = Global.RND.nextDouble();

		sample.setRenderId(getRenderId());
		sample.setSamplesPerPixel(getSamplesPerPixel());
		sample.setFilmPoint(new Point2D((double) getFilmX() + jitterX, (double) getFilmY() + jitterY));

		sample.setAdditional1DSamples(
				IntStream.range(0, getSamplesPerPixel()).mapToObj(i -> Global.RND.nextDouble()).collect(
						Collectors.toCollection(LinkedList::new)));

		sample.setAdditional2DSamples(IntStream
				.range(0, getSamplesPerPixel())
					.mapToObj(i -> new Point2D(Global.RND.nextDouble(), Global.RND.nextDouble()))
					.collect(Collectors.toCollection(LinkedList::new)));

		return sample;
	}

}
