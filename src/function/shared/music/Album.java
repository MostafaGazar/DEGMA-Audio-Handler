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
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Album stores data for one cd/album.* Like artist/album etc. And all track
 * objects.
 */
public class Album {
	private static final Pattern TIME = Pattern
			.compile("(\\d*):(\\d*)\\.(\\d*)");
	public String aArtist = "";
	public String aAlbum = "";
	public String aGenre = "";
	public String aYear = "";
	public String aComment = "";
	public String aDiscID = "";
	public String aTracksString = "";
	public String aTimeString = "";
	public String aSeconds = "";
	public boolean aCD = false;
	public ArrayList<Track> aTracks = new ArrayList<Track>();

	/**
     *
     */
	public Album() {
	}

	/**
	 * @param artist
	 *            - Artist name
	 * @param album
	 *            - Album name
	 * @param genre
	 *            - Genre name
	 * @param year
	 *            - Year
	 * @param comment
	 *            - Some comment
	 */
	public Album(String artist, String album, String genre, String year,
			String comment) {
		aArtist = artist;
		aAlbum = album;
		aGenre = genre;
		aYear = year;
		aComment = comment;
	}

	/**
	 * Add new track to this album.
	 * 
	 * @param name
	 * @param time
	 * @param sector
	 *            The sector number on the cd, which comes from cddawav
	 */
	public void addTrack(String name, String time, String sector) {
		aTracks.add(new Track(name, aTracks.size() + 1, time, sector));
	}

	/**
	 * Add new track to this album. This is for adding audio files.
	 * 
	 * @param name
	 * @param file
	 */
	public void addTrack(String name, File file) {
		aTracks.add(new Track(name, aTracks.size() + 1, file));

	}

	/**
	 * Calc running time for the tracks.
	 * 
	 * @return
	 */
	public void calcRunningTime() {
		int tot = 0;

		for (Track track : aTracks) {
			Matcher m1 = TIME.matcher(track.aTime);

			track.aTime2 = 0;
			if (m1.find()) {
				int min = 0;
				int sec = 0;
				int dec = 0;

				try {
					min = Integer.valueOf(m1.group(1));
					sec = Integer.valueOf(m1.group(2));
					dec = Integer.valueOf(m1.group(3));
					track.aTime2 = tot;
					tot += dec + (sec * 75) + (min * 60 * 75);
				} catch (NumberFormatException e) {
				}
			}
		}
	}

	/**
	 * Get number of selected tracks.
	 * 
	 * @return
	 */
	public int getNumOfSelectedTracks() {
		int count = 0;
		for (Track track : aTracks) {
			if (track.aSelected) {
				count++;
			}
		}
		return count;
	}

	/**
	 * Get track number of specific track.
	 * 
	 * @return
	 */
	public int getTrackNum(Track track) {
		int count = 0;
		for (Track track2 : aTracks) {
			if (track.equals(track2)) {
				break;
			} else {
				count++;
			}
		}
		return count;
	}

	/**
	 * Set disc id.
	 * 
	 * @param discID
	 */
	public void setDiscID(String discID) {
		aDiscID = discID.replaceAll("^0[xX]", "");
	}

	/**
	 * Get a debug string representation of this album.
	 * 
	 * @return
	 */
	@Override
	public String toString() {
		String tracks = "";
		for (Track track : aTracks) {
			tracks += track.aSector;
			tracks += " / ";
			tracks += track.aTime;
			tracks += " / ";
			tracks += track.aName;
			tracks += "\n";
		}
		return "Artist: " + aArtist + "\n" + "Album: " + aAlbum + "\n"
				+ "CDDBID: " + aDiscID + "\n" + "Genre: " + aGenre + "\n"
				+ "Time: " + aTimeString + "\n" + "Tracks: " + aTracksString
				+ "\n" + "Secs: " + aSeconds + "\n" + tracks;
	}
}
