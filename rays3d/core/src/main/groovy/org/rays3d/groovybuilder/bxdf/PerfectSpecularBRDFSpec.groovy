package org.rays3d.groovybuilder.bxdf

import static org.apache.commons.math3.util.FastMath.*

import org.rays3d.groovybuilder.WorldBuilder.Txtr
import org.rays3d.spectrum.RGBSpectrum
import org.rays3d.spectrum.Spectrum
import org.rays3d.texture.ConstantTexture
import org.rays3d.texture.Texture

class PerfectSpecularBRDFSpec {

	private Texture tint = new ConstantTexture(RGBSpectrum.WHITE)
	
	public static Txtr = new Txtr()

	public tint(Texture tint) {
		this.tint = tint
	}
}
