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
import com.naga.yulian.service.FsSearchContentService;
import com.naga.yulian.vo.FsSearchContentDTOVo;
import com.naga.yulian.vo.FsSearchContentParameter;
import com.naga.yulian.vo.FsSearchContentVo;

import net.sf.json.JSONObject;

/**
 * 搜索标签
 * 
 * @author miaowei
 *
 */
@RestController
public class FsGetSearchContentController {
    private static final Logger logger = LoggerFactory
            .getLogger(FsGetSearchContentController.class);

    @Autowired
    private FsSearchContentService fsSearchContentService;

    /**
     * 获得主题列表
     * 
     */
    @RequestMapping(value = "FsGetSearchContent", method = RequestMethod.POST)
    public @ResponseBody JsonResponse fsSearchTag(@RequestBody FsSearchContentParameter vo) {
        FsSearchContentVo a = new FsSearchContentVo();
        List<FsSearchContentDTOVo> fsGetTopicList = fsSearchContentService
                .getFsGetContentListVoList(vo.getUserID(), "");
        a.setOutDTO(fsGetTopicList);
        a.setToken("IamToken");
        return new JsonResponse().success(a);
    }
}
