/**
 * Created at: 11-10-2007
 */
package view.panels;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import javax.media.CannotRealizeException;
import javax.media.Manager;
import javax.media.NoPlayerException;
import javax.media.Player;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import util.ConstantMethods;
import view.Messages;
import view.NewMain;
import view.WaitingDialog;
import view.fileChooser.AudioVideoFilter;
import view.fileChooser.ImageFileView;

//import org.eclipse.swt.SWT;
//import org.eclipse.swt.widgets.FileDialog;
//import org.eclipse.swt.widgets.Shell;

import function.convert.mp3ToWav.MP3_To_WAV;
import function.cut.CutWithNoUI;


/**
 * Cut WAV files and in case of MP3 it converts it to WAV then cut.
 * @author Mostafa Gazar
 */
public class PlayCutPanel {
	private JPanel panel;
	private JPanel playerPanel;

	/**
	 * fc is a file chooser allows you to change the current image of the
	 * product
	 */
	private JFileChooser fc;

	/** path is the current image path */
	private String path = new String();
	private String dest;

	private static URL url;
	private static String runningAudio;

	private JTextField startTimeTx;
	private JTextField endTimeTx;

	Player player = null;

	private JList<String> timeList;
	private DefaultListModel<String> timeListModel = new DefaultListModel<String>();

	private final int MAX_SIZE = 180;// 45 pieces to cut.
	private String argTime[] = new String[MAX_SIZE];// XXX Change if needed.
	private int countSplitTime = 0;

	static {
		try {
			runningAudio = Messages.getString("Constants.welcomeAudio");
			url = new URL(runningAudio);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
	}

	public PlayCutPanel() {
		// panel = new JPanel(new GridLayout(6, 1, 0, 25));
		panel = new JPanel(new BorderLayout());

		JButton browse = new JButton(
				Messages.getString("Constants.changeTheRunningFile"),
				new ImageIcon(NewMain.class.getResource(Messages
						.getString("Constants.browseIcon"))));
		browse.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					openMethod();
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		});
		panel.add(browse, "North");
		// /////////////////////////////////////////////////
		playerPanel = new JPanel(new BorderLayout());
		try {
			player = Manager.createRealizedPlayer(url);
			playerPanel.add(player.getControlPanelComponent(), "North");
		} catch (Exception e) {
			JOptionPane
					.showMessageDialog(
							NewMain.frame,
							Messages.getString("Constants.error_NotSupportedTypeOrCrashedFile"), Messages.getString("Constants.title_SorryButProcessCanNotCompleted"), //$NON-NLS-2$
							JOptionPane.ERROR_MESSAGE);
		}
		panel.add(playerPanel, "South");
		// ///////////////////////////////////////////////
		JPanel cutterPanel = new JPanel();
		JPanel listPanel = new JPanel(new BorderLayout());

		JLabel startTimeLb;
		JLabel endTimeLb;
		// JList timeList;
		JButton addBt;
		JButton addStartFromPlayerBt;
		JButton addEndFromPlayerBt;
		JButton saveBt;

		// construct components
		startTimeTx = new JTextField(5);
		endTimeTx = new JTextField(5);
		startTimeLb = new JLabel(Messages.getString("Constants.startTimeInSec"));
		endTimeLb = new JLabel(Messages.getString("Constants.endTimeInSec"));

		timeList = new JList<String>(timeListModel);
		timeList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		timeList.setFixedCellWidth(200);
		// timeList.setFixedCellHeight(10);
		JScrollPane scrollPane = new JScrollPane(timeList);

		addBt = new JButton(Messages.getString("Constants.add"), new ImageIcon(
				NewMain.class.getResource(Messages
						.getString("Constants.addIcon"))));
		addBt.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				addMethod();
				// System.out.println(Math.round(player.getMediaTime().getSeconds()));
			}
		});

		addStartFromPlayerBt = new JButton(
				Messages.getString("Constants.currentPosition"));
		addStartFromPlayerBt.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				startTimeTx.setText(Double.toString(
						Math.round(player.getMediaTime().getSeconds()))
						.replace(".0", "")); //$NON-NLS-2$
			}
		});

		addEndFromPlayerBt = new JButton(
				Messages.getString("Constants.currentPosition"));
		addEndFromPlayerBt.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				endTimeTx.setText(Double.toString(
						Math.round(player.getMediaTime().getSeconds()))
						.replace(".0", "")); //$NON-NLS-2$
			}
		});

		saveBt = new JButton(Messages.getString("Constants.saveTo"),
				new ImageIcon(NewMain.class.getResource(Messages
						.getString("Constants.runIcon"))));
		saveBt.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				saveMethod();
			}
		});

		// adjust size and set layout
		// cutterPanel.setPreferredSize (new Dimension (384, 262));
		cutterPanel.setLayout(null);

		// add components
		cutterPanel.add(startTimeTx);
		cutterPanel.add(addStartFromPlayerBt);
		cutterPanel.add(endTimeTx);
		cutterPanel.add(addEndFromPlayerBt);
		cutterPanel.add(startTimeLb);
		cutterPanel.add(endTimeLb);
		// cutterPanel.add (timeList);

		listPanel.add(scrollPane, BorderLayout.WEST);
		cutterPanel.add(listPanel);
		cutterPanel.add(addBt);
		cutterPanel.add(saveBt);

		int shift = 0;
		int shift2 = 45;
		// set component bounds (only needed by Absolute Positioning)
		startTimeTx.setBounds(100, 10 + shift, 55, 25);
		addStartFromPlayerBt.setBounds(160, 10 + shift, 115, 25);

		endTimeTx.setBounds(100, 45 + shift, 55, 25);
		addEndFromPlayerBt.setBounds(160, 45 + shift, 115, 25);

		startTimeLb.setBounds(5, 10 + shift, 100, 25);
		endTimeLb.setBounds(5, 45 + shift, 100, 25);

		listPanel.setBounds(285, 10, 215, 320 + shift2);

		addBt.setBounds(0, 40 + shift2, 275, 30);
		saveBt.setBounds(0, 340 + shift2, 487, 30);

		panel.add(cutterPanel, "Center");
	}

	private void openMethod() throws NoPlayerException, CannotRealizeException,
			IOException {
		// Set up the file chooser.
		if (fc == null) {
			fc = new JFileChooser();

			// Add a custom file filter and disable the default
			// (Accept All) file filter.
			fc.addChoosableFileFilter(new AudioVideoFilter());
			fc.setAcceptAllFileFilterUsed(false);

			// Add custom icons for file types.
			fc.setFileView(new ImageFileView());
		}

		// Show it.
		int returnVal = fc.showDialog(NewMain.frame,
				Messages.getString("Constants.accept"));

		// Process the results.
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			File file = fc.getSelectedFile();
			path = file.getParent() + "\\" + file.getName();
		} else {
			return;
		}
		// Reset the file chooser for the next time it's shown.
		fc.setSelectedFile(null);
		/**********************************************************/
		runningAudio = path;
		url = new URL(path);

		try {
			player.stop();
		} catch (Exception e) {
			e.printStackTrace();
		}

		playerPanel.removeAll();

		try {
			player = Manager.createRealizedPlayer(url);
		} catch (Exception e11) {
			e11.printStackTrace();
		}
		playerPanel.add(player.getControlPanelComponent(), "North");
		playerPanel.updateUI();

		timeListModel.removeAllElements();
		argTime = null;
		argTime = new String[MAX_SIZE];
		countSplitTime = 0;
	}

	private void addMethod() {
		try {// TODO Clean the following check.
			Integer.parseInt(startTimeTx.getText());
			Integer.parseInt(endTimeTx.getText());
		} catch (Exception e) {
			JOptionPane
					.showMessageDialog(
							NewMain.frame,
							Messages.getString("Constants.error_CanNotBeAddedWrongNumberFormat"),
							Messages.getString("Constants.title_SorryButProcessCanNotCompleted"),
							JOptionPane.ERROR_MESSAGE);
		}
		argTime[countSplitTime] = "-s";
		countSplitTime = countSplitTime + 1;

		Integer temp = Integer.parseInt(startTimeTx.getText()) * 1000;
		argTime[countSplitTime] = temp.toString();
		countSplitTime = countSplitTime + 1;

		argTime[countSplitTime] = "-e";
		countSplitTime = countSplitTime + 1;

		temp = Integer.parseInt(endTimeTx.getText()) * 1000;
		argTime[countSplitTime] = temp.toString();
		countSplitTime = countSplitTime + 1;

		// timeListValues[timeListCount]="From second "+startTimeTx.getText()+" to "+endTimeTx.getText();
		StringBuffer tempBuff = new StringBuffer();
		tempBuff.append(Messages.getString("Constants.fromSecond"));
		tempBuff.append(startTimeTx.getText());
		tempBuff.append(Messages.getString("Constants.toSecond"));
		tempBuff.append(endTimeTx.getText());
		timeListModel.addElement(tempBuff.toString());
	}

	WaitingDialog holdingWd = null;

	private void saveMethod() {
		// Set up the file chooser.
		if (fc == null) {
			fc = new JFileChooser();

			// Add a custom file filter and disable the default
			// (Accept All) file filter.
			fc.addChoosableFileFilter(new AudioVideoFilter());
			fc.setAcceptAllFileFilterUsed(false);

			// Add custom icons for file types.
			fc.setFileView(new ImageFileView());
		}

		// Show it.
		int returnVal = fc.showDialog(NewMain.frame,
				Messages.getString("Constants.accept"));
		// Process the results.
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			File file = fc.getSelectedFile();
			dest = file.getParent() + "\\" + file.getName();
		} else {
			dest = null;
			return;
		}

		holdingWd = new WaitingDialog(NewMain.frame,
				Messages.getString("Constants.titleCutting"));
		// show the dialog
		holdingWd.setVisible(true);

		if ("mp3".equalsIgnoreCase(ConstantMethods.getExtension(new File(
				runningAudio)))) {// Convert to WAV to be able to cut.
			try {
				new MP3_To_WAV(runningAudio,
						System.getProperty("java.io.tmpdir")
								+ Messages.getString("Constants.temp_cut"));
			} catch (Exception e) {
				JOptionPane
						.showMessageDialog(
								NewMain.frame,
								Messages.getString("Constants.error")
										+ e.getMessage(),
								Messages.getString("Constants.title_SorryButProcessCanNotCompleted"),
								JOptionPane.ERROR_MESSAGE);
				e.printStackTrace();
				holdingWd.dispose();
			}
			runningAudio = System.getProperty("java.io.tmpdir")
					+ Messages.getString("Constants.temp_cut");
		}
		// , "-s", "5000", "-e", "10000","-s", "50000", "-e", "100000"};
		String arg[] = { "-o", Messages.getString("Constants.file") + dest,
				Messages.getString("Constants.file") + runningAudio };
		int len = arg.length + countSplitTime;
		int len1 = arg.length;

		String toSend[] = new String[len];
		for (int i = 0; i < len1; i++) {
			toSend[i] = arg[i];
		}
		for (int i = len1; i < len; i++) {
			toSend[i] = argTime[i - len1];
		}
		for (int i = 0; i < len; i++) {
			System.out.print(toSend[i] + "  ");
		}

		try {
			CutWithNoUI.cut(toSend);// ,holdingWd);
		} catch (Exception e) {
			JOptionPane
					.showMessageDialog(
							NewMain.frame,
							Messages.getString("Constants.error")
									+ e.getMessage(),
							Messages.getString("Constants.title_SorryButProcessCanNotCompleted"),
							JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
			holdingWd.dispose();
		}
		holdingWd.dispose();
	}

	/**
	 * @return the panel.
	 */
	public JPanel getPanel() {
		return panel;
	}
}
