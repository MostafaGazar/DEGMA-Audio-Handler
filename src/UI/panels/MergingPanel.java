package UI.panels;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.LinkedList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import controller.JAVAconvert.WAV_To_MP3.WAV_To_MP3;
import controller.concat_WAVs_To_WAVsOrMP3s.ConcatWithNoUI;

import UI.WaitingDialog;
import UI.Messages;
import UI.NewMain;
import UI.fileChooser.AudioVideoFilter;
import UI.fileChooser.ImageFileView;
import Util.ConstantMethods;

/**
 * Just merge WAV MP3(with bitrate under 256khz) files and others to one WAV or
 * MP3 <br>
 * still unstable
 * 
 * @author Mostafa Gazar
 * @start 11-10-2007
 */
public class MergingPanel {
	private JPanel panel;

	/**
	 * fc is a file chooser allows you to change the current image of the
	 * product
	 */
	private JFileChooser fc;

	/** path is the current image path */
	private String path = new String();

	private LinkedList<String> filesToBeMerged = new LinkedList<String>();
	private String outFilePath = null;
	private String temp = null;

	public MergingPanel() {
		panel = new JPanel(new GridLayout(6, 1, 0, 10));

		JButton addBt;
		JButton cleanFilesToBeMerged;
		// JButton setOutputFile;
		JButton mergeBt;

		// construct components
		addBt = new JButton(
				Messages.getString("Constants.addWAV_filesOr_MP3s"), //$NON-NLS-1$
				new ImageIcon(NewMain.class.getResource(Messages
						.getString("Constants.addWAV_filesOr_MP3sIcon")))); //$NON-NLS-1$
		addBt.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				addToFilesToBeMerged();
			}
		});

		mergeBt = new JButton(
				Messages.getString("Constants.mergeWAV_filesOr_MP3s"), //$NON-NLS-1$
				new ImageIcon(NewMain.class.getResource(Messages
						.getString("Constants.mergeWAV_filesOr_MP3sIcon")))); //$NON-NLS-1$
		mergeBt.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				setOutputFilePath();
				temp = null;
				if ("mp3".equalsIgnoreCase(ConstantMethods.getExtension(new File(outFilePath)))) { //$NON-NLS-1$
					temp = outFilePath;
					outFilePath = null;
					outFilePath = Messages.getString("Constants.file") + System.getProperty("java.io.tmpdir") + Messages.getString("Constants.temp_merge"); //$NON-NLS-1$
				}
				int size = filesToBeMerged.size() + 2;
				String arg[] = new String[size];

				arg[0] = "-o"; //$NON-NLS-1$
				if (outFilePath != null)
					arg[1] = outFilePath;
				else {
					JOptionPane.showMessageDialog(
							NewMain.frame,
							Messages.getString("Constants.error_YouHaveToSetTheOutputPath"), Messages.getString("Constants.title_SorryButProcessCanNotCompleted"), //$NON-NLS-1$ //$NON-NLS-2$
							JOptionPane.ERROR_MESSAGE); //$NON-NLS-1$
				}
				if (filesToBeMerged.size() == 0) {
					JOptionPane.showMessageDialog(
							NewMain.frame,
							Messages.getString("Constants.error_YouSpecifyFilesYouWantToMerge"), Messages.getString("Constants.title_SorryButProcessCanNotCompleted"), //$NON-NLS-1$ //$NON-NLS-2$
							JOptionPane.ERROR_MESSAGE); //$NON-NLS-1$
				}
				for (int i = 2; i < size; i++) {
					arg[i] = filesToBeMerged.pop();// get(i-2);
				}

				/*
				 * JDialog holdingWd = new JDialog(theMain.frame,
				 * Messages.getString("Constants.title_merging"), false);
				 * //$NON-NLS-1$
				 * holdingWd.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE
				 * ); holdingWd.setSize(new Dimension(200, 0));
				 * holdingWd.setResizable(false); Dimension
				 * d=frame.getToolkit().getScreenSize();
				 * holdingWd.setLocation((d
				 * .width/2)-(holdingWd.getWidth()/2),(d.
				 * height/2)-(holdingWd.getHeight()/2));
				 */

				WaitingDialog holdingWd = new WaitingDialog(NewMain.frame,
						Messages.getString("Constants.title_merging"));
				// show the dialog
				holdingWd.setVisible(true);
				try {
					if (!ConcatWithNoUI.merge(arg)) {
						JOptionPane.showMessageDialog(
								NewMain.frame,
								Messages.getString("Constants.error_CanMergeFilesSpecifiedMayBeBecauseOfCrashedFileOrNotSupportedFormat"), Messages.getString("Constants.title_SorryButProcessCanNotCompleted"), //$NON-NLS-1$ //$NON-NLS-2$
								JOptionPane.ERROR_MESSAGE); //$NON-NLS-1$
					} else {
						// System.out.println("done");
						if (temp != null) {
							outFilePath = outFilePath.replaceFirst(
									Messages.getString("Constants.file"), ""); //$NON-NLS-1$ //$NON-NLS-2$
							temp = temp.replaceFirst(
									Messages.getString("Constants.file"), ""); //$NON-NLS-1$ //$NON-NLS-2$
							// System.out.println(outFilePath+"   "+temp);
							new WAV_To_MP3(outFilePath, temp, holdingWd);
						}
						if (temp == null) {
							JOptionPane.showMessageDialog(
									NewMain.frame,
									Messages.getString("Constants.info_DoneSuccesfully"), Messages.getString("Constants.title_ProcessIsCompleted"), //$NON-NLS-1$ //$NON-NLS-2$
									JOptionPane.INFORMATION_MESSAGE); //$NON-NLS-1$
						}
					}
				} catch (Exception ee) {
					holdingWd.dispose();// setVisible(false);
				}
				if (temp == null) {
					holdingWd.dispose();// setVisible(false);
				}
			}
		});

		/*
		 * setOutputFile = new JButton ("Set output path", new
		 * ImageIcon(NewMain.class.getResource("icons/insert3.png")));
		 * setOutputFile.addActionListener(new ActionListener(){ public void
		 * actionPerformed(ActionEvent e) { setOutputFilePath(); } });
		 */

		cleanFilesToBeMerged = new JButton(
				Messages.getString("Constants.cleanInputPathesWAV_filesOr_MP3s"), //$NON-NLS-1$
				new ImageIcon(
						NewMain.class.getResource(Messages
								.getString("Constants.cleanInputPathesWAV_filesOr_MP3sIcon")))); //$NON-NLS-1$
		cleanFilesToBeMerged.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				filesToBeMerged = null;
				filesToBeMerged = new LinkedList<String>();
				JOptionPane.showMessageDialog(
						NewMain.frame,
						Messages.getString("Constants.info_AllTheInputPathesYouEnteredBeforeHasDeletedFromHistory"), Messages.getString("Constants.title_ProcessIsCompleted"), //$NON-NLS-1$ //$NON-NLS-2$
						JOptionPane.INFORMATION_MESSAGE); //$NON-NLS-1$
			}
		});

		panel.add(addBt);
		panel.add(cleanFilesToBeMerged);
		// panel.add (setOutputFile);
		panel.add(mergeBt);

	}

	protected void addToFilesToBeMerged() {
		// String path;
		// Set up the file chooser.
		if (fc == null) {
			fc = new JFileChooser();

			// Add a custom file filter and disable the default
			// (Accept All) file filter.
			fc.addChoosableFileFilter(new AudioVideoFilter());
			fc.setAcceptAllFileFilterUsed(false);

			// Add custom icons for file types.
			fc.setFileView(new ImageFileView());

			// Add the preview pane.
			// fc.setAccessory(new ImagePreview(fc));
		}

		// Show it.
		int returnVal = fc.showDialog(NewMain.frame,
				Messages.getString("Constants.accept")); //$NON-NLS-1$

		// Process the results.
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			File file = fc.getSelectedFile();
			path = file.getParent() + "\\" + file.getName(); //$NON-NLS-1$
			path = Messages.getString("Constants.file") + path;
			filesToBeMerged.add(path);
		} else {
			path = null;
		}
		// Reset the file chooser for the next time it's shown.
		fc.setSelectedFile(null);
	}

	protected void setOutputFilePath() {
		// Set up the file chooser.
		if (fc == null) {
			fc = new JFileChooser();

			// Add a custom file filter and disable the default
			// (Accept All) file filter.
			fc.addChoosableFileFilter(new AudioVideoFilter());
			fc.setAcceptAllFileFilterUsed(false);

			// Add custom icons for file types.
			fc.setFileView(new ImageFileView());

			// Add the preview pane.
			// fc.setAccessory(new ImagePreview(fc));
		}

		// Show it.
		int returnVal = fc.showDialog(NewMain.frame,
				Messages.getString("Constants.accept")); //$NON-NLS-1$

		// Process the results.
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			File file = fc.getSelectedFile();
			outFilePath = file.getParent() + "\\" + file.getName(); //$NON-NLS-1$
			outFilePath = Messages.getString("Constants.file") + outFilePath;
		} else {
			outFilePath = null;
		}
		// Reset the file chooser for the next time it's shown.
		fc.setSelectedFile(null);
	}

	/**
	 * @return the panel
	 */
	public JPanel getPanel() {
		return panel;
	}

}
