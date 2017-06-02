package com.naga.yulian.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.naga.yulian.dao.FsGetOtherLoveStoryMapper;
import com.naga.yulian.vo.FsGetOtherLoveStoryOutDtoVo;

/**
 * 获取目标爱情经历
 * 
 * @author Qiu
 *
 */
@Service("FsGetOtherLoveStoryService")
public class FsGetOtherLoveStoryService {

	@Autowired
	private FsGetOtherLoveStoryMapper fsGetOtherLoveStoryMapper;

	/**
	 * tagList
	 * 
	 */
	public List<FsGetOtherLoveStoryOutDtoVo> getFsGetLoveListVoList(String userID) {
		Map<String, String> a = new HashMap<String, String>();
		a.put("userid", userID);
		return fsGetOtherLoveStoryMapper.getFsGetOtherLoveStory(a);
	}

}
