package com.naga.yulian.dao;

import java.util.Map;

import com.naga.yulian.entity.Score;

public interface GoldMapper {

	int updateAllGold(Map<String, String> map);

	int updateForkGold(Map<String, String> map);

	int updateCommentGold(Map<String, String> map);

	int updateTopicGold(Map<String, String> map);

	int updateTopicAndTagGold(Map<String, String> map);

	int updateFollowGold(Map<String, String> map);

	// 前十名加禹币
	int updateAllUserAddGold();

}