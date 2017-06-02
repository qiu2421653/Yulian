package com.naga.yulian.dao;

import java.util.List;

import com.naga.yulian.entity.ComEnum;
import com.naga.yulian.vo.FsGetEnumInfoVo;

public interface ComEnumExpandMapper {

	/**
	 * 根据class获取内容
	 * @param comClass
	 * @return
	 */
	List<ComEnum> getEnumByClass(String comClass);
	/**
	 * 根据关键字获取枚举信息
	 * @param keyword
	 * @return
	 */
	List<FsGetEnumInfoVo> getEnumInfoByUserKeyWord(String keyword);
    
}