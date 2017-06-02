package com.naga.yulian.vo;

import java.util.List;

public class FsGetSearchUserRecommendVo {

    private String hPic;

    private String isFork;

    private String nickName;

    private List<FsGetSearchUserUrlListVo> urlList;

    private String userID;

    public String gethPic() {
        return hPic;
    }

    public void sethPic(String hPic) {
        this.hPic = hPic;
    }

    public String getIsFork() {
        return isFork;
    }

    public void setIsFork(String isFork) {
        this.isFork = isFork;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public List<FsGetSearchUserUrlListVo> getUrlList() {
        return urlList;
    }

    public void setUrlList(List<FsGetSearchUserUrlListVo> urlList) {
        this.urlList = urlList;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

}
