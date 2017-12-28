package org.rays3d.message.sample;

import java.io.Serializable;

import org.rays3d.geometry.Point2D;

public class SampleRequest implements Serializable {

	private static final long	serialVersionUID	= -5532500614275211500L;

	private long				renderId;

	private Point2D				filmPoint;
	
	private String				samplerName;
	private int					samplesPerPixel;

	public long getRenderId() {

		return renderId;
	}

	public void setId(long id) {

		this.renderId = id;
	}

	public Point2D getFilmPoint() {

		return filmPoint;
	}

	public void setFilmPoint(Point2D filmPoint) {

		this.filmPoint = filmPoint;
	}

	public String getSamplerName() {

		return samplerName;
	}

	public void setSamplerName(String samplerName) {

		this.samplerName = samplerName;
	}

	public int getSamplesPerPixel() {

		return samplesPerPixel;
	}

	public void setSamplesPerPixel(int samplesPerPixel) {

		this.samplesPerPixel = samplesPerPixel;
	}

}
