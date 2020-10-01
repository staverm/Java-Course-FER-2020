package hr.fer.zemris.java.hw11.jnotepadpp.local;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.KeyStroke;

/**
 * Class that is an AbstractAction which updates it's name and description on
 * each on each localization change.
 * 
 * @author staver
 *
 */
public abstract class LocalizableAction extends AbstractAction {

	private static final long serialVersionUID = 1L;

	/**
	 * LocalizableAction constructor.
	 * 
	 * @param key            localization string key
	 * @param acceleratorKey keyboard shortcut to be set for this action
	 * @param mnemonic       mnemonic character to be set for this action
	 * @param lp             localization provider
	 */
	public LocalizableAction(String key, KeyStroke acceleratorKey, int mnemonic, ILocalizationProvider lp) {
		this.putValue(NAME, lp.getString(key));
		this.putValue(Action.ACCELERATOR_KEY, acceleratorKey);
		if (mnemonic != 0) { // no mnemonic
			this.putValue(Action.MNEMONIC_KEY, mnemonic);
		}
		this.putValue(SHORT_DESCRIPTION, lp.getString(key + "Description"));

		lp.addLocalizationListener(new ILocalizationListener() {

			@Override
			public void localizationChanged() {
				LocalizableAction.this.putValue(NAME, lp.getString(key));
				LocalizableAction.this.putValue(SHORT_DESCRIPTION, lp.getString(key + "Description"));
			}

		});
	}

}
