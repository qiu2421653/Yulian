package com.naga.yulian.dao;

import java.util.List;

import com.naga.yulian.vo.FsGEtGoldRankVo;

public interface FsGetGoldRankExpandMapper {
	
	
	List<FsGEtGoldRankVo> fsGetGoldRankList();
	
	FsGEtGoldRankVo getUserTop(String uuId);
    
}