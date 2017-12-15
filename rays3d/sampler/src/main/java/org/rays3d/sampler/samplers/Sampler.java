package org.rays3d.sampler.samplers;

import java.util.Iterator;

import org.rays3d.message.Sample;
import org.rays3d.message.SampleRequest;

/**
 * A Sampler is an object that can generate {@link Sample}s for a single
 * image-location.
 * 
 * @author snowjak88
 */
public abstract class Sampler implements Iterator<Sample> {

	private final long	renderId;
	private final int	filmX, filmY;
	private final int	samplesToGenerate;

	public Sampler(SampleRequest sampleRequest) {

		this.renderId = sampleRequest.getRenderId();
		this.filmX = (int) sampleRequest.getFilmPoint().getX();
		this.filmY = (int) sampleRequest.getFilmPoint().getY();
		this.samplesToGenerate = sampleRequest.getSamplesPerPixel();

		System.out.println("Constructing new Sampler [" + filmX + "," + filmY + "/" + samplesToGenerate + "]");
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

	public int getSamplesToGenerate() {

		return samplesToGenerate;
	}
}
