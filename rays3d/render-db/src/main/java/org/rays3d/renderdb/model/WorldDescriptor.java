package org.rays3d.renderdb.model;

import java.util.Collection;
import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.OneToMany;
import javax.persistence.Version;

import org.springframework.data.annotation.CreatedDate;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Holds a single world-definition text, along with supporting metadata.
 * 
 * @author snowjak88
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@Entity
public class WorldDescriptor {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long							id;

	@Version
	private int								version;

	@CreatedDate
	private Date							created;

	@Basic
	private String							name;

	@Basic
	@Column(length = 1024)
	private String							description;

	@JsonIgnore
	@OneToMany(fetch = FetchType.EAGER, cascade = { CascadeType.ALL }, mappedBy = "worldDescriptor")
	private Collection<RenderDescriptor>	renderDescriptors;

	@Lob
	@Basic(fetch = FetchType.LAZY, optional = false)
	private String							text;

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

	public String getName() {

		return name;
	}

	public void setName(String name) {

		this.name = name;
	}

	public String getDescription() {

		return description;
	}

	public void setDescription(String description) {

		this.description = description;
	}

	public Collection<RenderDescriptor> getRenderDescriptors() {

		return renderDescriptors;
	}

	public void setRenderDescriptors(Collection<RenderDescriptor> renderDescriptors) {

		this.renderDescriptors = renderDescriptors;
	}

	public String getText() {

		return text;
	}

	public void setText(String text) {

		this.text = text;
	}
}
