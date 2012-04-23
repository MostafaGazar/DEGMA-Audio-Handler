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
package function.rip.ui;

import javax.swing.*;

import function.shared.music.Album;
import function.shared.ui.AlbumModel;
import function.shared.ui.ComponentFactory;

import java.awt.*;

/**
 * The tack table panel with all tracks listed. Can be tracks on a cd or from a
 * directory.
 * 
 * @edited Mostafa Gazar, eng.mostafa.gazar@gmail.com
 */
public class RipTrackPanel extends JPanel {
	private static final long serialVersionUID = 666L;
	private RipAlbumPanel aAlbumPanel = null;
	private JTable aTrackTable = null;

	public RipTrackPanel() {
		super();
		aTrackTable = new JTable(new AlbumModel());
		JScrollPane scrollPane = new JScrollPane();

		aAlbumPanel = new RipAlbumPanel();

		aTrackTable.getColumnModel().getColumn(0).setMinWidth(50);
		aTrackTable.getColumnModel().getColumn(0).setPreferredWidth(100);
		aTrackTable.getColumnModel().getColumn(0).setMaxWidth(100);

		aTrackTable.getColumnModel().getColumn(1).setMinWidth(50);
		aTrackTable.getColumnModel().getColumn(1).setPreferredWidth(50);
		aTrackTable.getColumnModel().getColumn(1).setMaxWidth(100);

		aTrackTable.getColumnModel().getColumn(2).setMinWidth(100);
		aTrackTable.getColumnModel().getColumn(2).setPreferredWidth(500);
		aTrackTable.getColumnModel().getColumn(2).setMaxWidth(1000);

		aTrackTable.getColumnModel().getColumn(3).setMinWidth(50);
		aTrackTable.getColumnModel().getColumn(3).setPreferredWidth(100);
		aTrackTable.getColumnModel().getColumn(3).setMaxWidth(100);

		aTrackTable.getColumnModel().getColumn(4).setMinWidth(50);
		aTrackTable.getColumnModel().getColumn(4).setPreferredWidth(100);
		aTrackTable.getColumnModel().getColumn(4).setMaxWidth(100);

		setLayout(ComponentFactory.createBorderLayout(5, 5));
		setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

		scrollPane.getViewport().setView(aTrackTable);

		add(scrollPane, BorderLayout.CENTER);
		add(aAlbumPanel, BorderLayout.SOUTH);
	}

	/**
	 * Tell table that changes has been done to the album.
	 */
	public void fire() {
		((AlbumModel) aTrackTable.getModel()).fireTableDataChanged();
	}

	/**
	 * Return album object.
	 * 
	 * @return The current Album
	 */
	public Album getAlbum() {
		return ((AlbumModel) aTrackTable.getModel()).getAlbum();
	}

	/**
	 * Return album panel object.
	 * 
	 * @return The AlbumPanel
	 */
	public RipAlbumPanel getAlbumPanel() {
		return aAlbumPanel;
	}

	/**
	 * Set new album.
	 * 
	 * @param album
	 */
	public void setAlbum(Album album) {
		((AlbumModel) aTrackTable.getModel()).setAlbum(album);
		aAlbumPanel.setAlbum(album);
	}
}