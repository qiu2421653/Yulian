package com.naga.yulian.dao;

import com.naga.yulian.entity.Media;

public interface MediaMapper {
    int deleteByPrimaryKey(String uuid);

    int insert(Media record);

    int insertSelective(Media record);

    Media selectByPrimaryKey(String uuid);

    int updateByPrimaryKeySelective(Media record);

    int updateByPrimaryKey(Media record);
}