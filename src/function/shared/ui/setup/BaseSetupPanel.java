package function.shared.ui.setup;

import javax.swing.*;

/**
 * Set preference.
 */
public abstract class BaseSetupPanel extends JPanel {
	private static final long serialVersionUID = 1L;

	public boolean aScroll = false;

	/**
     *
     */
	public BaseSetupPanel() {
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
	}

	/**
	 * Save settings.
	 */
	public abstract void save();
}
