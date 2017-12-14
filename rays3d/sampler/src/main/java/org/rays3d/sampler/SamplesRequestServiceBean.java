package org.rays3d.sampler;

import java.util.Iterator;
import java.util.NoSuchElementException;

import org.rays3d.geometry.Point2D;
import org.rays3d.message.Sample;
import org.rays3d.message.SampleRequest;
import org.rays3d.message.SamplerRequest;
import org.rays3d.sampler.samplers.NamedSamplerHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("samplesRequestService")
public class SamplesRequestServiceBean {

	@Autowired
	private NamedSamplerHolder namedSamplerHolder;

	/**
	 * Split a single {@link SamplerRequest} into a whole bunch of
	 * {@link SampleRequest}s (each representing a single pixel's worth of
	 * samples).
	 * 
	 * @param request
	 * @return
	 */
	public Iterator<SampleRequest> splitSamplerRequest(SamplerRequest request) {

		return new SampleRequestIterator(request);
	}

	/**
	 * For the given {@link SampleRequest}, split it into individual
	 * {@link Sample}s.
	 * 
	 * @param request
	 */
	public Iterator<Sample> splitSampleRequestIntoSamples(SampleRequest request) {

		return namedSamplerHolder.getSamplerByName(request.getSamplerName(), request);

	}

	public static class SampleRequestIterator implements Iterator<SampleRequest> {

		private final SamplerRequest	samplerRequest;
		private final int				minX, minY, maxX, maxY;
		private int						currentX, currentY;

		public SampleRequestIterator(SamplerRequest samplerRequest) {
			this.samplerRequest = samplerRequest;
			this.minX = 0;
			this.minY = 0;
			this.maxX = samplerRequest.getFilmWidth() - 1;
			this.maxY = samplerRequest.getFilmHeight() - 1;

			this.currentX = minX;
			this.currentY = minY - 1;
		}

		@Override
		public boolean hasNext() {

			if (currentX >= maxX && currentY >= maxY) {
				return false;
			}

			return true;
		}

		@Override
		public SampleRequest next() {

			if (!hasNext())
				throw new NoSuchElementException();

			currentY++;

			if (currentY > maxY) {

				currentY = minY;
				currentX++;

			}

			final SampleRequest sample = new SampleRequest();

			sample.setId(samplerRequest.getRenderId());
			sample.setSamplerName(samplerRequest.getSamplerName());
			sample.setFilmPoint(new Point2D(currentX, currentY));

			return sample;
		}

	}

}
