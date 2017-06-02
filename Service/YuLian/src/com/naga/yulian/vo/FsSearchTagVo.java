package com.naga.yulian.vo;

import java.util.List;

public class FsSearchTagVo {

    private List<FsSearchTagDTOVo> tagDTO;

    private String token;

    public List<FsSearchTagDTOVo> getTagDTO() {
        return tagDTO;
    }

    public void setTagDTO(List<FsSearchTagDTOVo> tagDTO) {
        this.tagDTO = tagDTO;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

}
