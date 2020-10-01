package hr.fer.zemris.java.custom.collections;

/**
 * Interface that represents a collection of elements.
 * It supports basic operations for collections like adding, removing, etc.
 * @author Mauro Staver
 *
 */
public interface Collection {
	
	/**
	 * Adds all elements from the specified Collection to this collection
	 *  if they satisfy the given Testers object test method.
	 * @param col Collection with elements to add if they satisfy the Tester.
	 * @param tester Tester object to test the elements.
	 */
	default void addAllSatisfying(Collection col, Tester tester) {
		ElementsGetter getter = col.createElementsGetter();
		
		getter.processRemaining(new Processor() {		
			@Override
			public void process(Object value) {
				if(tester.test(value)) {
					add(value);
				}				
			}	
		});
	}
	
	/**
	 * Returns a new ElementsGetter object for this collection.
	 * @return a new ElementsGetter object 
	 */
	ElementsGetter createElementsGetter();
	
	/**
	 * Returns true if this collection has no elements.
	 * @return true if collection is empty(has no elements), otherwise false
	 */
	default boolean isEmpty() {
		if(this.size() == 0) {
			return true;
		}
		return false;
	}
	
	/**
	 * Returns the number of elements in this collection.
	 * @return the number of elements in this collection.
	 */
	int size();
	
	/**
	 * Adds the given object into the collection.
	 * @param value object to be added
	 */
	void add(Object value);
	
	/**
	 * Returns true if the collection contains the specified element.
	 * @param value element whose presence in this collection is to be tested
	 * @return true if collection contains specified element, otherwise false
	 */
	boolean contains(Object value);
	
	/**
	 * Removes one occurrence of the specified element from this collection, if it is present.
	 * @param value Element to be removed
	 * @return true if successfully removed, false if given object doesn't exist
	 */
	boolean remove(Object value);
	
	/**
	 * Transforms collection into an array and returns that array.
	 * @return an array containing the elements of this collection.
	 */
	Object[] toArray();
	
	/**
	 * Processes each object in the collection using the process method from the given Processor object.
	 * @param processor Instance of processor class
	 */
	default void forEach(Processor processor) {
		ElementsGetter getter = this.createElementsGetter();
		getter.processRemaining(processor);
	}
	
	/**
	 * Adds all elements from the given collection into this collection.
	 * @param other collection whose elements we are adding.
	 */
	default void addAll(Collection other) {
		class Adder implements Processor{
			
			public void process(Object value) {
				add(value);
			}
			
		}
		
		other.forEach(new Adder());
	}
	

	/**
	 * Removes all elements from this collection.
	 */
	void clear();
}
