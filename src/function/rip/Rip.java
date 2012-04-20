package function.rip;

import javax.swing.JFrame;
import javax.swing.UIManager;

import function.rip.RipPanel;
import function.shared.thread.ThreadCollector;
import function.shared.ui.Application;
import function.shared.util.Log;


import util.Constants;


/**
 * This is the application object.<br>
 * And also the where the applications starts from.
 */
public class Rip extends Application {
	private static final long serialVersionUID = 666L;
	private ThreadCollector aThreads = new ThreadCollector();
	private static Rip aaRipper;
	private RipPanel aMainWindow = null;

	/**
	 * Create gui and try to restore size and position from last time.
	 */
	public Rip() {
		super();// Constants.PREFERENCE_NAME, Constants.PREFERENCE_NAME);

		Log.get().setLogLevel(1);
		aaRipper = this;
		aMainWindow = new RipPanel();// convert type
		aThreads.start();
		addMainPanel(aMainWindow);
		// restore(aMainWindow);
		// aMainWindow.aMenuPanel.enableUI();
		/*
		 * public void enableUI() { Album album =
		 * JRipper.get().getWin().getAlbum();
		 * 
		 * if (album != null && album.aTracks.size() > 0) {
		 * aConvertCD.setEnabled(true); aConvertCD2.setEnabled(album.aCD);
		 * aSelectAll.setEnabled(true); aSelectNone.setEnabled(true); } else {
		 * aConvertCD.setEnabled(false); aConvertCD2.setEnabled(false);
		 * aSelectAll.setEnabled(false); aSelectNone.setEnabled(false); } }
		 */
		// setIcon("icon.png");

		if (isWindows()) {
			Constants.CD_DEVICE_DEFAULT = "1,0,0";
		}
	}

	/*
	 * Encode a string in a specific codepage.
	 * 
	 * @param inString
	 * 
	 * @return public static String encode (String inString) { return inString;
	 * try { return new String(inString.getBytes(), aaCodepage); } catch
	 * (UnsupportedEncodingException e) { return inString; } }
	 */

	/**
	 * Return application object.
	 * 
	 * @return Application object
	 */
	public static Rip get() {
		return aaRipper;
	}

	/**
	 * Get thread collector
	 * 
	 * @return The thread collector thread
	 */
	public ThreadCollector getThreadCollector() {
		return aThreads;
	}

	/**
	 * Return main panel.
	 * 
	 * @return Main pamel object
	 */
	public RipPanel getWin() {
		return aMainWindow;
	}

	/**
	 * Quit application. Save last used path and screen position.
	 */
	public void quit() {
		aThreads.IS_RUNNING = false;
		// super.quit();
	}

	/**
	 * Start app. It accepts no cmd parametrar.
	 * 
	 * @param args
	 *            Not used
	 */
	public static void main(String[] args) {
		try {
			JFrame.setDefaultLookAndFeelDecorated(true);
			UIManager
					.setLookAndFeel("org.jvnet.substance.skin.SubstanceNebulaLookAndFeel");

			// UIManager.setLookAndFeel(new SubstanceLookAndFeel());
			// SwingUtilities.updateComponentTreeUI(ripper);
		} catch (Exception e) {
			e.printStackTrace();
		}

		// try {
		final Rip ripper = new Rip();

		JFrame frame = new JFrame("try");
		// ripper.setMainFrame(frame);
		// ripper.restore(ripper.getWin(), frame);

		frame.setContentPane(ripper.getWin());

		/*
		 * Pref.setPref("w", 500); Pref.setPref("h", 400); Pref.setPref("x",
		 * 100); Pref.setPref("y", 100);
		 */

		frame.setSize(500, 400);
		frame.setLocation(100, 100);
		// frame.pack();

		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		/*
		 * javax.swing.SwingUtilities.invokeLater(new Runnable() { public void
		 * run() { ripper.setVisible(true); } });
		 */
		// }
		// catch (Exception e) {
		/*
		 * System.out.println("Exception caught in main");
		 * System.out.println(e.getMessage());
		 * System.out.println(e.getStackTrace()); Log.get().addTime(1,
		 * "Exception caught in main: " + e.getMessage()); Log.get().addTime(1,
		 * "Stacktrace: " + e.getStackTrace()); System.exit(1);
		 */
		// }

		/*
		 * try { UIManager.setLookAndFeel(new SubstanceLookAndFeel()); } catch
		 * (Exception exc) { }
		 */
	}
}
