package org.rays3d.renderdb.model;

import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Version;

import org.springframework.data.annotation.CreatedDate;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Allows binary resources to be stored in the database.
 * 
 * @author snowjak88
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@Entity
public class Resource {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@JsonProperty
	private long	id;

	@Version
	@JsonProperty
	private int		version;

	@CreatedDate
	@JsonProperty
	private Date	created;

	@Basic
	@JsonProperty
	private String	mimeType;

	@Lob
	@Basic
	@JsonProperty
	private byte[]	data;

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

	public String getMimeType() {

		return mimeType;
	}

	public void setMimeType(String mimeType) {

		this.mimeType = mimeType;
	}

	public byte[] getData() {

		return data;
	}

	public void setData(byte[] data) {

		this.data = data;
	}

}
