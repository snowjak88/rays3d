package org.rays3d.message;

import java.io.Serializable;

public class SampleRequest implements Serializable {

	private static final long	serialVersionUID	= -5532500614275211500L;

	private long				id;

	private int					filmWidth;
	private int					filmHeight;

	private String				samplerName;
	private int					samplesPerPixel;

	public long getId() {

		return id;
	}

	public void setId(long id) {

		this.id = id;
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

	public String getSamplerName() {

		return samplerName;
	}

	public void setSamplerName(String samplerName) {

		this.samplerName = samplerName;
	}

	public int getSamplesPerPixel() {

		return samplesPerPixel;
	}

	public void setSamplesPerPixel(int samplesPerPixel) {

		this.samplesPerPixel = samplesPerPixel;
	}

}
