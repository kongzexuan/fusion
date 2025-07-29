package com.qn.auto.modules.page.manPage;

import com.qn.auto.modules.page.DemoPage.PageDemo;
import io.qameta.allure.Step;
import lombok.Getter;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

/*
* HomePage:首页*/
@Getter
public class HomePage {
    private WebDriver webDriver;

    @FindBy(xpath = "//*[@id=\"root\"]/div/div[2]/div[1]/div/div/div[2]")
    WebElement exitButton;

    @FindBy(xpath = "//*[@id=\"root\"]/div/div[2]/div[3]/div/div[2]/div[2]")
    WebElement companyButton;

    //事件点击按钮
    @FindBy(xpath = "//*[@id=\"root\"]/div/div[2]/div[5]/div/div[5]/span")
    WebElement eventButton;
    //图层
    @FindBy(xpath = "//div[contains(@class, 'layer-btn')][.//text()='图层']")
    WebElement layer;

    //在线设备
    @FindBy(xpath = "//*[@id=\"root\"]/div/div[2]/div[4]/div/div[2]/div/div/div[1]/div[2]")
    WebElement onlineDevice;
    //所有 设备
    @FindBy(xpath = "//*[@id=\"root\"]/div/div[2]/div[4]/div/div[2]/div/div/div[1]/div[1]")
    WebElement allDevice;




    //构造方法
    public HomePage(WebDriver webDriver) {
        this.webDriver = webDriver;
        //自动查找并绑定当前页面中声明的@FindBy注解的Web元素
        PageFactory.initElements(webDriver, this);
    }

    @Step("点击退出按钮")
    public void clickExitButton() {
        //fixme 优化 元素操作基类
        exitButton.click();
    }

    @Step("点击事件icon")
    public void clickEventButton() throws  Exception {
        exitButton.click();
    }
    @Step("点击图层icon")
    public void clickLayer() throws  Exception {
        layer.click();
    }
    @Step("点击在线设备")
    public void clickOnlineDevice() throws  Exception {
        onlineDevice.click();
    }
    @Step("点击所有设备")
    public void clickAllDevice() throws  Exception {
        allDevice.click();
    }

    @Step("确认退出")
    public PageDemo exitButton()throws  Exception {
        clickExitButton();
        Thread.sleep(2000);
        companyButton.click();
        //返回PageDemo对象
        return new PageDemo(webDriver);
    }

}
