package org.rays3d.camera;

import org.rays3d.geometry.Point3D;
import org.rays3d.geometry.Ray;
import org.rays3d.geometry.Vector3D;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Implements a simple Pinhole Camera -- i.e., a camera with a lens of
 * 0-diameter.
 * 
 * @author snowjak88
 */
public class PinholeCamera extends Camera {

	@JsonProperty
	private double	focalLength;

	@JsonIgnore
	private Point3D	focusPoint;

	public PinholeCamera(double filmSizeX, double filmSizeY, double imagePlaneSizeX, double imagePlaneSizeY,
			Point3D eyePoint, Point3D lookAt, Vector3D up, double focalLength) {
		super(filmSizeX, filmSizeY, imagePlaneSizeX, imagePlaneSizeY, eyePoint, lookAt, up);

		setFocalLength(focalLength);
	}

	public double getFocalLength() {

		return focalLength;
	}

	public void setFocalLength(double focalLength) {

		this.focalLength = focalLength;
		this.focusPoint = new Point3D(0d, 0d, -focalLength);
	}

	@Override
	public Ray getRay(double filmX, double filmY, double lensU, double lensV) {

		//
		// We disregard lensU and lensV -- this is a pinhole camera, its lens is
		// assumed to be of a point size!
		//
		final double cameraX = filmX * ( getImagePlaneSizeX() / getFilmSizeX() ) - ( getImagePlaneSizeX() / 2d );
		final double cameraY = filmY * ( getImagePlaneSizeY() / getFilmSizeY() ) - ( getImagePlaneSizeY() / 2d );

		final Point3D origin = new Point3D(cameraX, cameraY, 0d);
		final Vector3D direction = Vector3D.from(origin.subtract(focusPoint)).normalize();

		return cameraToWorld(new Ray(origin, direction));
	}

}
