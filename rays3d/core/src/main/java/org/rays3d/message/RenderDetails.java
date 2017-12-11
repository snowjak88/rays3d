package org.rays3d.message;

import java.util.HashMap;
import java.util.Map;

/**
 * Carries details relating to an on-going render.
 * 
 * @author snowjak88
 */
public class RenderDetails {

	private String				renderName		= null;

	private int					filmWidth		= 0, filmHeight = 0;

	private int					samplesPerPixel	= 0, totalSamples = 0;
	private String				samplerName		= null;

	private String				integratorName	= null;

	private Map<String, String>	extraSettings	= new HashMap<>();

	public String getRenderName() {

		return renderName;
	}

	public void setRenderName(String renderName) {

		this.renderName = renderName;
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

	public int getTotalSamples() {

		return totalSamples;
	}

	public void setTotalSamples(int totalSamples) {

		this.totalSamples = totalSamples;
	}

	public String getSamplerName() {

		return samplerName;
	}

	public void setSamplerName(String samplerName) {

		this.samplerName = samplerName;
	}

	public String getIntegratorName() {

		return integratorName;
	}

	public void setIntegratorName(String integratorName) {

		this.integratorName = integratorName;
	}

	public Map<String, String> getExtraSettings() {

		return extraSettings;
	}

	public void setExtraSettings(Map<String, String> extraSettings) {

		this.extraSettings = extraSettings;
	}
}
