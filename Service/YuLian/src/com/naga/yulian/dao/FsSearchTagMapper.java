package com.naga.yulian.dao;

import java.util.List;
import java.util.Map;

import com.naga.yulian.vo.FsSearchTagDTOVo;

public interface FsSearchTagMapper {

    /**
     * TagList
     * 
     */
    List<FsSearchTagDTOVo> getFsSearchTagVoList(Map<String, String> map);

}