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

package view.panels.helpDialog;

import javax.swing.*;
import javax.swing.text.html.HTMLEditorKit;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

/**
 * A help dialog object.
 */
public class HelpDialog extends BaseDialog implements ActionListener {
	private static final long serialVersionUID = 666L;
	private JEditorPane anEditor = null;

	/**
	 * Create help dialog.
	 * 
	 * @param owner
	 *            - Set to null to create a window that can hide below
	 *            application.
	 * @param title
	 * @param close_text
	 * @param targetURL
	 *            url of target page
	 * @throws java.awt.HeadlessException
	 */
	public HelpDialog(JFrame owner, String title, String close_text,
			String targetURL) throws HeadlessException {
		super(owner, title, false);

		if (targetURL == null) {
			anEditor = new JEditorPane();
		} else {
			try {
				anEditor = new JEditorPane(targetURL);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		JButton close = createButton(close_text, "", this, 0, 0);
		JScrollPane editScrollPane = new JScrollPane(anEditor);
		JPanel mainPanel = new JPanel();

		anEditor.setEditable(false);
		// mainPanel.setLayout(ComponentFactory.createBorderLayout(5, 5));
		BorderLayout tmp = new BorderLayout();
		tmp.setHgap(5);
		tmp.setVgap(5);
		mainPanel.setLayout(tmp);
		mainPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		mainPanel.setPreferredSize(new Dimension(640, 480));
		mainPanel.setMaximumSize(new Dimension(2000, 2000));

		mainPanel.add(editScrollPane, BorderLayout.CENTER);
		mainPanel.add(close, BorderLayout.SOUTH);
		setContentPane(mainPanel);
		pack();
	}

	/**
	 * @param e
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		setVisible(false);
	}

	/**
	 * Set text in html format.
	 * 
	 * @param text
	 */
	public void setHtml(String text) {
		anEditor.setEditorKit(new HTMLEditorKit());
		anEditor.setText(text);
		anEditor.setCaretPosition(0);
	}

	/**
	 * Set plain text.
	 * 
	 * @param text
	 */
	public void setText(String text) {
		anEditor.setText(text);
	}

	private JButton createButton(String label, String tip,
			ActionListener listener, int width, int max_width) {
		String mnemonicString[] = label.split("\\&");
		String label2 = label.replaceFirst("\\&", "");
		JButton tmp = new JButton(label2);
		if (tip.length() > 0) {
			tmp.setToolTipText(tip);
		}
		tmp.setAlignmentX(Component.LEFT_ALIGNMENT);
		if (width > 0) {
			tmp.setPreferredSize(new Dimension(width, (int) tmp
					.getPreferredSize().getHeight()));
		}
		tmp.setMaximumSize(new Dimension(max_width < 1 ? Short.MAX_VALUE
				: max_width, Short.MAX_VALUE));
		if (listener != null) {
			tmp.addActionListener(listener);
		}

		if (mnemonicString.length == 2) {
			tmp.setMnemonic(mnemonicString[1].charAt(0));
		}
		return tmp;
	}
}