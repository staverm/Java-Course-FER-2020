package hr.fer.zemris.java.hw11.jnotepadpp.local;

/**
 * Decorator class that wraps around an instance of IlocalizationProvider. This
 * class registers/unregisters itself as a listener to a given provider, by
 * calling <code>connect()</code> and <code>disconnect()</code> methods,
 * respectively. Each time it gets triggered, it triggers all of it's listeners.
 * This removes the need for other classes to register themselves as listeners
 * to the provider(and therefore possibly be unable to be collected by garbage
 * collector, because the provider holds a reference to them).
 * 
 * @author staver
 *
 */
public class LocalizationProviderBridge extends AbstractLocalizationProvider {

	/**
	 * Provider that this class is wrapped around.
	 */
	private ILocalizationProvider provider;
	/**
	 * Current language
	 */
	private String language;
	/**
	 * Connection status
	 */
	private boolean connected = false;
	/**
	 * Listener that listens to localization changes on provider, and triggers
	 * <code>localizationChanged()</code> method on all listeners registered on this
	 * object.
	 */
	private ILocalizationListener listener = new ILocalizationListener() {

		@Override
		public void localizationChanged() {
			LocalizationProviderBridge.this.fire();
		}

	};

	/**
	 * LocalizationProviderBridge constructor.
	 * 
	 * @param provider provider to be wrapped
	 */
	public LocalizationProviderBridge(ILocalizationProvider provider) {
		this.provider = provider;
		this.language = provider.getCurrentLanguage();
	}

	/**
	 * Disconnects from the provider.
	 */
	public void disconnect() {
		if (connected) {
			provider.removeLocalizationListener(listener);
			connected = false;
		}
	}

	/**
	 * Connects to the provider by registering itself as provider's listener. There
	 * can be only a single connection at some moment in time.
	 */
	public void connect() {
		if (!connected) {
			provider.addLocalizationListener(listener);
			connected = true;
			if (!language.equals(provider.getCurrentLanguage())) {
				this.language = provider.getCurrentLanguage();
				this.fire();
			}
		}
	}

	@Override
	public String getString(String key) {
		return provider.getString(key); // delegate
	}

	@Override
	public String getCurrentLanguage() {
		return language;
	}
}
