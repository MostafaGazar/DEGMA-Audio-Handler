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

package RipConvertShared.thread.process;

import RipConvertShared.thread.StreamThread;
import RipConvertShared.util.Log;
import RipConvertShared.util.ProcessParam;
import RipConvertShared.util.Progress;

/**
 * Process thread that uses other threads for reading from child thread outputs.
 * One of the reader can also write to another child process.
 */
public class ReadProcWriteFile extends ProcessRunner {
	/**
	 * Create a ProcessRunner object that can read from one process and write to
	 * a file.
	 * 
	 * @param log
	 * @param progress
	 * @param readParam
	 * @param writeFileName
	 * @param thread1
	 * @param thread2
	 */
	public ReadProcWriteFile(Log log, Progress progress,
			ProcessParam readParam, String writeFileName, StreamThread thread1,
			StreamThread thread2) {
		super(log, progress);
		aThread1 = thread1;
		aThread2 = thread2;
		aReadParam = readParam;
		aWriteFileName = writeFileName;
	}
}