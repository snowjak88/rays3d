package org.rays3d.sampler;

import java.util.Iterator;
import java.util.NoSuchElementException;

import org.rays3d.geometry.Point2D;
import org.rays3d.message.SamplerRequestMessage;
import org.rays3d.message.sample.Sample;
import org.rays3d.message.sample.SampleRequestMessage;
import org.rays3d.sampler.samplers.NamedSamplerScanner;
import org.rays3d.sampler.samplers.Sampler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("samplesRequestService")
public class SamplesRequestServiceBean {

	private static final Logger	LOG	= LoggerFactory.getLogger(SamplesRequestServiceBean.class);

	@Autowired
	private NamedSamplerScanner	namedSamplerScanner;

	/**
	 * Split a single {@link SamplerRequestMessage} into a whole bunch of
	 * {@link SampleRequestMessage}s (each representing a single pixel's worth of
	 * samples).
	 * 
	 * @param request
	 * @return
	 */
	public Iterator<SampleRequestMessage> splitSamplerRequest(SamplerRequestMessage request) {

		LOG.debug("Received new Sampler-Request ([{}x{}], {} spp) = {} total samples", request.getFilmWidth(),
				request.getFilmHeight(), request.getSamplesPerPixel(),
				(long) request.getFilmWidth() * (long) request.getFilmHeight() * (long) request.getSamplesPerPixel());
		return new SampleRequestIterator(request);
	}

	/**
	 * For the given {@link SampleRequestMessage}, split it into individual
	 * {@link Sample}s.
	 * 
	 * @param request
	 */
	public Iterator<Sample> splitSampleRequestIntoSamples(SampleRequestMessage request) {

		LOG.trace("Received new Sample-Request ([{},{}], {} samples)", request.getFilmPoint().getX(),
				request.getFilmPoint().getY(), request.getSamplesPerPixel());
		final Sampler sampler = namedSamplerScanner.getSamplerByName(request.getSamplerName(), request);
		LOG.trace("Found named sampler \"{}\": [{}].", request.getSamplerName(), sampler.getClass().getName());

		return sampler;

	}

	public static class SampleRequestIterator implements Iterator<SampleRequestMessage> {

		private final SamplerRequestMessage	samplerRequestMessage;
		private final int				minX, minY, maxX, maxY;
		private int						currentX, currentY;

		public SampleRequestIterator(SamplerRequestMessage samplerRequestMessage) {
			this.samplerRequestMessage = samplerRequestMessage;
			this.minX = 0;
			this.minY = 0;
			this.maxX = samplerRequestMessage.getFilmWidth() - 1;
			this.maxY = samplerRequestMessage.getFilmHeight() - 1;

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
		public SampleRequestMessage next() {

			if (!hasNext())
				throw new NoSuchElementException();

			currentY++;

			if (currentY > maxY) {

				currentY = minY;
				currentX++;

			}

			final SampleRequestMessage sample = new SampleRequestMessage();

			sample.setId(samplerRequestMessage.getRenderId());
			sample.setSamplerName(samplerRequestMessage.getSamplerName());
			sample.setSamplesPerPixel(samplerRequestMessage.getSamplesPerPixel());
			sample.setFilmPoint(new Point2D(currentX, currentY));

			return sample;
		}

	}

}
