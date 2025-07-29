package com.qn.auto.common.uiAuto;

import io.qameta.allure.Allure;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.ByteArrayInputStream;
import java.io.File;

/*
*屏幕相关的辅助类
* 截图、录屏
* */
public class AppiumScreenHelper {
    private WebDriver driver;

    public AppiumScreenHelper(WebDriver driver) {
        this.driver = driver;
    }

    // 截图方法
    public void screenShot(String fileName) {
        TakesScreenshot screenshotDriver = (TakesScreenshot) driver;
//        byte[] screenshot =screenshotDriver.getScreenshotAs(OutputType.BYTES);
//        Allure.addAttachment(fileName,  new ByteArrayInputStream(screenshot));
        String screenshot=screenshotDriver.getScreenshotAs(OutputType.BASE64);
        Allure.addAttachment(fileName, "image/png", new ByteArrayInputStream(java.util.Base64.getDecoder().decode(screenshot)), "png");
    }

}
