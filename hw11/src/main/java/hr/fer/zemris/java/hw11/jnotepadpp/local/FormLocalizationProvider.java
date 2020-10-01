package hr.fer.zemris.java.hw11.jnotepadpp.local;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;

/**
 * A class that extends LocalizationProviderBridge. It registers/unregisters
 * itself as a listener to a given provider on each windowOpened/windowClosed
 * event from the given frame. Therefore, the frame can be collected by the
 * garbage collector after it gets disposed.
 * 
 * @author staver
 *
 */
public class FormLocalizationProvider extends LocalizationProviderBridge {

	/**
	 * FormLocalizationProvider constructor. Creates a FormLocalizationProvider
	 * object that registers/unregisters itself as a listener to a given provider on
	 * each windowOpened/windowClosed event from the given frame. Therefore, the
	 * frame can be collected by the garbage collector after it gets disposed
	 * because the provider doesn't need to hold a reference to it.
	 * 
	 * @param provider localization provider
	 * @param frame frame whose windowOpened/windowClosed events are listened
	 */
	public FormLocalizationProvider(ILocalizationProvider provider, JFrame frame) {
		super(provider);

		frame.addWindowListener(new WindowAdapter() {

			@Override
			public void windowClosed(WindowEvent e) {
				FormLocalizationProvider.this.disconnect();
			}

			@Override
			public void windowOpened(WindowEvent e) {
				FormLocalizationProvider.this.connect();
			}
		});
	}

}
