package com.naga.yulian.dao;

import com.naga.yulian.entity.Topic;

public interface TopicMapper {
    int deleteByPrimaryKey(String uuid);

    int insert(Topic record);

    int insertSelective(Topic record);

    Topic selectByPrimaryKey(String uuid);

    int updateByPrimaryKeySelective(Topic record);

    int updateByPrimaryKey(Topic record);
}