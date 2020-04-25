package com.atf.utils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * 
 * Utilities
 * 
 * @author Srinivas R
 * 
 */
public class FileHandler {

	/**
	 * Method to delete File or Directory
	 * 
	 * @param path
	 */
	public static void deleteFileorDirectory(File path) {
		if (path.exists()) {
			File[] files = path.listFiles();
			for (int i = 0; i < files.length; i++) {
				if (files[i].isDirectory()) {
					deleteFileorDirectory(files[i]);
				} else {
					files[i].delete();
				}
			}
		}
		path.delete();
	}

	/**
	 * Method to return the files for the given datetime range
	 * 
	 * @param from
	 * @param to
	 */
	public static List<String> returnFiles(String from, String to) {
		List<String> strlist = new ArrayList<String>();

		File path = new File("Seamtemp");
		long begin = Long.parseLong(from.split("-")[1]);

		long end = Long.parseLong(to.split("-")[1]);
		if (path.exists()) {
			File[] files = path.listFiles();

			for (int i = 0; i < files.length; i++) {
				if ((files[i].toString().split("-")[0].split("/")[1]
						.equals(from.split("-")[0]))
						&& (Long.parseLong(files[i].toString().split("-")[1]
								.substring(0, 14)) >= begin && Long
								.parseLong(files[i].toString().split("-")[1]
										.substring(0, 14)) <= end))
					strlist.add(files[i].toString());

			}
		}

		return strlist;
	}
	
}





