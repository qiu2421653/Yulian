package com.naga.yulian.dao;

import java.util.List;

import com.naga.yulian.entity.Accounting;
import com.naga.yulian.vo.FsGetGoldDetailVo;

public interface AccountingExpandMapper {
	
	//获取禹币明细
	List<FsGetGoldDetailVo> getGoldDetailList(Accounting record);
	
}