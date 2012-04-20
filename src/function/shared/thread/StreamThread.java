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

package function.shared.thread;


import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;

import function.shared.util.Log;
import function.shared.util.Progress;


/**
 * Stream reader thread. For reading streams from process streams. A reader can
 * also write data to another process.
 */
public class StreamThread extends BaseThread {
	private static final int BUFFER_SIZE = 1024 * 128;

	public enum ReadType {
		UNDEFINED, READ_STDIN_BYTES, READ_STDIN_LINES, READ_STDERR_BYTES, READ_STDERR_LINES, READ_FILE_BYTES, READ_FILE_LINES,
	}

	public ReadType aType = ReadType.UNDEFINED;
	public boolean aWrite = false;

	private int aMilli = 0;
	private int aCount = 0;
	protected BufferedOutputStream aByteWriter = null;
	protected BufferedInputStream aByteReader = null;
	protected BufferedReader aLineReader = null;

	/**
	 * @param log
	 * @param readType
	 * @param write
	 */
	public StreamThread(Log log, Progress progress, ReadType readType,
			boolean write) {
		super(log, progress);
		aType = readType;
		aWrite = write;
		// addLog(2, toString());
	}

	/**
	 * Close streams.
	 */
	@Override
	public void close() {
		super.close();
		try {
			if (aByteWriter != null) {
				aByteWriter.close();
			}

			if (aByteReader != null) {
				aByteReader.close();
			}

			if (aLineReader != null) {
				aLineReader.close();
			}
		} catch (Exception e) {
			addLog(2, new StringBuffer().append("StreamThread::close() ")
					.append(e.getMessage()).toString());
		}
	}

	/**
	 * Default action is to send bytes to process writer.
	 * 
	 * @param bytes
	 * @param size
	 * @throws Exception
	 */
	public void data(byte[] bytes, int size) throws Exception {
		if (aWrite) {
			write(bytes, size);
		}
	}

	/**
	 * Default action is to send bytes to process writer.
	 * 
	 * @param line
	 * @throws Exception
	 */
	public void data(String line) throws Exception {
		byte[] bytes = line.getBytes();
		write(bytes, bytes.length);
	}

	/**
	 * @return Get type of process thread
	 */
	public ReadType getType() {
		return aType;
	}

	/**
	 * @return true if read stream will write to output stream.
	 */
	public boolean isWriting() {
		return aWrite;
	}

	/**
	 * Run stream reader thread.
	 */
	@Override
	public void run() {
		aRunning = true;
		if (aByteReader != null) {
			byte[] bytes = new byte[BUFFER_SIZE];
			int count = 0;

			while (aRunning) {
				try {
					count = aByteReader.read(bytes, 0, BUFFER_SIZE);
					if (count == -1) {
						aRunning = false;
					} else if (count > 0) {
						aCount += count;
						data(bytes, count);
					}
				} catch (Exception e) {
					setFailed(true);
					close();
					addLog(1, "StreamThread::run() " + e.getMessage()
							+ " insputstream type=" + aType + "  class="
							+ e.getClass().getName());
					break;
				}
			}
		} else if (aLineReader != null) {
			String tmp = null;
			while (aRunning) {
				try {
					tmp = aLineReader.readLine();
					if (tmp == null) {
						aRunning = false;
					} else if (tmp.length() > 0) {
						aCount += tmp.length();
						data(tmp);
					}
				} catch (Exception e) {
					setFailed(true);
					close();
					addLog(1, "StreamThread::run() " + e.getMessage()
							+ " reader type=" + aType + "  class="
							+ e.getClass().getName());
					aRunning = false;
				}
			}
		} else {
			assert false;
		}
	}

	/**
	 * @param stream
	 */
	public void setByteReader(BufferedInputStream stream) {
		aByteReader = stream;
	}

	/**
	 * @param stream
	 */
	public void setByteWriter(BufferedOutputStream stream) {
		aByteWriter = stream;
	}

	/**
	 * @param reader
	 */
	public void setLineReader(BufferedReader reader) {
		aLineReader = reader;
	}

	/**
	 * Set sleep time in milliseconds between every buffer write.
	 * 
	 * @param milli
	 */
	public void setSleep(int milli) {
		aMilli = milli;
	}

	/**
	 * Write bytes to output process.
	 * 
	 * @param bytes
	 * @param size
	 * @throws Exception
	 */
	public void write(byte[] bytes, int size) throws Exception {
		if (aByteWriter != null) {
			if (aMilli > 0) {
				try {
					Thread.sleep(aMilli);
				} catch (InterruptedException e) {
				}
			}
			aByteWriter.write(bytes, 0, size);
			aByteWriter.flush();

			if (aProgress != null) {
				aProgress.setMinorProgress(aCount);
			}
		}
	}
}
