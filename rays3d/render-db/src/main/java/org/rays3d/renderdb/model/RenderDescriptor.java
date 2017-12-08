package org.rays3d.renderdb.model;

import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Version;

import org.springframework.data.annotation.CreatedDate;

/**
 * Describes a render that can be picked-up by the system and processed.
 * 
 * @author snowjak88
 */
@Entity
public class RenderDescriptor {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long			id;

	@Version
	private int				version;

	@CreatedDate
	private Date			created;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	private World			world;

	@Basic(optional = false)
	private int				renderedImageWidth;
	@Basic(optional = false)
	private int				renderedImageHeight;

	@Basic(optional = false)
	private String			samplerName;
	@Basic(optional = false)
	private int				samplesPerPixel;

	@Enumerated(EnumType.STRING)
	private RenderStatus	samplingStatus		= RenderStatus.NOT_STARTED;

	@Enumerated(EnumType.STRING)
	private RenderStatus	integrationStatus	= RenderStatus.NOT_STARTED;

	@Enumerated(EnumType.STRING)
	private RenderStatus	filmStatus			= RenderStatus.NOT_STARTED;

	@Basic
	private String			imageMimeType;

	@Lob
	@Basic
	private byte[]			imageData;

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

	public Date getCreated() {

		return created;
	}

	public void setCreated(Date created) {

		this.created = created;
	}

	public World getWorld() {

		return world;
	}

	public void setWorld(World world) {

		this.world = world;
	}

	public int getRenderedImageWidth() {

		return renderedImageWidth;
	}

	public void setRenderedImageWidth(int renderedImageWidth) {

		this.renderedImageWidth = renderedImageWidth;
	}

	public int getRenderedImageHeight() {

		return renderedImageHeight;
	}

	public void setRenderedImageHeight(int renderedImageHeight) {

		this.renderedImageHeight = renderedImageHeight;
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
