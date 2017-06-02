package com.naga.yulian.dao;

import java.util.List;
import java.util.Map;

import com.naga.yulian.vo.FsGetForkLoveStoryOutDtoVo;

public interface FsGetForkLoveStoryMapper {

    /**
     * TagList
     * 
     */
    List<FsGetForkLoveStoryOutDtoVo> getFsGetForkLoveStory(Map<String, String> map);

    Map<String, Object> getForkAndFun(Map<String, String> map);

    Map<String, Object> getForkFunAndIsFollow(Map<String, String> map);
}