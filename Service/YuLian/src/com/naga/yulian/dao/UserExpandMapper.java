package com.naga.yulian.dao;

import com.naga.yulian.entity.User;
import com.naga.yulian.vo.FsGetUserInfoVo;
import com.naga.yulian.vo.FsWeChatLoadVo;
import com.naga.yulian.vo.LoadInVo;
import com.naga.yulian.vo.LoadOutVo;
import com.naga.yulian.vo.RegistVo;

public interface UserExpandMapper {
	/**
	 * 判断手机号码是否存在
	 * 
	 * @param 手机号码
	 * @return 存在数
	 */
	int selectUserLoginByMobile(String userId);

	/**
	 * 登录用户信息取得
	 * 
	 * @param 登录参数VO
	 * @return 用户信息
	 */
	LoadOutVo selectUserByMobile(LoadInVo vo);

	/**
	 * 登录用户信息取得
	 * 
	 * @param 登录参数VO
	 * @return 用户信息
	 */
	LoadOutVo selectUserByFace(LoadInVo vo);

	/**
	 * 修改密码
	 * 
	 * @param vo
	 * @return
	 */
	int updatePasswordByMobile(RegistVo vo);

	/**
	 * 获取用户个人信息
	 * 
	 * @param vo
	 * @return
	 */
	FsGetUserInfoVo selectUserInfoByUserId(String uuId);

	/**
	 * 编辑用户个人信息
	 * 
	 * @param user
	 * @return
	 */
	int editUserInfoByUserId(User user);
	
	/**
	 * 编辑用户个人信息
	 * 
	 * @param user
	 * @return
	 */
	int editUserInfoByUuId(User user);

	/**
	 * 获取用户个人信息
	 * 
	 * @param vo
	 * @return
	 */
	FsGetUserInfoVo selectUserByUserId(String uuId);

	LoadOutVo selectUserByWeChat(FsWeChatLoadVo vo);
}