package com.naga.yulian.controller;

import java.util.ArrayList;
import java.util.List;

import cn.jpush.api.JPushClient;
import cn.jpush.api.common.resp.APIConnectionException;
import cn.jpush.api.common.resp.APIRequestException;
import cn.jpush.api.push.PushResult;
import cn.jpush.api.push.model.Message;
import cn.jpush.api.push.model.Platform;
import cn.jpush.api.push.model.PushPayload;
import cn.jpush.api.push.model.PushPayload.Builder;
import cn.jpush.api.push.model.audience.Audience;

public class JPushCallBackNotifyController {

	private JPushClient jpush = new JPushClient("04602f4f5a4dae18a2f891cf", "38ec047baa1ba336c02a545c");// 修改这两个参数为你注册得到的

	private Platform platform = Platform.all();

	public Platform getPlatform() {
		return platform;
	}

	public void setPlatform(Platform platform) {
		this.platform = platform;
	}

	/**
	 * @param contendStr 推送内容
	 */
	public void pushMsg(String contendStr) {
		try {
			jpush.sendMessageAll(contendStr);
		} catch (APIConnectionException e) {
			// TODO LOG
			e.printStackTrace();
		} catch (APIRequestException e) {
			// TODO LOG
			e.printStackTrace();
		}
	}

	/**
	 * @param contendStr 推送内容
	 * @param aliasList 推送人集合
	 */
	public void pushMsg(String contendStr, List<String> aliasList) {
		Builder pushBuilder = PushPayload.newBuilder();
		// 设置发送设备
		pushBuilder.setPlatform(platform);
		// 推送人集合
		pushBuilder.setAudience(Audience.alias(aliasList));
		// 推送消息内容
		pushBuilder.setMessage(Message.content(contendStr));
		PushPayload ppl = pushBuilder.build();
		try {
			jpush.sendPush(ppl);
		} catch (APIConnectionException e) {
			// TODO LOG
			e.printStackTrace();
		} catch (APIRequestException e) {
			// TODO LOG
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		JPushCallBackNotifyController test = new JPushCallBackNotifyController();
		test.pushMsg("{\"topicId\":\"8d6693d2-d953-4894-98aa-c6fc40da29dc\","
				+ "\"title\":\"快来啊\",\"msg\":\"非常好看，哈哈哈哈哈\"}");
		System.out.println("-----Push Completed!");
	}

}