package com.naga.yulian.dao;

import java.util.List;
import java.util.Map;

import com.naga.yulian.vo.FsGetUsedTagsVo;

/**
 * 
 * @author Qiu
 * 
 *         获得可使用的标签
 */
public interface FsGetUsedTagsMapper {

	List<FsGetUsedTagsVo> getUsedTags(Map<String, String> map);

}