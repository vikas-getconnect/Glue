package com.atf.ui;

import com.atf.config.BrowserConfig;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

public class JsHelper {

    public static Logger LOGGER = LoggerFactory.getLogger("StaticLogger");
    protected Logger classlogger = LoggerFactory.getLogger(getClass());
    public static String separator = System.getProperty("file.separator");

    WebDriver driver;
    Actions actions;
    JavascriptExecutor js;
    WebDriverWait wait;

    public JsHelper() {

    }
    public JsHelper(WebDriver driver) throws Exception {
        this.driver = driver;
        this.actions = new Actions(driver);
        this.js = (JavascriptExecutor) driver;
        this.wait = new WebDriverWait(driver, BrowserConfig.getElementMaxTimeout() / 1000);
    }

    private void nullifyImplicitWait() {
        driver.manage().timeouts().implicitlyWait(0, TimeUnit.MILLISECONDS); //nullify implicitlyWait()
    }

    /**
     * Reset ImplicitWait.
     * To reset ImplicitWait time you would have to explicitly
     * set it to zero to nullify it before setting it with a new time value.
     */

    private void resetImplicitWait() throws Exception {
        driver.manage().timeouts().implicitlyWait(0, TimeUnit.MILLISECONDS); //nullify implicitlyWait()
        driver.manage().timeouts().implicitlyWait(BrowserConfig.getElementMinTimeout(), TimeUnit.MILLISECONDS); //reset implicitlyWait
    }

    /**
     * Set driver implicitlyWait() time.
     */
    public void setImplicitWait(int waitTime_InSeconds){
        try{
        driver.manage().timeouts().implicitlyWait(waitTime_InSeconds, TimeUnit.MILLISECONDS);
        }
        catch (NullPointerException npException){
            LOGGER.info("driver Null pointer");
        }
    }

    public void setDriver(WebDriver driver){
        this.driver=driver;
    }

    public void KeyStrokes(WebElement element, String keyValue) {
        element.sendKeys(Keys.valueOf(keyValue.toUpperCase()));
    }

    public void jsMouseOver(WebElement element) {
        String mouseOverScript = "if(document.createEvent){var evObj = document.createEvent('MouseEvents');evObj.initEvent('mouseover', true, false); arguments[0].dispatchEvent(evObj);} else if(document.createEventObject) { arguments[0].fireEvent('onmouseover');}";
        js.executeScript(mouseOverScript, element);
        iAmMeditating();

    }

    public void jsScrollIntoView(WebElement element) {
        String scrollToViewScript = "arguments[0].scrollIntoView(true);";
        js.executeScript(scrollToViewScript, element);
        iAmMeditating();

    }

    public void jqueryMouseOver(String cssSelector) {
        js.executeScript("$(\"" + cssSelector + "\").mouseover()");
        iAmMeditating();
    }

    public String jqueryGetText(String cssSelector) {
        String script = "return $('" + cssSelector + "').text()";
        String text = js.executeScript(script).toString();
        return text;
    }

    public String jqueryGetTextIgnoringChildElement(String parentElementCSS, String childElementCSS) {
        String script = "return $('" + parentElementCSS + "').children().remove('" + childElementCSS + "').end().text()";
        String text = js.executeScript(script).toString();
        return text;
    }

    public void jqueryClick(String cssSelector) {
        js.executeScript("$(\"" + cssSelector + "\").click()");
        iAmMeditating();
    }

    public void jqueryFill(String cssSelector, String value) {
        js.executeScript("$('" + cssSelector + "').val(\"" + value + "\")");
        iAmMeditating();
    }

    private Boolean waitAndPollForJqueryToBePresent( ) throws Exception {
        LOGGER.info("Entering into waitAndPollForJquerytobePresent");
        Boolean found = false;
        try {
            nullifyImplicitWait();
            RetryTillPass rtp = new RetryTillPass("com.bluejeans.bjeft.base.ui.JsHelper");
            Class[] signature = new Class[]{};

            found = (Boolean) rtp.waitForCodeToPass("isJqueryLoaded", true, signature, BrowserConfig.getElementMaxTimeout(), new Object());
        } catch (Exception e) {
            resetImplicitWait();
            LOGGER.error("Error in wait For Jquery to be present: " + e);
        }
        resetImplicitWait();
        return found;
    }

    public Boolean isJqueryLoaded() throws Exception {
        Boolean result = Boolean.parseBoolean(String.valueOf(js.executeScript("return jQuery.active == 0")));
        System.out.println("iS jquery LOADED !!!!!!!!!!!!!!!!"+result);
        return result;
    }


    public void executeJavascript(String script) throws Exception {
//        Assert.assertTrue(waitAndPollForJqueryToBePresent());
        js.executeScript(script);
        iAmMeditating();
    }

    public String executeJavascriptAndReturnString(String script) throws Exception {
//        Assert.assertTrue(waitAndPollForJqueryToBePresent());
        String result = String.valueOf(js.executeScript(script));
        iAmMeditating();
        return result;
    }

    public Integer executeJavascriptAndReturnInteger(String script) throws Exception {
//        Assert.assertTrue(waitAndPollForJqueryToBePresent());
        String result = String.valueOf(js.executeScript(script));
        iAmMeditating();
        return Integer.parseInt(result);
    }

    public void refresh() {
        driver.navigate().refresh();
    }

    public void jsControlKeyPlusClick(String locator) {
        try {
            String script = "e = jQuery.Event('click');e.ctrlKey = true;$('" + locator + "').trigger(e);";
            js.executeScript(script);
            iAmMeditating();
        } catch (Exception e) {
            LOGGER.error("Error while executing jsControlKeyPlusClick" + e.getMessage().toString());
        }
    }

    public void iAmMeditating() {
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public Boolean waitTillElementVisible(WebElement element) {
        try {
            wait.until(ExpectedConditions.visibilityOf(element));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public Boolean waitTillVisible(String locator) {
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(locator)));

            return true;
        } catch (Exception e) {
            return false;
        }

    }

    public boolean waitTillElementsToBeVisibleWithText(WebElement element, String message) {
        try {
            wait.until(ExpectedConditions.textToBePresentInElement(element, message));

            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public String isJqueryActive() {
        String result = js.executeScript("return jQuery.active == 0").toString();
        System.out.println(result);
        return result;
    }

    public String getCounterErrorValue() {
        String result = js.executeScript("return $('input.counter.error').val()").toString();
        return result;
    }

    public Boolean waitTillElementInDomAndIsVisible(WebElement element) {
        try {
            wait.until(ExpectedConditions.refreshed(ExpectedConditions.visibilityOf(element)));

            return true;
        } catch (Exception e) {
            return false;
        }

    }

    public void navigateToPage(String url) {
        driver.get(url);
        driver.navigate().refresh();
        LOGGER.info("Navigating to URL" + url);
    }


}

