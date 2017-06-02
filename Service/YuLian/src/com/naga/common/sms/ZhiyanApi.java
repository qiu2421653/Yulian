package com.naga.common.sms;

import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONObject;


/**
 *语音验证码
 */
public class ZhiyanApi {

	/**
	 * 可发送系统模板、自定义通知类、自定义验证码类模板
	 * @param args
	 * @throws Exception
	 */
	public String templateIdSend(String pmobile, String code) throws Exception {
		// 时间戳
		long timestamp = System.currentTimeMillis();
		//智验apiKey
        String apiKey = "afaa3b2ceeea47f59ed7dba6eb922030";
        //手机号
        String mobile = pmobile;
        //语音重复播报次数
        int playTimes = 3;
        //请求的URL
        String url = "http://voicecode.zhiyan.cn/voice/voiceCode.json";
        //uid
        String uid = CommonUtil.md5Hex(apiKey + mobile + timestamp);
        Map<String, String> header = new HashMap<String, String>();

        header.put("Accept", "application/json");
        header.put("Content-Type", "application/json;charset=utf-8");
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("apiKey",apiKey);
        map.put("mobile",mobile);
        map.put("code",code);
        map.put("playTimes",playTimes);
        map.put("uid",uid);
        String d=JSONObject.fromObject(map).toString();
        String res = CommonUtil.doPostByStr(url,d,"UTF-8",header);
		return res;
	}
	
	public static void main(String[] args) throws Exception {
		ZhiyanApi za = new ZhiyanApi();
		String res = za.templateIdSend("13079832805","5189");
	}
	
}
