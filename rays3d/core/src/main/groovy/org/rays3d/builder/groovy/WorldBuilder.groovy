package org.rays3d.builder.groovy

import org.rays3d.builder.groovy.geometry.CoordPairSpec
import org.rays3d.builder.groovy.geometry.CoordTrioSpec
import org.rays3d.builder.groovy.spectrum.RGBSpec
import org.rays3d.builder.groovy.spectrum.RGBSpectrumSpec
import org.rays3d.builder.groovy.texture.CheckerboardTextureSpec
import org.rays3d.builder.groovy.texture.ConstantTextureSpec
import org.rays3d.builder.groovy.texture.ImageFileTextureSpec
import org.rays3d.builder.groovy.texture.mapping.LinearTextureMappingSpec
import org.rays3d.geometry.Normal3D
import org.rays3d.geometry.Point2D
import org.rays3d.geometry.Point3D
import org.rays3d.geometry.Vector3D
import org.rays3d.spectrum.RGB
import org.rays3d.spectrum.RGBSpectrum
import org.rays3d.texture.CheckerboardTexture
import org.rays3d.texture.ConstantTexture
import org.rays3d.texture.ImageFileTexture
import org.rays3d.texture.Texture
import org.rays3d.texture.mapping.LinearTextureMapping

class WorldBuilder {

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
			return new Normal3D(trio.x, trio.y, trio.z)
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

	static class Txtr {

		def static ConstantTexture constant(@DelegatesTo(ConstantTextureSpec) Closure cl) {
			def spec = new ConstantTextureSpec()
			def code = cl.rehydrate(spec, this, this)
			code.resolveStrategy = Closure.DELEGATE_ONLY
			code()
			return new ConstantTexture(spec.constant)
		}

		def static CheckerboardTexture checkerboard(@DelegatesTo(CheckerboardTextureSpec) Closure cl) {
			def spec = new CheckerboardTextureSpec()
			def code = cl.rehydrate(spec, this, this)
			code.resolveStrategy = Closure.DELEGATE_ONLY
			code()
			return new CheckerboardTexture(spec.textures.toArray(new Texture[0]), spec.mapping)
		}

		def static ImageFileTexture imageFile(@DelegatesTo(ImageFileTextureSpec) Closure cl) {
			def spec = new ImageFileTextureSpec()
			def code = cl.rehydrate(spec, this, this)
			code.resolveStrategy = Closure.DELEGATE_ONLY
			code()
			return new ImageFileTexture(spec.imageFile, spec.mapping)
		}

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
