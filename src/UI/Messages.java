package UI;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

import RipConvertShared.util.Pref;
import Util.Constants;

public class Messages {
	private static final String BUNDLE_NAME = "lang/" + Pref.getPref(Constants.LANG, ""); //$NON-NLS-1$

	private static final ResourceBundle RESOURCE_BUNDLE = ResourceBundle
			.getBundle(BUNDLE_NAME);

	private Messages() {
	}

	public static String getString(String key) {
		try {
			return RESOURCE_BUNDLE.getString(key);
		} catch (MissingResourceException e) {
			return '!' + key + '!';
		}
	}
}
