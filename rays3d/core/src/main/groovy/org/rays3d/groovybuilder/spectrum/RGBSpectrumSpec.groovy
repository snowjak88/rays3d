package org.rays3d.groovybuilder.spectrum

import org.rays3d.groovybuilder.WorldBuilder.Spec
import org.rays3d.spectrum.RGB
import org.rays3d.spectrum.RGBSpectrum

class RGBSpectrumSpec {

	private double r, g, b

	public static Spec = new Spec()
	public static RGB = org.rays3d.spectrum.RGB

	public rgb(@DelegatesTo(RGBSpec) Closure cl) {
		def spec = new RGBSpec()
		def code = cl.rehydrate(spec, this, this)
		code.resolveStrategy = Closure.DELEGATE_ONLY
		code()
		r = spec.r
		g = spec.g
		b = spec.b
	}

	public rgb(RGB rgb) {
		r = rgb.red
		g = rgb.green
		b = rgb.blue
	}

	public r(double red) {
		this.r = red
	}

	public g(double green) {
		this.g = green
	}

	public b(double blue) {
		this.b = blue
	}
}
