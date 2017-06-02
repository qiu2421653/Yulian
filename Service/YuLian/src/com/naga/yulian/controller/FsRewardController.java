package com.naga.yulian.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

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
import com.naga.yulian.dao.UserMapper;
import com.naga.yulian.entity.User;
import com.naga.yulian.service.FsGetUserTimeLineService;
import com.naga.yulian.service.FsRewardService;
import com.naga.yulian.service.FsSetTopicForkService;
import com.naga.yulian.vo.FsGetTopicForks;
import com.naga.yulian.vo.FsGetTopicForksResultListVo;
import com.naga.yulian.vo.FsGetTopicForksVo;
import com.naga.yulian.vo.FsRewardVo;
import com.naga.yulian.vo.FsSetTopicForkVo;

import net.sf.json.JSONObject;

/**
 * 打赏
 */
@RestController
public class FsRewardController {
	
    private static final Logger logger = LoggerFactory.getLogger(FsRewardController.class);
    
    @Autowired
    private FsRewardService fsRewardService;
   
    @Autowired
    private FsGetUserTimeLineService fsGetUserTimeLineService;
    
    @Autowired
    private UserMapper userMapper;
    
    
    /**
     * 打赏
     * 
     * @param vo
     * @return
     */
	@RequestMapping(value = "FsReward", method = RequestMethod.POST)
	public @ResponseBody JsonResponse FsReward(
			@RequestBody FsRewardVo vo) {
		// 实例化
		int result = 0;
		FsSetTopicForkVo returnVo = new FsSetTopicForkVo();
		// 根据userId查询用户uuid
//		String uuid = fsGetUserTimeLineService.getUuidByUserId(vo.getUserId());
		String uuid = vo.getUserId();
		// 根据uuid获取user
		User user = userMapper.selectByPrimaryKey(uuid);
		BigDecimal currency = BigDecimal.valueOf(0);
		if(user.getCurrency() != null){
			currency = user.getCurrency();
		}
		
		// 与打赏数比较
		if(vo.getReward().compareTo(currency)>0){
			// 如果打赏钱数大于现有钱数
			logger.error("打赏禹币数大于现有禹币数");
			return new JsonResponse().failure(new ApiException("XXX",
					"打赏禹币数大于现有禹币数"));
		}else{
			vo.setUserId(uuid);
			// 执行打赏
			result = fsRewardService.fsReward(vo,user);
		}
		if(result == 0){
			// 打赏失败
			logger.error("打赏失败");
			return new JsonResponse().failure(new ApiException("XXX",
					"打赏失败"));
		}
		// 设值
		returnVo.setIsSuccess(1);
		//返回
		return new JsonResponse().success(returnVo);
	}
    
}
