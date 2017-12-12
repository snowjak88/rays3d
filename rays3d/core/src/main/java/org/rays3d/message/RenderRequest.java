package org.rays3d.message;

public class RenderRequest {

	private long	renderId;

	private int		filmWidth;
	private int		filmHeight;

	private String	samplerName;
	private int		samplesPerPixel;

	private String	integratorName;
	private String	extraIntegratorConfig;

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

	public String getIntegratorName() {

		return integratorName;
	}

	public void setIntegratorName(String integratorName) {

		this.integratorName = integratorName;
	}

	public String getExtraIntegratorConfig() {

		return extraIntegratorConfig;
	}

	public void setExtraIntegratorConfig(String extraIntegratorConfig) {

		this.extraIntegratorConfig = extraIntegratorConfig;
	}

}
