package org.rays3d.texture;

import java.lang.Double;
import java.lang.ArrayIndexOutOfBoundsException;
import org.apache.commons.math3.util.FastMath;
import org.rays3d.geometry.Point2D;
import org.rays3d.interact.DescribesSurface;
import org.rays3d.interact.SurfaceDescriptor;
import org.rays3d.spectrum.RGBSpectrum;
import org.rays3d.spectrum.Spectrum;
import org.rays3d.texture.mapping.LinearTextureMapping;
import org.rays3d.texture.mapping.TextureMapping;

/**
 * Defines a checkerboard {@link Texture} -- i.e., a division of texture-space
 * into squares, and the allocation of one {@link Texture} or another to each of
 * those areas.
 * <p>
 * Space is divided between the two constituent Textures by:
 * 
 * <pre>
 * i = | round(x + y) | % 2
 * </pre>
 * 
 * where <code>i = 0</code> = Texture #1, <code>i = 1</code> = Texture #2
 * </p>
 * 
 * @author snowjak88
 */
public class CheckerboardTexture extends Texture {

	private final Texture[] textures;

	/**
	 * Construct a new CheckerboardTexture using the two specified
	 * {@link Texture}s, and the default {@link LinearTextureMapping}.
	 * 
	 * @param texture1
	 * @param texture2
	 */
	public CheckerboardTexture(Texture texture1, Texture texture2) {
		this(texture1, texture2, new LinearTextureMapping());
	}

	/**
	 * Construct a new CheckerboardTexture using the two specified
	 * {@link Texture}s, and the given {@link TextureMapping}.
	 * 
	 * @param texture1
	 * @param texture2
	 * @param textureMapping
	 */
	public CheckerboardTexture(Texture texture1, Texture texture2, TextureMapping textureMapping) {

		super(textureMapping);
		this.textures = new Texture[] { texture1, texture2 };
	}

	@Override
	public <T extends DescribesSurface> Spectrum evaluate(SurfaceDescriptor<T> surface) {

		final Point2D mappedPoint = getTextureMapping().map(surface.getParam());
		final SurfaceDescriptor<T> mappedSurface = new SurfaceDescriptor<T>(surface.getDescribed(), surface.getPoint(),
				surface.getNormal(), mappedPoint);

		Spectrum result = RGBSpectrum.BLACK;
		try {
			result = textures[determineTextureIndex(mappedPoint)].evaluate(mappedSurface);
		} catch (ArrayIndexOutOfBoundsException e) {
			System.err.println("ArrayIndexOutOfBoundsException at [" + Double.toString(mappedPoint.getX()) + ", "
					+ Double.toString(mappedPoint.getY()));
		}
		return result;
	}

	/**
	 * For the given texture-coordinates, determine which of the this
	 * checkerboard's {@link Texture}s to use (0-based index).
	 * 
	 * @param textureParam
	 * @return
	 */
	public int determineTextureIndex(Point2D textureParam) {

		return (int) ( ( (long) FastMath.round(FastMath.abs(textureParam.getX()))
				+ (long) FastMath.round(FastMath.abs(textureParam.getY())) ) % (long) textures.length );
	}

}
