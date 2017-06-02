package com.naga.yulian.dao;

import java.util.List;
import java.util.Map;

import com.naga.yulian.vo.FsGetNearUsersOutVo;

/**
 * 
 * @author Qiu
 * 
 *         新建标签
 */
public interface FsGetNearUsersMapper {

	List<FsGetNearUsersOutVo> getNearUsersByDis(Map<String, Object> map);
}