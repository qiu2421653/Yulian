package com.naga.yulian.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.naga.common.json.JsonResponse;
import com.naga.yulian.service.FsSearchUserService;
import com.naga.yulian.vo.FsSearchUserDTOVo;
import com.naga.yulian.vo.FsSearchUserParameter;
import com.naga.yulian.vo.FsSearchUserVo;

/**
 * 搜索标签
 * 
 * @author miaowei
 *
 */
@RestController
public class FsSearchUserController {
    private static final Logger logger = LoggerFactory.getLogger(FsSearchUserController.class);

    @Autowired
    private FsSearchUserService fsSearchUserService;

    /**
     * 获得主题列表
     * 
     */
    @RequestMapping(value = "FsSearchUser", method = RequestMethod.POST)
    public @ResponseBody JsonResponse fsSearchUser(@RequestBody FsSearchUserParameter vo) {
        FsSearchUserVo a = new FsSearchUserVo();
        List<FsSearchUserDTOVo> fsGetTopicList = fsSearchUserService
                .getFsGetTagListVoList(vo.getUserID(), vo.getNickName());
        a.setOutDTO(fsGetTopicList);
        a.setToken("IamToken");
        return new JsonResponse().success(a);
    }
}
