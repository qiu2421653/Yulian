package com.naga.yulian.entity;

public class ComEnum {
    private String uuid;

    private String comClass;

    private String code;

    private String name;

    private String word;

    private Integer sort;

    private String remark;

    private String comDefault;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid == null ? null : uuid.trim();
    }

    public String getComClass() {
        return comClass;
    }

    public void setComClass(String comClass) {
        this.comClass = comClass == null ? null : comClass.trim();
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code == null ? null : code.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word == null ? null : word.trim();
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public String getComDefault() {
        return comDefault;
    }

    public void setComDefault(String comDefault) {
        this.comDefault = comDefault == null ? null : comDefault.trim();
    }
}