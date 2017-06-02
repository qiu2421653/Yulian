package com.naga.yulian.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.naga.yulian.dao.AdviseMapper;
import com.naga.yulian.entity.Advise;

@Service("AdviseService")
public class AdviseService {
	@Autowired
	private AdviseMapper AdviseMapper;
	
	public void insert(Advise entity){
		AdviseMapper.insert(entity);
	}
}
