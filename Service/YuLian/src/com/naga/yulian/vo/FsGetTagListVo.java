package com.naga.yulian.vo;

import java.util.List;

public class FsGetTagListVo {

    private List<FsGetTagListVoOutDTO> tagList;

    private String token;

    public List<FsGetTagListVoOutDTO> getTagList() {
        return tagList;
    }

    public void setTagList(List<FsGetTagListVoOutDTO> tagList) {
        this.tagList = tagList;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

}
