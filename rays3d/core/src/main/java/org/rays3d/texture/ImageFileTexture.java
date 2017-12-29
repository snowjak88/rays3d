package org.rays3d.texture;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.rays3d.geometry.Point2D;
import org.rays3d.interact.DescribesSurface;
import org.rays3d.interact.SurfaceDescriptor;
import org.rays3d.spectrum.RGB;
import org.rays3d.spectrum.RGBSpectrum;
import org.rays3d.spectrum.Spectrum;
import org.rays3d.texture.mapping.TextureMapping;

/**
 * A {@link Texture} implementation backed by an image-file.
 * 
 * @author snowjak88
 */
public class ImageFileTexture extends Texture {

	private final File			imageFile;
	private final BufferedImage	image;

	/**
	 * Construct a new ImageFileTexture, using the given <code>imageFile</code>
	 * and <code>textureMapping</code>.
	 * 
	 * @param imageFile
	 * @param textureMapping
	 * @throws IOException
	 *             if any error occurred while reading the given image-file
	 * @throws UnknownImageFileTypeException
	 *             if the given image-file is of a type we don't know how to
	 *             handle (see {@link ImageIO#getReaderFormatNames()})
	 */
	public ImageFileTexture(File imageFile, TextureMapping textureMapping)
			throws IOException, UnknownImageFileTypeException {
		super(textureMapping);

		this.imageFile = imageFile;

		this.image = ImageIO.read(imageFile);
		if (image == null)
			throw new UnknownImageFileTypeException();
	}

	@Override
	public <T extends DescribesSurface> Spectrum evaluate(SurfaceDescriptor<T> surface) {

		final Point2D textureUV = getTextureMapping().map(surface.getParam());

		final int textureX = (int) ((double) image.getWidth() * textureUV.getX());
		final int textureY = (int) ((double) image.getHeight() * textureUV.getY());
		final int imagePackedRgb = image.getRGB(textureX, textureY);
		return new RGBSpectrum(RGB.fromPacked(imagePackedRgb));
	}

	public File getImageFile() {

		return imageFile;
	}

	/**
	 * Exception thrown to indicate that a certain image-file is of a type we
	 * don't know how to handle.
	 * 
	 * @author snowjak88
	 */
	public static class UnknownImageFileTypeException extends Exception {

		private static final long serialVersionUID = 8924900106671084557L;

	}

}
