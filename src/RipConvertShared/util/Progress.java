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

import java.util.Vector;

/**
 * A progress object that can use one or two progress values and 1 to many work
 * tasks.
 */
public class Progress {
	private static Progress aaProgress = null;
	private Vector<String> aMinorMessageQue = new Vector<String>();
	private Vector<String> aMajorMessageQue = new Vector<String>();
	private Vector<Long> aMinorMaxValueQue = new Vector<Long>();
	private Integer aMinorValue = 0;
	private int aQueIndex = 0;
	private double aTotalValue = 0;
	private double aTotalRunningValue = 0;
	public boolean aIncreaseMode = false;
	public int aUglyFugly = 0;

	/**
     *
     */
	public Progress() {
	}

	/**
	 * Append new single progress task with max value as 100
	 * 
	 * @param minorMessage
	 */
	public void appendTask(String minorMessage) {
		aMinorMaxValueQue.add(100l);
		aTotalValue += 100;
		aMinorMessageQue.add(minorMessage);
		aMajorMessageQue.add("");
	}

	/**
	 * Append new single progress task
	 * 
	 * @param minorMaxValue
	 * @param minorMessage
	 */
	public void appendTask(long minorMaxValue, String minorMessage) {
		aMinorMaxValueQue.add(minorMaxValue);
		aTotalValue += minorMaxValue;
		aMinorMessageQue.add(minorMessage);
		aMajorMessageQue.add("");
	}

	/**
	 * Append progress task with max value as 100
	 * 
	 * @param minorMessage
	 * @param majorMessage
	 */
	public void appendTask(String minorMessage, String majorMessage) {
		aMinorMaxValueQue.add(100l);
		aTotalValue += 100;
		aMinorMessageQue.add(minorMessage);
		aMajorMessageQue.add(majorMessage);
	}

	/**
	 * Append progress task
	 * 
	 * @param minorMaxValue
	 * @param minorMessage
	 * @param majorMessage
	 */
	public void appendTask(long minorMaxValue, String minorMessage,
			String majorMessage) {
		aMinorMaxValueQue.add(minorMaxValue);
		aTotalValue += minorMaxValue;
		aMinorMessageQue.add(minorMessage);
		aMajorMessageQue.add(majorMessage);
	}

	/**
	 * Clear values. And set max to 100.
	 */
	public void clear() {
		aIncreaseMode = false;
		aUglyFugly = 0;
		aMinorValue = 0;
		aQueIndex = 0;
		aTotalValue = 0;
		aTotalRunningValue = 0;
		aMinorMessageQue.clear();
		aMajorMessageQue.clear();
		aMinorMaxValueQue.clear();
	}

	/**
	 * Get Progress object.
	 * 
	 * @return Get global progress object
	 */
	public static Progress get() {
		if (aaProgress == null) {
			aaProgress = new Progress();
		}
		return aaProgress;
	}

	/**
	 * Return work message.
	 * 
	 * @return Major work message
	 */
	public String getMajorMessage() {
		assert aMajorMessageQue.size() > aQueIndex;
		return aMajorMessageQue.elementAt(aQueIndex);
	}

	/**
	 * Return work message.
	 * 
	 * @return Minor work message
	 */
	public String getMinorMessage() {
		assert aMinorMessageQue.size() > aQueIndex;
		return aMinorMessageQue.elementAt(aQueIndex);
	}

	/**
	 * Get major progress value
	 * 
	 * @return Total progress as a value between 0 and 100
	 */
	public int getMajorProgress() {
		if (aTotalValue > 0) {
			return (int) (((aMinorValue + aTotalRunningValue) / aTotalValue) * 100d);
		} else {
			return aMinorValue;
		}
	}

	/**
	 * Get minor progress value
	 * 
	 * @return Minor progress value
	 */
	public int getMinorProgress() {
		assert aMinorMaxValueQue.size() > aQueIndex;
		Double d = (aMinorValue.doubleValue() / aMinorMaxValueQue.elementAt(
				aQueIndex).doubleValue()) * 100d;
		return d.intValue();
	}

	/**
	 * Go to next work task
	 */
	public void nextWorkTask() {
		assert aMinorMaxValueQue.size() > aQueIndex;
		aTotalRunningValue += aMinorMaxValueQue.get(aQueIndex);
		aQueIndex++;
		aMinorValue = 0;
	}

	/**
	 * Set minor progress value
	 * 
	 * @param value
	 */
	public void setMinorProgress(int value) {
		if (aIncreaseMode) {
			if ((value + aUglyFugly) < aMinorValue) {
				aUglyFugly += 100;
			}

			aMinorValue = value + aUglyFugly;
		} else {
			aMinorValue = value;
		}
	}
}
