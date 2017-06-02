package com.naga.yulian.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.naga.yulian.dao.FsGetRewardListExpandMapper;
import com.naga.yulian.vo.FsGetRewardListVo;

@Service("FsGetRewardListService")
public class FsGetRewardListService {
	private static final Logger logger = LoggerFactory.getLogger(FsGetRewardListService.class);

	@Autowired
	private FsGetRewardListExpandMapper fsGetRewardListExpandMapper;

	/**
	 * 获取打赏页列表集合
	 * 
	 * @param userId
	 * @return
	 */
	public List<FsGetRewardListVo> fsGetRewardListById(String uuId) {

		return fsGetRewardListExpandMapper.fsGetRewardListById(uuId);
	}

}