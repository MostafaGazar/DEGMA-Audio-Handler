package Util;

/**
 * Data structure for the time format (hours : minutes : seconds)
 * 
 * @author Mostafa Mohamed El-Sayed - lordms12@gmail.com <br>
 *         <b>Created at:</b> Feb 15, 2008
 */
public class Time {
	int hours;
	int minutes;
	int seconds;

	public Time(int hours, int minutes, int seconds) {
		this.hours = hours;
		this.minutes = minutes;
		this.seconds = seconds;
	}

	public long toMilliSeconds() {
		long milliSeconds;
		milliSeconds = (seconds + minutes * 60 + hours * 60 * 60) * 1000;

		return milliSeconds;
	}
}
