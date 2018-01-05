package org.rays3d.javabuilder;

public interface AbstractBuilder<T, P extends AbstractBuilder<?, ?>> {

	/**
	 * Finalize this builder, returning this builder's parent (to continue
	 * building).
	 * 
	 * @return
	 */
	public P end();

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
