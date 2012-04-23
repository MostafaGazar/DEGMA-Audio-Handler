/*
    Copyright (C) 2005 - 2007 Mikael Högdahl - dronten@gmail.com

    This library is free software; you can redistribute it and/or
    modify it under the terms of the GNU Lesser BaseSetupPanel Public
    License as published by the Free Software Foundation; either
    version 2.1 of the License, or (at your option) any later version.

    This library is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
    Lesser BaseSetupPanel Public License for more details.

    You should have received a copy of the GNU Lesser BaseSetupPanel Public
    License along with this library; if not, write to the Free Software
    Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA
 */
package function.shared.ui.setup;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import function.shared.ui.ComponentFactory;
import function.shared.ui.setup.CDReader;
import function.shared.ui.setup.Encoder;
import function.shared.ui.setup.FileNames;
import function.shared.ui.setup.General;
import function.shared.ui.setup.Program;
import function.shared.ui.setup.Tag;

import util.Constants;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * A preference dialog.
 * 
 * @edited Mostafa Gazar, eng.mostafa.gazar@gmail.com
 */
public class SetupDialog implements ActionListener, ListSelectionListener {
	private JPanel aSetupPanel = null;
	private JList<String> aList = null;
	private BaseSetupPanel[] aPanels = null;
	private BaseSetupPanel aCurrentPanel = null;

	private JPanel mainPanel;

	/**
	 * Create setup dialog.
	 */
	public SetupDialog(String saveText,
			String[] choices, BaseSetupPanel[] panels) throws HeadlessException {
		// super(owner, titleText, true);
		aPanels = panels;
		JButton saveButton = ComponentFactory.createButton(saveText, "", this,
				0, 0);
		aList = new JList<String>(choices);
		JScrollPane scrollPaneList = new JScrollPane();
		mainPanel = new JPanel();
		aSetupPanel = new JPanel();

		for (BaseSetupPanel panel : aPanels) {
			panel.setVisible(false);
		}

		aList.addListSelectionListener(this);
		scrollPaneList.getViewport().setView(aList);
		aSetupPanel.setLayout(ComponentFactory.createBorderLayout(5, 5));
		mainPanel.setLayout(ComponentFactory.createBorderLayout(5, 5));
		mainPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

		mainPanel.add(scrollPaneList, BorderLayout.WEST);
		mainPanel.add(aSetupPanel, BorderLayout.CENTER);
		mainPanel.add(saveButton, BorderLayout.SOUTH);

		aList.setSelectedIndex(0);
	}

	/**
	 * Let all child panels save them self before quitting.
	 */
	@Override
	public void actionPerformed(ActionEvent actionEvent) {
		for (BaseSetupPanel panel : aPanels) {
			panel.save();
		}
	}

	@Override
	public void valueChanged(ListSelectionEvent listSelectionEvent) {
		int index = aList.getSelectedIndex();

		if (aCurrentPanel != null) {
			aCurrentPanel.setVisible(false);
			aCurrentPanel = null;
		}

		if (index < aPanels.length) {
			aCurrentPanel = aPanels[index];
			aCurrentPanel.setVisible(true);
			aSetupPanel.add(aCurrentPanel, BorderLayout.NORTH);
		}
	}

	public static void main(String[] args) {
		String[] choices = Constants.SETUP_DIALOG_TABS;
		BaseSetupPanel[] panels = { new General(), new FileNames(), new Tag(),
				new Program(), new CDReader(), new Encoder() };
		new SetupDialog("&Save", choices, panels);
	}

	/**
	 * @return the mainPanel
	 */
	public JPanel getMainPanel() {
		return mainPanel;
	}
}
