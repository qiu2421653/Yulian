package com.naga.yulian.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

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
import com.naga.yulian.service.FsGetForkUsersService;
import com.naga.yulian.vo.FsGetForkUsersParameter;
import com.naga.yulian.vo.FsGetForksUserVo;
import com.naga.yulian.vo.FsGetForksVoOutDto;

/**
 * 获得关注用户
 * 
 * @author Qiu
 *
 */
@RestController
public class FsGetForkUsersController {
	private static final Logger logger = LoggerFactory.getLogger(FsGetForkUsersController.class);

	@Autowired
	private FsGetForkUsersService fsGetForkUsersService;

	/**
	 * 获得关注用户
	 * 
	 */
	@RequestMapping(value = "FsGetForkUsers", method = RequestMethod.POST)
	public @ResponseBody JsonResponse fsGetForkUsers(@RequestBody FsGetForkUsersParameter vo, HttpSession httpSession) {

		FsGetForksUserVo returnVo = new FsGetForksUserVo();

		// 分页
		PageHelper.startPage(vo.getCurrentPage() + 1, vo.getPageCount());

		List<FsGetForksVoOutDto> fsGetForkUsers = fsGetForkUsersService.fsGetForkUsers(vo.getUserID());

		returnVo.setOutDTO(fsGetForkUsers);

		return new JsonResponse().success(returnVo);
	}
}
