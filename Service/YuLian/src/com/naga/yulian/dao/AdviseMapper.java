package com.naga.yulian.dao;

import com.naga.yulian.entity.Advise;

public interface AdviseMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table advise
     *
     * @mbggenerated Mon May 16 14:28:38 CST 2016
     */
    int deleteByPrimaryKey(String uuid);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table advise
     *
     * @mbggenerated Mon May 16 14:28:38 CST 2016
     */
    int insert(Advise record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table advise
     *
     * @mbggenerated Mon May 16 14:28:38 CST 2016
     */
    int insertSelective(Advise record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table advise
     *
     * @mbggenerated Mon May 16 14:28:38 CST 2016
     */
    Advise selectByPrimaryKey(String uuid);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table advise
     *
     * @mbggenerated Mon May 16 14:28:38 CST 2016
     */
    int updateByPrimaryKeySelective(Advise record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table advise
     *
     * @mbggenerated Mon May 16 14:28:38 CST 2016
     */
    int updateByPrimaryKey(Advise record);
}