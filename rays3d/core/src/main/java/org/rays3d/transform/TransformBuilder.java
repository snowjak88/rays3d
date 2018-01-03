package org.rays3d.transform;

import org.rays3d.builder.AbstractBuilder;

public abstract class TransformBuilder<T extends Transform, P extends AbstractBuilder<?, ?>>
		implements AbstractBuilder<T, P> {

	private P parentBuilder;

	public TransformBuilder() {
		this(null);
	}

	public TransformBuilder(P parentBuilder) {
		this.parentBuilder = parentBuilder;
	}

	@Override
	public P getParentBuilder() {

		return parentBuilder;
	}

}
