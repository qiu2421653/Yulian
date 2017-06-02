package com.naga.yulian.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.naga.yulian.dao.AccountingExpandMapper;
import com.naga.yulian.entity.Accounting;
import com.naga.yulian.vo.FsGetGoldDetailVo;

@Service("FsGetGoldDetailService")
public class FsGetGoldDetailService {
	private static final Logger logger = LoggerFactory.getLogger(FsGetGoldDetailService.class);

	@Autowired
	private AccountingExpandMapper accountingExpandMapper;

	/**
	 * 获得禹币明细
	 * 
	 * @param 当前页
	 * @param 每页显示
	 * @param 用户ID
	 * @return FsGetCommentListVo
	 */
	public List<FsGetGoldDetailVo> getGoldDetailList(String uuId) {

		Accounting record = new Accounting();
		record.setCreaterid(uuId);
		return accountingExpandMapper.getGoldDetailList(record);

	}

}