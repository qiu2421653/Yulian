package com.naga.common.util;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.*;
import java.util.Map.Entry;

import com.google.gson.Gson;

import ognl.Ognl;
import ognl.OgnlContext;
import ognl.OgnlException;

import com.naga.common.exception.MySystemException;

/**
 * 自定义共同Util类
 */
public class MyCommonUtil {

    /**
     * 取得UUID
     *
     * @return UUID
     */
    public static String makeUUID() {
        String rtn = UUID.randomUUID().toString();
        return rtn;
    }

    /**
     * 判断是否为空
     * trim后的内容进行判断
     *
     * @param target 判断对象字符串
     * @return true:为空 false:不为空
     */
    public static boolean isEmpty(String target) {
        return (target == null || target.trim().length() == 0 || "".equals(target));
    }

    /**
     * 对象类型转字符串，对象为空返回空字符串
     * @param obj 需要转换的对象
     * @return 转换后字符串
     */
    public static String obj2String(Object obj) {
        if(isNotNull(obj)) {
            return "";
        } else {
            return obj.toString();
        }
        
    }
    
    /**
     * 判断对象不为空。
     *
     * @param obj String||Map||List||Set
     * @return 不空返回true 否则false
     */
    public static boolean isNotNull(Object obj) {
        if (obj instanceof String) {
            String str = (String) obj;
            return (str != null && !str.trim().equals(""));
        } else if (obj instanceof Map) {
            Map map = (Map) obj;
            return (map != null && !map.isEmpty());
        } else if (obj instanceof List) {
            List list = (List) obj;
            return (list != null && !list.isEmpty());
        } else if (obj instanceof Set) {
            Set set = (Set) obj;
            return (set != null && !set.isEmpty());
        } else {
            return obj != null;
        }
    }

    /**
     * 获取ognl表达式的值
     *
     * @param expression ognl表达式
     * @param valueMap   表达式用数据
     * @return 表达式的值
     */
    public static String getExpressionValue(String expression, Map<String, String> valueMap) {
        OgnlContext context = new OgnlContext();
        for (Entry<String, String> entry : valueMap.entrySet()) {
            context.put(entry.getKey(), entry.getValue());
        }
        String rtn = "";
        try {
            rtn = String.valueOf(Ognl.getValue(expression, context, context.getRoot()));
        } catch (OgnlException e) {
            throw new MySystemException(e, "msg.common.10010");
        }

        return rtn;
    }

    /**
     * Get hex string from byte array
     */
    public static String toHexString(byte[] res) {
        StringBuffer sb = new StringBuffer(res.length << 1);
        for (int i = 0; i < res.length; i++) {
            String digit = Integer.toHexString(0xFF & res[i]);
            if (digit.length() == 1) {
                digit = '0' + digit;
            }
            sb.append(digit);
        }
        return sb.toString();
    }
    
    /**
     * 将object类型转换成BigDecimal，并保留两位小数
     * 如果转换对象为null或不可转换，则返回0.00
     * @param obj 传入参数
     * @return 转换后的对象
     */
    public static BigDecimal convToDecimal(Object obj) {
		BigDecimal bd = new BigDecimal("0.00");
    	DecimalFormat df = new DecimalFormat("#.00");
    	if (obj == null) {
    		return bd;
    	}
    	try {
    		bd = new BigDecimal(obj.toString());
    		bd = new BigDecimal(df.format(bd));
    	} catch (Exception e) {
    		return new BigDecimal("0.00");
    	}
    	return bd;
    }

    /**
     * 生成随机6位密码
     * 
     * @return
     */
    public static String getNewPwd() {
        // 35是因为数组是从0开始的，26个字母+10个数字
        int pwdLength = 6;
        final int maxNum = 62;
        int i; // 生成的随机数
        char[] str = { 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o',
                'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', '0', '1', '2', '3', '4', '5',
                '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M',
                'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z' };
        StringBuffer pwd = new StringBuffer("");
        Random r = new Random();
        int count = 0;
        while (count < pwdLength) {
            // 生成随机数，取绝对值，防止生成负数，
            i = Math.abs(r.nextInt(maxNum)); // 生成的数最大为62-1
            if (i >= 0 && i < str.length) {
                pwd.append(str[i]);
                count++;
            }
        }
        return pwd.toString();
    }
	/**
	 * 历史比较工具 by 江风成 2016-4-27
	 * @param name 字段名
	 * @param news 新字段名
	 * @param old 旧字段名
	 * @return 历史记录
	 */
	public static String comparison(String name, String news, String old) {
		if (news == null || old == null) {
			news = "";
			old = "";
		}
		if (news.equals(old)) {
			return "";
		} else {
			return name + "(" + old + "->" + news + ")" + ",";
		}
	}
    public static Map<String, Object> object2Map(Object obj){
        @SuppressWarnings("unchecked")
        Map<String, Object> map = new Gson().fromJson(new Gson().toJson(obj), HashMap.class);
        return map;
    }

    /**
     * 向左补0
     * 
     * @param str
     *            源字符串
     * @param targetLength
     *            目标长度
     * @return
     */
    public static String appendZroeToLeft(String str, int targetLength) {
        if (targetLength <= 0) {
            return str;
        }
        int len = 0;
        if (str == null || "".equals(str.trim())) {
            len = targetLength;
            str = "";
        } else {
            str = str.trim();
            len = targetLength - str.length();
        }
        if (len <= 0) {
            return str;
        }
        StringBuffer zero = new StringBuffer();
        for (int i = 0; i < len; i++) {
            zero.append("0");
        }
        zero.append(str);
        return zero.toString();
    }

    /**
     * 向左补0
     * 
     * @param value
     *            源数字
     * @param targetLength
     *            目标长度
     * @return
     */
    public static String appendZroeToLeft(long value, int targetLength) {
        return appendZroeToLeft(value + "", targetLength);
    }

    /**
     * 向左补0
     * 
     * @param value
     *            源数字
     * @param targetLength
     *            目标长度
     * @return
     */
    public static String appendZroeToLeft(int value, int targetLength) {
        return appendZroeToLeft(value + "", targetLength);
    }

    /**
     * 校验是否字典
     * 
     * @param dicDtlNum
     * @return
     */
    public static boolean isDic(String dicDtlNum) {
        return isNotEmpty(dicDtlNum) && !"0000".equals(dicDtlNum);
    }

    /**
     * 判断是否为空 trim后的内容进行判断
     * 
     * @param target
     *            判断对象字符串
     * @return true:不为空 false:为空
     */
    public static boolean isNotEmpty(String target) {
        return !isEmpty(target);
    }
    
    /**
     * 数字转中文数据
     * 
     * @param i
     * @return
     */
    public static String intToZH(int i) {
        String[] zh = { "零", "一", "二", "三", "四", "五", "六", "七", "八", "九" };
        String[] unit = { "", "十", "百", "千", "万", "十", "百", "千", "亿", "十"};

        String str = "";
        StringBuffer sb = new StringBuffer(String.valueOf(i));
        sb = sb.reverse();
        int r = 0;
        int l = 0;
        for (int j = 0; j < sb.length(); j++) {
            /**
             * 当前数字
             */
            r = Integer.valueOf(sb.substring(j, j + 1));

            if (j != 0)
                /**
                 * 上一个数字
                 */
                l = Integer.valueOf(sb.substring(j - 1, j));

            if (j == 0) {
                if (r != 0 || sb.length() == 1)
                    str = zh[r];
                continue;
            }

            if (j == 1 || j == 2 || j == 3 || j == 5 || j == 6 || j == 7 || j == 9) {
                if (r != 0)
                    str = zh[r] + unit[j] + str;
                else if (l != 0)
                    str = zh[r] + str;
                continue;
            }

            if (j == 4 || j == 8) {
                str = unit[j] + str;
                if ((l != 0 && r == 0) || r != 0)
                    str = zh[r] + str;
                continue;
            }
        }
        return str;
    }
}
