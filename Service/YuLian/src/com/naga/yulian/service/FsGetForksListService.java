package com.naga.yulian.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.naga.yulian.dao.PraiseExpandMapper;
import com.naga.yulian.entity.Praise;
import com.naga.yulian.vo.FsGetForksVo;

@Service("FsGetForksListService")
public class FsGetForksListService {
	private static final Logger logger = LoggerFactory.getLogger(FsGetForksListService.class);

	@Autowired
	private PraiseExpandMapper praiseExpandEntityMapper;

	/**
	 * 获取点赞页列表集合
	 * 
	 * @param 当前页
	 * @param 每页显示
	 * @param 用户ID
	 * @return FsGetForksVo
	 */
	public List<FsGetForksVo> selectPraiseByUserId(String userId) {

		Praise record = new Praise();
		record.setCreaterId(userId);
		return praiseExpandEntityMapper.selectPraiseByUserId(record);
	}

}