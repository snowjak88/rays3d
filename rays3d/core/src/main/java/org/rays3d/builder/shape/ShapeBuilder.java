package org.rays3d.builder.shape;

import java.util.Deque;
import java.util.LinkedList;

import org.rays3d.builder.AbstractBuilder;
import org.rays3d.builder.transform.TransformBuilder;
import org.rays3d.builder.transform.TransformableBuilder;
import org.rays3d.shape.Shape;

public abstract class ShapeBuilder<S extends Shape, SB extends ShapeBuilder<S, SB, P>, P extends AbstractBuilder<?, ?>>
		implements TransformableBuilder<S, SB, P> {

	private P								parentBuilder;
	private Deque<TransformBuilder<?, SB>>	worldToLocalBuilders	= new LinkedList<>();

	public ShapeBuilder() {
		this(null);
	}

	public ShapeBuilder(P parentBuilder) {
		this.parentBuilder = parentBuilder;
	}

	// @SuppressWarnings("unchecked")
	@Override
	public Deque<TransformBuilder<?, SB>> getWorldToLocalBuilders() {

		return worldToLocalBuilders;
	}

	@Override
	public P getParentBuilder() {

		return parentBuilder;
	}

}
