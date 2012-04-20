package ui;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

import function.shared.util.Pref;


import util.Constants;


public class Messages {
	private static final String BUNDLE_NAME = "lang/" + Pref.getPref(Constants.LANG, "");

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
