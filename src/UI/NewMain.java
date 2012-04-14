package UI;

import Convert2.Convert;
import Rip.Rip;
import RipConvertShared.UI.setup.BaseSetupPanel;
import RipConvertShared.UI.setup.FileNames;
import RipConvertShared.UI.setup.General;
import RipConvertShared.UI.setup.SetupDialog;
import RipConvertShared.UI.setup.Tag;
import RipConvertShared.util.Pref;
import UI.panels.AudioRecoderUIPanel;
import UI.panels.HelpPanel;
import UI.panels.MergingPanel;
import UI.panels.PlayCutPanel;
import UI.panels.helpDialog.HelpDialog;
import UI.sideBar.JButtonBar;
import UI.sideBar.plaf.misc.IconPackagerButtonBarUI;
import Util.Constants;

import java.awt.AWTException;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.TrayIcon;
import java.awt.TrayIcon.MessageType;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;

import javax.swing.*;

/**
 * Main UI class.
 * 
 * @author Mostafa Gazar
 * @start 11-09-2007
 */
@SuppressWarnings({ "serial" })//$NON-NLS-1$ //$NON-NLS-2$
public class NewMain extends JPanel {
	public static final String PROGRAM_NAME = Constants.PREFERENCE_NAME;
	private static final String PROGRAM_ICON = "icons/main.png";
	public static JFrame frame;
	private static TrayIcon trayIcon;

	private final String PLAY_CUT = Messages.getString("Constants.play_Cut"); //$NON-NLS-1$
	private final String MERGING = Messages.getString("Constants.merging"); //$NON-NLS-1$
	private final String CONVERTOR = Messages.getString("Constants.convertor"); //$NON-NLS-1$
	private final String RIP = Messages.getString("Constants.rip"); //$NON-NLS-1$
	private final String RECORDER = Messages.getString("Constants.recorder"); //$NON-NLS-1$
	private final String RADIO = Messages.getString("Constants.radio"); //$NON-NLS-1$
	private final String SETTING = Messages.getString("Constants.setting"); //$NON-NLS-1$
	private final String FAVORITES = Messages.getString("Constants.favorites"); //$NON-NLS-1$

	JButtonBar toolbar;

	/** just another constructor used in main */
	public NewMain(int i) {

	}

	public NewMain() {
		setLayout(new BorderLayout());

		{ // with the icon packager L&F
			toolbar = new JButtonBar(JButtonBar.VERTICAL);
			toolbar.setUI(new IconPackagerButtonBarUI());

			add("Center", new ButtonBarPanel(toolbar)); //$NON-NLS-1$
		}
	}

	class ButtonBarPanel extends JPanel {

		private Component currentComponent;

		public ButtonBarPanel(JButtonBar toolbar) {
			setLayout(new BorderLayout());

			add("West", toolbar); //$NON-NLS-1$

			ButtonGroup group = new ButtonGroup();
			addButton(PLAY_CUT, Messages.getString("Constants.playIcon"), //$NON-NLS-1$
					makePanel(PLAY_CUT), toolbar, group, "", true);

			addButton(MERGING, Messages.getString("Constants.mergeIcon"), //$NON-NLS-1$
					makePanel(MERGING), toolbar, group, "", true);

			addButton(CONVERTOR, Messages.getString("Constants.convertIcon"), //$NON-NLS-1$
					makePanel(CONVERTOR), toolbar, group, "", true);

			addButton(RECORDER,
					Messages.getString("Constants.recordIcon"), //$NON-NLS-1$
					makePanel(RECORDER), toolbar, group,
					Messages.getString("Constants.recorderToolTip"), true);

			addButton(RIP, Messages.getString("Constants.ripIcon"), //$NON-NLS-1$
					makePanel(RIP), toolbar, group, "", true);

			addButton(RADIO, Messages.getString("Constants.radioIcon"), //$NON-NLS-1$
					makePanel(RADIO), toolbar, group, "", false);

			addButton(SETTING, Messages.getString("Constants.settingIcon"), //$NON-NLS-1$
					makePanel(SETTING), toolbar, group, "", true);

			addButton(FAVORITES, Messages.getString("Constants.favoritesIcon"), //$NON-NLS-1$
					makePanel(FAVORITES), toolbar, group, "", false);
		}

		private JPanel makePanel(String title) {// TODO modify
			JPanel panel = null;

			if (PLAY_CUT.compareTo(title) == 0) {
				panel = createPlay_CutPanel(panel);
			} else if (MERGING.compareTo(title) == 0) {
				panel = createMergingPanel(panel);
			} else if (CONVERTOR.compareTo(title) == 0) {
				panel = createConvertorPanel(panel);
			} else if (RECORDER.compareTo(title) == 0) {
				panel = createRecorderPanel(panel);
			} else if (RIP.compareTo(title) == 0) {
				panel = createRipPanel(panel);
			} else if (RADIO.compareTo(title) == 0) {
				panel = createRadioPanel(panel);
			} else if (SETTING.compareTo(title) == 0) {
				panel = createSettingPanel(panel);
			} else if (FAVORITES.compareTo(title) == 0) {
				panel = createFavoritesPanel(panel);
			}

			panel.setPreferredSize(new Dimension(500, 300));
			panel.setBorder(BorderFactory.createEmptyBorder(4, 4, 4, 4));
			return panel;
		}

		private void show(Component component) {
			if (currentComponent != null) {
				remove(currentComponent);
			}
			add("Center", currentComponent = component); //$NON-NLS-1$
			revalidate();
			repaint();
		}

		private void addButton(String title, String iconUrl,
				final Component component, JButtonBar bar, ButtonGroup group,
				String toolTip, boolean isEnabled) {
			Action action = new AbstractAction(title, new ImageIcon(
					NewMain.class.getResource(iconUrl))) {
				public void actionPerformed(ActionEvent e) {
					show(component);
				}
			};

			final JToggleButton button = new JToggleButton(action);
			button.setToolTipText(toolTip);
			button.setEnabled(isEnabled);
			bar.add(button);

			group.add(button);

			if (group.getSelection() == null) {
				button.setSelected(true);
				show(component);
			}
		}
	}

	private JPanel createPlay_CutPanel(JPanel panel) {
		panel = new PlayCutPanel().getPanel();

		return panel;
	}

	private JPanel createMergingPanel(JPanel panel) {
		panel = new MergingPanel().getPanel();

		return panel;
	}

	private JPanel createConvertorPanel(JPanel panel) {
		final Convert ripper = new Convert();
		panel = ripper.getWin();

		return panel;
	}

	private JPanel createRecorderPanel(JPanel panel) {
		panel = new AudioRecoderUIPanel(frame).getPanel();

		return panel;
	}

	private JPanel createRipPanel(JPanel panel) {
		final Rip ripper = new Rip();
		panel = ripper.getWin();

		return panel;
	}

	private JPanel createRadioPanel(JPanel panel) {
		JPanel aPanel = new JPanel();
		// TODO implement it
		return aPanel;
	}

	private JPanel createSettingPanel(JPanel panel) {// Show table
		String[] choices = Constants.SETUP_DIALOG_TABS;
		// BaseSetupPanel[] panels = {new General(), new FileNames(), new Tag(),
		// new Program(), new CDReader(), new Encoder()};
		BaseSetupPanel[] panels = { new General(), new FileNames(), new Tag() };
		SetupDialog setup = new SetupDialog("&Save", choices, panels);

		panel = setup.getMainPanel();

		return panel;
	}

	private JPanel createFavoritesPanel(JPanel panel) {
		JPanel aPanel = new JPanel();
		// TODO implement it
		return aPanel;
	}

	public void initUI() {
		frame = new JFrame(PROGRAM_NAME); //$NON-NLS-1$

		if (SystemTray.isSupported()) {
			// frame.setDefaultCloseOperation(javax.swing.WindowConstants.HIDE_ON_CLOSE);
			// frame.setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

			SystemTray tray = SystemTray.getSystemTray();

			Image trayImage = new ImageIcon(
					NewMain.class.getResource(PROGRAM_ICON)).getImage(); //$NON-NLS-1$

			ActionListener exitListener = new ActionListener() {

				public void actionPerformed(ActionEvent e) {
					int respuesta = JOptionPane
							.showConfirmDialog(
									null,
									Messages.getString("Constants.closeConfirm"), PROGRAM_NAME, JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE); //$NON-NLS-1$
					if (respuesta == JOptionPane.OK_OPTION) {
						System.exit(0);
					}
				}
			};

			PopupMenu popup = new PopupMenu();
			MenuItem exitItem = new MenuItem(Messages.getString("Tray.exit")); //$NON-NLS-1$
			MenuItem openAboutItem = new MenuItem(
					Messages.getString("Tray.about")); //$NON-NLS-1$
			MenuItem openHelpItem = new MenuItem(
					Messages.getString("Tray.help")); //$NON-NLS-1$

			openAboutItem.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					new HelpPanel(frame);
				}
			});
			openHelpItem.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					SwingUtilities.invokeLater(new Runnable() {
						public void run() {
							HelpDialog help = new HelpDialog(frame,
									NewMain.PROGRAM_NAME + "Help", "&Close",
									"http://lordms12.googlepages.com/help_index.html");
							help.setSize(400, 550);
							Dimension d = frame.getToolkit().getScreenSize();
							help.setLocation((d.width / 2)
									- (help.getWidth() / 2), (d.height / 2)
									- (help.getHeight() / 2));
							help.setVisible(true);
						}
					});
					// try {
					// File file = new File("help/index.html");
					// Process p =
					// Runtime.getRuntime().exec("rundll32 SHELL32.DLL,ShellExec_RunDLL "
					// + file.getAbsolutePath());// FIXME Change this windows
					// line
					// p.waitFor();
					// } catch (Exception e1) {
					// e1.printStackTrace();
					// }
				}
			});
			exitItem.addActionListener(exitListener);

			popup.add(openAboutItem);
			popup.add(openHelpItem);
			popup.add(exitItem);

			trayIcon = new TrayIcon(trayImage, PROGRAM_NAME, popup);
			trayIcon.setImageAutoSize(true);

			MouseListener mouseListener = new MouseListener() {

				public void mouseClicked(MouseEvent e) {
					if (frame.isVisible())
						frame.setVisible(false);
					else
						frame.setVisible(true);
				}

				public void mouseEntered(MouseEvent e) {
				}

				public void mouseReleased(MouseEvent e) {
				}

				public void mousePressed(MouseEvent e) {
				}

				public void mouseExited(MouseEvent e) {
				}
			};
			trayIcon.addMouseListener(mouseListener);

			if (tray.getTrayIcons().length < 1) {
				try {
					tray.add(trayIcon);
				} catch (AWTException e) {
					e.printStackTrace();
				}
			}

			if (Pref.getPref(Constants.FIRST_TIME_TO_RUN, "").length() == 0) {
				trayIcon.displayMessage(Constants.PREFERENCE_NAME,
						Messages.getString("Tray.ballonText"), MessageType.INFO);// TODO
																					// put
																					// option
																					// to
																					// hide
																					// it
																					// in
																					// future
																					// opening
			}
		}
		// else{
		// frame.setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
		// }
		// /////////////////////////////////////////////////
		frame.setIconImage(new ImageIcon(NewMain.class
				.getResource(PROGRAM_ICON)).getImage());
		frame.getContentPane().setLayout(new BorderLayout());
		NewMain aButtonBarMain = new NewMain();
		frame.getContentPane().add("Center", aButtonBarMain); //$NON-NLS-1$
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();
		// frame.setSize(582, 433);
		// System.out.println(frame.getWidth());

		Dimension d = frame.getToolkit().getScreenSize();
		frame.setLocation((d.width / 2) - (frame.getWidth() / 2),
				(d.height / 2) - (frame.getHeight() / 2));
		// frame.setLocation(100, 100);
		frame.setVisible(true);
	}

	public static void main(String[] args) {
		SplashScreen theSplashScreen = new SplashScreen(new JFrame(),
				"icons/splash.png");
		theSplashScreen.open(1000);

		new Pref(Constants.PREFERENCE_NAME);

		// Reset language if no language is chosen
		String language = Pref.getPref(Constants.LANG, "");// Does not return
															// null if nothing
															// found
		if (language != null && language.length() == 0) {
			Pref.setPref(Constants.LANG, Constants.LANG_DEFAULT_OPTION);
		}
		// Reset base directory if no one is chosen
		String baseDirectory = Pref.getPref(Constants.BASE_DIRECTORY_KEY, "");// Does
																				// not
																				// return
																				// null
																				// if
																				// nothing
																				// found
		if (baseDirectory != null && baseDirectory.length() == 0) {
			new File(Constants.DEFAULT_BASE_DIRECTORY_KEY).mkdir();
			Pref.setPref(Constants.BASE_DIRECTORY_KEY,
					Constants.DEFAULT_BASE_DIRECTORY_KEY);
		}

		// UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		JFrame.setDefaultLookAndFeelDecorated(true);
		try {
			// UIManager.setLookAndFeel("org.jvnet.substance.skin.SubstanceBusinessBlueSteelLookAndFeel");
			// UIManager.setLookAndFeel(new
			// SubstanceBusinessBlueSteelLookAndFeel());
			// UIManager.setLookAndFeel(new SubstanceBusinessLookAndFeel());
			String theme = Pref.getPref(Constants.THEME, "");
			if (theme == null || theme.length() == 0)
				theme = Constants.THEME_OPTIONS_VALUES[Constants.THEME_DEFAULT];
			UIManager.setLookAndFeel(theme);
		} catch (Exception e) {
			e.printStackTrace();
		}

		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				new NewMain(1).initUI();// any number
			}

			@Override
			protected void finalize() throws Throwable {
				super.finalize();
				Pref.setPref(Constants.FIRST_TIME_TO_RUN,
						Constants.FIRST_TIME_TO_RUN_TRUE);
			}
		});
	}
}