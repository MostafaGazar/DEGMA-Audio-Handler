/**
 * Created at: 11-10-2007
 */
package view.panels;

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

import ui.Messages;
import ui.NewMain;
import ui.WaitingDialog;
import util.ConstantMethods;
import view.fileChooser.AudioVideoFilter;
import view.fileChooser.ImageFileView;

import function.concat.FilesConcator;
import function.convert.util.WavToMp3;


/**
 * Just merge WAV or MP3 (with bitrate under 256Khz) files and others to one WAV or MP3.
 * @author Mostafa Gazar
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
				Messages.getString("Constants.addWAV_filesOr_MP3s"),
				new ImageIcon(NewMain.class.getResource(Messages
						.getString("Constants.addWAV_filesOr_MP3sIcon"))));
		addBt.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				addToFilesToBeMerged();
			}
		});

		mergeBt = new JButton(
				Messages.getString("Constants.mergeWAV_filesOr_MP3s"),
				new ImageIcon(NewMain.class.getResource(Messages
						.getString("Constants.mergeWAV_filesOr_MP3sIcon"))));
		mergeBt.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				setOutputFilePath();
				temp = null;
				if ("mp3".equalsIgnoreCase(ConstantMethods.getExtension(new File(outFilePath)))) {
					temp = outFilePath;
					outFilePath = null;
					outFilePath = Messages.getString("Constants.file") + System.getProperty("java.io.tmpdir") + Messages.getString("Constants.temp_merge");
				}
				int size = filesToBeMerged.size() + 2;
				String arg[] = new String[size];

				arg[0] = "-o";
				if (outFilePath != null)
					arg[1] = outFilePath;
				else {
					JOptionPane.showMessageDialog(
							NewMain.frame,
							Messages.getString("Constants.error_YouHaveToSetTheOutputPath"), Messages.getString("Constants.title_SorryButProcessCanNotCompleted"), //$NON-NLS-2$
							JOptionPane.ERROR_MESSAGE);
				}
				if (filesToBeMerged.size() == 0) {
					JOptionPane.showMessageDialog(
							NewMain.frame,
							Messages.getString("Constants.error_YouSpecifyFilesYouWantToMerge"), Messages.getString("Constants.title_SorryButProcessCanNotCompleted"), //$NON-NLS-2$
							JOptionPane.ERROR_MESSAGE);
				}
				for (int i = 2; i < size; i++) {
					arg[i] = filesToBeMerged.pop();// get(i-2);
				}

				/*
				 * JDialog holdingWd = new JDialog(theMain.frame,
				 * Messages.getString("Constants.title_merging"), false);
				 *
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
					if (!FilesConcator.merge(arg)) {
						JOptionPane.showMessageDialog(
								NewMain.frame,
								Messages.getString("Constants.error_CanMergeFilesSpecifiedMayBeBecauseOfCrashedFileOrNotSupportedFormat"), Messages.getString("Constants.title_SorryButProcessCanNotCompleted"), //$NON-NLS-2$
								JOptionPane.ERROR_MESSAGE);
					} else {
						// System.out.println("done");
						if (temp != null) {
							outFilePath = outFilePath.replaceFirst(
									Messages.getString("Constants.file"), ""); //$NON-NLS-2$
							temp = temp.replaceFirst(
									Messages.getString("Constants.file"), ""); //$NON-NLS-2$
							// System.out.println(outFilePath+"   "+temp);
							new WavToMp3(outFilePath, temp, holdingWd);
						}
						if (temp == null) {
							JOptionPane.showMessageDialog(
									NewMain.frame,
									Messages.getString("Constants.info_DoneSuccesfully"), Messages.getString("Constants.title_ProcessIsCompleted"), //$NON-NLS-2$
									JOptionPane.INFORMATION_MESSAGE);
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
				Messages.getString("Constants.cleanInputPathesWAV_filesOr_MP3s"),
				new ImageIcon(
						NewMain.class.getResource(Messages
								.getString("Constants.cleanInputPathesWAV_filesOr_MP3sIcon"))));
		cleanFilesToBeMerged.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				filesToBeMerged = null;
				filesToBeMerged = new LinkedList<String>();
				JOptionPane.showMessageDialog(
						NewMain.frame,
						Messages.getString("Constants.info_AllTheInputPathesYouEnteredBeforeHasDeletedFromHistory"), Messages.getString("Constants.title_ProcessIsCompleted"), //$NON-NLS-2$
						JOptionPane.INFORMATION_MESSAGE);
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
				Messages.getString("Constants.accept"));

		// Process the results.
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			File file = fc.getSelectedFile();
			path = file.getParent() + "\\" + file.getName();
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
				Messages.getString("Constants.accept"));

		// Process the results.
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			File file = fc.getSelectedFile();
			outFilePath = file.getParent() + "\\" + file.getName();
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
