package com.naga.yulian.dao;

import java.util.List;
import java.util.Map;

import com.naga.yulian.vo.FsSearchUserRecommendVo;

public interface FsSearchUserMapper {

    /**
     * UserList
     * 
     */
    List<FsSearchUserRecommendVo> getFsSearchUserVoList(Map<String, String> map);

}