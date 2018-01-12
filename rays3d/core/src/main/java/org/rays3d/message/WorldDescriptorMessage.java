package org.rays3d.message;

import java.io.Serializable;

public class WorldDescriptorMessage implements Serializable {

	private static final long	serialVersionUID	= 8584965443751662009L;

	private long				id;

	private String				name;

	private String				description;

	private String				text;

	public long getId() {

		return id;
	}

	public void setId(long id) {

		this.id = id;
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

	public String getText() {

		return text;
	}

	public void setText(String text) {

		this.text = text;
	}
}
