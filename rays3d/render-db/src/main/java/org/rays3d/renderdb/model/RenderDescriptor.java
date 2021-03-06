package org.rays3d.renderdb.model;

import java.util.Collection;
import java.util.Date;
import java.util.LinkedList;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Version;

import org.rays3d.message.RenderStatus;
import org.springframework.data.annotation.CreatedDate;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Describes a render that can be picked-up by the system and processed.
 * 
 * @author snowjak88
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@Entity
public class RenderDescriptor {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long					id;

	@Version
	private int						version;

	@JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
	@CreatedDate
	private Date					created;

	@JsonIgnore
	@ManyToOne(fetch = FetchType.EAGER, optional = false)
	private WorldDescriptor			worldDescriptor			= null;

	@Basic(optional = false)
	private int						filmWidth				= 0;
	@Basic(optional = false)
	private int						filmHeight				= 0;

	@Basic(optional = false)
	private String					samplerName				= null;
	@Basic(optional = false)
	private int						samplesPerPixel			= 0;

	@Basic(optional = false)
	private String					integratorName			= null;
	@Basic
	@Column(length = 2048)
	private String					extraIntegratorConfig	= null;

	@Enumerated(EnumType.STRING)
	private RenderStatus			renderingStatus			= RenderStatus.NOT_STARTED;

	@JsonIgnore
	@OneToMany(cascade = { CascadeType.ALL })
	private Collection<Resource>	renderedImages			= new LinkedList<>();

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

	public WorldDescriptor getWorldDescriptor() {

		return worldDescriptor;
	}

	public void setWorldDescriptor(WorldDescriptor worldDescriptor) {

		this.worldDescriptor = worldDescriptor;
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

	public Collection<Resource> getRenderedImages() {

		return renderedImages;
	}

	public void setRenderedImages(Collection<Resource> renderedImages) {

		this.renderedImages = renderedImages;
	}

}
