package org.rays3d.util;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;

import org.junit.Test;
import org.rays3d.util.FlaggingCollectionDecorator.FlaggingIterator;

public class FlaggingCollectionDecoratorTest {

	@Test
	public void test_unmodifiedCollection() {

		final FlaggingCollectionDecorator<String> collection = new FlaggingCollectionDecorator<>(LinkedList::new);

		assertFalse("Collection should not contain anything.", collection.contains("abc"));
		assertEquals("Collection should read as size 0.", 0, collection.size());
		assertFalse("Collection should not read as modified.", collection.readAndReset());
	}

	@Test
	public void test_addToCollection() {

		final FlaggingCollectionDecorator<String> collection = new FlaggingCollectionDecorator<>(LinkedList::new);

		collection.addAll(Arrays.asList("abc", "def", "ghi"));

		assertTrue("Collection does not contain expected String.", collection.contains("abc"));
		assertEquals("Collection should read as size 3.", 3, collection.size());
		assertTrue("Collection should read as modified.", collection.readAndReset());
		assertFalse("Collection should read as un-modified in subsequent call.", collection.readAndReset());
	}

	@Test
	public void test_removeFromCollection() {

		final FlaggingCollectionDecorator<String> collection = new FlaggingCollectionDecorator<>(LinkedList::new);

		collection.addAll(Arrays.asList("abc", "def", "ghi"));
		assertTrue("Collection should read as modified.", collection.readAndReset());
		assertFalse("Collection should read as un-modified in subsequent call.", collection.readAndReset());

		assertTrue("Collection should be able to remove member String.", collection.remove("abc"));
		assertFalse("Collection should not be able to remove non-member String.", collection.remove("foo"));
		assertTrue("Collection should read as modified.", collection.readAndReset());
		assertFalse("Collection should read as un-modified in subsequent call.", collection.readAndReset());
	}

	@Test
	public void test_removeFromIterator() {

		final FlaggingCollectionDecorator<String> collection = new FlaggingCollectionDecorator<>(LinkedList::new);

		collection.addAll(Arrays.asList("abc", "def", "ghi"));
		assertTrue("Collection should read as modified.", collection.readAndReset());
		assertFalse("Collection should read as un-modified in subsequent call.", collection.readAndReset());

		final Iterator<String> baseIterator = collection.iterator();
		assertTrue("Iterator should be of type FlaggingIterator.", baseIterator instanceof FlaggingIterator);

		final FlaggingIterator<String> iterator = (FlaggingIterator<String>) baseIterator;
		assertTrue("Iterator should be able to get first item.", iterator.hasNext());
		assertEquals("Iterator does not return first item.", "abc", iterator.next());

		assertTrue("Iterator should be able to get second item.", iterator.hasNext());
		assertEquals("Iterator does not return second item.", "def", iterator.next());

		assertTrue("Iterator should be able to get third item.", iterator.hasNext());
		assertEquals("Iterator does not return first item.", "ghi", iterator.next());

		assertFalse("Iterator should read as unmodified.", iterator.readAndReset());

		iterator.remove();
		assertTrue("Iterator should read as modified after removal.", iterator.readAndReset());
		assertFalse("Iterator should read as unmodified in subsequent call.", iterator.readAndReset());

		assertTrue("Collection should read as modified after removal via Iterator.", collection.readAndReset());

	}

}
