package com.qn.auto.utils;

import com.qn.auto.constant.TestCaseConstant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Properties;

/*关于Properties类常用的操作
 */
public class PropertiesUtil {
    Logger logger = LoggerFactory.getLogger(PropertiesUtil.class);
    private static PropertiesUtil instance;
    private Properties globalProperties = new Properties();
    private PropertiesUtil(){
        try {
            getAllProperties();
        } catch (IOException e) {
            logger.error("getAllProperties error",e);
        }
    }
    public static PropertiesUtil getInstance(){
        if(instance==null){
            synchronized (PropertiesUtil.class){
                if(instance==null){
                    instance =  new PropertiesUtil();
                }
            }
        }
        return instance;
    }
    //根据Key读取Value
    public String getValueByKey(String key) {
        try {
            String value = globalProperties.getProperty(key);
            return value;
        }catch (Exception e) {
            return null;
        }
    }
    public String setValueByKey(String key,String value) {
        try {
            globalProperties.setProperty(key,value);
            FileOutputStream out = new FileOutputStream(FileUtil.geFilePathInResources("properties/global.properties"));
            globalProperties.store(out,value);
            out.close();
            return value;
        }catch (Exception e) {
            return null;
        }
    }
    //读取Properties的全部信息
    private void getAllProperties() throws IOException {
        InputStreamReader reader = new InputStreamReader(
                new FileInputStream(FileUtil.geFilePathInResources("properties/global.properties")),
                TestCaseConstant.DEFAULT_FILE_CHARSET_UTF8);
        globalProperties.load(reader);

//        reader = new InputStreamReader(
//                new FileInputStream(FileUtil.geFilePathInResources("properties/api_names_idaas.properties")),
//                TestCaseConstant.DEFAULT_FILE_CHARSET_UTF8);
//        globalProperties.load(reader);
//        reader = new InputStreamReader(
//                new FileInputStream(FileUtil.geFilePathInResources("properties/api_names_developer.properties")),
//                TestCaseConstant.DEFAULT_FILE_CHARSET_UTF8);
//        globalProperties.load(reader);
        reader = new InputStreamReader(
                new FileInputStream(FileUtil.geFilePathInResources("properties/web.properties")),
                TestCaseConstant.DEFAULT_FILE_CHARSET_UTF8);
        globalProperties.load(reader);
        reader = new InputStreamReader(
                new FileInputStream(FileUtil.geFilePathInResources("properties/api_names_company.properties")),
                TestCaseConstant.DEFAULT_FILE_CHARSET_UTF8);
        globalProperties.load(reader);
//        reader = new InputStreamReader(
//                new FileInputStream(FileUtil.geFilePathInResources("properties/api_names_admin.properties")),
//                TestCaseConstant.DEFAULT_FILE_CHARSET_UTF8);
//        globalProperties.load(reader);
    }

}
