package org.rays3d.util;

import java.util.Collection;
import java.util.Iterator;
import java.util.function.Supplier;

/**
 * A FlaggingCollectionDecorator decorates an existing Collection, keeps track
 * of when it is in a modified state, and allows you to atomically
 * read-and-reset that flag.
 * 
 * @author snowjak88
 */
public class FlaggingCollectionDecorator<E> implements Collection<E> {

	private Collection<E>	collection;
	private boolean			modified;

	public FlaggingCollectionDecorator(Supplier<Collection<E>> newCollectionSupplier) {
		this.collection = newCollectionSupplier.get();
		modified = false;
	}

	@Override
	public int size() {

		synchronized (collection) {
			return this.collection.size();
		}
	}

	@Override
	public boolean isEmpty() {

		synchronized (collection) {
			return this.collection.isEmpty();
		}
	}

	@Override
	public boolean contains(Object o) {

		synchronized (collection) {
			return this.collection.contains(o);
		}
	}

	@Override
	public Iterator<E> iterator() {

		return new FlaggingIterator<>(this, collection::iterator);
	}

	@Override
	public Object[] toArray() {

		synchronized (collection) {
			return collection.toArray();
		}
	}

	@Override
	public <T> T[] toArray(T[] a) {

		synchronized (collection) {
			return collection.toArray(a);
		}
	}

	@Override
	public boolean add(E e) {

		synchronized (collection) {
			boolean result = collection.add(e);
			modified = modified | result;
			return result;
		}
	}

	@Override
	public boolean remove(Object o) {

		synchronized (collection) {
			boolean result = collection.remove(o);
			modified = modified | result;
			return result;
		}
	}

	@Override
	public boolean containsAll(Collection<?> c) {

		synchronized (collection) {
			return collection.containsAll(c);
		}
	}

	@Override
	public boolean addAll(Collection<? extends E> c) {

		synchronized (collection) {
			boolean result = collection.addAll(c);
			modified = modified | result;
			return result;
		}
	}

	@Override
	public boolean removeAll(Collection<?> c) {

		synchronized (collection) {
			boolean result = collection.removeAll(c);
			modified = modified | result;
			return result;
		}
	}

	@Override
	public boolean retainAll(Collection<?> c) {

		synchronized (collection) {
			boolean result = collection.retainAll(c);
			modified = modified | result;
			return result;
		}
	}

	@Override
	public void clear() {

		synchronized (collection) {
			modified = true;
			collection.clear();
		}
	}

	/**
	 * Return the value of the "is-modified" flag while resetting it to "false".
	 * 
	 * @return
	 */
	public boolean readAndReset() {

		synchronized (collection) {
			boolean result = modified;
			modified = false;
			return result;
		}
	}

	public static class FlaggingIterator<T> implements Iterator<T> {

		private FlaggingCollectionDecorator<T>	collection;
		private Iterator<T>						iterator;
		private boolean							flag;

		public FlaggingIterator(FlaggingCollectionDecorator<T> collection, Supplier<Iterator<T>> newIteratorSupplier) {
			this.collection = collection;
			this.iterator = newIteratorSupplier.get();
		}

		@Override
		public boolean hasNext() {

			synchronized (iterator) {
				return iterator.hasNext();
			}
		}

		@Override
		public T next() {

			synchronized (iterator) {
				return iterator.next();
			}
		}

		@Override
		public void remove() {

			synchronized (iterator) {
				synchronized (collection) {
					try {
						iterator.remove();
						flag = true;
						collection.modified = true;

					} catch (UnsupportedOperationException e) {
					}
				}
			}
		}

		/**
		 * Return the value of the "is-modified" flag, while resetting the flag
		 * to <code>false</code>
		 * 
		 * @return
		 */
		public boolean readAndReset() {

			synchronized (iterator) {
				boolean result = flag;
				flag = false;
				return result;
			}
		}

	}
}
