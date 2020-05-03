package UITests.Pages;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoginPage {

    public static Logger logger = LoggerFactory.getLogger(LoginPage.class);
    WebDriver driver;

    @FindBy(id = "email")
    public WebElement login_email;

    @FindBy(id = "passwd")
    public WebElement login_password;

    @FindBy(id = "SubmitLogin")
    public WebElement login_button;

    @FindBy(className = "lost_password form-group")
    public WebElement forgot_password;

    public LoginPage(WebDriver driver){
        this.driver = driver;

        PageFactory.initElements(driver,this);
    }


    public MyAccount login(String email, String password) throws Exception {
        logger.info("Entering email id :"+email);
        login_email.click();
        login_email.sendKeys(email);
        logger.info("Entering password :"+password);
        login_password.click();
        login_password.sendKeys(password);
        login_button.click();
        //waitForPageLoad(driver);
        return new MyAccount(driver);
    }

    public void sign_up(String email){

    }

//    public void waitForPageLoad(WebDriver driver){
//        new WebDriverWait(driver, 10).until(
//                webDriver -> ((JavascriptExecutor) webDriver).executeScript("return document.readyState").equals("complete"));
//    }
}
