package com.naga.yulian.dao;

import java.util.List;
import java.util.Map;

import com.naga.yulian.vo.FsGetSearchUserRecommendVo;
import com.naga.yulian.vo.FsGetSearchUserUrlListVo;

public interface FsGetSearchHistoryMapper {

    /**
     * UserList
     * 
     */
    List<FsGetSearchUserUrlListVo> getSearchUserUrlList(Map<String, String> map);

    /**
     * UserList
     * 
     */
    List<FsGetSearchUserRecommendVo> getSearchUserRecommendList(Map<String, String> map);

}