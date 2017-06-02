package com.naga.yulian.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.naga.common.json.JsonResponse;
import com.naga.yulian.service.FsGetForkLoveStoryService;
import com.naga.yulian.vo.FsGetForkLoveStoryOutDtoVo;
import com.naga.yulian.vo.FsGetForkLoveStoryVo;
import com.naga.yulian.vo.FsSearchUserParameter;

/**
 * 获取对应标签集合
 * 
 * @author miaowei
 *
 */
@RestController
public class FsGetForkLoveStoryController {
    private static final Logger logger = LoggerFactory
            .getLogger(FsGetForkLoveStoryController.class);

    @Autowired
    private FsGetForkLoveStoryService fsGetForkLoveStoryService;

    /**
     * 获得主题列表
     * 
     */
    @RequestMapping(value = "FsGetForkLoveStory", method = RequestMethod.POST)
    public @ResponseBody JsonResponse fsGetForkLoveStory(@RequestBody FsSearchUserParameter vo,
            HttpSession httpSession) {
        FsGetForkLoveStoryVo a = new FsGetForkLoveStoryVo();
        List<FsGetForkLoveStoryOutDtoVo> fsGetTopicList = fsGetForkLoveStoryService
                .getFsGetTagListVoList(vo.getUserID());
        Map<String, String> returnMap = new HashMap<String, String>();
        returnMap = fsGetForkLoveStoryService.getForkAndFun(vo.getUserID());
        a.setOutDTO(fsGetTopicList);
        a.setToken("token");
        a.setForkNum(returnMap.get("forkNum").toString());
        a.setFunsNum(returnMap.get("funsNum").toString());
        return new JsonResponse().success(a);
    }
}
