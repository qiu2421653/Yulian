package com.naga.yulian.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.PageHelper;
import com.naga.common.json.JsonResponse;
import com.naga.yulian.service.FsGetGoldDetailService;
import com.naga.yulian.vo.FsGetGoldDetailListVo;
import com.naga.yulian.vo.FsGetGoldDetailParamVo;
import com.naga.yulian.vo.FsGetGoldDetailVo;

import net.sf.json.JSONObject;

/**
 * 获得禹币明细
 */
@RestController
public class FsGetGoldDetailController {

	@Autowired
	private FsGetGoldDetailService fsGetGoldDetailService;

	/**
	 * 获得禹币明细
	 * 
	 * @param userID
	 * @return
	 */
	@RequestMapping(value = "FsGetGoldDetail", method = RequestMethod.POST)
	public @ResponseBody JsonResponse fsGetGoldDetailList(@RequestBody FsGetGoldDetailParamVo vo) {
		// 实例化
		FsGetGoldDetailListVo fsGetGoldDetailListVo = new FsGetGoldDetailListVo();
		// 分页
		PageHelper.startPage(vo.getCurrentPage() + 1, vo.getPageCount());
		// 获取Vo
		List<FsGetGoldDetailVo> fsGetGoldDetailVo = fsGetGoldDetailService.getGoldDetailList(vo.getUuId());
		if (fsGetGoldDetailVo.size() > 0) {

			for (int i = 0; i < fsGetGoldDetailVo.size(); i++) {
				if ("2".equals(fsGetGoldDetailVo.get(i).getType()) || "3".equals(fsGetGoldDetailVo.get(i).getType())) {

					fsGetGoldDetailVo.get(i).setOprAmount("+" + fsGetGoldDetailVo.get(i).getOprAmount());
				}

			}
		}
		// 设定FsGetGoldDetailVo
		fsGetGoldDetailListVo.setFsGetGoldDetailVo(fsGetGoldDetailVo);
		// 设定tokenID
		// TODO
		fsGetGoldDetailListVo.setToken("token");
		System.out.println(new JSONObject().fromObject(fsGetGoldDetailListVo));

		return new JsonResponse().success(fsGetGoldDetailListVo);
	}

}
