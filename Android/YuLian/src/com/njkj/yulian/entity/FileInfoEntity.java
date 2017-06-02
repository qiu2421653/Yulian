package com.njkj.yulian.entity;

/**
 * ==============================
 * 
 * @author Qiu
 * 
 * @Package com.njkj.yulian.entity
 * 
 * @Description:文件信息
 * 
 * @date 2016-5-6 上午10:46:39
 * 
 * @version 1.0 ==============================
 */
public class FileInfoEntity extends BaseEntity {

	/**   
	 *
	 */

	private static final long serialVersionUID = -928364241589208996L;

	public String name;
	public String path;
	public long lastModified;// 最后修改日期

}
