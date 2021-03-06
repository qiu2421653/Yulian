package com.naga.yulian.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.naga.yulian.dao.FsSearchTagMapper;
import com.naga.yulian.vo.FsSearchTagDTOVo;

/**
 * 获取对应标签集合
 * 
 * @author miaowei
 *
 */
@Service("FsSearchTagService")
public class FsSearchTagService {

    @Autowired
    private FsSearchTagMapper fsSearchTagMapper;

    /**
     * tagList
     * 
     */
    public List<FsSearchTagDTOVo> getFsGetTagListVoList(String userID, String content) {
        Map<String, String> a = new HashMap<String, String>();
        a.put("userID", userID);
        a.put("content", content);
        return fsSearchTagMapper.getFsSearchTagVoList(a);
    }

}
