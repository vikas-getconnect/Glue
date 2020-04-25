package UITests;

import com.atf.browser.Browser;
import com.atf.browser.BrowserMapper;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.firefox.ProfilesIni;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;

import java.io.File;
import java.util.Arrays;

import static org.openqa.selenium.chrome.ChromeDriverService.CHROME_DRIVER_EXE_PROPERTY;
import static org.openqa.selenium.ie.InternetExplorerDriverService.IE_DRIVER_EXE_PROPERTY;

public class UIBaseTest {

    @BeforeSuite
    public void suiteSetup(){

    }

    @BeforeClass
    public void setUp(){
        Browser browser = Browser.getBrowser("chrome");
        DesiredCapabilities desiredCapabilities=new DesiredCapabilities();
        browser=setDesiredCapabilities(desiredCapabilities,browser);
        WebDriver driver=getWebDriver(browser,desiredCapabilities);
        driver.get("https://www.google.com/");


    }

    private WebDriver getWebDriver(Browser browser, DesiredCapabilities capabilities) {
        WebDriver driver;
        System.out.println("Existing web driver");
        System.out.println("Browser is" + browser);
        System.out.println("Capabilities are" + capabilities);
        driver = BrowserMapper.getDriver(browser, capabilities);
        driver.manage().window().maximize();
        return driver;
    }


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


    private void setChromeCapabilities(DesiredCapabilities capabilities) {
        capabilities.setCapability("chrome.switches", Arrays.asList("--start-maximized"));
        capabilities.setCapability("chrome.switches", Arrays.asList("--use-fake-ui-for-media-stream"));
//        File chromedriver = new ChromeDriverDownloader().downloadAndExtract();
//        System.out.println("Chrome driver is extracted to" + chromedriver.getAbsolutePath());
        capabilities.setCapability(CHROME_DRIVER_EXE_PROPERTY, "/Users/vikaschaudhary/workspace/Glue/src/main/resources/drivers/chromedriver");
        capabilities.setCapability(CapabilityType.TAKES_SCREENSHOT, true);
    }

    private void setSafariCapabilities(DesiredCapabilities capabilities) {
        capabilities.setCapability(CapabilityType.TAKES_SCREENSHOT, true);
    }


    private void setFirefoxCapabilities(DesiredCapabilities capabilities) {
        FirefoxProfile firefoxProfile;
        ProfilesIni allProfiles = new ProfilesIni();

        if(System.getProperty("envProperties")!=null && System.getProperty("envProperties").contains("lync") || System.getProperty("envProperties").contains("skype")) {
            firefoxProfile=allProfiles.getProfile("lync");
        }
        else if(System.getProperty("envProperties")!=null && System.getProperty("envProperties").contains("jabber")) {
            firefoxProfile=allProfiles.getProfile("jabber");
        }else {
            firefoxProfile = new FirefoxProfile();
            System.out.println("New firefox profile created");
        }

        firefoxProfile.setPreference("browser.download.folderList", 2);

        firefoxProfile.setPreference("browser.download.manager.showWhenStarting", false);
        // Newly added code
        firefoxProfile.setPreference("browser.helperApps.alwaysAsk.force", false);
        firefoxProfile.setPreference("browser.download.manager.showWhenStarting",false);
        firefoxProfile.setPreference("browser.download.manager.alertOnEXEOpen", false);
        // Newly added code
        firefoxProfile.setPreference("browser.helperApps.neverAsk.saveToDisk", "text/csv,application/octet-stream,dmg");
        //firefoxProfile.setPreference("browser.helperApps.neverAsk.saveToDisk", "text/csv");
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


    private void setIeCapabilities(DesiredCapabilities capabilities) {
//        File internetExplorerExe = new IEDriverDownloader().downloadAndExtract();
//        System.out.println("IE driver is extracted to" + internetExplorerExe.getAbsolutePath());
//        capabilities.setCapability(IE_DRIVER_EXE_PROPERTY, internetExplorerExe.getAbsolutePath());
//        capabilities.setCapability(
//                InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS,
//                true);
//        capabilities.setJavascriptEnabled(true);
//
//        capabilities.setCapability(CapabilityType.TAKES_SCREENSHOT, true);
   }
}
