/*
    Copyright (C) 2005 - 2007 Mikael Högdahl - dronten@gmail.com

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

import RipConvertShared.thread.BaseThread;
import RipConvertShared.thread.StreamThread;
import RipConvertShared.util.Log;
import RipConvertShared.util.ProcessParam;
import RipConvertShared.util.Progress;

import java.io.*;

/**
 * Process thread that uses other threads for reading from child thread outputs.
 * One of the reader can also write to another child process.
 */
public class ProcessRunner extends BaseThread {
	protected int aExitReadCode = 0;
	protected int aExitWriteCode = 0;

	public StreamThread aThread1 = null;
	protected StreamThread aThread2 = null;
	protected Process aReadProcess = null;
	protected Process aWriteProcess = null;
	protected ProcessParam aReadParam = null;
	protected String aReadFileName = null;
	protected ProcessParam aWriteParam = null;
	protected String aWriteFileName = null;

	/**
	 * @param log
	 * @param progress
	 */
	public ProcessRunner(Log log, Progress progress) {
		super(log, progress);
	}

	/**
	 * Close reader threads and wait for processes to end.
	 */
	public void close() {
		boolean b1 = false;
		boolean b2 = false;

		try {
			if (aThread1 != null) {
				aThread1.close();
				if (aThread1.hasFailed()) {
					b1 = true;
				}
			}
		} catch (Exception e) {
			addLog(1, e.toString());
		}

		try {
			if (aThread2 != null) {
				aThread2.close();
				if (aThread2.hasFailed()) {
					b2 = true;
				}
			}
		} catch (Exception e) {
			addLog(1, e.toString());
		}

		if (aReadProcess != null) {
			try {
				aReadProcess.getInputStream().close();
			} catch (Exception e) {
				addLog(1, e.toString());
			}

		}

		if (aWriteProcess != null) {
			try {
				aWriteProcess.getOutputStream().close();
			} catch (Exception e) {
				addLog(1, e.toString());
			}
		}

		if (aReadProcess != null) {
			try {
				Thread.sleep(1000);
			} catch (Exception e) {
				addLog(1, e.toString());
			}
		}

		if (aReadProcess != null) {
			try {
				// aReadProcess.waitFor();
				aReadProcess.destroy();
				aExitReadCode = aReadProcess.exitValue();
			} catch (Exception e) {
				addLog(1, e.toString());
				aExitReadCode = -1;
			}

		}

		if (aWriteProcess != null) {
			try {
				aWriteProcess.waitFor();
				aExitWriteCode = aWriteProcess.exitValue();
				aWriteProcess.destroy();
			} catch (Exception e) {
				addLog(1, e.toString());
				aExitWriteCode = -1;
			}
		}

		// ! Ugly fix for some bad file descriptor exceptions in StreamThread
		// under suse 10.0 and java 1.5.05
		if ((b1 || b2) && (aExitReadCode > 0 || aExitWriteCode > 0)) {
			setFailed(true);
		}
	}

	/**
	 * Return exit code of the reading process.
	 * 
	 * @return Exit code from read process
	 */
	public int getExitReadCode() {
		return aExitReadCode;
	}

	/**
	 * Get the exit code of the writing process.
	 * 
	 * @return Exit code from write process
	 */
	public int getExitWriteCode() {
		return aExitWriteCode;
	}

	/**
	 * Return status of exit codes.
	 * 
	 * @return true if it has failed
	 */
	public boolean hasFailed() {
		return aExitReadCode != 0 || aExitWriteCode != 0 || super.hasFailed();
	}

	/**
	 * Set read/write stream for child threads. A stream can be from/to an
	 * process or a file.
	 * 
	 * @param thread
	 *            Read/Write thread
	 */
	private void setStreams(StreamThread thread) throws Exception {
		if (thread == null) {
			return;
		} else {
			addLog(2,
					"ProcessRunner::setStreams()  thread.type="
							+ thread.getType() + "  thread.write="
							+ thread.isWriting() + "  aReadProcess="
							+ aReadProcess + "  aReadFileName=" + aReadFileName);

			if (aReadProcess != null) {
				if (thread.getType() == StreamThread.ReadType.READ_STDIN_BYTES) {
					thread.setByteReader(new BufferedInputStream(aReadProcess
							.getInputStream()));
				} else if (thread.getType() == StreamThread.ReadType.READ_STDERR_BYTES) {
					thread.setByteReader(new BufferedInputStream(aReadProcess
							.getErrorStream()));
				} else if (thread.getType() == StreamThread.ReadType.READ_STDIN_LINES) {
					thread.setLineReader(new BufferedReader(
							new InputStreamReader(aReadProcess.getInputStream())));
				} else if (thread.getType() == StreamThread.ReadType.READ_STDERR_LINES) {
					thread.setLineReader(new BufferedReader(
							new InputStreamReader(aReadProcess.getErrorStream())));
				} else {
					assert false;
				}
			} else if (aReadFileName != null) {
				if (thread.getType() == StreamThread.ReadType.READ_FILE_BYTES) {
					thread.setByteReader(new BufferedInputStream(
							new FileInputStream(aReadFileName)));
				} else if (thread.getType() == StreamThread.ReadType.READ_FILE_LINES) {
					thread.setLineReader(new BufferedReader(
							new InputStreamReader(new FileInputStream(
									aReadFileName))));
				} else {
					assert false;
				}
			} else {
				assert false;
			}

			if (thread.isWriting()) {
				if (aWriteProcess != null) {
					thread.setByteWriter(new BufferedOutputStream(aWriteProcess
							.getOutputStream()));
				} else if (aWriteFileName != null) {
					thread.setByteWriter(new BufferedOutputStream(
							new FileOutputStream(aWriteFileName)));
				} else {
					assert false;
				}
			}
		}
	}

	/**
	 * Run all processes until they are done.
	 */
	public void run() {
		setFailed(false);

		try {
			if (aReadParam != null) {
				addLog(1, "ReadParam: " + aReadParam);
				aReadProcess = Runtime.getRuntime().exec(aReadParam.get());
			}

			if (aWriteParam != null) {
				addLog(1, "WriteParam: " + aWriteParam);
				aWriteProcess = Runtime.getRuntime().exec(aWriteParam.get());
			}
		} catch (Exception e) {
			System.out.println(e);
			System.out.flush();
			setFailed(true);
			addLog(1, e.getMessage());
			return;
		}

		try {
			setStreams(aThread1);
			setStreams(aThread2);

			if (aThread1 != null) {
				aThread1.start();
			}

			if (aThread2 != null) {
				aThread2.start();
			}

			aRunning = true;
			while (aRunning) {
				if (aThread1 != null && aThread2 != null) {
					if (!aThread1.isAlive() && !aThread2.isAlive()) {
						aRunning = false;
					}
				} else if (aThread1 != null && aThread2 == null) {
					if (!aThread1.isAlive()) {
						aRunning = false;
					}
				} else if (aThread1 == null && aThread2 != null) {
					if (!aThread2.isAlive()) {
						aRunning = false;
					}
				}

				try {
					Thread.sleep(50);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		} catch (Exception e) {
			setFailed(true);
			addLog(1, new StringBuffer().append("ProcessRunner::run()=")
					.append(e.getMessage()).toString());
		} finally {
			close();
			addLog(1, "Exit code read process=" + aExitReadCode);
			addLog(1, "Exit code write process=" + aExitWriteCode);
			addLog(1, "Has failed=" + hasFailed());
		}
	}

	/**
	 * Tell this thread and all child threads to stop logging.
	 */
	public void stopLogging() {
		if (aThread1 != null) {
			aThread1.stopLogging();
		}
		if (aThread2 != null) {
			aThread2.stopLogging();
		}
		super.stopLogging();
	}
}
