package org.rays3d.camera;

import org.rays3d.builder.AbstractBuilder;
import org.rays3d.geometry.Point3DBuilder;
import org.rays3d.geometry.Vector3DBuilder;

public abstract class CameraBuilder<T extends Camera, CB extends CameraBuilder<T, CB, P>, P extends AbstractBuilder<?, ?>>
		implements AbstractBuilder<T, P> {

	private P											parentBuilder;

	protected Point3DBuilder<CameraBuilder<T, CB, P>>	eyePointBuilder;
	protected double									imagePlaneSizeX	= 0, imagePlaneSizeY = 0;
	protected double									filmSizeX		= 0, filmSizeY = 0;
	protected Point3DBuilder<CameraBuilder<T, CB, P>>	lookAtBuilder;
	protected Vector3DBuilder<CameraBuilder<T, CB, P>>	upBuilder;

	public CameraBuilder() {
		this(null);
	}

	public CameraBuilder(P parentBuilder) {
		this.parentBuilder = parentBuilder;
	}

	/**
	 * Configure this camera's eye-point ("look-from point").
	 * 
	 * @return
	 */
	public Point3DBuilder<CameraBuilder<T, CB, P>> eyePoint() {

		this.eyePointBuilder = new Point3DBuilder<>(this);
		return this.eyePointBuilder;
	}

	/**
	 * Configure this camera's look-at point.
	 * 
	 * @return
	 */
	public Point3DBuilder<CameraBuilder<T, CB, P>> lookAt() {

		this.lookAtBuilder = new Point3DBuilder<>(this);
		return this.lookAtBuilder;
	}

	/**
	 * Configure this camera's "up" vector.
	 * 
	 * @return
	 */
	public Vector3DBuilder<CameraBuilder<T, CB, P>> up() {

		this.upBuilder = new Vector3DBuilder<>(this);
		return this.upBuilder;
	}

	/**
	 * Configure this camera's image-plane size (expressed in world-space
	 * coordinates).
	 * 
	 * @param xSize
	 * @param ySize
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public CB imagePlaneSize(double xSize, double ySize) {

		this.imagePlaneSizeX = xSize;
		this.imagePlaneSizeY = ySize;
		return (CB) this;
	}

	/**
	 * Configure this camera's film-size (expressed in pixels).
	 * 
	 * @param xSize
	 * @param ySize
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public CB filmSize(double xSize, double ySize) {

		this.filmSizeX = xSize;
		this.filmSizeY = ySize;
		return (CB) this;
	}

	@Override
	public P getParentBuilder() {

		return parentBuilder;
	}

}
