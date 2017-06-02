package com.naga.yulian.dao;

import java.util.Map;

import com.naga.yulian.entity.Follow;

public interface FollowMapper {
	int deleteByPrimaryKey(String uuid);

	int insert(Follow record);

	int insertSelective(Follow record);

	Follow selectByPrimaryKey(String uuid);

	int updateByPrimaryKeySelective(Follow record);

	int updateByPrimaryKey(Follow record);

	int selectById(Map<String, String> map);

	int updateByCreaterIdAndUserId(Map<String, String> map);
}