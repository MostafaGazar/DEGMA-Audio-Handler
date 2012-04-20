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

package function.shared.music;


import java.io.File;

import function.shared.util.Pref;


import util.Constants;

/**
 * Track stores data for one single cd/mp3 track.
 */
public class Track {
	public String aName = null;
	public String aTime = null;
	public int aTime2 = 0;
	public String aSector = null;
	public int aTrack = 0;
	public boolean aSelected = false;
	public String aFile = null;
	private int aDecoder = 0;
	private int aEncoder = 0;
	private int aEncoderIndex = 0;
	private static final String ILLEGAl_CHAR = "[:/\\\\?\\*]";

	/**
	 * @param name
	 *            - Track title
	 * @param track
	 *            - Track number
	 * @param sector
	 *            - Track time
	 * @param time
	 *            - Sector
	 */
	public Track(String name, int track, String time, String sector) {
		aDecoder = Constants.CD_TRACK;
		set(name, track, time, sector, null);
	}

	/**
	 * @param name
	 *            - Track title
	 * @param track
	 *            - Track number
	 * @param file
	 *            - Track file
	 */
	public Track(String name, int track, File file) {// Change
														// LoadDirectoryTask.java
		if (file.getName().toLowerCase().toLowerCase().endsWith(".flac")) {
			aDecoder = Constants.FLAC_TRACK;
		} else if (file.getName().toLowerCase().toLowerCase().endsWith(".wav")) {
			aDecoder = Constants.WAV_TRACK;
		} else if (file.getName().toLowerCase().toLowerCase().endsWith(".mp3")) {
			aDecoder = Constants.MP3_TRACK;
		} else if (file.getName().toLowerCase().toLowerCase().endsWith(".m4a")
				|| file.getName().toLowerCase().toLowerCase().endsWith(".mp4")
				|| file.getName().toLowerCase().toLowerCase().endsWith(".acc")) {
			aDecoder = Constants.M4A_TRACK;
		} else if (file.getName().toLowerCase().toLowerCase().endsWith(".ogg")) {
			aDecoder = Constants.OGG_TRACK;
		} else if (file.getName().toLowerCase().toLowerCase().endsWith(".wav")) {
			aDecoder = Constants.WAV_TRACK;
		} else {
			aDecoder = Constants.CD_TRACK;
		}

		set(name, track, "", "", file);

		if (Pref.getPref(Constants.TITLE_REMOVEPREFIX_KEY,
				Constants.TITLE_REMOVEPREFIX_DEFAULT)) {
			aName = aName.replaceAll("^[\\d\\s-]+", "");
		}
	}

	/**
	 * Create complete filename for a track.
	 * 
	 * @param album
	 *            Album data
	 * @param overRiderFileNameCreationForWholeAlbumEncoding
	 *            Use album title instead of the track title
	 * @return Full name of destination file
	 * @throws Exception
	 *             Throws exception if some error occured
	 */
	public String createFileName(Album album,
			boolean overRiderFileNameCreationForWholeAlbumEncoding)
			throws Exception {
		String baseDir = Pref.getPref(Constants.BASE_DIRECTORY_KEY, "");
		File file = new File(baseDir);
		int dirOption = Pref.getPref(Constants.DIRECTORY_KEY, 0);
		int fileOption = Pref.getPref(Constants.FILENAME_KEY, 0);
		int fileNumberOption = Pref.getPref(Constants.FILENUMBER_KEY, 1);
		boolean useNumber = Pref.getPref(Constants.FILENUMBER_USE_KEY,
				Constants.FILENUMBER_USE_DEFAULT);
		String fileNumberSepOption = Pref.getPref(
				Constants.FILENUMBER_SEPERATOR_KEY,
				Constants.FILENUMBER_SEPERATOR_DEFAULT);
		String extension = Constants.ENCODER_EXTENSION_OPTIONS[aEncoder];

		if (!file.isDirectory()) {
			throw new Exception(
					"Base direcory doesn't exist. Set directory in the setup dialog (General settings).");
		}

		baseDir += File.separator;

		String albumName = "";
		String artist2 = album.aArtist.replaceAll(ILLEGAl_CHAR, " ");
		String album2 = album.aAlbum.replaceAll(ILLEGAl_CHAR, " ");
		artist2 = artist2.trim();
		album2 = album2.trim();

		if (overRiderFileNameCreationForWholeAlbumEncoding) {
			if (artist2.length() > 0) {
				albumName += artist2;
				albumName += File.separator;
			}
		} else if (dirOption == 1) {
			// Genre/Artist/Album
			if (album.aGenre.length() > 0) {
				albumName += album.aGenre;
				albumName += File.separator;
			}

			if (artist2.length() > 0) {
				albumName += artist2;
				albumName += File.separator;
			}

			if (album2.length() > 0) {
				albumName += album2;
				albumName += File.separator;
			}
		} else if (dirOption == 2) {
			// Artist - Album
			if (artist2.length() > 0) {
				albumName += artist2;

				if (album2.length() > 0) {
					albumName += " - ";
					albumName += album2;
				}
			} else if (album2.length() > 0) {
				albumName += album2;
			}
		} else if (dirOption == 3) {
			// Genre/Artist - Album
			if (album.aGenre.length() > 0) {
				albumName += album.aGenre;
				albumName += File.separator;
			}

			if (artist2.length() > 0) {
				albumName += artist2;

				if (album2.length() > 0) {
					albumName += " - ";
					albumName += album2;
				}
			} else if (album2.length() > 0) {
				albumName += album2;
			}
		} else if (dirOption == 4) {
			// Artist/Album (Year)
			if (artist2.length() > 0) {
				albumName += artist2;
				albumName += File.separator;
			}

			if (album2.length() > 0) {
				albumName += album2;
			}

			if (album.aYear.length() > 0) {
				if (album2.length() > 0) {
					albumName += " ";
				}
				albumName += "(";
				albumName += album.aYear;
				albumName += ")";
			}
		} else if (dirOption == 5) {
			// Artist - Album (Year)
			albumName += artist2;

			if (album2.length() > 0) {
				if (albumName.length() > 0) {
					albumName += " - ";
				}
				albumName += album2;
			}

			if (album.aYear.length() > 0) {
				if (albumName.length() > 0) {
					albumName += " ";
				}

				albumName += "(";
				albumName += album.aYear;
				albumName += ")";
			}
		} else {
			// Artist/Album
			if (artist2.length() > 0) {
				albumName += artist2;
				albumName += File.separator;
			}

			if (album2.length() > 0) {
				albumName += album2;
				albumName += File.separator;
			}
		}

		if (albumName.endsWith(File.separator) == false) {
			albumName += File.separator;
		}

		String prefix = "";
		if (useNumber) {
			switch (fileNumberOption) {
			case 0:
				// N
				prefix = String.format("%d", aTrack);
				break;

			case 2:
				// NNN
				prefix = String.format("%03d", aTrack);
				break;

			case 1:
			default:
				// NN, default zero padded prefix
				prefix = String.format("%02d", aTrack);
				break;
			}
			prefix += fileNumberSepOption;
		}

		String fileName;

		if (overRiderFileNameCreationForWholeAlbumEncoding) {
			fileName = album2;
		} else {
			switch (fileOption) {
			case 1:
				// Artist - Track
				if (album.aArtist.length() == 0) {
					fileName = String.format("%s - %s", prefix, aName);
				} else {
					fileName = String.format("%s%s - %s", prefix,
							album.aArtist, aName);
				}
				break;

			case 2:
				// Album - Track
				if (album.aAlbum.length() == 0) {
					fileName = String.format("%s - %s", prefix, aName);
				} else {
					fileName = String.format("%s%s - %s", prefix, album.aAlbum,
							aName);
				}
				break;

			case 3:
				// Artist - Album - Track
				if (album.aArtist.length() == 0 && album.aAlbum.length() == 0) {
					fileName = String.format("%s - %s", prefix, aName);
				} else if (album.aArtist.length() == 0) {
					fileName = String.format("%s%s - %s", prefix, album.aAlbum,
							aName);
				} else if (album.aAlbum.length() == 0) {
					fileName = String.format("%s%s - %s", prefix,
							album.aArtist, aName);
				} else {
					fileName = String.format("%s%s - %s - %s", prefix,
							album.aArtist, album.aAlbum, aName);
				}
				break;

			case 0:
			default:
				// Track
				fileName = String.format("%s%s", prefix, aName);
				break;
			}
		}

		fileName = fileName.replaceAll("/", "-");
		fileName = fileName.replaceAll(ILLEGAl_CHAR, " ");
		fileName = fileName.replaceAll("\\.+", ",");
		fileName = fileName.replaceAll("(,*)$", "");
		fileName = fileName.replaceAll("\\s+", " ");
		fileName = fileName.replaceAll("\"", "");

		albumName = albumName.replaceAll("\\s+", " ");
		albumName = albumName.replaceAll("\"", "");
		albumName = albumName.replaceAll("\\.", "");
		albumName = albumName.trim();
		fileName = fileName.trim();

		fileName = baseDir + albumName + fileName;
		fileName += extension;

		// Check and create destination directory
		file = new File(baseDir + albumName);
		if (file.exists() && !file.isDirectory()) {
			throw new Exception("Destination is a file! " + baseDir + albumName);
		} else if (file.exists() && file.isDirectory()) {
			;
		} else if (!file.mkdirs()) {
			throw new Exception("Can't create directory! " + baseDir
					+ albumName);
		}

		file = new File(fileName);
		if (file.exists() && file.isDirectory()) {
			throw new Exception("Destination is a directory! " + fileName);
		}

		return fileName;
	}

	/**
	 * Create title tag depending on option TITLE_KEY
	 * 
	 * @param album
	 *            Album object
	 * @param useAlbum
	 *            Use album title instead of track title
	 * @return New title tag
	 */
	public String createTitleTag(Album album, boolean useAlbum) {
		int titleOption = Pref.getPref(Constants.TITLE_KEY, 0);
		String name = aName.replaceAll("\\\\'", "");

		if (useAlbum) {
			name = album.aAlbum.replaceAll("\\\\'", "");
		}
		if (titleOption == 1) {
			// Artist - Title
			if (album.aArtist.length() == 0) {
				name = String.format("%s", name);
			} else {
				name = String.format("%s - %s", album.aArtist, name);
			}
		} else if (titleOption == 2) {
			// Artist / Title
			if (album.aArtist.length() == 0) {
				name = String.format("%s", name);
			} else {
				name = String.format("%s / %s", album.aArtist, name);
			}
		} else if (titleOption == 3) {
			// Artist - Album - Title
			if (album.aArtist.length() == 0 && album.aAlbum.length() == 0) {
				name = String.format("%s", name);
			} else if (album.aArtist.length() == 0) {
				name = String.format("%s - %s", album.aAlbum, name);
			} else if (album.aAlbum.length() == 0) {
				name = String.format("%s - %s", album.aArtist, name);
			} else {
				name = String.format("%s - %s - %s", album.aArtist,
						album.aAlbum, name);
			}
		} else if (titleOption == 4) {
			// Artist / Album / Title
			if (album.aArtist.length() == 0 && album.aAlbum.length() == 0) {
				name = String.format("%s", name);
			} else if (album.aArtist.length() == 0) {
				name = String.format("%s / %s", album.aAlbum, name);
			} else if (album.aAlbum.length() == 0) {
				name = String.format("%s / %s", album.aArtist, name);
			} else {
				name = String.format("%s / %s / %s", album.aArtist,
						album.aAlbum, name);
			}
		} else if (titleOption == 5) {
			// Album - Title
			if (album.aAlbum.length() == 0) {
				name = String.format("%s", name);
			} else {
				name = String.format("%s - %s", album.aAlbum, name);
			}
		} else if (titleOption == 6) {
			// Album / Title
			if (album.aAlbum.length() == 0) {
				name = String.format("%s", name);
			} else {
				name = String.format("%s / %s", album.aAlbum, name);
			}
		}
		return name;
	}

	/**
	 * What decoder should be used when decoding in stream.
	 * 
	 * @return CD_TRACK, MP3_TRACK, OGG_TRACK, M4A_TRACK, FLAC_TRACK, WAV_TRACK
	 */
	public int getDecoder() {
		return aDecoder;
	}

	/**
	 * What encoder outstream will have.
	 * 
	 * @return CD_TRACK, MP3_TRACK, OGG_TRACK, M4A_TRACK, FLAC_TRACK, WAV_TRACK
	 */
	public int getEncoder() {
		return aEncoder;
	}

	/**
	 * @return Parameter index
	 */
	public int getEncoderIndex() {
		return aEncoderIndex;
	}

	/**
	 * @param name
	 *            - Track title
	 * @param track
	 *            - Track number
	 * @param time
	 *            - Track time
	 * @param sector
	 *            - Sector
	 * @param file
	 *            - Track file
	 */
	public void set(String name, int track, String time, String sector,
			File file) {
		setName(name);
		aTrack = track;
		aTime = time;
		aSector = sector;
		aSelected = true;
		aFile = (file != null) ? file.getPath() : null;
	}

	/**
	 * @param encoderIndex
	 */
	public void setEncoderIndex(int encoderIndex) {
		switch (encoderIndex) {
		case Constants.MP3_EXTREME_PARAM:
		case Constants.MP3_HIGH_PARAM:
		case Constants.MP3_NORMAL_PARAM:
		case Constants.MP3_BOOK_PARAM:
			aEncoderIndex = encoderIndex;
			aEncoder = Constants.MP3_TRACK;
			break;

		case Constants.OGG_EXTREME_PARAM:
		case Constants.OGG_HIGH_PARAM:
		case Constants.OGG_NORMAL_PARAM:
		case Constants.OGG_BOOK_PARAM:
			aEncoderIndex = encoderIndex;
			aEncoder = Constants.OGG_TRACK;
			break;

		case Constants.AAC_EXTREME_PARAM:
		case Constants.AAC_HIGH_PARAM:
		case Constants.AAC_NORMAL_PARAM:
		case Constants.AAC_BOOK_PARAM:
			aEncoderIndex = encoderIndex;
			aEncoder = Constants.M4A_TRACK;
			break;

		case Constants.FLAC_PARAM:
			aEncoderIndex = encoderIndex;
			aEncoder = Constants.FLAC_TRACK;
			break;

		case Constants.WAV_PARAM:
			aEncoderIndex = encoderIndex;
			aEncoder = Constants.WAV_TRACK;
			break;
		}
	}

	/**
	 * @param name
	 *            - Track title
	 */
	public void setName(String name) {
		aName = name;
		aName = aName.replaceAll("\\.[Ww][Aa][Vv]", "");
		aName = aName.replaceAll("\\.[Ff][Ll][Aa][Cc]", "");
		aName = aName.replaceAll("\\.[Mm][Pp][3]", "");
		aName = aName.replaceAll("\\.[Oo][Gg][Gg]", "");
		aName = aName.replaceAll("\\.[Mm][4][Aa]", "");
		aName = aName.replaceAll("\\\\'", "");
		aName = aName.replaceAll("'", "");
		aName = aName.replaceAll("\\s+", " ");
		aName = aName.trim();
		if (aName.length() == 0) {
			aName = String.format("AudioTrack");
		}
	}

	/**
	 * Convert track time to time in MSF format.
	 * 
	 * @return "00:00:00" to "99:59:74"
	 */
	public String timeToString() {
		int min = aTime2 / (60 * 75);
		int sec = (aTime2 - (min * (60 * 75))) / 75;
		int dec = (aTime2 - (min * (60 * 75)) - (sec * 75));
		return String.format("%02d:%02d:%02d", min, sec, dec);
	}
}
