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
package function.shared.ui;

import javax.swing.*;
import javax.swing.text.html.HTMLEditorKit;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * A help dialog object.
 */
public class HelpDialog extends BaseDialog implements ActionListener {
	private static final long serialVersionUID = 666L;
	private JEditorPane aEditor = null;

	/**
	 * Create help dialog.
	 * 
	 * @param owner
	 *            - Set to null to create a window that can hide below
	 *            application.
	 * @param title
	 * @param close_text
	 * @throws java.awt.HeadlessException
	 */
	public HelpDialog(JFrame owner, String title, String close_text)
			throws HeadlessException {
		super(owner, title, false);

		aEditor = new JEditorPane();
		JButton close = ComponentFactory.createButton(close_text, "", this, 0,
				0);
		JScrollPane editScrollPane = new JScrollPane(aEditor);
		JPanel mainPanel = new JPanel();

		aEditor.setEditable(false);
		mainPanel.setLayout(ComponentFactory.createBorderLayout(5, 5));
		mainPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		mainPanel.setPreferredSize(new Dimension(640, 480));
		mainPanel.setMaximumSize(new Dimension(2000, 2000));

		mainPanel.add(editScrollPane, BorderLayout.CENTER);
		mainPanel.add(close, BorderLayout.SOUTH);
		setContentPane(mainPanel);
		pack();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		setVisible(false);
	}

	/**
	 * Set text in html format.
	 */
	public void setHtml(String text) {
		aEditor.setEditorKit(new HTMLEditorKit());
		aEditor.setText(text);
		aEditor.setCaretPosition(0);
	}

	/**
	 * Set plain text.
	 */
	public void setText(String text) {
		aEditor.setText(text);
	}
}