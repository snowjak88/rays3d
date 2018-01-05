package org.rays3d.builder.groovy.transform

import org.rays3d.builder.groovy.geometry.CoordTrioSpec
import org.rays3d.geometry.Vector3D

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
