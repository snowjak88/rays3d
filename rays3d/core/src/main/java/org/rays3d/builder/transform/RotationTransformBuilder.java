package org.rays3d.builder.transform;

import org.rays3d.builder.AbstractBuilder;
import org.rays3d.builder.BuilderException;
import org.rays3d.builder.geometry.Vector3DBuilder;
import org.rays3d.transform.RotationTransform;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RotationTransformBuilder<P extends AbstractBuilder<?, ?>> extends TransformBuilder<RotationTransform, P> {

	private static final Logger								LOG					= LoggerFactory
			.getLogger(RotationTransformBuilder.class);

	private Vector3DBuilder<RotationTransformBuilder<P>>	axisBuilder			= null;
	private double											degreesOfRotation	= 0d;

	public RotationTransformBuilder() {
		super();
	}

	public RotationTransformBuilder(P parentBuilder) {
		super(parentBuilder);
	}

	/**
	 * Configure this RotationTransform's axis-of-rotation.
	 * 
	 * @return
	 */
	public Vector3DBuilder<RotationTransformBuilder<P>> axis() {

		this.axisBuilder = new Vector3DBuilder<RotationTransformBuilder<P>>(this);
		return this.axisBuilder;
	}

	/**
	 * Configure the extent of this RotationTransform's rotation about the
	 * configured axis.
	 * 
	 * @param degreesOfRotation
	 * @return
	 */
	public RotationTransformBuilder<P> degreesOfRotation(double degreesOfRotation) {

		this.degreesOfRotation = degreesOfRotation;
		return this;
	}

	@Override
	public RotationTransform build() throws BuilderException {

		try {

			if (this.axisBuilder == null)
				throw new BuilderException("Cannot build a RotationTransform without a configured axis!");
			if (this.degreesOfRotation == 0d)
				LOG.warn("Building a RotationTransform with {} degrees of rotation.", degreesOfRotation);

			return new RotationTransform(axisBuilder.build(), degreesOfRotation);

		} catch (BuilderException e) {
			LOG.error("Exception encountered while building a RotationTransform.", e);
			throw e;
		}
	}

}
