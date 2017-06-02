package com.naga.yulian.dao;

import com.naga.yulian.entity.Experience;

public interface ExperienceExpandMapper {

	/**
	 * 根据userId获取该用户可用经历
	 * 
	 * @param userId
	 * @return
	 */
	Experience getExperienceByUserId(String userId);

    
}