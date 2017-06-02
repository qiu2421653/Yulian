package com.naga.yulian.vo;

import java.util.List;

public class FsGetUploadMovieVo {

    private List<FsGetUploadMovieVideoListVo> videoList;

    private String token;

    public List<FsGetUploadMovieVideoListVo> getVideoList() {
        return videoList;
    }

    public void setVideoList(List<FsGetUploadMovieVideoListVo> videoList) {
        this.videoList = videoList;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

}
