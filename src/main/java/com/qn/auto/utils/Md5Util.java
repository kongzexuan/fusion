package com.qn.auto.utils;

import io.restassured.RestAssured;

import java.io.IOException;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Map;

import static io.restassured.RestAssured.given;

public class Md5Util {
    public String encode16(String str) {
        byte[] digest = null;
        MessageDigest md5 = null;
        try {
            md5 = MessageDigest.getInstance("md5");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        digest = md5.digest(str.getBytes(StandardCharsets.UTF_8));
        String md5str = new BigInteger(1, digest).toString(16);
        return md5str;
    }
    public static void main(String[] args) throws IOException {
        RestAssured.useRelaxedHTTPSValidation();
        String params= "{\n" +
                "    \"uuid\": \"80100200001\",\n" +
                "    \"password\": \"3e6c7d141e32189c917761138b026b74\",\n" +
                "    \"accessRequired\": true,\n" +
                "    \"verCode\": \"天生我才必有用,下一句是什么\"\n" +
                "}";
        Map<String, String> cookies = given().
                header("Content-Type", "application/json;charset=utf-8").
                body(params).log().all().
                post("https://172.25.29.91/api/voip/v1/user/web/login").getCookies();
        given().header("Content-Type", "application/json;charset=utf-8").cookies(cookies).log().all().
                post("https://172.25.29.91/api/voip/v1/organization/list").then().log().all();
    }
}