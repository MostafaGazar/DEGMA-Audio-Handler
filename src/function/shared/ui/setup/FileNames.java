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

package function.shared.ui.setup;


import javax.swing.*;

import function.shared.ui.ComponentFactory;
import function.shared.ui.setup.BaseSetupPanel;
import function.shared.util.Pref;


import util.Constants;

import java.awt.*;

/**
 * Set directory and file creation options (directorytype, filenametype,
 * prefixtype, prefixsep, useprefix).
 */
public class FileNames extends BaseSetupPanel {
	private static final long serialVersionUID = 1L;

	private JComboBox aDirChoices = null;
	private JComboBox aFileChoices = null;
	private JTextField aTrackSep = null;
	private JComboBox aTrackNo = null;
	private JCheckBox aPrefix = null;

	/**
     *
     */
	public FileNames() {
		aDirChoices = ComponentFactory
				.createCombo(
						Constants.DIRECTORY_OPTIONS,
						Pref.getPref(Constants.DIRECTORY_KEY,
								Constants.DIRECTORY_DEFAULT),
						"<html>Set the folder structure for the tracks.<br><b>If you are doing a whole album conversion this setting will not apply.</b><br>A whole album conversion will end up in base/Artist/albumname.extension.</html>",
						0, 0);
		aFileChoices = ComponentFactory
				.createCombo(Constants.FILENAME_OPTIONS, Pref.getPref(
						Constants.FILENAME_KEY, Constants.FILENAME_DEFAULT),
						"Set the format of the filename.", 0, 0);
		aTrackNo = ComponentFactory.createCombo(Constants.FILENUMBER_OPTIONS,
				Pref.getPref(Constants.FILENUMBER_KEY,
						Constants.FILENUMBER_DEFAULT),
				"Set the format of the number prefix for the filename.", 0, 0);
		aTrackSep = ComponentFactory
				.createInput(
						Pref.getPref(Constants.FILENUMBER_SEPERATOR_KEY,
								Constants.FILENUMBER_SEPERATOR_DEFAULT),
						"Set the text to insert between track number and the rest off the filename.",
						0, 0);
		aPrefix = ComponentFactory.createCheck(
				Pref.getPref(Constants.FILENUMBER_USE_KEY,
						Constants.FILENUMBER_USE_DEFAULT),
				"Insert Number Prefix",
				"Check to insert a number prefix before the filename.", 0, 0);

		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		add(ComponentFactory.createTwoPanel(new JLabel("Directory Layout"),
				aDirChoices));
		add(Box.createRigidArea(new Dimension(0, 5)));
		add(ComponentFactory.createTwoPanel(new JLabel("Track Filename"),
				aFileChoices));
		add(Box.createRigidArea(new Dimension(0, 5)));
		add(ComponentFactory.createTwoPanel(new JLabel("Track Number"),
				aTrackNo));
		add(Box.createRigidArea(new Dimension(0, 5)));
		add(ComponentFactory.createTwoPanel(
				new JLabel("Track Number Separator"), aTrackSep));
		add(Box.createRigidArea(new Dimension(0, 5)));
		add(ComponentFactory.createOnePanel(aPrefix));
	}

	/**
	 * Save settings.
	 */
	@Override
	public void save() {
		Pref.setPref(Constants.DIRECTORY_KEY, aDirChoices.getSelectedIndex());
		Pref.setPref(Constants.FILENAME_KEY, aFileChoices.getSelectedIndex());
		Pref.setPref(Constants.FILENUMBER_KEY, aTrackNo.getSelectedIndex());
		Pref.setPref(Constants.FILENUMBER_SEPERATOR_KEY, aTrackSep.getText());
		Pref.setPref(Constants.FILENUMBER_USE_KEY, aPrefix.isSelected());
	}

}
