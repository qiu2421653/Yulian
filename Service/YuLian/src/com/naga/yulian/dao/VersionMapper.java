package com.naga.yulian.dao;

import com.naga.yulian.entity.Version;

public interface VersionMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table version
     *
     * @mbggenerated Mon May 16 10:51:17 CST 2016
     */
    int deleteByPrimaryKey(String uuid);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table version
     *
     * @mbggenerated Mon May 16 10:51:17 CST 2016
     */
    int insert(Version record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table version
     *
     * @mbggenerated Mon May 16 10:51:17 CST 2016
     */
    int insertSelective(Version record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table version
     *
     * @mbggenerated Mon May 16 10:51:17 CST 2016
     */
    Version selectByPrimaryKey(String uuid);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table version
     *
     * @mbggenerated Mon May 16 10:51:17 CST 2016
     */
    int updateByPrimaryKeySelective(Version record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table version
     *
     * @mbggenerated Mon May 16 10:51:17 CST 2016
     */
    int updateByPrimaryKey(Version record);
}