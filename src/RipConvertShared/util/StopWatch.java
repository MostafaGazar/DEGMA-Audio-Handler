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

import java.util.Calendar;

/**
 * Stop watch object.
 */
public class StopWatch {
	private long aStartTime = 0;
	private long aStopTime = 0;

	public long getStartTime() {
		return aStartTime;
	}

	/**
	 * @return
	 */
	public long getStopTime() {
		return aStopTime;
	}

	/**
     *
     */
	public void start() {
		aStartTime = Calendar.getInstance().getTimeInMillis();
	}

	/**
     *
     */
	public void stop() {
		aStopTime = Calendar.getInstance().getTimeInMillis();
	}

	/**
	 * @return
	 */
	public double getDiffTime() {
		return (aStopTime - aStartTime) / 1000d;
	}

	/**
	 * @param start
	 * @param stop
	 * @return
	 */
	public String toString() {
		long start = aStartTime / 1000;
		long stop = aStopTime / 1000;
		long min = (stop - start) / 60;
		long sec = (stop - start) - (min * 60);

		return String.format("%02d:%02d", min, sec);
	}
}
