package com.naga.yulian.service;

import java.util.Date;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.naga.yulian.dao.AccusationMapper;
import com.naga.yulian.entity.Accusation;

@Service("FsReportTopicService")
public class FsReportTopicService {
	private static final Logger logger = LoggerFactory.getLogger(FsReportTopicService.class);

	@Autowired
	private AccusationMapper accusationMapper;

	/**
	 * 举报帖子
	 * 
	 * @param user
	 * @return
	 */
	public int insertAccusation(Accusation accusation) {

		accusation.setUuid(UUID.randomUUID().toString());
		accusation.setCreatedate(new Date());
		return accusationMapper.insertSelective(accusation);
	}

}