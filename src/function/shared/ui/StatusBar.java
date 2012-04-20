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
import javax.swing.border.BevelBorder;
import java.awt.*;
import java.util.Calendar;

/**
 * Status bar object.
 */
public class StatusBar extends JPanel {
	private static final long serialVersionUID = 666L;
	public static int FLASH_TIME = 500;
	public static int FLASH_TIMES = 6;
	public static Color FLASH_COLOR = Color.LIGHT_GRAY;
	public static Color FLASH_ERROR_COLOR = Color.RED;
	public static Color FLASH_NOTIFY_COLOR = Color.YELLOW;
	public static boolean FLASH_TURNED_ON = true;

	private JLabel aMessage = null;
	private Boolean aFlashRun = false;

	private static final int MESSAGE = 0;
	private static final int NOTIFY = 1;
	private static final int ERROR = 2;

	/**
     *
     */
	public StatusBar() {
		super();
		aMessage = new JLabel();

		setPreferredSize(new Dimension(Short.MAX_VALUE, 20));
		setLayout(ComponentFactory.createBorderLayout(5, 0));
		setBorder(BorderFactory.createEtchedBorder(BevelBorder.LOWERED));

		add(aMessage, BorderLayout.CENTER);
	}

	/**
	 * Set status bar text.
	 * 
	 * @param message
	 *            - Either resource key or raw text
	 * @param type
	 *            - MESAGE, NOTIFY or ERROR
	 * @param additional
	 *            - Additional messages
	 */
	private void setMessage(String message, int type, String[] additional) {
		for (String msg : additional) {
			message += " " + msg;
		}

		if (type == MESSAGE) {
			aMessage.setText(message);
		} else if (type == NOTIFY) {
			aMessage.setText(message);

			if (FLASH_TURNED_ON) {
				FLASH_COLOR = FLASH_NOTIFY_COLOR;
				new FlashGordon().start();
			}
		} else if (type == ERROR) {
			aMessage.setText(message);
			Toolkit.getDefaultToolkit().beep();

			if (FLASH_TURNED_ON) {
				FLASH_COLOR = FLASH_ERROR_COLOR;
				new FlashGordon().start();
			}
		}

	}

	/**
	 * Set status message.
	 * 
	 * @param message
	 * @param additional
	 */
	public void setMessage(String message, String... additional) {
		setMessage(message, MESSAGE, additional);
	}

	/**
	 * Set error message.
	 * 
	 * @param message
	 * @param additional
	 */
	public void setErrorMessage(String message, String... additional) {
		setMessage(message, ERROR, additional);
	}

	/**
	 * Set notify message.
	 * 
	 * @param message
	 * @param additional
	 */
	public void setNotifyMessage(String message, String... additional) {
		setMessage(message, NOTIFY, additional);
	}

	/**
	 * A thread that redraws background of status panel in alternative colors
	 * for some time.
	 */
	class FlashGordon extends Thread {
		@Override
		public void run() {
			synchronized (aFlashRun) {
				if (aFlashRun) {
					return;
				}
				aFlashRun = true;
			}

			long start = Calendar.getInstance().getTimeInMillis();
			Color bg = getBackground();

			while (true) {
				setBackground(FLASH_COLOR);

				try {
					Thread.sleep(FLASH_TIME);
				} catch (InterruptedException e) {
				}

				setBackground(bg);

				try {
					Thread.sleep(FLASH_TIME);
				} catch (InterruptedException e) {
				}

				if ((Calendar.getInstance().getTimeInMillis() - start) > (FLASH_TIME * FLASH_TIMES)) {
					break;
				}
			}

			synchronized (aFlashRun) {
				aFlashRun = false;
			}
		}
	}

	public static void main(String[] args) {
		JFrame frm = new JFrame();
		frm.setLayout(new BorderLayout());
		StatusBar st = new StatusBar();
		frm.add(st, BorderLayout.SOUTH);
		frm.setSize(400, 200);
		frm.setVisible(true);
		// st.setMessage("Load a CD or tracks from a directory, then convert selected tracks to mp3/ogg/m4a/flac/wav files");
		// st.setErrorMessage("Load a CD or tracks from a directory, then convert selected tracks to mp3/ogg/m4a/flac/wav files");
		st.setNotifyMessage("Load a CD or tracks from a directory, then convert selected tracks to mp3/ogg/m4a/flac/wav files");
	}
}
