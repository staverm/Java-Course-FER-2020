package hr.fer.zemris.java.hw11.jnotepadpp.local;

import java.awt.Image;
import java.io.IOException;
import java.io.InputStream;
import javax.swing.Action;
import javax.swing.ImageIcon;
import javax.swing.JButton;

/**
 * Class that is a JButton which updates it's tooltip to button's action name on
 * each localization change. Instances of this class have only an icon(no
 * set text).
 * 
 * @author staver
 *
 */
public class LocalizableJButton extends JButton {

	private static final long serialVersionUID = 1L;

	/**
	 * LocalizableJButton constructor.
	 * 
	 * @param action   button action
	 * @param iconName name of the button icon located in src/main/resources/icons
	 * @param lp       localization provider
	 */
	public LocalizableJButton(Action action, String iconName, ILocalizationProvider lp) {
		this.setAction(action);

		this.setText("");
		this.setToolTipText((String) action.getValue(Action.NAME));

		try (InputStream is = this.getClass().getResourceAsStream("../icons/" + iconName)) {
			byte[] bytes = is.readAllBytes();
			this.setIcon(new ImageIcon(new ImageIcon(bytes).getImage().getScaledInstance(24, 24, Image.SCALE_DEFAULT)));
		} catch (IOException e) {
			System.out.println("Error while reading icons.");
			e.printStackTrace();
		}

		lp.addLocalizationListener(new ILocalizationListener() {

			@Override
			public void localizationChanged() {
				LocalizableJButton.this.setText("");
				LocalizableJButton.this.setToolTipText((String) action.getValue(Action.NAME));
			}

		});
	}
}
