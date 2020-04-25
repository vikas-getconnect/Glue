package com.atf.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
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
			ClassLoader cl = getClass().getClassLoader();
			InputStream in = cl.getResourceAsStream(fileName);
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
