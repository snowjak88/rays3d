package org.rays3d.builder.groovy
import java.lang.reflect.Modifier

import org.codehaus.groovy.control.CompilerConfiguration
import org.rays3d.Primitive
import org.rays3d.builder.groovy.bxdf.LambertianBRDFSpec
import org.rays3d.builder.groovy.bxdf.PerfectSpecularBRDFSpec
import org.rays3d.builder.groovy.camera.PinholeCameraSpec
import org.rays3d.builder.groovy.geometry.CoordPairSpec
import org.rays3d.builder.groovy.geometry.CoordTrioSpec
import org.rays3d.builder.groovy.shape.PlaneShapeSpec
import org.rays3d.builder.groovy.shape.SphereShapeSpec
import org.rays3d.builder.groovy.spectrum.RGBSpec
import org.rays3d.builder.groovy.spectrum.RGBSpectrumSpec
import org.rays3d.builder.groovy.texture.CheckerboardTextureSpec
import org.rays3d.builder.groovy.texture.ConstantTextureSpec
import org.rays3d.builder.groovy.texture.ImageFileTextureSpec
import org.rays3d.builder.groovy.texture.mapping.LinearTextureMappingSpec
import org.rays3d.builder.groovy.transform.RotationTransformSpec
import org.rays3d.builder.groovy.transform.ScaleTransformSpec
import org.rays3d.builder.groovy.transform.TranslationTransformSpec
import org.rays3d.bxdf.LambertianBRDF
import org.rays3d.bxdf.PerfectSpecularBRDF
import org.rays3d.camera.PinholeCamera
import org.rays3d.geometry.Normal3D
import org.rays3d.geometry.Point2D
import org.rays3d.geometry.Point3D
import org.rays3d.geometry.Vector3D
import org.rays3d.shape.PlaneShape
import org.rays3d.shape.SphereShape
import org.rays3d.spectrum.RGB
import org.rays3d.spectrum.RGBSpectrum
import org.rays3d.texture.CheckerboardTexture
import org.rays3d.texture.ConstantTexture
import org.rays3d.texture.ImageFileTexture
import org.rays3d.texture.Texture
import org.rays3d.texture.mapping.LinearTextureMapping
import org.rays3d.transform.RotationTransform
import org.rays3d.transform.ScaleTransform
import org.rays3d.transform.TranslationTransform
import org.rays3d.world.World

class WorldBuilder {

	def static World parse(String worldScriptText) {

		CompilerConfiguration config = new CompilerConfiguration(CompilerConfiguration.DEFAULT)
		config.setScriptBaseClass(DelegatingScript.class.getName())

		GroovyShell sh = new GroovyShell(WorldBuilder.class.getClassLoader(), new Binding(), config)

		DelegatingScript script = (DelegatingScript) sh.parse(worldScriptText)
		script.setDelegate(new WorldBuilder())
		script.run()
	}

	def static World world(@DelegatesTo(WorldSpec) Closure cl) {
		def spec = new WorldSpec()
		def code = cl.rehydrate(spec, this, this)
		code.resolveStrategy = Closure.DELEGATE_ONLY
		code()

		final World world = new World()
		world.camera = spec.camera
		world.primitives.addAll spec.primitives
		world
	}

	def static Primitive _primitive(@DelegatesTo(PrimitiveSpec) Closure cl) {
		def spec = new PrimitiveSpec()
		def code = cl.rehydrate(spec, this, this)
		code.resolveStrategy = Closure.DELEGATE_ONLY
		code()
		new Primitive(spec.shape, spec.bsdf)
	}

	static class BSDF {

		def static LambertianBRDF lambertian(@DelegatesTo(LambertianBRDFSpec) Closure cl) {
			def spec = new LambertianBRDFSpec()
			def code = cl.rehydrate(spec, this, this)
			code.resolveStrategy = Closure.DELEGATE_ONLY
			code()
			new LambertianBRDF(spec.texture, spec.emissive, spec.emissivePower)
		}

		def static PerfectSpecularBRDF perfectSpecular(@DelegatesTo(PerfectSpecularBRDFSpec) Closure cl) {
			def spec = new PerfectSpecularBRDFSpec()
			def code = cl.rehydrate(spec, this, this)
			code.resolveStrategy = Closure.DELEGATE_ONLY
			code()
			new PerfectSpecularBRDF(spec.tint)
		}
	}

	static class Cam {

		def static PinholeCamera pinhole(@DelegatesTo(PinholeCameraSpec) Closure cl) {
			def spec = new PinholeCameraSpec()
			def code = cl.rehydrate(spec, this, this)
			code.resolveStrategy = Closure.DELEGATE_ONLY
			code()
			new PinholeCamera(spec.filmSizeX, spec.filmSizeY, spec.imagePlaneSizeX, spec.imagePlaneSizeY, spec.eyePoint, spec.lookAt, spec.up, spec.focalLength)
		}
	}

	static class Geo {

		def static Point2D point2d(@DelegatesTo(CoordPairSpec) Closure cl) {
			def pair = new CoordPairSpec()
			def code = cl.rehydrate(pair, this, this)
			code.resolveStrategy = Closure.DELEGATE_ONLY
			code()
			new Point2D(pair.x, pair.y)
		}

		def static Point3D point3d(@DelegatesTo(CoordTrioSpec) Closure cl) {
			def trio = new CoordTrioSpec()
			def code = cl.rehydrate(trio, this, this)
			code.resolveStrategy = Closure.DELEGATE_ONLY
			code()
			new Point3D(trio.x, trio.y, trio.z)
		}

		def static Vector3D vector3d(@DelegatesTo(CoordTrioSpec) Closure cl) {
			def trio = new CoordTrioSpec()
			def code = cl.rehydrate(trio, this, this)
			code.resolveStrategy = Closure.DELEGATE_ONLY
			code()
			new Vector3D(trio.x, trio.y, trio.z)
		}

		def static Normal3D normal3d(@DelegatesTo(CoordTrioSpec) Closure cl) {
			def trio = new CoordTrioSpec()
			def code = cl.rehydrate(trio, this, this)
			code.resolveStrategy = Closure.DELEGATE_ONLY
			code()
			new Normal3D(trio.x, trio.y, trio.z)
		}
	}

	static class Shape {

		def static SphereShape sphere(@DelegatesTo(SphereShapeSpec) Closure cl) {
			def spec = new SphereShapeSpec()
			def code = cl.rehydrate(spec, this, this)
			code.resolveStrategy = Closure.DELEGATE_ONLY
			code()
			new SphereShape(spec.radius, spec.worldToLocalTransforms)
		}

		def static PlaneShape plane(@DelegatesTo(PlaneShapeSpec) Closure cl) {
			def spec = new PlaneShapeSpec()
			def code = cl.rehydrate(spec, this, this)
			code.resolveStrategy = Closure.DELEGATE_ONLY
			code()
			new PlaneShape(spec.worldToLocalTransforms)
		}
	}

	static class Spec {

		def static RGB rgb(@DelegatesTo(RGBSpec) Closure cl) {
			def spec = new RGBSpec()
			def code = cl.rehydrate(spec, this, this)
			code.resolveStrategy = Closure.DELEGATE_ONLY
			code()
			new RGB(spec.r, spec.g, spec.b)
		}

		def static RGBSpectrum rgbSpectrum(@DelegatesTo(RGBSpectrumSpec) Closure cl) {
			def spec = new RGBSpectrumSpec()
			def code = cl.rehydrate(spec, this, this)
			code.resolveStrategy = Closure.DELEGATE_ONLY
			code()
			new RGBSpectrum(new RGB(spec.r, spec.g, spec.b))
		}
	}

	static class Tfm {

		def static RotationTransform rotate(@DelegatesTo(RotationTransformSpec) Closure cl) {
			def spec = new RotationTransformSpec()
			def code = cl.rehydrate(spec, this, this)
			code.resolveStrategy = Closure.DELEGATE_ONLY
			code()
			new RotationTransform(spec.axis, spec.degreesOfRotation)
		}

		def static ScaleTransform scale(@DelegatesTo(ScaleTransformSpec) Closure cl) {
			def spec = new ScaleTransformSpec()
			def code = cl.rehydrate(spec, this, this)
			code.resolveStrategy = Closure.DELEGATE_ONLY
			code()
			new ScaleTransform(spec.sx, spec.sy, spec.sz)
		}

		def static TranslationTransform translate(@DelegatesTo(TranslationTransformSpec) Closure cl) {
			def spec = new TranslationTransformSpec()
			def code = cl.rehydrate(spec, this, this)
			code.resolveStrategy = Closure.DELEGATE_ONLY
			code()
			new TranslationTransform(spec.dx, spec.dy, spec.dz)
		}
	}

	static class Txtr {

		def static ConstantTexture constant(@DelegatesTo(ConstantTextureSpec) Closure cl) {
			def spec = new ConstantTextureSpec()
			def code = cl.rehydrate(spec, this, this)
			code.resolveStrategy = Closure.DELEGATE_ONLY
			code()
			new ConstantTexture(spec.constant)
		}

		def static CheckerboardTexture checkerboard(@DelegatesTo(CheckerboardTextureSpec) Closure cl) {
			def spec = new CheckerboardTextureSpec()
			def code = cl.rehydrate(spec, this, this)
			code.resolveStrategy = Closure.DELEGATE_ONLY
			code()
			new CheckerboardTexture(spec.textures.toArray(new Texture[0]), spec.mapping)
		}

		def static ImageFileTexture imageFile(@DelegatesTo(ImageFileTextureSpec) Closure cl) {
			def spec = new ImageFileTextureSpec()
			def code = cl.rehydrate(spec, this, this)
			code.resolveStrategy = Closure.DELEGATE_ONLY
			code()
			new ImageFileTexture(spec.imageFile, spec.mapping)
		}

		def static Map = new Txtr.Map()
		static class Map {

			def static LinearTextureMapping linearMapping(@DelegatesTo(LinearTextureMappingSpec) Closure cl) {
				def spec = new LinearTextureMappingSpec()
				def code = cl.rehydrate(spec, this, this)
				code.resolveStrategy = Closure.DELEGATE_ONLY
				code()
				new LinearTextureMapping(spec.minU, spec.minV, spec.maxU, spec.maxV)
			}
		}
	}
}
