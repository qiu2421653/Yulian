package com.naga.yulian.dao;

import com.naga.yulian.entity.Reply;

public interface ReplyMapper {
    int deleteByPrimaryKey(String uuid);

    int insert(Reply record);

    int insertSelective(Reply record);

    Reply selectByPrimaryKey(String uuid);

    int updateByPrimaryKeySelective(Reply record);

    int updateByPrimaryKey(Reply record);
}