package com.qn.auto.common.ws;

import org.apache.commons.codec.digest.DigestUtils;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;
import java.util.Map;

public class Signature {
    private static final List<String> HEAD_NAME_EXCLUDE_APPEND = Arrays.asList("Content-MD5", "Content-Type");
    private static final List<String> HEAD_NAME_CLUDE_APPEND = Arrays.asList("Content-MD5", "Content-Type","X-Sd-Account","X-Sd-Apiver","X-Sd-Nonce","X-Sd-SerialNumber","X-Sd-Timestamp");
    public String getSignature(Map<String,String> headers,String url ,String pwdOrToken) {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("POST"+("\n")+url+("\n"));
        headers.entrySet().stream().sorted(Map.Entry.comparingByKey()).forEach(header -> {
            if(HEAD_NAME_CLUDE_APPEND.contains(header.getKey())) {
                if (!HEAD_NAME_EXCLUDE_APPEND.contains(header.getKey())) {
                    stringBuffer.append(header.getKey()).append(":").append(header.getValue());
                } else {
                    stringBuffer.append(header.getValue());
                }
                stringBuffer.append("\n");
            }
            });
        try {
            Mac mac = Mac.getInstance("HmacSHA256");
            String md5Hex = DigestUtils.md5Hex(pwdOrToken);
            mac.init(new SecretKeySpec(md5Hex.getBytes(), "HmacSHA256"));
            byte[] rawHmac = mac.doFinal(stringBuffer.toString().getBytes());
            String s = Base64.getEncoder().encodeToString(rawHmac);
            return s;
        } catch (Exception e) {
            return null;
        }
    }
    public String getSignaturePwdMd5(Map<String,String> headers,String url ,String pwdOrTokenMd5) {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("POST"+("\n")+url+("\n"));
        headers.entrySet().stream().sorted(Map.Entry.comparingByKey()).forEach(header -> {
            if(HEAD_NAME_CLUDE_APPEND.contains(header.getKey())) {
                if (!HEAD_NAME_EXCLUDE_APPEND.contains(header.getKey())) {
                    stringBuffer.append(header.getKey()).append(":").append(header.getValue());
                } else {
                    stringBuffer.append(header.getValue());
                }
                stringBuffer.append("\n");
            }
        });
        try {
            Mac mac = Mac.getInstance("HmacSHA256");
            mac.init(new SecretKeySpec(pwdOrTokenMd5.getBytes(), "HmacSHA256"));
            byte[] rawHmac = mac.doFinal(stringBuffer.toString().getBytes());
            String s = Base64.getEncoder().encodeToString(rawHmac);
            return s;
        } catch (Exception e) {
            return null;
        }
    }
}
