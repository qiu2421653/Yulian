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
import com.naga.yulian.dao.UserMapper;
import com.naga.yulian.entity.User;
import com.naga.yulian.vo.FsSetTopicForkVo;
import com.naga.yulian.vo.FsValidFaceVo;

/**
 * 根据用户id帖子id点赞/取消赞
 */
@RestController
public class FsValidFaceController {
    private static final Logger logger = LoggerFactory.getLogger(FsValidFaceController.class);
   
    @Autowired
    private UserMapper userMapper;

    
    /**
     * 验证face++
     * 
     */
	@RequestMapping(value = "FsValidFace", method = RequestMethod.POST)
	public @ResponseBody JsonResponse FsValidFace(
			@RequestBody FsValidFaceVo vo) {
		// 返回值
		int result = 0;
		// 查询user
		User user = userMapper.selectByPrimaryKey(vo.getUserID());
		// 设值
		user.setFace(vo.getFaceID());
		// 修改
		result = userMapper.updateByPrimaryKeySelective(user);
		//判断是否成功
		if(result==0){
			logger.error("认证失败");
			return new JsonResponse().failure(new ApiException("XXX",
					"脸部验证失败"));
		}
		// 成功则实例化
		FsSetTopicForkVo returnVo = new FsSetTopicForkVo();
		returnVo.setIsSuccess(1);
		//返回
		return new JsonResponse().success(returnVo);
	}
    
}
