package com.njkj.yulian.entity;

import java.util.ArrayList;

/**
 * ==============================
 * 
 * @author Qiu
 * 
 * @Package com.njkj.yulian.entity
 * 
 * @Description: 举报
 * 
 * @date 2016-3-28 下午1:42:18
 * 
 * @version 1.0 ==============================
 */
public class ReportEntity extends BaseEntity {
	private static final long serialVersionUID = 2464845512579356961L;

	public String type;

	public String typeName;

	public ArrayList<ReportEntity> fsGetEnumInfoVo;
}
