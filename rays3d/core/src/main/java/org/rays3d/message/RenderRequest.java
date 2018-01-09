package org.rays3d.message;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class RenderRequest implements Serializable {

	private static final long	serialVersionUID	= -7640200357002241967L;

	private long				id;
	private int					version;

	private int					filmWidth;
	private int					filmHeight;

	private String				samplerName;
	private int					samplesPerPixel;

	private String				integratorName;
	private String				extraIntegratorConfig;

	private RenderStatus		renderingStatus;

	public long getId() {

		return id;
	}

	public void setId(long id) {

		this.id = id;
	}

	public int getVersion() {

		return version;
	}

	public void setVersion(int version) {

		this.version = version;
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

	public RenderStatus getRenderingStatus() {

		return renderingStatus;
	}

	public void setRenderingStatus(RenderStatus renderingStatus) {

		this.renderingStatus = renderingStatus;
	}

}
