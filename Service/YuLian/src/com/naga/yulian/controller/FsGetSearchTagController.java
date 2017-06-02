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
import com.naga.yulian.service.FsSearchTagService;
import com.naga.yulian.vo.FsSearchTagDTOVo;
import com.naga.yulian.vo.FsSearchTagParameter;
import com.naga.yulian.vo.FsSearchTagVo;

import net.sf.json.JSONObject;

/**
 * 搜索标签
 * 
 * @author miaowei
 *
 */
@RestController
public class FsGetSearchTagController {
    private static final Logger logger = LoggerFactory.getLogger(FsGetSearchTagController.class);

    @Autowired
    private FsSearchTagService fsSearchTagService;

    /**
     * 获得主题列表
     * 
     */
    @RequestMapping(value = "FsGetSearchTag", method = RequestMethod.POST)
    public @ResponseBody JsonResponse fsSearchTag(@RequestBody FsSearchTagParameter vo) {
        FsSearchTagVo a = new FsSearchTagVo();
        List<FsSearchTagDTOVo> fsGetTopicList = fsSearchTagService
                .getFsGetTagListVoList(vo.getUserID(), "");
        a.setTagDTO(fsGetTopicList);
        a.setToken("IamToken");
        return new JsonResponse().success(a);
    }
}
