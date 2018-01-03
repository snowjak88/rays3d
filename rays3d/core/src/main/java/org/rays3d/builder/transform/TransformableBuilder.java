package org.rays3d.builder.transform;

import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import org.rays3d.builder.AbstractBuilder;
import org.rays3d.transform.RotationTransform;
import org.rays3d.transform.ScaleTransform;
import org.rays3d.transform.Transform;
import org.rays3d.transform.Transformable;
import org.rays3d.transform.TranslationTransform;

public interface TransformableBuilder<T extends Transformable, TB extends TransformableBuilder<T, TB, P>, P extends AbstractBuilder<?, ?>>
		extends AbstractBuilder<T, P> {

	/**
	 * Return this builder's current list of configured
	 * {@link TransformBuilder}s.
	 * 
	 * @return
	 */
	public Deque<TransformBuilder<?, TB>> getWorldToLocalBuilders();

	/**
	 * Configure a new {@link RotationTransform} on this transformable object.
	 * 
	 * @return
	 */
	public default RotationTransformBuilder<TB> rotate() {

		@SuppressWarnings("unchecked")
		final RotationTransformBuilder<TB> builder = new RotationTransformBuilder<>((TB) this);
		getWorldToLocalBuilders().addLast(builder);
		return builder;
	}

	/**
	 * Configure a new {@link ScaleTransform} on this transformable object.
	 * 
	 * @return
	 */
	public default ScaleTransformBuilder<TB> scale() {

		@SuppressWarnings("unchecked")
		final ScaleTransformBuilder<TB> builder = new ScaleTransformBuilder<>((TB) this);
		getWorldToLocalBuilders().addLast(builder);
		return builder;
	}

	/**
	 * Configure a new {@link TranslationTransform} on this transformable
	 * object.
	 * 
	 * @return
	 */
	public default TranslationTransformBuilder<TB> translate() {

		@SuppressWarnings("unchecked")
		final TranslationTransformBuilder<TB> builder = new TranslationTransformBuilder<>((TB) this);
		getWorldToLocalBuilders().addLast(builder);
		return builder;
	}

	/**
	 * Build the list of configured {@link TransformableBuilder}s and return it.
	 * 
	 * @return
	 */
	public default List<Transform> buildWorldToLocalTransforms() {

		return getWorldToLocalBuilders().stream().map(b -> b.build()).collect(Collectors.toCollection(LinkedList::new));
	}

}
