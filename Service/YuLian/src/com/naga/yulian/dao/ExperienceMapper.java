package com.naga.yulian.dao;

import com.naga.yulian.entity.Experience;

public interface ExperienceMapper {
	int deleteByPrimaryKey(String uuid);

	int insert(Experience record);

	int insertSelective(Experience record);

	Experience selectByPrimaryKey(String uuid);

	int updateByPrimaryKeySelective(Experience record);

	int updateByPrimaryKey(Experience record);
}