package com.naga.yulian.dao;

import java.util.List;
import java.util.Map;

import com.naga.yulian.vo.FirstListVo;

public interface FsGetFirstListMapper {

	List<Map<String, Object>> getFsGetFirstList(FirstListVo Vo);

	List<FirstListVo> getFsGetAllTags();

	List<Map<String, Object>> getFsGetUrlsByTag(Map<String, String> map);
}