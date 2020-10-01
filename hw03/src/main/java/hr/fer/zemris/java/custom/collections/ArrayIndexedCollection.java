package hr.fer.zemris.java.custom.collections;

import java.util.ConcurrentModificationException;
import java.util.NoSuchElementException;

/**
 * Class that implements an array based collection with dynamic resizing.
 * It supports all basic collection operations.
 * @author Mauro Staver
 *
 */
public class ArrayIndexedCollection implements List{
	
	/**
	 * Class that implements the ElementsGetter interface for use on the ArrayIndexedCollection objects.
	 * @author Mauro Staver
	 */
	private static class ArrayElementsGetter implements ElementsGetter{
		
		private int index = 0;
		private long savedModificationCount;
		private ArrayIndexedCollection array;
		
		/**
		 * ArrayElementsGetter constructor.
		 * Constructs and initializes a new ArrayElementsGetter object.
		 * @param array Array on which we want to use the ListElementsGetter.
		 */
		public ArrayElementsGetter(ArrayIndexedCollection array) {
			this.array = array;
			savedModificationCount = array.modificationCount;
		}
		
		@Override
		public boolean hasNextElement() {
			if(savedModificationCount != array.modificationCount) {
				throw new ConcurrentModificationException("The array has been modified.");
			}
			
			if(index < array.size) {
				return true;
			}else {
				return false;
			}
		}

		@Override
		public Object getNextElement() throws NoSuchElementException {
			if(savedModificationCount != array.modificationCount) {
				throw new ConcurrentModificationException("The array has been modified.");
			}
			if(hasNextElement() == false) {
				throw new NoSuchElementException("No more elements in ElementsGetter");
			}
			
			return array.get(index++);
		}
		
	}
	
	private static final int INITIALCAPACITY = 16;
	
	private int size;
	private long modificationCount = 0;
	private Object[] elements;
	
	@Override
	public ElementsGetter createElementsGetter() {
		return new ArrayElementsGetter(this);
	}
	
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
	 * @return the capacity of the internal array.
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
	 * If exists, removes the first occurrence of the specified element from this collection.
	 * @param value Element to be removed
	 * @return True if successfully removed, false if collection doesn't contain the specified element.
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
	
	@Override
	public Object[] toArray() {
		Object[] array = new Object[size]; 
		
		for(int i = 0; i < size; ++i) {
			array[i] = elements[i];
		}
		
		return array;
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
		modificationCount++;
	}
	
	@Override
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
		modificationCount++;
	}
	
	@Override
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
		modificationCount++;
	}
	
	@Override
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
	
	@Override
	public void remove(int index) throws IndexOutOfBoundsException{
		if(index < 0 || index >= size) {
			throw new IndexOutOfBoundsException();
		}
		for(int i = index; i < size-1; ++i) {
			elements[i] = elements[i+1];
		}
		elements[size] = null;
		size--;
		modificationCount++;
	}
}
