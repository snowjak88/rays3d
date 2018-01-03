package org.rays3d.builder;

public interface AbstractBuilder<T, P extends AbstractBuilder<?, ?>> {

	/**
	 * Return this builder's configured parent-builder (or <code>null</code> if
	 * this builder has no parent).
	 * 
	 * @return
	 */
	public P getParentBuilder();

	/**
	 * Finalize this builder, returning this builder's parent (to continue
	 * building).
	 * 
	 * @return
	 * @throws BuilderException
	 *             if any exception has been encountered while building the
	 *             instance
	 */
	public default P end() throws BuilderException {

		return getParentBuilder();
	}

	/**
	 * Finalize this builder, returning a new instance.
	 * 
	 * @return
	 * @throws BuilderException
	 *             if any exception has been encountered while building the
	 *             instance
	 */
	public abstract T build() throws BuilderException;
}
