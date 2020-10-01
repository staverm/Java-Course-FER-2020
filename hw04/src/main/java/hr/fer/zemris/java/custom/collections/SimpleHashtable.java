package hr.fer.zemris.java.custom.collections;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Class that implements a simple Hashtable. It supports all basic Hashtable
 * operations.
 * 
 * @author Mauro Staver
 *
 * @param <K> type of the entry keys
 * @param <V> type of entry values
 */
public class SimpleHashtable<K, V> implements Iterable<SimpleHashtable.TableEntry<K, V>> {

	/**
	 * A (key,value) pair used as entries in a Hashtable. It contains a key, value
	 * and a reference to the next TableEntry.
	 * 
	 * @author Mauro Staver
	 *
	 * @param <K> type of the entry keys
	 * @param <V> type of entry values
	 */
	public static class TableEntry<K, V> {
		K key;
		V value;
		TableEntry<K, V> next;

		/**
		 * Constructs and initializes a new TableEntry with the specified values.
		 * 
		 * @param key   key of this TableEntry
		 * @param value value of this TableEntry
		 * @param next  reference to the next TableEntry
		 */
		public TableEntry(K key, V value, TableEntry<K, V> next) {
			this.key = key;
			this.value = value;
			this.next = next;
		}

		/**
		 * Key getter.
		 * 
		 * @return the key of this TableEntry
		 */
		public K getKey() {
			return key;
		}

		/**
		 * Value getter.
		 * 
		 * @return the value of this TableEntry
		 */
		public V getValue() {
			return value;
		}

		/**
		 * Value setter.
		 * 
		 * @param value value to set the value of this TableEntry.
		 */
		public void setValue(V value) {
			this.value = value;
		}
	}

	/**
	 * Iterator implementation. Used for iterating a HashMap.
	 * 
	 * @author Mauro Staver
	 *
	 */
	private class IteratorImpl implements Iterator<SimpleHashtable.TableEntry<K, V>> {

		private SimpleHashtable.TableEntry<K, V> entry = null; // current entry
		private SimpleHashtable.TableEntry<K, V> prevEntry; // previous entry
		private int index = 0; // index of the first slot that hasn't been searched
		private int iterCount = 0; // number of iterated entries
		private int oldSize; // size of the Hashtable at the moment when this Iterator was created
		private int oldModificationCount; // modificationCount at the moment when this Iterator was created
		private boolean illegalState = false; // iterator state

		/**
		 * Constructs a new Iterator.
		 */
		public IteratorImpl() {
			oldModificationCount = modificationCount;
			oldSize = size;
		}

		@Override
		public boolean hasNext() {
			if (oldModificationCount != modificationCount) {
				throw new ConcurrentModificationException("The hash table has been modified!");
			}

			if (iterCount < oldSize) {
				return true;
			}
			return false;
		}

		@Override
		public TableEntry<K, V> next() {
			if (oldModificationCount != modificationCount) {
				throw new ConcurrentModificationException("The hash table has been modified!");
			}

			prevEntry = entry;
			if (entry == null || entry.next == null) { // null entry or entry is the last in list -> find the next slot
				boolean loopEnd = true;
				for (; index < table.length; ++index) { // find first non empty slot
					if (table[index] != null) {
						entry = table[index]; // found
						index++; // index is now the position that's one after the last searched slot
						loopEnd = false;
						break;
					}
				}

				if (loopEnd) {
					throw new NoSuchElementException("No more elements to iterate.");
				}
			} else {
				entry = entry.next;
			}

			illegalState = false;
			iterCount++;
			return entry;
		}

		@Override
		public void remove() {
			if (oldModificationCount != modificationCount) {
				throw new ConcurrentModificationException("The hash table has been modified!");
			}
			if (entry == null) {
				throw new IllegalStateException("empty entry buffer.");
			}
			if (illegalState) {
				throw new IllegalStateException("Removing the same element twice");
			}

			if (table[index - 1] == entry) { // entry to remove is the first in its slots list
				table[index - 1] = entry.next; // remove
			} else {
				prevEntry.next = entry.next;
				entry = prevEntry;
			}

			modificationCount++;
			oldModificationCount++;
			size--;
			illegalState = true;
		}

	}

	private TableEntry<K, V>[] table;
	private int size = 0;
	private int modificationCount = 0;

	/**
	 * Creates a new Hash table with the initial capacity set to 16.
	 */
	@SuppressWarnings("unchecked")
	public SimpleHashtable() {
		table = (TableEntry<K, V>[]) new TableEntry[16];
	}

	/**
	 * Creates a new Hash table with the initial capacity set to the smallest power
	 * of 2 that's larger or equal to the specified value for the initial capacity.
	 * 
	 * @param initialCapacity value for initial capacity of the hash table.
	 */
	@SuppressWarnings("unchecked")
	public SimpleHashtable(int initialCapacity) {
		if (initialCapacity < 1) {
			throw new IllegalArgumentException("Initial capacity has to be greater than 1!");
		}

		// smallest power of 2 thats >= the initialCapacity
		double log2 = Math.log(initialCapacity) / Math.log(2);
		int capacity = (int) Math.pow(2.0, Math.ceil(log2));

		table = (TableEntry<K, V>[]) new TableEntry[capacity];
	}

	/**
	 * Calculates and returns the index position for an entry with the given
	 * key(using its hashCode).
	 * 
	 * @param key the key whose index position is being calculated
	 * @return the index position for an entry with the given key using its
	 *         hashCode.
	 * @throws IllegalArgumentException if the given key is null.
	 */
	private int getIndex(Object key) {
		if (key == null) {
			throw new NullPointerException("Key can't be null!");
		}
		return Math.abs(key.hashCode()) % table.length;
	}

	/**
	 * Associates the specified value with the specified key in this Hashtable. If
	 * the Hashtable previously contained a mapping for the key, the old value is
	 * replaced.
	 * 
	 * @param key   - key with which the value is being associated.
	 * @param value - value to be associated with the key.
	 * @throws IllegalArgumentException if the given key is null.
	 */
	public void put(K key, V value) {
		if (key == null) {
			throw new NullPointerException("Key can't be null!");
		}

		int index = getIndex(key);
		
		if (table[index] == null) { // case when no entries in list at table[index]
			table[index] = new TableEntry<>(key, value, null); // add new entry
		} else { // list not empty
			TableEntry<K, V> entry = table[index];

			for (entry = table[index]; entry.next != null; entry = entry.next) { // loop through list
				if (entry.key.equals(key)) {
					entry.value = value; // only update the entries value
					return;
				}
			}

			if (entry.key.equals(key)) { // if last entry in list has the key of an entry to be added
				entry.value = value; // only update
				return;
			}

			// add new entry at the end of the list
			TableEntry<K, V> newEntry = new TableEntry<>(key, value, null);
			entry.next = newEntry;
		}

		// new entry added
		size++;
		modificationCount++;
		if (((double) size) / table.length >= 0.75) {
			doubleCapacity();
		}
	}

	/**
	 * Doubles the capacity of the internal array. Creates a new array with double
	 * the size of the original and imports old entries into it.
	 */
	@SuppressWarnings("unchecked")
	private void doubleCapacity() {
		TableEntry<K, V>[] newTable = (TableEntry<K, V>[]) new TableEntry[table.length * 2];

		for (int i = 0; i < table.length; i++) {
			for (TableEntry<K, V> entry = table[i]; entry != null; entry = entry.next) {

				int index = Math.abs(entry.key.hashCode()) % newTable.length;

				if (newTable[index] == null) { // case when no entries in list at table[index]
					newTable[index] = new TableEntry<>(entry.key, entry.value, null);
					continue;
				}

				TableEntry<K, V> head; // head of the list
				for (head = newTable[index]; head.next != null; head = head.next)
					;
				// head is now reference to the last element in list

				// add new entry
				head.next = new TableEntry<>(entry.key, entry.value, null);
			}
		}

		table = newTable;
	}

	/**
	 * Returns the value which is mapped with the given key, or null if this
	 * Hashtable contains no mapping for the given key.
	 * 
	 * @param key - the key whose mapped value is to be returned.
	 * @return the value to which the given key is mapped to, or null if this
	 *         Hashtable contains no mapping for the given key.
	 */
	public V get(Object key) {
		if (key == null) {
			return null;
		}

		int index = getIndex(key);

		for (TableEntry<K, V> entry = table[index]; entry != null; entry = entry.next) {
			if (entry.key.equals(key)) {
				return entry.value;
			}
		}

		return null;
	}

	/**
	 * Removes from Hashtable the entry with the specified key. If the entry with
	 * the specified key doesn't exist, the method does nothing.
	 * 
	 * @param key the key of the entry to remove.
	 */
	public void remove(Object key) {
		if (key == null) {
			return;
		}

		int index = getIndex(key);

		if (table[index] == null) { // case when no entries in list at table[index]
			return;
		}

		TableEntry<K, V> entry = table[index];
		if (entry.key.equals(key)) { // first entry in list is to be removed
			table[index] = entry.next; // remove
		} else {
			TableEntry<K, V> prev = null;
			while (entry != null && !entry.key.equals(key)) { // loop until the element to be removed found or the end
																// of the list
				prev = entry;
				entry = entry.next;
			}

			if (entry == null) { // key not found, reached the end of the list
				return;
			}

			prev.next = entry.next; // key found, remove
		}
		size--;
		modificationCount++;
	}

	/**
	 * Returns true if this Hashtable contains an entry with the specified key,
	 * false otherwise.
	 * 
	 * @param key the key of an entry to look for.
	 * @return Returns true if this Hashtable contains an entry with the specified
	 *         key, false otherwise.
	 */
	public boolean containsKey(Object key) {
		if (key == null) {
			return false;
		}

		int index = getIndex(key);

		for (TableEntry<K, V> entry = table[index]; entry != null; entry = entry.next) {
			if (entry.key.equals(key)) {
				return true;
			}
		}

		return false;
	}

	/**
	 * Returns true if this Hashtable contains an entry with the specified value,
	 * false otherwise.
	 * 
	 * @param value the value of an entry to look for.
	 * @return Returns true if this Hashtable contains an entry with the specified
	 *         value, false otherwise.
	 */
	public boolean containsValue(Object value) {
		for (int i = 0; i < table.length; i++) {
			for (TableEntry<K, V> entry = table[i]; entry != null; entry = entry.next) {

				if (entry.value == null && value == null) { // special case for comparing nulls
					return true;
				} else if (entry.value != null && entry.value.equals(value)) { // general case
					return true;
				}
			}
		}

		return false;
	}

	/**
	 * Returns the number of map entries in this Hashtable.
	 * 
	 * @return the number of map entries in this Hashtable.
	 */
	public int size() {
		return size;
	}

	/**
	 * Returns true if this Hashtable contains no map entries, false otherwise.
	 * 
	 * @return true if this Hashtable contains no map entries, false otherwise.
	 */
	public boolean isEmpty() {
		if (size == 0) {
			return true;
		}
		return false;
	}

	/**
	 * Removes all entries from this Hashtable. The Hashtable capacity remains the
	 * same.
	 */
	@SuppressWarnings("unchecked")
	public void clear() {
		table = (TableEntry<K, V>[]) new TableEntry[table.length];
		size = 0;
		modificationCount++;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("[");

		boolean first = true;
		for (int i = 0; i < table.length; i++) {
			for (TableEntry<K, V> entry = table[i]; entry != null; entry = entry.next) {
				if (first) {
					sb.append(entry.key.toString() + "=");

					if (entry.value == null) {
						sb.append("null");
					} else {
						sb.append(entry.value.toString());
					}

					first = false;
				} else {
					sb.append(", " + entry.key.toString() + "=");

					if (entry.value == null) {
						sb.append("null");
					} else {
						sb.append(entry.value.toString());
					}
				}
			}
		}

		sb.append("]");
		return sb.toString();
	}

	@Override
	public Iterator<TableEntry<K, V>> iterator() {
		return new IteratorImpl();
	}
}
