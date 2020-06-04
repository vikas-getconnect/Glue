package com.atf.browser;

import org.openqa.selenium.Capabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;

import java.lang.reflect.InvocationTargetException;

/**
 * This class to wait for driver to initialize only when required
 */
class WebDriverLazyLoader {
    private Class webDriverClass;

    public WebDriverLazyLoader(Class webDriverClass) {
        this.webDriverClass = webDriverClass;
    }

    public WebDriver getWebDriverClass() {
        try {
            return getWebDriverWithoutCapabilities();
        } catch (WebDriverException webException) {
            System.out.println("Webdriver Exception is " + webException.getMessage());
            waitForSometime();
            System.out.println("Retrying the browser port lock");
            return getWebDriverWithoutCapabilities();
        } catch (Exception e) {
            System.out.println("Hey..WebDriverLadyLoader.java didn't give you a webdriver class even after retrying twice..Check what's wrong" + e);
            e.printStackTrace();
        }
        return null;
    }

    private void waitForSometime() {
        try {
            Thread.sleep(15000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private WebDriver getWebDriverWithoutCapabilities() {
        try {
            return (WebDriver) this.webDriverClass.newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

    private WebDriver getWebDriverWithCapabilities(Capabilities capabilities) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        return (WebDriver) this.webDriverClass.getConstructor(Capabilities.class).newInstance(capabilities);
    }

    public WebDriver getWebDriverClass(Capabilities capabilities) {
        try {
            return getWebDriverWithCapabilities(capabilities);
        } catch (WebDriverException e) {
            e.printStackTrace();

        } catch (InvocationTargetException e) {
            waitForSometime();
            System.out.println("Retrying the browser port lock");
            try {
                return getWebDriverWithCapabilities(capabilities);
            } catch (NoSuchMethodException innerException) {
                e.printStackTrace();
            } catch (IllegalAccessException innerException) {
                e.printStackTrace();
            } catch (InvocationTargetException innerException) {
                e.printStackTrace();
            } catch (InstantiationException innerException) {
                e.printStackTrace();
            }
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }
}