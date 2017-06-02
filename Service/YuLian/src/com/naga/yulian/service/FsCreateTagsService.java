package com.naga.yulian.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.naga.yulian.dao.FsCreateTagMapper;
import com.naga.yulian.entity.Tag;
import com.naga.yulian.vo.FsGetUsedTagsVo;

/**
 * 新建标签
 * 
 * @author Qiu
 *
 */
@Service("FsCreateTagsService")
public class FsCreateTagsService {

	@Autowired
	private FsCreateTagMapper fsCreateTagMapper;

	/**
	 * UsedTags
	 * 
	 */
	public int createTags(Tag tag) {
		return fsCreateTagMapper.createTags(tag);
	}

}
