package com.njkj.yulian.ui.activity;

import java.util.Timer;
import java.util.TimerTask;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.njkj.yulian.R;
import com.njkj.yulian.controller.LoginController;
import com.njkj.yulian.core.callback.SimpleCallback;
import com.njkj.yulian.entity.RetEntity;
import com.njkj.yulian.entity.UserEntity;
import com.njkj.yulian.utils.CLog;

public class RegisteredActivity extends BaseActivity implements OnClickListener {
	protected static final String TAG = "RegisteredActivity";
	private Button login_main_registered;// 注册
	private EditText registered_phone, registered_verification,
			registered_password;// 电话号,验证码,密码
	private LinearLayout registered_obtain_verification, registered_bottom;// 获取验证码
	private ImageButton registered_type;// 密码显示状态
	private TextView registered_message, registered_time;
	private boolean type = false;
	private boolean Registered_type = false;
	private int i = 60;
	private int TIME = 1000;
	private Timer timer;
	private boolean timeType = false;
	TimerTask task;

	LoginController mLoginController;// 登录controll

	Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			if (i >= 1) {
				registered_time.setText("重新获取(" + Integer.toString(i--) + ")");
			} else {
				registered_time.setText(R.string.verification_update);
				task.cancel();
				timeType = true;
				registered_obtain_verification.setClickable(true);
			}
		};
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.loginmain);
		setHeaderLeftText();
		mLoginController = new LoginController();
		Intent intent = getIntent();
		Registered_type = intent.getBooleanExtra("updatepassword", false);// 如果password有值则该页面代表修改密码页面
		initView();
	}

	private void initView() {
		timer = new Timer(true);
		registered_time = (TextView) findViewById(R.id.registered_time);
		registered_phone = (EditText) findViewById(R.id.registered_phone);
		registered_verification = (EditText) findViewById(R.id.registered_verification);
		registered_obtain_verification = (LinearLayout) findViewById(R.id.registered_obtain_verification);
		registered_password = (EditText) findViewById(R.id.registered_password);
		registered_type = (ImageButton) findViewById(R.id.registered_type);
		registered_message = (TextView) findViewById(R.id.registered_message);
		login_main_registered = (Button) findViewById(R.id.login_main_registered);
		registered_bottom = (LinearLayout) findViewById(R.id.registered_bottom);
		registered_obtain_verification.setOnClickListener(this);
		registered_type.setOnClickListener(this);
		registered_message.setOnClickListener(this);
		login_main_registered.setOnClickListener(this);
		if (Registered_type == true) {
			setHeaderTitle(R.string.update_password);
			registered_password.setHint("新密码");
			login_main_registered.setText(R.string.update_password);// 修改密码
			registered_bottom.setVisibility(View.GONE);
		} else {
			setHeaderTitle(R.string.registered);
			registered_password.setHint("密码");
			login_main_registered.setText(R.string.registered);
			registered_bottom.setVisibility(View.VISIBLE);
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.title_left1:
			finish();
			break;
		case R.id.login_main_registered:// 注册
			String mobile = registered_phone.getText().toString();
			String validCode = registered_verification.getText().toString();
			String passWord = registered_password.getText().toString();
			// 验证信息
			checkMessage(mobile, passWord, validCode);
			break;
		case R.id.registered_obtain_verification:// 获取验证码
			String phone = registered_phone.getText().toString();
			if ("".equals(phone))
				showShortToast("手机号不能为空");
			else
				verify(phone);
			break;
		case R.id.registered_type:// 显示密码/隐藏密码
			if (type == false) {
				registered_password
						.setTransformationMethod(HideReturnsTransformationMethod
								.getInstance());// 密码可见
				registered_type.setBackgroundResource(R.drawable.eye_open);
				type = true;
			} else {
				type = false;
				registered_password
						.setTransformationMethod(PasswordTransformationMethod
								.getInstance());// 密码不可见
				registered_type.setBackgroundResource(R.drawable.eye_close);
			}
			break;
		case R.id.registered_message:// 查看软件协议
			// TODO 用户协议
			startAnimActivity(AgreementActivity.class);
			break;
		default:
			break;
		}
	}

	/** 发送验证码 */
	public void verify(String phone) {
		showDialog();
		// 手机号
		String forgetPwd;
		mLoginController.reqValid(getString(R.string.FsGetValidCode), phone,
				new SimpleCallback() {
					@Override
					public void onCallback(Object data) {
						if (data == null) {
							showShortToast(getResources().getString(
									R.string.error));
						} else {
							RetEntity<UserEntity> entity = (RetEntity<UserEntity>) data;
							if (entity.success) {
								// 2604
								showShortToast("验证码已经发送,请注意查收");
								registered_obtain_verification
										.setClickable(false);
								if (timeType == true) {
									i = 60;
								}
								task = new TimerTask() {
									public void run() {
										Message message = new Message();
										message.what = 1;
										handler.sendMessage(message);
									}
								};
								timer.schedule(task, 1000, 1000); // 延时1000ms后执行，1000ms执行一次
								// 发送成功
							} else {
								showShortToast(entity.exceptions.get(0).message);
							}
						}
						hideProgress();
					}
				});
	}

	// 验证信息
	private void checkMessage(String mobile, String passWord, String validCode) {
		if (TextUtils.isEmpty(mobile)) {
			showShortToast("手机号不能为空");
			return;
		}
		if (TextUtils.isEmpty(passWord)) {
			showShortToast("密码不能为空");
			return;
		}
		if (TextUtils.isEmpty(validCode)) {
			showShortToast("验证码不能为空");
			return;
		}
		if (mobile.length() < 11) {
			showShortToast("手机号小于11位,请重新输入");
			return;
		}
		if (passWord.length() < 4) {
			showShortToast("密码小于4位,请重新输入");
			return;
		}
		if (validCode.length() < 4) {
			showShortToast("验证码小于4位,请重新输入");
			return;
		}
		if (Registered_type)
			// 修改密码
			reqUpdatePwd(mobile, passWord, validCode);
		else
			// 注册
			reqRegist(mobile, passWord, validCode);
	}

	/** 注册用户 */
	public void reqRegist(final String mobile, String passWord, String validCode) {
		showDialog();
		String latitude = mConfigDao.getString("latitude") == "" ? "38.919345"
				: mConfigDao.getString("latitude");
		String longitude = mConfigDao.getString("longitude") == "" ? "121.621391"
				: mConfigDao.getString("longitude");
		// 手机号
		mLoginController.reqRegist(getString(R.string.FsRegist), mobile,
				passWord, validCode, latitude, longitude, new SimpleCallback() {
					@Override
					public void onCallback(Object data) {
						if (data == null) {
							showShortToast(getResources().getString(
									R.string.error));
						} else {
							RetEntity<UserEntity> entity = (RetEntity<UserEntity>) data;
							if (entity.success) {
								// 存入用户ID
								mConfigDao.setString("userID",
										entity.result.userID);
								mConfigDao.setString("nickName", mobile);
								// 注册成功,跳转首页
								startAnimActivity(MainActivity.class);
								hideProgress();
								taskManager.removeActivity("LoginActivity");
								finish();
							} else {
								showShortToast(entity.exceptions.get(0).message);
							}
						}
						hideProgress();
					}
				});
	}

	/** 修改密码 */
	public void reqUpdatePwd(final String mobile, String passWord,
			String validCode) {
		showDialog();
		mLoginController.reqUpdatePwd(getString(R.string.FsUpdatePwd), mobile,
				passWord, validCode, new SimpleCallback() {
					@Override
					public void onCallback(Object data) {
						if (data == null) {
							showShortToast(getString(R.string.error));
						} else {
							RetEntity<UserEntity> entity = (RetEntity<UserEntity>) data;
							if (entity.success) {
								// 存入用户ID
								mConfigDao.setString("userID",
										entity.result.uuid);
								mConfigDao.setString("nickName", mobile);
								// 注册成功,跳转首页
								startAnimActivity(MainActivity.class);
								hideProgress();
								taskManager.removeActivity("LoginActivity");
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
	protected void onDestroy() {
		super.onDestroy();
		timer.cancel();// 退出计时器
	}
}
