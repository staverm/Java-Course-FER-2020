package hr.fer.zemris.java.custom.scripting.exec;

import java.util.EmptyStackException;
import java.util.HashMap;
import java.util.Map;

/**
 * Data structure that associates strings with stacks of ValueWrapper objects.
 * De facto a map that maps strings to stacks.
 * 
 * @author staver
 *
 */
public class ObjectMultistack {

	/**
	 * Represents a node in a link-list that models a stack. Map maps a string to
	 * the head node which is of this type.
	 * 
	 * @author staver
	 *
	 */
	private static class MultistackEntry {
		private MultistackEntry next;
		private ValueWrapper value;

		/**
		 * MultistackEntry constructor. Constructs and initializes a new MultistackEntry
		 * entry with the given values.
		 * 
		 * @param value value to be set as this MultistackEntry object's value
		 * @param next  reference to next MultistackEntry in a linked-list
		 */
		public MultistackEntry(ValueWrapper value, MultistackEntry next) {
			this.value = value;
			this.next = next;
		}

		/**
		 * Returns the next item in list or null if none exist.
		 * 
		 * @return next item in list or null if none exist
		 */
		public MultistackEntry getNext() {
			return next;
		}

		/**
		 * Returns the value of this MultistackEntry object.
		 * 
		 * @return value of this MultistackEntry object
		 */
		public ValueWrapper getValue() {
			return value;
		}
	}

	private Map<String, MultistackEntry> map = new HashMap<>(); // map used to map strings to stacks

	/**
	 * Pushes the given ValueWrapper object to the stack associated with the given
	 * string key.
	 * 
	 * @param keyName      string key associated with the stack where the
	 *                     ValueWrapper object is to be pushed.
	 * @param valueWrapper ValueWrapper object to be pushed
	 */
	public void push(String keyName, ValueWrapper valueWrapper) {
		map.put(keyName, new MultistackEntry(valueWrapper, map.get(keyName)));
	}

	/**
	 * Pops from the stack associated with the given string key. Returns popped
	 * ValueWrapper object.
	 * 
	 * @param keyName string key associated with the stack which is to be popped.
	 * @return popped ValueWrapper object
	 * @throws EmptyStackException if the associated stack is empty
	 */
	public ValueWrapper pop(String keyName) {
		if (!map.containsKey(keyName)) {
			throw new EmptyStackException();
		}
		ValueWrapper popped = map.get(keyName).getValue();
		
		if(map.get(keyName).getNext() == null) {
			map.remove(keyName);
		}else {
			map.put(keyName, map.get(keyName).getNext());
		}
		return popped;
	}

	/**
	 * Returns the first ValueWrapper object from the stack associated with the
	 * given string.
	 * 
	 * @param keyName string key associated with the stack from which the first
	 *                ValueWrapper object is to be returned.
	 * @return first ValueWrapper object from the stack associated with the given
	 *         string
	 */
	public ValueWrapper peek(String keyName) {
		if (!map.containsKey(keyName)) {
			throw new EmptyStackException();
		}
		return map.get(keyName).getValue();
	}

	/**
	 * Returns true if the stack associated with the given string is empty, false
	 * otherwise.
	 * 
	 * @param keyName string key associated with the stack
	 * @return true if the stack associated with the given string is empty, false
	 *         otherwise
	 */
	public boolean isEmpty(String keyName) {
		return !map.containsKey(keyName);
	}
}
