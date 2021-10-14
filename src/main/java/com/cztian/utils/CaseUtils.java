package com.cztian.utils;

/**
 * 命名工具类
 */
public class CaseUtils {

    /**
     * 转换成驼峰命名
     */
    public static String toCamelCase(String name, boolean firstUpper) {
        if (null == name || 0 == name.trim().length()) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        //遍历字符，若发现下划线则置标志为true，表示下一个字符应调整为大写
        boolean flag = false;
        for (char ch : name.toCharArray()) {
            if (ch == '_') {
                flag = true;
            } else {
                sb.append(flag ? toUpper(ch) : ch);
                flag = false;
            }
        }
        if (firstUpper) {
            sb.setCharAt(0, toUpper(sb.charAt(0)));
        }
        return sb.toString();
    }

    private static char toUpper(char ch) {
        return ch >= 'a' && ch <= 'z' ? (char) (ch - 32) : ch;
    }

}
