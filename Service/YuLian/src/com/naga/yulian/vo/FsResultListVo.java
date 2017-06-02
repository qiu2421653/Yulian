package com.naga.yulian.vo;

import java.util.Map;

public class FsResultListVo {

  
     
    private Map<String, Object>  result;
 
    public Map<String, Object> getResult() {
        return result;
    }
    public void setResult(Map<String, Object> mapss) {
        this.result = mapss;
    }
    public String getIsSuccess() {
        return isSuccess;
    }
    public void setIsSuccess(String isSuccess) {
        this.isSuccess = isSuccess;
    }
    private String isSuccess;


}
