package com.qn.auto.modules.page.DemoPage;

import com.qn.auto.modules.page.manPage.HomePage;
import lombok.Getter;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
/*
* PageDemo:登录页
* */

@Getter
public class PageDemo {
    private WebDriver driver;

    // 使用 @FindBy 注解定位元素,获取xpath元素
    @FindBy(xpath = "//*[@id=\"basic_account\"]")
    private WebElement nameInput;

    @FindBy(xpath = "//*[@id=\"basic_password\"]")
    private WebElement passwordInput;
    @FindBy(xpath = "//*[@id=\"root\"]/div/div[2]/div/div[3]")
    private WebElement loginButton;

    // 构造函数
    public PageDemo(WebDriver driver) {
        this.driver = driver;
        // 初始化 PageFactory
        PageFactory.initElements(driver, this);
    }
    // 输入账号
    public void enterName(String name) {
        nameInput.sendKeys(name);
    }
    // 输入密码
    public void enterPassword(String password) {
        passwordInput.sendKeys(password);
    }
    // 点击登录按钮
    public HomePage clickLogin() {
        loginButton.click();
        return new HomePage(driver);
    }


}
