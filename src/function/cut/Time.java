package function.cut;

/**
 * Data structure for time (hours : minutes : seconds).
 * @author Mostafa Gazar, eng.mostafa.gazar@gmail.com
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
