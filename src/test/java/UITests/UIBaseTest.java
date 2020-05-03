package UITests;

import com.atf.browser.Browser;
import com.atf.browser.BrowserMapper;
import com.atf.config.BrowserConfig;
import com.atf.ui.UiBase;
import org.fluentlenium.core.Fluent;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.firefox.ProfilesIni;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;

import java.io.File;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;

import static org.openqa.selenium.chrome.ChromeDriverService.CHROME_DRIVER_EXE_PROPERTY;
import static org.openqa.selenium.ie.InternetExplorerDriverService.IE_DRIVER_EXE_PROPERTY;

public class UIBaseTest {
    public static Logger logger = LoggerFactory.getLogger(UIBaseTest.class);
    WebDriver driver=null;


    @BeforeClass(alwaysRun = true)
    public void setUpUI() throws Exception {
        String type=BrowserConfig.getBrowserType();
        Browser browser = Browser.getBrowser(type);
        DesiredCapabilities desiredCapabilities=new DesiredCapabilities();
        browser=browser.setDesiredCapabilities(desiredCapabilities,browser);
        driver=browser.getWebDriver(browser,desiredCapabilities);
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
    }

    @AfterClass(alwaysRun = true)
    public void cleanUpUI(){
        driver.close();
    }
}
