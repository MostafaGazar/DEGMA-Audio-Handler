package view.fileChooser;

import java.io.File;
import javax.swing.filechooser.*;

public class AudioVideoFilter extends FileFilter {

	@Override
	public boolean accept(File f) {
		/*
		 * if (f.isDirectory()) { return true; }
		 * 
		 * String extension = Utils.getExtension(f); if (extension != null) { if
		 * (extension.compareToIgnoreCase(Utils.AVI) == 0||
		 * extension.compareToIgnoreCase(Utils.MP3) == 0||
		 * extension.compareToIgnoreCase(Utils.MP4) == 0||
		 * extension.compareToIgnoreCase(Utils.M4A) == 0||
		 * extension.compareToIgnoreCase(Utils.MPG) == 0||
		 * extension.compareToIgnoreCase(Utils.MPEG)== 0||
		 * extension.compareToIgnoreCase(Utils.MID) == 0||
		 * extension.compareToIgnoreCase(Utils.MIDI)== 0||
		 * extension.compareToIgnoreCase(Utils.OGG) == 0||
		 * extension.compareToIgnoreCase(Utils.WAV) == 0||
		 * extension.compareToIgnoreCase(Utils.WMA) == 0||
		 * extension.compareToIgnoreCase(Utils.AAC) == 0||
		 * extension.compareToIgnoreCase(Utils.AIFF)== 0){ return true; } else {
		 * return true; } }
		 */

		return true;
	}

	// The description of this filter
	@Override
	public String getDescription() {
		return "Just Audio and Video";
	}
}
