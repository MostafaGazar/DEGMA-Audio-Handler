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

import function.shared.thread.StreamThread;
import function.shared.util.Log;

/**
 * Save lines from stderr to the log object.
 */
public class ErrorParserThread extends StreamThread {
	/**
	 * @param log
	 *            - Log object
	 */
	public ErrorParserThread(Log log) {
		super(log, null, ReadType.READ_STDERR_LINES, false);
	}

	/**
	 * @param line
	 */
	@Override
	public void data(String line) {
		addLog(2, line);
	}
}
