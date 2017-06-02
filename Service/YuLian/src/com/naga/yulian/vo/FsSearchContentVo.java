package com.naga.yulian.vo;

import java.util.List;

public class FsSearchContentVo {

    private List<FsSearchContentDTOVo> outDTO;

    private String token;

    public List<FsSearchContentDTOVo> getOutDTO() {
        return outDTO;
    }

    public void setOutDTO(List<FsSearchContentDTOVo> outDTO) {
        this.outDTO = outDTO;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

}
