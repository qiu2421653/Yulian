package com.naga.yulian.dao;

import java.util.List;
import java.util.Map;

import com.naga.yulian.vo.FsGetOtherLoveStoryOutDtoVo;

public interface FsGetOtherLoveStoryMapper {

    /**
     * TagList
     * 
     */
    List<FsGetOtherLoveStoryOutDtoVo> getFsGetOtherLoveStory(Map<String, String> map);

}