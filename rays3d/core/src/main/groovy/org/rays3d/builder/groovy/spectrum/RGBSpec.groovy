package org.rays3d.builder.groovy.spectrum

import org.rays3d.spectrum.RGB


class RGBSpec {

	private double r, g, b
	
	public static RGB = org.rays3d.spectrum.RGB
	
	public r(double red) {
		this.r = red
	}
	
	public g(double green) {
		this.g = green
	}
	
	public b(double blue) {
		this.b = blue
	}
	
	public rgb(RGB rgb) {
		this.r = rgb.red
		this.g = rgb.green
		this.b = rgb.blue
	}

}
