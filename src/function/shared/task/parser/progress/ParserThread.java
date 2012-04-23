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
package function.shared.task.parser.progress;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import function.shared.thread.StreamThread;
import function.shared.util.Log;
import function.shared.util.Progress;

/**
 * Base class for parsing data from the decoders.
 */
public class ParserThread extends StreamThread {
	private int aLastPercent = -10;
	private boolean aToStdout = false;
	protected Pattern aPattern = null;
	protected int aMatchCount = 0;

	/**
	 * @param toStdout
	 *            Debug param, if true print some extra debug message
	 */
	public ParserThread(boolean toStdout) {
		super(Log.get(), Progress.get(), ReadType.READ_STDERR_LINES, false);
		aToStdout = toStdout;
	}

	/**
	 * Parse progress output from various programs.
	 * 
	 * @param line
	 *            Text string from decoder
	 */
	@Override
	public void data(String line) {
		Log.get().print(3, "ParserThread: <" + line);
		Matcher matcher;

		matcher = aPattern.matcher(line);
		if (matcher.find()) {
			Log.get().print(
					3,
					"MATCHER=" + matcher.group(1) + "    GROUPCOUNT="
							+ matcher.groupCount());

			try {
				if (matcher.groupCount() == aMatchCount && aMatchCount == 1) {
					int v = Double.valueOf(matcher.group(1)).shortValue();
					if (v >= 0 && v <= 100) {
						Progress.get().setMinorProgress(v);
					}
				} else if (matcher.groupCount() == aMatchCount
						&& aMatchCount == 2) {
					double d1 = Double.valueOf(matcher.group(1));
					double d2 = Double.valueOf(matcher.group(2));
					if (d1 >= 0 && d1 <= d2 && d2 > 0) {
						Progress.get()
								.setMinorProgress((int) ((d1 / d2) * 100));
					}
				}
			} catch (Exception e) {
				Log.get().print(3, e.toString());
			}

			try {
				int v = Progress.get().getMinorProgress();
				if ((v - 10) >= aLastPercent) {
					aLastPercent = v;
					if (aToStdout) {
						Log.get().addTime(
								2,
								String.format("%d - %d%% done %s    %s",
										Progress.get().getMajorProgress(),
										Progress.get().getMinorProgress(),
										Progress.get().getMinorMessage(),
										Progress.get().getMajorMessage()));
						Log.get().print(
								2,
								String.format("%d - %d%% done %s    %s",
										Progress.get().getMajorProgress(),
										Progress.get().getMinorProgress(),
										Progress.get().getMinorMessage(),
										Progress.get().getMajorMessage()));
					}
				}
			} catch (Exception e) {
				Log.get()
						.addTime(1, "ParserThread:Exception " + e.getMessage());
			}
		}
	}

	/**
	 * @return Number of valid matches
	 */
	public int getMatchCount() {
		return aMatchCount;
	}

	/**
	 * @return Pattern object
	 */
	public Pattern getPattern() {
		return aPattern;
	}
}
