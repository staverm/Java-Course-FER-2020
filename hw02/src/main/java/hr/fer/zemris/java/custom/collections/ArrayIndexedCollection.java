package hr.fer.zemris.java.custom.collections;

/**
 * Class that implements an array based collection with dynamic resizing.
 * It supports all basic collection operations.
 * @author Mauro Staver
 *
 */
public class ArrayIndexedCollection extends Collection{
	
	private static final int INITIALCAPACITY = 16;
	
	private int size;
	private Object[] elements;
	
	/**
	 * Constructor.
	 * Creates a new instance of this collection using the given value for the capacity of the internal array.
	 * @param initialCapacity value for initial capacity for internal array
	 * @throws IllegalArgumentException if given capacity is less than 1
	 */
	public ArrayIndexedCollection(int initialCapacity) throws IllegalArgumentException{
		if(initialCapacity < 1) {
			throw new IllegalArgumentException("The argument for capacity has to be"
					+ "larger than 0");
		} 
			
		elements = new Object[initialCapacity];
		size = 0;
	}
	
	/**
	 * Constructor.
	 * Creates a new instance of this collection and initializes it using the given collection's objects.
	 * Initial capacity of internal array is the larger value of given collection's size and initialCapacity.
	 * @param other Collection used to initialize this instance of ArrayIndexedCollection.
	 * @param initialCapacity value for initial capacity for internal array
	 * @throws NullPointerException if other is null
	 */
	public ArrayIndexedCollection(Collection other, int initialCapacity) throws NullPointerException{	
		this(Math.max(other.size(), initialCapacity));	//throws exception if other is null	
		this.addAll(other);
	}
	
	/**
	 * Constructor.
	 * Creates a new instance of this collection.
	 * Initial capacity of internal array is set to 16.
	 */
	public ArrayIndexedCollection() {
		this(INITIALCAPACITY);
	}
	
	/**
	 * Constructor.
	 * Creates a new instance of this collection and initializes it using the given collection's objects.
	 * Initial capacity of internal array is the larger value of given collection's size and 16
	 * @param other Collection used to initialize this collection
	 */
	public ArrayIndexedCollection(Collection other) {
		this(other, INITIALCAPACITY);
	}
	
	
	@Override
	public int size() {
		return size;
	}
	
	/**
	 * Returns the capacity of the internal array.
	 * @return int that represents the capacity of the internal array.
	 */
	public int capacity() {
		return elements.length;
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
	 * @return true if successfully removed, false if given object doesn't exist
	 */
	@Override
	public boolean remove(Object value) {
		int index = indexOf(value);
		
		if(index == -1) {
			return false;
		}
		
		remove(index);
		return true;
	}
	
	
	/**
	 * Transforms collection into array.
	 * @return array consisting of objects from collection
	 */
	@Override
	public Object[] toArray() {
		Object[] array = new Object[size]; 
		
		for(int i = 0; i < size; ++i) {
			array[i] = elements[i];
		}
		
		return array;
	}
	
	@Override
	public void forEach(Processor processor) {
		for(int i = 0; i < size; ++i) {
			processor.process(elements[i]);
		}
	}
	
	/**
	 * Doubles the capacity if collection is full.
	 * Complexity: amortized O(1)
	 */
	private void doubleSizeIfFull() {
		int originalCapacity = elements.length;
		
		if(size >= originalCapacity) {	//reallocate with double size
			Object[] tmp = new Object[2*originalCapacity];
			
			for(int i = 0; i < size; ++i) { //copy elements
				tmp[i] = elements[i]; 
			}
			
			elements = tmp;
		}
	}
	/**
	 * Adds the given object at the end of the collection.
	 * Complexity: amortized O(1)
	 * @param value object to be added
	 * @throws NullPointerException if the given object is null
	 */
	@Override
	public void add(Object value) throws NullPointerException{
		if(value == null) {
			throw new NullPointerException();
		}
		
		doubleSizeIfFull();
		
		elements[size++] = value;
	}
	
	/**
	 * Returns the object stored at position index.
	 * Complexity: O(1)
	 * @param index position of an object in array 
	 * @return object stored at position index
	 * @throws IndexOutOfBoundsException if index is not in [0, size-1]
	 */
	public Object get(int index) throws IndexOutOfBoundsException{
		if(index < 0 || index >= size) {
			throw new IndexOutOfBoundsException();
		}
		return elements[index];
	}
	
	@Override
	public void clear() {
		for(int i = 0; i < size; ++i) {
			elements[i] = null;
		}
		size = 0;
	}
	
	/**
	 * Inserts the given object at the given position in array.
	 * Complexity: O(n)
	 * @param value Object to be inserted
	 * @param position position where we want to insert 
	 * @throws IndexOutOfBoundsException if position is not in interval [0, size]
	 */
	public void insert(Object value, int position) throws IndexOutOfBoundsException{
		if(position < 0 || position > size) {
			throw new IndexOutOfBoundsException();
		}
		
		doubleSizeIfFull();
		
		for(int i = size; i > position; --i) {
			elements[i] = elements[i-1];
		}
		elements[position] = value;
		size++;
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
			for(int i = 0; i < size; ++i) {
				if(value.equals(elements[i])) {
					return i;
				}
			}
		}
		return -1; //not found
	}
	
	/**
	 * Removes element at the specified index from collection.
	 * @param index position of element we want to remove
	 * @throws IndexOutOfBoundsException if position is not in interval [0, size-1]
	 */
	public void remove(int index) throws IndexOutOfBoundsException{
		if(index < 0 || index >= size) {
			throw new IndexOutOfBoundsException();
		}
		for(int i = index; i < size-1; ++i) {
			elements[i] = elements[i+1];
		}
		elements[size] = null;
		size--;
	}
}
