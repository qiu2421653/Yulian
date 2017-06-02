package com.naga.yulian.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.naga.yulian.dao.FsGetFirstListMapper;
import com.naga.yulian.vo.FirstListVo;
import com.naga.yulian.vo.FsGetFirstOutDtoVo;

@Service("FsGetFirstListService")
public class FsGetFirstListService {
	private static final Logger logger = LoggerFactory.getLogger(FsGetFirstListService.class);

	@Autowired
	private FsGetFirstListMapper FsGetFirstListMapper;

	public List<Map<String, Object>> SelectFirstList(FirstListVo Vo) {

		return FsGetFirstListMapper.getFsGetFirstList(Vo);
	}

	public List<FirstListVo> getFsGetAllTags() {

		return FsGetFirstListMapper.getFsGetAllTags();
	}

	public List<Map<String, Object>> getFsGetUrlsByTag(String tagCode) {
		Map<String, String> a = new HashMap<String, String>();
		a.put("tagCode", tagCode);
		return FsGetFirstListMapper.getFsGetUrlsByTag(a);
	}

}