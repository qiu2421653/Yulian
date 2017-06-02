package com.naga.yulian.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.naga.yulian.dao.FsGetTagListMapper;
import com.naga.yulian.vo.FsGetTagListVoOutDTO;

/**
 * 获取对应标签集合
 * 
 * @author miaowei
 *
 */
@Service("FsGetUploadMovieService")
public class FsGetUploadMovieService {

    @Autowired
    private FsGetTagListMapper fsGetTagListMapper;

    /**
     * tagList
     * 
     */
    public List<FsGetTagListVoOutDTO> getFsGetTagListVoList(String userID, String tagID) {
        Map<String, String> a = new HashMap<String, String>();
        a.put("userid", userID);
        a.put("tag", tagID);
        return fsGetTagListMapper.getFsGetTagListVoList(a);
    }

}
