/*
    Copyright (C) 2005 - 2007 Mikael H�gdahl - dronten@gmail.com

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
package function.rip.task;

import java.awt.*;
import java.util.Vector;

import function.rip.Rip;
import function.shared.music.Album;
import function.shared.music.Track;
import function.shared.task.Command;
import function.shared.thread.BaseThread;
import function.shared.ui.HelpDialog;
import function.shared.ui.ProgressDialog;
import function.shared.util.Log;
import function.shared.util.Progress;
import function.shared.util.StopWatch;

import util.Constants;
import view.Messages;
import view.NewMain;

/**
 * The audio converter task.<br>
 * Ripp cd or transcode audio files.
 * 
 * @edited Mostafa Gazar, eng.mostafa.gazar@gmail.com
 */
public class RipperTask {
	private Album aAlbum;
	private int aEncoderIndex = 0;

	/**
	 * @param album
	 * @param encoderIndex
	 */
	public RipperTask(Album album, int encoderIndex) {
		aEncoderIndex = encoderIndex;
		aAlbum = album;
	}

	/**
	 * Encode/decode selected tracks.
	 */
	public void doTask() {
		StopWatch watch = new StopWatch();
		Vector<BaseThread> threads = new Vector<BaseThread>();
		ProgressDialog dlg = new ProgressDialog(NewMain.frame,
				Messages.getString("Constants.encodingSelectedTracks"),
				"&Cancel", true);
		boolean failed = false;
		String message = "";

		dlg.showMajorProgress();
		Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
		dlg.setLocation((d.width / 2) - (dlg.getWidth() / 2), (d.height / 2)
				- (dlg.getHeight() / 2));

		Rip.get()
				.getWin()
				.getStatusBar()
				.setMessage(
						Messages.getString("Constants.encodingSelectedTracks"));

		Progress.get().clear();
		Log.get().clear();
		watch.start();

		try {
			for (Track track : aAlbum.aTracks) {
				if (track.aSelected) {
					track.setEncoderIndex(aEncoderIndex);
					String fileName = track.createFileName(aAlbum, false);

					if (track.getDecoder() != Constants.CD_TRACK
							&& track.aFile.equalsIgnoreCase(fileName)) {
						throw new Exception(
								Messages.getString("Constants.ripMessage1")
										+ track.aFile);
					} else {
						switch (track.getDecoder()) {
						case Constants.CD_TRACK:
							Command.createEncoderForCD(aAlbum, track, threads,
									fileName, false);
							break;

						case Constants.MP3_TRACK:
						case Constants.OGG_TRACK:
						case Constants.M4A_TRACK:
						case Constants.FLAC_TRACK:
							Command.createEncoderForFiles(aAlbum, track,
									threads, fileName);
							break;

						case Constants.WAV_TRACK:
							Command.createEncoderForWavFiles(aAlbum, track,
									threads, fileName);
							break;

						default:
							throw new Exception(
									Messages.getString("Constants.ripMessage2"));
						}
					}
				}
			}

			if (threads.size() < 1) {
				throw new Exception(Messages.getString("Constants.ripMessage3"));

			}

			// Run all threads one by one in the progress dialog.
			dlg.show(threads); 

			if (dlg.hasBeenStopped()) {
				throw new Exception(Messages.getString("Constants.ripMessage4"));

			} else if (dlg.hasFailed()) {
				throw new Exception(Messages.getString("Constants.ripMessage5"));

			}
		} catch (Exception e) {
			Log.get().addTime(1, e.getMessage());
			message = e.getMessage();
			failed = true;
		} finally {
			dlg.cancelTask();
			Rip.get().getThreadCollector().add(threads);
			watch.stop();

			if (dlg.hasBeenStopped()) {
				Rip.get().getWin().getStatusBar().setNotifyMessage(message);
			} else if (failed) {
				HelpDialog showLog = new HelpDialog(NewMain.frame,
						NewMain.PROGRAM_NAME + " Log", "&Close");
				showLog.setText(Log.get().getLogMessage());
				d = Toolkit.getDefaultToolkit().getScreenSize();
				showLog.setLocation((d.width / 2) - (showLog.getWidth() / 2),
						(d.height / 2) - (showLog.getHeight() / 2));

				showLog.setVisible(true);
				Rip.get().getWin().getStatusBar().setErrorMessage(message);
			} else {
				Rip.get()
						.getWin()
						.getStatusBar()
						.setNotifyMessage(
								Messages.getString("Constants.ripMessage6"),
								watch.toString());
				Toolkit.getDefaultToolkit().beep();
			}
		}
	}
}