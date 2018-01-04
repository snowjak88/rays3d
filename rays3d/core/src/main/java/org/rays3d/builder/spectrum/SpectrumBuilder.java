package org.rays3d.builder.spectrum;

import org.rays3d.builder.AbstractBuilder;
import org.rays3d.spectrum.Spectrum;

public abstract class SpectrumBuilder<S extends Spectrum, P extends AbstractBuilder<?, ?>>
		implements AbstractBuilder<S, P> {

	private P parentBuilder;

	public SpectrumBuilder() {
		this(null);
	}

	public SpectrumBuilder(P parentBuilder) {
		this.parentBuilder = parentBuilder;
	}

	@Override
	public P end() {

		return parentBuilder;
	}

}
