package com.naga.yulian.dao;

import java.util.List;
import java.util.Map;

import com.naga.yulian.entity.Praise;
import com.naga.yulian.vo.FsGetForksVo;
import com.naga.yulian.vo.FsGetTopicForksResultListVo;

public interface PraiseExpandMapper {
	
	/**
	 * 根据用户id、帖子id删除点赞
	 * 
	 * @param map
	 * @return
	 */
    int deleteByTopId(Map<String,String> map);

	List<FsGetTopicForksResultListVo> fsGetTopicForks(Map<String, Object> map);

	List<FsGetForksVo> selectPraiseByUserId(Praise record);
}