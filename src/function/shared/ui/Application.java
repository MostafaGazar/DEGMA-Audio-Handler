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
package function.shared.ui;

import javax.swing.*;

import function.shared.util.Log;
import function.shared.util.Pref;

import java.awt.*;
import java.util.Properties;

/**
 * Base Application object. Extend to create an gui application.
 * 
 * @edited Mostafa Gazar, eng.mostafa.gazar@gmail.com
 */
public class Application extends JPanel {
	public static final String CODEPAGE = "codepage";

	private static Application aaApplicatation = null;
	private JPanel aMainPanel = null;
	private Cursor aDefaultCursor = null;
	private Cursor aWaitCursor = null;
	private String aOperatingSystem = null;
	private static final long serialVersionUID = 666L;
	protected String aAppName = "";

	/**
	 * Create Application and try to set look & feel and load resource file.
	 */
	public Application() {
		Log.get().setLogLevel(3);
		aaApplicatation = this;
		aDefaultCursor = new Cursor(Cursor.DEFAULT_CURSOR);
		aWaitCursor = new Cursor(Cursor.WAIT_CURSOR);
		aOperatingSystem = System.getProperty("os.name");

		try {
			if (Pref.getPref(CODEPAGE, "").length() > 0) {
				setCodePage(Pref.getPref(CODEPAGE, ""));
			} else {
				Log.get().addTime(
						1,
						"Current codepage:"
								+ System.getProperty("file.encoding"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Get application.
	 * 
	 * @return Application instance
	 */
	public static Application get() {
		return aaApplicatation;
	}

	/**
	 * Get codepage name.
	 */
	public String getCodePage() {
		return System.getProperty("file.encoding");
	}

	/**
	 * Is this linux?
	 * 
	 * @return true if this os is linux
	 */
	public boolean isLinux() {
		return aOperatingSystem.startsWith("Linux");
	}

	/**
	 * Is this windows?
	 * 
	 * @return true if this os is windows
	 */
	public boolean isWindows() {
		return aOperatingSystem.startsWith("Windows");
	}

	/**
	 * Set new codepage. It's the "file.encoding" system property. To do other
	 * encoding changes you must do it manually.
	 */
	public void setCodePage(String newCodePage) {
		if (newCodePage.length() > 0 || Pref.getPref(CODEPAGE, "").length() > 0) {
			Pref.setPref(CODEPAGE, newCodePage);

			Log.get().addTime(1,
					"Old codepage:" + System.getProperty("file.encoding"));

			Properties pi = System.getProperties();
			pi.put("file.encoding", newCodePage);
			System.setProperties(pi);

			Log.get().addTime(1,
					"New codepage:" + System.getProperty("file.encoding"));
		}
	}

	/**
	 * Set normal cursor
	 */
	public void setNormalCursor() {
		aMainPanel.setCursor(aDefaultCursor);
	}

	/**
	 * Set wait cursor
	 */
	public void setWaitCursor() {
		aMainPanel.setCursor(aWaitCursor);
	}

	/**
	 * Show error message.
	 * 
	 * @param resName
	 *            - Resource key
	 */
	public void showError(String resName) {
		JOptionPane.showMessageDialog(this, resName, aAppName,
				JOptionPane.ERROR_MESSAGE);
	}

	/**
	 * Show error message.
	 * 
	 * @param resName
	 *            - Resource key
	 * @param extraMessage
	 *            - Resource key
	 */
	public void showHtmlError(String resName, String extraMessage) {
		JOptionPane
				.showMessageDialog(this, "<html>" + resName + "<br>"
						+ extraMessage + "</html>", aAppName,
						JOptionPane.ERROR_MESSAGE);
	}

	/**
	 * Show information message.
	 * 
	 * @param resName
	 *            - Resource key
	 */
	public void showInfo(String resName) {
		JOptionPane.showMessageDialog(this, resName, aAppName,
				JOptionPane.INFORMATION_MESSAGE);
	}

	/**
	 * @param mainPanel
	 *            the aMainPanel to set
	 */
	public void addMainPanel(JPanel mainPanel) {
		aMainPanel = mainPanel;
		add(aMainPanel);
	}
}
