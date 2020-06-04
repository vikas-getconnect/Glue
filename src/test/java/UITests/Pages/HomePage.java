package UITests.Pages;

import org.fluentlenium.core.domain.FluentWebElement;
import org.openqa.selenium.support.FindBy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Home Page for http://automationpractice.com/index.php?controller=my-account
 */
public class HomePage {

    public static Logger logger = LoggerFactory.getLogger(HomePage.class);

    //

    @FindBy(className = "account")
    public FluentWebElement accountName;

    public void verifyAccountName(String name) {

    }
}
