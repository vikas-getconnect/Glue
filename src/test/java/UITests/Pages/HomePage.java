package UITests.Pages;

import org.fluentlenium.core.domain.FluentWebElement;
import org.openqa.selenium.support.FindBy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HomePage {

    public static Logger logger = LoggerFactory.getLogger(HomePage.class);

    //http://automationpractice.com/index.php?controller=my-account

    @FindBy(className = "account")
    public FluentWebElement accountName;

    public void verifyAccountName(String name){

    }
}
