package com.qn.auto.common.ws;

import com.qn.auto.common.pojo.TerminalType;
import com.qn.auto.utils.PropertiesUtil;
import com.qn.auto.utils.RandomValueUtil;
import com.qn.auto.common.pojo.TerminalType;
import com.qn.auto.utils.PropertiesUtil;
import com.qn.auto.utils.RandomValueUtil;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.config.HeaderConfig;
import io.restassured.config.LogConfig;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.output.WriterOutputStream;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static com.qn.auto.utils.HttpsAdminUtils.*;
import static io.restassured.RestAssured.config;
import static io.restassured.RestAssured.given;

@Slf4j
@Data
public class TouristHttpsUtils {
    private static final String CONTENT_TYPE = "Content-Type";
    private static final String LOG_HEADER = "响应----";
    private static Map<String, String> initHeaders = new HashMap<>();

    private TouristHttpsUtils() {
        throw new IllegalStateException("Utility class");
    }

    public static void hdcHeadersInit(TerminalType terminalType) {
        initHeaders.put("X-Sd-Apiver", "1.0");
        initHeaders.put("X-Sd-Nonce", RandomValueUtil.getRandomStr(32));
        initHeaders.put("X-Sd-Timestamp", String.valueOf(System.currentTimeMillis()));
        initHeaders.put("X-QN-Project", "alpha");
        initHeaders.put("X-QN-Version", PropertiesUtil.getInstance().getValueByKey("X-QN-Version"));
        initHeaders.put("X-QN-Terminal", terminalType.getTerminal());
        //基线项目 initHeaders.put("X-QN-Appkey", "e5460e82b254776c86b81d97da2613e7");
        /*initHeaders.put("X-QN-Appkey", "9cfbc239f4c349eb0315f44dbab72c6d");*/
        initHeaders.put("X-QN-Appkey", PropertiesUtil.getInstance().getValueByKey("ak"));
        initHeaders.put(CONTENT_TYPE, "application/json; charset=UTF-8");
    }

    public static Map<String, String> touristAccessHeaders(String url, String body, TerminalType terminalType) throws MalformedURLException {
        hdcHeadersInit(terminalType);
        URL urlAdress = new URL(url);
        String path = urlAdress.getPath();
        initHeaders.put("Content-MD5", DigestUtils.md5Hex(body));
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMM");
        Date date = new Date(System.currentTimeMillis());
        String mouth = formatter.format(date);
        String key = "GeeDowTech_" + mouth.trim();
        String a = new Signature().getSignature(initHeaders, path, key);
        initHeaders.put("X-Sd-Signature", a);
        return initHeaders;
    }

    public static Map<String, String> touristAccessHeaders(String url, String body, String contentType, TerminalType terminalType) throws MalformedURLException {
        hdcHeadersInit(terminalType);
        URL urlAdress = new URL(url);
        String path = urlAdress.getPath();
        Map<String, String> header = initHeaders;
        header.remove("Content-Type");
        header.put("Content-Type", contentType);
        String value = terminalType.getTerminal() + "_" + PropertiesUtil.getInstance().getValueByKey("X-QN-Version");
        log.info("上传终端日志:{}", value);
        header.put("Content-MD5", DigestUtils.md5Hex(value));
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMM");
        Date date = new Date(System.currentTimeMillis());
        String mouth = formatter.format(date);
        String key = "GeeDowTech_" + mouth.trim();
        String a = new Signature().getSignature(header, path, key);
        header.put("X-Sd-Signature", a);
        return header;
    }

    public static RequestSpecification initConfigFile() throws IOException {
        RestAssured.useRelaxedHTTPSValidation();
        RestAssured.config = config().headerConfig(HeaderConfig.headerConfig().overwriteHeadersWithName(CONTENT_TYPE));
        FileWriter file = new FileWriter("src/main/resources/data.log");
        PrintStream printStream = new PrintStream(new WriterOutputStream(file, StandardCharsets.UTF_8), true);
        RestAssured.config = RestAssured.config().logConfig(LogConfig.logConfig().defaultStream(printStream));
        RequestSpecBuilder builder = new RequestSpecBuilder();
        builder.setConfig(RestAssured.config);
        builder.setContentType(multipart);
        return builder.build();
    }

    public static RequestSpecification initConfig() throws IOException {
        RestAssured.useRelaxedHTTPSValidation();
        RestAssured.config = config().headerConfig(HeaderConfig.headerConfig().overwriteHeadersWithName(AUTHORIZATION));
        RestAssured.config = config().headerConfig(HeaderConfig.headerConfig().overwriteHeadersWithName(CONTENT_TYPE));
        FileWriter file = new FileWriter("src/main/resources/data.log");
        PrintStream printStream = new PrintStream(new WriterOutputStream(file, StandardCharsets.UTF_8), true);
        RestAssured.config = RestAssured.config().logConfig(LogConfig.logConfig().defaultStream(printStream));
        RequestSpecBuilder builder = new RequestSpecBuilder();
        builder.setConfig(RestAssured.config);
        builder.setContentType(APPLICATION_JSON_CHARSET_UTF_8);
        return builder.build();
    }

    public static synchronized Response touristHttpsUtil(String method, String urlPath, String params, TerminalType terminalType, File file, String fileName, String fileType) throws Exception {
        RequestSpecification requestSpecification = given(initConfigFile());
        Map<String, String> map = touristAccessHeaders(urlPath, params, multipart, terminalType);
        requestSpecification = requestSpecification.headers(map);
        log.info("请求url----" + urlPath);
        log.info("请求header----" + map);
        log.info("请求方法----" + method);
        log.info("请求参数----" + params);
        Response response;
        String r = urlPath.trim().replace(PropertiesUtil.getInstance().getValueByKey("host"), "").replace("/", "/");
        if ("post".equalsIgnoreCase(method)) {
            response = requestSpecification
                    .multiPart(fileName, file, fileType)
                    .body(params).log().all().post(urlPath);
            response.then().log().all();
            addHttpLogToAllure(r);
            log.info(LOG_HEADER + response.asString());
            return response;
        } else if ("get".equalsIgnoreCase(method)) {
            response = requestSpecification
                    .multiPart(fileName, file, fileType)
                    .body(params).log().all().get(urlPath);
            response.then().log().all();
            addHttpLogToAllure(r);
            log.info(LOG_HEADER + response.asString());
            return response;
        } else if ("delete".equalsIgnoreCase(method)) {
            response = requestSpecification.
                    multiPart(fileName, file, fileType).
                    body(params).log().all().delete(urlPath);
            response.then().log().all();
            addHttpLogToAllure(r);
            log.info(LOG_HEADER + response.asString());
            return response;
        } else {
            response = requestSpecification.
                    multiPart(fileName, file, fileType).
                    body(params).log().all().put(urlPath);
            response.then().log().all();
            addHttpLogToAllure(r);
            log.info(LOG_HEADER + response.asString());
            return response;
        }
    }

    public static synchronized Response touristHttpsUtil(String method, String urlPath, String params, TerminalType terminalType) throws Exception {
        RequestSpecification requestSpecification = given(initConfig());
        Map<String, String> headers = touristAccessHeaders(urlPath, params, terminalType);
        requestSpecification = requestSpecification.headers(headers);
        log.info("请求url----" + urlPath);
        log.info("请求header----" + headers);
        log.info("请求方法----" + method);
        log.info("请求参数----" + params);
        Response response;
        String r = urlPath.trim().replace(PropertiesUtil.getInstance().getValueByKey("host"), "").replace("/", "/");
        if ("post".equalsIgnoreCase(method)) {
            response = requestSpecification
                    .body(params).log().all().post(urlPath);
            response.then().log().all();
            addHttpLogToAllure(r);
            log.info(LOG_HEADER + response.asString());
            return response;
        } else if ("get".equalsIgnoreCase(method)) {
            response = requestSpecification
                    .body(params).log().all().get(urlPath);
            response.then().log().all();
            addHttpLogToAllure(r);
            log.info(LOG_HEADER + response.asString());
            return response;
        } else if ("delete".equalsIgnoreCase(method)) {
            response = requestSpecification.
                    body(params).log().all().delete(urlPath);
            response.then().log().all();
            addHttpLogToAllure(r);
            log.info(LOG_HEADER + response.asString());
            return response;
        } else {
            response = requestSpecification.
                    body(params).log().all().put(urlPath);
            response.then().log().all();
            addHttpLogToAllure(r);
            log.info(LOG_HEADER + response.asString());
            return response;
        }
    }
}
