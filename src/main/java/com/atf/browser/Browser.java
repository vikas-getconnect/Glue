package com.atf.browser;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.util.Arrays;

import static org.openqa.selenium.chrome.ChromeDriverService.CHROME_DRIVER_EXE_PROPERTY;
import static org.openqa.selenium.ie.InternetExplorerDriverService.IE_DRIVER_EXE_PROPERTY;


/**
 * enum class which maps browser with respective capabilities
 */
public enum Browser {
    htmlunit("default"),
    firefox("firefox"),
    chrome("chrome"),
    safari("safari"),
    internet_explorer("ie"),
    PHANTOMJS("phantomjs");

    private String browserName;

    Browser(String browserName) {
        this.browserName = browserName;
    }

    public static Browser getBrowser(String name) {
        for (Browser browser : values()) {
            String browserName = browser.getName();
            if (browserName.equalsIgnoreCase(name)) {
                return browser;
            }
        }
        return htmlunit;
    }

    public String getName() {
        return this.browserName;
    }

    /***
     * This method returns the browser and capabilities
     * @param browser
     * @param capabilities
     * */
    public WebDriver getWebDriver(Browser browser, DesiredCapabilities capabilities) {
        WebDriver driver;
        System.out.println("Existing web driver");
        System.out.println("Browser is" + browser);
        System.out.println("Capabilities are" + capabilities);
        driver = BrowserMapper.getDriver(browser, capabilities);
        driver.manage().window().maximize();
        return driver;
    }

    /***
     * Sets the capabilities based on browser type
     * @param capabilities
     * @param browser
     * */
    public Browser setDesiredCapabilities(DesiredCapabilities capabilities, Browser browser) {
        String browserName = browser.name();
        System.out.println("Browser Name is" + browserName);
        capabilities.setBrowserName(browserName);
        if (browserName == "CHROME" || browserName == "chrome") {
            setChromeCapabilities(capabilities);

        }
        if (browserName == "HTMLUNIT" || browserName == "htmlunit") {
            capabilities.setJavascriptEnabled(true);
        }

        if (browserName == "FIREFOX" || browserName == "firefox") {
            setFirefoxCapabilities(capabilities);
        }

        if (browserName == "SAFARI" || browserName == "safari") {
            setSafariCapabilities(capabilities);
        }

        if (browserName == "IE" || browserName == "ie" || browserName == "Internet Explorer" || browserName == "internet_explorer") {
            setIeCapabilities(capabilities);
        }

        return browser;
    }


    /**
     * Set chrome capabilities
     *
     * @param capabilities
     */
    private void setChromeCapabilities(DesiredCapabilities capabilities) {
        capabilities.setCapability("chrome.switches", Arrays.asList("--start-maximized"));
        capabilities.setCapability(CHROME_DRIVER_EXE_PROPERTY, System.getProperty("user.dir") + "/src/main/resources/drivers/chromedriver");
        capabilities.setCapability(CapabilityType.TAKES_SCREENSHOT, true);
    }

    private void setSafariCapabilities(DesiredCapabilities capabilities) {
        capabilities.setCapability(CapabilityType.TAKES_SCREENSHOT, true);
    }


    /**
     * Set firefox capabilities
     *
     * @param capabilities
     */
    private void setFirefoxCapabilities(DesiredCapabilities capabilities) {
        FirefoxProfile firefoxProfile;

        firefoxProfile = new FirefoxProfile();
        System.out.println("New firefox profile created");

        firefoxProfile.setPreference("browser.download.folderList", 2);

        firefoxProfile.setPreference("browser.download.manager.showWhenStarting", false);
        firefoxProfile.setPreference("browser.helperApps.alwaysAsk.force", false);
        firefoxProfile.setPreference("browser.download.manager.showWhenStarting", false);
        firefoxProfile.setPreference("browser.download.manager.alertOnEXEOpen", false);
        firefoxProfile.setPreference("browser.helperApps.neverAsk.saveToDisk", "text/csv,application/octet-stream,dmg");
        firefoxProfile.setPreference("plugin.state.rbjninstallplugin", 2);
        firefoxProfile.setPreference("plugin.state.rbjnplugin", 2);
        firefoxProfile.setPreference("plugin.state.npbjninstallplugin", 2);
        firefoxProfile.setPreference("plugin.state.npbjnplugin", 2);
        firefoxProfile.setPreference("plugin.state.bjninstallplugin", 2);
        firefoxProfile.setPreference("plugin.state.bjnplugin", 2);
        firefoxProfile.setPreference("plugin.state.nprbjninstallplugin", 2);
        firefoxProfile.setPreference("plugin.state.nprbjnplugin", 2);

        capabilities.setCapability(FirefoxDriver.PROFILE, firefoxProfile);
        capabilities.setCapability(CapabilityType.TAKES_SCREENSHOT, true);
    }

    /**
     * Set IE capabilities
     *
     * @param capabilities
     */
    private void setIeCapabilities(DesiredCapabilities capabilities) {
        capabilities.setCapability(IE_DRIVER_EXE_PROPERTY, System.getProperty("user.dir") + "/src/main/resources/drivers/IEDriverServer.exe");
        capabilities.setCapability(
                InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS,
                true);
        capabilities.setJavascriptEnabled(true);

        capabilities.setCapability(CapabilityType.TAKES_SCREENSHOT, true);
    }

}