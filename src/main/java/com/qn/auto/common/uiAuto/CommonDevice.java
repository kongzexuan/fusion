package com.qn.auto.common.uiAuto;

import com.qn.auto.common.pojo.BrowserType;
import com.qn.auto.utils.URLUtils;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

import java.io.IOException;
import java.util.Date;

@Slf4j
public class CommonDevice {

    //初始webdriver
    public static WebDriver getWebDriver(BrowserType browserType) throws InterruptedException, IOException {
        log.info("初始化浏览器：" + browserType);
        WebDriver webDriver = webDriver(browserType);
        return webDriver;
    }

    //配置各浏览器的WebDriver
    public static WebDriver webDriver(BrowserType browserType) throws IOException {
        WebDriver webDriver;
        switch (browserType) {
            case CHROME:
                //ChromeOptions chrome浏览器的专属options
                ChromeOptions chromeOptions = new ChromeOptions();
                //remote-allow-origins 即允许跨域访问（允许所有请求之意）
                chromeOptions.addArguments("--remote-allow-origins=*");
                chromeOptions.setCapability("acceptInsecureCerts", true);
                chromeOptions.setCapability("se:name", "WebControl_"+new Date());

                webDriver = createDriver(chromeOptions, "Chrome");
                break;

            case FIREFOX:
                FirefoxOptions firefoxOptions = new FirefoxOptions();
                firefoxOptions.setCapability("acceptInsecureCerts", true);
                webDriver = createDriver(firefoxOptions, "Firefox");
                break;

            case EDGE:
                EdgeOptions edgeOptions = new EdgeOptions();
                edgeOptions.addArguments("--remote-allow-origins=*");
                edgeOptions.setCapability("acceptInsecureCerts", true);
                webDriver = createDriver(edgeOptions, "Edge");
                break;

            default:
                throw new IllegalArgumentException("Unsupported browser type: " + browserType);
        }

        webDriver.manage().window().setSize(new Dimension(1920, 1080));
        //打开登录页面
        webDriver.get(URLUtils.getURL("weblogin"));
        return webDriver;
    }

    private static WebDriver createDriver(Object options, String browserName) throws IOException {
        WebDriver webDriver;
        //判断浏览器类型，创建对应的WebDriver实例
        switch (browserName) {
            case "Chrome":
                webDriver = new ChromeDriver((ChromeOptions) options);
                break;
            case "Firefox":
                webDriver = new FirefoxDriver((FirefoxOptions) options);
                break;
            case "Edge":
                webDriver = new EdgeDriver((EdgeOptions) options);
                break;
            default:
                throw new IllegalArgumentException("Unsupported browser: " + browserName);
        }
        return webDriver;
    }
}
