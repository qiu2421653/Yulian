package com.naga.yulian.dao;

import java.util.Map;

import com.naga.yulian.entity.Score;

public interface ScoreMapper {

	int insert(Score score);

	int updateAllScore(Map<String, String> map);

	int selectUpdateState(Map<String, String> map);

	int updateTopicScore(Map<String, String> map);

	int updateTopicAndTagScore(Map<String, String> map);

	int updateTopicDateScore(Map<String, String> map);

	int updateTopicAndTagDateScore(Map<String, String> map);

	int updateForkScore(Map<String, String> map);

	int updateForkDateScore(Map<String, String> map);

	int updateCommentScore(Map<String, String> map);

	int updateCommentDateScore(Map<String, String> map);

	int updateFollow(Map<String, String> map);

	//前十名加积分
	int updateAllUserAddScore();

}