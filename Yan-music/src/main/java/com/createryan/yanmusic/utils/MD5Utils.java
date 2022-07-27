package com.createryan.yanmusic.utils;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @author: createryan
 * @date 2022/7/26 20:59
 */

public class MD5Utils {
    public static String md5(String text) {

        // 用来保存加密后的密文的数组
        byte[] secreBytes = null;

        try {
            // 将明文转成byte数组并进行加密，获得密文数组
            secreBytes = MessageDigest.getInstance("md5").digest(
                    text.getBytes());
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("没有md5这个算法");
        }
        // 将二进制数组转16进制表示的字符串
        String md5code = new BigInteger(1, secreBytes).toString(16);
        //128位2进制数组转成16进制时，可能不足32位
        //在字符串前面补0，使所有的字符串长度一定是32位
        for (int i = 0; i < 32 - md5code.length(); i++) {
            md5code = "0" + md5code;
        }
        return md5code;
    }
}
