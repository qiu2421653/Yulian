package com.naga.yulian.dao;

import java.util.List;
import java.util.Map;

import com.naga.yulian.vo.FsSearchContentDTOVo;

public interface FsSearchContentMapper {

    /**
     * TagList
     * 
     */
    List<FsSearchContentDTOVo> getFsSearchContentVoList(Map<String, String> map);

}