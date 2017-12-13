package org.rays3d.message;

public class RenderRequest {

	private long			id;

	private int				filmWidth;
	private int				filmHeight;

	private String			samplerName;
	private int				samplesPerPixel;

	private String			integratorName;
	private String			extraIntegratorConfig;

	private RenderStatus	renderingStatus;
	private RenderStatus	samplingStatus;
	private RenderStatus	integrationStatus;
	private RenderStatus	filmStatus;

	private String			imageMimeType;
	private byte[]			imageData;

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

	public RenderStatus getSamplingStatus() {

		return samplingStatus;
	}

	public void setSamplingStatus(RenderStatus samplingStatus) {

		this.samplingStatus = samplingStatus;
	}

	public RenderStatus getIntegrationStatus() {

		return integrationStatus;
	}

	public void setIntegrationStatus(RenderStatus integrationStatus) {

		this.integrationStatus = integrationStatus;
	}

	public RenderStatus getFilmStatus() {

		return filmStatus;
	}

	public void setFilmStatus(RenderStatus filmStatus) {

		this.filmStatus = filmStatus;
	}

	public String getImageMimeType() {

		return imageMimeType;
	}

	public void setImageMimeType(String imageMimeType) {

		this.imageMimeType = imageMimeType;
	}

	public byte[] getImageData() {

		return imageData;
	}

	public void setImageData(byte[] imageData) {

		this.imageData = imageData;
	}

}
