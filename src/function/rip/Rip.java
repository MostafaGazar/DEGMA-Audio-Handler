package function.rip;

import javax.swing.JFrame;
import javax.swing.UIManager;

import function.rip.RipPanel;
import function.shared.thread.ThreadCollector;
import function.shared.ui.Application;
import function.shared.util.Log;

import util.Constants;

/**
 * @author Mostafa Gazar, eng.mostafa.gazar@gmail.com
 */
public class Rip extends Application {
	private static final long serialVersionUID = 666L;
	private ThreadCollector aThreads = new ThreadCollector();
	private static Rip aaRipper;
	private RipPanel aMainWindow = null;

	/**
	 * Create GUI and try to restore size and position from last time.
	 */
	public Rip() {
		super();

		Log.get().setLogLevel(1);
		aaRipper = this;
		aMainWindow = new RipPanel();// Convert type.
		aThreads.start();
		addMainPanel(aMainWindow);

		if (isWindows()) {
			Constants.CD_DEVICE_DEFAULT = "1,0,0";
		}
	}

	/**
	 * Return application object.
	 * 
	 * @return Application object
	 */
	public static Rip get() {
		return aaRipper;
	}

	/**
	 * Get thread collector.
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
	}

	/**
	 * Testing start point. It accepts no parametrars.
	 * @param args Not used
	 */
	public static void main(String[] args) {
		try {
			JFrame.setDefaultLookAndFeelDecorated(true);
			UIManager
					.setLookAndFeel("org.jvnet.substance.skin.SubstanceNebulaLookAndFeel");
		} catch (Exception e) {
			e.printStackTrace();
		}

		final Rip ripper = new Rip();

		JFrame frame = new JFrame("Test Rip");

		frame.setContentPane(ripper.getWin());

		frame.setSize(500, 400);
		frame.setLocation(100, 100);

		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}
