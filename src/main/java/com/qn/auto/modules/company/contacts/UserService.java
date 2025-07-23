package com.qn.auto.modules.company.contacts;

import com.alibaba.fastjson.JSONObject;
import com.qn.auto.utils.HttpsCompanyUtils;
import com.qn.auto.utils.URLUtils;
import io.qameta.allure.Step;
import io.restassured.response.Response;

import java.util.ArrayList;

public class UserService {

    @Step("企业管理后台-通讯录-查询用户列表")
    public  Response UserList(String pageNum, String pageSize, String orgId) throws Exception {
        String params="{\"pageNum\":1,\"pageSize\":20,\"orgId\":501}";
        JSONObject jsonObject = JSONObject.parseObject(params);
        jsonObject.put("pageNum", pageNum);
        jsonObject.put("pageSize", pageSize);
        jsonObject.put("orgId", orgId);
        params = jsonObject.toJSONString();
        return HttpsCompanyUtils.httpsUtil("post", URLUtils.getURL("api_name_contact_userList")
                ,params);
    }
    @Step("企业管理后台-通讯录-查询用户")
    public  Response userListSearch( String orgId,String search) throws Exception {
        String params="{\"pageNum\":1,\"pageSize\":20,\"orgId\":15,\"search\":\"19412345678\"}";
        JSONObject jsonObject = JSONObject.parseObject(params);
        jsonObject.put("orgId", orgId);
        jsonObject.put("search", search);
        params = jsonObject.toJSONString();

        return HttpsCompanyUtils.httpsUtil("post", URLUtils.getURL("api_name_contact_userList")
                ,params);
    }

    @Step("企业管理后台-通讯录--获取指定组织的userlist")
    public Response fetchList(String orgId)throws Exception{
        String params="{\"orgId\":249260}";
        JSONObject jsonObject = JSONObject.parseObject(params);
        jsonObject.put("orgId",orgId);
        params = jsonObject.toJSONString();

        return HttpsCompanyUtils.httpsUtil("post", URLUtils.getURL("api_name_contact_userList")
                ,params);
    }

    @Step("企业管理后台-通讯录-创建用户")
    public  Response createUser(String orgId, String roleId, String phone, String username, String password) throws Exception {
        String params="{\"orgId\":34,\"roleId\":54,\"phone\":\"18611106900\",\"username\":\"113243\",\"password\":\"3e6c7d141e32189c917761138b026b74\"}";
        JSONObject jsonObject = JSONObject.parseObject(params);
        jsonObject.put("orgId", orgId);
        jsonObject.put("roleId", roleId);
        jsonObject.put("phone", phone);
        jsonObject.put("username", username);
        jsonObject.put("password", password);
        params = jsonObject.toJSONString();
//        params= JSONTool.replaceVaule(params,"$.orgId",orgId);
//        params= JSONTool.replaceVaule(params,"$.roleId",roleId);
//        params= JSONTool.replaceVaule(params,"$.phone",phone);
//        params= JSONTool.replaceVaule(params,"$.username",username);
//        params= JSONTool.replaceVaule(params,"$.password",password);
        return HttpsCompanyUtils.httpsUtil("post", URLUtils.getURL("api_name_contact_createUser")
                ,params);
    }

    @Step("企业管理后台-通讯录-编辑用户")
    public  Response editUser(String userId,String orgId, String roleId, String phone, String username) throws Exception {
        String params="{\"userId\":252921,\"orgId\":54,\"roleId\":54,\"phone\":\"18611106900\",\"username\":\"113243\"}";
        JSONObject jsonObject = JSONObject.parseObject(params);
        jsonObject.put("userId", userId);
        jsonObject.put("orgId", orgId);
        jsonObject.put("roleId", roleId);
        jsonObject.put("phone", phone);
        jsonObject.put("username", username);
        params = jsonObject.toJSONString();
//        params= JSONTool.replaceVaule(params,"$.userId",userId);
//        params= JSONTool.replaceVaule(params,"$.orgId",orgId);
//        params= JSONTool.replaceVaule(params,"$.roleId",roleId);
//        params= JSONTool.replaceVaule(params,"$.phone",phone);
//        params= JSONTool.replaceVaule(params,"$.username",username);
        return HttpsCompanyUtils.httpsUtil("post", URLUtils.getURL("api_name_contact_editUser")
                ,params);
    }

    @Step("企业管理后台-通讯录-置顶用户")
    public  Response topUser(boolean top, int userId) throws Exception {
        String params="{\"top\":true,\"userId\":575242}";
        JSONObject jsonObject = JSONObject.parseObject(params);
        jsonObject.put("top",top);
        jsonObject.put("userId",userId);
        params = jsonObject.toJSONString();

        return HttpsCompanyUtils.httpsUtil("post", URLUtils.getURL("api_name_contact_topUser")
                ,params);
    }

    @Step("企业管理后台-通讯录-重置用户密码")
    public  Response resetPwd(String userId,String newPwd) throws Exception {
        String params="{\"userId\":249260,\"newPwd\":\"3e6c7d141e32189c917761138b026b74\"}";
        JSONObject jsonObject = JSONObject.parseObject(params);
        jsonObject.put("userId",userId);
        jsonObject.put("newPwd",newPwd);
        params = jsonObject.toJSONString();

        return HttpsCompanyUtils.httpsUtil("post", URLUtils.getURL("api_name_contact_resetPwd")
                ,params);
    }

    @Step("企业管理后台-通讯录-删除用户")
    public  Response deleteUser(ArrayList userIds) throws Exception {
        String params="{\"userIds\":[]}";
        JSONObject jsonObject = JSONObject.parseObject(params);

        for (int i=0;i<userIds.size();i++) {

            jsonObject.getJSONArray("userIds").add(userIds.get(i));

        }
        params = jsonObject.toJSONString();
        return HttpsCompanyUtils.httpsUtil("post", URLUtils.getURL("api_name_contact_deleteUser")
                ,params);
    }

}
