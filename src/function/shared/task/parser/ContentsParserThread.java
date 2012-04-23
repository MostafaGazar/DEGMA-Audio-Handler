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
package function.shared.task.parser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import function.shared.music.Album;
import function.shared.thread.StreamThread;
import function.shared.util.Log;
import function.shared.util.Progress;

/**
 * Parse the content info from the cdda2aw program and set album information.
 */
public class ContentsParserThread extends StreamThread {
	private static final Pattern TRACK = Pattern
			.compile("T\\d+:\\s+(\\d*)\\s*(\\d*:\\d\\d.\\d\\d).*title.*?'(.*)' from '");
	private static final Pattern ALBUM = Pattern
			.compile("Album title: '(.*)'.*? from '(.*)'");
	private static final Pattern CDDB = Pattern.compile("CDDB discid: (.*)");
	private static final Pattern TIME = Pattern
			.compile("Tracks:(\\d*)\\s*(\\d*):(\\d*).(\\d*)");
	private static final Pattern FAILED = Pattern
			.compile("cddb connect failed:");
	public Album aAlbum = new Album();

	/**
	 * Create parser and set work message.
	 */
	public ContentsParserThread(Log log, Progress progress) throws Exception {
		super(log, progress, ReadType.READ_STDERR_LINES, false);
		aProgress.appendTask(2000, "Reading contents of the cd...", "");
	}

	/**
	 * @return true if we has failed with reading disc toc
	 */
	@Override
	public boolean hasFailed() {
		if (aAlbum.aDiscID.length() == 0) {
			addLog(1, "Can't read disc id number!");
			return true;
		} else {
			return super.hasFailed();
		}
	}

	/**
	 * Callback for the input data.
	 * 
	 * @param line
	 *            String from cdda2wav
	 */
	@Override
	public void data(String line) {
		Matcher m1 = TRACK.matcher(line);
		Matcher m2 = ALBUM.matcher(line);
		Matcher m3 = CDDB.matcher(line);
		Matcher m4 = TIME.matcher(line);
		Matcher m5 = FAILED.matcher(line);

		if (m1.find()) {
			aAlbum.addTrack(m1.group(3), m1.group(2), m1.group(1));
		} else if (m2.find()) {
			aAlbum.aAlbum = m2.group(1);
			aAlbum.aArtist = m2.group(2);
		} else if (m3.find()) {
			aAlbum.setDiscID(m3.group(1));
		} else if (m4.find()) {
			int min = 0;
			int sec = 0;

			try {
				min = Integer.valueOf(m4.group(2)) * 60;
				sec = Integer.valueOf(m4.group(3));
			} catch (NumberFormatException e) {
			}

			aAlbum.aTracksString = m4.group(1);
			aAlbum.aTimeString = m4.group(2) + ":" + m4.group(3) + "."
					+ m4.group(4);
			aAlbum.aSeconds = String.valueOf(min + sec);
		} else if (m5.find()) {
			setFailed(true);
		}

		addLog(1, line);
	}

	@Override
	public String toString() {
		if (aAlbum != null) {
			return "ContentsParserThread.toString() " + aAlbum.toString();
		} else {
			return "ContentsParserThread.toString() ";
		}
	}
}
