package com.naga.yulian.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.mysql.jdbc.StringUtils;
import com.naga.common.json.ApiException;
import com.naga.common.json.JsonResponse;
import com.naga.common.util.SystemConfigUtil;
import com.naga.common.util.UploadUtil;
import com.naga.common.vo.UploadFile;
import com.naga.yulian.dao.ExperienceExpandMapper;
import com.naga.yulian.dao.GoldMapper;
import com.naga.yulian.dao.MediaMapper;
import com.naga.yulian.dao.ScoreMapper;
import com.naga.yulian.dao.TopicMapper;
import com.naga.yulian.entity.Experience;
import com.naga.yulian.entity.Media;
import com.naga.yulian.entity.Topic;
import com.naga.yulian.vo.FsSetTopicForkVo;

/**
 * 发布帖子
 */
@RestController
public class FsSetTopicController {

	private static final Logger logger = LoggerFactory.getLogger(FsSetTopicController.class);

	@Autowired
	private ExperienceExpandMapper experienceExpandMapper;

	@Autowired
	private TopicMapper topicMapper;

	@Autowired
	private MediaMapper mediaMapper;

	@Autowired
	private ScoreMapper scoreMapper;

	@Autowired
	private GoldMapper goldMapper;

	@Autowired
	private SystemConfigUtil systemConfigUtil;

	/**
	 * 填写帖子
	 * 
	 * @param request
	 * @param response
	 * @param files
	 * @param userId
	 * @param content
	 * @param tag
	 * @return
	 */
	@RequestMapping(value = "FsSetTopic", method = RequestMethod.POST)
	public @ResponseBody JsonResponse FsSetTopic(HttpServletRequest request, HttpServletResponse response,
			@RequestParam MultipartFile[] files, @RequestParam(name = "userId") String userId,
			@RequestParam(name = "content") String content, @RequestParam(name = "tag") String tag) {
		System.out.println("FsSetTopic----------->");
		// 根据userId取当前经历
		Experience experience = experienceExpandMapper.getExperienceByUserId(userId);
		if (null == experience) {
			logger.error("该用户无可用经历");
			return new JsonResponse().failure(new ApiException("XXX", "该用户无可用经历"));
		}

		// 上传文件
		List<String> urls = new ArrayList<String>();
		Map<String, String> heightMap = new HashMap<>();
		Map<String, String> widthMap = new HashMap<>();
		List<UploadFile> list = UploadUtil.uploadFiles(files, request, systemConfigUtil.getBasepath(),
				systemConfigUtil.getTopicpath());
		// List<UploadFile> list = UploadUtil.uploadFiles(files, request,
		// "D://upload/","test");
		if (list != null && list.size() > 0) {
			for (UploadFile file : list) {
				if (file != null) {
					urls.add(file.getFilePath());
					heightMap.put(file.getFilePath(), file.getHeight() + "");
					widthMap.put(file.getFilePath(), file.getWidth() + "");
				}
			}
		}

		// 向topic表中添加数据
		// 实例化
		Topic topic = new Topic();
		// 生成uuid
		String topicUuid = UUID.randomUUID().toString();
		// 设值
		topic.setUuid(topicUuid);
		topic.setExpId(experience.getUuid());
		topic.setName(" ");
		topic.setContent(content);
		topic.setCreaterId(userId);
		topic.setCreateDate(new Date());
		topic.setStatus((short) 10);
		topic.setTag(tag);
		// TODO 类型
		short topicType = 1;
		if (files.length > 0 && files[0].getSize() > 0) {
			topicType = 2;
		}
		topic.setType(topicType);
		// 设置封面
		if (urls != null && urls.size() > 0) {
			topic.setCover(systemConfigUtil.getWebpath() + urls.get(0));
		}
		// 插入
		topicMapper.insertSelective(topic);

		// 向media表中添加数据
		// 实例化
		Media media = new Media();
		// 遍历
		if (urls != null && urls.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				// 实例化
				media = new Media();
				media.setUuid(UUID.randomUUID().toString());
				media.setFkId(topicUuid);
				media.setPath(systemConfigUtil.getWebpath() + urls.get(i));
				// TODO
				media.setContent("");
				media.setHeight(heightMap.get(urls.get(i)));
				media.setWidth(widthMap.get(urls.get(i)));
				mediaMapper.insertSelective(media);
			}
		}
		// 查询
		Map<String, String> map = new HashMap<String, String>();
		map.put("uuId", userId);
		int dateStte = scoreMapper.selectUpdateState(map);
		int state = 0;
		if (dateStte == 1) {
			// 日期一致
			if (StringUtils.isNullOrEmpty(tag)) {
				state = scoreMapper.updateTopicScore(map);
				if (state == 1)
					goldMapper.updateTopicGold(map);
			} else {
				state = scoreMapper.updateTopicAndTagScore(map);
				if (state == 1)
					goldMapper.updateTopicAndTagGold(map);
			}

		} else {
			// 日期不一致
			if (StringUtils.isNullOrEmpty(tag)) {
				state = scoreMapper.updateTopicDateScore(map);
				if (state == 1)
					goldMapper.updateTopicGold(map);
			} else {
				state = scoreMapper.updateTopicAndTagDateScore(map);
				if (state == 1)
					goldMapper.updateTopicAndTagGold(map);
			}
		}
		// 成功则实例化
		FsSetTopicForkVo returnVo = new FsSetTopicForkVo();
		returnVo.setIsSuccess(1);
		// 返回
		return new JsonResponse().success(returnVo);
	}

}
