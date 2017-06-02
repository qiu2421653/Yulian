package com.naga.yulian.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.naga.yulian.dao.VersionExpandMapper;
import com.naga.yulian.entity.Version;

@Service("AppInfoService")
public class AppInfoService {
	@Autowired
    private VersionExpandMapper VersionExpandMapper;
	public Version getLatestVersionInfo(String system){
		return VersionExpandMapper.selectLatestVersion(system);
	}
}
