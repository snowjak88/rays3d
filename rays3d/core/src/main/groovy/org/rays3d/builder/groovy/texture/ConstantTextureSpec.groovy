package org.rays3d.builder.groovy.texture

import org.rays3d.builder.groovy.WorldBuilder.Spec
import org.rays3d.spectrum.Spectrum
import org.rays3d.texture.mapping.TextureMapping

class ConstantTextureSpec {

	private Spectrum constant

	public static Spec = new Spec()

	public spectrum(Spectrum constant){
		this.constant = constant
	}
}
