package controller.JAVAconvert.MIDI_To__;

/*
 * Copyright (c) 1999 by Matthias Pfisterer
 * Copyright (c) 2003 by Florian Bomers
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 * - Redistributions of source code must retain the above copyright notice,
 *   this list of conditions and the following disclaimer.
 * - Redistributions in binary form must reproduce the above copyright
 *   notice, this list of conditions and the following disclaimer in the
 *   documentation and/or other materials provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT
 * LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS
 * FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE
 * COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT,
 * INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION)
 * HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT,
 * STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED
 * OF THE POSSIBILITY OF SUCH DAMAGE.
 */

/*
 |<---            this code is formatted to fit into 80 columns             --->|
 */

import java.io.File;
import java.io.IOException;

import javax.sound.midi.Sequence;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiFileFormat;
import javax.sound.midi.Track;
import javax.sound.midi.InvalidMidiDataException;

/*
 Purpose: 
 This program can convert MIDI type 1 files to MIDI type 0 files. It has two modes: single mode and multi mode.In single mode (default), all events of all tracks are assembled in one track which is saved as a single file.In multi mode (selected by -m), each track of the input file is separated and saved to the output file. In single mode, the input file will be overwritten if no output file name is provided. In multi mode, the track number is appended to the basename of the input file (i.e. it is inserted before the extension).

 Usage: 
 java MidiConverter [ -m ] input_midifile output_midifile <<<<<<<<<<<

 Parameters:
 -m              <<<<< selects multi mode
 input_midifile  <<<<< the name of the MIDI file to process
 output_midifile <<<<< the name of the output MIDI file (optional)
 */
/**
 * <titleabbrev>MidiConverter</titleabbrev> <title>Converting MIDI type 1 files
 * to MIDI type 0 files</title>
 * 
 * <formalpara><title>Purpose</title> <para>This program can convert MIDI type 1
 * files to MIDI type 0 files. It has two modes: single mode and multi
 * mode.</para>
 * 
 * <para>In single mode (default), all events of all tracks are assembled in one
 * track which is saved as a single file.</para>
 * 
 * <para>In multi mode (selected by <option>-m</option>), each track of the
 * input file is separated and saved to the output file. In single mode, the
 * input file will be overwritten if no output file name is provided. In multi
 * mode, the track number is appended to the basename of the input file (i.e. it
 * is inserted before the extension).</para> </formalpara>
 * 
 * <formalpara><title>Usage</title> <para> <cmdsynopsis><command>java
 * MidiConverter</command> <arg><option>-m</option></arg> <arg
 * choice="plain"><replaceable
 * class="parameter">input_midifile</replaceable></arg> <arg
 * choice="plain"><replaceable
 * class="parameter">output_midifile</replaceable></arg> </cmdsynopsis>
 * </para></formalpara>
 * 
 * <formalpara><title>Parameters</title> <variablelist> <varlistentry>
 * <term><option>-m</option></term> <listitem><para>selects multi
 * mode</para></listitem> </varlistentry> <varlistentry> <term><replaceable
 * class="parameter">input_midifile</replaceable></term> <listitem><para>the
 * name of the MIDI file to process</para></listitem> </varlistentry>
 * <varlistentry> <term><replaceable
 * class="parameter">output_midifile</replaceable></term> <listitem><para>the
 * name of the output MIDI file (optional)</para></listitem> </varlistentry>
 * </variablelist> </formalpara>
 * 
 * <formalpara><title>Bugs, limitations</title> <para>Not well tested.</para>
 * </formalpara>
 * 
 * <formalpara><title>Source code</title> <para> <ulink
 * url="MidiConverter.java.html">MidiConverter.java</ulink> </para>
 * </formalpara>
 */
// TO DO: add an option '-T x' to transpose (be sure not to modifiy channel 10!)
public class MidiConverterType_1ToType_0 {
	public static void main(String[] args) {
		if (args.length < 1 || args.length > 3 || args[0].equals("-h")) {
			printUsageAndExit();
		}
		/*
		 * This variable says whether we should process in multi mode (each
		 * track is saved in an own file) or in single mode (all tracks are
		 * united to one track and saved in a single file).
		 */
		boolean bUseMultiMode = false;
		int inFilenameIndex = 0;
		if (args[0].equals("-m")) {
			bUseMultiMode = true;
			inFilenameIndex++;
			if (args.length <= inFilenameIndex) {
				out("You need to specify an input file name!");
				printUsageAndExit();
			}
		}

		String strInFilename = args[inFilenameIndex];
		File inFile = new File(strInFilename);
		if (!inFile.exists()) {
			out("The input file '" + strInFilename + "' does not exist!");
			printUsageAndExit();
		}

		String strOutFilename = strInFilename;
		if (args.length > (inFilenameIndex + 1)) {
			strOutFilename = args[inFilenameIndex + 1];
		}

		/* verify that the input file is indeed type 1 */
		MidiFileFormat mff = null;
		try {
			mff = MidiSystem.getMidiFileFormat(inFile);
		} catch (InvalidMidiDataException e) {
			e.printStackTrace();
			System.exit(1);
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(1);
		}
		if (mff.getType() == 0) {
			out("The input file you specified is already a type 0 MIDI file.");
			System.exit(1);
		}

		Sequence sequence = null;
		try {
			sequence = MidiSystem.getSequence(inFile);
		} catch (InvalidMidiDataException e) {
			e.printStackTrace();
			System.exit(1);
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(1);
		}
		Track[] aTracks = sequence.getTracks();

		if (aTracks.length == 0) {
			out("The input file you specified does not contain any tracks! exit.");
			System.exit(1);
		}

		float fDivisionType = sequence.getDivisionType();
		int nResolution = sequence.getResolution();
		if (bUseMultiMode) {
			// for each input track, create a new sequence and copy all
			// events of the input track to a new track in the new sequence
			// then save the new sequence as a distinct file
			for (int nTrack = 0; nTrack < aTracks.length; nTrack++) {
				Sequence singleTrackSequence = null;
				try {
					singleTrackSequence = new Sequence(fDivisionType,
							nResolution);
				} catch (InvalidMidiDataException e) {
					e.printStackTrace();
					System.exit(1);
				}
				Track track = singleTrackSequence.createTrack();
				for (int i = 0; i < aTracks[nTrack].size(); i++) {
					track.add(aTracks[nTrack].get(i));
				}
				int nDotPosition = strOutFilename.lastIndexOf('.');
				String strSingleTrackFilename = null;
				if (nDotPosition == -1) {
					strSingleTrackFilename = strOutFilename.substring(0,
							nDotPosition) + "-" + nTrack;
				} else {
					strSingleTrackFilename = strOutFilename.substring(0,
							nDotPosition)
							+ "-"
							+ nTrack
							+ strOutFilename.substring(nDotPosition);
				}

				try {
					int written = MidiSystem.write(singleTrackSequence, 0,
							new File(strSingleTrackFilename));
					out("Wrote " + strSingleTrackFilename + " (" + written
							+ " bytes).");
				} catch (IOException e) {
					e.printStackTrace();
					System.exit(1);
				}
			}
		} else // single mode
		{
			Track firstTrack = aTracks[0];
			int nTrack;

			for (nTrack = 1; nTrack < aTracks.length; nTrack++) {
				Track track = aTracks[nTrack];

				// add all events of this track to the first track
				for (int i = 0; i < track.size(); i++) {
					firstTrack.add(track.get(i));
				}

				// delete this track from the sequence
				sequence.deleteTrack(track);
			}

			// write out the new sequence
			try {
				int written = MidiSystem.write(sequence, 0, new File(
						strOutFilename));
				out("Wrote " + strOutFilename + " successfully (" + written
						+ " bytes).");
			} catch (IOException e) {
				e.printStackTrace();
				System.exit(1);
			}
		}

	}

	private static void printUsageAndExit() {
		out("usage:");
		out("java MidiConverter  [-m]  <midifile> [outputfile]");
		System.exit(1);
	}

	private static void out(String strMessage) {
		System.out.println(strMessage);
	}
}

/*** MidiConverter.java ***/

