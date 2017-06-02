package com.naga.yulian.dao;

import com.naga.yulian.entity.Experience;
import com.naga.yulian.entity.Media;

public interface FsCreateLoveMapper {

    /**
     * TagList
     * 
     */
    int createLove(Experience topicVo);

    /**
     * TagList
     * 
     */
    int updateExperience(Experience topicVo);

    /**
     * TagList
     * 
     */
    int createLovePath(Media mediaVo);
}