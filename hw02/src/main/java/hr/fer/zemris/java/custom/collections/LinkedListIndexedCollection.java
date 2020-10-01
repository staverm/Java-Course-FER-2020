package hr.fer.zemris.java.custom.collections;

/**
 * Class that implements a doubly linked list.
 * It supports all basic collection operations.
 * @author Mauro Staver
 *
 */
public class LinkedListIndexedCollection extends Collection{
	/**
	 * Class that implements a node in a doubly linked list.
	 * @author Mauro Staver
	 *
	 */
	private static class ListNode{
		ListNode prev;
		ListNode next;
		Object value;
		
		/**
		 * Node constructor.
		 * Creates a new list node with the given values.
		 * @param prev reference to the previous node
		 * @param next reference to the next node
		 * @param value value of the node
		 */
		private ListNode(ListNode prev, ListNode next, Object value){
			this.prev = prev;
			this.next = next;
			this.value = value;
		}
	}
	
	private int size;
	private ListNode first;
	private ListNode last;
	
	/**
	 * Constructor.
	 * Initializes empty List.
	 */
	public LinkedListIndexedCollection() {
		first = last = null;
		size = 0;
	}
	
	/**
	 * Constructor.
	 * Initializes a linked list with elements from the given collection.
	 * @param other Collection used to initialize a list. 
	 */
	public LinkedListIndexedCollection(Collection other) {
		addAll(other);
	}
	
	@Override
	public int size() { 
		return size;
	}
	
	/**
	 * Adds the given object at the end of this collection.
	 * Complexity: O(1)
	 * @param value object to be added
	 * @throws NullPointerException if given object is null
	 */
	@Override
	public void add(Object value) throws NullPointerException{
		if(value == null) {
			throw new NullPointerException();
		}
		
		ListNode newNode = new ListNode(last,null,value);
		
		if(this.isEmpty()) {
			first = newNode;
		}else {
			last.next = newNode;
		}
		
		last = newNode;
		size++;
	}
	
	@Override
	public boolean contains(Object value) {
		int index = indexOf(value);
		if(index == -1) {
			return false;
		}
		return true;
	}
	
	/**
	 * If exists, removes the first instance of the given object.
	 * @param value Object to be removed
	 * @return true if successfully removed, false if the given object doesn't exist
	 */
	@Override
	public boolean remove(Object value) {
		for(ListNode node = first; node != null; node = node.next) {
			if(value.equals(node.value)) {
				removeNode(node);
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Removes the given node from list.
	 * It is expected that the list contains the given node.
	 * Complexity: O(1)
	 * @param node Node to be removed
	 */
	private void removeNode(ListNode node) {
		ListNode nodeBefore = node.prev;
		ListNode nodeAfter = node.next;
		
		if(node == first && node == last) { //given node is the only element in list
			first = last = null;
		}else if(node == first){ //given node is the first element in list
			nodeAfter.prev = null;
			first = nodeAfter;
		}else if(node == last) { //given node is the last element in list
			nodeBefore.next = null;
			last = nodeBefore;
		}else {					//given node is somewhere in the middle of the list
			nodeBefore.next = nodeAfter;
			nodeAfter.prev = nodeBefore;
		}
		
		size--;
	}
	
	/**
	 * Removes element at specified index from collection.
	 * Complexity: n/2+1
	 * @param index position of element we want to remove
	 * @throws IndexOutOfBoundsException if position is not in interval [0, size-1]
	 */
	public void remove(int index) throws IndexOutOfBoundsException{		
		ListNode node = getNode(index); //node to remove
		removeNode(node);
	}
	
	/**
	 * Transforms List into an array of objects and returns that array.
	 * @return array consisting of objects from list
	 */
	@Override
	public Object[] toArray() {		
		Object[] array = new Object[size];
		
		int index = 0;
		for(ListNode node = first; node != null; node = node.next) {
			array[index++] = node.value;
		}
		
		return array;
	}
	
	@Override
	public void forEach(Processor processor) {
		for(ListNode node = first; node != null; node = node.next) {
			processor.process(node.value);
		}
	}
	
	@Override
	public void clear() {
		first = last = null;
		size = 0;
	}
	
	/**
	 * Returns the object stored at position index.
	 * Complexity:  n/2 + 1
	 * @param index position of an object in collection
	 * @return object stored at position index
	 * @throws IndexOutOfBoundsException if index is not in interval [0, size-1]
	 */
	public Object get(int index) throws IndexOutOfBoundsException {		
		ListNode node = getNode(index);
		return node.value;
	}
	
	/**
	 * Returns reference to Node stored at position index.
	 * Complexity:  n/2 + 1
	 * @param index position of an object in collection
	 * @return reference to Node stored at position index
	 * @throws IndexOutOfBoundsException if index is not in interval [0, size-1]
	 */
	private ListNode getNode(int index) throws IndexOutOfBoundsException {
		if(index < 0 || index >= size) {
			throw new IndexOutOfBoundsException();
		}
		
		ListNode iterator;
		int numberOfItems; //number of steps to iterate
		boolean forward; //move forwards or backwards
		
		if(index < size/2) {
			iterator = first;
			numberOfItems = index;
			forward = true;
		}else {
			iterator = last;
			numberOfItems = size - index - 1;
			forward = false;
		}
		
		for(int i = 0; i < numberOfItems; ++i) {
			if(forward == true) { 
				iterator = iterator.next; //move forward
			}else {
				iterator = iterator.prev; //move backward
			}
		}
		return iterator;
	}
	
	/**
	 * Inserts the given object at the given position in linked-list.
	 * Complexity: n/2+1
	 * @param value Object to be inserted
	 * @param position position where we want to insert 
	 * @throws IndexOutOfBoundsException if position is not in interval [0, size]
	 */
	public void insert(Object value, int position) throws IndexOutOfBoundsException {		
		if(position == size) { //insert at end
			add(value);
		}else if(position == 0) { //insert at the beginning
			ListNode newNode = new ListNode(null,first, value);
			
			first.prev = newNode;
			first = newNode;
			size++;
		}else { //insert somewhere in the middle
			ListNode nodeBefore = getNode(position-1); //throws exception
			
			ListNode nodeAfter = nodeBefore.next;
			ListNode newNode = new ListNode(nodeBefore, nodeAfter, value);
			
			nodeBefore.next = newNode;
			nodeAfter.prev = newNode;
			size++;
		}
		
	}
	
	/**
	 * Searches the collection and returns the index of the first occurrence of
	 *  the given object or -1 if not found.
	 *  Complexity: O(n)
	 * @param value object we are searching for
	 * @return index of the first occurrence of the given object or -1 if not found
	 */
	public int indexOf(Object value) {
		if(value != null) {			
			int index = 0;
			
			for(ListNode node = first; node != null; node = node.next, index++) {
				if(value.equals(node.value)) {
					return index;
				}
			}
		}
		return -1; //not found
	}
	
}
