package UI.fileChooser;

import java.io.File;
import javax.swing.ImageIcon;

public class Utils {
	public final static String AVI = "avi";
	public final static String MP3 = "mp3";
	public final static String MP4 = "mp4";
	public final static String MPG = "mpg";
	public final static String OGG = "ogg";
	public final static String WAV = "wav";
	public final static String WMA = "wma";
	public final static String AAC = "aac";
	public final static String AIFF = "aiff";
	public final static String MPEG = "mpeg";
	public static final String M4A = "m4a";
	public static final String MID = "mid";
	public static final String MIDI = "midi";

	/*
	 * Get the extension of a file.
	 */
	public static String getExtension(File f) {
		String ext = null;
		String s = f.getName();
		int i = s.lastIndexOf('.');

		if (i > 0 && i < s.length() - 1) {
			ext = s.substring(i + 1).toLowerCase();
		}
		return ext;
	}

	/** Returns an ImageIcon, or null if the path was invalid. */
	protected static ImageIcon createImageIcon(String path) {
		java.net.URL imgURL = Utils.class.getResource(path);
		if (imgURL != null) {
			return new ImageIcon(imgURL);
		} else {
			System.err.println("Couldn't find file: " + path);
			return null;
		}
	}
}
