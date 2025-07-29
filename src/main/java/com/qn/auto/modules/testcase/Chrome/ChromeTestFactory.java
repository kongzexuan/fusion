package com.qn.auto.modules.testcase.Chrome;

import com.qn.auto.common.pojo.BrowserType;

import io.qameta.allure.Feature;
import org.testng.annotations.Factory;

@Feature("chrome")
public class ChromeTestFactory {
    @Factory
    public Object[] factoryMethod() throws Exception {

        return new Object[]{
                new TestDemo(BrowserType.CHROME),
        };
    }

}
