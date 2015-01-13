package ecommerce;

import java.io.File;

public class FileAccess {

	static private String destFolder;
	static public void setDestFolder(String val){
		destFolder = val;
	}

	public static boolean Move(File srcFile, String destPath) {
		File dir = new File(destPath);
		boolean success = srcFile.renameTo(new File(dir, srcFile.getName()));
		return success;
	}

	public static boolean Move(String srcFile, String fileName) {

		File file = new File(srcFile);
		File dir = new File(destFolder);
		boolean success = file.renameTo(new File(dir, fileName));
		return success;
	}
}
