package org.rays3d.film.holder;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;

import org.apache.camel.EndpointInject;
import org.apache.camel.ProducerTemplate;
import org.rays3d.film.films.Film;
import org.rays3d.film.films.SimpleFilm;
import org.rays3d.message.FilmDescriptorMessage;
import org.rays3d.message.ResourceDescriptorMessage;
import org.rays3d.message.sample.Sample;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Responsible for accumulating {@link Sample}s into {@link Film} instances.
 * 
 * @author snowjak88
 */
@Component
public class FilmUpdateBean {

	private final static Logger		LOG						= LoggerFactory.getLogger(FilmUpdateBean.class);

	@EndpointInject(uri = "activemq:rays3d.render.forID.filmRequest")
	private ProducerTemplate		refreshFilmRequestQueue;

	@EndpointInject(uri = "activemq:rays3d.render.updateCompletion.forID")
	private ProducerTemplate		updateCompletionStatusQueue;

	@EndpointInject(uri = "activemq:rays3d.render.newImages")
	private ProducerTemplate		postNewImagesQueue;

	private Map<Long, FilmDescriptorMessage>	filmDescriptorMessages			= new HashMap<>();
	private Map<Long, Film>			filmInstances			= new HashMap<>();
	private Map<Long, Long>			remainingSampleCounts	= new HashMap<>();

	public void initializeFilm(FilmDescriptorMessage filmDescriptorMessage) {

		final long renderId = filmDescriptorMessage.getRenderId();
		LOG.debug("Initializing Film instance for render-ID {}", renderId);

		LOG.trace("Inserting FilmDescriptorMessage into the cache.");
		filmDescriptorMessages.put(renderId, filmDescriptorMessage);

		final long expectedSampleCount = (long) filmDescriptorMessage.getFilmWidth() * (long) filmDescriptorMessage.getFilmHeight()
				* (long) filmDescriptorMessage.getSamplesPerPixel();
		LOG.info("Inserting expected sample-count for render-ID {} to {}", renderId, expectedSampleCount);
		remainingSampleCounts.put(renderId, expectedSampleCount);
	}

	public void addSample(Sample sample) throws IOException {

		final long renderId = sample.getRenderId();
		LOG.debug("Receiving sample for render-ID {} at [{},{}] -- expected {} samples remaining", renderId,
				Integer.toString((int) sample.getFilmPoint().getX()),
				Integer.toString((int) sample.getFilmPoint().getY()), remainingSampleCounts.getOrDefault(renderId, 0l));

		if (!filmDescriptorMessages.containsKey(renderId)) {

			LOG.info("Cache-miss for FilmDescriptorMessage for render-ID {} -- refreshing ...", renderId);
			final FilmDescriptorMessage filmDescriptorMessage = refreshFilmRequestQueue.requestBody(renderId, FilmDescriptorMessage.class);

			filmDescriptorMessages.put(renderId, filmDescriptorMessage);

			final long expectedSampleCount = (long) filmDescriptorMessage.getFilmWidth() * (long) filmDescriptorMessage.getFilmHeight()
					* (long) filmDescriptorMessage.getSamplesPerPixel();
			LOG.info("Resetting expected sample-count for render-ID {} to {}", renderId, expectedSampleCount);
			remainingSampleCounts.put(renderId, expectedSampleCount);
		}

		if (!filmInstances.containsKey(renderId)) {

			final FilmDescriptorMessage request = filmDescriptorMessages.get(renderId);
			final int width = request.getFilmWidth();
			final int height = request.getFilmHeight();

			LOG.info("No Film-instance for render-ID {} -- creating a [{}x{}] instance ...", renderId, width, height);
			filmInstances.put(renderId, new SimpleFilm(width, height));
		}

		final Film film = filmInstances.get(renderId);
		long expectedSamplesRemaining = remainingSampleCounts.get(renderId);

		LOG.trace("Adding sample to the film-instance ...");
		film.addSample(sample);

		expectedSamplesRemaining--;
		LOG.trace("Decrementing expected-samples-remaining to {}", expectedSamplesRemaining);
		remainingSampleCounts.put(renderId, expectedSamplesRemaining);

		if (expectedSamplesRemaining == 0) {
			LOG.info("Expected-sample-count reached 0 for render-ID {}.", renderId);

			LOG.info("Building and sending a new image ...");

			LOG.debug("Creating BufferedImage from the Film instance ...");
			final BufferedImage image = film.getImage();

			LOG.debug("Saving BufferedImage as a byte-buffer (format = PNG)");
			final ByteArrayOutputStream byteOutputStream = new ByteArrayOutputStream();
			try {
				ImageIO.write(image, "png", byteOutputStream);
			} catch (IOException e) {
				LOG.error("Encountered exception while saving rendered-image!", e);
				throw e;
			}

			LOG.debug("Converting byte-buffer to a ResourceDescriptorMessage and sending ...");
			final ResourceDescriptorMessage resource = new ResourceDescriptorMessage();
			resource.setData(byteOutputStream.toByteArray());
			resource.setMimeType("image/png");

			postNewImagesQueue.sendBodyAndHeader(resource, "renderId", renderId);

			LOG.info("Marking this render-ID ({}) as COMPLETE.", renderId);
			final Map<String, Object> completionHeaders = new HashMap<>();
			completionHeaders.put("completion", "COMPLETE");
			updateCompletionStatusQueue.sendBodyAndHeaders(renderId, completionHeaders);

			LOG.debug("Dropping cached instances relating to render-ID {}", renderId);
			filmDescriptorMessages.remove(renderId);
			filmInstances.remove(renderId);
			remainingSampleCounts.remove(renderId);
		}

	}

}
