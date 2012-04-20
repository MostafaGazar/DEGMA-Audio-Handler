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

package function.shared.ui;


import javax.swing.table.DefaultTableModel;

import function.shared.music.Album;
import function.shared.music.Track;


import util.Constants;

/**
 * AlbumModel is a table model that contains all tracks that will be ripped or
 * encoded/decoded.
 */
public class AlbumModel extends DefaultTableModel {// AbstractTableModel{
	private static final long serialVersionUID = 1L;

	private Album aAlbum = null;

	/**
     *
     */
	public AlbumModel() {
	}

	/**
	 * Return current album.
	 * 
	 * @return The current used Album object
	 */
	public Album getAlbum() {
		return aAlbum;
	}

	/**
	 * Get table column name.
	 * 
	 * @param col
	 *            - Column number
	 * @return Name of column
	 */
	@Override
	public String getColumnName(int col) {
		if (col == 0) {
			return "Convert";
		} else if (col == 1) {
			return "Track";
		} else if (col == 2) {
			return "Title";
		} else if (col == 3) {
			return "Type";
		} else {
			return "Time";
		}
	}

	/**
	 * Return number of rows.
	 * 
	 * @return Number of tracks
	 */
	@Override
	public int getRowCount() {
		if (aAlbum != null) {
			return aAlbum.aTracks.size();
		} else {
			return 0;
		}
	}

	/**
	 * Return what type of object table is going to edit.
	 * 
	 * @param column
	 *            Column number
	 * @return Class type
	 */
	@Override
	@SuppressWarnings("unchecked")
	public Class getColumnClass(int column) {
		Object anObject = getValueAt(0, column);

		if (anObject != null)
			return anObject.getClass();

		return null;
	}

	/**
	 * 5 they are.
	 * 
	 * @return Columns
	 */
	@Override
	public int getColumnCount() {
		return 5;
	}

	/**
	 * @param row
	 * @param col
	 * @return Object to paint
	 */
	@Override
	public Object getValueAt(int row, int col) {
		Track track;

		try {
			track = aAlbum.aTracks.get(row);
		} catch (Exception e) {
			return null;
		}

		assert track != null;

		if (col == 0) {
			return track.aSelected;
		} else if (col == 1) {
			return track.aTrack;
		} else if (col == 2) {
			return track.aName;
		} else if (col == 3) {
			switch (track.getDecoder()) {
			case Constants.MP3_TRACK:
				return "MP3";

			case Constants.OGG_TRACK:
				return "Ogg";

			case Constants.FLAC_TRACK:
				return "Flac";

			case Constants.M4A_TRACK:
				return "M4A";

			case Constants.CD_TRACK:
				return "CD";

			case Constants.WAV_TRACK:
				return "Wav";

			default:
				assert false;
				return "";
			}
		} else {
			return track.aTime;
		}
	}

	/**
	 * Only filename and select box can be edited.
	 * 
	 * @param row
	 * @param col
	 * @return true if cell can be edited
	 */
	@Override
	public boolean isCellEditable(int row, int col) {
		switch (col) {
		case 0:
		case 2:
			return true;

		default:
			return false;
		}

	}

	/**
	 * Set new album to display.
	 * 
	 * @param album
	 */
	public void setAlbum(Album album) {
		aAlbum = album;
		fireTableDataChanged();
	}

	/**
	 * Select/unselect track or edit track name.
	 * 
	 * @param value
	 * @param row
	 * @param col
	 */
	@Override
	public void setValueAt(Object value, int row, int col) {
		assert value != null;
		Track track;

		try {
			track = aAlbum.aTracks.get(row);
		} catch (Exception e) {
			return;
		}

		if (col == 0) {
			track.aSelected = (Boolean) value;
		} else if (col == 2) {
			track.aName = (String) value;
		}
		fireTableDataChanged();
	}
}
