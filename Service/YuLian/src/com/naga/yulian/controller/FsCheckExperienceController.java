package com.naga.yulian.controller;

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
import com.naga.yulian.dao.ExperienceExpandMapper;
import com.naga.yulian.entity.Experience;
import com.naga.yulian.vo.FsCheckExperienceReturnVo;
import com.naga.yulian.vo.UserIdVo;

/**
 * 根据用户id判断用户是否有可用经历
 */
@RestController
public class FsCheckExperienceController {
    private static final Logger logger = LoggerFactory.getLogger(FsCheckExperienceController.class);
   
    @Autowired
    private ExperienceExpandMapper experienceExpandMapper;

    
    /**
     * 根据用户id获取时间轴内容
     * 
     * @param userId
     * @param httpSession
     * @return
     */
	@RequestMapping(value = "FsCheckExperience", method = RequestMethod.POST)
	public @ResponseBody JsonResponse FsCheckExperience(
			@RequestBody UserIdVo vo) {
		// 根据userId取当前经历
    	Experience experience = experienceExpandMapper.getExperienceByUserId(vo.getUserID());
    	if(null == experience){
    		logger.error("该用户无可用经历");
			return new JsonResponse().failure(new ApiException("XXX",
					"该用户无可用经历"));
    	}
		// 成功则实例化
    	FsCheckExperienceReturnVo returnVo = new FsCheckExperienceReturnVo();
    	returnVo.setExpId(experience.getUuid());
		//返回
		return new JsonResponse().success(returnVo);
	}
    
}
