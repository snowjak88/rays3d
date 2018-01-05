package org.rays3d.groovybuilder

import org.rays3d.bxdf.BSDF
import org.rays3d.shape.Shape

class PrimitiveSpec {

	private BSDF bsdf
	private Shape shape

	public static Shape = new org.rays3d.groovybuilder.WorldBuilder.Shape()
	public static BSDF = new org.rays3d.groovybuilder.WorldBuilder.BSDF()

	public bsdf(BSDF bsdf) {
		this.bsdf = bsdf
	}

	public shape(Shape shape) {
		this.shape = shape
	}
}
