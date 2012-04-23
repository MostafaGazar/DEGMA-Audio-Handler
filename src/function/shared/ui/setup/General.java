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

import function.shared.ui.Application;
import function.shared.ui.ComponentFactory;
import function.shared.ui.setup.BaseSetupPanel;
import function.shared.util.Pref;

import util.Constants;
import view.Messages;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Set some misc. options (lookefeel, allowempty, sleep, basedir).
 * 
 * @edited Mostafa Gazar, eng.mostafa.gazar@gmail.com
 */
public class General extends BaseSetupPanel implements ActionListener {
	private static final long serialVersionUID = 1L;

	private JComboBox<String> aThmemeChooser = null;
	private JComboBox<String> aLanguageChooser = null;
	private JTextField aBaseDirectory = null;
	private JButton aBrowseMusicDir = null;

	public General() {
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

		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

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
	}

	/**
	 * Select base music directory.
	 */
	@Override
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
	@Override
	public void save() {
		Pref.setPref(Constants.THEME,
				Constants.THEME_OPTIONS_VALUES[aThmemeChooser
						.getSelectedIndex()]);
		Pref.setPref(Constants.LANG,
				Constants.LANG_OPTIONS_VALUES[aLanguageChooser
						.getSelectedIndex()]);
		Pref.setPref(Constants.BASE_DIRECTORY_KEY, aBaseDirectory.getText());
	}
}
