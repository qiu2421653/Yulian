package com.naga.yulian.dao;

import com.naga.yulian.entity.Collection;

public interface CollectionMapper {
    int deleteByPrimaryKey(String uuid);

    int insert(Collection record);

    int insertSelective(Collection record);

    Collection selectByPrimaryKey(String uuid);

    int updateByPrimaryKeySelective(Collection record);

    int updateByPrimaryKey(Collection record);
}