package com.naga.common.filter;

/**
 * Created by 张玉良 on 2016-4-16.
 */
public enum  PerType {
    /**
     * 高 所有权限都验证. 最高安全级别
     */
    HIGH,

    /**
     * 中 只验证是否登陆 中等安全级别
     */
    MIDDLE,

    /**
     * 权限开启状态
     */
    LOW
}
