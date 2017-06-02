package com.naga.yulian.dao;

import java.util.List;
import java.util.Map;

import com.naga.yulian.vo.FsGetMyLoveStoryOutDtoVo;

public interface FsGetMyLoveStoryMapper {

    /**
     * TagList
     * 
     */
    List<FsGetMyLoveStoryOutDtoVo> getFsGetMyLoveStory(Map<String, String> map);

}