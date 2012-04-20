package function.convert.mp3ToWav;

//import org.eclipse.swt.SWT;
//import org.eclipse.swt.widgets.FileDialog;
//import org.eclipse.swt.widgets.Shell;

import javazoom.jl.converter.Converter;
import javazoom.jl.decoder.JavaLayerException;

/**
 * This class is using <b>jl1.0.jar</b> to convert mp3 files to wav files.
 * 
 * @author lordms12
 * @done 04-09-2007
 */
public class MP3_To_WAV {

	public MP3_To_WAV(String source, String dest) throws JavaLayerException {
		Converter aConverter = new Converter();
		aConverter.convert(source, dest);
		/*
		 * try { aConverter.convert(source, dest); } catch (JavaLayerException
		 * e) { e.printStackTrace(); }
		 */
	}

	// public static void main(String[] args) {
	// String runningAudio = "128.mp3";
	// System.out.println("Hi");
	// ///////////////////////////////////
	// JFrame.setDefaultLookAndFeelDecorated(true);
	// try {
	// UIManager.setLookAndFeel("org.jvnet.substance.skin.SubstanceBusinessBlueSteelLookAndFeel");
	// } catch (Exception e) {
	// e.printStackTrace();
	// }
	//
	// new NewMain(1).initUI();//any number
	//
	// NewMain.frame.toBack();
	// Shell s = new Shell();
	//
	// FileDialog fd = new FileDialog(s, SWT.SAVE);
	// fd.setText("Save");
	// fd.setFileName("*.mp3");
	// String[] filterExt = {"*.*", "*.mp3", "*.wav"};
	// fd.setFilterExtensions(filterExt);
	//
	// String dest = fd.open();
	// if(dest == null)
	// return;
	// //System.out.println(dest);
	// NewMain.frame.toFront();
	// ///////////////////////////////////
	// WaitingDialog holdingWd = new WaitingDialog(NewMain.frame,
	// Messages.getString("Constants.converting"));
	// // show the dialog
	// holdingWd.setVisible(true);
	//
	// if("mp3".equalsIgnoreCase(ConstantMethods.getExtension(new
	// File(runningAudio)))){//convert to wav to be able to cut
	// //System.out.println("need to convert first");
	// try {
	//				new MP3_To_WAV(runningAudio, Messages.getString("Constants.temp_cut"));
	// } catch (Exception e) {
	//				JOptionPane.showMessageDialog(NewMain.frame, Messages.getString("Constants.error")+e.getMessage(), Messages.getString("Constants.title_SorryButProcessCanNotCompleted"), 
	//						JOptionPane.ERROR_MESSAGE);
	// holdingWd.dispose();//setVisible(false);
	// }
	// runningAudio = Messages.getString("Constants.temp_cut");//TODO make
	// random instead of 1
	// }
	//		String arg[]={"-o", Messages.getString("Constants.file")+dest, Messages.getString("Constants.file")+runningAudio};//, "-s", "5000", "-e", "10000","-s", "50000", "-e", "100000"};
	// int len=arg.length+0;
	// int len1=arg.length;
	// //System.out.println("countSplitTime = "+(countSplitTime+1)/4);
	// String toSend[]=new String[len];
	// for(int i=0;i<len1;i++){
	// toSend[i]=arg[i];
	// }
	//
	// for(int i=0;i<len;i++){
	//			System.out.print(toSend[i]+"  ");
	// }
	//
	// CutWithNoUI.cut(toSend);//,holdingWd);
	// holdingWd.dispose();
	// }
	// /*public static void main(String[] args) {
	// Converter aConverter=new Converter();
	// try {
	// aConverter.convert("main128.mp3", "out_2.wav");
	// } catch (JavaLayerException e) {
	// e.printStackTrace();
	// }
	// }*/

}
