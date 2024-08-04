package com.example.coderunner.util;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class Base64Util {

    /**
     * 编码字符串为 Base64
     *
     * @param input 输入字符串
     * @return 编码后的 Base64 字符串
     */
    public static String encode(String input) {
        return Base64.getEncoder().encodeToString(input.getBytes(StandardCharsets.UTF_8));
    }

    /**
     * 解码 Base64 字符串
     *
     * @param encoded Base64 编码字符串
     * @return 解码后的字符串
     */
    public static String decode(String encoded) {
        if (encoded == null) {
            return null;
        }
        try {
            // 移除所有非 Base64 字符
            String sanitizedEncoded = encoded.replaceAll("[^A-Za-z0-9+/=]", "");
            return new String(Base64.getDecoder().decode(sanitizedEncoded), StandardCharsets.UTF_8);
        } catch (IllegalArgumentException e) {
            return "解码错误";
        }
    }
}
