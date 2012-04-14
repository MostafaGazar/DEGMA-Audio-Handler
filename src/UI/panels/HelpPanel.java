package UI.panels;

import java.awt.Dimension;

import javax.swing.JFrame;

import UI.NewMain;
import UI.panels.helpDialog.HelpDialog;
import Util.Constants;

public class HelpPanel {

	public HelpPanel(JFrame frame) {
		StringBuffer textBuff = new StringBuffer();
		textBuff.append("<html><body>");
		textBuff.append("<font face=\"Verdana\">");// I think it does not make
													// difference
		textBuff.append("<h2>" + Constants.PREFERENCE_NAME + "</h2>");
		textBuff.append("<a href=\"mailto:lordms12@gmail.com\">lordms12@gmail.com</a><br>");// absolutely
		textBuff.append("<a href=\"http://lordms12.googlepages.com\">http://lordms12.googlepages.com</a><br><br>");
		textBuff.append("DEGMA Audio Handler is totaly free for use for more information visit <a href=\"http://lordms12.googlepages.com/degma\">http://lordms12.googlepages.com/degma</a>.<br><br><br>");
		textBuff.append("DEGMA Audio Handler uses <i>l2fprod, substance, Easy Layout, JLayer 1.0, mp3 wave machine, jmf 2_1_1e, jRipper 2007.01, cdda2wav, lame, oggenc, oggdec, flac, faac and faad</i><br>");
		textBuff.append("<br> Regards, <br> <b>Mostafa Gazar</b> <br>(c) 2009");
		textBuff.append("</font>");
		textBuff.append("</body></html>");

		HelpDialog help = new HelpDialog(frame,
				"About " + NewMain.PROGRAM_NAME, "&Close", null);
		help.setSize(400, 550);
		Dimension d = frame.getToolkit().getScreenSize();
		help.setLocation((d.width / 2) - (help.getWidth() / 2), (d.height / 2)
				- (help.getHeight() / 2));
		help.setVisible(true);
		help.setHtml(textBuff.toString());
	}
}
