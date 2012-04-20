package function.convert;

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


import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

import function.convert.UI.TrackPanel;
import function.convert.task.LoadDirectoryTask;
import function.convert.task.RipperTask;
import function.shared.UI.QualityChooser;
import function.shared.UI.StatusBar;
import function.shared.music.Album;



import view.Messages;
import view.NewMain;

/**
 * JRipper application class. Create main gui stuff here.
 */
public class MainWindow extends JPanel {
	private static final long serialVersionUID = 666L;
	// public MyMenuPanel aMenuPanel = null;

	public JPanel aMenuPanel = new JPanel(new BorderLayout());
	public JButton loadBt = null;
	// public JComboBox qualityCbx = null;
	public QualityChooser qualityCbx = null;
	public JButton convertBt = null;

	public TrackPanel aTrackPanel = null;
	private StatusBar aStatusBar = null;

	/**
	 * Create main application window.
	 */
	public MainWindow() {
		super();
		// aMenuPanel = new MyMenuPanel();//JPanel(new BorderLayout());
		loadBt = new JButton(Messages.getString("Constants.loadDir"),
				new ImageIcon(NewMain.class.getResource(Messages
						.getString("Constants.browseIcon"))));

		// qualityCbx =
		// ComponentFactory.createCombo(Constants.ENCODER_NAME_DEFAULTS,
		// Pref.getPref(Constants.ENCODER_KEY, Constants.ENCODER_DEFAULT),
		// "Select encoder type.", 100, 25);
		qualityCbx = new QualityChooser();
		convertBt = new JButton(Messages.getString("Constants.convert"),
				new ImageIcon(NewMain.class.getResource(Messages
						.getString("Constants.runIcon"))));

		aTrackPanel = new TrackPanel();
		aStatusBar = new StatusBar();

		setLayout(new BorderLayout());

		aMenuPanel.setPreferredSize(new Dimension(100, 0));
		loadBt.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				LoadDirectoryTask task = new LoadDirectoryTask();
				task.doTask();
			}
		});
		convertBt.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Album album = Convert.get().getWin().getAlbum();

				if (Convert.get().getWin().getTrackPanel().getAlbumPanel()
						.copyAlbum(album)) {
					RipperTask task = new RipperTask(album, qualityCbx
							.getSelected());// getSelectedIndex());
					task.doTask();
				}
			}
		});

		aMenuPanel.setPreferredSize(new Dimension(145, 0));
		aMenuPanel.add(qualityCbx, BorderLayout.NORTH);
		aMenuPanel.add(convertBt, BorderLayout.CENTER);
		// aMenuPanel.add(loadBt, BorderLayout.SOUTH);

		add(loadBt, BorderLayout.NORTH);
		add(aTrackPanel, BorderLayout.CENTER);
		add(aMenuPanel, BorderLayout.WEST);
		add(aStatusBar, BorderLayout.SOUTH);

		aStatusBar.setMessage(Messages.getString("Constants.convertStatusBar"));
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
	public TrackPanel getTrackPanel() {
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
