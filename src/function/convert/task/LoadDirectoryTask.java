/*
    Copyright (C) 2005 - 2007 Mikael Högdahl - dronten@gmail.com

    This library is free software; you can redistribute it and/or
    modify it under the terms of the GNU Lesser General Public
    License as published by the Free Software Foundation; either
    version 2.1 of the License, or (at your option) any later version.

    This library is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
    Lesser General Public License for more details.

    You should have received a copy of the GNU Lesser General Public
    License along with this library; if not, write to the Free Software
    Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA
 */
package function.convert.task;

import javax.swing.*;

import function.convert.Convert;
import function.shared.music.Album;
import function.shared.music.MP3Genre;
import function.shared.music.Track;
import function.shared.ui.Application;
import function.shared.util.Pref;

import util.Constants;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * Load audio files from a local directory.
 * @edited Mostafa Gazar, eng.mostafa.gazar@gmail.com
 */
public class LoadDirectoryTask {

	/**
	 * Fire up the dir selection dialog and get all those wav and flac files.
	 */
	public void doTask() {
		JFileChooser chooser = new JFileChooser();
		File currDir = new File(Pref.getPref(Constants.BASE_DIRECTORY_KEY, ""));

		chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		chooser.setDialogTitle("Select Directory");
		if (currDir.isDirectory()) {
			chooser.setCurrentDirectory(currDir);
		}
		if (chooser.showOpenDialog(Application.get()) == JFileChooser.APPROVE_OPTION) {
			File dir = new File(chooser.getSelectedFile().getPath());
			File[] files = dir.listFiles();
			ArrayList<File> wavFiles = new ArrayList<File>();

			if (files != null) {
				for (File f : files) {
					if (f.canRead()) {
						if (f.isFile()) {// Change Track.java
							if (f.getName().toLowerCase().endsWith(".wav")
									|| f.getName().toLowerCase()
											.endsWith(".mp3")
									|| f.getName().toLowerCase()
											.endsWith(".ogg")
									|| f.getName().toLowerCase()
											.endsWith(".m4a")
									|| f.getName().toLowerCase()
											.endsWith(".mp4")
									|| f.getName().toLowerCase()
											.endsWith(".acc")
									|| f.getName().toLowerCase()
											.endsWith(".flac")) {
								wavFiles.add(new File(f.getPath()));
							}
						}
					}
				}
			}

			if (wavFiles.size() > 0) {
				File albumFile = wavFiles.get(0).getParentFile();
				File artistFile = ((albumFile != null) ? albumFile
						.getParentFile() : albumFile);
				Album album = new Album(
						artistFile != null ? artistFile.getName() : "",
						albumFile != null ? albumFile.getName() : "",
						MP3Genre.DEFAULT_STRING, "", "");

				for (File f : wavFiles) {
					album.addTrack(f.getName(), f);
				}

				Collections.sort(album.aTracks, new FileNameComparator());

				int count = 1;
				for (Track track : album.aTracks) {
					track.aTrack = count;
					count++;
				}

				Convert.get().getWin().setAlbum(album);
			} else {
				Album album = new Album("", "", "", "", "");
				Convert.get().getWin().setAlbum(album);
				Convert.get().getWin().getStatusBar()
						.setErrorMessage("No tracks have been found!");
			}
		}
	}

	/**
	 * Compare tracks.
	 */
	class FileNameComparator implements Comparator<Object> {
		/**
		 * Compare two tracks.
		 */
		@Override
		public int compare(Object o, Object o1) {
			Track file1 = (Track) o;
			Track file2 = (Track) o1;

			return file1.aFile.compareTo(file2.aFile);
		}
	}
}