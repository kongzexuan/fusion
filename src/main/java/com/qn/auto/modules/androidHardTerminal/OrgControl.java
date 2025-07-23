package com.qn.auto.modules.androidHardTerminal;

import com.qn.auto.common.pojo.TerminalType;
import com.qn.auto.common.ws.TouristHttpsUtils;
import com.qn.auto.utils.URLUtils;
import io.qameta.allure.Step;
import io.restassured.response.Response;

public class OrgControl {
    @Step("通过序列号获取账号部门")
    public static Response getOrgForUser(String serialNumber, TerminalType terminalType) throws Exception {
        return TouristHttpsUtils.touristHttpsUtil("post",
                URLUtils.getURL("api_name_user_info") +"?serialNumber="+serialNumber,serialNumber, terminalType);
    }
    @Step("置顶部门")
    public static Response toppingOrg(String orgId, TerminalType terminalType) throws Exception {
        return TouristHttpsUtils.touristHttpsUtil("post",
                URLUtils.getURL("api_name_org_top")+"?deptId="+orgId,orgId, terminalType);
    }
}
