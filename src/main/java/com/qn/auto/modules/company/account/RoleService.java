package com.qn.auto.modules.company.account;

import com.alibaba.fastjson.JSONObject;
import com.qn.auto.utils.HttpsCompanyUtils;
import com.qn.auto.utils.URLUtils;
import io.qameta.allure.Step;
import io.restassured.response.Response;

import java.util.ArrayList;

public class RoleService {
    @Step("企业管理后台-账户管理-角色列表")
    public Response roleList(String pageNum,String pageSize) throws Exception {
        String params="{\"pageNum\":1,\"pageSize\":20}";
        JSONObject jsonObject = JSONObject.parseObject(params);
        jsonObject.put("pageNum", pageNum);
        jsonObject.put("pageSize", pageSize);
        params = jsonObject.toJSONString();
//        params= JSONTool.replaceVaule(params,"$.pageNum",pageNum);
//        params= JSONTool.replaceVaule(params,"$.pageSize",pageSize);
        return HttpsCompanyUtils.httpsUtil("post", URLUtils.getURL("api_name_contact_roleList")
                ,params);
    }
    @Step("企业管理后台-账户管理-创建角色")
    public Response roleCreate(String roleName, String roleDesc, String readWrite, String deptLimit, ArrayList powerList, ArrayList client,String roleId) throws Exception {
        String params="{\"roleDesc\":\"test\",\"roleId\":2806,\"roleName\":\"测试角色1\",\"deptLimit\":0,\"readWrite\":1,\"client\":[\"join_room_allowed\",\"create_book_room_allowed\",\"recording_conference_room_allowed\"],\"powerList\":[\"homePage\",\"userManager\",\"visibleRule\",\"currentConference\",\"appoinConference\",\"historyConference\",\"recordManager\",\"fixedMeetingRoom\",\"mediaServiceManager\",\"logManager\",\"serviceOverview\",\"serviceRecharge\",\"roleManager\"]}";
        JSONObject jsonObject = JSONObject.parseObject(params);

        jsonObject.put("roleDesc", roleDesc);
        jsonObject.put("roleName", roleName);
        jsonObject.put("readWrite", readWrite);
        jsonObject.put("deptLimit", deptLimit);
        jsonObject.put("powerList", powerList);
        jsonObject.put("client", client);
        jsonObject.put("roleId", roleId);
        params = jsonObject.toJSONString();
        return HttpsCompanyUtils.httpsUtil("post", URLUtils.getURL("api_name_contact_roleCreate")
                ,params);
    }
    @Step("企业管理后台-账户管理-创建角色")
    public Response roleCreate2(String roleName, String roleDesc, String readWrite, String deptLimit, ArrayList powerList, ArrayList client) throws Exception {
        String params="{\"roleDesc\":\"test\",\"roleName\":\"测试角色1\",\"deptLimit\":0,\"readWrite\":1,\"client\":[\"join_room_allowed\",\"create_book_room_allowed\",\"recording_conference_room_allowed\"],\"powerList\":[\"homePage\",\"userManager\",\"visibleRule\",\"currentConference\",\"appoinConference\",\"historyConference\",\"recordManager\",\"fixedMeetingRoom\",\"mediaServiceManager\",\"logManager\",\"serviceOverview\",\"serviceRecharge\",\"roleManager\"]}";
        JSONObject jsonObject = JSONObject.parseObject(params);
        jsonObject.put("roleDesc", roleDesc);
        jsonObject.put("roleName", roleName);
        jsonObject.put("readWrite", readWrite);
        jsonObject.put("deptLimit", deptLimit);
        jsonObject.put("powerList", powerList);
        jsonObject.put("client", client);
        params = jsonObject.toJSONString();
//        params= JSONTool.replaceVaule(params,"$.roleName",roleName);
//        params= JSONTool.replaceVaule(params,"$.roleDesc",roleDesc);
//        params= JSONTool.replaceVaule(params,"$.readWrite",readWrite);
//        params= JSONTool.replaceVaule(params,"$.deptLimit",deptLimit);
//        params= JSONTool.replaceVaule(params,"$.powerList",powerList);
//        params= JSONTool.replaceVaule(params,"$.client",client);
        return HttpsCompanyUtils.httpsUtil("post", URLUtils.getURL("api_name_contact_roleCreate")
                ,params);
    }
    @Step("企业管理后台-账户管理-新增角色")
    public Response roleAdd(String roleName, String desc) throws Exception {

        String params="{\"roleName\": \"ce测试使用\",\"desc\": \"自动化测试角色\"}";
        JSONObject jsonObject = JSONObject.parseObject(params);
        jsonObject.put("roleName", roleName);
        jsonObject.put("desc", desc);
        params = jsonObject.toJSONString();
        return HttpsCompanyUtils.httpsUtil("post", URLUtils.getURL("api_name_contact_rolemangeAdd"),params);
    }
    @Step("企业管理后台-账户管理-设置角色权限-所有权限")
    public Response rolePermissions(String roleId) throws Exception {
        String params="{\n" +
                "    \"roleId\": 10064,\n" +
                "    \"permissionIds\": [\n" +
                "        90,\n" +
                "        89,\n" +
                "        91,\n" +
                "        92,\n" +
                "        55,\n" +
                "        116,\n" +
                "        56,\n" +
                "        57,\n" +
                "        117,\n" +
                "        58,\n" +
                "        59,\n" +
                "        60,\n" +
                "        61,\n" +
                "        118,\n" +
                "        67,\n" +
                "        71,\n" +
                "        76,\n" +
                "        77,\n" +
                "        70,\n" +
                "        112,\n" +
                "        113,\n" +
                "        109,\n" +
                "        111,\n" +
                "        110,\n" +
                "        120,\n" +
                "        121,\n" +
                "        103,\n" +
                "        104,\n" +
                "        105,\n" +
                "        106,\n" +
                "        108,\n" +
                "        155\n" +
                "    ],\n" +
                "    \"permissionLevel\": 1,\n" +
                "    \"deptLimit\": 0,\n" +
                "    \"dataScopes\": [\n" +
                "        {\n" +
                "            \"bizPlatform\": \"meetingClient\",\n" +
                "            \"resourceScope\": \"All\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"bizPlatform\": \"exp\",\n" +
                "            \"resourceScope\": \"All\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"bizPlatform\": \"fusion\",\n" +
                "            \"resourceScope\": \"All\"\n" +
                "        }\n" +
                "    ]\n" +
                "}";
        JSONObject jsonObject = JSONObject.parseObject(params);
        jsonObject.put("roleId",roleId);
        params = jsonObject.toJSONString();
        return HttpsCompanyUtils.httpsUtil("post", URLUtils.getURL("api_name_contact_setPermissions"),params);
    }
    @Step("企业管理后台-账户管理-角色成员列表")
    public Response roleUserList(String roleId, String pageNum, String pageSize) throws Exception {
        String params="{\"roleId\":\"10\",\"pageNum\":1,\"pageSize\":20}";
       JSONObject jsonObject = JSONObject.parseObject(params);
       jsonObject.put("roleId", roleId);
       jsonObject.put("pageNum", pageNum);
       jsonObject.put("pageSize", pageSize);
       params = jsonObject.toJSONString();
        return HttpsCompanyUtils.httpsUtil("post", URLUtils.getURL("api_name_contact_roleUserList")
                ,params);
    }
    @Step("企业管理后台-账户管理-增加角色成员")
    public Response roleAddMember(String roleId, ArrayList uuidList) throws Exception {
        String params="{\"roleId\":54,\"uuidList\":[\"80100204439\"]}";
        JSONObject jsonObject = JSONObject.parseObject(params);
        jsonObject.put("roleId", roleId);
        jsonObject.put("uuidList", uuidList);
        params = jsonObject.toJSONString();
        return HttpsCompanyUtils.httpsUtil("post", URLUtils.getURL("api_name_contact_roleAddMember")
                ,params);
    }
    @Step("企业管理后台-账户管理-更换角色")
    public Response exchangeRole(ArrayList param) throws Exception {
        String params="{\"param\":[{\"userId\":252922,\"oldRoleId\":\"54\",\"newRoleId\":346}]}";
        JSONObject jsonObject = JSONObject.parseObject(params);
        jsonObject.put("param", param);
        params = jsonObject.toJSONString();
        return HttpsCompanyUtils.httpsUtil("post", URLUtils.getURL("api_name_contact_exchangeRole")
                ,params);
    }
    @Step("企业管理后台-账户管理-查看角色信息")
    public Response getRoleById(String roleId) throws Exception {
        String params="{\"roleId\":659}";
        JSONObject jsonObject = JSONObject.parseObject(params);
        jsonObject.put("roleId", roleId);
        params = jsonObject.toJSONString();
        return HttpsCompanyUtils.httpsUtil("post", URLUtils.getURL("api_name_contact_getRoleById")
                ,params);
    }
    @Step("企业管理后台-账户管理-删除角色")
    public  Response deleteRole(String roleId) throws Exception {
        String params="{\"roleId\":659}";
        JSONObject jsonObject = JSONObject.parseObject(params);
        jsonObject.put("roleId", roleId);
        params = jsonObject.toJSONString();
        return HttpsCompanyUtils.httpsUtil("post", URLUtils.getURL("api_name_contact_deleteRole")
                ,params);
    }
}
