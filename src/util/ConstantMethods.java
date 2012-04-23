package util;

import java.io.File;

/**
 * 
 * @author Mostafa Gazar, eng.mostafa.gazar@gmail.com
 */
public class ConstantMethods {

	public static String getExtension(File f) {
		String ext = null;
		String s = f.getName();
		int i = s.lastIndexOf('.');

		if (i > 0 && i < s.length() - 1) {
			ext = s.substring(i + 1).toLowerCase();
		}
		return ext;
	}

	public static String[] getLangs() {
		File folder = new File("lang");
		File[] listOfFiles = folder.listFiles();
		int length = listOfFiles.length;
		String[] listOfFilesNames = new String[length];

		for (int i = 0; i < length; i++) {
			if (listOfFiles[i].isFile()) {
				listOfFilesNames[i] = listOfFiles[i].getName().replace(
						".properties", "");
			}
		}

		return listOfFilesNames;
	}
}
