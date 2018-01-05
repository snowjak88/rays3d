package org.rays3d.groovybuilder.shape

import org.rays3d.groovybuilder.WorldBuilder.Tfm
import org.rays3d.transform.Transform

class SphereShapeSpec {

	private double radius = 1.0
	private List<Transform> worldToLocalTransforms = []
	
	public static Tfm = new Tfm()

	public radius(double radius) {
		this.radius = radius
	}

	public transform(Transform worldToLocal) {
		this.worldToLocalTransforms << worldToLocal
	}

	public transforms(List<Transform> worldToLocal) {
		this.worldToLocalTransforms.addAll worldToLocal
	}
}
