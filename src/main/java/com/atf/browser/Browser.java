package com.atf.browser;

public enum Browser {
    htmlunit("default"),
    firefox("firefox"),
    chrome("chrome"),
    safari("safari"),
    internet_explorer("ie"),
    PHANTOMJS("phantomjs");

    private String browserName;

    Browser(String browserName) {
        this.browserName = browserName;
    }

    public String getName() {
        return this.browserName;
    }

    public static Browser getBrowser(String name) {
        for (Browser browser : values()) {
            String browserName = browser.getName();
            if (browserName.equalsIgnoreCase(name)) {
                return browser;
            }
        }
        return htmlunit;
    }
}