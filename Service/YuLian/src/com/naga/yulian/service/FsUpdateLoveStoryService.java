package com.naga.yulian.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.naga.yulian.dao.FsCreateLoveMapper;
import com.naga.yulian.dao.FsUpdateLoveStoryMapper;
import com.naga.yulian.entity.Experience;

/**
 * 获取对应标签集合
 * 
 * @author miaowei
 *
 */
@Service("FsUpdateLoveStoryService")
public class FsUpdateLoveStoryService {

	@Autowired
	private FsUpdateLoveStoryMapper fsUpdateLoveStory;

	@Autowired
	private FsCreateLoveMapper fsCreateLoveMapper;

	public int FsUpdateLoveStory(String uuid, short status) {
		int i = 1;
		Experience experienceVo = new Experience();

		experienceVo.setStatus(status);

		experienceVo.setUuid(uuid);
		i = fsCreateLoveMapper.updateExperience(experienceVo);
		if (status == 10) {
			// 设置当前
			updateLoveStory(experienceVo);
		}
		return i;
	}

	/**
	 * 
	 */
	public int deleteTopic(String uuid) {
		Experience experience = new Experience();
		experience.setUuid(uuid);
		return fsUpdateLoveStory.deleteTopic(experience);
	}

	/**
	 * 
	 */
	public int deletePraise(String uuid) {
		Experience experience = new Experience();
		experience.setUuid(uuid);
		return fsUpdateLoveStory.deletePraise(experience);
	}

	/**
	 * 
	 */
	public int deleteMedia(String uuid) {
		Experience experience = new Experience();
		experience.setUuid(uuid);
		return fsUpdateLoveStory.deleteMedia(experience);
	}

	/**
	 * 
	 */
	public int deleteReply(String uuid) {
		Experience experience = new Experience();
		experience.setUuid(uuid);
		return fsUpdateLoveStory.deleteReply(experience);
	}

	/**
	 * 
	 */
	public int deleteLoveStory(String uuid) {
		Experience experience = new Experience();
		experience.setUuid(uuid);
		return fsUpdateLoveStory.deleteLoveStory(experience);
	}

	/**
	 * tagList
	 * 
	 */
	public int updateLoveStory(Experience experience) {
		return fsUpdateLoveStory.updateLoveStory(experience);
	}

	/**
	 * tagList
	 * 
	 */
	public int updateLoveStoryHide(String createrId) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("createrId", createrId);
		return fsUpdateLoveStory.updateLoveStoryHide(map);
	}

}
