package com.naga.common.util;

public class NumberUtils {

    /** 基本数字单位; */
    private static final String[] units = { "千", "百", "十", "" };

    /** 中文数字; */
    private static final char[] numChars = { '一', '二', '三', '四', '五', '六', '七',
            '八', '九' };

    /** 中文零 */
    private static char numZero = '零';

    /**
     * 将一位数字转换为一位中文数字; eg: 1 返回 一;
     * 
     * @param onlyArabNumber
     * @return
     */
    public static char numberCharArab2CN(char onlyArabNumber) {

        if (onlyArabNumber == '0') {
            return numZero;
        }

        if (onlyArabNumber > '0' && onlyArabNumber <= '9') {
            return numChars[onlyArabNumber - '0' - 1];
        }

        return onlyArabNumber;
    }

    /**
     * 将千以内的数字转换为中文数字;
     * 
     * @param num
     * @return
     */
    public static String numberKArab2CN(Integer num) {

        char[] numChars = (num + "").toCharArray();
        String tempStr = "";
        int inc = units.length - numChars.length;
        if (numChars.length == 2 && numChars[0] == '1') {
            // 两位数，并且是十几的数字时，
            tempStr = units[2] + numberCharArab2CN(numChars[1]);
        } else {
            for (int i = 0; i < numChars.length; i++) {
                if (numChars[i] != '0') {
                    tempStr += numberCharArab2CN(numChars[i]) + units[i + inc];
                } else {
                    tempStr += numberCharArab2CN(numChars[i]);
                }
            }
        }

        // 将连续的零保留一个
        tempStr = tempStr.replaceAll(numZero + "+", numZero + "");

        // 去掉未位的零
        tempStr = tempStr.replaceAll(numZero + "$", "");
        return tempStr;
    }
}
