package com.naga.yulian.vo;

import java.util.List;

public class FsGetSearchUserVo {

    private List<FsGetSearchUserOutDTOVo> outDTO;

    private String token;

    public List<FsGetSearchUserOutDTOVo> getOutDTO() {
        return outDTO;
    }

    public void setOutDTO(List<FsGetSearchUserOutDTOVo> outDTO) {
        this.outDTO = outDTO;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

}
