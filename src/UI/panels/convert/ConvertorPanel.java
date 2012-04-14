package UI.panels.convert;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

import controller.JAVAconvert.MP3_To_WAV.MP3_To_WAV;
import controller.JAVAconvert.WAV_To_MP3.WAV_To_MP3;

import javazoom.jl.decoder.JavaLayerException;

import UI.WaitingDialog;
import UI.Messages;
import UI.fileChooser.AudioVideoFilter;
import UI.fileChooser.ImageFileView;
import Util.ConstantMethods;

/**
 * Just convert
 * 
 * @author Mostafa Gazar
 * @start 11-10-2007
 */
public class ConvertorPanel {
	private JFrame frame;
	private JPanel panel;

	/**
	 * fc is a file chooser allows you to change the current image of the
	 * product
	 */
	private JFileChooser fc;

	public ConvertorPanel(JFrame parent) {
		this.frame = parent;
		panel = new JPanel();

		JLabel fromLb;
		JLabel toLb;

		final JRadioButton mp3_1;
		final JRadioButton wav_1;
		final JRadioButton midi_1;
		final JRadioButton amr_1;
		final JRadioButton mp3_2;
		final JRadioButton wav_2;
		final JRadioButton midi_2;
		final JRadioButton amr_2;
		JButton convertBt;
		JButton advancedConvertBt;

		// construct components
		fromLb = new JLabel(Messages.getString("Constants.from")); //$NON-NLS-1$
		toLb = new JLabel(Messages.getString("Constants.to")); //$NON-NLS-1$

		ButtonGroup bg1 = new ButtonGroup();
		mp3_1 = new JRadioButton(Messages.getString("Constants.mp3")); //$NON-NLS-1$
		wav_1 = new JRadioButton(Messages.getString("Constants.wav")); //$NON-NLS-1$
		midi_1 = new JRadioButton(Messages.getString("Constants.midi")); //$NON-NLS-1$
		amr_1 = new JRadioButton(Messages.getString("Constants.amr")); //$NON-NLS-1$
		bg1.add(mp3_1);
		bg1.add(wav_1);
		bg1.add(midi_1);
		bg1.add(amr_1);

		ButtonGroup bg2 = new ButtonGroup();
		mp3_2 = new JRadioButton(Messages.getString("Constants.mp3"));
		wav_2 = new JRadioButton(Messages.getString("Constants.wav"));
		midi_2 = new JRadioButton(Messages.getString("Constants.midi"));
		amr_2 = new JRadioButton(Messages.getString("Constants.amr"));
		bg2.add(mp3_2);
		bg2.add(wav_2);
		bg2.add(midi_2);
		bg2.add(amr_2);

		advancedConvertBt = new JButton(
				Messages.getString("Constants.openAdvancedConverting")); //$NON-NLS-1$
		advancedConvertBt.setToolTipText(Messages
				.getString("Constants.advancedConvertToolTip"));
		advancedConvertBt.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// openRip();
			}
		});
		convertBt = new JButton(Messages.getString("Constants.convert")); //$NON-NLS-1$
		convertBt.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (mp3_1.isSelected() && mp3_2.isSelected()
						|| wav_1.isSelected() && wav_2.isSelected()
						|| midi_1.isSelected() && midi_2.isSelected()
						|| amr_1.isSelected() && amr_2.isSelected()) {
					JOptionPane.showMessageDialog(
							frame,
							Messages.getString("Constants.error_TheInputTypeMustBeDiffrentFromTheOutputType"), Messages.getString("Constants.title_SorryButProcessCanNotCompleted"), //$NON-NLS-1$
							JOptionPane.ERROR_MESSAGE); //$NON-NLS-1$
				}
				// midi to mp3 not supported
				if (midi_1.isSelected() && mp3_2.isSelected()) {
					JOptionPane.showMessageDialog(
							frame,
							Messages.getString("Constants.error_NotSupportedConversion"), Messages.getString("Constants.title_SorryButProcessCanNotCompleted"), //$NON-NLS-1$
							JOptionPane.ERROR_MESSAGE); //$NON-NLS-1$
				}
				// mp3 to wav
				else if (mp3_1.isSelected() && wav_2.isSelected()) {
					setPathes("1"); //$NON-NLS-1$
				}
				// wav to mp3
				else if (wav_1.isSelected() && mp3_2.isSelected()) {
					setPathes("2"); //$NON-NLS-1$
				}
				// midi to wav
				else if (midi_1.isSelected() && wav_2.isSelected()) {
					setPathes("3"); //$NON-NLS-1$
				}
			}
		});

		// set components properties
		// midi_1.setEnabled (false);
		amr_1.setEnabled(false);
		midi_2.setEnabled(false);
		amr_2.setEnabled(false);

		// adjust size and set layout
		// setPreferredSize (new Dimension (384, 262));
		panel.setLayout(null);

		// add components
		panel.add(mp3_1);
		panel.add(fromLb);
		panel.add(toLb);
		panel.add(wav_1);
		panel.add(midi_1);
		panel.add(amr_1);
		panel.add(mp3_2);
		panel.add(wav_2);
		panel.add(midi_2);
		panel.add(amr_2);
		panel.add(convertBt);
		panel.add(advancedConvertBt);

		// set component bounds (only needed by Absolute Positioning)
		mp3_1.setBounds(20, 40, 100, 25);
		fromLb.setBounds(25, 10, 100, 25);
		toLb.setBounds(190, 10, 100, 25);
		wav_1.setBounds(20, 70, 100, 25);
		midi_1.setBounds(20, 100, 100, 25);
		amr_1.setBounds(20, 130, 100, 25);
		mp3_2.setBounds(185, 40, 100, 25);
		wav_2.setBounds(185, 70, 100, 25);
		midi_2.setBounds(185, 100, 100, 25);
		amr_2.setBounds(185, 130, 100, 25);
		convertBt.setBounds(25, 170, 215, 30);
		advancedConvertBt.setBounds(25, 210, 215, 30);
	}

	public void setPathes(String type) {
		String source, dest = null;
		// Set up the file chooser.
		if (fc == null) {
			fc = new JFileChooser();

			// Add a custom file filter and disable the default
			fc.addChoosableFileFilter(new AudioVideoFilter());
			fc.setAcceptAllFileFilterUsed(false);
			// Add custom icons for file types.
			fc.setFileView(new ImageFileView());
		}

		// Show it.
		int returnVal = fc.showDialog(frame,
				Messages.getString("Constants.accept"));

		// Process the results.
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			File file = fc.getSelectedFile();
			source = file.getParent() + "\\" + file.getName(); //$NON-NLS-1$
		} else {
			source = null;
		}
		// Reset the file chooser for the next time it's shown.
		fc.setSelectedFile(null);
		/************************************************************/
		// Set up the file chooser.
		if ("3".equals(type.toString())) {
			// new AudioRecoderUIFrame(source,null);
			return;
		}
		if (fc == null) {
			fc = new JFileChooser();

			// Add a custom file filter and disable the default
			fc.addChoosableFileFilter(new AudioVideoFilter());
			fc.setAcceptAllFileFilterUsed(false);
			// Add custom icons for file types.
			fc.setFileView(new ImageFileView());
		}
		// Show it.
		returnVal = fc
				.showDialog(frame, Messages.getString("Constants.accept"));

		// Process the results.
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			File file = fc.getSelectedFile();
			dest = file.getParent() + "\\" + file.getName(); //$NON-NLS-1$
		} else {
			dest = null;
		}
		// Reset the file chooser for the next time it's shown.
		fc.setSelectedFile(null);
		/************************************************************/
		if (source != null && dest != null) {
			// System.out.println(source + "     " + dest);
			if ("1".equals(type)) {//mp3 to wav //$NON-NLS-1$
				// JDialog holdingWd = new JDialog(theMain.frame,
				// Messages.getString("Constants.converting"), false);
				WaitingDialog holdingWd = new WaitingDialog(frame,
						Messages.getString("Constants.converting"));
				try {
					// show the dialog
					holdingWd.setVisible(true);

					new MP3_To_WAV(source, dest);

					JOptionPane
							.showMessageDialog(
									frame,
									Messages.getString("Constants.info_DoneSuccesfully"),
									Messages.getString("Constants.title_ProcessIsCompleted"),
									JOptionPane.INFORMATION_MESSAGE); //$NON-NLS-1$
					holdingWd.dispose();// setVisible(false);
				} catch (JavaLayerException e) {
					JOptionPane
							.showMessageDialog(
									frame,
									Messages.getString("Constants.error")
											+ e.getMessage(),
									Messages.getString("Constants.title_SorryButProcessCanNotCompleted"),
									JOptionPane.ERROR_MESSAGE); //$NON-NLS-1$
					holdingWd.dispose();// setVisible(false);
				}
			} else if ("2".equals(type)) {//wav to mp3 //$NON-NLS-1$
				WaitingDialog holdingWd = new WaitingDialog(frame,
						Messages.getString("Constants.converting"));
				// show the dialog
				holdingWd.setVisible(true);

				if ("wav".equalsIgnoreCase(ConstantMethods
						.getExtension(new File(dest)))) {
					dest = dest.split(".wav")[0];
					dest = dest + ".mp3";
				}
				new WAV_To_MP3(source, dest, holdingWd);

				// holdingWd.dispose();
			}
		}
	}

	/**
	 * @return the panel
	 */
	public JPanel getPanel() {
		return panel;
	}

}
