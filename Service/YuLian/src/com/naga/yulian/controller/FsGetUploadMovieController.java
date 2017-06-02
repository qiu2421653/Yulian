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

import com.naga.common.json.JsonResponse;
import com.naga.yulian.service.FsGetTagListService;
import com.naga.yulian.vo.FsGetTagListParameter;
import com.naga.yulian.vo.FsGetTagListVo;
import com.naga.yulian.vo.FsGetTagListVoOutDTO;

import net.sf.json.JSONObject;

/**
 * 获取对应标签集合
 * 
 * @author miaowei
 *
 */
@RestController
public class FsGetUploadMovieController {
    private static final Logger logger = LoggerFactory.getLogger(FsGetUploadMovieController.class);

    @Autowired
    private FsGetTagListService fsGetTagListService;

    /**
     * 获得主题列表
     * 
     */
    @RequestMapping(value = "FsGetUploadMovie", method = RequestMethod.POST)
    public @ResponseBody JsonResponse fsGetTagList(@RequestBody FsGetTagListParameter vo,
            HttpSession httpSession) {
        FsGetTagListVo a = new FsGetTagListVo();
        List<FsGetTagListVoOutDTO> fsGetTopicList = fsGetTagListService
                .getFsGetTagListVoList(vo.getUserID(), vo.getTagID());
        a.setTagList(fsGetTopicList);
        a.setToken("IamToken");
        return new JsonResponse().success(a);
    }
}
