package org.rays3d.message;

import java.io.Serializable;

public class IntegratorRequest implements Serializable {

	private static final long	serialVersionUID	= 7795015581119478084L;

	private long				renderId;

	private int					filmWidth;
	private int					filmHeight;
	private int					samplesPerPixel;

	private String				integratorName;
	private String				extraIntegratorConfig;

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
