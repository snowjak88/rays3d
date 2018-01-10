package org.rays3d.sampler.samplers;

import java.util.Iterator;

import org.rays3d.message.sample.Sample;
import org.rays3d.message.sample.SampleRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A Sampler is an object that can generate {@link Sample}s for a single
 * image-location.
 * 
 * @author snowjak88
 */
public abstract class Sampler implements Iterator<Sample> {

	private static final Logger	LOG	= LoggerFactory.getLogger(Sampler.class);

	private final long			renderId;
	private final int			filmX, filmY;
	private final int			samplesPerPixel;

	public Sampler(SampleRequest sampleRequest) {

		this.renderId = sampleRequest.getRenderId();
		this.filmX = (int) sampleRequest.getFilmPoint().getX();
		this.filmY = (int) sampleRequest.getFilmPoint().getY();
		this.samplesPerPixel = sampleRequest.getSamplesPerPixel();

		LOG.trace("Constructing new Sample generator [{},{}/{}]", filmX, filmY, samplesPerPixel);
	}

	public long getRenderId() {

		return renderId;
	}

	public int getFilmX() {

		return filmX;
	}

	public int getFilmY() {

		return filmY;
	}

	public int getSamplesPerPixel() {

		return samplesPerPixel;
	}

}
