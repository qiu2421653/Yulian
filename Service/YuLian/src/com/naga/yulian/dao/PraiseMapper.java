package com.naga.yulian.dao;

import com.naga.yulian.entity.Praise;

public interface PraiseMapper {
    int deleteByPrimaryKey(String uuid);

    int insert(Praise record);

    int insertSelective(Praise record);

    Praise selectByPrimaryKey(String uuid);

    int updateByPrimaryKeySelective(Praise record);

    int updateByPrimaryKey(Praise record);

}