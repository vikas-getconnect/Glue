package com.atf.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;

public abstract class BrowserConfig {
    public static final String BROWSER_TYPE = "browser.type";
    public static final String TEST_TAG = "browser.test.tag";
    public static final String username = "browser.username";
    public static final String email = "browser.email";
    public static final String password = "browser.password";
    public static final String participant_passcode = "browser.participant_passcode";
    public static final String moderator_passcode = "browser.moderator_passcode";
    public static final String BROWSER_PROPERTIES = "browser.properties";
    public static final String HUB_URL = "hub.url";
    public static final String HUB_URL_MULTIMONITOR = "hub.url.multimonitor";
    public static final String ENV_PROP = "envProperties";
    public static final String ELEMENT_MIN_TIMEOUT = "element.mintimeout";
    public static final String ELEMENT_MAX_TIMEOUT = "element.maxtimeout";
    public static final String PAGELOAD_MAX_TIMEOUT = "pageload.maxtimeout";

    private static final String MODERATOR_BROWSER_TYPE = "moderatorBrowserType";
    private static final String PARTICIPANT_BROWSER_TYPE = "participantBrowserType";
    private static final String MODERATOR_BROWSER_IP = "moderatorIp";
    private static final String PARTICIPANT_BROWSER_IP = "participantIp";
    private static final String WEBRTC_BROWSER_IP = "webrtcIp";
    private static final String SKINNY_BROWSER_IP = "skinnyIp";
    private static final String CARMEL_IP = "carmelIp";
    private static final String SECURITY_ENABLE_PROXY = "securityProxyEnable";
    private static final String SECURITY_PROXY_SERVER = "securityProxyServer";
    private static final String SECURITY_PROXY_PORT = "securityProxyPort";
    public static final String HIGH_DIM_X = "highdimx";
    public static final String HIGH_DIM_Y = "higdimy";
    public static final String MEDIUM_DIM_X = "mediumdimx";
    public static final String MEDIUM_DIM_Y = "mediumdimy";
    public static final String LOW_DIM_X = "lowdimx";
    public static final String LOW_DIM_Y = "lowdimy";
    public static final String DIM_X = "dimx";
    public static final String DIM_Y = "dimy";

    public static Logger LOGGER = LoggerFactory.getLogger("StaticLogger");
    public static String separator = System.getProperty("file.separator");

    private static String resourceFile = System.getProperty("PROPERTY_FILE");

    public static String getModerator_BrowserType() throws Exception {
        Properties properties = getProperties();
        return System.getProperty(MODERATOR_BROWSER_TYPE) != null ?
                System.getProperty(MODERATOR_BROWSER_TYPE) : properties.getProperty(MODERATOR_BROWSER_TYPE);
    }

    public static String getParticipant_BrowserType() throws Exception {
        Properties properties = getProperties();
        return System.getProperty(PARTICIPANT_BROWSER_TYPE) != null ?
                System.getProperty(PARTICIPANT_BROWSER_TYPE) : properties.getProperty(PARTICIPANT_BROWSER_TYPE);
    }
    public static String getModerator_BrowserIP() throws Exception {
        Properties properties = getProperties();
        return System.getProperty(MODERATOR_BROWSER_IP) != null ?
                System.getProperty(MODERATOR_BROWSER_IP) : properties.getProperty(MODERATOR_BROWSER_IP);
    }
    public static String getWebrtc_BrowserIP() throws Exception {
        Properties properties = getProperties();
        return System.getProperty(WEBRTC_BROWSER_IP) != null ?
                System.getProperty(WEBRTC_BROWSER_IP) : properties.getProperty(WEBRTC_BROWSER_IP);
    }
    public static String getSkinny_BrowserIP() throws Exception {
        Properties properties = getProperties();
        return System.getProperty(SKINNY_BROWSER_IP) != null ?
                System.getProperty(SKINNY_BROWSER_IP) : properties.getProperty(SKINNY_BROWSER_IP);
    }
    public static String getCarmelIP() throws Exception {
        Properties properties = getProperties();
        return System.getProperty(CARMEL_IP) != null ?
                System.getProperty(CARMEL_IP) : properties.getProperty(CARMEL_IP);
    }
    public static String getParticipant_BrowserIP() throws Exception {
        Properties properties = getProperties();
        return System.getProperty(PARTICIPANT_BROWSER_IP) != null ?
                System.getProperty(PARTICIPANT_BROWSER_IP) : properties.getProperty(PARTICIPANT_BROWSER_IP);
    }

    public static String getSecurityEnableStatus() throws Exception {
        Properties properties = getProperties();
        return System.getProperty(SECURITY_ENABLE_PROXY) != null ?
                System.getProperty(SECURITY_ENABLE_PROXY) : properties.getProperty(SECURITY_ENABLE_PROXY);
    }

    public static String getSecurityProxyServerIP() throws Exception {
        Properties properties = getProperties();
        return System.getProperty(SECURITY_PROXY_SERVER) != null ?
                System.getProperty(SECURITY_PROXY_SERVER) : properties.getProperty(SECURITY_PROXY_SERVER);
    }

    public static String getSecurityProxyServerPort() throws Exception {
        Properties properties = getProperties();
        return System.getProperty(SECURITY_PROXY_PORT) != null ?
                System.getProperty(SECURITY_PROXY_PORT) : properties.getProperty(SECURITY_PROXY_PORT);
    }

    public static String getHubURL() throws Exception {
        Properties properties = getProperties();
        LOGGER.debug("Asked Hub Url is " + System.getProperty(HUB_URL));
        return System.getProperty(HUB_URL) != null ?
                System.getProperty(HUB_URL) : properties.getProperty(HUB_URL);
    }

    public static String getHubURLForMultiMonitor() throws Exception {
        Properties properties = getProperties();
        LOGGER.debug("Asked Hub Url is " + System.getProperty(HUB_URL_MULTIMONITOR));
        return System.getProperty(HUB_URL_MULTIMONITOR) != null ?
                System.getProperty(HUB_URL_MULTIMONITOR) : properties.getProperty(HUB_URL_MULTIMONITOR);
    }

    public static String getEnvProp() throws Exception {
        Properties properties = getProperties();
        return System.getProperty(ENV_PROP) != null ?
                System.getProperty(ENV_PROP) : properties.getProperty(ENV_PROP);
    }

    public static String getBrowserType() throws Exception {
        Properties properties = getProperties();
        return System.getProperty(BROWSER_TYPE) != null ?
                System.getProperty(BROWSER_TYPE) : properties.getProperty(BROWSER_TYPE);
    }

    public static String getTestTags() throws Exception {
        Properties properties = getProperties();
        return System.getProperty(TEST_TAG) != null ?
                System.getProperty(TEST_TAG) : properties.getProperty(TEST_TAG);
    }

    private static Properties props;
    private static Properties getProperties() throws Exception {
        ///// new code added starts - This ensures that the property file is only loaded once.
        if(props==null) {
            props = loadGradleResource(resourceFile);
        }
        return props;
        ///// new code added ends
        //return ResourceHandler.loadGradleResource(resourceFile);
    }

    public static String getBrowserProperties() throws Exception {
        Properties properties = getProperties();
        return System.getProperty(BROWSER_PROPERTIES) != null ?
                System.getProperty(BROWSER_PROPERTIES) : properties.getProperty(BROWSER_PROPERTIES);
    }

    public static int getElementMinTimeout() throws Exception {
        Properties properties = getProperties();
        return Integer.parseInt(System.getProperty(ELEMENT_MIN_TIMEOUT) != null ?
                System.getProperty(ELEMENT_MIN_TIMEOUT) : properties.getProperty(ELEMENT_MIN_TIMEOUT));
    }

    public static int getElementMaxTimeout() throws Exception {
        Properties properties = getProperties();
        return Integer.parseInt(System.getProperty(ELEMENT_MAX_TIMEOUT) != null ?
                System.getProperty(ELEMENT_MAX_TIMEOUT) : properties.getProperty(ELEMENT_MAX_TIMEOUT));
    }

    public static int getPageLoadMaxTimeOut() throws Exception {
        Properties properties = getProperties();
        return Integer.parseInt(System.getProperty(PAGELOAD_MAX_TIMEOUT) != null ?
                System.getProperty(PAGELOAD_MAX_TIMEOUT) : properties.getProperty(PAGELOAD_MAX_TIMEOUT));
    }

    public static int getHighDimX() throws Exception {
        Properties properties = getProperties();
        return Integer.parseInt(System.getProperty(HIGH_DIM_X) != null ?
                System.getProperty(HIGH_DIM_X) : properties.getProperty(HIGH_DIM_X));
    }

    public static int getHighDimY() throws Exception {
        Properties properties = getProperties();
        return Integer.parseInt(System.getProperty(HIGH_DIM_Y) != null ?
                System.getProperty(HIGH_DIM_Y) : properties.getProperty(HIGH_DIM_Y));
    }

    public static int getMediumDimX() throws Exception {
        Properties properties = getProperties();
        return Integer.parseInt(System.getProperty(MEDIUM_DIM_X) != null ?
                System.getProperty(MEDIUM_DIM_X) : properties.getProperty(MEDIUM_DIM_X));
    }

    public static int getMediumDimY() throws Exception {
        Properties properties = getProperties();
        return Integer.parseInt(System.getProperty(MEDIUM_DIM_Y) != null ?
                System.getProperty(MEDIUM_DIM_Y) : properties.getProperty(MEDIUM_DIM_Y));
    }

    public static int getLowDimX() throws Exception {
        Properties properties = getProperties();
        return Integer.parseInt(System.getProperty(LOW_DIM_X) != null ?
                System.getProperty(LOW_DIM_X) : properties.getProperty(LOW_DIM_X));
    }

    public static int getLowDimY() throws Exception {
        Properties properties = getProperties();
        return Integer.parseInt(System.getProperty(LOW_DIM_Y) != null ?
                System.getProperty(LOW_DIM_Y) : properties.getProperty(LOW_DIM_Y));
    }

    public static String getDimX() throws Exception {
        Properties properties = getProperties();
        return System.getProperty(DIM_X) != null ?
                System.getProperty(DIM_X) : properties.getProperty(DIM_X);
    }

    public static String getDimY() throws Exception {
        Properties properties = getProperties();
        return System.getProperty(DIM_Y) != null ?
                System.getProperty(DIM_Y) : properties.getProperty(DIM_Y);
    }

    protected BrowserConfig() {
        // do nothing
    }

    public static Properties loadGradleResource(String fileName) throws Exception {
        File resourceFile = new File(fileName);
        if (resourceFile.exists()) {
            Properties properties = new Properties();
            FileInputStream props = new FileInputStream(resourceFile);
            properties.load(props);
            return properties;
        } else {
            BrowserConfig.LOGGER.info("File doesn't exist in location: " + resourceFile.getAbsolutePath());
            System.out.println("File doesn't exist in location: " + resourceFile.getAbsolutePath());
        }
        return null;
    }

}
