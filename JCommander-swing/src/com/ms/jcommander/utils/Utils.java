package com.ms.jcommander.utils;

import java.io.File;

public class Utils {	
	
	public static final String SYSTEM_NAME = System.getProperty("os.name");
	
	public static final boolean IS_WINDOWS = SYSTEM_NAME.toLowerCase().startsWith("windows");
	
	private static final String SEPARATOR = IS_WINDOWS ? "\\" : "/";

	public static final long DOUBLE_CLICK_PERIOD = 500;
	
	public static String removeFileName(String path) {
		int lastSlash = path.lastIndexOf(SEPARATOR);
		if (lastSlash == path.length() -1) {
			lastSlash = path.substring(0, path.length() - 2).lastIndexOf(SEPARATOR);
		}
		if (IS_WINDOWS) {
			if (path.length() == 3) {
				return path;
			} else if (lastSlash <= 2) {
				lastSlash += 1;
			}
		}
		return path.substring(0, lastSlash);
		
	}

	public static String getRoot(String path) {
		for (File f : File.listRoots()) {
			if (path.startsWith(f.getAbsolutePath())) {
				return f.getAbsolutePath();
			}
		}
		return path;
	}

}
