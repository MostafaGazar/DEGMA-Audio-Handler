/*
    Copyright (C) 2005 - 2007 Mikael Högdahl - dronten@gmail.com

    This library is free software; you can redistribute it and/or
    modify it under the terms of the GNU Lesser BaseSetupPanel Public
    License as published by the Free Software Foundation; either
    version 2.1 of the License, or (at your option) any later version.

    This library is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
    Lesser BaseSetupPanel Public License for more details.

    You should have received a copy of the GNU Lesser BaseSetupPanel Public
    License along with this library; if not, write to the Free Software
    Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA
 */
package function.shared.util;

import java.util.prefs.Preferences;

/**
 * User preference object.
 */
public class Pref {
	private static Pref aaPref = null;
	private Preferences aPref = null;

	/**
	 * Create preference object.
	 */
	public Pref(String prefName) {
		aaPref = this;
		aPref = Preferences.userRoot().node(prefName);
	}

	/**
	 * Get preference object.
	 */
	public static Preferences get() {
		assert aaPref != null;
		return aaPref.aPref;
	}

	/**
	 * Get preference value.
	 */
	public static String getPref(String key, String def) {
		return aaPref.aPref.get(key, def);
	}

	/**
	 * Get preference value.
	 */
	public static int getPref(String key, int def) {
		return aaPref.aPref.getInt(key, def);
	}

	/**
	 * Get preference value.
	 */
	public static double getPref(String key, double def) {
		return aaPref.aPref.getDouble(key, def);
	}

	/**
	 * Get preference value.
	 */
	public static boolean getPref(String key, boolean def) {
		return aaPref.aPref.getBoolean(key, def);
	}

	/**
	 * Get preference value.
	 */
	public static int getPref(String key, int option, int def) {
		String key2 = String.format("%s%03d", key, option);
		return aaPref.aPref.getInt(key2, def);
	}

	/**
	 * Get preference value.
	 */
	public static String getPref(String key, int option, String def) {
		String key2 = String.format("%s%03d", key, option);
		return aaPref.aPref.get(key2, def);
	}

	/**
	 * Set preference.
	 */
	public static void setPref(String key, String val) {
		aaPref.aPref.put(key, val);
	}

	/**
	 * Set preference.
	 */
	public static void setPref(String key, int val) {
		aaPref.aPref.putInt(key, val);
	}

	/**
	 * Set preference.
	 */
	public static void setPref(String key, double val) {
		aaPref.aPref.putDouble(key, val);
	}

	/**
	 * Set preference.
	 */
	public static void setPref(String key, boolean val) {
		aaPref.aPref.putBoolean(key, val);
	}
}
