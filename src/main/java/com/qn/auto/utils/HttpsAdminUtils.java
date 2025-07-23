package com.qn.auto.utils;

import io.qameta.allure.Allure;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.config.HeaderConfig;
import io.restassured.config.LogConfig;
import io.restassured.filter.session.SessionFilter;
import io.restassured.http.Header;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.output.WriterOutputStream;

import java.io.*;
import java.util.Map;

import static io.restassured.RestAssured.config;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
/* *
 * @description 超管登录获取token方法
 * @author qn
 * @date 2023/10/16 11:00
 * @version 1.0
 * @since 1.0
* */

@Slf4j
public class HttpsAdminUtils {
    public static final String AUTHORIZATION = "Authorization";
    public static final String APPLICATION_JSON_CHARSET_UTF_8 = "application/json;charset=UTF-8";
    public static String multipart="multipart/form-data";
    public static SessionFilter cookieFilter = new SessionFilter();
    public static Map<String, String> cookie = null;

    public static RequestSpecification initConfig() throws IOException {
        RestAssured.useRelaxedHTTPSValidation();
        RestAssured.config = config().headerConfig(HeaderConfig.headerConfig().overwriteHeadersWithName(AUTHORIZATION));
        RestAssured.config = config().headerConfig(HeaderConfig.headerConfig().overwriteHeadersWithName("Content-Type"));
        FileWriter file = new FileWriter("src/main/resources/data.log");
        PrintStream printStream = new PrintStream(new WriterOutputStream(file, "utf-8"), true);
        RestAssured.config = RestAssured.config().logConfig(LogConfig.logConfig().defaultStream(printStream));
        RequestSpecBuilder builder = new RequestSpecBuilder();
        builder.setConfig(RestAssured.config);
        builder.setContentType(APPLICATION_JSON_CHARSET_UTF_8);
        return builder.build();
    }
    public static RequestSpecification initConfigFile() throws IOException {
        RestAssured.useRelaxedHTTPSValidation();
        RestAssured.config = config().headerConfig(HeaderConfig.headerConfig().overwriteHeadersWithName("Content-Type"));
        FileWriter file = new FileWriter("src/main/resources/data.log");
        PrintStream printStream = new PrintStream(new WriterOutputStream(file, "utf-8"), true);
        RestAssured.config = RestAssured.config().logConfig(LogConfig.logConfig().defaultStream(printStream));
        RequestSpecBuilder builder = new RequestSpecBuilder();
        builder.setConfig(RestAssured.config);
        builder.setContentType(multipart);
        return builder.build();
    }
    /**
     * 控制台接口获取token
     *
     * @return
     * @throws Exception
     */
    public static Map<String, String> consoleToken(String username, String password) throws IOException {
        RequestSpecification requestSpecification = given(initConfig());
        RequestSpecification request = requestSpecification.header(new Header("X-Qn-Appkey",PropertiesUtil.getInstance().getValueByKey("ak")));
        requestSpecification = request.filter(cookieFilter);
        String params = "{\"uuid\":\"" + username + "\",\"password\":\"" + new Md5Util().encode16(password) + "\",\"accessRequired\":false}";
        Response response = requestSpecification.body(params).log().all().post(URLUtils.getURL("api_name_admin_login"));
        response.then().assertThat().body("errorCode", equalTo(0));
        cookie = response.getCookies();
        //将缓存里的accesstoken按照username进行隔离
//        LocalCacheUtil.getInstance().putValue(username+"_console_access_token",cookie.toString());
        return cookie;
    }
    /**
     * @param method
     * @param urlPath
     * @param params
     * @return
     * @throws Exception
     */
    public static synchronized Response httpsUtil(String method, String urlPath, String params) throws Exception {
        //如果不使用用户名，默认登录
        return httpsUtil(method, urlPath, params, PropertiesUtil.getInstance().getValueByKey("admin_username"), PropertiesUtil.getInstance().getValueByKey("admin_password"), false);
    }

    public static synchronized Response httpsUtil(String method, String urlPath, String params, File file,String fileName,String fileType) throws Exception {
        //如果不使用用户名，默认登录
        return httpsUtil(method, urlPath, params, PropertiesUtil.getInstance().getValueByKey("admin_username"), PropertiesUtil.getInstance().getValueByKey("admin_password"), false, file,fileName,fileType);
    }

    /**
     * 控制台使用的http请求方法
     *
     * @param method
     * @param urlPath
     * @param params
     * @return
     * @throws Exception
     */
    public static synchronized Response httpsUtil(String method, String urlPath, String params, String userName, String password, boolean needSwitch) throws Exception {
        return httpsUtil(method, urlPath, params, needSwitch, userName, password);
    }
    public static synchronized Response httpsUtil(String method, String urlPath, String params, String userName, String password, boolean needSwitch, File file,String fileName,String fileType) throws Exception {
        return httpsUtil(method, urlPath, params, needSwitch, userName, password, file,fileName,fileType);
    }
    /**
     * 请求接口时，指定用户名和密码
     *
     * @param method
     * @param urlPath
     * @param params
     * @param username
     * @param password
     * @return
     * @throws Exception
     */
    private static synchronized Response httpsUtil(String method, String urlPath, String params, boolean needSwitch, String username, String password, File file,String fileName,String fileType) throws Exception {
        RequestSpecification requestSpecification = given(initConfigFile());
//        requestSpecification = requestSpecification.filter(cookieFilter);
        log.info("请求url----" + urlPath);
        log.info("请求方法----" + method);
        log.info("请求参数----" + params);
        if (cookie == null) {
            cookie = consoleToken(username, password);
            requestSpecification.cookies(cookie);
        } else if (needSwitch) {
            cookie = consoleToken(username, password);
            requestSpecification.cookies(cookie);
        }
        Response response = null;
        String r = urlPath.trim().replace(PropertiesUtil.getInstance().getValueByKey("host"), "").replace("/", "/");
        if ("post".equalsIgnoreCase(method)) {
            response = requestSpecification.cookies(cookie)
                    .multiPart(fileName,file,fileType)
                    .body(params).log().all().post(urlPath);
            if (response.jsonPath().getInt("errorCode") == 11100) {
                cookie = consoleToken(username, password);
                requestSpecification.cookies(cookie);
                response = requestSpecification.cookies(cookie)
                        .multiPart(fileName,file,fileType)
                        .body(params).log().all().post(urlPath);
                return response;
            }
            response.then().log().all();
            addHttpLogToAllure(r);
            log.info("响应----" + response.asString());
            return response;
        } else if ("get".equalsIgnoreCase(method)) {
            response = requestSpecification
                    .multiPart(fileName,file,fileType)
                    .body(params).log().all().get(urlPath);
            if (response.jsonPath().getInt("errorCode") == 11100) {
                consoleToken(username, password);
                response = requestSpecification
                        .multiPart(fileName,file,fileType)
                        .body(params).log().all().get(urlPath);
                return response;
            }
            response.then().log().all();
            addHttpLogToAllure(r);
            //log.info("响应----"+response.asString());
            return response;
        } else if ("delete".equalsIgnoreCase(method)) {
            response = requestSpecification.
                    multiPart(fileName,file,fileType).
                    body(params).log().all().delete(urlPath);
            if (response.jsonPath().getInt("errorCode") == 11100) {
                consoleToken(username, password);
                response = requestSpecification
                        .multiPart(fileName,file,fileType)
                        .body(params).log().all().delete(urlPath);
            }
            response.then().log().all();
            addHttpLogToAllure(r);
            log.info("响应----" + response.asString());
            return response;
        } else {
            response = requestSpecification.
                    multiPart(file).
                    body(params).log().all().put(urlPath);
            if (response.jsonPath().getInt("errorCode") == 11100) {
                consoleToken(username, password);
                response = requestSpecification
                        .multiPart(fileName,file,fileType)
                        .body(params).log().all().put(urlPath);
            }
            response.then().log().all();
            addHttpLogToAllure(r);
            log.info("响应----" + response.asString());
            return response;
        }
    }

    private static synchronized Response httpsUtil(String method, String urlPath, String params, boolean needSwitch, String username, String password) throws Exception {
        RequestSpecification requestSpecification = given(initConfig());
//        requestSpecification = requestSpecification.filter(cookieFilter);
        log.info("请求url----" + urlPath);
        log.info("请求方法----" + method);
        log.info("请求参数----" + params);

        if (cookie == null) {
            cookie = consoleToken(username, password);
            requestSpecification.cookies(cookie);
        } else if (needSwitch) {
            cookie = consoleToken(username, password);
            requestSpecification.cookies(cookie);
        }
        Response response = null;
        String r = urlPath.trim().replace(PropertiesUtil.getInstance().getValueByKey("host"), "").replace("/", "/");
        if ("post".equalsIgnoreCase(method)) {
            response = requestSpecification.cookies(cookie)
                    .body(params).log().all().post(urlPath);
            if (response.jsonPath().getInt("errorCode") == 11100) {
                cookie = consoleToken(username, password);
                requestSpecification.cookies(cookie);
                response = requestSpecification.cookies(cookie)
                        .body(params).log().all().post(urlPath);
                return response;
            }
            response.then().log().all();
            addHttpLogToAllure(r);
            log.info("响应----" + response.asString());
            return response;
        } else if ("get".equalsIgnoreCase(method)) {
            response = requestSpecification
                    .body(params).log().all().get(urlPath);
            if (response.jsonPath().getInt("errorCode") == 11100) {
                consoleToken(username, password);
                response = requestSpecification
                        .body(params).log().all().get(urlPath);
                return response;
            }
            response.then().log().all();
            addHttpLogToAllure(r);
            //log.info("响应----"+response.asString());
            return response;
        } else if ("delete".equalsIgnoreCase(method)) {
            response = requestSpecification.
                    body(params).log().all().delete(urlPath);
            if (response.jsonPath().getInt("errorCode") == 11100) {
                consoleToken(username, password);
                response = requestSpecification
                        .body(params).log().all().delete(urlPath);
            }
            response.then().log().all();
            addHttpLogToAllure(r);
            log.info("响应----" + response.asString());
            return response;
        } else {
            response = requestSpecification.
                    body(params).log().all().put(urlPath);
            if (response.jsonPath().getInt("errorCode") == 11100) {
                consoleToken(username, password);
                response = requestSpecification
                        .body(params).log().all().put(urlPath);
            }
            response.then().log().all();
            addHttpLogToAllure(r);
            log.info("响应----" + response.asString());
            return response;
        }
    }


    public static void addHttpLogToAllure(String name) {
        try {
            FileInputStream content = new FileInputStream("src/main/resources/data.log");
            Allure.addAttachment(name + "接口日志", content);
            content.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
