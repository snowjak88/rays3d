package org.rays3d.camera;

import org.rays3d.builder.AbstractBuilder;
import org.rays3d.builder.BuilderException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PinholeCameraBuilder<P extends AbstractBuilder<?, ?>>
		extends CameraBuilder<PinholeCamera, PinholeCameraBuilder<P>, P> {

	private static final Logger	LOG	= LoggerFactory.getLogger(PinholeCameraBuilder.class);

	private double				focalLength;

	public PinholeCameraBuilder() {
	}

	public PinholeCameraBuilder(P parentBuilder) {
		super(parentBuilder);
	}

	public PinholeCameraBuilder<P> focalLength(double focalLength) {

		this.focalLength = focalLength;
		return this;
	}

	@Override
	public PinholeCamera build() throws BuilderException {

		try {

			if (eyePointBuilder == null)
				throw new BuilderException("Cannot build a camera without a configured eye-point!");
			if (lookAtBuilder == null)
				throw new BuilderException("Cannot build a camera without a configured look-at point!");
			if (upBuilder == null)
				throw new BuilderException("Cannot build a camera without a configured up-vector!");
			if (focalLength == 0d)
				throw new BuilderException("Cannot build a camera without a configured focal-length!");
			if (imagePlaneSizeX == 0d || imagePlaneSizeY == 0d)
				throw new BuilderException("Cannot build a camera with a image-plane of size 0 ["
						+ Double.toString(imagePlaneSizeX) + "," + Double.toString(imagePlaneSizeY) + "]!");
			if (filmSizeX == 0d || filmSizeY == 0d)
				throw new BuilderException("Cannot build a camera with a film-size of 0 [" + Double.toString(filmSizeX)
						+ "," + Double.toString(filmSizeY) + "]!");

			return new PinholeCamera(filmSizeX, filmSizeY, imagePlaneSizeX, imagePlaneSizeY, eyePointBuilder.build(),
					lookAtBuilder.build(), upBuilder.build(), focalLength);

		} catch (BuilderException e) {
			LOG.error("Exception while building a pinhole camera.", e);
			throw e;
		}
	}

}
