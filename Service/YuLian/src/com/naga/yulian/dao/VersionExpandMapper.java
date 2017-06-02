package com.naga.yulian.dao;

import com.naga.yulian.entity.Version;


public interface VersionExpandMapper {
    
    /**
     * 最新版本信息取得
     * @param 当前客户端OS
     * @return 最新版本信息
     */
    Version selectLatestVersion(String system);
}