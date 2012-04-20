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

import java.awt.*;
import java.awt.event.ActionListener;

/**
 *
 */
public class ComponentFactory {
	public static final int MAX_WIDTH = Short.MAX_VALUE;

	/**
	 * Create border layout with hgap and vgap set.
	 * 
	 * @param hgap
	 * @param vgap
	 * @return
	 */
	public static BorderLayout createBorderLayout(int hgap, int vgap) {
		BorderLayout tmp = new BorderLayout();
		tmp.setHgap(hgap);
		tmp.setVgap(vgap);
		return tmp;
	}

	/**
	 * Create button.
	 * 
	 * @param label
	 * @param tip
	 * @param listener
	 * @param width
	 * @param max_width
	 * @return
	 */
	public static JButton createButton(String label, String tip,
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

	/**
	 * Create new combo box.
	 * 
	 * @param value
	 *            - true to check the checkbox
	 * @param label
	 *            - Checkbox label string
	 * @param tip
	 *            - Tip string
	 * @param width
	 *            - Preferred width
	 * @param max_width
	 * @return
	 */
	public static JCheckBox createCheck(boolean value, String label,
			String tip, int width, int max_width) {
		JCheckBox tmp = new JCheckBox(label);
		tmp.setSelected(value);
		if (tip.length() > 0) {
			tmp.setToolTipText(tip);
		}
		tmp.setHorizontalAlignment(SwingConstants.LEFT);
		tmp.setAlignmentX(Component.LEFT_ALIGNMENT);
		if (width > 0) {
			tmp.setPreferredSize(new Dimension(width, (int) tmp
					.getPreferredSize().getHeight()));
		}
		tmp.setMaximumSize(new Dimension(max_width < 1 ? Short.MAX_VALUE
				: max_width, Short.MAX_VALUE));
		return tmp;
	}

	/**
	 * @param choices
	 * @param index
	 * @param tip
	 * @param width
	 * @param max_width
	 * @return
	 */
	public static JComboBox createCombo(String[] choices, int index,
			String tip, int width, int max_width) {
		JComboBox tmp = new JComboBox(choices);
		if (index < tmp.getItemCount()) {
			tmp.setSelectedIndex(index);
		}
		tmp.setToolTipText(tip);
		tmp.setAlignmentX(Component.LEFT_ALIGNMENT);
		if (width > 0) {
			tmp.setPreferredSize(new Dimension(width, (int) tmp
					.getPreferredSize().getHeight()));
		}
		tmp.setMaximumSize(new Dimension(max_width < 1 ? Short.MAX_VALUE
				: max_width, Short.MAX_VALUE));
		return tmp;
	}

	/**
	 * Create an editable combo box.
	 * 
	 * @param choices
	 * @param text
	 * @param tip
	 * @param width
	 * @param max_width
	 * @return
	 */
	public static JComboBox createCombo(String[] choices, String text,
			String tip, int width, int max_width) {
		JComboBox tmp = new JComboBox(choices);
		tmp.setEditable(true);
		tmp.setSelectedItem(text);
		tmp.setToolTipText(tip);
		tmp.setAlignmentX(Component.LEFT_ALIGNMENT);
		if (width > 0) {
			tmp.setPreferredSize(new Dimension(width, (int) tmp
					.getPreferredSize().getHeight()));
		}
		tmp.setMaximumSize(new Dimension(max_width < 1 ? Short.MAX_VALUE
				: max_width, Short.MAX_VALUE));
		return tmp;
	}

	/**
	 * @param choices
	 * @param choices2
	 * @param tip
	 * @param width
	 * @param max_width
	 * @return
	 */
	public static JComboBox createCombo(String[] choices, String[] choices2,
			String index, String tip, int width, int max_width) {
		JComboBox tmp = new JComboBox(choices);

		int pos = 0;
		int count = 0;
		for (String s : choices2) {
			if (s.equalsIgnoreCase(index)) {
				pos = count;
				break;
			}
			count++;
		}
		if (pos < tmp.getItemCount()) {
			tmp.setSelectedIndex(pos);
		}
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
		return tmp;
	}

	/**
	 * @param model
	 * @param index
	 * @param tip
	 * @param width
	 * @param max_width
	 * @return
	 */
	public static JComboBox createCombo(DefaultComboBoxModel model, int index,
			String tip, int width, int max_width) {
		JComboBox tmp = new JComboBox(model);
		if (index < tmp.getItemCount()) {
			tmp.setSelectedIndex(index);
		}
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
		return tmp;
	}

	/**
	 * @param obj1
	 * @param obj2
	 * @param obj3
	 * @param obj4
	 * @return
	 */
	public static JPanel createFourPanel(Component obj1, Component obj2,
			Component obj3, Component obj4) {
		JPanel tmp = new JPanel();
		tmp.setLayout(new BoxLayout(tmp, BoxLayout.X_AXIS));
		obj1.setPreferredSize(new Dimension(tmp.getWidth() / 5, 20));
		obj2.setPreferredSize(new Dimension(tmp.getWidth() / 5, 20));
		obj3.setPreferredSize(new Dimension(tmp.getWidth() / 5, 20));
		obj4.setPreferredSize(new Dimension(tmp.getWidth() / 5, 20));
		tmp.add(obj1);
		tmp.add(Box.createRigidArea(new Dimension(5, 0)));
		tmp.add(obj2);
		tmp.add(Box.createRigidArea(new Dimension(5, 0)));
		tmp.add(obj3);
		tmp.add(Box.createRigidArea(new Dimension(5, 0)));
		tmp.add(obj4);
		return tmp;
	}

	/**
	 * Create text edit box.
	 * 
	 * @param value
	 * @param tip
	 * @param width
	 * @param max_width
	 * @return
	 */
	public static JTextField createInput(String value, String tip, int width,
			int max_width) {
		JTextField tmp = new JTextField();
		tmp.setText(value);
		if (tip.length() > 0) {
			tmp.setToolTipText(tip);
		}
		tmp.setAlignmentX(Component.LEFT_ALIGNMENT);
		if (width > 0) {
			tmp.setPreferredSize(new Dimension(width, (int) tmp
					.getPreferredSize().getHeight()));
		}
		tmp.setMaximumSize(new Dimension(max_width < 1 ? Short.MAX_VALUE
				: max_width, (int) tmp.getPreferredSize().getHeight()));
		return tmp;
	}

	/**
	 * Create text edit box.
	 * 
	 * @param value
	 * @param tip
	 * @param listener
	 * @param width
	 * @param max_width
	 * @return
	 */
	public static JTextField createInput(String value, String tip,
			ActionListener listener, int width, int max_width) {
		JTextField tmp = createInput(value, tip, width, max_width);
		if (listener != null) {
			tmp.addActionListener(listener);
		}
		return tmp;
	}

	/**
	 * Create new label.
	 * 
	 * @param label
	 * @param tip
	 * @param width
	 * @param max_width
	 * @return
	 */
	public static JLabel createLabel(String label, String tip, int width,
			int max_width) {
		JLabel tmp = new JLabel(label);
		if (tip.length() > 0) {
			tmp.setToolTipText(tip);
		}
		// tmp.setAlignmentX(Component.LEFT_ALIGNMENT);
		if (width > 0) {
			tmp.setPreferredSize(new Dimension(width, (int) tmp
					.getPreferredSize().getHeight()));
		}
		tmp.setMaximumSize(new Dimension(max_width < 1 ? Short.MAX_VALUE
				: max_width, Short.MAX_VALUE));
		return tmp;
	}

	/**
	 * @param object
	 * @return
	 */
	public static JPanel createOnePanel(Component object) {
		JPanel tmp = new JPanel();
		tmp.setLayout(ComponentFactory.createBorderLayout(5, 5));
		tmp.add(object, BorderLayout.CENTER);
		return tmp;
	}

	/**
	 * @param leftObject
	 * @param centerObject
	 * @param rightObject
	 * @return
	 */
	public static JPanel createThreePanel(Component leftObject,
			Component centerObject, Component rightObject) {
		JPanel tmp = new JPanel();
		tmp.setLayout(ComponentFactory.createBorderLayout(5, 5));
		tmp.add(leftObject, BorderLayout.WEST);
		tmp.add(centerObject, BorderLayout.CENTER);
		tmp.add(rightObject, BorderLayout.EAST);
		return tmp;
	}

	/**
	 * @param leftObject
	 * @param centerObject
	 * @return
	 */
	public static JPanel createTwoPanel(Component leftObject,
			Component centerObject) {
		JPanel tmp = new JPanel();
		tmp.setLayout(ComponentFactory.createBorderLayout(5, 5));
		tmp.add(leftObject, BorderLayout.WEST);
		tmp.add(centerObject, BorderLayout.CENTER);
		return tmp;
	}

	/**
	 * @param leftObject
	 * @param centerObject
	 * @return
	 */
	public static JPanel createTwoPanelEq(Component leftObject,
			Component centerObject) {
		JPanel tmp = new JPanel();
		tmp.setLayout(ComponentFactory.createBorderLayout(5, 5));
		tmp.add(leftObject, BorderLayout.WEST);
		tmp.add(centerObject, BorderLayout.EAST);
		return tmp;
	}
}
