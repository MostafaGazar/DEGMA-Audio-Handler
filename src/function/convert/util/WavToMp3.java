/**
 * Create at: 04-09-2007
 */
package function.convert.util;

import javax.media.MediaLocator;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import ui.WaitingDialog;

/**
 * Use <b>jmf.jar</b> to convert wav files to mp3 files.
 * 
 * @author Mostafa Gazar, eng.mostafa.gazar@gmail.com
 */
public class WavToMp3 extends JPanel implements IConverterListener {
	private static final long serialVersionUID = 1L;

	WaitingDialog holdingWd;

	public WavToMp3(String source, String dest, WaitingDialog holdingWd) {
		this.holdingWd = holdingWd;
		FormatConverter converter = new FormatConverter(this);
		// TODO Show start converting.
		System.out.println("Converting...");
		converter.convert(new MediaLocator("file://" + source),
				new MediaLocator("file://" + dest), false);
	}

	@Override
	public void onConverterEvent(int status, int reason) {
		if (IConverterListener.CONVERTER_SUCCESS == status) {
			// TODO Show in UI.
			System.out.println("File converted.");
			String opt[] = { "OK" };
			JOptionPane.showOptionDialog(this, "Done successfully", "Done",
					JOptionPane.INFORMATION_MESSAGE,
					JOptionPane.INFORMATION_MESSAGE, null, opt, "1");
			if (holdingWd != null)
				holdingWd.dispose();// hide();
		} else {
			// TODO Show in UI.
			System.out.println("Conversion failed:  " + status + ", " + reason);
			String opt[] = { "OK" };
			JOptionPane.showOptionDialog(this, "Error: " + status + ", "
					+ reason, "Sorry but process can not completed",
					JOptionPane.INFORMATION_MESSAGE, JOptionPane.ERROR_MESSAGE,
					null, opt, "1");
			if (holdingWd != null)
				holdingWd.dispose();// hide();
		}
	}
}
