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


import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import function.shared.thread.StreamThread;
import function.shared.util.Log;
import function.shared.util.Progress;


/**
 * Parse the content info from the cdda2aw program and set album information.
 */
public class ScanbusParserThread extends StreamThread {
	private static final Pattern DEVICE = Pattern
			.compile("\\s*(\\d+,\\d+,\\d+)\\s+\\d+\\)\\s(.*)([rR][oO][mM])(.*)");
	public Vector<String> aDevices = new Vector<String>();

	/**
	 * Create parser and set work message.
	 */
	public ScanbusParserThread(Log log, Progress progress) throws Exception {
		super(log, progress, ReadType.READ_STDIN_LINES, false);
		aProgress.appendTask(2000, "Trying to scan for cd devices...", "");
		aDevices.add("--- Probed Devices ---");
	}

	/**
	 * Callback for the input data.
	 * 
	 * @param line
	 */
	@Override
	public void data(String line) {
		Matcher m1 = DEVICE.matcher(line);

		if (m1.find()) {
			aDevices.add(m1.group(1) + " - " + m1.group(2) + m1.group(3)
					+ m1.group(4));
		}
		addLog(1, line);
	}
}
