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

package function.rip.task;

import java.awt.Dimension;
import java.awt.Toolkit;

import function.rip.Rip;
import function.shared.UI.ProgressDialog;
import function.shared.task.Command;
import function.shared.task.parser.ContentsParserThread;
import function.shared.thread.process.ProcessRunner;
import function.shared.thread.process.ReadProc;
import function.shared.util.Log;
import function.shared.util.Progress;


import view.NewMain;


/**
 * Load tracks from cd.
 */
public class LoadCDTask {
	/**
     *
     */
	public LoadCDTask() {
	}

	/**
	 * Load tracks from cd device.
	 */
	public void doTask() {
		ProgressDialog dlg = new ProgressDialog(NewMain.frame, "Loading CD",
				"&Cancel", true);
		ContentsParserThread parserThread = null;
		ProcessRunner runner = null;
		boolean failed = false;

		try {
			Progress.get().clear();
			Log.get().clear();
			Rip.get().getWin().setAlbum(null);

			parserThread = new ContentsParserThread(Log.get(), Progress.get());
			parserThread.aAlbum.aCD = true;
			runner = new ReadProc(Log.get(), null, Command.getCDTOCDecoder(),
					parserThread);

			// dlg.centerOnApplication();
			Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
			dlg.setLocation((d.width / 2) - (dlg.getWidth() / 2),
					(d.height / 2) - (dlg.getHeight() / 2));

			dlg.show(runner);
		} catch (Exception e) {
			Log.get().addTime(1, e.getMessage());
			failed = true;
		} finally {
			String message = "CD tracks have been loaded";
			dlg.cancelTask();

			if (runner != null) {
				if (runner.isAlive()) {
					Rip.get().getThreadCollector().add(runner);
					failed = true;
				} else if (runner.hasFailed()) {
					message = "Loading CD failed! Check the log for more information";
					failed = true;
				} else {
					Rip.get().getWin().setAlbum(parserThread.aAlbum);
				}
			}

			if (dlg.hasBeenStopped()) {
				Rip.get().getWin().getStatusBar()
						.setNotifyMessage("Loading CD has been canceled!");
			} else if (failed) {
				Rip.get().getWin().getStatusBar().setErrorMessage(message);
			} else {
				Rip.get().getWin().getStatusBar().setMessage(message);
			}
		}
	}
}