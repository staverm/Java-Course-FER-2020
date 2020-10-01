package hr.fer.zemris.java.hw11.jnotepadpp.local;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * A singleton class that offers an implementation of a localization provider.
 * 
 * @author staver
 *
 */
public class LocalizationProvider extends AbstractLocalizationProvider{
	
	/**
	 * Single instance of this class.
	 */
	private static final LocalizationProvider instance = new LocalizationProvider();
	
	/**
	 * Current language
	 */
	private String language = "en";
	private ResourceBundle bundle;
	
	/**
	 * LocalizationProvider constructor.
	 */
	private LocalizationProvider() {
		Locale locale = Locale.forLanguageTag(language);
		bundle = ResourceBundle.getBundle("hr.fer.zemris.java.hw11.jnotepadpp.local.translations", locale);
	}
	
	/**
	 * Returns a single instance of this class.
	 * 
	 * @return  a single instance of this class
	 */
	public static LocalizationProvider getInstance() {
		return instance;
	}
	
	/**
	 * Sets current language to the given language.
	 * 
	 * @param language language to be set
	 */
	public void setLanguage(String language) {
		this.language = language;
		Locale locale = Locale.forLanguageTag(language);
		bundle = ResourceBundle.getBundle("hr.fer.zemris.java.hw11.jnotepadpp.local.translations", locale);
		fire();
	}

	@Override
	public String getString(String key) {
		return bundle.getString(key);
	}

	@Override
	public String getCurrentLanguage() {
		return language;
	}
	
	
	
}
