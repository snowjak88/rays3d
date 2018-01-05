package org.rays3d.builder.groovy

import org.rays3d.Primitive
import org.rays3d.builder.groovy.WorldBuilder.Cam
import org.rays3d.camera.Camera

class WorldSpec {

	private Camera camera
	private List<Primitive> primitives = []

	public static Cam = new Cam()
	
	public camera(Camera camera) {
		this.camera = camera
	}

	public primitive(@DelegatesTo(PrimitiveSpec) Closure cl) {
		def spec = new PrimitiveSpec()
		def code = cl.rehydrate(spec, this, this)
		code.resolveStrategy = Closure.DELEGATE_ONLY
		code()
		primitives << new Primitive(spec.shape, spec.bsdf)
	}
}
