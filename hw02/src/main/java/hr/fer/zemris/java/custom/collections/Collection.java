package hr.fer.zemris.java.custom.collections;

/**
 * Class that represents an abstract collection.
 * It supports basic operations for collections.
 * @author Mauro Staver
 *
 */
public class Collection {
	
	/**
	 * Package protected default constructor.
	 */
	protected Collection() {
		
	}
	
	/**
	 * Checks if collection is empty.
	 * @return true if collection is empty(has no elements), otherwise false
	 */
	public boolean isEmpty() {
		if(this.size() == 0) {
			return true;
		}
		return false;
	}
	
	/**
	 * Returns size of collection.
	 * @return int that represents size of the collection
	 */
	public int size() {
		return 0;
	}
	
	/**
	 * Adds the given object into collection.
	 * @param value object to be added
	 */
	public void add(Object value) {
		
	}
	
	/**
	 * Checks if collection contains the given object.
	 * @param value object we want to check
	 * @return true if collection contains given object, otherwise false
	 */
	public boolean contains(Object value) {
		return false;
	}
	
	/**
	 * If exists, removes one instance of the given object.
	 * @param value Object to be removed
	 * @return true if successfully removed, false if given object doesn't exist
	 */
	public boolean remove(Object value) {
		return false;
	}
	
	/**
	 * Transforms collection into an array and returns that array.
	 * @return array of objects from collection
	 */
	public Object[] toArray() {
		throw new UnsupportedOperationException("Operation not implemented");
	}
	
	/**
	 * Processes each object in collection using the process method from the given Processor object.
	 * @param processor Instance of processor class
	 */
	public void forEach(Processor processor) {
		
	}
	
	/**
	 * Adds all elements from the given collection into this collection.
	 * @param other
	 */
	public void addAll(Collection other) {
		class Adder extends Processor{
			
			public void process(Object value) {
				add(value);
			}
			
		}
		
		other.forEach(new Adder());
	}
	

	/**
	 * Removes all elements from this collection.
	 */
	public void clear() {
		
	}
}
