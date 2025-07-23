import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.qn.auto.common.pojo.BrowserType;
import com.qn.auto.common.pojo.TerminalType;
import com.qn.auto.common.pojo.User;
import com.qn.auto.common.uiAuto.CommonDevice;
import com.qn.auto.modules.androidHardTerminal.OrgControl;
import com.qn.auto.modules.company.account.RoleService;
import com.qn.auto.modules.company.contacts.OrganizationService;
import com.qn.auto.modules.company.contacts.UserService;
import com.qn.auto.utils.RandomValueUtil;
import io.restassured.response.Response;
import org.apache.commons.codec.digest.DigestUtils;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

public class uiTest {
    private WebDriver webDriver;
    @BeforeMethod
    public void setUp() throws Exception {
        // 创建浏览器实例
        webDriver = CommonDevice.getWebDriver(BrowserType.CHROME);
    }

    @Test
    public void joinRoom() throws Exception {
        // 1. 验证浏览器是否成功启动
        Assert.assertNotNull(webDriver, "浏览器实例未成功创建");
        System.out.println("✅ 浏览器启动成功: " + webDriver);

        // 2. 执行基本操作验证
        webDriver.get("https://172.25.29.91/qn-fusion-comm/login");
        String title = webDriver.getTitle();
        System.out.println("✅ 页面标题: " + title);
        Assert.assertTrue(title.contains("百度"), "页面标题验证失败");

        // 3. 添加等待确保页面加载完成
        Thread.sleep(2000);

        // 4. 验证页面元素存在
        // 这里使用简单的页面验证，实际应使用您的页面对象
        String currentUrl = webDriver.getCurrentUrl();
        System.out.println("✅ 当前URL: " + currentUrl);
        Assert.assertTrue(currentUrl.contains("baidu"), "URL验证失败");

        // 5. 添加截图功能（可选但推荐）
        /*
        File screenshot = ((TakesScreenshot)webDriver).getScreenshotAs(OutputType.FILE);
        FileUtils.copyFile(screenshot, new File("screenshots/joinRoom-test.png"));
        System.out.println("✅ 截图已保存");
        */

        System.out.println("✅✅ joinRoom测试通过");
    }

    @AfterMethod
    public void tearDown() {
        // 确保浏览器关闭
        if (webDriver != null) {
            webDriver.quit();
            System.out.println("🛑 浏览器已关闭");
        }
    }


    @Test
    public void createUser(int num) throws Exception {
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

        Response roleCreatRes = roleservice.roleCreate(roleName, roleDesc, readWrite, deptLimit, powerList, client, "");
        String roleId = roleCreatRes.jsonPath().getString("result.Id");

        List<User> userList = new ArrayList<>();
        for (int i = 1; i <= num; i++) {
            String password = "123456Aa";
            String telephone = RandomValueUtil.getTelephone("194");
            String username = RandomValueUtil.getRandomStr(3);
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
//        return userList;

    }
}
