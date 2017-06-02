package com.naga.yulian.dao;

import com.naga.yulian.entity.Levelname;

public interface LevelnameExpandMapper {
	//获取当前积分所在的等级
	Levelname getCurrentLevel(String currentPoint);
}