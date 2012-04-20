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
import java.util.Vector;

import function.rip.Rip;
import function.shared.task.Command;
import function.shared.task.parser.ErrorParserThread;
import function.shared.task.parser.ScanbusParserThread;
import function.shared.thread.process.ProcessRunner;
import function.shared.thread.process.ReadStdoutAndStderrProc;
import function.shared.ui.ProgressDialog;
import function.shared.util.Log;
import function.shared.util.Progress;


import ui.NewMain;

/**
 * Scan for available SCSI drives.
 */
public class ScanbusTask {
	public Vector<String> aDevices = null;

	/**
     *
     */
	public ScanbusTask() {
	}

	/**
	 * Run cdda2wav for an scanbus operation.
	 */
	public void doTask() {
		ProgressDialog dlg = new ProgressDialog(NewMain.frame,
				"Probing For SCSI Devices", "&Cancel", true);
		ScanbusParserThread parserThread = null;
		ErrorParserThread errorThread = null;
		ProcessRunner runner = null;
		Rip.get().getWin().getStatusBar().setMessage("Probing after devices");

		try {
			Progress.get().clear();
			Log.get().clear();
			Rip.get().getWin().setAlbum(null);

			parserThread = new ScanbusParserThread(Log.get(), Progress.get());
			errorThread = new ErrorParserThread(Log.get());
			runner = new ReadStdoutAndStderrProc(Log.get(), null,
					Command.getScanbus(), parserThread, errorThread);

			// dlg.centerOnApplication();
			Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
			dlg.setLocation((d.width / 2) - (dlg.getWidth() / 2),
					(d.height / 2) - (dlg.getHeight() / 2));

			dlg.show(runner);
		} catch (Exception e) {
			Log.get().addTime(1, e.getMessage());
		} finally {
			dlg.cancelTask();

			if (runner != null) {
				if (runner.isAlive()) {
					Rip.get().getThreadCollector().add(runner);
				} else if (runner.hasFailed()) {
					Rip.get()
							.getWin()
							.getStatusBar()
							.setMessage(
									"Something failed when probing for SCSI devices!");
				} else {
					aDevices = parserThread.aDevices;
				}
			}
		}
	}
}