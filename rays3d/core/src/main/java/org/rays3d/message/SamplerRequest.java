package org.rays3d.message;

public class SamplerRequest {

	private long	renderId;

	private int		filmWidth;
	private int		filmHeight;

	private String	samplerName;
	private int		samplesPerPixel;

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
