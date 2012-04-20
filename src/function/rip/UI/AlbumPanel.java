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

package function.rip.UI;


import javax.swing.*;

import function.rip.Rip;
import function.shared.music.Album;
import function.shared.music.MP3Genre;
import function.shared.util.Pref;


import util.Constants;

import java.awt.*;
import java.util.Calendar;

/**
 * Show album data. It's a panel that contains a text fields for artist, album,
 * year, comment, genre and time. These data will be the meta info that will be
 * used for encoding the tracks.
 */
public class AlbumPanel extends JPanel {
	private static final long serialVersionUID = 666L;
	/**
	 * Artist string
	 */
	public JTextField aArtist = null;
	public JTextField aAlbum = null;
	public JTextField aYear = null;
	public JTextField aComment = null;
	public JComboBox aGenre = null;
	public JLabel aTime = null;

	Calendar now = Calendar.getInstance();

	/**
	 * Create album panel.
	 */
	public AlbumPanel() {
		super();

		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

		// Row 1
		JPanel tmp = new JPanel();
		tmp.setLayout(new BoxLayout(tmp, BoxLayout.X_AXIS));
		aArtist = new JTextField("");
		aYear = new JTextField("");
		aTime = new JLabel("00:00.00");
		aYear.setPreferredSize(new Dimension(100, (int) aYear.getSize()
				.getHeight()));
		aYear.setMaximumSize(new Dimension(200, 200));

		tmp.add(new JLabel("Artist"));
		tmp.add(Box.createRigidArea(new Dimension(3, 0)));
		tmp.add(aArtist);
		tmp.add(Box.createRigidArea(new Dimension(10, 0)));
		tmp.add(new JLabel("Year"));
		tmp.add(Box.createRigidArea(new Dimension(3, 0)));
		tmp.add(aYear);
		tmp.add(Box.createRigidArea(new Dimension(10, 0)));
		tmp.add(aTime);
		add(tmp);
		add(Box.createRigidArea(new Dimension(0, 3)));

		// Row 2
		tmp = new JPanel();
		tmp.setLayout(new BoxLayout(tmp, BoxLayout.X_AXIS));
		aAlbum = new JTextField("");
		aGenre = new JComboBox(MP3Genre.get().getGenres());
		aGenre.setSelectedIndex(MP3Genre.get().getGenres()
				.indexOf(MP3Genre.DEFAULT_STRING));
		aGenre.setPreferredSize(new Dimension(150, (int) aGenre.getSize()
				.getHeight()));
		aGenre.setMaximumSize(new Dimension(400, 200));

		tmp.add(new JLabel("Album"));
		tmp.add(Box.createRigidArea(new Dimension(3, 0)));
		tmp.add(aAlbum);
		tmp.add(Box.createRigidArea(new Dimension(10, 0)));
		tmp.add(new JLabel("Genre"));
		tmp.add(Box.createRigidArea(new Dimension(3, 0)));
		tmp.add(aGenre);
		add(tmp);
		add(Box.createRigidArea(new Dimension(0, 3)));

		// Row 3
		tmp = new JPanel();
		aComment = new JTextField("");
		tmp.setLayout(new BoxLayout(tmp, BoxLayout.X_AXIS));
		tmp.add(new JLabel("Comment"));
		tmp.add(Box.createRigidArea(new Dimension(3, 0)));
		tmp.add(aComment);
		add(tmp);

		add(Box.createRigidArea(new Dimension(0, 3)));
	}

	/**
	 * Copy album data.
	 * 
	 * @param album
	 *            - The album data object
	 * @return - true if data was ok
	 */
	public boolean copyAlbum(Album album) {
		album.aArtist = aArtist.getText();
		album.aAlbum = aAlbum.getText();
		album.aGenre = (String) aGenre.getSelectedItem();
		album.aYear = aYear.getText();
		album.aComment = aComment.getText();

		if (album.aGenre == null) {
			album.aGenre = MP3Genre.DEFAULT_STRING;
		}

		if (!Pref.getPref(Constants.ALBUM_DATA_KEY,
				Constants.ALBUM_DATA_DEFAULT)) {
			if (album.aArtist.length() == 0) {
				Rip.get().getWin().getStatusBar()
						.setErrorMessage("Set artist name before encoding");
				aArtist.requestFocus();
				return false;
			}

			if (album.aAlbum.length() == 0) {
				Rip.get().getWin().getStatusBar()
						.setErrorMessage("Set album name before encoding");
				aAlbum.requestFocus();
				return false;
			}

			if (album.aYear.length() == 0) {
				Rip.get().getWin().getStatusBar()
						.setErrorMessage("Set year before encoding");
				aYear.setText(((Integer) (now.get(Calendar.YEAR))).toString());
				aYear.requestFocus();
				return false;
			}
		}

		return true;
	}

	/**
	 * Reset album data.
	 */
	public void reset() {
		aArtist.setText("");
		aAlbum.setText("");
		aYear.setText("");
		aTime.setText("00:00.00");
		aComment.setText("");
		aGenre.setSelectedIndex(MP3Genre.DEFAULT_SORTED_NUM);
	}

	/**
	 * Set album data.
	 * 
	 * @param album
	 *            - The album data object
	 */
	public void setAlbum(Album album) {
		if (album != null) {
			if (album.aArtist.length() > 0) {
				aArtist.setText(album.aArtist);
				aAlbum.setText(album.aAlbum);
				aYear.setText(album.aYear);
				aComment.setText(album.aComment);
				aGenre.setSelectedIndex(MP3Genre.get().getGenres()
						.indexOf(MP3Genre.get().parseGenre(album.aGenre)));
			}
			aTime.setText(album.aTimeString);
		}
	}
}