package org.rays3d.message;

import java.io.Serializable;

/**
 * Allows a process to indicate its status for a particular render.
 * 
 * @author snowjak88
 */
public enum RenderStatus implements Serializable {

	NOT_STARTED, STARTED, IN_PROGRESS, COMPLETE
}
