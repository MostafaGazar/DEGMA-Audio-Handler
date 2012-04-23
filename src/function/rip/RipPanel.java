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
package function.rip;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

import function.rip.task.LoadCDTask;
import function.rip.task.RipperTask;
import function.rip.ui.RipTrackPanel;
import function.shared.music.Album;
import function.shared.ui.QualityChooser;
import function.shared.ui.StatusBar;

import view.Messages;
import view.NewMain;

/**
 * JRipper application class. Create main GUI stuff here.
 * 
 * @edited Mostafa Gazar, eng.mostafa.gazar@gmail.com
 */
public class RipPanel extends JPanel {
	private static final long serialVersionUID = 666L;

	public JPanel aMenuPanel = new JPanel(new BorderLayout());
	public JButton loadBt = null;
	public QualityChooser qualityCbx = null;
	public JButton convertBt = null;

	public RipTrackPanel aTrackPanel = null;
	private StatusBar aStatusBar = null;

	/**
	 * Create main application window.
	 */
	public RipPanel() {
		super();
		loadBt = new JButton(Messages.getString("Constants.loadCd"),
				new ImageIcon(NewMain.class.getResource(Messages
						.getString("Constants.browseIcon"))));

		qualityCbx = new QualityChooser();
		convertBt = new JButton(Messages.getString("Constants.rip"),
				new ImageIcon(NewMain.class.getResource(Messages
						.getString("Constants.runIcon"))));

		aTrackPanel = new RipTrackPanel();
		aStatusBar = new StatusBar();

		setLayout(new BorderLayout());

		aMenuPanel.setPreferredSize(new Dimension(100, 0));
		loadBt.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				LoadCDTask task = new LoadCDTask();
				task.doTask();
			}
		});
		convertBt.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Album album = Rip.get().getWin().getAlbum();

				if (Rip.get().getWin().getTrackPanel().getAlbumPanel()
						.copyAlbum(album)) {
					RipperTask task = new RipperTask(album, qualityCbx
							.getSelected());
					task.doTask();
				}
			}
		});

		aMenuPanel.setPreferredSize(new Dimension(145, 0));
		aMenuPanel.add(qualityCbx, BorderLayout.NORTH);
		aMenuPanel.add(convertBt, BorderLayout.CENTER);

		add(loadBt, BorderLayout.NORTH);
		add(aTrackPanel, BorderLayout.CENTER);
		add(aMenuPanel, BorderLayout.WEST);
		add(aStatusBar, BorderLayout.SOUTH);

		aStatusBar.setMessage(Messages.getString("Constants.ripStatusBar"));
	}

	/**
	 * Get current album.
	 * 
	 * @return Get album object
	 */
	public Album getAlbum() {
		return aTrackPanel.getAlbum();
	}

	/**
	 * Get jRippers statusbar.
	 * 
	 * @return The application statusbar
	 */
	public StatusBar getStatusBar() {
		return aStatusBar;
	}

	/**
	 * Get jRippers track table panel.
	 * 
	 * @return The track table panel
	 */
	public RipTrackPanel getTrackPanel() {
		return aTrackPanel;
	}

	/**
	 * Set new album to tracktable.
	 * 
	 * @param album
	 */
	public void setAlbum(Album album) {
		aTrackPanel.setAlbum(album);
	}
}
