package hr.fer.zemris.java.custom.collections;

import java.util.ConcurrentModificationException;
import java.util.NoSuchElementException;

/**
 * Class that implements a doubly linked list. It supports all basic collection
 * operations.
 * 
 * @author Mauro Staver
 *
 */
public class LinkedListIndexedCollection<E> implements List<E> {
	/**
	 * Class that implements a node in a doubly linked list.
	 * 
	 * @author Mauro Staver
	 *
	 */
	private static class ListNode<E> {
		ListNode<E> prev;
		ListNode<E> next;
		E value;

		/**
		 * Node constructor. Creates a new list node with the given values.
		 * 
		 * @param prev  reference to the previous node
		 * @param next  reference to the next node
		 * @param value value of the node
		 */
		private ListNode(ListNode<E> prev, ListNode<E> next, E value) {
			this.prev = prev;
			this.next = next;
			this.value = value;
		}
	}

	/**
	 * Class that implements the ElementsGetter interface for use on the
	 * LinkedListIndexedCollection objects.
	 * 
	 * @author Mauro Staver
	 */
	private static class ListElementsGetter<E> implements ElementsGetter<E> {

		private ListNode<E> nextNode;
		private long savedModificationCount;
		private LinkedListIndexedCollection<E> list;

		/**
		 * ListElementsGetter constructor. Constructs and initializes a new
		 * ListElementsGetter object.
		 * 
		 * @param list List on which we want to use the ListElementsGetter.
		 */
		public ListElementsGetter(LinkedListIndexedCollection<E> list) {
			this.list = list;
			nextNode = list.first;
			savedModificationCount = list.modificationCount;
		}

		@Override
		public boolean hasNextElement() {
			if (savedModificationCount != list.modificationCount) {
				throw new ConcurrentModificationException("The list has been modified.");
			}

			if (nextNode != null) {
				return true;
			} else {
				return false;
			}
		}

		@Override
		public E getNextElement() throws NoSuchElementException {
			if (savedModificationCount != list.modificationCount) {
				throw new ConcurrentModificationException("The list has been modified.");
			}
			if (hasNextElement() == false) {
				throw new NoSuchElementException("No more elements in ElementsGetter");
			}

			ListNode<E> returnNode = nextNode;
			nextNode = nextNode.next;
			return returnNode.value;
		}

	}

	private int size;
	private long modificationCount = 0;
	private ListNode<E> first;
	private ListNode<E> last;

	@Override
	public ElementsGetter<E> createElementsGetter() {
		return new ListElementsGetter<>(this);
	}

	/**
	 * Constructor. Initializes empty List.
	 */
	public LinkedListIndexedCollection() {
		first = last = null;
		size = 0;
	}

	/**
	 * Constructor. Initializes a linked list with elements from the given
	 * collection.
	 * 
	 * @param other Collection used to initialize a list.
	 */
	public LinkedListIndexedCollection(Collection<? extends E> other) {
		addAll(other);
	}

	@Override
	public int size() {
		return size;
	}

	/**
	 * Adds the given object at the end of this collection. Complexity: O(1)
	 * 
	 * @param value object to be added
	 * @throws NullPointerException if given object is null
	 */
	@Override
	public void add(E value) throws NullPointerException {
		if (value == null) {
			throw new NullPointerException();
		}

		ListNode<E> newNode = new ListNode<>(last, null, value);

		if (this.isEmpty()) {
			first = newNode;
		} else {
			last.next = newNode;
		}

		last = newNode;
		size++;
		modificationCount++;
	}

	@Override
	public boolean contains(Object value) {
		int index = indexOf(value);
		if (index == -1) {
			return false;
		}
		return true;
	}

	/**
	 * If exists, removes the first occurrence of the specified element from this
	 * list.
	 * 
	 * @param value Element to be removed
	 * @return True if successfully removed, false if list doesn't contain the
	 *         specified element.
	 */
	@Override
	public boolean remove(Object value) {
		for (ListNode<E> node = first; node != null; node = node.next) {
			if (value.equals(node.value)) {
				removeNode(node);
				return true;
			}
		}
		return false;
	}

	/**
	 * Removes the given node from list. It is expected that the list contains the
	 * given node. Complexity: O(1)
	 * 
	 * @param node Node to be removed
	 */
	private void removeNode(ListNode<E> node) {
		ListNode<E> nodeBefore = node.prev;
		ListNode<E> nodeAfter = node.next;

		if (node == first && node == last) { // given node is the only element in list
			first = last = null;
		} else if (node == first) { // given node is the first element in list
			nodeAfter.prev = null;
			first = nodeAfter;
		} else if (node == last) { // given node is the last element in list
			nodeBefore.next = null;
			last = nodeBefore;
		} else { // given node is somewhere in the middle of the list
			nodeBefore.next = nodeAfter;
			nodeAfter.prev = nodeBefore;
		}

		size--;
		modificationCount++;
	}

	@Override
	public void remove(int index) throws IndexOutOfBoundsException {
		ListNode<E> node = getNode(index); // node to remove
		removeNode(node);
	}

	@Override
	public Object[] toArray() {
		Object[] array = new Object[size];

		int index = 0;
		for (ListNode<E> node = first; node != null; node = node.next) {
			array[index++] = node.value;
		}

		return array;
	}

	@Override
	public void clear() {
		first = last = null;
		size = 0;
		modificationCount++;
	}

	@Override
	public E get(int index) throws IndexOutOfBoundsException {
		ListNode<E> node = getNode(index);
		return node.value;
	}

	/**
	 * Returns reference to Node stored at position index. Complexity: n/2 + 1
	 * 
	 * @param index position of an object in collection
	 * @return reference to Node stored at position index
	 * @throws IndexOutOfBoundsException if index is not in interval [0, size-1]
	 */
	private ListNode<E> getNode(int index) throws IndexOutOfBoundsException {
		if (index < 0 || index >= size) {
			throw new IndexOutOfBoundsException();
		}

		ListNode<E> iterator;
		int numberOfItems; // number of steps to iterate
		boolean forward; // move forwards or backwards

		if (index < size / 2) {
			iterator = first;
			numberOfItems = index;
			forward = true;
		} else {
			iterator = last;
			numberOfItems = size - index - 1;
			forward = false;
		}

		for (int i = 0; i < numberOfItems; ++i) {
			if (forward == true) {
				iterator = iterator.next; // move forward
			} else {
				iterator = iterator.prev; // move backward
			}
		}
		return iterator;
	}

	@Override
	public void insert(E value, int position) throws IndexOutOfBoundsException {
		if (position == size) { // insert at end
			add(value);
			return;
		} else if (position == 0) { // insert at the beginning
			ListNode<E> newNode = new ListNode<>(null, first, value);

			first.prev = newNode;
			first = newNode;
		} else { // insert somewhere in the middle
			ListNode<E> nodeBefore = getNode(position - 1); // throws exception

			ListNode<E> nodeAfter = nodeBefore.next;
			ListNode<E> newNode = new ListNode<>(nodeBefore, nodeAfter, value);

			nodeBefore.next = newNode;
			nodeAfter.prev = newNode;
		}

		size++;
		modificationCount++;
	}

	@Override
	public int indexOf(Object value) {
		if (value != null) {
			int index = 0;

			for (ListNode<E> node = first; node != null; node = node.next, index++) {
				if (value.equals(node.value)) {
					return index;
				}
			}
		}
		return -1; // not found
	}

}
