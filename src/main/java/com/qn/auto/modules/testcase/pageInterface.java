package com.qn.auto.modules.testcase;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.qn.auto.common.pojo.BrowserType;
import com.qn.auto.common.pojo.TerminalType;
import com.qn.auto.common.pojo.User;
import com.qn.auto.common.uiAuto.CommonDevice;
import com.qn.auto.modules.PageDemo;
import com.qn.auto.modules.androidHardTerminal.OrgControl;
import com.qn.auto.modules.company.account.RoleService;
import com.qn.auto.modules.company.contacts.OrganizationService;
import com.qn.auto.modules.company.contacts.UserService;
import com.qn.auto.modules.manPage.HomePage;
import com.qn.auto.utils.RandomValueUtil;
import io.restassured.response.Response;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.codec.digest.DigestUtils;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.*;
import org.testng.asserts.SoftAssert;

import java.util.ArrayList;
import java.util.List;

public class pageInterface {

    @Setter//添加setter方法
    @Getter//添加getter方法
    private static List<HomePage> homePages = new ArrayList<>();

    @Setter
    @Getter
    private static List<User> users = new ArrayList<>();

    public pageInterface(BrowserType browserType) {
        this.browserType = browserType;
    }

    public PageDemo pageDemo;
    public BrowserType browserType;
    public SoftAssert softAssert;

    @BeforeMethod
    public void initAssert(){
        softAssert = new SoftAssert();
    }


    //注意，Base类中init方法名称不要与父类中init方法名称相同，否则执行顺序会出错
    @BeforeSuite
    public void initInterface() throws Exception {
        //设置user
        List<User> userList = createUser(1);
        setUsers(userList);

        //设置homePage
        List<HomePage> home=getHomePage(userList,browserType);
        setHomePages(home);




    }
    static List<HomePage>  getHomePage(List<User> userList,BrowserType browserType)throws Exception{

        //创建homepage对象
        List<HomePage> pageDemoList= new ArrayList<>();
        for(User user:userList){
            //创建webdriver
            WebDriver webDriver = CommonDevice.getWebDriver(browserType);
            //获取WebDriver的选项管理接口,设置浏览器全屏
            webDriver.manage().window().fullscreen();

            String accountId = user.getAccountId();
            String password = user.getPassword();
            PageDemo pageDemo = new PageDemo(webDriver);
            pageDemo.enterName(accountId);
            pageDemo.enterPassword(password);
            HomePage homePage=pageDemo.clickLogin();
            pageDemoList.add(homePage);
            //打开新标签页

        }

        return pageDemoList;
    }

    static List<User> createUser(int num) throws Exception {
        //创建用户
        OrganizationService organizationService = new OrganizationService();
        RoleService roleservice = new RoleService();
        UserService userService = new UserService();

        Response res = organizationService.organizationList();
        String listResult = res.jsonPath().getString("result");
        JSONObject listResultJsonObj = JSON.parseObject(listResult);
        String parentId = listResultJsonObj.getString("orgId");
//       在根组织下创建一个新的组织
        res = organizationService.createOrganization(RandomValueUtil.getRandomStr(2), parentId);
        String orgId = res.jsonPath().getString("result.userId");
        OrgControl.toppingOrg(orgId, TerminalType.HDC);
        //创建角色
        ArrayList<String> powerList = new ArrayList();
        ArrayList<String> client = new ArrayList();
        powerList.add("homePage");
        powerList.add("userManager");
        powerList.add("visibleRule");
        powerList.add("currentConference");
        powerList.add("appoinConference");
        client.add("join_room_allowed");
        client.add("create_book_room_allowed");
        client.add("recording_conference_room_allowed");
        String readWrite = "1";
        String deptLimit = "0";
        String roleName = RandomValueUtil.getRandomStr(4);
        String roleDesc = RandomValueUtil.getRandomStr(4);

//        Response roleCreatRes = roleservice.roleCreate(roleName, roleDesc, readWrite, deptLimit, powerList, client, "");
        Response roleAdd=roleservice.roleAdd(roleName,roleDesc);
        String roleId=roleAdd.jsonPath().getString("result");
        Response rolePermissions=roleservice.rolePermissions(roleId);
        List<User> userList = new ArrayList<>();
        for (int i = 1; i <= num; i++) {
            String password = "123456Aa";
            String telephone = RandomValueUtil.getTelephone("194");
            String username = RandomValueUtil.getRandomStr(3);
            //fixme 修改创建用户接口
            Response createUserRes = userService.createUser(orgId, roleId, telephone, username, DigestUtils.md5Hex(password));
            if (createUserRes.statusCode() == 200) {
                User user = new User();
                user.setUserId(createUserRes.jsonPath().getString("result.userId"));
                user.setPassword(password);
                String accountId = userService.userListSearch(orgId, telephone).jsonPath().getString("result.list[0].account");
                user.setAccountId(accountId);
                user.setInOrgId(orgId);
                user.setUserName(username);
                user.setRoleId(roleId);
                userList.add(user);
//                PropertiesUtil.getInstance().setValueByKey("Windows_UI_user" + i + "_account", accountId);
            } else {
                throw new Exception("init user failed");
            }

        }
        return userList;

    }

    //清理用户
    @AfterSuite
    public void clearInterface() throws Exception {
        //删除用户
        UserService userService = new UserService();
        ArrayList<Integer> userIdlist= (ArrayList) getUsers().stream().map(User::getUserId).collect(java.util.stream.Collectors.toList());
        Response deletePonse=userService.deleteUser(userIdlist);
        Assert.assertEquals(deletePonse.getStatusCode(), 200, "用户删除成功");
        //删除组织
        OrganizationService organizationService = new OrganizationService();
        organizationService.deleteOrganization(getUsers().get(0).getInOrgId());

        //删除角色
        RoleService roleService = new RoleService();
        roleService.deleteRole(getUsers().get(0).getRoleId());
        closeBrowser();

    }
    //关闭浏览器
    public static void closeBrowser() {
        getHomePages().forEach(homePage -> {
            if (homePage.getWebDriver() != null) {
                homePage.getWebDriver().quit();
            }
        });
    }


}
