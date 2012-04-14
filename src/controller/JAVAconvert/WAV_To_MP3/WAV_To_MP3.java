package controller.JAVAconvert.WAV_To_MP3;

import javax.media.MediaLocator;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import UI.WaitingDialog;

/**
 * This class is using the class and interface beside which use <b>jmf.jar</b>
 * to convert wav files to mp3 files.
 * 
 * @author lordms12
 * @done 04-09-2007
 */
public class WAV_To_MP3 extends JPanel implements IConverterListener {
	private static final long serialVersionUID = 1L;

	WaitingDialog holdingWd;

	public WAV_To_MP3(String source, String dest, WaitingDialog holdingWd) {
		this.holdingWd = holdingWd;
		FormatConverter converter = new FormatConverter(this);
		System.out.println("Converting...");// TODO show start converting
		converter.convert(new MediaLocator("file://" + source), // "file:/"
																// +source
				new MediaLocator("file://" + dest), false);
	}

	public void onConverterEvent(int status, int reason) {
		if (IConverterListener.CONVERTER_SUCCESS == status) {
			System.out.println("File converted.");// TODO out in UI
			String opt[] = { "OK" };
			JOptionPane.showOptionDialog(this, "Done successfully", "Done",
					JOptionPane.INFORMATION_MESSAGE,
					JOptionPane.INFORMATION_MESSAGE, null, opt, "1");
			if (holdingWd != null)
				holdingWd.dispose();// hide();
		} else {
			System.out.println("Conversion failed:  " + status + ", " + reason);// TODO
																				// out
																				// in
																				// UI
			String opt[] = { "OK" };
			JOptionPane.showOptionDialog(this, "Error: " + status + ", "
					+ reason, "Sorry but process can not completed",
					JOptionPane.INFORMATION_MESSAGE, JOptionPane.ERROR_MESSAGE,
					null, opt, "1");
			if (holdingWd != null)
				holdingWd.dispose();// hide();
		}
	}

	public static void main(String[] args) {
		// new WAV_To_MP3("C:/test.wav","C:/test.mp3");
	}

}
