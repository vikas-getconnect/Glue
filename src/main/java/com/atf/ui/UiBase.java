package com.atf.ui;

import com.atf.config.BrowserConfig;
import org.fluentlenium.core.Fluent;
import org.fluentlenium.core.FluentPage;
import org.fluentlenium.core.domain.FluentWebElement;
import org.fluentlenium.core.filter.Filter;
import org.junit.Assert;
import static junit.framework.Assert.fail;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import static org.fluentlenium.core.filter.FilterConstructor.withText;


public class UiBase extends FluentPage {
    private static String OS = System.getProperty("os.name");
    private boolean isUI;
    public static Logger LOGGER = LoggerFactory.getLogger("StaticLogger");

    public void setIsUI(Boolean uiState) {
        isUI = uiState;
    }

    public Boolean isUI() {
        return isUI;
    }


    /**
     * Coming to implicit wait, If you have set it once then you would have to explicitly set it to zero to nullify it -
     */
    private void nullifyImplicitWait() {
        getDriver().manage().timeouts().implicitlyWait(0, TimeUnit.MILLISECONDS); //nullify implicitlyWait()
    }

    /**
     * Reset ImplicitWait.
     * To reset ImplicitWait time you would have to explicitly
     * set it to zero to nullify it before setting it with a new time value.
     */

    private void resetImplicitWait() throws Exception {
        getDriver().manage().timeouts().implicitlyWait(0, TimeUnit.MILLISECONDS); //nullify implicitlyWait()
        getDriver().manage().timeouts().implicitlyWait(BrowserConfig.getElementMaxTimeout(), TimeUnit.MILLISECONDS); //reset implicitlyWait
    }

    /**
     * Set driver implicitlyWait() time.
     */
    private void setImplicitWait(int waitTime_InSeconds) {
        getDriver().manage().timeouts().implicitlyWait(waitTime_InSeconds, TimeUnit.MILLISECONDS);
    }

    public void refreshPage() throws Exception {

        gotoURL(getDriver().getCurrentUrl());
    }

    public String getCurrentUrl() throws Exception {

        return getDriver().getCurrentUrl();
    }

    public Fluent waitForPageToLoad() throws Exception {
        int elementMaxTimeout = BrowserConfig.getElementMaxTimeout();
        return await().atMost(elementMaxTimeout).untilPage().isLoaded();
    }

    public Boolean isElementDisplayed(FluentWebElement element) {
        LOGGER.debug("Inside isElementDisplayed(FluentWebElement element)");
        Boolean isItDisplayed;

        try {
            isItDisplayed = element.isDisplayed();
        } catch (Exception e) {
            isItDisplayed = false;
        }
        return isItDisplayed;
    }

    public Boolean isElementDisplayedSelenium(WebElement element1, WebDriver driver) {
        WebDriverWait wait = new WebDriverWait(driver,0);
        WebElement element = wait.until(ExpectedConditions.visibilityOf(element1));
        if (element == null){
            return false;
        }
        return true;
    }

    /**
    * To be used when no waiting for the element is required when an element is not displayed
    */
    public Boolean isElementDisplayedWithoutDefaultWait(FluentWebElement element) throws Exception {

        LOGGER.debug("Inside isElementDisplayedWithoutDefaultWait");
        Boolean isItDisplayed;

        try {
            nullifyImplicitWait();
            isItDisplayed = element.isDisplayed();
        } catch (Exception e) {
            resetImplicitWait();
            isItDisplayed = false;
        }
        resetImplicitWait();

        LOGGER.debug("Is Element displayed Status : " + isItDisplayed);
        return isItDisplayed;
    }

    public Boolean isElementDisplayedWithoutDefaultWaitSelenium(WebElement element1, WebDriver driver) throws Exception {

        WebDriverWait wait = new WebDriverWait(driver,10);
        WebElement element = null;
        try {
            element = wait.until(ExpectedConditions.visibilityOf(element1));
        }catch (NoSuchElementException e){
            return false;
        }catch (TimeoutException e){
            return false;
        }
        if (element == null){
            return false;
        }
        return true;
    }

    public Boolean waitForElementToBeDisplayed(FluentWebElement fluentWebElement) throws Exception {
        LOGGER.debug("Entering into waitForElementToBeDisplayed");
        boolean isDisplayed = waitAndPollForElementToBeDisplayed(fluentWebElement);
        if (isDisplayed) {
            LOGGER.debug("Is Element displayed Status : " + isDisplayed);
        }
        Assert.assertTrue("Element is not displayed",isDisplayed);//fail if not found
        return isDisplayed;
    }

    public Boolean waitForElementToBeDisplayed(FluentWebElement fluentWebElement, int maxWaitInMilliseconds) throws Exception {
        LOGGER.debug("Entering into waitForElementToBeDisplayed");
        boolean isDisplayed = waitAndPollForElementToBeDisplayed(fluentWebElement,maxWaitInMilliseconds);
        if (isDisplayed) {
            LOGGER.debug("Is Element displayed Status : " + isDisplayed);
        }
        Assert.assertTrue("Element is not displayed",isDisplayed);//fail if not found
        return isDisplayed;
    }

    public Boolean waitForElementToBeDisplayedWebElementSelenium(WebElement element1, int maxWaitInSeconds, WebDriver driver) throws Exception {
        WebDriverWait wait = new WebDriverWait(driver,maxWaitInSeconds);
        WebElement element = wait.until(ExpectedConditions.visibilityOf( element1));
        if (element == null){
            Assert.fail("Element is not displayed --->"+ element1.toString());
            return false;
        }
        return true;
    }


    public void waitForElementToDisappear(FluentWebElement fluentWebElement) throws Exception {
        LOGGER.debug("Entering into waitForElementToDisappear");
        boolean isDisplayed = waitAndPollForElementDisappearance(fluentWebElement);
        if (isDisplayed) {
            LOGGER.debug("Is Element Displayed Status : " + isDisplayed);
        }
        Assert.assertFalse(isDisplayed);//fail if found
    }

    public void waitForElementToDisappear(FluentWebElement fluentWebElement, int maxWaitInMilliseconds) throws Exception {
        LOGGER.debug("Entering into waitForElementToDisappear");
        boolean isDisplayed = waitAndPollForElementDisappearance(fluentWebElement,maxWaitInMilliseconds);
        if (isDisplayed) {
            LOGGER.debug("Is Element Displayed Status : " + isDisplayed);
        }
        Assert.assertFalse(isDisplayed);//fail if found
    }

    private Boolean waitAndPollForElementToBeDisplayed(FluentWebElement element) throws Exception {
        LOGGER.debug("Entering into waitAndPollForElementToBeDisplayed");
        Boolean found = false;
        try {
            nullifyImplicitWait();
            RetryTillPass rtp = new RetryTillPass("com.bluejeans.bjeft.base.ui.UiBase");
            Class[] signature = new Class[]{FluentWebElement.class};

            found = (Boolean) rtp.waitForCodeToPass("isElementDisplayed", true, signature, BrowserConfig.getElementMaxTimeout(), element);
        } catch (Exception e) {
            resetImplicitWait();
            LOGGER.error("Error in waitForElementToBeDisplayed: " + e);
        }
        resetImplicitWait();

        return found;
    }

    private Boolean waitAndPollForElementToBeDisplayed(FluentWebElement element, int maxWaitInMilliseconds) throws Exception {
        LOGGER.debug("Entering into waitAndPollForElementToBeDisplayed");
        Boolean found = false;
        try {
            nullifyImplicitWait();
            RetryTillPass rtp = new RetryTillPass("com.bluejeans.bjeft.base.ui.UiBase");
            Class[] signature = new Class[]{FluentWebElement.class};

            found = (Boolean) rtp.waitForCodeToPass("isElementDisplayed", true, signature, maxWaitInMilliseconds, element);
        } catch (Exception e) {
            resetImplicitWait();
            LOGGER.error("Error in waitForElementToBeDisplayed: " + e);
        }
        resetImplicitWait();

        return found;
    }

    private Boolean waitAndPollForElementDisappearance(FluentWebElement element) throws Exception {
        LOGGER.debug("Entering into waitAndPollForElementDisappearance");
        Boolean result = true;
        try {
            nullifyImplicitWait();
            RetryTillPass rtp = new RetryTillPass("com.bluejeans.bjeft.base.ui.UiBase");
            Class[] signature = new Class[]{FluentWebElement.class};

            result = (Boolean) rtp.waitForCodeToPass("isElementDisplayed", false, signature, BrowserConfig.getElementMaxTimeout(), element);
        } catch (Exception e) {
            resetImplicitWait();
            LOGGER.error("Error in waitAndPollForElementDisappearance: " + e);
        }
        resetImplicitWait();
        return result;
    }

    private Boolean waitAndPollForElementDisappearance(FluentWebElement element,int maxWaitInMilliseconds) throws Exception {
        LOGGER.debug("Entering into waitAndPollForElementDisappearance");
        Boolean result = true;
        try {
            nullifyImplicitWait();
            RetryTillPass rtp = new RetryTillPass("com.bluejeans.bjeft.base.ui.UiBase");
            Class[] signature = new Class[]{FluentWebElement.class};

            result = (Boolean) rtp.waitForCodeToPass("isElementDisplayed", false, signature, maxWaitInMilliseconds, element);
        } catch (Exception e) {
            resetImplicitWait();
            LOGGER.error("Error in waitAndPollForElementDisappearance: " + e);
        }
        resetImplicitWait();
        return result;
    }

    public void waitForUrlToContainString(String stringToCheck) throws Exception {
        int maxWait = BrowserConfig.getElementMaxTimeout();
        int currentWait = 0;
        WebDriver driver = getDriver();
        while (!driver.getCurrentUrl().contains(stringToCheck)){
            Thread.sleep(500);
            LOGGER.debug(driver.getCurrentUrl());

            currentWait=currentWait+500;
            if (currentWait >= maxWait) {
                fail("Url did not load to contain: " + stringToCheck);
                break;
            }
        }
    }

    public void clickOnDisplayedElement(FluentWebElement fluentWebElement) throws Exception {
        boolean isDisplayed = waitAndPollForElementToBeDisplayed(fluentWebElement);
        if (isDisplayed) {
            LOGGER.debug("Is Element Displayed Status : " + isDisplayed);
            Thread.sleep(100);
            click(fluentWebElement);
          // System.out.println("Is Element Displayed Status : " + fluentWebElement);
        }
        Assert.assertTrue(isDisplayed);
    }

    public void clickOnDisplayedWebElementSelenium(WebElement element1, WebDriver driver){
        WebDriverWait wait = new WebDriverWait(driver, 15);
        WebElement element = wait.until(ExpectedConditions.visibilityOf(element1));
        if (element==null){
            Assert.fail("Element is not displayed --->"+ element1.toString());
        }
        element.click();
        System.out.println("Clicked on element with path (InMeetingObject----> "+ element1.toString());
    }


    public void clickIfDisplayedElement(FluentWebElement fluentWebElement) throws Exception {
        boolean isDisplayed = waitAndPollForElementToBeDisplayed(fluentWebElement);
        if (isDisplayed) {
            LOGGER.debug("Is Element Displayed Status : " + isDisplayed);
            Thread.sleep(100);
            click(fluentWebElement);

            System.out.println("Is Element Displayed Status : " + fluentWebElement);
        }
    }

    public void clickAndWaitForPageToLoad(FluentWebElement fluentWebElement) throws Exception {
        clickOnDisplayedElement(fluentWebElement);
        awaitForPageToLoad();
    }

    public void clickAndWaitForPageToLoadBE(FluentWebElement fluentWebElement) throws Exception {
        clickOnDisplayedElement(fluentWebElement);
        openNewTab();
        Thread.sleep(10000);
        switchToAnyLaunchedBrowserTab();
       // gotoURL("http://bluejeans.com");
    }

    public FluentWebElement getDisplayedElement(FluentWebElement fluentWebElement) {
        Boolean isItDisplayed = false;
        try {
            isItDisplayed = waitAndPollForElementToBeDisplayed(fluentWebElement);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Assert.assertTrue(isItDisplayed);
        return fluentWebElement;
    }

    public void enterTextValue(FluentWebElement fluentWebElement, String text) throws Exception {
        getDisplayedElement(fluentWebElement);
        Thread.sleep(100);
        click(fluentWebElement);
        Thread.sleep(100);
        fill(fluentWebElement).with(text);
        Thread.sleep(100);
    }

    public void enterTextValueWebElementSelenium(WebElement element1, String text, WebDriver driver) throws Exception {
        WebDriverWait wait = new WebDriverWait(driver, 15);
        WebElement element = wait.until(ExpectedConditions.visibilityOf( element1));
        element.click();
        element.sendKeys(text);
    }

    public void enterTextValueAfterClear(FluentWebElement fluentWebElement, String text) throws InterruptedException {
        getDisplayedElement(fluentWebElement);
        clear(fluentWebElement);
        Thread.sleep(100);
        click(fluentWebElement);
        Thread.sleep(100);
        clear(fluentWebElement);
        fill(fluentWebElement).with(text);
        Thread.sleep(100);
    }

    public void enterTextValueAfterClearSelenium(WebElement WebElement, String text, WebDriver driver) throws InterruptedException {
        WebDriverWait wait = new WebDriverWait(driver, 15);
        org.openqa.selenium.WebElement element = wait.until(ExpectedConditions.visibilityOf(WebElement));
        element.click();
        element.clear();
        element.sendKeys(text);
    }

    public String getName(FluentWebElement fluentWebElement) {
        return fluentWebElement.getValue();
    }


    public Boolean isSelected(FluentWebElement fluentWebElement) {
        return fluentWebElement.isSelected();
    }

    public void gotoURL(String url) throws Exception {

        LOGGER.info("Going to Url "+url);
        goTo(url);
        waitForPageToLoad();
    }

    public void gotoURLSelenium(String url, WebDriver driver) throws Exception {

        LOGGER.info("Going to Url "+url);
        driver.get(url);

    }

    public void executeJavaScript(String script) throws Exception{

        WebDriver mydriver = getDriver();
        getDriver().manage().timeouts().setScriptTimeout(10, TimeUnit.SECONDS);
        JavascriptExecutor js = (JavascriptExecutor) getDriver();
        //js.executeAsyncScript(script /*, http://localhost:8080/jquery-1.7.2.js */);
        // ready to rock
        js.executeScript(script);
    }

//    public void mouseOverOnElement(FluentWebElement targetElement) throws Exception {
//
//        WebDriver _driver = getDriver();
//        Actions builder = new Actions(_driver);
//
//        boolean isElementDisplayed = waitForElementToBeDisplayed(targetElement);
//
//        WebElement elementToHoverOn = targetElement.getElement();
//
//        if(isElementDisplayed){
//            try
//            {
//                builder.moveToElement(elementToHoverOn).build().perform();
//                Thread.sleep(2000);// sleep for 2  seconds
//            }
//            catch (Exception e)
//            {
//                e.printStackTrace();
//            }
//        }
//    }

    public void mouseOverOnElementSelenium(WebElement targetElement, WebDriver driver) throws Exception {

        WebDriver _driver = driver;
        Actions builder = new Actions(_driver);

        boolean isElementDisplayed = waitForElementToBeDisplayedWebElementSelenium(targetElement,15,driver);

        WebElement elementToHoverOn = targetElement;

        if(isElementDisplayed){
            try
            {
                builder.moveToElement(elementToHoverOn).build().perform();
                Thread.sleep(2000);// sleep for 2  seconds
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
    }

    public void switchToAnyLaunchedBrowserTab() throws Exception {
        WebDriver mydriver=getDriver();
        ArrayList<String> tabList = new ArrayList<String>(mydriver.getWindowHandles());
        LOGGER.info("SWITCHING TO WHATEVER TAB " + tabList.size());
        mydriver.switchTo().window(tabList.get(0));
        //mydriver.close();

        // change focus to new tab
        mydriver.switchTo().window(tabList.get(0));
        LOGGER.debug("Switched to TAB with URL" + mydriver.getCurrentUrl());
    }

    public void switchToNewlyLaunchedBrowserTab() throws Exception {
        WebDriver mydriver=getDriver();
        ArrayList<String> tabList = new ArrayList<String>(mydriver.getWindowHandles());

        mydriver.switchTo().window(tabList.get(0));
        mydriver.close();

        // change focus to new tab
        try {
            mydriver.switchTo().window(tabList.get(1));
        }catch(IndexOutOfBoundsException e ) {
            Thread.sleep(3000);
            tabList = new ArrayList<String>(mydriver.getWindowHandles());
            mydriver.switchTo().window(tabList.get(0));
        }
        LOGGER.debug("Switched to TAB with URL" + mydriver.getCurrentUrl());
    }


    public void switchToNewlyLaunchedBrowserTabIfAvailable() throws Exception {
        WebDriver mydriver=getDriver();
        ArrayList<String> newTab = new ArrayList<String>(mydriver.getWindowHandles());
        String oldTab = mydriver.getWindowHandle();
        newTab.remove(oldTab);
        // change focus to new tab
        try{
            mydriver.switchTo().window(newTab.get(0));
        }catch(Exception e){
            LOGGER.debug("No New tab found");
        }
        LOGGER.debug("Switched to TAB with URL" + mydriver.getCurrentUrl());
    }


    public void openAndSwitchToNewTabUsingAnyLinkOnPage() throws Exception{
        WebDriver driver=getDriver();
        WebElement elem = driver.findElement(By.xpath("//a[contains(@id,'nav')]"));
        Actions actions=new Actions(driver);

        actions.keyDown(Keys.LEFT_CONTROL).keyDown(Keys.LEFT_SHIFT).perform();

        try {
            elem.click();
        }catch (Exception e){
            e.printStackTrace();
        }
        finally {
            actions.keyUp(Keys.LEFT_CONTROL).keyUp(Keys.LEFT_SHIFT).perform();
        }

        actions.keyDown(Keys.COMMAND).keyDown(Keys.LEFT_SHIFT).perform();

        try {
            elem.click();
        }catch (Exception e){
            e.printStackTrace();
        }
        finally {
            actions.keyUp(Keys.COMMAND).keyUp(Keys.LEFT_SHIFT).perform();
        }
    }


    public void openNewTab() throws Exception

    {
        WebDriver driver=getDriver();
        LOGGER.info("OS : "+ OS);
        WebElement element = driver.findElement(By.cssSelector("body"));
        LOGGER.info("Pressing ctrl+t");
     /*   if (OS.startsWith("Mac")) {
            LOGGER.info("OS : Mac OS");
            driver.findElement(By.cssSelector("body")).sendKeys(Keys.COMMAND + "t");
        }
        else {
            LOGGER.info("OS = windows/ubuntu/other");
            driver.findElement(By.cssSelector("body")).sendKeys(Keys.CONTROL + "t");
        }*/


        if (OS.startsWith("Mac")) {
            LOGGER.info("OS : Mac OS");
            driver.findElement(By.cssSelector("body")).sendKeys(Keys.COMMAND + "t");
        }

        element.sendKeys(Keys.CONTROL + "t");



        ArrayList<String> tabs = new ArrayList<String> (driver.getWindowHandles());
        driver.switchTo().window(tabs.get(0));

    }

    public void switchToNewlyLaunchedBrowserTabWithoutClosingExistingTab() throws Exception {
        WebDriver myDriver=getDriver();
        ArrayList<String> newTab = new ArrayList<String>(myDriver.getWindowHandles());
        String oldTab = myDriver.getWindowHandle();
        // change focus to new tab
        myDriver.switchTo().window(newTab.get(0));
        LOGGER.debug(myDriver.getCurrentUrl());
        LOGGER.debug("Switched to TAB with URL" + myDriver.getCurrentUrl());
    }

    public void clearAllCookies() throws InterruptedException {
        WebDriver mydriver = getDriver();
        LOGGER.info("Cookie Set Pre Delete: " + String.valueOf(mydriver.manage().getCookies()));
        mydriver.manage().deleteAllCookies();
        LOGGER.debug("Cookie Set Post Delete: " + String.valueOf(mydriver.manage().getCookies()));
    }

    public Boolean checkDynamicBannerForString(String bannerText, int maxTimeoutInMilliSeconds, int pollAfterEveryInMilliSeconds) throws InterruptedException {
        int currentTime=0;
        while(currentTime<maxTimeoutInMilliSeconds){

            LOGGER.debug(String.valueOf(pageSource().toLowerCase().contains(bannerText.toLowerCase())));
            if(pageSource().toLowerCase().contains(bannerText.toLowerCase())){
                return true;
            }
            currentTime+=pollAfterEveryInMilliSeconds;
            Thread.sleep(pollAfterEveryInMilliSeconds);
        }
        return false;
    }

    public Boolean doesPageContainStringWithoutWait(String searchString, int maxTimeoutInMilliSeconds, int pollAfterEveryInMilliSeconds) throws InterruptedException {

        if(pageSource().contains(searchString)){
            return true;
        }
        return false;
    }

    public Boolean doesPageContainString(String searchString, int maxTimeoutInMilliSeconds, int pollAfterEveryInMilliSeconds) throws InterruptedException {
        int currentTime=0;
        float remain_val;
        while(currentTime<maxTimeoutInMilliSeconds){
            remain_val= (float)maxTimeoutInMilliSeconds - (float)currentTime;
            if(pageSource().contains(searchString)){
                return true;
            }
            currentTime+=pollAfterEveryInMilliSeconds;
            Thread.sleep(pollAfterEveryInMilliSeconds);
            if (remain_val % 1000 == 0) {
                LOGGER.debug("Waiting for string on page: " + searchString + " for another " + remain_val + " milliseconds");
            }
        }
        return false;
    }

    public String getAlertText() throws Exception {
        Alert alert = getDriver().switchTo().alert();
        String alertText = alert.getText();
        return alertText;
    }

    public boolean isAlertPresent() throws Exception {
        try {
            getDriver().switchTo().alert();
            return true;
        } catch (NoAlertPresentException Ex) {
            return false;
        }
    }

    public void alertOk() throws Exception {
        Alert alert = getDriver().switchTo().alert();
        alert.accept();
    }

    public void alertCancel() throws Exception {
        Alert alert = getDriver().switchTo().alert();
        alert.dismiss();
    }

    /**
     ***********************************************************************************************************************
     ***********************************************************************************************************************
     ***********************************************************************************************************************
     ***********************************************************************************************************************
     * New Base Methods DO NOT MODIFY Methods above this. ask Rivet-QA for change requirements
     ***********************************************************************************************************************
     ***********************************************************************************************************************
     ***********************************************************************************************************************
     ***********************************************************************************************************************
     */

    /**
     * OLD Methods
     */


    public Fluent awaitAndClick(FluentWebElement element) throws Exception {
        awaitForElementPresence(getCssLocator(element.getElement()));
        return super.click(element);
    }

    @Override
    public Fluent click(String cssSelector, Filter... filters) {
        return super.click(cssSelector, filters);
    }

    public String getCssLocator(WebElement element) {
        String val = element.toString();
        return val.substring(val.lastIndexOf("selector:") + 10, val.length() - 1).trim();
    }

    public String getCssLocatorFromId(WebElement element) {
        String val = element.toString();
        return val.substring(val.lastIndexOf("id:") + 4, val.length() - 1).trim();
    }

    public Fluent awaitForElementPresence(String CSSLocator) throws Exception {
        int elementMaxTimeout = BrowserConfig.getElementMaxTimeout();
        LOGGER.info("The max time out is" + elementMaxTimeout);
        LOGGER.info("Looking for CSS Element : " + CSSLocator);
        return await().atMost(elementMaxTimeout).until(CSSLocator).isPresent();
    }

    public Fluent awaitForElementPresenceWithMinTimeout(String CSSLocator) throws Exception {
        int elementMinTimeout = BrowserConfig.getElementMinTimeout();
        return await().atMost(elementMinTimeout).until(CSSLocator).isPresent();
    }

    public Fluent awaitForElementPresence(String CSSLocator, int timeInSeconds) throws Exception {
        return await().atMost(timeInSeconds, TimeUnit.SECONDS).until(CSSLocator).isPresent();
    }


    public Fluent verifyElementNotPresent(String CSSLocator) throws Exception {
        LOGGER.info("******BrowserConfig.getElementMaxTimeout()   " + BrowserConfig.getElementMaxTimeout());
        int elementMaxTimeout = BrowserConfig.getElementMaxTimeout();
        return await().atMost(elementMaxTimeout).until(CSSLocator).isNotPresent();
    }

    public Fluent verifyElementNotPresentWithTimeOutAs(FluentWebElement element, long timeOut) throws Exception {
        return await().atMost(timeOut).until(getCssLocator(element.getElement())).isNotPresent();
    }

    public void hoverAndClick(FluentWebElement element, WebDriver driver) throws Exception {
        int elementMaxTimeout = BrowserConfig.getPageLoadMaxTimeOut();
        Thread.sleep(elementMaxTimeout);
        new JsHelper(driver).jqueryClick(getCssLocator(element.getElement()));

    }

    public FluentWebElement clickWithLinkText(String linkText) throws Exception {
        return findFirst("a", withText().contains(linkText)).click();
    }


    public Fluent awaitForElementEnabled(FluentWebElement element) throws Exception {
        int elementMaxTimeout = BrowserConfig.getElementMaxTimeout();
        return await().atMost(elementMaxTimeout).until(getCssLocator(element.getElement())).areEnabled();
    }

    public Boolean isElementExists(FluentWebElement element) throws Exception {
        return element.isDisplayed();
    }

    public Fluent awaitForPageToLoad() throws Exception {
        int elementMaxTimeout = BrowserConfig.getElementMaxTimeout();
        return await().atMost(elementMaxTimeout).untilPage().isLoaded();
    }

    public String getText(FluentWebElement element){
        return element.getText();
    }
    public String getButtonUrl(FluentWebElement element){return element.getAttribute("formaction");}
    public String getAttributeText (FluentWebElement element){return element.getAttribute("text");}

    public String getStyle(FluentWebElement fluentWebElement){
        return fluentWebElement.getAttribute("style");
    }
    public String getHref(FluentWebElement element){return element.getAttribute("href");}
/*    public FluentList findAllWebElements(String name, Filter... filters) {
        return FluentWebElement.find(name, filters);// check fn argument
    }*/

    public Fluent waitForPageToLoadWithRefresh() throws Exception {
        try {
            LOGGER.info("Inside waitForPageToLoadWithRefresh()");
            return waitForPageToLoad();
        } catch (TimeoutException e){
            LOGGER.warn("TimeoutException, refreshing once in waitForPageToLoadWithRefresh()");
            LOGGER.info("Refreshing once");
            refreshPage();
            return waitForPageToLoad();
        }
    }

    public void waitForCssElementToBeClickable(String Css){
        new WebDriverWait(getDriver(), 30).until(ExpectedConditions.elementToBeClickable(By.cssSelector(Css)));
    }

    public String convertDateToString(long timeInMillies) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd MMM yyyy HH:mm:ss");
        Date resultdate = new Date(timeInMillies);
        String dateAsString = sdf.format(resultdate);
        System.out.println(sdf.format(resultdate));
        return dateAsString;
    }

    public String getOnlyDateFromMillies(long timeInMillies) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd MMM yyyy");
        Date newDate = new Date(timeInMillies);
        String date = sdf.format(newDate);
        System.out.println("********* date from time : " + date + "************");
        return date;
    }

    public String getTimeFromMillies (long timeInMillies) {
        SimpleDateFormat sdf = new SimpleDateFormat("hh:mm a");
        Date newDate = new Date(timeInMillies);
        String timeWithAmPm = sdf.format(newDate);
        System.out.println("********* Time from date is: " + timeWithAmPm + "************");
        return timeWithAmPm;
    }

    public String getOnlyDateFromMillies(long timeInMillies,String dateFormat) {
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
        Date resultdate = new Date(timeInMillies);
        String dateAsString = sdf.format(resultdate);
        System.out.println(sdf.format(resultdate));
        return dateAsString;
    }



}
