package org.rays3d.builder.java.transform;

import org.rays3d.builder.java.AbstractBuilder;
import org.rays3d.transform.Transform;

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
	public P end() {

		return parentBuilder;
	}

}
