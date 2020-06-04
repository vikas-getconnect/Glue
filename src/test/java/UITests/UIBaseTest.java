package UITests;

import com.atf.browser.Browser;
import com.atf.config.BrowserConfig;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

import java.util.concurrent.TimeUnit;

public class UIBaseTest {
    public static Logger logger = LoggerFactory.getLogger(UIBaseTest.class);
    WebDriver driver = null;


    @BeforeClass(alwaysRun = true)
    public void setUpUI() throws Exception {
        String type = BrowserConfig.getBrowserType();
        Browser browser = Browser.getBrowser(type);
        DesiredCapabilities desiredCapabilities = new DesiredCapabilities();
        browser = browser.setDesiredCapabilities(desiredCapabilities, browser);
        driver = browser.getWebDriver(browser, desiredCapabilities);
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
    }

    @AfterClass(alwaysRun = true)
    public void cleanUpUI() {
        driver.close();
    }
}
