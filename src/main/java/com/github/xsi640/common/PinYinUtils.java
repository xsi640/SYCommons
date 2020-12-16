package com.github.xsi640.common;

import com.github.stuxuhai.jpinyin.PinyinException;
import com.github.stuxuhai.jpinyin.PinyinFormat;
import com.github.stuxuhai.jpinyin.PinyinHelper;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 汉语拼音转换工具类
 *
 * @author suyang
 **/
public class PinYinUtils {

    public static String toPinYin(char c, PinYinType type) throws PinyinException {
        return toPinYin(String.valueOf(c), type);
    }

    /**
     * 讲字符串转化为拼音
     *
     * @param text 要转换的字符串
     * @param type 转换的类型
     * @return 转换后的拼音
     * @throws PinyinException 转换失败
     */
    public static String toPinYin(String text, PinYinType type) throws PinyinException {
        return toPinYin(text, "", type);
    }

    /**
     * 讲字符串转化为拼音
     *
     * @param text     要转换的字符串
     * @param strSplit 分隔符
     * @param type     转换的类型
     * @return 转换后的拼音
     * @throws PinyinException 转换失败
     */
    public static String toPinYin(String text, String strSplit, PinYinType type) throws PinyinException {
        if (StringUtils.isEmpty(text)) {
            return "";
        }

        switch (type) {
            case Short:
                String pinyin = PinyinHelper.getShortPinyin(text);
                if (!StringUtils.isEmpty(strSplit)) {
                    List<String> lists = pinyin.chars().mapToObj(c -> String.valueOf((char) c)).collect(Collectors.toList());
                    return CollectionUtils.toString(lists, strSplit);
                }
                return pinyin;
            case ToneMark:
                return PinyinHelper.convertToPinyinString(text, strSplit, PinyinFormat.WITH_TONE_MARK);
            case ToneNumber:
                return PinyinHelper.convertToPinyinString(text, strSplit, PinyinFormat.WITH_TONE_NUMBER);
            default:
                return PinyinHelper.convertToPinyinString(text, strSplit, PinyinFormat.WITHOUT_TONE);
        }
    }

    public enum PinYinType {
        /**
         * 带声母的
         * nǐ,hǎo,shì,jiè
         */
        ToneMark,
        /**
         * 声母是数字的
         * ni3,hao3,shi4,jie4
         */
        ToneNumber,
        /**
         * 不带声母的
         * ni,hao,shi,jie
         */
        Normal,
        /**
         * 简写的
         * nhsj
         */
        Short
    }
}
