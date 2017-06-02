package com.naga.yulian.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.naga.yulian.dao.FsGetUsedTagsMapper;
import com.naga.yulian.vo.FsGetTagListVoOutDTO;
import com.naga.yulian.vo.FsGetUsedTagsVo;

/**
 * 获得可使用的标签
 * 
 * @author Qiu
 *
 */
@Service("FsGetUsedTagsService")
public class FsGetUsedTagsService {

	@Autowired
	private FsGetUsedTagsMapper gsGetUsedTagsMapper;

	/**
	 * UsedTags
	 * 
	 */
	public List<FsGetUsedTagsVo> getUsedTags(String userID) {
		Map<String, String> a = new HashMap<String, String>();
		a.put("userId", userID);
		return gsGetUsedTagsMapper.getUsedTags(a);
	}

}
