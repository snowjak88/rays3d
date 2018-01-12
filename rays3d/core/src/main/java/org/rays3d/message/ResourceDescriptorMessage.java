package org.rays3d.message;

import java.io.Serializable;

/**
 * Message bean for sending/receiving binary resources.
 * 
 * @author snowjak88
 */
public class ResourceDescriptorMessage implements Serializable {

	private static final long	serialVersionUID	= 3265949836364331146L;

	private long				id;
	private byte[]				data;
	private String				mimeType;

	public long getId() {

		return id;
	}

	public void setId(long id) {

		this.id = id;
	}

	public byte[] getData() {

		return data;
	}

	public void setData(byte[] data) {

		this.data = data;
	}

	public String getMimeType() {

		return mimeType;
	}

	public void setMimeType(String mimeType) {

		this.mimeType = mimeType;
	}

}
