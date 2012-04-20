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

import java.awt.*;

/**
 * Base dialog object. Implements common routines.
 */
public abstract class BaseDialog extends JDialog {
	private static final long serialVersionUID = 1L;

	/**
	 * Create dialog.
	 * 
	 * @param owner
	 *            - Set to null to create a window that can hide below
	 *            application.
	 * @param title
	 * @throws java.awt.HeadlessException
	 */
	public BaseDialog(JFrame owner, String title, boolean modal)
			throws HeadlessException {
		super(owner, title, modal);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);

		addWindowListener(new java.awt.event.WindowAdapter() {
			@Override
			public void windowClosing(java.awt.event.WindowEvent e) {
				close();
				setVisible(false);
				dispose();
			}
		});

		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
	}

	/**
	 * Center dialog on application window.
	 */
	/*
	 * public void centerOnApplication() { Point winPos =
	 * Application.get().getLocation(); Dimension winSize =
	 * Application.get().getSize(); Dimension dlgSize = getSize();
	 * 
	 * double x = winPos.getX() + (winSize.width / 2); double y = winPos.getY()
	 * + (winSize.height / 2);
	 * 
	 * x -= (dlgSize.width / 2); y -= (dlgSize.height / 2); setLocation(new
	 * Point((int) x, (int) y)); }
	 */

	/**
	 * Center dialog on screen.
	 */
	public void centerOnScreen() {
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		Dimension dlgSize = getSize();
		Point newPos = new Point((screenSize.width / 2) - (dlgSize.width / 2),
				(screenSize.height / 2) - (dlgSize.height / 2));
		setLocation(newPos);
	}

	/**
     *
     */
	public void close() {
	}

	;

	/**
	 * Center dialog on parent window.
	 */
	public void leftOnParent() {
		Point windowPos = this.getOwner().getLocation();
		setLocation(windowPos);
	}
}