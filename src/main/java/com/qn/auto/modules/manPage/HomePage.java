package com.qn.auto.modules.manPage;

import com.qn.auto.modules.PageDemo;
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
    @Step("确认退出")
    public PageDemo exitButton()throws  Exception {
        clickExitButton();
        Thread.sleep(2000);
        companyButton.click();
        //返回PageDemo对象
        return new PageDemo(webDriver);
    }

}
