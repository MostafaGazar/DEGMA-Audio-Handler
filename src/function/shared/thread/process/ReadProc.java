/*
    Copyright (C) 2005 - 2007 Mikael H�gdahl - dronten@gmail.com

    This library is free software; you can redistribute it and/or
    modify it under the terms of the GNU Lesser BaseSetupPanel Public
    License as published by the Free Software Foundation; either
    version 2.1 of the License, or (at your option) any later version.

    This library is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
    Lesser BaseSetupPanel Public License for more details.

    You should have received a copy of the GNU Lesser BaseSetupPanel Public
    License along with this library; if not, write to the Free Software
    Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA
 */
package function.shared.thread.process;

import function.shared.thread.StreamThread;
import function.shared.util.Log;
import function.shared.util.ProcessParam;
import function.shared.util.Progress;

/**
 * Process thread that uses other threads for reading from child thread outputs.
 * One of the reader can also write to another child process.
 */
public class ReadProc extends ProcessRunner {
	
	/**
	 * Create a ProcessRunner object that executes a process and reads message
	 * from stdin.
	 * 
	 * @param log
	 *            Log object
	 * @param progress
	 *            Progress object
	 * @param readParam
	 *            Read params object
	 * @param stdinThread
	 *            Stdin thread object
	 */
	public ReadProc(Log log, Progress progress, ProcessParam readParam,
			StreamThread stdinThread) {
		super(log, progress);
		aThread1 = stdinThread;
		aReadParam = readParam;
	}
}
