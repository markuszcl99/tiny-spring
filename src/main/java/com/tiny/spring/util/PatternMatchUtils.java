package com.tiny.spring.util;

import com.sun.istack.internal.Nullable;

/**
 * @author: markus
 * @date: 2023/11/12 1:59 PM
 * @Description:
 * @Blog: https://markuszhang.com
 * It's my honor to share what I've learned with you!
 */
public abstract class PatternMatchUtils {
    public PatternMatchUtils() {
    }


    /**
     * 用给定的模式匹配字符串。 * 模式格式: "xxx*", "*xxx", "*xxx*" 以及 "xxx*yyy"，*代表若干个字符。
     */
    public static boolean simpleMatch(@Nullable String pattern, @Nullable String str) {
        // 先判断字符串或者匹配模式是否为空
        if (pattern == null || str == null) {
            return false;
        }

        // 在判断模式中是否包含*
        int firstIndex = pattern.indexOf('*');
        if (firstIndex == -1) {
            // 不包含的话就是精确匹配
            return pattern.equals(str);
        }
        // 是否首字符就是*，意味着 *XXX格式
        if (firstIndex == 0) {
            if (pattern.length() == 1) {
                // 只有一个 * ,那么就是匹配所有
                return true;
            }
            // 尝试查找下一个 *
            int nextIndex = pattern.indexOf('*', 1);
            if (nextIndex == -1) {
                // 没有下一个 * 了，说明后续不需要再进行模式匹配了，直接endWith判断
                return str.endsWith(pattern.substring(1));
            }
            // 截取两个 * 之间的部分
            String part = pattern.substring(1, nextIndex);
            if (part.isEmpty()) {
                // 这部分为空，形如**，则移到后面的模式进行匹配
                return simpleMatch(pattern.substring(nextIndex), str);
            }
            // 两个 * 之间不为空，则在字符串中查找这部分子串
            int partIndex = str.indexOf(part);
            while (partIndex != -1) {
                // 模式串移位到第二个 * 之后，目标字符串移位到子串之后，递归再进行匹配
                if (simpleMatch(pattern.substring(nextIndex), str.substring(partIndex + part.length()))) {
                    return true;
                }
                partIndex = str.indexOf(part, partIndex + 1);
            }
            return false;
        }

        // 对不是 * 开头的模式，前面部分要精确匹配，后面的子串重新递归匹配
        return (str.length() >= firstIndex &&
                pattern.substring(0, firstIndex).equals(str.substring(0, firstIndex)) &&
                simpleMatch(pattern.substring(firstIndex), str.substring(firstIndex)));
    }

    public static boolean simpleMatch(@Nullable String[] patterns, String str) {
        if (patterns != null) {
            String[] var2 = patterns;
            int var3 = patterns.length;

            for (int var4 = 0; var4 < var3; ++var4) {
                String pattern = var2[var4];
                if (simpleMatch(pattern, str)) {
                    return true;
                }
            }
        }

        return false;
    }
}
