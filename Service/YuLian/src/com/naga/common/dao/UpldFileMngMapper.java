package com.naga.common.dao;

import com.naga.common.entity.UpldFileMng;

public interface UpldFileMngMapper {

    /**
     * This method was generated by MyBatis Generator. This method corresponds to the database table t_upld_file_mng
     * @mbggenerated  Wed Apr 27 18:30:45 CST 2016
     */
    int deleteByPrimaryKey(String oId);

    /**
     * This method was generated by MyBatis Generator. This method corresponds to the database table t_upld_file_mng
     * @mbggenerated  Wed Apr 27 18:30:45 CST 2016
     */
    int insert(UpldFileMng record);

    /**
     * This method was generated by MyBatis Generator. This method corresponds to the database table t_upld_file_mng
     * @mbggenerated  Wed Apr 27 18:30:45 CST 2016
     */
    int insertSelective(UpldFileMng record);

    /**
     * This method was generated by MyBatis Generator. This method corresponds to the database table t_upld_file_mng
     * @mbggenerated  Wed Apr 27 18:30:45 CST 2016
     */
    UpldFileMng selectByPrimaryKey(String oId);

    /**
     * This method was generated by MyBatis Generator. This method corresponds to the database table t_upld_file_mng
     * @mbggenerated  Wed Apr 27 18:30:45 CST 2016
     */
    int updateByPrimaryKeySelective(UpldFileMng record);

    /**
     * This method was generated by MyBatis Generator. This method corresponds to the database table t_upld_file_mng
     * @mbggenerated  Wed Apr 27 18:30:45 CST 2016
     */
    int updateByPrimaryKey(UpldFileMng record);
}