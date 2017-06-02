package com.naga.yulian.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.naga.yulian.dao.FsGetGoldRankExpandMapper;
import com.naga.yulian.vo.FsGEtGoldRankVo;

@Service("FsGetGoldRankService")
public class FsGetGoldRankService {
	private static final Logger logger = LoggerFactory.getLogger(FsGetGoldRankService.class);

	@Autowired
	private FsGetGoldRankExpandMapper fsGetGoldRankExpandMapper;

	/**
	 * 获得禹币排行榜
	 * 
	 * @param currentPage
	 * @param pageCount
	 * @param userID
	 * @return
	 */
	public List<FsGEtGoldRankVo> fsGetGoldRankList() {

		// Reply record = new Reply();
		// record.setCreaterId(userID);
		return fsGetGoldRankExpandMapper.fsGetGoldRankList();
	}

	/**
	 * 获得禹币排行榜
	 * 
	 * @param currentPage
	 * @param pageCount
	 * @param userID
	 * @return
	 */
	public FsGEtGoldRankVo getUserTop(String uuId) {

		// Reply record = new Reply();
		// record.setCreaterId(userID);
		return fsGetGoldRankExpandMapper.getUserTop(uuId);
	}

}