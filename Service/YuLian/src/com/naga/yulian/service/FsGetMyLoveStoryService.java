package com.naga.yulian.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.naga.yulian.dao.FsGetMyLoveStoryMapper;
import com.naga.yulian.vo.FsGetMyLoveStoryOutDtoVo;

/**
 * 获取对应标签集合
 * 
 * @author miaowei
 *
 */
@Service("FsGetMyLoveStoryService")
public class FsGetMyLoveStoryService {

    @Autowired
    private FsGetMyLoveStoryMapper fsGetMyLoveStoryMapper;

    /**
     * tagList
     * 
     */
    public List<FsGetMyLoveStoryOutDtoVo> getFsGetTagListVoList(String userID) {
        Map<String, String> a = new HashMap<String, String>();
        a.put("userid", userID);
        return fsGetMyLoveStoryMapper.getFsGetMyLoveStory(a);
    }

}
