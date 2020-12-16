package com.github.xsi640.common;

/**
 * 通配符工具类
 */
public class WildcardUtils {
    /**
     * 通配符匹配
     * Usage:
     * <pre>
     *  wildcardMatching("abc", "a*")   //return true
     *  wildcardMatching("abc", "a??")  //return true
     *  wildcardMatching("abc", "a?")   //return false
     *  wildcardMatching("abc", "?b?")  //return true
     *  wildcardMatching("abc", "*c")   //return true
     * </pre>
     *
     * @param input   待测试的字符串
     * @param pattern 通配符字符
     * @return true-匹配，false-不匹配
     */
    public static boolean wildcardMatching(String input, String pattern) {
        if (input == null && pattern == null) {
            return true;
        }
        if (input != null && pattern == null) {
            return false;
        }
        if (input == null) {
            boolean allAsterisks = true;
            for (int i = 0; i < pattern.length(); i++) {
                if (pattern.charAt(i) != '*') {
                    return false;
                }
            }
            return true;
        }
        return wildcardMatchingInternal(input, pattern, 0, 0);
    }

    private static boolean wildcardMatchingInternal(String input, String pattern, int iIndex, int pIndex) {
        if (iIndex >= input.length() && pIndex >= pattern.length()) {
            return true;
        }
        if (iIndex >= input.length() && pattern.charAt(pIndex) != '*') {
            return false;
        }
        if (iIndex >= input.length() && pattern.charAt(pIndex) == '*') {
            return wildcardMatchingInternal(input, pattern, iIndex, pIndex + 1);
        }
        if (iIndex < input.length() && pIndex >= pattern.length()) {
            return false;
        }
        if (input.charAt(iIndex) == pattern.charAt(pIndex)) {
            return wildcardMatchingInternal(input, pattern, iIndex + 1, pIndex + 1);
        }
        if (pattern.charAt(pIndex) == '?') {
            return wildcardMatchingInternal(input, pattern, iIndex + 1, pIndex + 1);
        }
        if (pattern.charAt(pIndex) == '*') {
            return wildcardMatchingInternal(input, pattern, iIndex + 1, pIndex) ||
                    wildcardMatchingInternal(input, pattern, iIndex, pIndex + 1);
        }
        return false;
    }
}
