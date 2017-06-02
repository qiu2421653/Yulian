package com.naga.yulian.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.naga.common.json.ApiException;
import com.naga.common.json.JsonResponse;
import com.naga.common.util.SystemConfigUtil;
import com.naga.common.util.UploadUtil;
import com.naga.common.vo.UploadFile;
import com.naga.yulian.dao.MediaMapper;
import com.naga.yulian.dao.UserExpandMapper;
import com.naga.yulian.dao.UserMapper;
import com.naga.yulian.entity.Media;
import com.naga.yulian.entity.User;
import com.naga.yulian.service.FsEditUserInfoService;
import com.naga.yulian.vo.FsGetUserInfoVo;
import com.naga.yulian.vo.FsSetTopicForkVo;

import net.sf.json.JSONObject;

/**
 * 编辑用户个人信息
 */
@RestController
public class FsEditUserInfoController {
	private static final Logger logger = LoggerFactory.getLogger(FsEditUserInfoController.class);

	@Autowired
	private FsEditUserInfoService fsEditUserInfoService;

	@Autowired
	private UserMapper userMapper;

	@Autowired
	private UserExpandMapper userExpandMapper;

	@Autowired
	private MediaMapper mediaMapper;

	@Autowired
	private SystemConfigUtil systemConfigUtil;

	/**
	 * 编辑用户个人信息
	 * 
	 * @param 当前页
	 * @param 每页显示
	 * @param 用户ID
	 * @return JsonResponse
	 */
	@RequestMapping(value = "FsEditUserInfo", method = RequestMethod.POST)
	public @ResponseBody JsonResponse fsEditUserInfoList(HttpServletRequest request,
			@RequestParam MultipartFile[] files, @RequestParam(name = "uuId") String uuId,
			@RequestParam(name = "sex") String sex, @RequestParam(name = "name") String name) {
		// 返回值
		int result = 0;

		// 上传文件
		List<String> urls = new ArrayList<String>();
		Map<String, String> heightMap = new HashMap<>();
		Map<String, String> widthMap = new HashMap<>();
		List<UploadFile> list = UploadUtil.uploadFiles(files, request, systemConfigUtil.getBasepath(),
				systemConfigUtil.getTopicpath());

		if (list != null && list.size() > 0) {
			for (UploadFile file : list) {
				if (file != null) {
					urls.add(file.getFilePath());
					heightMap.put(file.getFilePath(), file.getHeight() + "");
					widthMap.put(file.getFilePath(), file.getWidth() + "");
				}
			}
		}
		// 实例化
		Media media = new Media();
		// 删除media表中与该用户有关的图片信息
		result = fsEditUserInfoService.deleteInfoByUserId(uuId);
		// media.setFkId(userId);
		// 遍历
		if (urls != null && urls.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				// 实例化
				media = new Media();
				media.setUuid(UUID.randomUUID().toString());
				media.setFkId(uuId);
				media.setPath(systemConfigUtil.getWebpath() + urls.get(i));
				// TODO
				// media.setContent("");
				media.setHeight(heightMap.get(urls.get(i)));
				media.setWidth(widthMap.get(urls.get(i)));
				// 向media表中添加数据
				result = mediaMapper.insertSelective(media);
			}
		}
		User user = new User();
		user.setUuid(uuId);
		user.setName(name);
		user.setSex((short) Integer.parseInt(sex));
		// 更新user表的数据
		result = userMapper.updateByPrimaryKeySelective(user);
		// 判断是否成功
		if (result == 0) {
			logger.error("编辑失败");
			return new JsonResponse().failure(new ApiException("XXX", "编辑失败"));
		}
		// 插入积分
		if (urls != null && urls.size() > 0) {
			if (name != null && sex != null && sex != "0") {
				int updateScore = fsEditUserInfoService.updateScore(uuId);
				if (updateScore == 1) {
					// 插入金币
					fsEditUserInfoService.updateAllGold(uuId);
				}
			}
		}
		// 成功则实例化
		FsSetTopicForkVo vo = new FsSetTopicForkVo();
		vo.setIsSuccess(result);

		FsGetUserInfoVo selectUserInfoByUserId = userExpandMapper.selectUserInfoByUserId(uuId);
		return new JsonResponse().success(selectUserInfoByUserId);
	}

}
