package com.naga.yulian.vo;

import java.util.List;

public class FsSearchUserVo {

    private List<FsSearchUserDTOVo> outDTO;

    private String token;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public List<FsSearchUserDTOVo> getOutDTO() {
        return outDTO;
    }

    public void setOutDTO(List<FsSearchUserDTOVo> outDTO) {
        this.outDTO = outDTO;
    }

}
