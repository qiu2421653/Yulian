package com.naga.yulian.controller;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.naga.common.json.JsonResponse;
import com.naga.yulian.dao.ComEnumExpandMapper;
import com.naga.yulian.dao.TopicMapper;
import com.naga.yulian.entity.ComEnum;
import com.naga.yulian.entity.Topic;
import com.naga.yulian.vo.FsSharedInfoReturnVo;
import com.naga.yulian.vo.FsSharedInfoVo;

import net.sf.json.JSONObject;

/**
 * 根据帖子id分页取点赞人信息
 */
@RestController
public class FsSharedInfoController {
    private static final Logger logger = LoggerFactory.getLogger(FsSharedInfoController.class);
   
    @Autowired
    private ComEnumExpandMapper comEnumExpandMapper;

    @Autowired
    private TopicMapper topicMapper;
    
    /**
     * 获取分享内容
     * 
     * @param userId
     * @param httpSession
     * @return
     */
	@RequestMapping(value = "FsSharedInfo", method = RequestMethod.POST)
	public @ResponseBody JsonResponse FsSharedInfo(
			@RequestBody FsSharedInfoVo vo) {
		// 实例化
		FsSharedInfoReturnVo returnVo = new FsSharedInfoReturnVo();
		// 判断是否分享文章
		if(vo.getIsShareTopic() == 1){
			// 分享文章
			// 获取文章信息
			String topicId = vo.getTopicId();
			// 根据topicId获取内容
			Topic topic = topicMapper.selectByPrimaryKey(topicId);
			// 0 imageUrl
			returnVo.setImageUrl(topic.getCover());
			// 1 TODO siteUrl 网址未定
			returnVo.setSiteUrl("www.naga.com/"+topicId+"/index.html");
			// 2 text
			returnVo.setText(topic.getContent());
			// 3 title
			returnVo.setTitle(topic.getName());
			// 4 TODO titleUrl 网址未定
			returnVo.setTitleUrl("www.naga.com/"+topicId+"/index.html");
		}else{
			// 分享下载链接
			// 获取分享信息
			// 初始化
			List<ComEnum> list = new ArrayList<>();
			// 根据class获取enumList
			list = comEnumExpandMapper.getEnumByClass("ShareInfo");
			// 基础数据返回5条
			// 0 imageUrl
			returnVo.setImageUrl(list.get(0).getName());
			// 1 siteUrl
			returnVo.setSiteUrl(list.get(1).getName());
			// 2 text
			returnVo.setText(list.get(2).getName());
			// 3 title
			returnVo.setTitle(list.get(3).getName());
			// 4 titleUrl
			returnVo.setTitleUrl(list.get(4).getName());
		}
		//返回
		return new JsonResponse().success(returnVo);
	}
    
}
