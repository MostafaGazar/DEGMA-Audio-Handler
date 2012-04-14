package interfaces;

import Util.Time;

/**
 * @author Mostafa Mohamed El-Sayed - lordms12@gmail.com <br>
 *         <b>Created at:</b> Feb 15, 2008
 */
public interface PLayCutIF {

	/**
	 * Add a new period user want to the out file
	 * 
	 * @param startTime
	 *            the start time of the period user want to cut (hours : minutes
	 *            : seconds)
	 * @param endTime
	 *            the end time of the period user want to cut (hours : minutes :
	 *            seconds)
	 */
	public void addToCuttingList(Time startTime, Time endTime);

	/**
	 * Delete a period from the list of periods to be cut
	 * 
	 * @param index
	 *            the period index(in the order of entering it)
	 */
	public void deleteFromCuttingList(int index);

	/**
	 * Start cutting the input file into one pieces(the output file)
	 * 
	 * @return null: if there is no error, not null: error message(there is an
	 *         error in the cutting process)
	 */
	public String doCut();

	/**
	 * Set the input path of the input file <br>
	 * <code>= (</code>file:/ <code>+ input path)</code>
	 * 
	 * @param inputPath
	 *            the full input path of the input file
	 */
	public void setInputPath(String inputPath);

	/**
	 * Set the output path of the output file <br>
	 * <code>= (</code>file:/ <code>+ output path)</code>
	 * 
	 * @param outputPath
	 *            the full output path of the output file
	 */
	public void setOutputPath(String outputPath);

	/**
	 * Set the frame mode to true means start and end points are specified in
	 * frames but setting frame mode to false means that start and end points
	 * are real times in seconds
	 * 
	 * @param frameMode
	 *            true: start and end points are specified in frames
	 */
	public void setFrameMode(boolean frameMode);
}
