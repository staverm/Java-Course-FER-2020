package hr.fer.zemris.java.custom.collections;

/**
 * Class that implements a Dictionary as an adaptor around
 * ArrayIndexedCollection. The Dictionary stores (key,value) pairs. It supports
 * all basic dictionary operations.
 * 
 * @author Mauro Staver
 *
 * @param <K> the type of keys maintained by this dictionary
 * @param <V> the type of the mapped values
 */
public class Dictionary<K, V> {

	/**
	 * Class that implements a (key,value) pair. Such pairs are stored in a
	 * dictionary.
	 *
	 * @author Mauro Staver
	 *
	 * @param <K> the type of key
	 * @param <V> the type of value
	 */
	private static class Pair<K, V> {
		K key;
		V value;

		/**
		 * Constructs and initializes a (key,value) Pair with the specified values.
		 * 
		 * @param key   - Key of the pair. Can't be null.
		 * @param value - Value of the pair.
		 * @throws IllegalArgumentException if the given key is null.
		 */
		private Pair(K key, V value) {
			if (key == null) {
				throw new IllegalArgumentException("Key can't be null");
			}

			this.key = key;
			this.value = value;
		}
	}

	private ArrayIndexedCollection<Pair<K, V>> array;

	/**
	 * Default constructor. Constructs a new Dictionary object.
	 */
	public Dictionary() {
		array = new ArrayIndexedCollection<>();
	}

	/**
	 * Associates the specified value with the specified key in this dictionary. If
	 * the dictionary previously contained a mapping for the key, the old value is
	 * replaced.
	 * 
	 * @param key   - key with which the value is being associated.
	 * @param value - value to be associated with the key.
	 * @throws IllegalArgumentException if the given key is null.
	 */
	public void put(K key, V value) {
		if (key == null) {
			throw new IllegalArgumentException("Key can't be null");
		}

		for (int i = 0; i < array.size(); i++) {
			Pair<K, V> entry = array.get(i);

			if (entry.key.equals(key)) {
				entry.value = value;
				return;
			}
		}

		array.add(new Pair<>(key, value));
	}

	/**
	 * Returns the value which is mapped with the given key, or null if this
	 * dictionary contains no mapping for the given key.
	 * 
	 * @param key - the key whose mapped value is to be returned.
	 * @return the value to which the given key is mapped to, or null if this
	 *         dictionary contains no mapping for the given key.
	 */
	public V get(Object key) {
		ElementsGetter<Pair<K, V>> getter = array.createElementsGetter();

		while (getter.hasNextElement()) {
			Pair<K, V> pair = getter.getNextElement();
			if (pair.key.equals(key)) {
				return pair.value;
			}
		}

		return null;
	}

	/**
	 * Returns true if this dictionary contains no map entries, false otherwise.
	 * 
	 * @return true if this dictionary contains no map entries, false otherwise.
	 */
	public boolean isEmpty() {
		return array.isEmpty();
	}

	/**
	 * Returns the number of map entries in this dictionary.
	 * 
	 * @return the number of map entries in this dictionary.
	 */
	public int size() {
		return array.size();
	}

	/**
	 * Removes all of the mappings from this dictionary. The dictionary is empty
	 * after this call returns.
	 */
	public void clear() {
		array.clear();
	}
}
