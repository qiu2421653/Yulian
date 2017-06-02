package com.naga.yulian.dao;

import java.util.Map;

public interface FollowExpandMapper {
	/**
	 * 根据用户id、帖子id删除点赞
	 * 
	 * @param map
	 * @return
	 */
	int deleteById(Map<String, String> map);

	int updateById(Map<String, String> map);
}