package view.fileChooser;

import java.io.File;
import javax.swing.*;
import javax.swing.filechooser.*;

public class ImageFileView extends FileView {
	ImageIcon aviIcon = Utils.createImageIcon("images/avi.png");
	ImageIcon mp3Icon = Utils.createImageIcon("images/mp3.png");
	ImageIcon mp4Icon = Utils.createImageIcon("images/mp4.png");
	ImageIcon mpgIcon = Utils.createImageIcon("images/mpg.png");
	ImageIcon oggIcon = Utils.createImageIcon("images/ogg.png");
	ImageIcon wavIcon = Utils.createImageIcon("images/wav.png");
	ImageIcon wmaIcon = Utils.createImageIcon("images/wma.png");
	ImageIcon aacIcon = Utils.createImageIcon("images/aac.png");
	ImageIcon aiffIcon = Utils.createImageIcon("images/aiff.png");
	ImageIcon midIcon = Utils.createImageIcon("images/mid.png");

	@Override
	public String getName(File f) {
		return null; // let the L&F FileView figure this out
	}

	@Override
	public String getDescription(File f) {
		return null; // let the L&F FileView figure this out
	}

	@Override
	public Boolean isTraversable(File f) {
		return null; // let the L&F FileView figure this out
	}

	@Override
	public String getTypeDescription(File f) {
		String extension = Utils.getExtension(f);
		String type = null;

		if (extension != null) {
			if (extension.compareToIgnoreCase(Utils.AVI) == 0) {
				type = "avi type";
			} else if (extension.compareToIgnoreCase(Utils.MP3) == 0) {
				type = "mp3 type";
			} else if (extension.compareToIgnoreCase(Utils.MP4) == 0
					|| extension.compareToIgnoreCase(Utils.M4A) == 0) {
				type = "mp4 type";
			} else if (extension.compareToIgnoreCase(Utils.MPG) == 0
					|| extension.compareToIgnoreCase(Utils.MPEG) == 0) {
				type = "mpg type";
			} else if (extension.compareToIgnoreCase(Utils.OGG) == 0) {
				type = "ogg type";
			} else if (extension.compareToIgnoreCase(Utils.WAV) == 0) {
				type = "wav type";
			} else if (extension.compareToIgnoreCase(Utils.WMA) == 0) {
				type = "wma type";
			} else if (extension.compareToIgnoreCase(Utils.AAC) == 0) {
				type = "aac type";
			} else if (extension.compareToIgnoreCase(Utils.AIFF) == 0) {
				type = "aiff type";
			} else if (extension.compareToIgnoreCase(Utils.MID) == 0
					|| extension.compareToIgnoreCase(Utils.MIDI) == 0) {
				type = "mid type";
			}
		}
		return type;
	}

	@Override
	public Icon getIcon(File f) {
		String extension = Utils.getExtension(f);
		Icon icon = null;

		if (extension != null) {
			if (extension.compareToIgnoreCase(Utils.AVI) == 0) {
				icon = aviIcon;
			} else if (extension.compareToIgnoreCase(Utils.MP3) == 0) {
				icon = mp3Icon;
			} else if (extension.compareToIgnoreCase(Utils.MP4) == 0
					|| extension.compareToIgnoreCase(Utils.M4A) == 0) {
				icon = mp4Icon;
			} else if (extension.compareToIgnoreCase(Utils.MPG) == 0
					|| extension.compareToIgnoreCase(Utils.MPEG) == 0) {
				icon = mpgIcon;
			} else if (extension.compareToIgnoreCase(Utils.OGG) == 0) {
				icon = oggIcon;
			} else if (extension.compareToIgnoreCase(Utils.WAV) == 0) {
				icon = wavIcon;
			} else if (extension.compareToIgnoreCase(Utils.WMA) == 0) {
				icon = wmaIcon;
			} else if (extension.compareToIgnoreCase(Utils.AAC) == 0) {
				icon = aacIcon;
			} else if (extension.compareToIgnoreCase(Utils.AIFF) == 0) {
				icon = aiffIcon;
			} else if (extension.compareToIgnoreCase(Utils.MID) == 0
					|| extension.compareToIgnoreCase(Utils.MIDI) == 0) {
				icon = midIcon;
			}
		}
		return icon;
	}
}
