package com.atf.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Reads configuration file and sets the common properties required to run
 * tests.
 */
public class ConfigReader {

    private static final String CONFIGLOCATION = System.getProperty("user.dir") + "/src/test/resources/configs/";
    protected static Logger logger = LoggerFactory
            .getLogger(ConfigReader.class);
    private static PropertyProvider propertyProvider;


    public ConfigReader() {

        String env = System.getProperty("env");
        String type = System.getProperty("type");

        if (type == null || !(type.equals("ui") || type.equals("api"))) {
            logger.error("Pass correct type as 'api' or 'ui'");
            System.exit(1);
        }
        if (env == null) {
            env = "local";
        }
        String propFile = null;
        if (env.equals("local"))
            propFile = CONFIGLOCATION + type + "/default.properties";
        else
            propFile = CONFIGLOCATION + type + "/" + env + ".properties";

        propertyProvider = new PropertyProvider(propFile);

    }

    /**
     * @param key
     * @param regex
     * @return list of values
     * <p>
     * This method can be use to get mulitple values from config file, based on regex as the split
     * parameter
     * E.g. partitions=live,notlive
     */
    public String[] getValues(String key, String regex) {
        String[] value;
        value = propertyProvider.getProperty(key).split(regex);
        return value;

    }


    public String getValue(String key) {

        String value;
        value = propertyProvider.getProperty(key);
        return value;

    }

    public void updateProperties(String key, String newValue) {
        String env = System.getProperty("env");
        String type = System.getProperty("type");
        if (env == null) {
            env = "local";
        }
        String propFile = null;
        if (env.equals("local"))
            propFile = CONFIGLOCATION + type + "/default.properties";
        else
            propFile = CONFIGLOCATION + type + "/" + env + ".properties";
        PropertyProvider newPropertyProvider = new PropertyProvider(propFile);
        newPropertyProvider.setProperty(key, newValue);
    }

}