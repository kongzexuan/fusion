package com.qn.auto.modules.testcase;

import com.qn.auto.common.pojo.BrowserType;
import lombok.extern.slf4j.Slf4j;
import org.testng.annotations.Test;
@Slf4j
public class TestDemo extends TestDemoBase{
    public TestDemo(BrowserType browserType) {
        super(browserType);
    }

    @Test(description = "登录")
    public void login() throws Exception {
        log.info("用户名: " + getUsers().get(0).getAccountId());
//        log.info("用户名: " + getUsers().get(1).getAccountId());
        Thread.sleep(3000);
        pageDemo=homePage.exitButton();
//        pageDemo2=homePage2.exitButton();


    }
}
