package com.njkj.yulian.ui.activity;

import java.util.HashMap;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.PlatformDb;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.wechat.friends.Wechat;

import com.njkj.yulian.R;
import com.njkj.yulian.controller.LoginController;
import com.njkj.yulian.core.callback.SimpleCallback;
import com.njkj.yulian.entity.RetEntity;
import com.njkj.yulian.entity.UserEntity;

public class LoginActivity extends BaseActivity implements OnClickListener,
		PlatformActionListener {
	private EditText password, phone;
	private ImageButton password_type;
	private boolean type = false;
	private Button login_submit;
	private TextView registered, forgot_password;// 注册
	private Button login_weixin, login_face;
	private int FACESUCCESS = 1;
	private int REGISTEREDSUCCESS = 2;
	private int UPDATEPASSWORDSUCCESS = 3;

	private LoginController controller;

	private boolean isGuide;// 由引导页进入

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);
		setHeaderTitle(R.string.login);
		setHeaderLeftText();
		initView();
		controller = new LoginController();
		isGuide = getIntent().getBooleanExtra("isGuide", false);
	}

	private void initView() {
		phone = (EditText) findViewById(R.id.phone);
		password = (EditText) findViewById(R.id.password);
		login_submit = (Button) findViewById(R.id.login_submit);
		login_weixin = (Button) findViewById(R.id.login_weixin);
		login_face = (Button) findViewById(R.id.login_face);
		registered = (TextView) findViewById(R.id.registered);
		forgot_password = (TextView) findViewById(R.id.forgot_password);
		password_type = (ImageButton) findViewById(R.id.password_type);
		registered.setOnClickListener(this);
		forgot_password.setOnClickListener(this);
		password_type.setOnClickListener(this);
		login_submit.setOnClickListener(this);
		login_weixin.setOnClickListener(this);
		login_face.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.title_left1:// title返回
			finish();
			break;
		case R.id.password_type:// 显示密码 /隐藏密码
			if (type == false) {
				password.setTransformationMethod(HideReturnsTransformationMethod
						.getInstance());// 密码可见
				password_type.setBackgroundResource(R.drawable.eye_open);
				type = true;
			} else {
				type = false;
				password.setTransformationMethod(PasswordTransformationMethod
						.getInstance());// 密码不可见
				password_type.setBackgroundResource(R.drawable.eye_close);
			}
			break;
		case R.id.login_submit:// 登陆
			String moblie = phone.getText().toString();
			String passWord = password.getText().toString();
			checkMessage(moblie, passWord);
			break;
		case R.id.login_weixin:// 微信
			showShortToast("微信");
			Platform weixin = ShareSDK.getPlatform(Wechat.NAME);
			authorize(weixin);
			break;
		case R.id.login_face:// face++
			Intent intent = new Intent(LoginActivity.this, CameraPreview.class);
			intent.putExtra("type", true);
			startActivityForResult(intent, FACESUCCESS);// 表示可以返回结果
			break;
		case R.id.registered:// 注册
			Intent intent1 = new Intent(LoginActivity.this,
					RegisteredActivity.class);
			intent1.putExtra("type", true);
			startActivityForResult(intent1, REGISTEREDSUCCESS);// 表示可以返回结果
			break;
		case R.id.forgot_password:// 忘记密码
			Intent intent2 = new Intent(LoginActivity.this,
					RegisteredActivity.class);
			intent2.putExtra("updatepassword", true);
			startActivityForResult(intent2, UPDATEPASSWORDSUCCESS);// 表示可以返回结果
			break;
		default:
			break;
		}
	}

	// 第三方登陆
	public void authorize(Platform plat) {
		// 判断指定平台是否已经完成授权
		if (plat.isAuthValid()) {
			String userId = plat.getDb().getUserName();
			Toast.makeText(this, userId, Toast.LENGTH_SHORT).show();
		}
		plat.setPlatformActionListener(this);
		// true不使用SSO授权，false使用SSO授权
		plat.SSOSetting(true);
		// 获取用户资料
		plat.showUser(null);
	}

	/** 验证信息 */
	private void checkMessage(String moblie, String passWord) {
		if (TextUtils.isEmpty(moblie)) {
			showShortToast("手机号不能为空！");
			return;
		}
		if (TextUtils.isEmpty(passWord)) {
			showShortToast("密码不能为空！");
			return;
		}
		if (moblie.length() < 11) {
			showShortToast("手机号码长度不正确,请重新输入！");
			return;
		}
		if (passWord.length() < 4) {
			showShortToast("密码长度不正确,请重新输入！");
			return;
		}
		String lat = mConfigDao.getString("latitude");
		String lon = mConfigDao.getString("longitude");
		reqLogin(moblie, passWord, lat, lon);
	}

	/** 密码登录 */
	private void reqLogin(final String mobile, String passWord, String lat,
			String lon) {
		showDialog();
		controller.reqLogin(getString(R.string.FsLoad), mobile, passWord, lat,
				lon, new SimpleCallback() {
					@Override
					public void onCallback(Object data) {
						if (data == null) {
							showShortToast(getString(R.string.error));
						} else {
							RetEntity<UserEntity> entity = (RetEntity<UserEntity>) data;
							if (entity.success) {
								// 登录成功,保存信息
								showShortToast("登陆成功");
								String nickName = mobile;
								mConfigDao.setString("userID",
										entity.result.uuid);
								if (!TextUtils.isEmpty(entity.result.name))
									nickName = entity.result.name;
								mConfigDao.setString("nickName", nickName);
								mConfigDao.setString("hPic", entity.result.hPic);
								if (isGuide)
									startAnimActivity(MainActivity.class);
								finish();
							} else {
								showShortToast(entity.exceptions.get(0).message);
							}
						}
						hideProgress();
					}
				});
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == FACESUCCESS) {
			if (resultCode == 1) {
				boolean type = data.getBooleanExtra("type", false);
				if (type == true) {
					showShortToast("登陆成功");
					finish();
				} else {
					showShortToast("登陆失败");
				}
			} else {
				showShortToast("登陆已取消");
			}
		} else if (requestCode == REGISTEREDSUCCESS) {
			if (resultCode == 1) {
				boolean type = data.getBooleanExtra("aaa", false);
				if (type == true) {
					showShortToast("注册成功");
				} else {
					showShortToast("注册失败");
				}
			}
		} else if (requestCode == UPDATEPASSWORDSUCCESS) {
			if (resultCode == 1) {
				boolean type = data.getBooleanExtra("aaa", false);
				if (type == true) {
					showShortToast("修改密码成功");
				} else {
					showShortToast("修改密码失败");
				}
			}
		}
	}

	@Override
	public void onCancel(Platform arg0, int arg1) {

	}

	@Override
	public void onComplete(Platform platform, int action,
			HashMap<String, Object> arg2) {
		// 用户资源都保存到res
		// 通过打印res数据看看有哪些数据是你想要的
		if (action == Platform.ACTION_USER_INFOR) {
			PlatformDb platDB = platform.getDb();// 获取数平台数据DB
			// 通过DB获取各种数据
			platDB.getToken();
			platDB.getUserGender();
			platDB.getUserIcon();
			platDB.getUserId();
			platDB.getUserName();
			System.out.println("用户资料: " + platDB.getUserName());
		}

	}

	@Override
	public void onError(Platform arg0, int arg1, Throwable arg2) {
		// TODO Auto-generated method stub

	}
}
