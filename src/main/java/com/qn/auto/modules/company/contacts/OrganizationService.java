package com.qn.auto.modules.company.contacts;

import com.alibaba.fastjson.JSONObject;
import com.qn.auto.utils.HttpsCompanyUtils;
import com.qn.auto.utils.URLUtils;
//import com.qnsz.autotest.utils.JSONTool;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class OrganizationService {
    @Step("企业管理后台-通讯录-查询组织列表")
    public Response organizationList() throws Exception {
        return HttpsCompanyUtils.httpsUtil("post", URLUtils.getURL("api_name_contact_organizationList")
                , "");
    }

    @Step("企业管理后台-通讯录-查询指定企业的组织列表")
    public Response organizationListDesignatedCompany(String userName, String PassWord) throws Exception {
        return HttpsCompanyUtils.httpsUtil("post", URLUtils.getURL("api_name_contact_powerList"),
                "", userName, PassWord, true);
    }

    @Step("企业管理后台-通讯录-创建组织")
    public Response createOrganization(String orgName, String parentId) throws Exception {
        String params = "{\"orgName\":\"test\",\"parentId\":15}";
        JSONObject jsonObject = JSONObject.parseObject(params);
        jsonObject.put("orgName", orgName);
        jsonObject.put("parentId", parentId);
        params = jsonObject.toJSONString();
//        params = JSONTool.replaceVaule(params, "$.orgName", orgName);
//        params = JSONTool.replaceVaule(params, "$.parentId", parentId);
        return HttpsCompanyUtils.httpsUtil("post", URLUtils.getURL("api_name_contact_createOrganization")
                , params);
    }

    @Step("企业管理后台-通讯录-修改组织")
    public Response editOrganization(String orgName, String orgId) throws Exception {
        String params = "{\"orgId\":1086,\"orgName\":\"test123\"}";
        JSONObject jsonObject = JSONObject.parseObject(params);
        jsonObject.put("orgName", orgName);
        jsonObject.put("orgId", orgId);
        params = jsonObject.toJSONString();
//        params = JSONTool.replaceVaule(params, "$.orgName", orgName);
//        params = JSONTool.replaceVaule(params, "$.orgId", orgId);
        return HttpsCompanyUtils.httpsUtil("post", URLUtils.getURL("api_name_contact_editOrganization")
                , params);
    }

    @Step("企业管理后台-通讯录-删除组织")
    public Response deleteOrganization(String orgId) throws Exception {
        String params = "{\"orgId\":1086}";
        JSONObject jsonObject = JSONObject.parseObject(params);
        jsonObject.put("orgId", orgId);
        params = jsonObject.toJSONString();
//        params = JSONTool.replaceVaule(params, "$.orgId", orgId);
        return HttpsCompanyUtils.httpsUtil("post", URLUtils.getURL("api_name_contact_deleteOrganization")
                , params);
    }
}
