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

package function.shared.UI;


import javax.swing.*;

import function.shared.util.Log;
import function.shared.util.Pref;


import java.awt.*;
import java.util.Properties;

/**
 * Base Application object. Extend to create an gui application.
 */
public class Application extends JPanel {
	public static final String CODEPAGE = "codepage";

	private static Application aaApplicatation = null;
	// private static JFrame mainFrame;
	private JPanel aMainPanel = null;
	private Cursor aDefaultCursor = null;
	private Cursor aWaitCursor = null;
	private String aOperatingSystem = null;
	private static final long serialVersionUID = 666L;
	protected String aAppName = "";

	/**
	 * Create Application and try to set look & feel and load resource file.
	 * 
	 * @param appName
	 * @param prefName
	 */
	public Application() {// String appName, String prefName) {
		// super(appName);
		Log.get().setLogLevel(3);
		aaApplicatation = this;
		aDefaultCursor = new Cursor(Cursor.DEFAULT_CURSOR);
		aWaitCursor = new Cursor(Cursor.WAIT_CURSOR);
		aOperatingSystem = System.getProperty("os.name");
		// aAppName = appName;

		// new Pref(prefName);

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

		// setLookAndFeel(true);
	}

	/**
	 * Get application.
	 * 
	 * @return Application instance
	 */
	public static Application get() {
		return aaApplicatation;
	}

	/*
	 * public static JFrame getMainFrame() { return mainFrame; }
	 */

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

	/*
	 * Quit application. Save last used path and screen position.
	 */
	/*
	 * public void quit() { try { Dimension size = getSize(); Point pos =
	 * getLocation();
	 * 
	 * Pref.setPref("w", Double.toString(size.getWidth())); Pref.setPref("h",
	 * Double.toString(size.getHeight())); Pref.setPref("x",
	 * Double.toString(pos.getX())); Pref.setPref("y",
	 * Double.toString(pos.getY())); } catch (Exception e) { }
	 * //setVisible(false); //dispose(); //System.exit(0); }
	 */

	/**
	 * Set new codepage. It's the "file.encoding" system property. To do other
	 * encoding changes you must do it manually.
	 * 
	 * @param newCodePage
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
	 * @param iconName
	 */
	/*
	 * public void setIcon(String iconName) { try { URL pathShell; ClassLoader
	 * cl = Application.class.getClassLoader(); pathShell =
	 * cl.getResource(iconName);
	 * 
	 * Image icon = Toolkit.getDefaultToolkit().getImage(pathShell);
	 * setIconImage(icon); } catch (Exception e) { e.printStackTrace(); } }
	 */

	/**
	 * Set normal cursor
	 */
	public void setNormalCursor() {
		aMainPanel.setCursor(aDefaultCursor);
	}

	/**
	 * Set application title text.
	 * 
	 * @param additionalText
	 */
	/*
	 * public void setTitle(String additionalText) { if (additionalText != null)
	 * { super.setTitle(aAppName + additionalText); } else {
	 * super.setTitle(aAppName); } }
	 */

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
	 * Add main panel and restore size.
	 * 
	 * @param panel
	 */
	/*
	 * public void restore(JPanel panel, JFrame frm) { aMainPanel = panel;
	 * mainFrame = frm;
	 * 
	 * //add(aMainPanel); // double w = Pref.getPref("w", 800.0); // double h =
	 * Pref.getPref("h", 600.0); // double x = Pref.getPref("x", 50.0); //
	 * double y = Pref.getPref("y", 50.0); // Dimension screenSize =
	 * Toolkit.getDefaultToolkit().getScreenSize(); // if (w < 100) { // w =
	 * 100; // } // if (w > 3000) { // w = 800; // } // if (h < 100) { // h =
	 * 100; // } // if (h > 3000) { // h = 600; // } // if (x < 0) { // x = 0;
	 * // } // if (x > screenSize.getWidth()) { // x = 50; // } // if (y < 0) {
	 * // y = 0; // } // if (y > screenSize.getHeight()) { // y = 50; // }
	 * 
	 * mainFrame.setContentPane(aMainPanel); mainFrame.setSize(500, 400);
	 * mainFrame.setLocation(100, 100); //mainFrame.pack(); //setSize((int) w,
	 * (int) h); //setLocation((int) x, (int) y);
	 * 
	 * // addWindowListener(new java.awt.event.WindowAdapter() { // public void
	 * windowClosing(java.awt.event.WindowEvent e) { // quit(); // } // });
	 * 
	 * mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); }
	 */

	/*
	 * @param mainFrame the mainFrame to set
	 */
	/*
	 * public void setMainFrame(JFrame mainFrame) { Application.mainFrame =
	 * mainFrame; }
	 */

	/**
	 * @param mainPanel
	 *            the aMainPanel to set
	 */
	public void addMainPanel(JPanel mainPanel) {
		aMainPanel = mainPanel;
		add(aMainPanel);
	}
}
