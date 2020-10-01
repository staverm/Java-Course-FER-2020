package hr.fer.zemris.java.custom.collections;

/**
 * Interface that extends the Collection interface and defines new methods specific to indexed collections.
 * @author Mauro Staver
 *
 */
public interface List extends Collection{
	
	/**
	 * Returns the element at the specified position in this list.
	 * @param index the element to return
	 * @return the element at the specified position in this list
	 * @throws IndexOutOfBoundsException if index is not in interval [0, size-1]
	 */
	Object get(int index);
	
	/**
	 * Inserts the given object at the specified position in this list.
	 * @param value Object to be inserted
	 * @param position position in the list where we want to insert 
	 * @throws IndexOutOfBoundsException if the position is not in interval [0, size]
	 */
	void insert(Object value, int position);
	
	/**
	 * Searches the collection and returns the index of the first occurrence of
	 *  the given object or -1 if not found.
	 * @param value object to search for
	 * @return index of the first occurrence of the given object or -1 if not found
	 */
	int indexOf(Object value);
	
	/**
	 * Removes the element at the specified position from this list.
	 * @param index position of the element we want to remove
	 * @throws IndexOutOfBoundsException if the position is not in interval [0, size-1]
	 */
	void remove(int index);
}
