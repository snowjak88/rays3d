package org.rays3d.message;

import org.rays3d.geometry.Point2D;
import org.rays3d.geometry.Ray;
import org.rays3d.spectrum.Spectrum;

public class Sample {

	private long		renderId;

	private Point2D		filmPoint;
	private Point2D		imagePlanePoint;
	private Ray			cameraRay;
	private Spectrum	radiance;

	public long getRenderId() {

		return renderId;
	}

	public void setRenderId(long renderId) {

		this.renderId = renderId;
	}

	public Point2D getFilmPoint() {

		return filmPoint;
	}

	public void setFilmPoint(Point2D filmPoint) {

		this.filmPoint = filmPoint;
	}

	public Point2D getImagePlanePoint() {

		return imagePlanePoint;
	}

	public void setImagePlanePoint(Point2D imagePlanePoint) {

		this.imagePlanePoint = imagePlanePoint;
	}

	public Ray getCameraRay() {

		return cameraRay;
	}

	public void setCameraRay(Ray cameraRay) {

		this.cameraRay = cameraRay;
	}

	public Spectrum getRadiance() {

		return radiance;
	}

	public void setRadiance(Spectrum radiance) {

		this.radiance = radiance;
	}

}
