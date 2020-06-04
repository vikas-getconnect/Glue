package com.atf.config;

import com.atf.utils.ConfigReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Reads the UI/Browser properties file params
 */
public abstract class BrowserConfig {
    public static final String BROWSER_TYPE = "browser.type";
    public static final String username = "browser.username";
    public static final String email = "browser.email";
    public static final String password = "browser.password";
    public static final String BROWSER_PROPERTIES = "browser.properties";
    public static final String ELEMENT_MIN_TIMEOUT = "element.mintimeout";
    public static final String ELEMENT_MAX_TIMEOUT = "element.maxtimeout";
    public static final String PAGELOAD_MAX_TIMEOUT = "pageload.maxtimeout";

    public static final String HIGH_DIM_X = "highdimx";
    public static final String HIGH_DIM_Y = "higdimy";
    public static final String MEDIUM_DIM_X = "mediumdimx";
    public static final String MEDIUM_DIM_Y = "mediumdimy";
    public static final String LOW_DIM_X = "lowdimx";
    public static final String LOW_DIM_Y = "lowdimy";
    public static final String DIM_X = "dimx";
    public static final String DIM_Y = "dimy";

    public static final String URL = "app.url";

    public static Logger logger = LoggerFactory.getLogger(BrowserConfig.class);

    private static ConfigReader conf = null;

    protected BrowserConfig() {
        // do nothing
    }

    private static ConfigReader getProperties() throws Exception {
        // new code added starts - This ensures that the property file is only loaded once.
        if (conf == null) {
            conf = new ConfigReader();
        }
        return conf;
    }

    public static String getBrowserType() throws Exception {
        ConfigReader properties = getProperties();
        return System.getProperty(BROWSER_TYPE) != null ?
                System.getProperty(BROWSER_TYPE) : properties.getValue(BROWSER_TYPE);
    }

    public static String getBrowserProperties() throws Exception {
        ConfigReader properties = getProperties();
        return System.getProperty(BROWSER_TYPE) != null ?
                System.getProperty(BROWSER_TYPE) : properties.getValue(BROWSER_TYPE);
    }

    public static int getElementMinTimeout() throws Exception {
        ConfigReader properties = getProperties();
        return Integer.parseInt(System.getProperty(ELEMENT_MIN_TIMEOUT) != null ?
                System.getProperty(ELEMENT_MIN_TIMEOUT) : properties.getValue(ELEMENT_MIN_TIMEOUT));
    }

    public static int getElementMaxTimeout() throws Exception {
        ConfigReader properties = getProperties();
        return Integer.parseInt(System.getProperty(ELEMENT_MAX_TIMEOUT) != null ?
                System.getProperty(ELEMENT_MAX_TIMEOUT) : properties.getValue(ELEMENT_MAX_TIMEOUT));
    }

    public static int getPageLoadMaxTimeOut() throws Exception {
        ConfigReader properties = getProperties();
        return Integer.parseInt(System.getProperty(PAGELOAD_MAX_TIMEOUT) != null ?
                System.getProperty(PAGELOAD_MAX_TIMEOUT) : properties.getValue(PAGELOAD_MAX_TIMEOUT));
    }

    public static int getHighDimX() throws Exception {
        ConfigReader properties = getProperties();
        return Integer.parseInt(System.getProperty(HIGH_DIM_X) != null ?
                System.getProperty(HIGH_DIM_X) : properties.getValue(HIGH_DIM_X));
    }

    public static int getHighDimY() throws Exception {
        ConfigReader properties = getProperties();
        return Integer.parseInt(System.getProperty(HIGH_DIM_Y) != null ?
                System.getProperty(HIGH_DIM_Y) : properties.getValue(HIGH_DIM_Y));
    }

    public static int getMediumDimX() throws Exception {
        ConfigReader properties = getProperties();
        return Integer.parseInt(System.getProperty(MEDIUM_DIM_X) != null ?
                System.getProperty(MEDIUM_DIM_X) : properties.getValue(MEDIUM_DIM_X));
    }

    public static int getMediumDimY() throws Exception {
        ConfigReader properties = getProperties();
        return Integer.parseInt(System.getProperty(MEDIUM_DIM_Y) != null ?
                System.getProperty(MEDIUM_DIM_Y) : properties.getValue(MEDIUM_DIM_Y));
    }

    public static int getLowDimX() throws Exception {
        ConfigReader properties = getProperties();
        return Integer.parseInt(System.getProperty(LOW_DIM_X) != null ?
                System.getProperty(LOW_DIM_X) : properties.getValue(LOW_DIM_X));
    }

    public static int getLowDimY() throws Exception {
        ConfigReader properties = getProperties();
        return Integer.parseInt(System.getProperty(LOW_DIM_Y) != null ?
                System.getProperty(LOW_DIM_Y) : properties.getValue(LOW_DIM_Y));
    }

    public static String getDimX() throws Exception {
        ConfigReader properties = getProperties();
        return System.getProperty(DIM_X) != null ?
                System.getProperty(DIM_X) : properties.getValue(DIM_X);
    }

    public static String getDimY() throws Exception {
        ConfigReader properties = getProperties();
        return System.getProperty(DIM_Y) != null ?
                System.getProperty(DIM_Y) : properties.getValue(DIM_Y);
    }

    public static String getUrl() throws Exception {
        ConfigReader properties = getProperties();
        return System.getProperty(URL) != null ?
                System.getProperty(URL) : properties.getValue(URL);
    }

    public static String getEmail() throws Exception {
        ConfigReader properties = getProperties();
        return System.getProperty(email) != null ?
                System.getProperty(email) : properties.getValue(email);
    }

    public static String getPassword() throws Exception {
        ConfigReader properties = getProperties();
        return System.getProperty(password) != null ?
                System.getProperty(password) : properties.getValue(password);
    }

    public static String getUsername() throws Exception {
        ConfigReader properties = getProperties();
        return System.getProperty(username) != null ?
                System.getProperty(username) : properties.getValue(username);
    }

}
