package com.example.demo;



import org.springframework.util.DigestUtils;

/**
 * @author wuweifeng wrote on 2017/10/26.
 */
public class CommonUtil {

    public static String md5(String str) {
        return DigestUtils.md5DigestAsHex(str.getBytes());
    }
}
