package org.rays3d.message;

public class WorldFileRequest {

	private long	id;

	private String	name;

	private String	description;

	private String	file;

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

	public String getFile() {

		return file;
	}

	public void setFile(String file) {

		this.file = file;
	}
}
