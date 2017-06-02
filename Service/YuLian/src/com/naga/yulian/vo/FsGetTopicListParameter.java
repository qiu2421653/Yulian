package com.naga.yulian.vo;

public class FsGetTopicListParameter {

    private String token;

    private int pageCount;

    private int currentPage;

    private String userID;

    private boolean isShowAll;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public int getPageCount() {
        return pageCount;
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public boolean isShowAll() {
        return isShowAll;
    }

    public void setShowAll(boolean isShowAll) {
        this.isShowAll = isShowAll;
    }

}