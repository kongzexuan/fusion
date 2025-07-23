import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.qn.auto.common.pojo.TerminalType;
import com.qn.auto.common.pojo.User;
import com.qn.auto.modules.androidHardTerminal.OrgControl;
import com.qn.auto.modules.company.account.RoleService;
import com.qn.auto.modules.company.contacts.OrganizationService;
import com.qn.auto.modules.company.contacts.UserService;
import com.qn.auto.utils.RandomValueUtil;
import com.qn.auto.utils.URLUtils;
import io.restassured.response.Response;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;
@Slf4j
public class userTest {

    @Test
    public void createUser() throws Exception {
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
        for (int i = 1; i <= 2; i++) {
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
