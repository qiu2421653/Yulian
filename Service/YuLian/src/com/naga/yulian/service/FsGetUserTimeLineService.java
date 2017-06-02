package com.naga.yulian.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.naga.yulian.dao.FsGetUserTimeLineExpandMapper;
import com.naga.yulian.vo.FsGetUserTimeLineOutDTOTimeListVo;

/**
 * 时间轴service
 * 
 * @author wangyan
 *
 */
@Service("FsGetUserTimeLineService")
public class FsGetUserTimeLineService {

	@Autowired
    private FsGetUserTimeLineExpandMapper fsGetUserTimeLineExpandMapper;

	/**
	 * 根据userId获取用户头像
	 * 
	 * @param fkId
	 * @return
	 */
	public String gethPicByFkId(String fkId) {
		return fsGetUserTimeLineExpandMapper.gethPicByFkId(fkId);
	}
	
	/**
	 * 根据userId获取用户uuid
	 * 
	 * @param userId
	 * @return
	 */
	public String getUuidByUserId(String userId) {
		return fsGetUserTimeLineExpandMapper.getUuidByUserId(userId);
	}

	/**
	 * 根据userId获取时间轴列表
	 * 
	 * @param userId
	 * @return
	 */
	public List<FsGetUserTimeLineOutDTOTimeListVo> fsGetUserTimeLine(String userId) {
		return fsGetUserTimeLineExpandMapper.fsGetUserTimeLine(userId);
	}

	/**
	 * 根据userId获取当前经历背景图片
	 * 
	 * @param userId
	 * @return
	 */
	public String getTopicThumbById(String userId) {
		return fsGetUserTimeLineExpandMapper.getTopicThumbById(userId);
	}
	
}
