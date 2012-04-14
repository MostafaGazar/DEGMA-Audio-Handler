package UI;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Toolkit;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JProgressBar;
import javax.swing.SwingUtilities;

public class WaitingDialog extends JDialog {// Window{//
	private static final long serialVersionUID = 1L;

	public WaitingDialog(JFrame parent, String title) {
		super(parent);// , true
		setLayout(new FlowLayout());
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		// setTitle(title);
		// setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

		/*
		 * ImageIcon waitingImg = new
		 * ImageIcon(HoldingDialog.class.getResource(Messages
		 * .getString("Constants.waitingImg"))); add(new JLabel(waitingImg));
		 */

		JProgressBar aProgressBar = new JProgressBar();
		aProgressBar.setStringPainted(true);
		aProgressBar.setString(title);
		aProgressBar.setIndeterminate(true);
		this.add(aProgressBar);

		this.pack();
		Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation((d.width / 2) - (this.getWidth() / 2), (d.height / 2)
				- (this.getHeight() / 2));

		this.setResizable(false);
		// this.setVisible(true);
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				WaitingDialog holdingDialog = new WaitingDialog(new JFrame(),
						"test");
				holdingDialog.setVisible(true);
			}
		});
	}

}
/*
 * package UI;
 * 
 * import java.awt.Component; import java.awt.Dimension; import
 * java.awt.FlowLayout; import java.awt.Toolkit;
 * 
 * import javax.swing.JDialog; import javax.swing.JFrame; import
 * javax.swing.JProgressBar; import javax.swing.JWindow;
 * 
 * public class WaitingDialog extends JDialog implements Runnable{//Window{//
 * Thread mainThread = null; public WaitingDialog(JFrame parent, String title){
 * super(parent); setLayout(new FlowLayout()); //setTitle(title);
 * //setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
 * 
 * JProgressBar aProgressBar = new JProgressBar();
 * aProgressBar.setStringPainted(true); aProgressBar.setString(title);
 * aProgressBar.setIndeterminate(true); add(aProgressBar);
 * 
 * pack(); Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
 * setLocation
 * ((d.width/2)-(this.getWidth()/2),(d.height/2)-(this.getHeight()/2));
 * 
 * mainThread = new Thread(WaitingDialog.this); mainThread.start();
 * //setResizable(false); } public void run() { while(true){
 * if(WaitingDialog.this.isVisible()) repaint(); try { Thread.sleep(500); }
 * catch (InterruptedException e) { e.printStackTrace(); } } } public static
 * void main(String[] args) { WaitingDialog holdingDialog = new
 * WaitingDialog(new JFrame(), "test"); holdingDialog.setVisible(true); }
 * 
 * }
 */