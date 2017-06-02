package com.naga.yulian.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.naga.yulian.dao.FsSearchContentMapper;
import com.naga.yulian.vo.FsSearchContentDTOVo;

/**
 * 获取对应标签集合
 * 
 * @author miaowei
 *
 */
@Service("FsSearchContentService")
public class FsSearchContentService {

    @Autowired
    private FsSearchContentMapper fsSearchContentMapper;

    /**
     * tagList
     */
    public List<FsSearchContentDTOVo> getFsGetContentListVoList(String userID, String content) {
        Map<String, String> a = new HashMap<String, String>();
        a.put("userID", userID);
        a.put("content", content);
        return fsSearchContentMapper.getFsSearchContentVoList(a);
    }

}
