package com.atf.browser;

import org.openqa.selenium.Capabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
//import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.safari.SafariDriver;

import java.util.HashMap;
import java.util.Map;


public class BrowserMapper {
    private String name;

    private static Map<Browser, WebDriverLazyLoader> browserMapper = new HashMap<Browser, WebDriverLazyLoader>();
    private static Map<Browser, WebDriver> webDriverInstances = new HashMap<Browser, WebDriver>();


    static {
        browserMapper.put(Browser.chrome, new WebDriverLazyLoader(ChromeDriver.class));
        browserMapper.put(Browser.internet_explorer, new WebDriverLazyLoader(InternetExplorerDriver.class));
        browserMapper.put(Browser.firefox, new WebDriverLazyLoader(FirefoxDriver.class));
        browserMapper.put(Browser.safari, new WebDriverLazyLoader(SafariDriver.class));
        browserMapper.put(Browser.htmlunit, new WebDriverLazyLoader(HtmlUnitDriver.class));
        //browserMapper.put(Browser.PHANTOMJS, new WebDriverLazyLoader(PhantomJSDriver.class));
    }

    public static WebDriver getDriver(Browser browser, Capabilities capabilities) {
        WebDriverLazyLoader webDriverLazyLoader = browserMapper.get(browser);
        if (webDriverLazyLoader != null) {
            if (browser == Browser.PHANTOMJS) {
                return webDriverLazyLoader.getWebDriverClass(capabilities);

            } else if (browser == Browser.chrome) {
                System.out.println("HEY...Browser capabilites" + capabilities.toString());
                System.setProperty("webdriver.chrome.driver", (String) capabilities.getCapability("webdriver.chrome.driver"));
                return webDriverLazyLoader.getWebDriverClass(capabilities);

            } else if (browser == Browser.firefox) {
                return webDriverLazyLoader.getWebDriverClass(capabilities);

            } else if (browser == Browser.safari) {
                return webDriverLazyLoader.getWebDriverClass(capabilities);

            } else if (browser == Browser.internet_explorer) {
                System.out.println("HEY...Browser capabilites" + capabilities.toString());
                System.setProperty("webdriver.ie.driver.extractpath", (String) capabilities.getCapability("webdriver.ie.driver"));
                return webDriverLazyLoader.getWebDriverClass(capabilities);


            } else {
                return webDriverLazyLoader.getWebDriverClass();
            }
        }
        return browserMapper.get(Browser.htmlunit).getWebDriverClass();
    }
}