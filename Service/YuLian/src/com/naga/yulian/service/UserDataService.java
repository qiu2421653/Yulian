package com.naga.yulian.service;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.naga.common.exception.MySystemException;
import com.naga.common.sms.ZhiyanApi;
import com.naga.yulian.dao.MediaMapper;
import com.naga.yulian.dao.MobilevalidateMapper;
import com.naga.yulian.dao.ScoreMapper;
import com.naga.yulian.dao.UserExpandMapper;
import com.naga.yulian.dao.UserMapper;
import com.naga.yulian.entity.Media;
import com.naga.yulian.entity.Mobilevalidate;
import com.naga.yulian.entity.Score;
import com.naga.yulian.entity.User;
import com.naga.yulian.vo.FsGetUserInfoVo;
import com.naga.yulian.vo.FsWeChatLoadVo;
import com.naga.yulian.vo.LoadInVo;
import com.naga.yulian.vo.LoadOutVo;
import com.naga.yulian.vo.RegistVo;

/**
 * 用户数据共通业务处理类
 * 
 * @author liangzhihao
 *
 */
@Service("UserDataService")
public class UserDataService {

	@Autowired
	private UserExpandMapper UserExpandMapper;
	@Autowired
	private UserMapper UserMapper;
	@Autowired
	private ScoreMapper scoreMapper;
	@Autowired
	private MediaMapper MediaMapper;
	@Autowired
	private MobilevalidateMapper MobilevalidateMapper;

	/**
	 * 创建新用户
	 */
	public int regist(String uuid, RegistVo vo) {
		User user = new User();
		user.setUuid(uuid);
		user.setCurrency(0);
		user.setSex((short) 0);
		user.setLatitude(vo.getLatitude());
		user.setLongitude(vo.getLongitude());
		user.setUserid(vo.getMobile());
		user.setPasswrod(this.getMD5(vo.getPassWord()));
		user.setName(vo.getMobile().substring(0, 4) + "xxxx" + vo.getMobile().substring(8, 11));
		return UserMapper.insert(user);
	}

	/**
	 * 创建新用户积分
	 */
	public int score(Score score) {
		return scoreMapper.insert(score);
	}

	/**
	 * 修改密码
	 */
	public int updatePwd(RegistVo vo) {
		vo.setPassWord(this.getMD5(vo.getPassWord()));
		return UserExpandMapper.updatePasswordByMobile(vo);
	}

	/**
	 * 修改密码
	 */
	public FsGetUserInfoVo selectUserByUserId(RegistVo vo) {
		return UserExpandMapper.selectUserByUserId(vo.getMobile());
	}

	/**
	 * 验证手机号码是否存在
	 * 
	 * @param mobile
	 *            手机号码
	 */
	public int checkMobile(String mobile) {
		int mobileCount = UserExpandMapper.selectUserLoginByMobile(mobile);
		return mobileCount;
	}

	/**
	 * 用户信息取得
	 * 
	 * @param vo
	 *            登录用参数
	 */
	public LoadOutVo selectUserInfo(LoadInVo vo) {
		vo.setPassWord(this.getMD5(vo.getPassWord()));
		return UserExpandMapper.selectUserByMobile(vo);
	}

	/**
	 * 用户信息取得(FACE++)
	 * 
	 * @param vo
	 *            登录用参数
	 */
	public LoadOutVo selectUserInfoByFace(LoadInVo vo) {
		return UserExpandMapper.selectUserByFace(vo);
	}

	/**
	 * 用户信息取得(weChat)
	 * 
	 * @param vo
	 *            登录用参数
	 */
	public LoadOutVo selectUserInfoByWeChat(FsWeChatLoadVo vo) {
		return UserExpandMapper.selectUserByWeChat(vo);
	}

	/**
	 * 用户注册(weChat)
	 * 
	 * @param vo
	 *            登录用参数
	 */
	public LoadOutVo insertUserInfoByWeChat(FsWeChatLoadVo vo) {
		// 实例化
		User user = new User();
		// 生成uuid
		String userUuid = UUID.randomUUID().toString();
		// 设值
		user.setUuid(userUuid);
		user.setUserid(vo.getOpenid());
		user.setPasswrod(vo.getOpenid());
		user.setName(vo.getNickname());
		user.setSex((short) vo.getSex());
		user.setClas((short) 0);
		// 插入
		if (UserMapper.insertSelective(user) > 0) {
			// 实例化
			Media media = new Media();
			// 设值
			media.setUuid(UUID.randomUUID().toString());
			media.setFkId(userUuid);
			media.setPath(vo.getHeadimgurl());
			// 插入
			MediaMapper.insertSelective(media);
		}

		LoadOutVo outVo = new LoadOutVo();
		outVo.setCurrency("0");
		outVo.sethPic(vo.getHeadimgurl());
		outVo.setName(vo.getNickname());
		outVo.setPoint("0");
		if (vo.getSex() == 1) {
			outVo.setSex("男");
		} else if (vo.getSex() == 2) {
			outVo.setSex("女");
		} else {
			outVo.setSex("不详");
		}
		outVo.setUserId(vo.getOpenid());
		outVo.setUuid(userUuid);

		return outVo;
	}

	/**
	 * 保存验证码
	 * 
	 * @param mobile
	 * @param validCode
	 */
	public void saveValidCode(String mobile, String validCode) {
		Mobilevalidate entity = new Mobilevalidate();
		entity.setMobile(mobile);
		entity.setValidateid(validCode);
		MobilevalidateMapper.deleteByPrimaryKey(mobile);
		MobilevalidateMapper.insert(entity);
	}

	/**
	 * 取得保存的验证码
	 * 
	 * @param mobile
	 * @return
	 */
	public String getValidCodeByMobile(String mobile) {
		String validCode = null;
		Mobilevalidate entity = MobilevalidateMapper.selectByPrimaryKey(mobile);
		if (entity != null) {
			validCode = entity.getValidateid();
		}
		return validCode;
	}

	/**
	 * 删除
	 * 
	 * @param mobile
	 * @param validCode
	 */
	public void deleteValidCode(String mobile) {
		MobilevalidateMapper.deleteByPrimaryKey(mobile);
	}

	/**
	 * 拨打验证电话
	 * 
	 * @param mobile
	 *            手机号码 code 验证码
	 */
	public String sendMsg(String mobile, String code) {

		// 实例化验证电话接口
		ZhiyanApi api = new ZhiyanApi();

		// 定义返回值
		String result = StringUtils.EMPTY;

		// 调用验证电话方法
		try {
			result = api.templateIdSend(mobile, code);

		} catch (Exception e) {
			// 调用异常时抛出错误。
			throw new MySystemException(e, "msg.common.10010");
		}

		// 返回结果
		return result;
	}

	/**
	 * 生成MD5
	 * 
	 * @param str
	 * @return
	 */
	public String getMD5(String str) {
		byte[] buf = str.getBytes();
		MessageDigest md5;
		StringBuilder sb = new StringBuilder();
		try {
			md5 = MessageDigest.getInstance("MD5");
			md5.update(buf);
			byte[] tmp = md5.digest();
			for (byte b : tmp) {
				sb.append(Integer.toHexString(b & 0xff));
			}
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return sb.toString();
	}

}
