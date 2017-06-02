package com.naga.yulian.vo;

import java.util.List;

public class FsGetTopicListVo {

    private List<FsGetTopicListAdvertsVo> adverts;

    private List<FsGetTopicListFirstTagsVo> firstTags;

    private FsGetTopicListMailTagVo mailTag;

    private List<FsGetTopicListRecommTagsVo> recommTags;

    private String token;

    private List<FsGetTopicListTopicListVo> topicList;

    public List<FsGetTopicListAdvertsVo> getAdverts() {
        return adverts;
    }

    public void setAdverts(List<FsGetTopicListAdvertsVo> adverts) {
        this.adverts = adverts;
    }

    public List<FsGetTopicListFirstTagsVo> getFirstTags() {
        return firstTags;
    }

    public void setFirstTags(List<FsGetTopicListFirstTagsVo> firstTags) {
        this.firstTags = firstTags;
    }

    public FsGetTopicListMailTagVo getMailTag() {
        return mailTag;
    }

    public void setMailTag(FsGetTopicListMailTagVo mailTag) {
        this.mailTag = mailTag;
    }

    public List<FsGetTopicListRecommTagsVo> getRecommTags() {
        return recommTags;
    }

    public void setRecommTags(List<FsGetTopicListRecommTagsVo> recommTags) {
        this.recommTags = recommTags;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public List<FsGetTopicListTopicListVo> getTopicList() {
        return topicList;
    }

    public void setTopicList(List<FsGetTopicListTopicListVo> topicList) {
        this.topicList = topicList;
    }

}