package ui;

import java.net.URL;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.MediaTracker;
import java.awt.Toolkit;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.JFrame;
import javax.swing.Timer;
import javax.swing.JWindow;

/**
 * Class representing an application splash screen.
 * <p>
 * Typical usage:
 * 
 * <pre>
 * SplashScreen splashScreen = new SplashScreen(&quot;/com/company/splash.jpg&quot;);
 * splashScreen.open(3000);
 * </pre>
 * 
 * @author <a href="mailto:jacob.dreyer@geosoft.no">Jacob Dreyer</a>
 * @edited Mostafa Gazar, eng.mostafa.gazar@gmail.com
 */
public class SplashScreen extends JWindow {
	private static final long serialVersionUID = 1L;

	private Image splashImage;
	private int splashPositionX;
	private int splashPositionY;
	private int splashPositionWidth;
	private int splashPositionHeight;

	/**
	 * Create a new splash screen object of the specified image. The image file
	 * is located and referred to through the deployment, not the local file
	 * system; A typical value might be "/com/company/splash.jpg".
	 * 
	 * @param imageFileName
	 *            Name of image file resource to act as splash screen.
	 */
	public SplashScreen(JFrame owner, String imageFileName) {
		super(owner);
		setAlwaysOnTop(true);

		try {
			Toolkit toolkit = Toolkit.getDefaultToolkit();

			URL imageUrl = getClass().getResource(imageFileName);
			splashImage = toolkit.getImage(imageUrl);

			MediaTracker mediaTracker = new MediaTracker(this);
			mediaTracker.addImage(splashImage, 0);
			mediaTracker.waitForID(0);

			splashPositionWidth = splashImage.getWidth(this);
			splashPositionHeight = splashImage.getHeight(this);

			Dimension screenSize = toolkit.getScreenSize();

			splashPositionX = (screenSize.width - splashPositionWidth) / 2;
			splashPositionY = (screenSize.height - splashPositionHeight) / 2;
		} catch (Exception exception) {
			exception.printStackTrace();
			splashImage = null;
		}
	}

	/**
	 * Open the splash screen and keep it open for the specified duration or
	 * until close() is called explicitly.
	 */
	public void open(int nMilliseconds) {
		if (splashImage == null)
			return;

		Timer timer = new Timer(Integer.MAX_VALUE, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				((Timer) event.getSource()).stop();
				close();
			};
		});

		timer.setInitialDelay(nMilliseconds);
		timer.start();

		setBounds(splashPositionX, splashPositionY, splashPositionWidth,
				splashPositionHeight);
		setVisible(true);
	}

	/**
	 * Close the splash screen.
	 */
	public void close() {
		setVisible(false);
		dispose();
	}

	/**
	 * Paint the splash screen window.
	 * 
	 * @param graphics
	 *            The graphics instance.
	 */
	@Override
	public void paint(Graphics graphics) {
		// System.out.println ("paint");
		if (splashImage == null)
			return;
		graphics.drawImage(splashImage, 0, 0, splashPositionWidth,
				splashPositionHeight, this);
	}

	public static void main(String[] args) {
		SplashScreen theSplashScreen = new SplashScreen(new JFrame(),
				"icons/splash.png");
		theSplashScreen.open(2000);
	}
}
