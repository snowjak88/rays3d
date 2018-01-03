package org.rays3d.camera;

import org.rays3d.geometry.Point3D;
import org.rays3d.geometry.Ray;
import org.rays3d.geometry.Vector3D;
import org.rays3d.geometry.util.Matrix;
import org.rays3d.message.sample.Sample;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Represents a model for a camera and its associated system of lenses.
 * <p>
 * A Camera implementation is responsible for translating between locations in
 * the image-plane and rays in world-space. If appropriate, the Camera may also
 * accept specified locations on its lens-disc (where all its constituent lenses
 * are modeled as a single composite lens).
 * </p>
 * 
 * @author snowjak88
 */
public abstract class Camera {

	@JsonProperty
	private Point3D		eyePoint;

	@JsonProperty
	private Point3D		lookAt;
	@JsonProperty
	private Vector3D	up;

	@JsonProperty
	private double		imagePlaneSizeX;
	@JsonProperty
	private double		imagePlaneSizeY;
	@JsonProperty
	private double		filmSizeX;
	@JsonProperty
	private double		filmSizeY;

	@JsonIgnore
	private Matrix		cameraTwist		= null;
	private Matrix		cameraTranslate	= null;

	/**
	 * Construct a new Camera located at the given <code>eyePoint</code>,
	 * looking at <code>lookAt</code>, with the camera's "up" vector oriented
	 * along <code>up</code>.
	 * <p>
	 * The Camera's image-plane is considered to be centered at (0,0,0) (in
	 * camera coordinates), with maximum extents at:
	 * 
	 * <pre>
	 * [ -imagePlaneSizeX / 2, -imagePlaneSizeY / 2 ] , [ +imagePlaneSizeX / 2, +imagePlaneSizeY / 2 ]
	 * </pre>
	 * </p>
	 * 
	 * @param imagePlaneSizeX
	 * @param imagePlaneSizeY
	 * @param eyePoint
	 * @param lookAt
	 * @param up
	 */
	public Camera(double filmSizeX, double filmSizeY, double imagePlaneSizeX, double imagePlaneSizeY, Point3D eyePoint,
			Point3D lookAt, Vector3D up) {

		this.filmSizeX = filmSizeX;
		this.filmSizeY = filmSizeY;

		this.imagePlaneSizeX = imagePlaneSizeX;
		this.imagePlaneSizeY = imagePlaneSizeY;

		this.eyePoint = eyePoint;
		this.lookAt = lookAt;
		this.up = up;
	}

	private void initializeMatrices() {

		final Vector3D eyeVect = Vector3D.from(eyePoint), lookAtVect = Vector3D.from(lookAt);

		final Vector3D cameraZAxis = lookAtVect.subtract(eyeVect).normalize();
		final Vector3D cameraXAxis = up.crossProduct(cameraZAxis).normalize();
		final Vector3D cameraYAxis = cameraZAxis.crossProduct(cameraXAxis).normalize();

		//@formatter:off
		cameraTwist =
				new Matrix(new double[][] {	{ cameraXAxis.getX(), cameraYAxis.getX(), cameraZAxis.getX(), 0d },
											{ cameraXAxis.getY(), cameraYAxis.getY(), cameraZAxis.getY(), 0d },
											{ cameraXAxis.getZ(), cameraYAxis.getZ(), cameraZAxis.getZ(), 0d },
											{                 0d,                 0d,                 0d, 1d } });
		cameraTranslate =
				new Matrix(new double[][] {	{ 1d, 0d, 0d, +eyePoint.getX() },
											{ 0d, 1d, 0d, +eyePoint.getY() },
											{ 0d, 0d, 1d, +eyePoint.getZ() },
											{ 0d, 0d, 0d,               1d } });
		//@formatter:on
	}

	/**
	 * Translate the given {@link Sample} into a Ray in world-space.
	 * 
	 * @param sample
	 * @return
	 */
	public Ray getRay(Sample sample) {

		return getRay(sample.getFilmPoint().getX(), sample.getFilmPoint().getY(), sample.getLensUV().getX(),
				sample.getLensUV().getY());
	}

	/**
	 * Translate the given coordinates on the film-plane (i.e., pixel
	 * coordinates) into a Ray in world-space. It is assumed that the camera's
	 * lens is sampled at (0.5,0.5) -- i.e., through the middle of its
	 * lens-system.
	 * 
	 * @param filmX
	 *            x-coordinate in the film-plane
	 * @param filmY
	 *            y-coordinate in the film-plane
	 */
	public Ray getRay(double filmX, double filmY) {

		return getRay(filmX, filmY, 0.5d, 0.5d);
	}

	/**
	 * Translate the given coordinates on the film-plane (i.e., pixel
	 * coordinates) into a Ray in world-space. The Ray is modeled so that it
	 * passes through the camera's composite-lens at the specified
	 * <code>lens</code> eyePoint (clamped to the interval [0.0 - 1.0]).
	 * 
	 * @param filmX
	 *            x-coordinate in the film-plane
	 * @param filmY
	 *            y-coordinate in the film-plane
	 * @param lensU
	 *            x-coordinate in the lens-plane. Clamped to the interval [0.0,
	 *            1.0]
	 * @param lensV
	 *            y-coordinate in the lens-plane. Clamped to the interval [0.0,
	 *            1.0]
	 * @return
	 */
	public abstract Ray getRay(double filmX, double filmY, double lensU, double lensV);

	/**
	 * Transform the given {@link Ray} (in camera-coordinates) into a Ray in
	 * world coordinates.
	 */
	protected Ray cameraToWorld(Ray ray) {

		Point3D origin = ray.getOrigin();
		Vector3D direction = ray.getDirection();

		if (cameraTranslate == null || cameraTwist == null)
			initializeMatrices();

		origin = Point3D.from(cameraTranslate.multiply(cameraTwist).multiply(origin, 0));
		direction = Vector3D.from(cameraTwist.multiply(direction, 1));

		return new Ray(origin, direction);
	}

	public Point3D getEyePoint() {

		return eyePoint;
	}

	public void setEyePoint(Point3D eyePoint) {

		cameraTwist = null;
		cameraTranslate = null;
		this.eyePoint = eyePoint;
	}

	public Point3D getLookAt() {

		return lookAt;
	}

	public void setLookAt(Point3D lookAt) {

		cameraTwist = null;
		cameraTranslate = null;
		this.lookAt = lookAt;
	}

	public Vector3D getUp() {

		return up;
	}

	public void setUp(Vector3D up) {

		cameraTwist = null;
		cameraTranslate = null;
		this.up = up;
	}

	public double getImagePlaneSizeX() {

		return imagePlaneSizeX;
	}

	public void setImagePlaneSizeX(double imagePlaneSizeX) {

		this.imagePlaneSizeX = imagePlaneSizeX;
	}

	public double getImagePlaneSizeY() {

		return imagePlaneSizeY;
	}

	public void setImagePlaneSizeY(double imagePlaneSizeY) {

		this.imagePlaneSizeY = imagePlaneSizeY;
	}

	public double getFilmSizeX() {

		return filmSizeX;
	}

	public void setFilmSizeX(double filmSizeX) {

		this.filmSizeX = filmSizeX;
	}

	public double getFilmSizeY() {

		return filmSizeY;
	}

	public void setFilmSizeY(double filmSizeY) {

		this.filmSizeY = filmSizeY;
	}

	@Override
	public int hashCode() {

		final int prime = 31;
		int result = 1;
		result = prime * result + ( ( cameraTranslate == null ) ? 0 : cameraTranslate.hashCode() );
		result = prime * result + ( ( cameraTwist == null ) ? 0 : cameraTwist.hashCode() );
		long temp;
		temp = Double.doubleToLongBits(filmSizeX);
		result = prime * result + (int) ( temp ^ ( temp >>> 32 ) );
		temp = Double.doubleToLongBits(filmSizeY);
		result = prime * result + (int) ( temp ^ ( temp >>> 32 ) );
		temp = Double.doubleToLongBits(imagePlaneSizeX);
		result = prime * result + (int) ( temp ^ ( temp >>> 32 ) );
		temp = Double.doubleToLongBits(imagePlaneSizeY);
		result = prime * result + (int) ( temp ^ ( temp >>> 32 ) );
		return result;
	}

	@Override
	public boolean equals(Object obj) {

		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Camera other = (Camera) obj;
		if (cameraTranslate == null) {
			if (other.cameraTranslate != null)
				return false;
		} else if (!cameraTranslate.equals(other.cameraTranslate))
			return false;
		if (cameraTwist == null) {
			if (other.cameraTwist != null)
				return false;
		} else if (!cameraTwist.equals(other.cameraTwist))
			return false;
		if (Double.doubleToLongBits(filmSizeX) != Double.doubleToLongBits(other.filmSizeX))
			return false;
		if (Double.doubleToLongBits(filmSizeY) != Double.doubleToLongBits(other.filmSizeY))
			return false;
		if (Double.doubleToLongBits(imagePlaneSizeX) != Double.doubleToLongBits(other.imagePlaneSizeX))
			return false;
		if (Double.doubleToLongBits(imagePlaneSizeY) != Double.doubleToLongBits(other.imagePlaneSizeY))
			return false;
		return true;
	}

}
