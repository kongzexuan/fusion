package com.qn.auto.utils;

import com.qn.auto.constant.TestCaseConstant;

import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLDecoder;

public class FileUtil {
    /**
     * @param fileName 带有相对路径的文件名称,例如properties/global.properties  timg.jpg
     * @return
     */
    public static String geFilePathInResources(String fileName) throws UnsupportedEncodingException {
        URL url = ClassLoader.getSystemResource(fileName);// 获取文件的URL
        return URLDecoder.decode(url.getFile(), TestCaseConstant.DEFAULT_FILE_CHARSET_UTF8);
    }

}
