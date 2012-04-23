package function.shared.ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.border.TitledBorder;

import util.Constants;
import view.Messages;

/**
 * @author Mostafa Gazar, eng.mostafa.gazar@gmail.com
 */
public class QualityChooser extends JPanel {
	private static final long serialVersionUID = 1L;

	ButtonGroup qualityBtGroup = new ButtonGroup();

	TitledBorder titled = BorderFactory.createTitledBorder(Messages
			.getString("Constants.quality"));

	private int selected;

	JRadioButton mp3Extreme;
	JRadioButton mp3High;
	JRadioButton mp3Normal;
	JRadioButton mp3AudioBook;

	JRadioButton oggExtreme;
	JRadioButton oggHigh;
	JRadioButton oggNormal;
	JRadioButton oggAudioBook;

	JRadioButton aacExtreme;
	JRadioButton aacHigh;
	JRadioButton accNormal;
	JRadioButton accAudioBook;

	JRadioButton flac;

	JRadioButton wav;

	public QualityChooser() {
		super();
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		setBorder(titled);

		mp3Extreme = new JRadioButton(Constants.ENCODER_NAME_DEFAULTS[0]);
		mp3Extreme.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				selected = 0;
			}
		});
		mp3High = new JRadioButton(Constants.ENCODER_NAME_DEFAULTS[1]);
		mp3High.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				selected = 1;
			}
		});
		mp3Normal = new JRadioButton(Constants.ENCODER_NAME_DEFAULTS[2]);
		mp3Normal.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				selected = 2;
			}
		});
		mp3AudioBook = new JRadioButton(Constants.ENCODER_NAME_DEFAULTS[3]);
		mp3AudioBook.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				selected = 3;
			}
		});

		oggExtreme = new JRadioButton(Constants.ENCODER_NAME_DEFAULTS[4]);
		oggExtreme.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				selected = 4;
			}
		});
		oggHigh = new JRadioButton(Constants.ENCODER_NAME_DEFAULTS[5]);
		oggHigh.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				selected = 5;
			}
		});
		oggNormal = new JRadioButton(Constants.ENCODER_NAME_DEFAULTS[6]);
		oggNormal.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				selected = 6;
			}
		});
		oggAudioBook = new JRadioButton(Constants.ENCODER_NAME_DEFAULTS[7]);
		oggAudioBook.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				selected = 7;
			}
		});

		aacExtreme = new JRadioButton(Constants.ENCODER_NAME_DEFAULTS[8]);
		aacExtreme.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				selected = 8;
			}
		});
		aacHigh = new JRadioButton(Constants.ENCODER_NAME_DEFAULTS[9]);
		aacHigh.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				selected = 9;
			}
		});
		accNormal = new JRadioButton(Constants.ENCODER_NAME_DEFAULTS[10]);
		accNormal.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				selected = 10;
			}
		});
		accAudioBook = new JRadioButton(Constants.ENCODER_NAME_DEFAULTS[11]);
		accAudioBook.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				selected = 11;
			}
		});

		flac = new JRadioButton(Constants.ENCODER_NAME_DEFAULTS[12]);
		flac.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				selected = 12;
			}
		});

		wav = new JRadioButton(Constants.ENCODER_NAME_DEFAULTS[13]);
		wav.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				selected = 13;
			}
		});

		qualityBtGroup.add(mp3Extreme);
		qualityBtGroup.add(mp3High);
		qualityBtGroup.add(mp3Normal);
		qualityBtGroup.add(mp3AudioBook);

		qualityBtGroup.add(oggExtreme);
		qualityBtGroup.add(oggHigh);
		qualityBtGroup.add(oggNormal);
		qualityBtGroup.add(oggAudioBook);

		qualityBtGroup.add(aacExtreme);
		qualityBtGroup.add(aacHigh);
		qualityBtGroup.add(accNormal);
		qualityBtGroup.add(accAudioBook);

		qualityBtGroup.add(flac);

		qualityBtGroup.add(wav);

		add(mp3Extreme);
		add(mp3High);
		add(mp3Normal);
		add(mp3AudioBook);

		add(oggExtreme);
		add(oggHigh);
		add(oggNormal);
		add(oggAudioBook);

		add(aacExtreme);
		add(aacHigh);
		add(accNormal);
		add(accAudioBook);

		add(flac);

		add(wav);
	}

	public static void main(String[] args) {
		JFrame frm = new JFrame();
		frm.getContentPane().add(new QualityChooser());
		frm.pack();
		frm.setVisible(true);
	}

	/**
	 * @return the selected
	 */
	public int getSelected() {
		return selected;
	}

}
