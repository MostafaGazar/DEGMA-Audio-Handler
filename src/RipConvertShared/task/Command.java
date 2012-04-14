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

package RipConvertShared.task;

import RipConvertShared.music.Album;
import RipConvertShared.music.Track;
import RipConvertShared.thread.process.ProcessRunner;
import RipConvertShared.thread.process.ReadFileWriteFile;
import RipConvertShared.thread.process.ReadFileWriteProc;
import RipConvertShared.thread.process.ReadProc;
import RipConvertShared.thread.process.ReadProcWriteProc;
import RipConvertShared.task.parser.progress.*;
import RipConvertShared.thread.BaseThread;
import RipConvertShared.thread.StreamThread;
import RipConvertShared.util.Log;
import RipConvertShared.util.Pref;
import RipConvertShared.util.ProcessParam;
import RipConvertShared.util.Progress;
import Util.Constants;

import java.io.File;
import java.util.Vector;

/**
 * Build shell commands to execute console applications in threads.
 */
public class Command {
	public static boolean DEBUG = false;

	/**
	 * Only static methods.
	 */
	private Command() {
	}

	/**
	 * Encode streams from cd to wav/mp3/ogg/flac/aac files.
	 * 
	 * @param album
	 *            Album object
	 * @param track
	 *            Track object
	 * @param threads
	 *            Thread arrray which will conain this encoder/decoder task
	 * @param outFileName
	 *            Name of encoded track
	 * @param wholecd
	 *            true if whole cd is to be ripped into one track
	 * @throws Exception
	 *             Throws exception if some error occured
	 */
	public static void createEncoderForCD(Album album, Track track,
			Vector<BaseThread> threads, String outFileName, boolean wholecd)
			throws Exception {
		ProcessRunner processRunner = null;
		ParserThread parserThread = new ParseCD(Command.DEBUG);

		switch (track.getEncoder()) {
		case Constants.MP3_TRACK:
			processRunner = new ReadProcWriteProc(Log.get(), null,
					Command.getCDDecoder(track.aTrack, null, wholecd),
					Command.getMP3Encoder(album, track, outFileName, wholecd),
					new StreamThread(Log.get(), null,
							StreamThread.ReadType.READ_STDIN_BYTES, true),
					parserThread);
			break;

		case Constants.OGG_TRACK:
			processRunner = new ReadProcWriteProc(Log.get(), null,
					Command.getCDDecoder(track.aTrack, null, wholecd),
					Command.getOggEncoder(album, track, outFileName, wholecd),
					new StreamThread(Log.get(), null,
							StreamThread.ReadType.READ_STDIN_BYTES, true),
					parserThread);
			break;

		case Constants.M4A_TRACK:
			processRunner = new ReadProcWriteProc(Log.get(), null,
					Command.getCDDecoder(track.aTrack, null, wholecd),
					Command.getAACEncoder(album, track, outFileName, wholecd),
					new StreamThread(Log.get(), null,
							StreamThread.ReadType.READ_STDIN_BYTES, true),
					parserThread);
			break;

		case Constants.FLAC_TRACK:
			processRunner = new ReadProcWriteProc(Log.get(), null,
					Command.getCDDecoder(track.aTrack, null, wholecd),
					Command.getFlacEncoder(album, track, outFileName, wholecd),
					new StreamThread(Log.get(), null,
							StreamThread.ReadType.READ_STDIN_BYTES, true),
					parserThread);
			break;

		case Constants.WAV_TRACK:
			processRunner = new ReadProc(Log.get(), null, Command.getCDDecoder(
					track.aTrack, outFileName, wholecd), parserThread);
			break;

		default:
			assert false;
		}

		File file = new File(outFileName);

		if (wholecd) {
			Progress.get().appendTask(album.aTracks.size() * 100, outFileName);
		} else {
			Progress.get().appendTask(file.getName(), file.getParent());
		}

		if (processRunner.aThread1 != null) {
			try {
				processRunner.aThread1
						.setSleep(Integer.valueOf(Constants.SLEEP_OPTIONS[Pref
								.getPref(Constants.SLEEP_KEY,
										Constants.SLEEP_DEFAULT)]));
			} catch (NumberFormatException e) {
				Log.get().addTime(1, e.getMessage());
				processRunner.aThread1.setSleep(0);
			}
		}
		threads.add(processRunner);
	}

	/**
	 * Encode wav/mp3/ogg/flac/aac files to wav/mp3/ogg/flac/aac.
	 * 
	 * @param album
	 *            Album object
	 * @param track
	 *            Track object
	 * @param threads
	 *            Thread arrray which will conain this encoder/decoder task
	 * @param outFileName
	 *            Name of encoded track
	 * @throws Exception
	 *             Throws exception if some error occured
	 */
	public static void createEncoderForFiles(Album album, Track track,
			Vector<BaseThread> threads, String outFileName) throws Exception {
		ParserThread parserThread = null;
		ProcessRunner processRunner = null;
		StreamThread byteReaderThread = null;
		ProcessParam decoderParam = null;
		ProcessParam decoderParamWav = null;

		switch (track.getDecoder()) {
		case Constants.MP3_TRACK:
			parserThread = new ParseMP3(Command.DEBUG);
			byteReaderThread = new StreamThread(Log.get(), null,
					StreamThread.ReadType.READ_STDIN_BYTES, true);
			decoderParam = Command.getMP3Decoder(track.aFile, null);
			decoderParamWav = Command.getMP3Decoder(track.aFile, outFileName);
			break;

		case Constants.OGG_TRACK:
			parserThread = new ParseOgg(Command.DEBUG);
			byteReaderThread = new StreamThread(Log.get(), null,
					StreamThread.ReadType.READ_STDIN_BYTES, true);
			decoderParam = Command.getOggDecoder(track.aFile, null);
			decoderParamWav = Command.getOggDecoder(track.aFile, outFileName);
			break;

		case Constants.M4A_TRACK:
			parserThread = new ParseM4A(Command.DEBUG);
			byteReaderThread = new StreamThread(Log.get(), null,
					StreamThread.ReadType.READ_STDIN_BYTES, true);
			decoderParam = Command.getAACDecoder(track.aFile, null);
			decoderParamWav = Command.getAACDecoder(track.aFile, outFileName);
			break;

		case Constants.FLAC_TRACK:
			parserThread = new ParseFlac(Command.DEBUG);
			byteReaderThread = new StreamThread(Log.get(), null,
					StreamThread.ReadType.READ_STDIN_BYTES, true);
			decoderParam = Command.getFlacDecoder(track.aFile, null);
			decoderParamWav = Command.getFlacDecoder(track.aFile, outFileName);
			break;

		default:
			assert false;
		}

		switch (track.getEncoder()) {
		case Constants.MP3_TRACK:
			processRunner = new ReadProcWriteProc(Log.get(), null,
					decoderParam, Command.getMP3Encoder(album, track,
							outFileName, false), byteReaderThread, parserThread);
			break;

		case Constants.OGG_TRACK:
			processRunner = new ReadProcWriteProc(Log.get(), null,
					decoderParam, Command.getOggEncoder(album, track,
							outFileName, false), byteReaderThread, parserThread);
			break;

		case Constants.M4A_TRACK:
			processRunner = new ReadProcWriteProc(Log.get(), null,
					decoderParam, Command.getAACEncoder(album, track,
							outFileName, false), byteReaderThread, parserThread);
			break;

		case Constants.FLAC_TRACK:
			processRunner = new ReadProcWriteProc(Log.get(), null,
					decoderParam, Command.getFlacEncoder(album, track,
							outFileName, false), byteReaderThread, parserThread);
			break;

		case Constants.WAV_TRACK:
			processRunner = new ReadProc(Log.get(), null, decoderParamWav,
					parserThread);
			break;

		default:
			assert false;
		}

		File file = new File(outFileName);
		Progress.get().appendTask(file.getName(), file.getParent());
		byteReaderThread.setSleep(Pref.getPref(Constants.SLEEP_KEY, 0));
		threads.add(processRunner);

		try {
			byteReaderThread.setSleep(Integer
					.valueOf(Constants.SLEEP_OPTIONS[Pref.getPref(
							Constants.SLEEP_KEY, Constants.SLEEP_DEFAULT)]));
		} catch (NumberFormatException e) {
			Log.get().addTime(1, e.getMessage());
			byteReaderThread.setSleep(0);
		}
	}

	/**
	 * Encode wav files to mp3/ogg/flac/aac.
	 * 
	 * @param album
	 *            Album object
	 * @param track
	 *            Track object
	 * @param threads
	 *            Thread arrray which will conain this encoder/decoder task
	 * @param outFileName
	 *            Name of encoded track
	 * @throws Exception
	 *             Throws exception if some error occured
	 */
	public static void createEncoderForWavFiles(Album album, Track track,
			Vector<BaseThread> threads, String outFileName) throws Exception {
		ProcessRunner processRunner = null;
		StreamThread byteReaderThread = new StreamThread(Log.get(),
				Progress.get(), StreamThread.ReadType.READ_FILE_BYTES, true);

		switch (track.getEncoder()) {
		case Constants.MP3_TRACK:
			processRunner = new ReadFileWriteProc(Log.get(), null, track.aFile,
					Command.getMP3Encoder(album, track, outFileName, false),
					byteReaderThread);
			break;

		case Constants.OGG_TRACK:
			processRunner = new ReadFileWriteProc(Log.get(), null, track.aFile,
					Command.getOggEncoder(album, track, outFileName, false),
					byteReaderThread);
			break;

		case Constants.M4A_TRACK:
			processRunner = new ReadFileWriteProc(Log.get(), null, track.aFile,
					Command.getAACEncoder(album, track, outFileName, false),
					byteReaderThread);
			break;

		case Constants.FLAC_TRACK:
			processRunner = new ReadFileWriteProc(Log.get(), null, track.aFile,
					Command.getFlacEncoder(album, track, outFileName, false),
					byteReaderThread);
			break;

		case Constants.WAV_TRACK:
			processRunner = new ReadFileWriteFile(Log.get(), null, track.aFile,
					outFileName, byteReaderThread);
			break;

		default:
			assert false;
		}

		File file = new File(outFileName);
		Progress.get().appendTask(new File(track.aFile).length(),
				file.getName(), file.getParent());
		try {
			byteReaderThread.setSleep(Integer
					.valueOf(Constants.SLEEP_OPTIONS[Pref.getPref(
							Constants.SLEEP_KEY, Constants.SLEEP_DEFAULT)]));
		} catch (NumberFormatException e) {
			Log.get().addTime(1, e.getMessage());
			byteReaderThread.setSleep(0);
		}
		threads.add(processRunner);
	}

	/**
	 * Get params for decoding aac data.
	 * 
	 * @param inFileName
	 *            Name of source file
	 * @param outFileName
	 *            Name of destination file
	 * @return Command line parameters for reading wav files
	 */
	public static ProcessParam getAACDecoder(String inFileName,
			String outFileName) {
		ProcessParam param = new ProcessParam();

		param.add(Pref.getPref(Constants.AAC_DECODER_KEY,
				Constants.AAC_DECODER_DEFAULT));
		if (outFileName != null) {
			param.add("-o");
			param.add(outFileName);
		} else {
			param.add("-w");
		}
		param.add(inFileName);

		return param;
	}

	/**
	 * Get params for encoding aac data.
	 * 
	 * @param album
	 *            Album object
	 * @param track
	 *            Track object
	 * @param fileName
	 *            Name of destination file
	 * @return Command line parameters for encoding aac
	 * @throws Exception
	 *             Throws exception for some error
	 */
	public static ProcessParam getAACEncoder(Album album, Track track,
			String fileName, boolean useAlbum) throws Exception {
		ProcessParam param = new ProcessParam();
		String encoder;
		String option;

		if (track.getEncoderIndex() < Constants.ENCODER_INDEX_COUNT) {
			encoder = Pref.getPref(Constants.AAC_ENCODER_KEY,
					Constants.AAC_ENCODER_DEFAULT);
			option = Pref.getPref(
					Constants.ENCODER_PARAM_KEYS[track.getEncoderIndex()],
					Constants.ENCODER_PARAM_DEFAULTS[track.getEncoderIndex()]);
		} else {
			throw new Exception("AAC ENCODER OUT OF RANGE, SHOULD NOT HAPPEN");
		}

		param.add(encoder);
		param.addSplitString(option);
		param.addQuote("--artist", album.aArtist);
		param.addQuote("--album", album.aAlbum);
		param.addQuote("--title", track.createTitleTag(album, useAlbum));
		param.addQuote("--year", album.aYear);
		param.addQuote("--track", track.aTrack);
		param.addQuote("--genre", album.aGenre);
		param.addQuote("--comment", album.aComment);
		param.add("-o");
		param.add(fileName);
		param.add("-");

		return param;
	}

	/**
	 * Get params for reading wav data and save it to file or write to stdout.
	 * 
	 * @param track
	 *            Track object
	 * @param outFileName
	 *            Name of destination file
	 * @param wholecd
	 *            true if whole cd is read as one track
	 * @return Command line parameters for reading cd
	 */
	public static ProcessParam getCDDecoder(int track, String outFileName,
			boolean wholecd) {
		ProcessParam param = new ProcessParam();

		param.add(Pref.getPref(Constants.CD_READER_KEY,
				Constants.CD_READER_DEFAULT));
		param.add("-D");
		param.add(Pref.getPref(Constants.CD_DEVICE_KEY,
				Constants.CD_DEVICE_DEFAULT));

		if (Pref.getPref(Constants.CD_SPEED_KEY, 0) != 0) {
			param.add("-S", String.format("%d",
					Pref.getPref(Constants.CD_SPEED_KEY, 0)));
		}

		if (Pref.getPref(Constants.PARANOIA_KEY, Constants.PARANOIA_DEFAULT)) {
			param.add("-paranoia");
		}

		if (Pref.getPref(Constants.CD_MONO_KEY, Constants.CD_MONO_DEFAULT)) {
			param.add("-m");
		}

		param.add("-g");
		param.add("-H");

		if (wholecd) {
			param.add("-d");
			param.add("99999");
		} else {
			param.add("-t");
			param.add(track);
		}

		if (outFileName != null) {
			param.add(outFileName);
		} else {
			param.add("-");
		}

		return param;
	}

	/**
	 * Get params for loading cd toc.
	 * 
	 * @return Command line parameters for reading table of contents from an cd
	 */
	public static ProcessParam getCDTOCDecoder() {
		ProcessParam param = new ProcessParam();

		param.add(Pref.getPref(Constants.CD_READER_KEY,
				Constants.CD_READER_DEFAULT));
		param.add("-D");
		param.add(Pref.getPref(Constants.CD_DEVICE_KEY,
				Constants.CD_DEVICE_DEFAULT));
		param.add("-g");
		param.add("-H");
		param.add("-J");
		param.add("-v");
		param.add("toc,title,sectors");

		return param;
	}

	// /**
	// * Get params for loading toc from cddb server.
	// *
	// * @return Command line parameters for reading cd tracks
	// */
	// public static ProcessParam getCDDBDecoder() {
	// ProcessParam param = new ProcessParam();
	//
	// param.add(Pref.getPref(Constants.CD_READER_KEY,
	// Constants.CD_READER_DEFAULT));
	// param.add("-D");
	// param.add(Pref.getPref(Constants.CD_DEVICE_KEY,
	// Constants.CD_DEVICE_DEFAULT));
	// param.add("-g");
	// param.add("-H");
	// param.add("-J");
	// param.add("-v");
	// param.add("toc,title,sectors");
	// param.add("-L");
	// param.add("1");
	// param.add("-cddbp-server=" + Pref.getPref(Constants.CDDBP_SERVER_KEY,
	// Constants.CDDBP_SERVER_DEFAULT));
	// param.add("-cddbp-port=" + Pref.getPref(Constants.CDDBP_PORT_KEY,
	// Constants.CDDBP_PORT_DEFAULT));
	//
	// return param;
	// }

	/**
	 * Get params for converting flac to wav, either to file or stdout.
	 * 
	 * @param inFileName
	 *            Name of source file
	 * @param outFileName
	 *            Source file or null for stdout
	 * @return Params for decoding flac file to wav
	 */
	public static ProcessParam getFlacDecoder(String inFileName,
			String outFileName) {
		ProcessParam param = new ProcessParam();

		param.add(Pref.getPref(Constants.FLAC_ENCODER_KEY,
				Constants.FLAC_ENCODER_DEFAULT));
		param.add("-f");
		param.add("-d");
		if (outFileName != null) {
			param.add("-o");
			param.add(outFileName);
		} else {
			param.add("-c");
		}
		param.add(inFileName);

		return param;
	}

	/**
	 * Get params for converting wav to flac.
	 * 
	 * @param album
	 *            Album object
	 * @param track
	 *            Track object
	 * @param fileName
	 *            Name of destination file
	 * @return ProcessParam object
	 * @throws Exception
	 *             Throws exception if error occured
	 */
	public static ProcessParam getFlacEncoder(Album album, Track track,
			String fileName, boolean useAlbum) throws Exception {
		ProcessParam param = new ProcessParam();
		String encoder;
		String option;

		if (track.getEncoderIndex() < Constants.ENCODER_INDEX_COUNT) {
			encoder = Pref.getPref(Constants.FLAC_ENCODER_KEY,
					Constants.FLAC_ENCODER_DEFAULT);
			option = Pref.getPref(
					Constants.ENCODER_PARAM_KEYS[track.getEncoderIndex()],
					Constants.ENCODER_PARAM_DEFAULTS[track.getEncoderIndex()]);
		} else {
			throw new Exception("FLAC ENCODER OUT OF RANGE, SHOULD NOT HAPPEN");
		}

		param.add(encoder);
		param.addSplitString(option);
		param.add("-f");
		param.add("--totally-silent");
		param.add("--no-padding");
		param.add(String.format("--tag=artist=%s",
				album.aArtist.replaceAll("\"", "")));
		param.add(String.format("--tag=album=%s",
				album.aAlbum.replaceAll("\"", "")));
		param.add(String.format("--tag=title=%s",
				track.createTitleTag(album, useAlbum).replaceAll("\"", "")));
		param.add(String.format("--tag=date=%s", album.aYear));
		param.add(String.format("--tag=tracknum=%s", track.aTrack));
		param.add(String.format("--tag=genre=%s", album.aGenre));
		param.add(String.format("--tag=comment=%s",
				album.aComment.replaceAll("\"", "")));
		param.add("-o");
		param.add(fileName);
		param.add("-");

		return param;
	}

	/*
	 * Get params for converting wav to mp3.
	 * 
	 * @param album Album object
	 * 
	 * @param track Track object
	 * 
	 * @param fileName Destination filename
	 * 
	 * @return ProcessParam object
	 * 
	 * @throws Exception
	 */
	public static ProcessParam getMP3Encoder(Album album, Track track,
			String fileName, boolean useAlbum) throws Exception {
		ProcessParam param = new ProcessParam();
		String encoder;
		String option;

		if (track.getEncoderIndex() < Constants.ENCODER_INDEX_COUNT) {
			encoder = Pref.getPref(Constants.MP3_ENCODER_KEY,
					Constants.MP3_ENCODER_DEFAULT);
			option = Pref.getPref(
					Constants.ENCODER_PARAM_KEYS[track.getEncoderIndex()],
					Constants.ENCODER_PARAM_DEFAULTS[track.getEncoderIndex()]);
		} else {
			throw new Exception("MP3 ENCODER OUT OF RANGE, SHOULD NOT HAPPEN");
		}

		param.add(encoder);
		param.addSplitString(option);
		param.add("--quiet");
		param.add("--add-id3v2");
		param.add("--ta", album.aArtist);
		param.add("--tl", album.aAlbum);
		param.add("--tt", track.createTitleTag(album, useAlbum));
		param.add("--ty", album.aYear);
		param.add("--tn", track.aTrack);
		param.add("--tg", album.aGenre);
		param.add("--tc", album.aComment);
		param.add("-");
		param.add(fileName);

		return param;
	}

	/**
	 * Get params for converting mp3 to wav data.
	 * 
	 * @param inFileName
	 *            Source filename
	 * @param outFileName
	 *            Destination file or null for stdout
	 * @return Params for decoding mp3 file to wav
	 * @throws Exception
	 *             Throws exception if error occured
	 */
	public static ProcessParam getMP3Decoder(String inFileName,
			String outFileName) throws Exception {
		ProcessParam param = new ProcessParam();

		param.add(Pref.getPref(Constants.MP3_ENCODER_KEY,
				Constants.MP3_ENCODER_DEFAULT));
		param.add("--decode");
		param.add(inFileName);
		if (outFileName != null) {
			param.add(outFileName);
		} else {
			param.add("-");
		}

		return param;
	}

	/**
	 * Get params for converting mp3 to wav file.
	 * 
	 * @param inFileName
	 *            Source filename
	 * @param outFileName
	 *            Destination file or null for stdout
	 * @return Params for decoding mp3 file to wav
	 * @throws Exception
	 *             Throws exception if error occured
	 */
	public static ProcessParam getOggDecoder(String inFileName,
			String outFileName) throws Exception {
		ProcessParam param = new ProcessParam();

		param.add(Pref.getPref(Constants.OGG_DECODER_KEY,
				Constants.OGG_DECODER_DEFAULT));
		param.add(inFileName);
		param.add("-o");
		if (outFileName != null) {
			param.add(outFileName);
		} else {
			param.add("-");
		}

		return param;
	}

	/**
	 * Get params for converting audio to ogg by oggenc.
	 * 
	 * @param album
	 *            Album object
	 * @param track
	 *            Track object
	 * @param fileName
	 *            Name of destination file
	 * @return ProcessParam object with command line parameters for encoding ogg
	 * @throws Exception
	 *             Throws exception if error occured
	 */
	public static ProcessParam getOggEncoder(Album album, Track track,
			String fileName, boolean useAlbum) throws Exception {
		ProcessParam param = new ProcessParam();
		String encoder;
		String option;

		if (track.getEncoderIndex() < Constants.ENCODER_INDEX_COUNT) {
			encoder = Pref.getPref(Constants.OGG_ENCODER_KEY,
					Constants.OGG_ENCODER_DEFAULT);
			option = Pref.getPref(
					Constants.ENCODER_PARAM_KEYS[track.getEncoderIndex()],
					Constants.ENCODER_PARAM_DEFAULTS[track.getEncoderIndex()]);
		} else {
			throw new Exception("OGG ENCODER OUT OF RANGE, SHOULD NOT HAPPEN");
		}

		param.add(encoder);
		param.addSplitString(option);
		param.add("--quiet");
		param.add("--artist", album.aArtist);
		param.add("--album", album.aAlbum);
		param.add("--title", track.createTitleTag(album, useAlbum));
		param.add("--date", album.aYear);
		param.add("--tracknum", track.aTrack);
		param.add("--genre", album.aGenre);
		param.add("-c", "comment=" + album.aComment);
		param.add("-o");
		param.add(fileName);
		param.add("-");

		return param;
	}

	/**
	 * Scan for scsi devices.
	 * 
	 * @return Command line parameters for executin scanbus command for cdda2wav
	 */
	public static ProcessParam getScanbus() {
		ProcessParam param = new ProcessParam();

		param.add(Pref.getPref(Constants.CD_READER_KEY,
				Constants.CD_READER_DEFAULT));
		param.add("-scanbus");

		return param;
	}
}
