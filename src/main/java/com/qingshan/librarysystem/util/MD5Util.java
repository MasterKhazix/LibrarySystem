package com.qingshan.librarysystem.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * MD5 加密工具类 — 用于密码存储
 */
public class MD5Util {

    /**
     * 对字符串进行 MD5 加密，返回 32 位小写十六进制字符串
     */
    public static String md5(String input) {
        if (input == null || input.isEmpty()) {
            return "";
        }
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] digest = md.digest(input.getBytes());
            StringBuilder sb = new StringBuilder();
            for (byte b : digest) {
                // 每个字节转成 2 位十六进制
                sb.append(String.format("%02x", b & 0xff));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("MD5 加密失败", e);
        }
    }
}
