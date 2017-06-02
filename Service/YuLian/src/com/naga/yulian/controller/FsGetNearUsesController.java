package com.naga.yulian.controller;

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

import com.github.pagehelper.PageHelper;
import com.naga.common.json.JsonResponse;
import com.naga.yulian.entity.User;
import com.naga.yulian.service.FsEditUserInfoService;
import com.naga.yulian.service.FsGetNearUsersService;
import com.naga.yulian.vo.FsGetNearUserListVo;
import com.naga.yulian.vo.FsGetNearUsersInVo;
import com.naga.yulian.vo.FsGetNearUsersOutVo;

/**
 * 获取附近的人
 * 
 * @author Qiu
 */
@RestController
public class FsGetNearUsesController {
	private static final Logger logger = LoggerFactory.getLogger(FsGetNearUsesController.class);

	@Autowired
	private FsGetNearUsersService fsGetNearUsersService;

	@Autowired
	private FsEditUserInfoService fsEditUserInfoService;

	/**
	 * 获取附近的人
	 * 
	 * @return
	 */
	@RequestMapping(value = "FsGetNearUses", method = RequestMethod.POST)
	public @ResponseBody JsonResponse fsGetNearUses(@RequestBody FsGetNearUsersInVo vo) {
		// 实例化
		FsGetNearUserListVo returnVo = new FsGetNearUserListVo();
		List<FsGetNearUsersOutVo> nearUserList;

		if (StringUtils.isNotEmpty(vo.getUserId())) {
			User user = new User();
			user.setUserid(vo.getUserId());
			user.setLatitude(String.valueOf(vo.getLatitude()));
			user.setLongitude(String.valueOf(vo.getLongitude()));
			fsEditUserInfoService.editUserInfoByUuId(user);
		}
		try {
			// 分页
			PageHelper.startPage(vo.getCurrentPage() + 1, vo.getPageCount());

			nearUserList = fsGetNearUsersService.getNearUsersByDis(vo);

			returnVo.setOutDTO(nearUserList);
			return new JsonResponse().success(returnVo);
		} catch (Exception e) {
			return new JsonResponse().failure();
		}
	}

}
