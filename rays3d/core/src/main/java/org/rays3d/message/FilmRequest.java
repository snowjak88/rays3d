package org.rays3d.message;

import java.io.Serializable;

public class FilmRequest implements Serializable {

	private static final long	serialVersionUID	= 1980152642044784895L;

	private long				renderId;

	private int					filmWidth;
	private int					filmHeight;
	private int					samplesPerPixel;

	public long getRenderId() {

		return renderId;
	}

	public void setRenderId(long renderId) {

		this.renderId = renderId;
	}

	public int getFilmWidth() {

		return filmWidth;
	}

	public void setFilmWidth(int filmWidth) {

		this.filmWidth = filmWidth;
	}

	public int getFilmHeight() {

		return filmHeight;
	}

	public void setFilmHeight(int filmHeight) {

		this.filmHeight = filmHeight;
	}

	public int getSamplesPerPixel() {

		return samplesPerPixel;
	}

	public void setSamplesPerPixel(int samplesPerPixel) {

		this.samplesPerPixel = samplesPerPixel;
	}

}
