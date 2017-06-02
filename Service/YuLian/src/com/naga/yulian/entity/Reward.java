package com.naga.yulian.entity;

import java.util.Date;

public class Reward {
    private String uuid;

    private String topId;

    private Integer currency;

    private String createrId;

    private Date createDate;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid == null ? null : uuid.trim();
    }

    public String getTopId() {
        return topId;
    }

    public void setTopId(String topId) {
        this.topId = topId == null ? null : topId.trim();
    }

    public Integer getCurrency() {
        return currency;
    }

    public void setCurrency(Integer currency) {
        this.currency = currency;
    }

    public String getCreaterId() {
        return createrId;
    }

    public void setCreaterId(String createrId) {
        this.createrId = createrId == null ? null : createrId.trim();
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
}