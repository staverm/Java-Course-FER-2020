package hr.fer.zemris.java.hw11.jnotepadpp.local;

/**
 * Interface that models a localization provider.
 * 
 * @author staver
 *
 */
public interface ILocalizationProvider {
	
	/**
	 * Registers the given ILocalizationListener to this provider.
	 * 
	 * @param l listener to be registered
	 */
	void addLocalizationListener(ILocalizationListener l);
	
	/**
	 * Unregisters the given ILocalizationListener from this provider.
	 * 
	 * @param l listener to be unregistered
	 */
	void removeLocalizationListener(ILocalizationListener l);
	
	/**
	 * Returns localized string associated with the given key.
	 * 
	 * @param key key of the associated localized string
	 * @return localized string associated with the given key
	 */
	String getString(String key);
	
	/**
	 * Returns currently selected language.
	 * 
	 * @return currently selected language
	 */
	String getCurrentLanguage();
}
