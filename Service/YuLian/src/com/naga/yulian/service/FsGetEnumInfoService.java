package com.naga.yulian.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.naga.yulian.dao.ComEnumExpandMapper;
import com.naga.yulian.vo.FsGetEnumInfoVo;

@Service("FsGetEnumInfoService")
public class FsGetEnumInfoService {
	private static final Logger logger = LoggerFactory.getLogger(FsGetEnumInfoService.class);

	@Autowired
	private ComEnumExpandMapper comEnumExpandMapper;

	/**
	 * 根据关键字获取枚举信息
	 * @param keyword
	 * @return
	 */
	public List<FsGetEnumInfoVo> getEnumInfoByUserKeyWord(String keyword) {

		// record.setCreaterId(userID);
		return comEnumExpandMapper.getEnumInfoByUserKeyWord(keyword);
	}

}