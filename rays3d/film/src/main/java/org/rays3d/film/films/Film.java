package org.rays3d.film.films;

import java.awt.image.BufferedImage;

import org.rays3d.message.sample.Sample;

public interface Film {

	/**
	 * Include the given {@link Sample} in the growing image.
	 * 
	 * @param sample
	 */
	public void addSample(Sample sample);

	/**
	 * Create the {@link BufferedImage} containing this {@link Film}'s image.
	 * 
	 * @return
	 */
	public BufferedImage getImage();

}
