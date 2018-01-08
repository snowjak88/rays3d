package org.rays3d.film.films;

import java.awt.image.BufferedImage;

import org.rays3d.message.sample.Sample;
import org.rays3d.spectrum.RGB;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SimpleFilm implements Film {

	private final static Logger	LOG	= LoggerFactory.getLogger(SimpleFilm.class);

	private final int			width, height;

	private RGB[][]				radiances;

	public SimpleFilm(int width, int height) {
		this.width = width;
		this.height = height;

		this.radiances = new RGB[width][height];
	}

	@Override
	public void addSample(Sample sample) {

		final int x = (int) sample.getFilmPoint().getX();
		final int y = (int) sample.getFilmPoint().getY();

		if (( x < 0 || x >= radiances.length ) || ( y < 0 || y >= radiances[0].length )) {
			LOG.warn(
					"For render-ID {} -- given film-location ({},{}) is outside of film boundaries [0,{}) x [0,{})! Cannot apply radiance.",
					sample.getRenderId(), Double.toString(sample.getFilmPoint().getX()),
					Double.toString(sample.getFilmPoint().getY()), radiances.length, radiances[0].length);
			return;
		}

		if (radiances[x][y] == null) {
			LOG.trace("Adding sample RGB at ({},{})", x, y);
			radiances[x][y] = sample.getRadiance().toRGB();
		}
		else {
			LOG.trace("Adding sample RGB to existing RGB at ({},{})", x, y);
			radiances[x][y] = radiances[x][y].add(sample.getRadiance().toRGB());
		}
	}

	@Override
	public BufferedImage getImage() {

		final BufferedImage image = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_ARGB);

		LOG.info("Building new [{}x{}] image ...", getWidth(), getHeight());
		for (int x = 0; x < getWidth(); x++)
			for (int y = 0; y < getHeight(); y++)
				image.setRGB(x, y, radiances[x][y].toPacked());

		LOG.info("Image built!");

		return image;
	}

	public int getWidth() {

		return width;
	}

	public int getHeight() {

		return height;
	}

}
