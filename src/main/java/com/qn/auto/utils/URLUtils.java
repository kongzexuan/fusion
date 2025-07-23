package com.qn.auto.utils;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class URLUtils {
    public static String getURL(String apiName){
        log.info("apiName: " + apiName);
        log.info("获取接口地址: " + PropertiesUtil.getInstance().getValueByKey(apiName));
        log.info("获取端口: " + PropertiesUtil.getInstance().getValueByKey("host") );
        return PropertiesUtil.getInstance().getValueByKey("host").
                concat(PropertiesUtil.getInstance().getValueByKey(apiName));
    }
    public static String getHost(){
        return PropertiesUtil.getInstance().getValueByKey("host");
    }
    public static String getHostWithoutHttpProtocoll(){
        String host = PropertiesUtil.getInstance().getValueByKey("host");
        if(host.contains("://")){
            return host.split("://")[1];
        }
        else {
            return host;
        }
    }
}
