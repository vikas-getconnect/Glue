package UITests.Pages;

import org.fluentlenium.core.domain.FluentWebElement;
import org.openqa.selenium.support.FindBy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SignUpPage {

    public static Logger logger = LoggerFactory.getLogger(SignUpPage.class);

    @FindBy(id = "id_gender1")
    public FluentWebElement mr;

    @FindBy(id = "id_gender2")
    public FluentWebElement mrs;

    @FindBy(id = "customer_firstname")
    public FluentWebElement firstName;

    @FindBy(id = "customer_lastname")
    public FluentWebElement lastName;

    @FindBy(id = "email")
    public FluentWebElement email;

    @FindBy(id = "passwd")
    public FluentWebElement password;

    @FindBy(id = "days")
    public FluentWebElement days;

    @FindBy(id = "months")
    public FluentWebElement months;

    @FindBy(id = "years")
    public FluentWebElement years;

    @FindBy(id = "firstname")
    public FluentWebElement add_firstName;

    @FindBy(id = "lastname")
    public FluentWebElement add_lastName;

    @FindBy(id = "address1")
    public FluentWebElement address;

    @FindBy(id = "city")
    public FluentWebElement city;

    @FindBy(id = "postcode")
    public FluentWebElement pincode;

    @FindBy(id = "phone")
    public FluentWebElement phone;

    @FindBy(id = "alias")
    public FluentWebElement alias;

    @FindBy(id = "submitAccount")
    public FluentWebElement register;

    @FindBy(id = "account-creation_form")
    public FluentWebElement account_creation;

}
