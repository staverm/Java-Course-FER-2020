package hr.fer.zemris.java.custom.collections;

;

/**
 * Interface that represents a collection of elements. It supports basic
 * operations for collections like adding, removing, etc.
 * 
 * @author Mauro Staver
 *
 */
public interface Collection<E> {

	/**
	 * Adds all elements from the specified Collection to this collection if they
	 * satisfy the given Testers object test method.
	 * 
	 * @param col    Collection with elements to add if they satisfy the Tester.
	 * @param tester Tester object to test the elements.
	 */
	default void addAllSatisfying(Collection<? extends E> col, Tester<E> tester) {
		ElementsGetter<? extends E> getter = col.createElementsGetter();

		class processor implements Processor<E> {
			@Override
			public void process(E value) {
				if (tester.test(value)) {
					add(value);
				}
			}
		}

		getter.processRemaining(new processor());
	}

	/**
	 * Returns a new ElementsGetter object for this collection.
	 * 
	 * @return a new ElementsGetter object
	 */
	ElementsGetter<E> createElementsGetter();

	/**
	 * Returns true if this collection has no elements.
	 * 
	 * @return true if collection is empty(has no elements), otherwise false
	 */
	default boolean isEmpty() {
		if (this.size() == 0) {
			return true;
		}
		return false;
	}

	/**
	 * Returns the number of elements in this collection.
	 * 
	 * @return the number of elements in this collection.
	 */
	int size();

	/**
	 * Adds the given object into the collection.
	 * 
	 * @param value object to be added
	 */
	void add(E value);

	/**
	 * Returns true if the collection contains the specified element.
	 * 
	 * @param value element whose presence in this collection is to be tested
	 * @return true if collection contains specified element, otherwise false
	 */
	boolean contains(Object value);

	/**
	 * Removes one occurrence of the specified element from this collection, if it
	 * is present.
	 * 
	 * @param value Element to be removed
	 * @return true if successfully removed, false if given object doesn't exist
	 */
	boolean remove(Object value);

	/**
	 * Transforms collection into an array and returns that array.
	 * 
	 * @return an array containing the elements of this collection.
	 */
	Object[] toArray();

	/**
	 * Processes each object in the collection using the process method from the
	 * given Processor object.
	 * 
	 * @param processor Instance of processor class
	 */
	default void forEach(Processor<? super E> processor) {
		ElementsGetter<E> getter = this.createElementsGetter();
		getter.processRemaining(processor);
	}

	/**
	 * Adds all elements from the given collection into this collection.
	 * 
	 * @param other collection whose elements we are adding.
	 */
	default void addAll(Collection<? extends E> other) {
		class Adder implements Processor<E> {
			@Override
			public void process(E value) {
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
