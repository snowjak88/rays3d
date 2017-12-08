package org.rays3d.renderdb.model;

import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Version;

import org.springframework.data.annotation.CreatedDate;

/**
 * Holds a single world-definition file, along with supporting metadata.
 * 
 * @author snowjak88
 */
@Entity
public class World {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long	id;

	@Version
	private int		version;

	@CreatedDate
	private Date	created;

	@Basic
	private String	name;

	@Basic
	@Column(length = 1024)
	private String	description;

	@Lob
	@Basic(optional = false)
	private String	file;

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

	public String getFile() {

		return file;
	}

	public void setFile(String file) {

		this.file = file;
	}
}
