package UI.panels;

import RipConvertShared.util.Pref;
import UI.Messages;
import UI.NewMain;
import UI.fileChooser.AudioVideoFilter;
import UI.fileChooser.ImageFileView;
import Util.Constants;

import com.sohlman.easylayout.EasyLayout;
import com.sohlman.easylayout.Position;

import controller.recorder.SimpleAudioRecorder;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

import javax.media.Manager;
import javax.media.Player;
import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.TargetDataLine;
import javax.swing.*;

/**
 * @author Mostafa Gazar
 * @date 06-10-2007
 */
public class AudioRecoderUIPanel {
	private JPanel mainPanel = new JPanel(new BorderLayout());
	private JPanel recordPanel;
	private JPanel playerPanel = new JPanel(new BorderLayout());

	StatusBar theStatusBar = new StatusBar();

	private Player player;

	private static URL url;
	private static String runningAudio;

	private JTextField pathTx;

	private SimpleAudioRecorder recorder;

	private JFrame parent;
	static {
		try {
			runningAudio = Messages.getString("Constants.welcomeAudio"); //$NON-NLS-1$
			url = new File(runningAudio).toURL();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
	}

	public AudioRecoderUIPanel(JFrame frm) {
		// enableEvents(64L);
		this.parent = frm;
		try {
			createControls();
		} catch (Exception exception) {
			exception.printStackTrace();
		}
	}

	protected void createControls() {
		// setTitle("EasyLayout TestApplication");
		// jpanel = (JPanel)getContentPane();
		int ai[] = {// col
		40, 40, 20 };
		int ai1[] = {// raw
		10, 45, 45 };

		EasyLayout easylayout = new EasyLayout(null, null, ai, ai1, 3, 3);
		recordPanel = new JPanel(easylayout);
		// jpanel.setLayout(easylayout);

		JButton recordBt = new JButton(new ImageIcon(
				NewMain.class.getResource(Messages
						.getString("Constants.recordImg"))));
		recordBt.setBorder(BorderFactory.createEmptyBorder(4, 4, 4, 4));
		recordBt.setOpaque(true);
		recordBt.setRolloverIcon(new ImageIcon(NewMain.class
				.getResource(Messages.getString("Constants.recordOverImg"))));
		recordBt.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String strFilename = pathTx.getText();
				if (strFilename.length() == 0) {
					pathTx.setText(Pref.getPref(Constants.BASE_DIRECTORY_KEY,
							"") + Messages.getString("Constants.recordFile"));
					if (strFilename.length() == 0)
						JOptionPane.showMessageDialog(
								parent,
								Messages.getString("Constants.error_SetOutputFolder"),
								Messages.getString("Constants.title_SorryButProcessCanNotCompleted"),
								JOptionPane.ERROR_MESSAGE);
				} else {
					File outputFile = new File(strFilename);

					/*
					 * For simplicity, the audio data format used for recording
					 * is hardcoded here. We use PCM 44.1 kHz, 16 bit signed,
					 * stereo.
					 */
					AudioFormat audioFormat = new AudioFormat(
							AudioFormat.Encoding.PCM_SIGNED, 44100.0F, 16, 2,
							4, 44100.0F, false);

					/*
					 * Now, we are trying to get a TargetDataLine. The
					 * TargetDataLine is used later to read audio data from it.
					 * If requesting the line was successful, we are opening it
					 * (important!).
					 */
					DataLine.Info info = new DataLine.Info(
							TargetDataLine.class, audioFormat);
					TargetDataLine targetDataLine = null;
					try {
						targetDataLine = (TargetDataLine) AudioSystem
								.getLine(info);
						targetDataLine.open(audioFormat);
					} catch (LineUnavailableException e1) {
						JOptionPane.showMessageDialog(
								parent,
								Messages.getString("Constants.error_UnableToGetARecordingLine"),
								Messages.getString("Constants.title_SorryButProcessCanNotCompleted"),
								JOptionPane.ERROR_MESSAGE);
						e1.printStackTrace();
						System.exit(1);
					}

					/*
					 * Again for simplicity, we've hardcoded the audio file
					 * type, too.
					 */
					AudioFileFormat.Type targetType = AudioFileFormat.Type.WAVE;

					/*
					 * Now, we are creating an SimpleAudioRecorder object. It
					 * contains the logic of starting and stopping the
					 * recording, reading audio data from the TargetDataLine and
					 * writing the data to a file.
					 */
					recorder = new SimpleAudioRecorder(targetDataLine,
							targetType, outputFile);

					theStatusBar.setNotifyMessage("Start recording.");
					recorder.start();
				}
			}
		});

		JButton stopBt = new JButton(new ImageIcon(
				NewMain.class.getResource(Messages
						.getString("Constants.stopImg"))));
		stopBt.setBorder(BorderFactory.createEmptyBorder(4, 4, 4, 4));
		stopBt.setOpaque(true);
		stopBt.setRolloverIcon(new ImageIcon(NewMain.class.getResource(Messages
				.getString("Constants.stopOverImg"))));
		stopBt.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (recorder == null)
					JOptionPane.showMessageDialog(
							parent,
							Messages.getString("Constants.error_PressRecordFirst"),
							Messages.getString("Constants.title_SorryButProcessCanNotCompleted"),
							JOptionPane.ERROR_MESSAGE);
				else {
					// WaitingDialog aWaitingDialog = new WaitingDialog(parent,
					// Messages.getString("Constants.title_recording"));
					recorder.stopRecording();
					theStatusBar
							.setNotifyMessage("Recording stop and saved to file.");
					// aWaitingDialog.dispose();
				}
			}
		});

		pathTx = new JTextField(Pref.getPref(Constants.BASE_DIRECTORY_KEY, "")
				+ Messages.getString("Constants.recordFile"));
		JButton setSavePathBt = new JButton(
				Messages.getString("Constants.changeSavePath"));
		setSavePathBt.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser chooser = new JFileChooser();

				// chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				chooser.setDialogTitle("Select Directory");
				if (chooser.showOpenDialog(parent) == JFileChooser.APPROVE_OPTION) {
					pathTx.setText(chooser.getSelectedFile().getPath());
				}
			}
		});

		JButton changeRunningFileBt = new JButton(
				Messages.getString("Constants.changeTheRunningFile"));
		changeRunningFileBt.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					String path;
					JFileChooser fc = new JFileChooser();

					fc.addChoosableFileFilter(new AudioVideoFilter());
					fc.setAcceptAllFileFilterUsed(false);

					// Add custom icons for file types.
					fc.setFileView(new ImageFileView());

					// Show it.
					int returnVal = fc.showDialog(parent,
							Messages.getString("Constants.accept"));

					// Process the results.
					if (returnVal == JFileChooser.APPROVE_OPTION) {
						File file = fc.getSelectedFile();
						path = file.getParent() + "\\" + file.getName(); //$NON-NLS-1$
						// Add.setEnabled(true);
						// System.out.println(path);
					} else {
						path = null;
					}
					// Reset the file chooser for the next time it's shown.
					fc.setSelectedFile(null);
					// //////////////////////////////////
					if (path != null) {
						playerPanel.removeAll();

						player.stop();
						url = new File(path).toURL();
						try {
							player = Manager.createRealizedPlayer(url);
						} catch (Exception e11) {
							e11.printStackTrace();
						}

						playerPanel.add(player.getControlPanelComponent(),
								"North");

						playerPanel.updateUI();// repaint();
					}
				} catch (Exception e1) {
					e1.printStackTrace();
				}

			}
		});

		player = null;
		try {
			player = Manager.createRealizedPlayer(url);
		} catch (Exception e) {
			e.printStackTrace();
		}
		playerPanel.add(player.getControlPanelComponent(), "North");

		recordPanel.add(recordBt, new Position(0, 2));
		recordPanel.add(stopBt, new Position(1, 2));
		recordPanel.add(pathTx, new Position(0, 1, 2, 1, 1, 1, -1, -1));
		recordPanel.add(setSavePathBt, new Position(2, 1));
		recordPanel.add(changeRunningFileBt, new Position(2, 2));
		recordPanel.add(playerPanel, new Position(0, 0, 3, 1, 1, 1, -1, -1));

		mainPanel.add(recordPanel, BorderLayout.NORTH);
		mainPanel.add(theStatusBar, BorderLayout.SOUTH);// "South"
	}

	public static void main(String args[]) {
		// new Pref("temp");
		JFrame frame = new JFrame();
		AudioRecoderUIPanel layoutframe = new AudioRecoderUIPanel(null);
		frame.add(layoutframe.getPanel());
		frame.pack();
		frame.validate();
		Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
		Dimension dimension1 = frame.getSize();
		if (dimension1.height > dimension.height)
			dimension1.height = dimension.height;
		if (dimension1.width > dimension.width)
			dimension1.width = dimension.width;
		frame.setLocation((dimension.width - dimension1.width) / 2,
				(dimension.height - dimension1.height) / 2);
		frame.setVisible(true);
	}

	/**
	 * @return the panel
	 */
	public JPanel getPanel() {
		return mainPanel;
	}
}