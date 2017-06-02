package com.naga.yulian.dao;

import java.util.List;
import java.util.Map;

import com.naga.yulian.vo.FsGetForksVoOutDto;

/**
 * @author Qiu 获得关注用户
 */
public interface FsGetForkUsers {

	List<FsGetForksVoOutDto> fsGetForkUsers(Map<String, String> map);
}