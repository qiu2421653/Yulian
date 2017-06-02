package com.naga.yulian.service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.naga.yulian.dao.FollowExpandMapper;
import com.naga.yulian.dao.FollowMapper;
import com.naga.yulian.entity.Follow;

/**
 * 点赞/取消赞service
 * 
 * @author wangyan
 *
 */
@Service("FsSetTopicCarefulService")
public class FsSetTopicCarefulService {

	@Autowired
	private FollowMapper followMapper;

	@Autowired
	private FollowExpandMapper followExpandMapper;

	/**
	 * 查询有无记录
	 * 
	 * @param userId
	 * @param expId
	 * @return
	 */
	public int selectById(String userId, String expId) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("userId", expId);
		map.put("createrId", userId);
		return followMapper.selectById(map);
	}

	/**
	 * 
	 * @param userId
	 * @param infoId
	 * @param isCareful
	 * @return
	 */
	public int FsSetTopicCareful(String userId, String expId) {
		// 返回值
		int resultCount = 0;
		// 点赞
		// 实例化
		Follow followEntity = new Follow();
		// 设值
		followEntity.setUuid(UUID.randomUUID().toString());
		followEntity.setUserId(expId);
		followEntity.setCreaterId(userId);
		followEntity.setCreateDate(new Date());
		followEntity.setStatus((short) 1);
		// 执行插入
		resultCount = followMapper.insert(followEntity);
		// 返回
		return resultCount;
	}

	/**
	 * 
	 * 修改
	 */
	public int updateByCreaterIdAndUserId(String userId, String expId) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("userId", expId);
		map.put("createrId", userId);
		return followMapper.updateByCreaterIdAndUserId(map);
	}

	/**
	 * 
	 * @param userId
	 * @param infoId
	 * @param isCareful
	 * @return
	 */
	public int FsSetTopicCarefulCancel(String userId, String expId) {
		// 返回值
		int resultCount = 0;
		// 取消赞
		// 实例化Map
		Map<String, String> map = new HashMap<>();
		// 将信息设置到map中
		map.put("userId", userId);
		map.put("expId", expId);
		// 根据用户id、帖子id删除点赞记录
		resultCount = followExpandMapper.deleteById(map);
		// 返回
		return resultCount;
	}

	/**
	 * 
	 * @param userId
	 * @param infoId
	 * @param isCareful
	 * @return
	 */
	public int FsupdateCarefulCancel(String userId, String expId) {
		// 返回值
		int resultCount = 0;
		// 取消赞
		// 实例化Map
		Map<String, String> map = new HashMap<>();
		// 将信息设置到map中
		map.put("userId", userId);
		map.put("expId", expId);
		// 根据用户id、帖子id删除点赞记录
		resultCount = followExpandMapper.updateById(map);
		// 返回
		return resultCount;
	}

}
