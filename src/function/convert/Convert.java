package function.convert;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import function.convert.ConvertPanel;
import function.shared.thread.ThreadCollector;
import function.shared.ui.Application;
import function.shared.util.Log;
import function.shared.util.Pref;



import util.Constants;

/**
 * @author Mostafa Gazar, eng.mostafa.gazar@gmail.com
 */
public class Convert extends Application {
	private static final long serialVersionUID = 666L;
	private ThreadCollector aThreads = new ThreadCollector();
	private static Convert aConvert;
	private ConvertPanel aMainWindow = null;

	/**
	 * Create GUI and try to restore size and position from last time.
	 */
	public Convert() {
		super();

		Log.get().setLogLevel(1);
		aConvert = this;
		aMainWindow = new ConvertPanel();// convert type
		aThreads.start();
		addMainPanel(aMainWindow);

		if (isWindows()) {
			Constants.CD_DEVICE_DEFAULT = "1,0,0";
		}
	}

	/**
	 * Return application object.
	 * @return Application object
	 */
	public static Convert get() {
		return aConvert;
	}

	/**
	 * Get thread collector
	 * @return The thread collector thread
	 */
	public ThreadCollector getThreadCollector() {
		return aThreads;
	}

	/**
	 * Return main panel.
	 * @return Main panel object
	 */
	public ConvertPanel getWin() {
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
		new Pref(Constants.PREFERENCE_NAME);
		try {
			JFrame.setDefaultLookAndFeelDecorated(true);
			UIManager.setLookAndFeel("org.jvnet.substance.skin.SubstanceBusinessBlueSteelLookAndFeel");
		} catch (Exception e) {
			e.printStackTrace();
		}
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				final Convert ripper = new Convert();

				JFrame frame = new JFrame("Test Convert");

				frame.setContentPane(ripper.getWin());

				frame.setSize(500, 600);
				frame.setLocation(100, 100);

				frame.setVisible(true);
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

			}
		});
	}
}
