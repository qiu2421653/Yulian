package com.naga.yulian.dao;

import com.naga.yulian.entity.ComEnum;

public interface ComEnumMapper {
    int deleteByPrimaryKey(String uuid);

    int insert(ComEnum record);

    int insertSelective(ComEnum record);

    ComEnum selectByPrimaryKey(String uuid);

    int updateByPrimaryKeySelective(ComEnum record);

    int updateByPrimaryKey(ComEnum record);
}