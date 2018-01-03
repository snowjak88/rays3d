package org.rays3d.spectrum;

import org.rays3d.builder.AbstractBuilder;

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
	public P getParentBuilder() {

		return parentBuilder;
	}

}
