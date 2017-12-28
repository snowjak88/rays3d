package org.rays3d;

import java.util.List;

import org.rays3d.bxdf.BSDF;
import org.rays3d.geometry.Point2D;
import org.rays3d.geometry.Point3D;
import org.rays3d.geometry.Ray;
import org.rays3d.geometry.Vector3D;
import org.rays3d.interact.Interactable;
import org.rays3d.interact.Interaction;
import org.rays3d.interact.SurfaceDescriptor;
import org.rays3d.shape.Shape;
import org.rays3d.transform.Transform;
import org.rays3d.transform.Transformable;

public class Primitive implements Interactable, Transformable {

	private final Shape	shape;
	private final BSDF	bsdf;

	public Primitive(Shape shape, BSDF bsdf) {
		this.shape = shape;
		this.bsdf = bsdf;
	}

	@Override
	public boolean isIntersectableWith(Ray ray) {

		return shape.isIntersectableWith(ray);
	}

	@SuppressWarnings("unchecked")
	@Override
	public SurfaceDescriptor<Shape> getSurface(Ray ray) {

		return shape.getSurface(ray);
	}

	@SuppressWarnings("unchecked")
	@Override
	public SurfaceDescriptor<Shape> getSurfaceNearestTo(Point3D neighbor) {

		return shape.getSurfaceNearestTo(neighbor);
	}

	@SuppressWarnings("unchecked")
	@Override
	public SurfaceDescriptor<Shape> sampleSurface() {

		return shape.sampleSurface();
	}

	@SuppressWarnings("unchecked")
	@Override
	public SurfaceDescriptor<Shape> sampleSurfaceFacing(Point3D neighbor) {

		return shape.sampleSurfaceFacing(neighbor);
	}

	@Override
	public double computeSolidAngle(Point3D viewedFrom) {

		return shape.computeSolidAngle(viewedFrom);
	}

	@Override
	public Point2D getParamFromLocalSurface(Point3D point) {

		return shape.getParamFromLocalSurface(point);
	}

	@Override
	public List<Transform> getWorldToLocalTransforms() {

		return shape.getWorldToLocalTransforms();
	}

	@Override
	public List<Transform> getLocalToWorldTransforms() {

		return shape.getLocalToWorldTransforms();
	}

	@Override
	public void appendTransform(Transform transform) {

		shape.appendTransform(transform);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Interaction<Primitive> getInteraction(Ray ray) {

		final SurfaceDescriptor<Shape> surface = shape.getSurface(ray);
		//
		// Calculate the surface-point's distance from the ray origin, along the
		// ray, in terms of multiples of the ray's direction's length.
		final double distanceFromOrigin = Vector3D.from(surface.getPoint().subtract(ray.getOrigin())).getMagnitude();
		final double t = ( distanceFromOrigin ) / ( ray.getDirection().getMagnitude() );

		return new Interaction<Primitive>(this, ray.forT(t), surface.getPoint(), surface.getNormal(),
				surface.getParam());
	}

	public Shape getShape() {

		return shape;
	}

	public BSDF getBsdf() {

		return bsdf;
	}
}
