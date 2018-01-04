package org.rays3d.builder.groovy.texture.mapping

import org.rays3d.texture.mapping.LinearTextureMapping

class LinearTextureMappingSpec {

	private double minU = 0, minV = 0, maxU = 1, maxV = 1

	public minU(double minU) {
		this.minU = minU
	}

	public minV(double minV) {
		this.minV = minV
	}

	public maxU(double maxU) {
		this.maxU = maxU
	}

	public maxV(double maxV) {
		this.maxV = maxV
	}

	public min(double u, double v) {
		this.minU = u
		this.minV = v
	}

	public max(double u, double v) {
		this.maxU = u
		this.maxV = v
	}
}
