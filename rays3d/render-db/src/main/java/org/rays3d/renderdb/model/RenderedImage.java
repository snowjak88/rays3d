package org.rays3d.renderdb.model;

import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Version;

import org.springframework.data.annotation.CreatedDate;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Holds the image produced by operating on a {@link RenderDescriptor}.
 * 
 * @author snowjak88
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@Entity
public class RenderedImage {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long				id;

	@Version
	private int					version;

	@CreatedDate
	private Date				created;

	@JsonIgnore
	@ManyToOne(fetch = FetchType.EAGER, optional = false)
	private RenderDescriptor	renderDescriptor;

	@Basic
	private String				imageMimeType;

	@Lob
	@Basic(fetch = FetchType.LAZY)
	private byte[]				imageData;

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

	public RenderDescriptor getRenderDescriptor() {

		return renderDescriptor;
	}

	public void setRenderDescriptor(RenderDescriptor renderDescriptor) {

		this.renderDescriptor = renderDescriptor;
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
