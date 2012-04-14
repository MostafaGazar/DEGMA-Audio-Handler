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

package RipConvertShared.util;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * A log object.
 */
public class Log {
	private int aLogLevel = 0;
	private final ArrayList<String> aLog = new ArrayList<String>();
	private int aMaxSize = 0;
	private static Log aaLog = null;
	private BufferedOutputStream aFile = null;

	/**
	 * Create log object.
	 * 
	 * @param maxSize
	 *            - Max number of strings to store
	 */
	public Log(int maxSize) {
		aMaxSize = maxSize;
	}

	/**
	 * Create log object.
	 * 
	 * @param maxSize
	 *            - Max number of strings to store
	 * @param fileName
	 *            - Save also every line to logfile
	 */
	public Log(int maxSize, String fileName) {
		aMaxSize = maxSize;

		try {
			aFile = new BufferedOutputStream(new FileOutputStream(fileName));
		} catch (Exception e) {
			aFile = null;
		}
	}

	/**
	 * Add log message.
	 * 
	 * @param logLevel
	 * @param message
	 * @param add_time
	 */
	private void add(int logLevel, String message, boolean add_time) {
		if (logLevel <= aLogLevel) {
			String s = "";

			if (add_time) {
				GregorianCalendar cal = new GregorianCalendar();
				s = String.format("%02d:%02d:%02d %s",
						cal.get(Calendar.HOUR_OF_DAY),
						cal.get(Calendar.MINUTE), cal.get(Calendar.SECOND),
						message);
			} else {
				s = message;
			}

			synchronized (aLog) {
				if (aFile != null) {
					try {
						aFile.write(s.getBytes());
						aFile.write("\n".getBytes());
						aFile.flush();
					} catch (Exception e) {
					}
				}
				aLog.add(s);
				if (aLog.size() > aMaxSize) {
					aLog.remove(0);
				}
			}
		}
	}

	/**
	 * Add log message.
	 * 
	 * @param logLevel
	 * @param message
	 */
	public void add(int logLevel, String message) {
		add(logLevel, message, false);
	}

	/**
	 * Add log message with a time stamp first.
	 * 
	 * @param logLevel
	 * @param message
	 */
	public void addTime(int logLevel, String message) {
		add(logLevel, message, true);
	}

	/**
	 * Clear all log messages.
	 */
	public void clear() {
		aLog.clear();
	}

	/**
	 * Get global log. If it doesn't exist create it. Maximum rows is 1000.
	 * 
	 * @return
	 */
	public static Log get() {
		if (aaLog == null) {
			aaLog = new Log(1000);
		}
		return aaLog;
	}

	/**
	 * Get global log. If it doesn't exist create it. Maximum rows is 1000.
	 * 
	 * @return
	 */
	public static Log get(String fileName) {
		if (aaLog == null) {
			aaLog = new Log(1000, fileName);
		}
		return aaLog;
	}

	/**
	 * Add all log rows in one string with newlines between every row.
	 * 
	 * @return
	 */
	public String getLogMessage() {
		String message = "";

		for (String s : aLog) {
			message += s + "\n";
		}
		return message;
	}

	/**
	 * Set current log level s
	 * 
	 * @param level
	 */
	public void setLogLevel(int level) {
		aLogLevel = level;
	}

	/**
	 * Print message to screen.
	 * 
	 * @param verbose
	 * @param message
	 */
	public void print(int verbose, String message) {
		if (verbose <= aLogLevel) {

			GregorianCalendar cal = new GregorianCalendar();
			System.out.println(String.format("%02d:%02d:%02d %s",
					cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE),
					cal.get(Calendar.SECOND), message));
			System.out.flush();
		}
	}

	/**
	 * Save log to file.
	 * 
	 * @param fileName
	 */
	public void save(String fileName) {
		try {
			BufferedOutputStream out = new BufferedOutputStream(
					new FileOutputStream(fileName));

			for (String line : aLog) {
				out.write(line.getBytes());
				out.write("\n".getBytes());
			}

			out.close();
		} catch (IOException e) {
		}
	}

}
