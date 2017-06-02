package com.naga.yulian.dao;

import java.util.List;
import java.util.Map;

import com.naga.yulian.vo.FsGetUploadMovieVideoListVo;

public interface FsGetUploadMovieMapper {

    /**
     * TagList
     * 
     */
    List<FsGetUploadMovieVideoListVo> getFsGetUploadMovie(Map<String, String> map);

}