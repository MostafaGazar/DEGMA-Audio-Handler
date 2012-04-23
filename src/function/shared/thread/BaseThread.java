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

import function.shared.util.Log;
import function.shared.util.Progress;

/**
 * Base class for worker threads.
 */
public class BaseThread extends Thread {
	private Log aLog = null;
	protected Progress aProgress = null;
	protected boolean aRunning = true;
	private boolean aFailed = false;

	/**
	 * Create thread and set log/progress object. Can be null.
	 */
	public BaseThread(Log log, Progress progress) {
		super();
		aLog = log;
		aProgress = progress;
	}

	/**
	 * Add log message.
	 */
	public void addLog(int verbose, String message) {
		if (aLog != null) {
			aLog.addTime(verbose, message);
		}
	}

	/**
	 * isRunning is returning false after this call.
	 */
	public void close() {
		aRunning = false;
	}

	/**
	 * Get log object.
	 * 
	 * @return Log object
	 */
	public Log getLog() {
		return aLog;
	}

	/**
	 * Has thread failed during running?
	 * 
	 * @return true if something failed
	 */
	public boolean hasFailed() {
		return aFailed;
	}

	/**
	 * Set failed status.
	 */
	public void setFailed(boolean failed) {
		aFailed = failed;
	}

	/**
	 * Set log object.
	 */
	public void setLog(Log log) {
		aLog = log;
	}

	/**
	 * Stop adding messages to the log.
	 */
	public void stopLogging() {
		if (aLog != null) {
			synchronized (aLog) {
				aLog = null;
			}
		}
	}

	/**
	 * Tell thread that it should break the loop.
	 */
	public void stopRunning() {
		aRunning = false;
	}

}
