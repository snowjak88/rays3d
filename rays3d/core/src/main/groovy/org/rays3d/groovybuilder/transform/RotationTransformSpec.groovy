package org.rays3d.groovybuilder.transform

import org.rays3d.geometry.Vector3D
import org.rays3d.groovybuilder.geometry.CoordTrioSpec

import groovy.lang.Closure
import groovy.lang.DelegatesTo

class RotationTransformSpec {

	private Vector3D axis
	private double degreesOfRotation

	public axis(Vector3D axis) {
		this.axis = axis
	}

	public axis(@DelegatesTo(CoordTrioSpec) Closure cl) {
		def trio = new CoordTrioSpec()
		def code = cl.rehydrate(trio, this, this)
		code.resolveStrategy = Closure.DELEGATE_ONLY
		code()
		this.axis = new Vector3D(trio.x, trio.y, trio.z)
	}

	public degreesOfRotation(double degreesOfRotation) {
		this.degreesOfRotation = degreesOfRotation
	}
}
