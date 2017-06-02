package com.naga.yulian.dao;

import java.util.List;

import com.naga.yulian.vo.FsGetRewardListVo;

public interface FsGetRewardListExpandMapper {
    
	//获取打赏页列表集合
	List<FsGetRewardListVo> fsGetRewardListById(String uuId);
}