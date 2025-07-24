package com.qn.auto.modules.testcase;

import com.qn.auto.common.pojo.BrowserType;
import com.qn.auto.modules.PageDemo;
import com.qn.auto.modules.manPage.HomePage;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

public class TestDemoBase extends pageInterface{
    public PageDemo pageDemo;
    public PageDemo pageDemo2;

    public HomePage homePage;
    public HomePage homePage2;

    public TestDemoBase(BrowserType browserType) {
        super(browserType);

    }

    //注意，Base类中init方法名称不要与父类中init方法名称相同，否则执行顺序会出错
    @BeforeClass
    public void initBase()throws Exception{
        //获取homePage
        homePage=getHomePages().get(0);
//        homePage2=getHomePages().get(1);




    }


    @AfterClass
    public void clearBase(){

    }
}
