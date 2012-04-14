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

import RipConvertShared.UI.ComponentFactory;
import RipConvertShared.UI.setup.BaseSetupPanel;
import RipConvertShared.util.Pref;
import Util.Constants;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Set encoder options. Dump to wav files and decoding has no options.
 */
public class Encoder extends BaseSetupPanel implements ActionListener {
	private static final long serialVersionUID = 1L;

	private JComboBox aDefault = null;
	private JTextField aEncoderName[];
	private JTextField aEncoderParam[];
	private JButton aResetButton[];

	/**
     *
     */
	public Encoder() {
		aScroll = true;

		aEncoderParam = new JTextField[Constants.ENCODER_INDEX_WAV];
		aEncoderName = new JTextField[Constants.ENCODER_INDEX_WAV];
		aResetButton = new JButton[Constants.ENCODER_INDEX_WAV];
		String[] names = new String[Constants.ENCODER_INDEX_WAV];
		for (int f = 0; f < Constants.ENCODER_INDEX_WAV; f++) {
			names[f] = Pref.getPref(Constants.ENCODER_NAME_KEYS[f],
					Constants.ENCODER_NAME_DEFAULTS[f]);
		}

		aDefault = ComponentFactory.createCombo(names,
				Pref.getPref(Constants.ENCODER_KEY, Constants.ENCODER_DEFAULT),
				"Select default encoder in the main window.", 0, 0);

		for (int f = 0; f < Constants.ENCODER_INDEX_WAV; f++) {
			aEncoderName[f] = ComponentFactory.createInput(
					Pref.getPref(Constants.ENCODER_NAME_KEYS[f], names[f]),
					"Set name of the encoder profile.", 0, 0);
			aEncoderName[f].setPreferredSize(new Dimension(150, 20));
			aEncoderParam[f] = ComponentFactory.createInput(Pref.getPref(
					Constants.ENCODER_PARAM_KEYS[f],
					Constants.ENCODER_PARAM_DEFAULTS[f]),
					"Set encoder quality parameter string", 0, 0);

			if (f < Constants.ENCODER_INDEX_OGG) {
				aResetButton[f] = ComponentFactory
						.createButton(
								"Reset MP3",
								"Reset encoder parameter and name to its default value.",
								this, 0, 0);
			} else if (f < Constants.ENCODER_INDEX_AAC) {
				aResetButton[f] = ComponentFactory
						.createButton(
								"Reset Ogg",
								"Reset encoder parameter and name to its default value.",
								this, 0, 0);
			} else if (f < Constants.ENCODER_INDEX_FLAC) {
				aResetButton[f] = ComponentFactory
						.createButton(
								"Reset AAC",
								"Reset encoder parameter and name to its default value.",
								this, 0, 0);
			} else {
				aResetButton[f] = ComponentFactory
						.createButton(
								"Reset Flac",
								"Reset encoder parameter and name to its default value.",
								this, 0, 0);
			}

		}

		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

		add(ComponentFactory.createTwoPanel(new JLabel("Default Encoder"),
				aDefault));
		add(Box.createRigidArea(new Dimension(0, 10)));

		for (int f = 0; f < Constants.ENCODER_INDEX_WAV; f++) {
			add(ComponentFactory.createThreePanel(aEncoderName[f],
					aEncoderParam[f], aResetButton[f]));
			add(Box.createRigidArea(new Dimension(0, 5)));
		}
	}

	/**
	 * Restore default name and parameters if button is pressed.
	 * 
	 * @param actionEvent
	 */
	public void actionPerformed(ActionEvent actionEvent) {
		int index = 0;
		for (JButton button : aResetButton) {
			if (button == actionEvent.getSource()) {
				aEncoderName[index]
						.setText(Constants.ENCODER_NAME_DEFAULTS[index]);
				aEncoderParam[index]
						.setText(Constants.ENCODER_PARAM_DEFAULTS[index]);
				return;
			}
			index++;
		}
	}

	/**
	 * Save settings.
	 */
	public void save() {
		Pref.setPref(Constants.ENCODER_KEY, aDefault.getSelectedIndex());
		for (int f = 0; f < Constants.ENCODER_INDEX_WAV; f++) {
			Pref.setPref(Constants.ENCODER_NAME_KEYS[f],
					aEncoderName[f].getText());
			Pref.setPref(Constants.ENCODER_PARAM_KEYS[f],
					aEncoderParam[f].getText());
		}
	}

}
