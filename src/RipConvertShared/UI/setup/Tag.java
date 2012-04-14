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

/**
 * Set music title tag option.
 */
public class Tag extends BaseSetupPanel {
	private static final long serialVersionUID = 1L;

	private JComboBox aTitelTagChoices = null;
	private JCheckBox aRemoveNonLetters = null;

	/**
     *
     */
	public Tag() {
		aTitelTagChoices = ComponentFactory.createCombo(
				Constants.TITLE_OPTIONS,
				Pref.getPref(Constants.TITLE_KEY, Constants.TITLE_DEFAULT),
				"Set how title tag will be named.", 0, 0);
		aRemoveNonLetters = ComponentFactory
				.createCheck(
						Pref.getPref(Constants.TITLE_REMOVEPREFIX_KEY,
								Constants.TITLE_REMOVEPREFIX_DEFAULT),
						"Remove Prefix Number For Files",
						"<html>Check to remove all none letters first in the filename for the title tag when importing wav files.</html>",
						0, 0);

		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		add(ComponentFactory.createTwoPanel(new JLabel("Title Tag"),
				aTitelTagChoices));
		add(Box.createRigidArea(new Dimension(0, 5)));
		add(ComponentFactory.createOnePanel(aRemoveNonLetters));
	}

	/**
	 * Save settings.
	 */
	public void save() {
		Pref.setPref(Constants.TITLE_KEY, aTitelTagChoices.getSelectedIndex());
		Pref.setPref(Constants.TITLE_REMOVEPREFIX_KEY,
				aRemoveNonLetters.isSelected());
	}

}
