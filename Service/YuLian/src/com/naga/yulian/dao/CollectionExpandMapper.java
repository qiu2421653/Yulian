package com.naga.yulian.dao;

import java.util.Map;

public interface CollectionExpandMapper {

    /**
     * 根据用户id、帖子id删除点赞
     * 
     * @param map
     * @return
     */
    int deleteById(Map<String, String> map);

}