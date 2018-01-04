package org.rays3d.builder.groovy

import static org.junit.Assert.*

import static org.rays3d.builder.groovy.WorldBuilder.*;

import org.junit.Test
import org.rays3d.builder.groovy.WorldBuilder.Spec
import org.rays3d.geometry.Normal3D
import org.rays3d.geometry.Point2D
import org.rays3d.geometry.Point3D
import org.rays3d.geometry.Vector3D
import org.rays3d.spectrum.RGB
import org.rays3d.spectrum.RGBSpectrum
import org.rays3d.texture.CheckerboardTexture
import org.rays3d.texture.ConstantTexture
import org.rays3d.texture.ImageFileTexture
import org.rays3d.texture.mapping.LinearTextureMapping


class WorldBuilderTest {

	@Test
	public void testPoint2d() {

		final Point2D point = Geo.point2d {
			x 0
			y 1
		}

		assertEquals "Point (X) is not as expected!", 0.0, point.getX(), 0.00001
		assertEquals "Point (Y) is not as expected!", 1.0, point.getY(), 0.00001
	}

	@Test
	public void testPoint3d() {

		final Point3D point = Geo.point3d {
			x 0
			y 1
			z 2
		}

		assertEquals "Point (X) is not as expected!", 0.0, point.getX(), 0.00001
		assertEquals "Point (Y) is not as expected!", 1.0, point.getY(), 0.00001
		assertEquals "Point (Z) is not as expected!", 2.0, point.getZ(), 0.00001
	}

	@Test
	public void testVector3d() {

		final Vector3D vector = Geo.vector3d {
			x 0
			y 1
			z 2
		}

		assertEquals "Vector (X) is not as expected!", 0.0, vector.getX(), 0.00001
		assertEquals "Vector (Y) is not as expected!", 1.0, vector.getY(), 0.00001
		assertEquals "Vector (Z) is not as expected!", 2.0, vector.getZ(), 0.00001
	}

	@Test
	public void testNormal3d() {

		final Normal3D normal = Geo.normal3d {
			x 0
			y 1
			z 2
		}

		assertEquals "Normal (X) is not as expected!", 0.0, normal.x, 0.00001
		assertEquals "Normal (Y) is not as expected!", 1.0, normal.y, 0.00001
		assertEquals "Normal (Z) is not as expected!", 2.0, normal.z, 0.00001
	}

	@Test
	public void testRGB_component() {

		final RGB rgb = Spec.rgb {
			r 1.0
			g 0.5
			b 0.0
		}

		assertEquals "RGB (R) is not as expected", 1.0, rgb.red, 0.00001
		assertEquals "RGB (G) is not as expected", 0.5, rgb.green, 0.00001
		assertEquals "RGB (B) is not as expected", 0.0, rgb.blue, 0.00001
	}

	@Test
	public void testRGB_rgb() {

		final RGB rgb = Spec.rgb { rgb RGB.GREEN }

		assertEquals "RGB (R) is not as expected", RGB.GREEN.red, rgb.red, 0.00001
		assertEquals "RGB (G) is not as expected", RGB.GREEN.green, rgb.green, 0.00001
		assertEquals "RGB (B) is not as expected", RGB.GREEN.blue, rgb.blue, 0.00001
	}

	@Test
	public void testRGBSpectrum_rgbBuilder() {

		final RGBSpectrum spectrum = Spec.rgbSpectrum {
			rgb { r 1.0; g 0.5; b 0.0 }
		}

		assertEquals "RGB (R) is not as expected", 1.0, spectrum.RGB.red, 0.00001
		assertEquals "RGB (G) is not as expected", 0.5, spectrum.RGB.green, 0.00001
		assertEquals "RGB (B) is not as expected", 0.0, spectrum.RGB.blue, 0.00001
	}

	@Test
	public void testRGBSpectrum_rgbValue() {

		final RGBSpectrum spectrum = Spec.rgbSpectrum { rgb RGB.GREEN }

		assertEquals "RGB (R) is not as expected", RGB.GREEN.red, spectrum.RGB.red, 0.00001
		assertEquals "RGB (G) is not as expected", RGB.GREEN.green, spectrum.RGB.green, 0.00001
		assertEquals "RGB (B) is not as expected", RGB.GREEN.blue, spectrum.RGB.blue, 0.00001
	}

	@Test
	public void testRGBSpectrum_components() {

		final RGBSpectrum spectrum = Spec.rgbSpectrum {
			r 1.0
			g 0.5
			b 0.0
		}

		assertEquals "RGB (R) is not as expected", 1.0, spectrum.RGB.red, 0.00001
		assertEquals "RGB (G) is not as expected", 0.5, spectrum.RGB.green, 0.00001
		assertEquals "RGB (B) is not as expected", 0.0, spectrum.RGB.blue, 0.00001
	}

	@Test
	public void testLinearTextureMapping() {

		final LinearTextureMapping mapping = Txtr.Map.linearMapping {
			min 0.0, 1.0
			max 2.0, 3.0
		}

		assertEquals "Min-U not as expected", 0.0, mapping.getTextureMinU(), 0.00001
		assertEquals "Min-V not as expected", 1.0, mapping.getTextureMinV(), 0.00001
		assertEquals "Max-U not as expected", 2.0, mapping.getTextureMaxU(), 0.00001
		assertEquals "Max-V not as expected", 3.0, mapping.getTextureMaxV(), 0.00001
	}

	@Test
	public void testConstantTexture() {

		final ConstantTexture texture = Txtr.constant {
			constant Spec.rgbSpectrum {
				r 1.0
				g 0.5
				b 0.0
			}
		}

		assertEquals "Texture (R) is not as expected", 1.0, texture.constant.toRGB().red, 0.00001
		assertEquals "Texture (G) is not as expected", 0.5, texture.constant.toRGB().green, 0.00001
		assertEquals "Texture (B) is not as expected", 0.0, texture.constant.toRGB().blue, 0.00001
	}

	@Test
	public void testCheckerboardTexture() {

		final CheckerboardTexture texture = Txtr.checkerboard {
			texture Txtr.constant {
				constant Spec.rgbSpectrum {
					r 1.0
					g 0.5
					b 0.0
				}
			}
			texture Txtr.constant {
				constant Spec.rgbSpectrum { rgb RGB.GREEN }
			}
			mapping Txtr.Map.linearMapping {
				min 0.0, 1.0
				max 2.0, 3.0
			}
		}

		assertEquals "Checkerboard does not have expected number of sub-Textures", 2, texture.getTextures().size()
		assertNotNull "Checkerboard does not have any mapping", texture.getTextureMapping()
		assertTrue "Checkerboard does not have the expected type of mapping", texture.getTextureMapping() instanceof LinearTextureMapping
		assertEquals "Mapping min-U not as expected", 0.0, ((LinearTextureMapping)texture.getTextureMapping()).getTextureMinU(), 0.00001
		assertEquals "Mapping min-V not as expected", 1.0, ((LinearTextureMapping)texture.getTextureMapping()).getTextureMinV(), 0.00001
		assertEquals "Mapping max-U not as expected", 2.0, ((LinearTextureMapping)texture.getTextureMapping()).getTextureMaxU(), 0.00001
		assertEquals "Mapping max-V not as expected", 3.0, ((LinearTextureMapping)texture.getTextureMapping()).getTextureMaxV(), 0.00001
	}

	public void testImageFileTexture() {

		final ImageFileTexture texture = Txtr.imageFile { image "build/resources/test/2x2_test.png" }

		assertEquals "Texture is not pointed at the expected image-file", "2x2_test.png", texture.imageFile.name
		assertNotNull "Texture does not have any mapping", texture.getTextureMapping()
	}
}
