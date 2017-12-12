package org.rays3d.message;

public class WorldFileRequest {

	private long	renderId;

	private String	worldDescription;

	private String	worldFile;

	public long getRenderId() {

		return renderId;
	}

	public void setRenderId(long renderId) {

		this.renderId = renderId;
	}

	public String getWorldDescription() {

		return worldDescription;
	}

	public void setWorldDescription(String worldDescription) {

		this.worldDescription = worldDescription;
	}

	public String getWorldFile() {

		return worldFile;
	}

	public void setWorldFile(String worldFile) {

		this.worldFile = worldFile;
	}
}
