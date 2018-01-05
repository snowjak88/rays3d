package org.rays3d.groovybuilder.camera

import org.rays3d.geometry.Point3D
import org.rays3d.geometry.Vector3D
import org.rays3d.groovybuilder.WorldBuilder.Geo
import org.rays3d.groovybuilder.geometry.CoordTrioSpec

import groovy.lang.Closure
import groovy.lang.DelegatesTo

class PinholeCameraSpec {

	private double filmSizeX, filmSizeY
	private double imagePlaneSizeX, imagePlaneSizeY
	private Point3D eyePoint, lookAt
	private Vector3D up
	private double focalLength
	
	public static Geo = new Geo()
	public static Vector3D = org.rays3d.geometry.Vector3D

	public filmSize(double sizeX, double sizeY) {
		this.filmSizeX = sizeX
		this.filmSizeY = sizeY
	}

	public imagePlaneSize(double sizeX, double sizeY) {
		this.imagePlaneSizeX = sizeX
		this.imagePlaneSizeY = sizeY
	}

	public eye(@DelegatesTo(CoordTrioSpec) Closure cl) {
		def trio = new CoordTrioSpec()
		def code = cl.rehydrate(trio, this, this)
		code.resolveStrategy = Closure.DELEGATE_ONLY
		code()
		this.eyePoint = new Point3D(trio.x, trio.y, trio.z)
	}

	public lookAt(@DelegatesTo(CoordTrioSpec) Closure cl) {
		def trio = new CoordTrioSpec()
		def code = cl.rehydrate(trio, this, this)
		code.resolveStrategy = Closure.DELEGATE_ONLY
		code()
		this.lookAt = new Point3D(trio.x, trio.y, trio.z)
	}

	public up(Vector3D up) {
		this.up = up
	}

	public up(@DelegatesTo(CoordTrioSpec) Closure cl) {
		def trio = new CoordTrioSpec()
		def code = cl.rehydrate(trio, this, this)
		code.resolveStrategy = Closure.DELEGATE_ONLY
		code()
		this.up = new Vector3D(trio.x, trio.y, trio.z)
	}

	public focalLength(double focalLength) {
		this.focalLength = focalLength
	}
}
