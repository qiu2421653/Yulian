package com.naga.yulian.dao;

import java.util.List;
import java.util.Map;

import com.naga.yulian.vo.FsGetTagListVoOutDTO;

public interface FsGetTagListMapper {

    /**
     * TagList
     * 
     */
    List<FsGetTagListVoOutDTO> getFsGetTagListVoList(Map<String, String> map);

}