package org.rays3d.groovybuilder

import static org.apache.commons.math3.util.FastMath.*
import static org.junit.Assert.*
import static org.rays3d.groovybuilder.WorldBuilder.*

import org.junit.Test
import org.rays3d.Primitive
import org.rays3d.bxdf.LambertianBRDF
import org.rays3d.bxdf.PerfectSpecularBRDF
import org.rays3d.camera.PinholeCamera
import org.rays3d.geometry.Normal3D
import org.rays3d.geometry.Point2D
import org.rays3d.geometry.Point3D
import org.rays3d.geometry.Vector3D
import org.rays3d.groovybuilder.WorldBuilder.Spec
import org.rays3d.shape.SphereShape
import org.rays3d.spectrum.RGB
import org.rays3d.spectrum.RGBSpectrum
import org.rays3d.texture.CheckerboardTexture
import org.rays3d.texture.ConstantTexture
import org.rays3d.texture.ImageFileTexture
import org.rays3d.texture.mapping.LinearTextureMapping
import org.rays3d.transform.RotationTransform
import org.rays3d.transform.ScaleTransform
import org.rays3d.transform.TranslationTransform
import org.rays3d.world.World


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
			minUV 0.0, 1.0
			maxUV 2.0, 3.0
		}

		assertEquals "Min-U not as expected", 0.0, mapping.getTextureMinU(), 0.00001
		assertEquals "Min-V not as expected", 1.0, mapping.getTextureMinV(), 0.00001
		assertEquals "Max-U not as expected", 2.0, mapping.getTextureMaxU(), 0.00001
		assertEquals "Max-V not as expected", 3.0, mapping.getTextureMaxV(), 0.00001
	}

	@Test
	public void testConstantTexture() {

		final ConstantTexture texture = Txtr.constant {
			spectrum Spec.rgbSpectrum {
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
				spectrum Spec.rgbSpectrum {
					r 1.0
					g 0.5
					b 0.0
				}
			}
			texture Txtr.constant {
				spectrum Spec.rgbSpectrum { rgb RGB.GREEN }
			}
			mapping Txtr.Map.linearMapping {
				minUV 0.0, 1.0
				maxUV 2.0, 3.0
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

	@Test
	public void testImageFileTexture() {

		final ImageFileTexture texture = Txtr.imageFile { image new File("build/resources/test/2x2_test.png") }

		assertEquals "Texture is not pointed at the expected image-file", "2x2_test.png", texture.imageFile.name
		assertNotNull "Texture does not have any mapping", texture.getTextureMapping()
	}

	@Test
	public void testRotateTransform() {

		final RotationTransform transform = Tfm.rotate {
			axis { x 0.5; y 0.5; z 0 }
			degreesOfRotation 45
		}

		assertNotNull "Rotation does not have an axis", transform.getAxis()
		assertEquals "Rotation axis (X) not as expected", 0.5, transform.getAxis().x, 0.00001
		assertEquals "Rotation axis (X) not as expected", 0.5, transform.getAxis().y, 0.00001
		assertEquals "Rotation axis (X) not as expected", 0.0, transform.getAxis().z, 0.00001
		assertEquals "Rotation degrees not as expected", 45.0, transform.getDegreesOfRotation(), 0.00001
	}

	@Test
	public void testScaleTransform() {

		final ScaleTransform transform = Tfm.scale {
			sx 1.0
			sy 2.0
			sz 3.0
		}

		assertEquals "Scale factor (X) not as expected", 1.0, transform.getSx(), 0.00001
		assertEquals "Scale factor (Y) not as expected", 2.0, transform.getSy(), 0.00001
		assertEquals "Scale factor (Z) not as expected", 3.0, transform.getSz(), 0.00001
	}

	@Test
	public void testTranslateTransform() {

		final TranslationTransform transform = Tfm.translate {
			dx 1.0
			dy 2.0
			dz 3.0
		}

		assertEquals "Translation (X) not as expected", 1.0, transform.getDx(), 0.00001
		assertEquals "Translation (Y) not as expected", 2.0, transform.getDy(), 0.00001
		assertEquals "Translation (Z) not as expected", 3.0, transform.getDz(), 0.00001
	}

	@Test
	public void testSphere() {

		final SphereShape sphere = Shape.sphere {
			radius 1.5
			transform Tfm.rotate {
				axis { x 0.5; y 0.5; z 0.0 }
				degreesOfRotation 45
			}
			transform Tfm.translate {
				dx 2
				dy 3
				dz 4
			}
		}

		assertEquals "Sphere does not have expected radius", 1.5, sphere.getRadius(), 0.00001
		assertFalse "Sphere does not have any Transforms", sphere.getWorldToLocalTransforms().isEmpty()
		assertEquals "Sphere does not have expected number of Transforms", 2, sphere.getWorldToLocalTransforms().size()
	}

	@Test
	public void testLambertianBRDF() {

		final LambertianBRDF brdf = BSDF.lambertian {
			texture Txtr.constant {
				spectrum Spec.rgbSpectrum { rgb RGB.GREEN }
			}
			emissive Spec.rgbSpectrum { rgb RGB.WHITE }
		}

		assertTrue "BRDF texture is not a ConstantTexture", brdf.getTexture() instanceof ConstantTexture
		assertEquals "BRDF texture (R) is not as expected", RGB.GREEN.red, ((ConstantTexture) brdf.getTexture()).constant.toRGB().red, 0.00001
		assertEquals "BRDF texture (G) is not as expected", RGB.GREEN.green, ((ConstantTexture) brdf.getTexture()).constant.toRGB().green, 0.00001
		assertEquals "BRDF texture (B) is not as expected", RGB.GREEN.blue, ((ConstantTexture) brdf.getTexture()).constant.toRGB().blue, 0.00001

		assertTrue "BRDF emissive is not a ConstantTexture", brdf.getEmissive() instanceof ConstantTexture
		assertEquals "BRDF emissive (R) is not as expected", RGB.WHITE.red, ((ConstantTexture) brdf.getEmissive()).constant.toRGB().red, 0.00001
		assertEquals "BRDF emissive (G) is not as expected", RGB.WHITE.green, ((ConstantTexture) brdf.getEmissive()).constant.toRGB().green, 0.00001
		assertEquals "BRDF emissive (B) is not as expected", RGB.WHITE.blue, ((ConstantTexture) brdf.getEmissive()).constant.toRGB().blue, 0.00001

		assertEquals "BRDF emissivie power (R) is not correct", RGB.WHITE.red * 4d * PI, brdf.getTotalEmissivePower().toRGB().red, 0.00001
		assertEquals "BRDF emissivie power (G) is not correct", RGB.WHITE.green * 4d * PI, brdf.getTotalEmissivePower().toRGB().green, 0.00001
		assertEquals "BRDF emissivie power (B) is not correct", RGB.WHITE.blue * 4d * PI, brdf.getTotalEmissivePower().toRGB().blue, 0.00001
	}

	@Test
	public void testPerfectSpecularBRDF() {

		final PerfectSpecularBRDF brdf = BSDF.perfectSpecular {
			tint Txtr.constant {
				spectrum Spec.rgbSpectrum { rgb RGB.GREEN }
			}
		}

		assertTrue "BRDF texture is not a ConstantTexture", brdf.getTint() instanceof ConstantTexture
		assertEquals "BRDF texture (R) is not as expected", RGB.GREEN.red, ((ConstantTexture) brdf.getTint()).constant.toRGB().red, 0.00001
		assertEquals "BRDF texture (G) is not as expected", RGB.GREEN.green, ((ConstantTexture) brdf.getTint()).constant.toRGB().green, 0.00001
		assertEquals "BRDF texture (B) is not as expected", RGB.GREEN.blue, ((ConstantTexture) brdf.getTint()).constant.toRGB().blue, 0.00001
	}

	@Test
	public void testPrimitive() {

		final Primitive primitive = _primitive {
			shape Shape.sphere { radius 1.5 }
			bsdf BSDF.lambertian {
				texture Txtr.constant {
					spectrum Spec.rgbSpectrum { rgb RGB.GREEN }
				}
			}
		}

		assertNotNull "Primitive does not have a shape configured", primitive.getShape()
		assertNotNull "Primitive does not have a BDSF configured", primitive.getBsdf()
		assertTrue "Primitive's shape is not a SphereShape", primitive.getShape() instanceof SphereShape
		assertTrue "Primitive's BSDF is not a LambertianBRDF", primitive.getBsdf() instanceof LambertianBRDF
	}

	@Test
	public void testPinholeCamera() {

		final PinholeCamera camera = Cam.pinhole {
			filmSize 100, 200
			imagePlaneSize 4, 8
			eye { x(-1); y 3; z(-5) }
			lookAt { x 2; y 4; z 6 }
			up Vector3D.J
			focalLength 8
		}

		assertEquals "Camera's film-size (X) is not as expected", 100.0, camera.getFilmSizeX(), 0.00001
		assertEquals "Camera's film-size (Y) is not as expected", 200.0, camera.getFilmSizeY(), 0.00001

		assertEquals "Camera's image-plane-size (X) is not as expected", 4.0, camera.getImagePlaneSizeX(), 0.00001
		assertEquals "Camera's image-plane-size (Y) is not as expected", 8.0, camera.getImagePlaneSizeY(), 0.00001

		assertEquals "Camera's eye-point (X) is not as expected", -1.0, camera.eyePoint.x, 0.00001
		assertEquals "Camera's eye-point (Y) is not as expected", 3.0, camera.eyePoint.y, 0.00001
		assertEquals "Camera's eye-point (Z) is not as expected", -5.0, camera.eyePoint.z, 0.00001

		assertEquals "Camera's look-at point (X) is not as expected", 2.0, camera.lookAt.x, 0.00001
		assertEquals "Camera's look-at point (Y) is not as expected", 4.0, camera.lookAt.y, 0.00001
		assertEquals "Camera's look-at point (Z) is not as expected", 6.0, camera.lookAt.z, 0.00001

		assertEquals "Camera's up-vector (X) is not as expected", 0.0, camera.up.x, 0.00001
		assertEquals "Camera's up-vector (Y) is not as expected", 1.0, camera.up.y, 0.00001
		assertEquals "Camera's up-vector (Z) is not as expected", 0.0, camera.up.z, 0.00001

		assertEquals "Camera's focal-length is not as expected", 8.0, camera.getFocalLength(), 0.00001
	}

	@Test
	public void testWorld() {

		final World world = world {
			camera Cam.pinhole {
				filmSize 100, 200
				imagePlaneSize 4, 8
				eye { x(-1); y 3; z(-5) }
				lookAt { x 2; y 4; z 6 }
				up Vector3D.J
				focalLength 8
			}
			primitive {
				shape Shape.sphere {
					radius 1.5
					transform Tfm.translate { dx (-3); dy 0; dz 0 }
				}
				bsdf BSDF.lambertian {
					texture Txtr.constant {
						spectrum Spec.rgbSpectrum { rgb RGB.GREEN }
					}
				}
			}
			primitive {
				shape Shape.sphere {
					radius 1.5
					transform Tfm.translate { dx (+3); dy 0; dz 0 }
				}
				bsdf BSDF.lambertian {
					texture Txtr.constant {
						spectrum Spec.rgbSpectrum { rgb RGB.GREEN }
					}
				}
			}
		}

		assertNotNull "World does not contain a Camera", world.getCamera()
		assertFalse "World does not contain any Primitives", world.getPrimitives().isEmpty()
		assertEquals "World does not contain expected number of Primitives", 2, world.getPrimitives().size()
	}

	@Test
	public void testParseWorld_string() {

		def worldText = '''\
world {
	camera Cam.pinhole {
		filmSize 100, 200
		imagePlaneSize 4, 8
		eye { x(-1); y 3; z(-5) }
		lookAt { x 2; y 4; z 6 }
		up Vector3D.J
		focalLength 8
	}
	primitive {
		shape Shape.sphere {
			radius 1.5
			transform Tfm.translate { dx (-3); dy 0; dz 0 }
		}
		bsdf BSDF.perfectSpecular {
			tint Txtr.constant {
				spectrum Spec.rgbSpectrum { rgb RGB.GREEN }
			}
		}
	}
	primitive {
		shape Shape.sphere {
			radius 0.5
			transform Tfm.translate { dx (+3); dy 0; dz 0 }
		}
		bsdf BSDF.lambertian {
			texture Txtr.imageFile {
				image new File("build/resources/test/2x2_test.png")
				mapping Txtr.Map.linearMapping {
					minUV 0.0, 1.0
					maxUV 2.0, 3.0
				}
			}
		}
	}
}'''

		final World world = parse(worldText)

		assertNotNull "World does not contain a Camera", world.getCamera()
		assertEquals "Camera's film-size (X) is not as expected", 100.0, world.getCamera().getFilmSizeX(), 0.00001
		assertEquals "Camera's film-size (Y) is not as expected", 200.0, world.getCamera().getFilmSizeY(), 0.00001

		assertEquals "Camera's image-plane-size (X) is not as expected", 4.0, world.getCamera().getImagePlaneSizeX(), 0.00001
		assertEquals "Camera's image-plane-size (Y) is not as expected", 8.0, world.getCamera().getImagePlaneSizeY(), 0.00001

		assertEquals "Camera's eye-point (X) is not as expected", -1.0, world.getCamera().eyePoint.x, 0.00001
		assertEquals "Camera's eye-point (Y) is not as expected", 3.0, world.getCamera().eyePoint.y, 0.00001
		assertEquals "Camera's eye-point (Z) is not as expected", -5.0, world.getCamera().eyePoint.z, 0.00001

		assertEquals "Camera's look-at point (X) is not as expected", 2.0, world.getCamera().lookAt.x, 0.00001
		assertEquals "Camera's look-at point (Y) is not as expected", 4.0, world.getCamera().lookAt.y, 0.00001
		assertEquals "Camera's look-at point (Z) is not as expected", 6.0, world.getCamera().lookAt.z, 0.00001

		assertEquals "Camera's up-vector (X) is not as expected", 0.0, world.getCamera().up.x, 0.00001
		assertEquals "Camera's up-vector (Y) is not as expected", 1.0, world.getCamera().up.y, 0.00001
		assertEquals "Camera's up-vector (Z) is not as expected", 0.0, world.getCamera().up.z, 0.00001

		assertTrue "Camera is not an instance of PinholeCamera", world.getCamera() instanceof PinholeCamera
		assertEquals "Camera's focal-length is not as expected", 8.0, ((PinholeCamera)world.getCamera()).getFocalLength(), 0.00001

		assertFalse "World does not contain any Primitives", world.getPrimitives().isEmpty()
		assertEquals "World does not contain expected number of Primitives", 2, world.getPrimitives().size()

		//
		//
		// TESTING PRIMITIVES
		//
		Iterator<Primitive> primitiveIterator = world.getPrimitives().iterator()

		//
		// First primitive
		assertTrue "primitiveIterator does not have first Primitive", primitiveIterator.hasNext()
		Primitive primitive = primitiveIterator.next()

		assertTrue "First primitive's shape is not a sphere", primitive.getShape() instanceof SphereShape
		assertEquals "First primitive's shape's radius is not as expected", 1.5, ((SphereShape) primitive.getShape()).getRadius(), 0.00001
		assertEquals "First primitive's shape does not have expected number of Transforms", 1, primitive.getShape().getWorldToLocalTransforms().size()

		assertTrue "First primitive's BSDF is not a PerfectSpecularBRDF", primitive.getBsdf() instanceof PerfectSpecularBRDF
		assertTrue "First primitive's BRDF does not have expected Constant tint", ((PerfectSpecularBRDF)primitive.getBsdf()).getTint() instanceof ConstantTexture
		assertTrue "First primitive's BRDF does not have expected Constant tint type", ((ConstantTexture) ((PerfectSpecularBRDF)primitive.getBsdf()).getTint()).getConstant() instanceof RGBSpectrum
		assertEquals "First primitive's BRDF (R) is not as expected", 0.0, ((ConstantTexture) ((PerfectSpecularBRDF)primitive.getBsdf()).getTint()).getConstant().toRGB().red, 0.00001
		assertEquals "First primitive's BRDF (G) is not as expected", 1.0, ((ConstantTexture) ((PerfectSpecularBRDF)primitive.getBsdf()).getTint()).getConstant().toRGB().green, 0.00001
		assertEquals "First primitive's BRDF (B) is not as expected", 0.0, ((ConstantTexture) ((PerfectSpecularBRDF)primitive.getBsdf()).getTint()).getConstant().toRGB().blue, 0.00001

		//
		// Second primitive
		assertTrue "primitiveIterator does not have second Primitive", primitiveIterator.hasNext()
		primitive = primitiveIterator.next()

		assertTrue "Second primitive's shape is not a sphere", primitive.getShape() instanceof SphereShape
		assertEquals "Second primitive's shape's radius is not as expected", 0.5, ((SphereShape) primitive.getShape()).getRadius(), 0.00001
		assertEquals "Second primitive's shape does not have expected number of Transforms", 1, primitive.getShape().getWorldToLocalTransforms().size()

		assertTrue "Second primitive's BSDF is not a LambertianBRDF", primitive.getBsdf() instanceof LambertianBRDF
		assertTrue "Second primitive's BRDF does not have expected Image texture", ((LambertianBRDF)primitive.getBsdf()).getTexture() instanceof ImageFileTexture
		assertTrue "Second primitive's BRDF's texture does not have expected Image file-name", ((ImageFileTexture) ((LambertianBRDF)primitive.getBsdf()).getTexture()).getImageFile().name == "2x2_test.png"
		assertTrue "Second primitive's BRDF's texture does not have expected Mapping type", ((ImageFileTexture) ((LambertianBRDF)primitive.getBsdf()).getTexture()).getTextureMapping() instanceof LinearTextureMapping
		assertEquals "Second primitive's BRDF's texture-mapping does not have expected Min-U", 0.0, ((LinearTextureMapping) ((ImageFileTexture) ((LambertianBRDF)primitive.getBsdf()).getTexture()).getTextureMapping()).getTextureMinU(), 0.00001
		assertEquals "Second primitive's BRDF's texture-mapping does not have expected Min-V", 1.0, ((LinearTextureMapping) ((ImageFileTexture) ((LambertianBRDF)primitive.getBsdf()).getTexture()).getTextureMapping()).getTextureMinV(), 0.00001
		assertEquals "Second primitive's BRDF's texture-mapping does not have expected Max-U", 2.0, ((LinearTextureMapping) ((ImageFileTexture) ((LambertianBRDF)primitive.getBsdf()).getTexture()).getTextureMapping()).getTextureMaxU(), 0.00001
		assertEquals "Second primitive's BRDF's texture-mapping does not have expected Max-V", 3.0, ((LinearTextureMapping) ((ImageFileTexture) ((LambertianBRDF)primitive.getBsdf()).getTexture()).getTextureMapping()).getTextureMaxV(), 0.00001
	}
}
