package hr.fer.zemris.java.hw11.jnotepadpp.local;

import java.util.ArrayList;
import java.util.List;

/**
 * An abstract class that implements ILocalizationProvider and implements it's listener related methods.
 * 
 * @author staver
 *
 */
public abstract class AbstractLocalizationProvider implements ILocalizationProvider{
	
	/**
	 * List of listeners registered on this class.
	 */
	List<ILocalizationListener> listeners = new ArrayList<>();
	
	@Override
	public void addLocalizationListener(ILocalizationListener l) {
		listeners.add(l);
	}
	
	@Override
	public void removeLocalizationListener(ILocalizationListener l) {
		listeners.remove(l);
	}
	
	/**
	 * Triggers <code>localizationChanged()</code> method for each listener.
	 */
	public void fire() {
		listeners.forEach(l -> l.localizationChanged());
	}


}
