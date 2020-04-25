package com.atf.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Reads configuration file and sets the common properties required to run seam
 * tests.
 * 
 *
 */
public class APIConfigReader {

	protected static Logger logger = LoggerFactory
			.getLogger(APIConfigReader.class);
	private static PropertyProvider propertyProvider;

	private static final String BJN_BASELOCATION = "configs/api/";


	


	public APIConfigReader() {

		String env = System.getProperty("env");
		String projectName= System.getProperty("project");

		if (env == null) {
			env = "local";
		}
		String propFile=null; 
		if(projectName==null && env.equals("local"))
			propFile =  BJN_BASELOCATION +"default.properties";
		else 
			propFile = BJN_BASELOCATION + "env/"+projectName+"/bjn_" + projectName + "_"
					+ env + ".properties";

		propertyProvider = new PropertyProvider(propFile);

	}

	public APIConfigReader(String projectName) {

		String env = System.getProperty("env");

		if (env == null) {
			env = "local";
		}

		String propFile = BJN_BASELOCATION + "env/"+projectName+"/bjn_" + projectName + "_"
				+ env + ".properties";

		propertyProvider = new PropertyProvider(propFile);

	}

	/**
	 * @param key
	 * @param regex
	 * @return list of values
	 * 
	 * This method can be use to get mulitple values from config file, based on regex as the split 
	 * parameter
	 * E.g. partitions=live,notlive 
	 */
	public String[] getValues(String key,String regex) { 
		String[] value;
		value = propertyProvider.getProperty(key).split(regex);
		return value;

	}
	
	

	public String getValue(String key) {

		String value;
		value = propertyProvider.getProperty(key);
		return value;

	}

	public void updateProperties(String key,String newValue){
		String env = System.getProperty("env");
		String propFile = BJN_BASELOCATION + "env/bjn_" + env + ".properties";
		PropertyProvider newPropertyProvider  = new PropertyProvider(propFile);
		newPropertyProvider.setProperty(key,newValue);
	}

}