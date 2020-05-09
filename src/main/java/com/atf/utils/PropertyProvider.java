package com.atf.utils;

import java.io.*;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Utility to read values from a property file , which can be used to define
 * configuration details.
 * 
 */

public class PropertyProvider {
	
	protected static Logger logger = LoggerFactory.getLogger(PropertyProvider.class);
	private Properties dictionary;

	public PropertyProvider(String fileName) {
		dictionary = new Properties();
		try {
			File initialFile = new File(fileName);
			InputStream in = new FileInputStream(initialFile);
			if (in == null)
				throw new IllegalArgumentException("file not found!");
			dictionary.load(in);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public String getProperty(String key) {
		return dictionary.getProperty(key);
	}

	public void setProperty(String key, String value) { dictionary.setProperty(key,value);}

	public static void main(String[] args) {

		logger.info("");
		PropertyProvider propertyProvider = new PropertyProvider(
				"configs/api/default.properties");

		String q1 = propertyProvider.getProperty("baseUrl");

		logger.info("IP for q2 env is: " + q1);

	}

}
