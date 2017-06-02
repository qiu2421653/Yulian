package com.naga.yulian.dao;

import java.util.Map;

import com.naga.yulian.entity.Experience;

public interface FsUpdateLoveStoryMapper {

	// 获取打赏页列表集合
	int deleteLoveStory(Experience experience);

	int deleteTopic(Experience experience);

	int deletePraise(Experience experience);

	int deleteMedia(Experience experience);

	int deleteReply(Experience experience);

	int updateLoveStory(Experience experience);

	int updateLoveStoryHide(Map<String, String> map);

}