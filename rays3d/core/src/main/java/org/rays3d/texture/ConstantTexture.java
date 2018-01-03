package org.rays3d.texture;

import org.rays3d.interact.DescribesSurface;
import org.rays3d.interact.SurfaceDescriptor;
import org.rays3d.spectrum.Spectrum;
import org.rays3d.texture.mapping.LinearTextureMapping;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * A "constant" texture is simply one color, all the time.
 * 
 * @author snowjak88
 */
public class ConstantTexture extends Texture {

	@JsonProperty
	private Spectrum constant;

	public ConstantTexture(Spectrum constant) {
		super(new LinearTextureMapping());

		this.constant = constant;
	}

	@Override
	public <T extends DescribesSurface> Spectrum evaluate(SurfaceDescriptor<T> surface) {

		return constant;
	}

	public Spectrum getConstant() {

		return constant;
	}

	protected void setConstant(Spectrum constant) {

		this.constant = constant;
	}

}
