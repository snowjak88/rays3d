package org.rays3d.builder.java.camera;

import org.rays3d.builder.java.AbstractBuilder;
import org.rays3d.builder.java.geometry.Point3DBuilder;
import org.rays3d.builder.java.geometry.Vector3DBuilder;
import org.rays3d.camera.Camera;
import org.rays3d.geometry.Point3D;
import org.rays3d.geometry.Vector3D;

public abstract class CameraBuilder<T extends Camera, CB extends CameraBuilder<T, CB, P>, P extends AbstractBuilder<?, ?>>
		implements AbstractBuilder<T, P> {

	private P						parentBuilder;

	protected Point3DBuilder<CB>	eyePointBuilder;
	protected double				imagePlaneSizeX	= 0, imagePlaneSizeY = 0;
	protected double				filmSizeX		= 0, filmSizeY = 0;
	protected Point3DBuilder<CB>	lookAtBuilder;
	protected Vector3DBuilder<CB>	upBuilder;

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
	@SuppressWarnings("unchecked")
	public Point3DBuilder<CB> eyePoint() {

		this.eyePointBuilder = (Point3DBuilder<CB>) new Point3DBuilder<>((CB) this);
		return this.eyePointBuilder;
	}

	/**
	 * Configure this camera's eye-point ("look-from point").
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public CB eyePoint(Point3D eyePoint) {

		this.eyePointBuilder = new Point3DBuilder<>((CB) this).point(eyePoint);
		return (CB) this;
	}

	/**
	 * Configure this camera's look-at point.
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Point3DBuilder<CB> lookAt() {

		this.lookAtBuilder = new Point3DBuilder<>((CB) this);
		return this.lookAtBuilder;
	}

	/**
	 * Configure this camera's look-at point.
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public CB lookAt(Point3D lookAt) {

		this.lookAtBuilder = new Point3DBuilder<CB>((CB) this).point(lookAt);
		return (CB) this;
	}

	/**
	 * Configure this camera's "up" vector.
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Vector3DBuilder<CB> up() {

		this.upBuilder = new Vector3DBuilder<>((CB) this);
		return this.upBuilder;
	}

	/**
	 * Configure this camera's "up" vector.
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public CB up(Vector3D up) {

		this.upBuilder = new Vector3DBuilder<CB>((CB) this).vector(up);
		return (CB) this;
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
	public P end() {

		return parentBuilder;
	}

}
