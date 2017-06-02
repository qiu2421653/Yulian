package com.naga.yulian.controller;

import java.io.IOException;
import java.util.Random;

import javax.servlet.http.HttpSession;

import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.naga.common.json.ApiException;
import com.naga.common.json.JsonResponse;
import com.naga.common.sms.CallBackResut;
import com.naga.common.sms.CallBackState;
import com.naga.yulian.service.UserDataService;
import com.naga.yulian.vo.MobileVo;

/**
 * 获取验证码
 * 
 * @author liangzhihao
 *
 */
@RestController
public class FsGetValidCodeController {
	private static final Logger logger = LoggerFactory
			.getLogger(FsGetValidCodeController.class);

	@Autowired
	private UserDataService UserDataService;

	/**
	 * GET方式验证手机号码是否存在
	 * 
	 * @param mobile
	 *            手机号码
	 * @return int 存在人数
	 */
	@RequestMapping(value = "FsGetValidCode", method = RequestMethod.POST)
	public @ResponseBody JsonResponse fsGetValidCode(
			@RequestBody MobileVo vo,HttpSession httpSession) {
		try {
			String mobile = vo.getMobile();
			String code = makeRandom(4);
			String serviceResult = UserDataService.sendMsg(mobile, code);
			ObjectMapper mapper = new ObjectMapper();
			CallBackResut callBackResult;

			callBackResult = mapper.readValue(serviceResult,
					CallBackResut.class);

			if (CallBackState.SUCCESS.getState().equals(
					callBackResult.getResult())) {
				UserDataService.saveValidCode(mobile, code);
				return new JsonResponse().success();
			} else {
				logger.error("语音验证发送失败，错误码：" + callBackResult.getResult());
				return new JsonResponse().failure(new ApiException("XXX",
						"语音验证发送失败,请联系客户"));
			}
		} catch (IOException e) {
			logger.error("语音验证执行结果转换错误");
			return new JsonResponse().failure();
		}
	}
	
	/**
     * 随机产生设定位数的字符串
     * @param  count 位数
     * @return 随机数字
     */
	 private String makeRandom(int count){
	        StringBuffer sb = new StringBuffer();
	        String str = "0123456789";
	        Random r = new Random();
	        for(int i=0;i<count;i++){
	            int num = r.nextInt(str.length());
	            sb.append(str.charAt(num));
	            str = str.replace((str.charAt(num)+""), "");
	        }
	        return sb.toString();
	}
}
