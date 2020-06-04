package UITests;

import UITests.Pages.LoginPage;
import UITests.Pages.MyAccount;
import com.atf.config.BrowserConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.Test;


public class MockUITest extends UIBaseTest {
    public static Logger logger = LoggerFactory.getLogger(MockUITest.class);

    public String getUrl() {
        String url = null;
        try {
            url = BrowserConfig.getUrl();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return url;
    }


    @Test(groups = {"signin"})
    public void testSignIn() throws Exception {
        String url = getUrl();
        driver.get(url);
        LoginPage loginObj = new LoginPage(driver);
        MyAccount myAccountObj = loginObj.login(BrowserConfig.getEmail(), BrowserConfig.getPassword());
        String currentUrl = driver.getCurrentUrl();
        logger.info("Current url:" + currentUrl);
        Assert.assertEquals(currentUrl, "http://automationpractice.com/index.php?controller=my-account");
        Assert.assertEquals("MY ACCOUNT", myAccountObj.getHeading());
        Assert.assertEquals(myAccountObj.getinfo(),
                "Welcome to your account. Here you can manage all of your personal information and orders.");
        Assert.assertEquals(myAccountObj.getAccountName(), BrowserConfig.getUsername());
    }


}
