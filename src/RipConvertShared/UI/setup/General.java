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

package RipConvertShared.UI.setup;

import RipConvertShared.UI.Application;
import RipConvertShared.UI.ComponentFactory;
import RipConvertShared.UI.setup.BaseSetupPanel;
import RipConvertShared.util.Pref;
import UI.Messages;
import Util.Constants;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Set some misc. options (lookefeel, allowempty, sleep, basedir).
 */
public class General extends BaseSetupPanel implements ActionListener {
	private static final long serialVersionUID = 1L;

	// private JComboBox aLookAndFeel = null;
	// private JComboBox aSleep = null;
	// private JCheckBox aEmptyAlbumData = null;
	// private JCheckBox aDumpCueSheet = null;
	// private String aCurrentLF;
	private JComboBox aThmemeChooser = null;
	private JComboBox aLanguageChooser = null;
	private JTextField aBaseDirectory = null;
	private JButton aBrowseMusicDir = null;

	// private JTextField aCodepage = null;

	/**
     *
     */
	public General() {
		// aCurrentLF = Pref.getPref(JRipper.LOOKFEEL, "");
		// aLookAndFeel =
		// ComponentFactory.createCombo(Constants.SETUP_LF_OPTIONS,
		// Constants.SETUP_LF_CLASSES, aCurrentLF,
		// "<html>Set look and feel.<br>Not all work on all OS and not all libraries might be available.<br>Restart program for best result.</html>",
		// 0, 0);
		// aLookAndFeel.setSelectedIndex(2);
		// aLookAndFeel.setEnabled(false);
		// aEmptyAlbumData =
		// ComponentFactory.createCheck(Pref.getPref(Constants.ALBUM_DATA_KEY,
		// Constants.ALBUM_DATA_DEFAULT), "Allow Encoding Without Album Data",
		// "Check to allow encoding tracks without album tags.", 0, 0);
		// aDumpCueSheet =
		// ComponentFactory.createCheck(Pref.getPref(Constants.CUE_SHEET_KEY,
		// Constants.CUE_SHEET_DEFAULT),
		// "Create cue sheet when doing an complete album conversion",
		// "<html>Create cue sheet when converting a complete CD into single track.<br>Cue sheet will end up in artist directory with \"album name\".cue as the filename.</html>",
		// 0, 0);
		// aSleep = ComponentFactory.createCombo(Constants.SLEEP_OPTIONS,
		// Pref.getPref(Constants.SLEEP_KEY, Constants.SLEEP_DEFAULT),
		// "<html>Set number of milliseconds to sleep between every buffer write.<br>This is to keep processor load down,<br>and the fan sound in laptops.<br><b>But try to lower cd reading speed first</b></html>",
		// 0, 0);
		aThmemeChooser = ComponentFactory.createCombo(Constants.THEME_OPTIONS,
				Pref.getPref(Constants.THEME, Constants.THEME_DEFAULT),
				Messages.getString("Setting.themeChooserTooltip"), 0, 0);
		aLanguageChooser = ComponentFactory.createCombo(Constants.LANG_OPTIONS,
				Pref.getPref(Constants.LANG, Constants.LANG_DEFAULT),
				Messages.getString("Setting.languageChooserTooltip"), 0, 0);
		aBaseDirectory = ComponentFactory.createInput(
				Pref.getPref(Constants.BASE_DIRECTORY_KEY, ""),
				"Select root folder for the encoded tracks.", 0, 0);
		aBrowseMusicDir = ComponentFactory.createButton("&>>",
				"Select directory...", this, 0, 0);
		// aCodepage =
		// ComponentFactory.createInput(Pref.getPref(Application.CODEPAGE, ""),
		// "<html>Set character codepage for mp3 tags.<br>If characters are looking strange you might have mp3 tags in a different codepage.<br><br><b>Changing this perhaps make things better. Or maybe not.</b></html>",
		// 0, 0);

		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

		// add(ComponentFactory.createTwoPanel(new JLabel("Set Theme"),
		// aLookAndFeel));
		// add(Box.createRigidArea(new Dimension(0, 5)));
		// add(ComponentFactory.createOnePanel(aEmptyAlbumData));
		// add(Box.createRigidArea(new Dimension(0, 5)));
		// add(ComponentFactory.createOnePanel(aDumpCueSheet));
		// add(Box.createRigidArea(new Dimension(0, 5)));
		// add(ComponentFactory.createTwoPanel(new JLabel("Sleep"), aSleep));
		// add(Box.createRigidArea(new Dimension(0, 5)));
		add(ComponentFactory.createTwoPanel(
				new JLabel(Messages.getString("Setting.language")),
				aLanguageChooser));
		add(Box.createRigidArea(new Dimension(0, 5)));
		add(ComponentFactory
				.createTwoPanel(
						new JLabel(Messages.getString("Setting.theme")),
						aThmemeChooser));
		add(Box.createRigidArea(new Dimension(0, 5)));
		add(Box.createRigidArea(new Dimension(0, 5)));
		add(ComponentFactory.createThreePanel(
				new JLabel(Messages.getString("Setting.musicFolder")),
				aBaseDirectory, aBrowseMusicDir));
		add(Box.createRigidArea(new Dimension(0, 5)));
		// add(ComponentFactory.createTwoPanel(new JLabel("Character Codepage"),
		// aCodepage));
	}

	/**
	 * Select base music directory.
	 * 
	 * @param actionEvent
	 */
	public void actionPerformed(ActionEvent actionEvent) {
		if (actionEvent.getSource() == aBrowseMusicDir) {
			JFileChooser chooser = new JFileChooser();

			chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
			chooser.setDialogTitle("Select Directory");
			if (chooser.showOpenDialog(Application.get()) == JFileChooser.APPROVE_OPTION) {
				aBaseDirectory.setText(chooser.getSelectedFile().getPath());
			}
		}
	}

	/**
	 * Save settings.
	 */
	public void save() {
		// String[] lf = Constants.SETUP_LF_CLASSES;

		// Pref.setPref(JRipper.LOOKFEEL, lf[aLookAndFeel.getSelectedIndex()]);
		// Pref.setPref(Constants.ALBUM_DATA_KEY, aEmptyAlbumData.isSelected());
		// Pref.setPref(Constants.CUE_SHEET_KEY, aDumpCueSheet.isSelected());
		// Pref.setPref(Constants.SLEEP_KEY, aSleep.getSelectedIndex());

		Pref.setPref(Constants.THEME,
				Constants.THEME_OPTIONS_VALUES[aThmemeChooser
						.getSelectedIndex()]);
		Pref.setPref(Constants.LANG,
				Constants.LANG_OPTIONS_VALUES[aLanguageChooser
						.getSelectedIndex()]);
		Pref.setPref(Constants.BASE_DIRECTORY_KEY, aBaseDirectory.getText());
		// JRipper.get().setCodePage(aCodepage.getText());

		/*
		 * if
		 * (!aCurrentLF.equalsIgnoreCase(lf[aLookAndFeel.getSelectedIndex()])) {
		 * Application.get().setLookAndFeel(true); }
		 */
	}
}
