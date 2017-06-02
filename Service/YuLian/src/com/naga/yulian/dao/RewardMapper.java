package com.naga.yulian.dao;

import com.naga.yulian.entity.Reward;

public interface RewardMapper {
    int deleteByPrimaryKey(String uuid);

    int insert(Reward record);

    int insertSelective(Reward record);

    Reward selectByPrimaryKey(String uuid);

    int updateByPrimaryKeySelective(Reward record);

    int updateByPrimaryKey(Reward record);
}