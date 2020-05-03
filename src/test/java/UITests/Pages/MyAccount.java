package UITests.Pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MyAccount{

    public static Logger logger = LoggerFactory.getLogger(MyAccount.class);
    WebDriver driver;

    //http://automationpractice.com/index.php?controller=my-account

    @FindBy(id = "icon-chevron-left")
    public WebElement goToHome;

    @FindBy(id = "myaccount-link-list")
    public WebElement account_details;

    @FindBy(linkText = "Order history and details")
    public WebElement order_history;

    @FindBy(className = "My credit slips")
    public WebElement credit_slip;

    @FindBy(className = "info-account")
    public WebElement info;

    @FindBy(className = "page-heading")
    public WebElement heading;

    @FindBy(className = "account")
    public WebElement account_name;

    public MyAccount(WebDriver driver) {
        this.driver =driver;
        PageFactory.initElements(driver,this);
    }

    public void gotToHome(){
        logger.info("Navigate to home page");
        goToHome.click();
    }

    public String getinfo(){
        return info.getText();
    }

    public String getHeading(){
        return heading.getText();
    }

    public String getAccountName(){
        return account_name.getText();
    }

}
