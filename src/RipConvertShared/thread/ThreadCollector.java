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

package RipConvertShared.thread;

import java.util.ArrayList;
import java.util.Vector;

/**
 * Run ThradDump in background and dump.
 */
public class ThreadCollector extends Thread {
	public boolean IS_RUNNING = true;
	private final ArrayList<BaseThread> aThread = new ArrayList<BaseThread>();

	/**
     *
     */
	public ThreadCollector() {
	}

	/**
	 * Add thread to collector.
	 * 
	 * @param thread
	 */
	public void add(BaseThread thread) {
		thread.stopLogging();
		synchronized (aThread) {
			aThread.add(thread);
		}
	}

	/**
	 * Add threads to collector.
	 * 
	 * @param threads
	 */
	public void add(Vector<BaseThread> threads) {
		for (BaseThread thread : threads) {
			if (thread != null) {
				if (thread.isAlive()) {
					thread.stopLogging();
					add(thread);
				}
			}
		}
	}

	/**
	 * Run thread and close all existing threads.
	 */
	public void run() {
		BaseThread thread;

		while (IS_RUNNING) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
			}

			synchronized (aThread) {
				if (aThread.size() > 0) {
					thread = aThread.remove(0);
				} else {
					thread = null;
				}
			}

			if (thread != null) {
				thread.close();
			}
		}
	}
}
