package hr.fer.zemris.java.hw11.jnotepadpp.local;

import javax.swing.JMenu;

/**
 * Class that is a JMenu which updates it's text on each localization change.
 * 
 * @author staver
 *
 */
public class LocalizableJMenu extends JMenu{

	private static final long serialVersionUID = 1L;
	
	/**
	 * LocalizableJMenu constructor.
	 * 
	 * @param key localization string key
	 * @param lp localization provider
	 */
	public LocalizableJMenu(String key, ILocalizationProvider lp) {
		
		this.setText(lp.getString(key));
				
		lp.addLocalizationListener(new ILocalizationListener() {
			
			@Override
			public void localizationChanged() {
				LocalizableJMenu.this.setText(lp.getString(key));
			}
			
		});
	}
}
