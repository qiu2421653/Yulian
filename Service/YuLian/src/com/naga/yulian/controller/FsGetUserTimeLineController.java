package com.naga.yulian.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.naga.common.json.ApiException;
import com.naga.common.json.JsonResponse;
import com.naga.yulian.dao.MediaExpandMapper;
import com.naga.yulian.service.FsGetUserTimeLineService;
import com.naga.yulian.vo.FsGetUserTimeLineOutDTOTimeListVo;
import com.naga.yulian.vo.FsGetUserTimeLineOutDTOVo;
import com.naga.yulian.vo.FsGetUserTimeLineVo;
import com.naga.yulian.vo.FsGetUserTopicListReturnImgUrlVo;
import com.naga.yulian.vo.FsGetUserTimeLineUrlVo;

/**
 * 根据用户id获取时间轴内容
 */
@RestController
public class FsGetUserTimeLineController {
	
    private static final Logger logger = LoggerFactory.getLogger(FsGetUserTimeLineController.class);
   
    @Autowired
    private FsGetUserTimeLineService fsGetUserTimeLineService;
    
    @Autowired
    private MediaExpandMapper mediaExpandMapper;

    /**
     * 根据用户id获取时间轴内容
     * 
     * @param userId
     * @param httpSession
     * @return
     */
	@RequestMapping(value = "FsGetUserTimeLine", method = RequestMethod.GET)
	public @ResponseBody JsonResponse FsGetUserTimeLine(
			@RequestParam String userId,HttpSession httpSession) {
		// 实例化
		FsGetUserTimeLineVo vo = new FsGetUserTimeLineVo();
		FsGetUserTimeLineOutDTOVo outDTO = new FsGetUserTimeLineOutDTOVo();
		// 根据userId查询用户uuid
//		String uuid = fsGetUserTimeLineService.getUuidByUserId(userId);
		String uuid = userId;
		// 根据用户uuid查询用户头像
		String hPicPath = fsGetUserTimeLineService.gethPicByFkId(uuid);
		// 设值
		outDTO.sethPic(hPicPath);
		// 根据uuid获取用户主题的背景图片
		String topicThumb = fsGetUserTimeLineService.getTopicThumbById(uuid);
		outDTO.setTopicThumb(topicThumb);
		
		
		List<FsGetUserTimeLineOutDTOTimeListVo> timeList = new ArrayList<>();
		List<FsGetUserTopicListReturnImgUrlVo> urllist = new ArrayList<>();
		
		timeList = fsGetUserTimeLineService.fsGetUserTimeLine(uuid);
		
		// 遍历
		for(int i=0;i<timeList.size();i++){
			// 初始化
			urllist = new ArrayList<>();
			// 根据帖子id获取图片列表
			urllist = mediaExpandMapper.getUrlListById(timeList.get(i).getTopicId());
			timeList.get(i).setUrlList(urllist);
		}
		
		// 设值时间轴列表信息
		outDTO.setTimeList(timeList);
		vo.setOutDTO(outDTO);
		
		return new JsonResponse().success(vo);
	}
    

}
