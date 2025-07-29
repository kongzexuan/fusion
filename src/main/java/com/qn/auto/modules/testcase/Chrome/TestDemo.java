package com.qn.auto.modules.testcase.Chrome;

import com.qn.auto.common.pojo.BrowserType;
import com.qn.auto.common.uiAuto.AppiumScreenHelper;
import io.qameta.allure.Allure;
import io.qameta.allure.Step;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
public class TestDemo extends TestDemoBase {
    public TestDemo(BrowserType browserType) {
        super(browserType);
    }

    @Test(description = "查看地图显示")
    public void mapDisplay() throws Exception {
        AppiumScreenHelper appiumScreenHelper = new AppiumScreenHelper(homePage.getWebDriver());

        Thread.sleep(3000);
        Allure.step("步骤一：查看底部按钮文案内容是否正确");
        WebElement eventText =homePage.getWebDriver().findElement(By.xpath("//span[@class='show-text' and contains(text(), '事件')]"));
        WebElement userText =homePage.getWebDriver().findElement(By.xpath("//span[@class='show-text' and contains(text(), '用户')]"));
        WebElement devicesText =homePage.getWebDriver().findElement(By.xpath("//span[@class='show-text' and contains(text(), '设备')]"));
        WebElement examineText =homePage.getWebDriver().findElement(By.xpath("//span[@class='show-text' and contains(text(), '巡查')]"));
        WebElement dispatchText =homePage.getWebDriver().findElement(By.xpath("//span[@class='show-text' and contains(text(), '调度')]"));
        assertEquals(eventText.getText(), "事件", "事件按钮文案内容不正确");
        assertEquals(userText.getText(), "用户", "用户按钮文案内容不正确");
        assertAll(

                () -> assertEquals(devicesText.getText(), "设备", "设备按钮文案内容不正确")
                , () -> assertEquals(examineText.getText(), "巡查", "巡查按钮文案内容不正确")
                , () -> assertEquals(dispatchText.getText(), "调度", "调度按钮文案内容不正确")
        );
        Allure.step("步骤二：查看地图默认地址");
        appiumScreenHelper.screenShot("地图地址应为洛宁县");
    }


@Test(description = "图层切换", dependsOnMethods = "mapDisplay")
public void layerSwitch() throws Exception {
    AppiumScreenHelper appiumScreenHelper = new AppiumScreenHelper(homePage.getWebDriver());
    Allure.step("步骤一：点击图层切换按钮");
    Thread.sleep(1000);
    homePage.clickLayer();

    Allure.step("步骤二：查看图层切换按钮文案内容是否正确");
    WebElement allDeviceText =homePage.getWebDriver().findElement(By.xpath("//div[@class='drop-item_options' and contains(text(), '所有设备')]"));
    WebElement onlineDeviceText =homePage.getWebDriver().findElement(By.xpath("//div[@class='drop-item_options' and contains(text(), '在线设备')]"));
    WebElement eventPreviewText =homePage.getWebDriver().findElement(By.xpath("//div[@class='drop-item_options' and contains(text(), '事件预警')]"));
    WebElement planeMapText =homePage.getWebDriver().findElement(By.xpath("//div[@class='drop-item_options' and contains(text(), '平面地图')]"));
    WebElement satelliteMapText =homePage.getWebDriver().findElement(By.xpath("//div[@class='drop-item_options' and contains(text(), '卫星地图')]"));
    WebElement twinMapText =homePage.getWebDriver().findElement(By.xpath("//div[@class='drop-item_options' and contains(text(), '孪生地图')]"));
    assertEquals(allDeviceText.getText(), "所有设备", "所有设备按钮文案内容不正确");
    assertEquals(onlineDeviceText.getText(), "在线设备", "在线设备按钮文案内容不正确");
    assertEquals(eventPreviewText.getText(), "事件预警", "事件预警按钮文案内容不正确");
    assertEquals(planeMapText.getText(), "平面地图", "平面地图按钮文案内容不正确");
    assertEquals(satelliteMapText.getText(), "卫星地图", "卫星地图按钮文案内容不正确");
    assertEquals(twinMapText.getText(), "孪生地图", "孪生地图按钮文案内容不正确");
    Allure.step("步骤三：切换在线设备图层");
    appiumScreenHelper.screenShot("显示所有设备地图");
    homePage.clickOnlineDevice();
    appiumScreenHelper.screenShot("点击在线设备后地图的显示");
    Allure.step("步骤四：切换回所有设备图层");
    homePage.clickAllDevice();
    appiumScreenHelper.screenShot("点击所有设备后地图的显示");
}
}
