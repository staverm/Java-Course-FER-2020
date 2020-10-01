package hr.fer.zemris.java.gui.prim;

import java.util.List;
import java.util.ArrayList;

import javax.swing.ListModel;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;

/**
 * List model that holds prime numbers. Primes are generated incrementally: by
 * calling the <code>next()</code> method - the next prime number is generated
 * and all listeners are alerted.
 * 
 * @author staver
 *
 */
public class PrimListModel implements ListModel<Integer> {

	/**
	 * List of listeners
	 */
	List<ListDataListener> listeners = new ArrayList<>();

	/**
	 * List of primes
	 */
	List<Integer> primes = new ArrayList<>();

	/**
	 * Constructs a new PrimListModel. PrimListModel holds 1 as the first element at
	 * moment of creation.
	 */
	public PrimListModel() {
		primes.add(1);
		fireIntervalAdded(this, 0, 0);
	}

	/**
	 * Generates the next prime number and alerts all listeners.
	 */
	public void next() {
		int x = primes.get(primes.size() - 1) + 1;
		while (true) {
			if (isPrime(x)) {
				primes.add(x);
				fireIntervalAdded(this, primes.size() - 1, primes.size() - 1);
				break;
			}
			x++;
		}
	}

	/**
	 * Returns true if the given number is a prime, false otherwise.
	 * 
	 * @param x number to test if prime
	 * @return true if the given number is a prime, false otherwise
	 */
	private boolean isPrime(int x) {
		if (x <= 1) {
			return false;
		}

		if (x <= 3) {
			return true;
		}

		if (x % 2 == 0 || x % 3 == 0) {
			return false;
		}

		for (int i = 5; i * i <= x; i = i + 6)
			if (x % i == 0 || x % (i + 2) == 0)
				return false;

		return true;
	}

	/**
	 * This method should be called after one or more elements are added to the
	 * model. The new elements are specified by a closed interval index0, index1.
	 * This method alerts all listeners about the added interval.
	 *
	 * @param source the <code>ListModel</code> that changed, typically "this"
	 * @param index0 one end of the new interval
	 * @param index1 the other end of the new interval
	 */
	private void fireIntervalAdded(Object source, int index0, int index1) {
		ListDataEvent e = new ListDataEvent(source, ListDataEvent.INTERVAL_ADDED, index0, index1);
		for (ListDataListener l : listeners) {
			l.intervalAdded(e);
		}
	}

	@Override
	public int getSize() {
		return primes.size();
	}

	@Override
	public Integer getElementAt(int index) {
		return primes.get(index);
	}

	@Override
	public void addListDataListener(ListDataListener l) {
		listeners = new ArrayList<>(listeners);
		listeners.add(l);
	}

	@Override
	public void removeListDataListener(ListDataListener l) {
		listeners = new ArrayList<>(listeners);
		listeners.remove(l);
	}

}
