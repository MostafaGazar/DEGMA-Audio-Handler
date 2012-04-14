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

package RipConvertShared.music;

import java.util.Collections;
import java.util.HashMap;
import java.util.Set;
import java.util.Vector;

/**
 * Singleton object that contains all available genre strings for the mp3 genre
 * tag.
 */
public class MP3Genre {
	public static int DEFAULT_NUM = 12;
	public static int DEFAULT_SORTED_NUM = 95;
	public static String DEFAULT_STRING = "Other";

	private static MP3Genre aaMP3Genre = null;
	private HashMap<String, Integer> aNameToNumberLowerCase = null;
	private HashMap<Integer, String> aNumberToName = null;
	private Vector<String> aSortedGenre = null;

	/**
	 * Create new genre object.
	 */
	private MP3Genre() {
		HashMap<String, Integer> nameToNumber = new HashMap<String, Integer>();
		aNameToNumberLowerCase = new HashMap<String, Integer>();
		aNumberToName = new HashMap<Integer, String>();
		aSortedGenre = new Vector<String>();
		int index = 0;

		nameToNumber.put("Blues", index++);
		nameToNumber.put("Classic Rock", index++);
		nameToNumber.put("Country", index++);
		nameToNumber.put("Dance", index++);
		nameToNumber.put("Disco", index++);
		nameToNumber.put("Funk", index++);
		nameToNumber.put("Grunge", index++);
		nameToNumber.put("Hip-Hop", index++);
		nameToNumber.put("Jazz", index++);
		nameToNumber.put("Metal", index++);
		nameToNumber.put("New Age", index++);
		nameToNumber.put("Oldies", index++);
		nameToNumber.put("Other", index++);
		nameToNumber.put("Pop", index++);
		nameToNumber.put("R&B", index++);
		nameToNumber.put("Rap", index++);
		nameToNumber.put("Reggae", index++);
		nameToNumber.put("Rock", index++);
		nameToNumber.put("Techno", index++);
		nameToNumber.put("Industrial", index++);
		nameToNumber.put("Alternative", index++);
		nameToNumber.put("Ska", index++);
		nameToNumber.put("Death Metal", index++);
		nameToNumber.put("Pranks", index++);
		nameToNumber.put("Soundtrack", index++);
		nameToNumber.put("Euro-Techno", index++);
		nameToNumber.put("Ambient", index++);
		nameToNumber.put("Trip-Hop", index++);
		nameToNumber.put("Vocal", index++);
		nameToNumber.put("Jazz+Funk", index++);
		nameToNumber.put("Fusion", index++);
		nameToNumber.put("Trance", index++);
		nameToNumber.put("Classical", index++);
		nameToNumber.put("Instrumental", index++);
		nameToNumber.put("Acid", index++);
		nameToNumber.put("House", index++);
		nameToNumber.put("Game", index++);
		nameToNumber.put("Sound Clip", index++);
		nameToNumber.put("Gospel", index++);
		nameToNumber.put("Noise", index++);
		nameToNumber.put("Alt. Rock", index++);
		nameToNumber.put("Bass", index++);
		nameToNumber.put("Soul", index++);
		nameToNumber.put("Punk", index++);
		nameToNumber.put("Space", index++);
		nameToNumber.put("Meditative", index++);
		nameToNumber.put("Instrumental Pop", index++);
		nameToNumber.put("Instrumental Rock", index++);
		nameToNumber.put("Ethnic", index++);
		nameToNumber.put("Gothic", index++);
		nameToNumber.put("Darkwave", index++);
		nameToNumber.put("Techno-Industrial", index++);
		nameToNumber.put("Electronic", index++);
		nameToNumber.put("Pop-Folk", index++);
		nameToNumber.put("Eurodance", index++);
		nameToNumber.put("Dream", index++);
		nameToNumber.put("Southern Rock", index++);
		nameToNumber.put("Comedy", index++);
		nameToNumber.put("Cult", index++);
		nameToNumber.put("Gangsta Rap", index++);
		nameToNumber.put("Top 40", index++);
		nameToNumber.put("Christian Rap", index++);
		nameToNumber.put("Pop/Funk", index++);
		nameToNumber.put("Jungle", index++);
		nameToNumber.put("Native American", index++);
		nameToNumber.put("Cabaret", index++);
		nameToNumber.put("New Wave", index++);
		nameToNumber.put("Psychedelic", index++);
		nameToNumber.put("Rave", index++);
		nameToNumber.put("Showtunes", index++);
		nameToNumber.put("Trailer", index++);
		nameToNumber.put("Low-Fi", index++);
		nameToNumber.put("Tribal", index++);
		nameToNumber.put("Acid Punk", index++);
		nameToNumber.put("Acid Jazz", index++);
		nameToNumber.put("Polka", index++);
		nameToNumber.put("Retro", index++);
		nameToNumber.put("Musical", index++);
		nameToNumber.put("Rock & Roll", index++);
		nameToNumber.put("Hard Rock", index++);
		nameToNumber.put("Folk", index++);
		nameToNumber.put("Folk/Rock", index++);
		nameToNumber.put("National Folk", index++);
		nameToNumber.put("Swing", index++);
		nameToNumber.put("Fast Fusion", index++);
		nameToNumber.put("Bebop", index++);
		nameToNumber.put("Latin", index++);
		nameToNumber.put("Revival", index++);
		nameToNumber.put("Celtic", index++);
		nameToNumber.put("Bluegrass", index++);
		nameToNumber.put("Avantgarde", index++);
		nameToNumber.put("Gothic Rock", index++);
		nameToNumber.put("Progressive Rock", index++);
		nameToNumber.put("Psychedelic Rock", index++);
		nameToNumber.put("Symphonic Rock", index++);
		nameToNumber.put("Slow Rock", index++);
		nameToNumber.put("Big Band", index++);
		nameToNumber.put("Chorus", index++);
		nameToNumber.put("Easy Listening", index++);
		nameToNumber.put("Acoustic", index++);
		nameToNumber.put("Humour", index++);
		nameToNumber.put("Speech", index++);
		nameToNumber.put("Chanson", index++);
		nameToNumber.put("Opera", index++);
		nameToNumber.put("Chamber Music", index++);
		nameToNumber.put("Sonata", index++);
		nameToNumber.put("Symphony", index++);
		nameToNumber.put("Booty Bass", index++);
		nameToNumber.put("Primus", index++);
		nameToNumber.put("Porn Groove", index++);
		nameToNumber.put("Satire", index++);
		nameToNumber.put("Slow Jam", index++);
		nameToNumber.put("Club", index++);
		nameToNumber.put("Tango", index++);
		nameToNumber.put("Samba", index++);
		nameToNumber.put("Folklore", index++);
		nameToNumber.put("Ballad", index++);
		nameToNumber.put("Power Ballad", index++);
		nameToNumber.put("Rhythmic Soul", index++);
		nameToNumber.put("Freestyle", index++);
		nameToNumber.put("Duet", index++);
		nameToNumber.put("Punk Rock", index++);
		nameToNumber.put("Drum Solo", index++);
		nameToNumber.put("A Cappella", index++);
		nameToNumber.put("Euro-House", index++);
		nameToNumber.put("Dance Hall", index++);
		nameToNumber.put("Goa", index++);
		nameToNumber.put("Drum & Bass", index++);
		nameToNumber.put("Club-House", index++);
		nameToNumber.put("Hardcore", index++);
		nameToNumber.put("Terror", index++);
		nameToNumber.put("Indie", index++);
		nameToNumber.put("BritPop", index++);
		nameToNumber.put("Negerpunk", index++);
		nameToNumber.put("Polsk Punk", index++);
		nameToNumber.put("Beat", index++);
		nameToNumber.put("Christian Gangsta Rap", index++);
		nameToNumber.put("Heavy Metal", index++);
		nameToNumber.put("Black Metal", index++);
		nameToNumber.put("Crossover", index++);
		nameToNumber.put("Contemporary Christian", index++);
		nameToNumber.put("Christian Rock", index++);
		nameToNumber.put("Merengue", index++);
		nameToNumber.put("Salsa", index++);
		nameToNumber.put("Thrash Metal", index++);
		nameToNumber.put("Anime", index++);
		nameToNumber.put("Jpop", index++);
		nameToNumber.put("Synthpop", index++);

		Set<String> names = nameToNumber.keySet();

		for (String s : names) {
			aNumberToName.put(nameToNumber.get(s), s);
			aSortedGenre.add(s);
		}
		Collections.sort(aSortedGenre);

		for (String s : names) {
			aNameToNumberLowerCase.put(s.toLowerCase(), nameToNumber.get(s));
		}
	}

	/**
	 * Get MP3Genre object.
	 * 
	 * @return MP3Genre
	 */
	public static MP3Genre get() {
		if (aaMP3Genre == null) {
			aaMP3Genre = new MP3Genre();
		}
		return aaMP3Genre;
	}

	/**
	 * Get a list with ID3v1 genres.
	 */
	public Vector<String> getGenres() {
		return aSortedGenre;
	}

	/**
	 * Parse genre string.
	 * 
	 * @param genre
	 * @return
	 */
	public String parseGenre(String genre) {
		int num = getGenreNumber(genre);

		if (num != DEFAULT_NUM) {
			return aNumberToName.get(num); // Input genre string is ok, return
											// it
		} else {
			// Try to grab all numbers in string
			String nums = "";
			for (int f = 0; f < genre.length(); f++) {
				char c = genre.charAt(f);
				if (c >= '0' && c <= '9') {
					nums += c;
				}
			}
			try {
				num = Integer.parseInt(nums);
			} catch (NumberFormatException e1) {
				return DEFAULT_STRING;
			}

			if (num >= 0 && num < aNumberToName.size()) {
				return aNumberToName.get(num);
			} else {
				return DEFAULT_STRING;
			}
		}
	}

	/**
	 * Use genre number to return a Genre string.
	 * 
	 * @param num
	 *            - Genre number
	 * @return Genre string or DEFAULT_STRING
	 */
	public String getGenre(int num) {
		if (num >= 0 && num < aNumberToName.size()) {
			return aNumberToName.get(num);
		} else {
			return DEFAULT_STRING;
		}
	}

	/**
	 * Use genre number to return a Genre string.
	 * 
	 * @param genre
	 *            - Genre number
	 * @return Genre string or DEFAULT_STRING
	 */
	public String getGenre(String genre) {
		int num;

		try {
			num = Integer.parseInt(genre);
		} catch (NumberFormatException e) {
			num = DEFAULT_NUM;
		}

		if (num >= 0 && num < aNumberToName.size()) {
			return aNumberToName.get(num);
		} else {
			return DEFAULT_STRING;
		}
	}

	/**
	 * Use genre name to get genre number.
	 * 
	 * @param genre
	 *            - Genre string, like "Blues"
	 * @return Genre number or DEFAULT_NUM
	 */
	public int getGenreNumber(String genre) {
		Integer i = aNameToNumberLowerCase.get(genre.toLowerCase());
		if (i != null) {
			return i.intValue();
		} else {
			return DEFAULT_NUM;
		}
	}
}
