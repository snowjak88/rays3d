package org.rays3d.javabuilder;

/**
 * Indicates that an exception has been encountered while executing a builder.
 * 
 * @author snowjak88
 */
public class BuilderException extends RuntimeException {

	private static final long serialVersionUID = 2804370992496481917L;

	public BuilderException() {
		super();
	}

	public BuilderException(String message) {
		super(message);
	}

	public BuilderException(Throwable cause) {
		super(cause);
	}

	public BuilderException(String message, Throwable cause) {
		super(message, cause);
	}

}
