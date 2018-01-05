package org.rays3d.groovybuilder.bxdf

import static org.apache.commons.math3.util.FastMath.*

import org.rays3d.groovybuilder.WorldBuilder.Spec
import org.rays3d.groovybuilder.WorldBuilder.Txtr
import org.rays3d.spectrum.RGBSpectrum
import org.rays3d.spectrum.Spectrum
import org.rays3d.texture.ConstantTexture
import org.rays3d.texture.Texture

class LambertianBRDFSpec {

	private Texture texture
	private Texture emissive = null
	private Spectrum emissivePower = RGBSpectrum.BLACK
	
	public static Spec = new Spec()
	public static Txtr = new Txtr()

	public texture(Texture texture) {
		this.texture = texture
	}

	public emissive(Spectrum unitRadiance) {
		this.emissive = new ConstantTexture(unitRadiance)
		this.emissivePower = unitRadiance.multiply(4d * PI)
	}

	public emissive(Texture emissive, Spectrum totalPower) {
		this.emissive = emissive
		this.emissivePower = totalPower
	}
}
