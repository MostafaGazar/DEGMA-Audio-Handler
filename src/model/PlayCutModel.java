package model;

import interfaces.PLayCutIF;

import java.util.Vector;

import javax.media.MediaLocator;

import Util.Time;

/**
 * @author Mostafa Mohamed El-Sayed - lordms12@gmail.com <br>
 *         <b>Created at:</b> Feb 15, 2008
 */
public class PlayCutModel implements PLayCutIF {

	/** inputURL the input file path */
	String inputURL = null;
	/** inputMediaLocator generate the input media locators */
	MediaLocator inputMediaLocator;

	/** outputURL the output file path */
	String outputURL = null;
	/** outputMediaLocator generate the output media locators */
	MediaLocator outputMediaLocator;

	/** startV contains the start time in <b>seconds</b> of all periods */
	Vector<Long> startV = new Vector<Long>();
	/** endV contains the end time in <b>seconds</b> of all periods */
	Vector<Long> endV = new Vector<Long>();

	/** frameMode true: start and end points are specified in frames */
	boolean frameMode = false;

	@Override
	public void addToCuttingList(Time startTime, Time endTime) {
		startV.addElement(startTime.toMilliSeconds());
		endV.addElement(endTime.toMilliSeconds());
	}

	@Override
	public void deleteFromCuttingList(int index) {
		startV.remove(index);
		endV.remove(index);
	}

	@Override
	public String doCut() {

		return null;
	}

	@Override
	public void setInputPath(String inputPath) {
		inputURL = inputPath;
		// inputURL = "file:/"+inputPath;
	}

	@Override
	public void setOutputPath(String outputPath) {
		outputURL = outputPath;
		// outputURL = "file:/"+outputPath;
	}

	@Override
	public void setFrameMode(boolean frameMode) {

	}

}
