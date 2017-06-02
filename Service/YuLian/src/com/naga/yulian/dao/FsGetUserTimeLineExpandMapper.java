package com.naga.yulian.dao;

import java.util.List;

import com.naga.yulian.vo.FsGetUserTimeLineOutDTOTimeListVo;

public interface FsGetUserTimeLineExpandMapper {
	
    /**
     * 根据fkId获取用户头像
     * @param fkId
     * @return 用户头像URL
     */
    String gethPicByFkId(String fkId);

    /**
     * 根据userId获取用户uuid
     * @param userId
     * @return 用户uuid
     */
	String getUuidByUserId(String userId);

	/**
	 * 根据userId获取时间轴
	 * @param userId
	 * @return
	 */
	List<FsGetUserTimeLineOutDTOTimeListVo> fsGetUserTimeLine(String userId);

	/**
	 * 根据userId获取当前经历背景图片
	 * 
	 * @param userId
	 * @return
	 */
	String getTopicThumbById(String userId);
}