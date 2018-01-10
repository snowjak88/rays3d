package org.rays3d.builder.groovy.shape

import org.rays3d.builder.groovy.WorldBuilder.Tfm
import org.rays3d.transform.Transform

class PlaneShapeSpec {

	private List<Transform> worldToLocalTransforms = []
	
	public static Tfm = new Tfm()

	public transform(Transform worldToLocal) {
		this.worldToLocalTransforms << worldToLocal
	}

	public transforms(List<Transform> worldToLocal) {
		this.worldToLocalTransforms.addAll worldToLocal
	}
}
