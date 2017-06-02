package com.njkj.yulian.ui.activity;

import java.io.File;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.njkj.yulian.R;
import com.njkj.yulian.controller.UpgradeController;
import com.njkj.yulian.core.callback.SimpleCallback;
import com.njkj.yulian.core.helper.UpgradeHelper;
import com.njkj.yulian.dao.ConfigDao;
import com.njkj.yulian.entity.RetEntity;
import com.njkj.yulian.entity.UpgradeEntity;
import com.njkj.yulian.utils.AppUtil;
import com.njkj.yulian.utils.AttPathUtils;
import com.njkj.yulian.utils.CLog;
import com.njkj.yulian.utils.DataCleanManager;

public class SettingActivity extends BaseActivity implements OnClickListener {

	protected static final String TAG = "SettingActivity";
	UpgradeHelper mUpgradeHelper; // 版本更新下载
	UpgradeController mUpgradeController; // 获取版本更新

	TextView tv_cache_size;// 缓存
	TextView tv_version_name;// 版本号
	TextView tv_login;// 登录|退出登录

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.setting);
		setHeaderTitle(R.string.setting);
		setHeaderLeftText();
		initView();
		initData();
	}

	@Override
	protected void onResume() {
		super.onResume();
		// 版本号
		tv_version_name.setText("v " + AppUtil.getVersionName());
		// 缓存大小
		tv_cache_size.setText(getCacheFileSize());

		if (TextUtils.isEmpty(mConfigDao.getString("userID")))
			tv_login.setText(getString(R.string.login));
		else
			tv_login.setText(getString(R.string.logout_current_account));

	}

	private void initView() {
		tv_cache_size = (TextView) findViewById(R.id.tv_cache_size);
		tv_version_name = (TextView) findViewById(R.id.tv_version_name);
		tv_login = (TextView) findViewById(R.id.tv_login);
	}

	private void initData() {
		mUpgradeController = new UpgradeController();
		mUpgradeHelper = new UpgradeHelper(mContext);
	}

	@Override
	public void onClick(View v) {
		super.onClick(v);
		switch (v.getId()) {
		case R.id.ll_feedback:
			// 意见反馈
			if (checkLogin()) {
				startActivity(new Intent(this, YJFKActivity.class));
			}
			break;
		case R.id.ll_about_us:
			// 关于我们
			startActivity(new Intent(this, AboutActivity.class));
			break;
		case R.id.ll_update_release:
			// 版本更新
			reqUpgrade();
			break;
		case R.id.ll_clear_cache:
			// 清缓存
			clearCache();
			break;
		case R.id.tv_login:
			// 退出登录
			if (checkLogin()) {
				loginOut();
			}
			break;
		case R.id.ll_push:
			// 推送
			startActivity(new Intent(this, PushSettingActivity.class));
			break;
		default:
			break;
		}
	}

	public void onMyClick(View view) {
		onClick(view);
	}

	// 版本更新
	private void reqUpgrade() {
		double version = AppUtil.getVersionName();
		int system = getResources().getInteger(R.integer.syscode);
		showDialog();
		mUpgradeController.reqUpgrade(getString(R.string.FsUpGrade), system,
				new SimpleCallback() {
					@Override
					public void onCallback(Object data) {
						// 有数据存在
						if (data != null) {
							RetEntity<UpgradeEntity> upgradeBean = (RetEntity<UpgradeEntity>) data;
							if (upgradeBean.success) {
								if (upgradeBean.result.forced == 0) {
									// TODO 版本对比,更新
									double curVersion = AppUtil
											.getVersionName();
									double netVersion = Double
											.parseDouble(upgradeBean.result.versionno);
									if (curVersion < netVersion) {
										// 需要更新
										// 设置描述
										mUpgradeHelper.confNorUpdate(
												upgradeBean.result.remarks,
												false);
										mConfigDao.setString("new_verison_url",
												upgradeBean.result.path);
									} else {
										showShortToast("已是最新版本");
									}
								} else {
									CLog.e(TAG, "强制更新");
									// 设置描述
									mUpgradeHelper.confNorUpdate(
											upgradeBean.result.remarks, true);
									mConfigDao.setString("new_verison_url",
											upgradeBean.result.path);
								}
							} else {
								// 出错了,不提示用户
								CLog.d(TAG, "false");
							}
						} else {
							showShortToast(getString(R.string.error));
						}
						hideProgress();
					}
				});
	}

	// 清除缓存
	private void clearCache() {
		if (getCacheFileSize().equals("0.0b")) {
			showShortToast("清除成功");
			return;
		} else {
			// 清除缓存(data/data/com.hkkj.didi/cache/bitmap??)
			boolean flag = DataCleanManager.delAllFile(AttPathUtils
					.getInternalDir().getAbsoluteFile().getAbsolutePath());
			if (flag) {
				showShortToast("清除成功");
			} else {
				showShortToast("清除成功");
			}
		}
		// 缓存大小
		tv_cache_size.setText(getCacheFileSize());
	}

	// DiskCache缓存大小
	private String getCacheFileSize() {
		// 缓存地址(data/data/com.naga.yulian/cache)
		File abFile = new File(AttPathUtils.getInternalDir().getAbsoluteFile()
				.getAbsolutePath());
		long folderSize = DataCleanManager.getFolderSize(abFile);
		String cache;
		float cacheSize;
		if (folderSize > 1024 * 1024) {
			cacheSize = (float) folderSize / 1024 / 1024;
			cache = Math.floor(cacheSize * 10d) / 10 + "M";
		} else if (folderSize > 1024) {
			cacheSize = (float) folderSize / 1024;
			cache = Math.floor(cacheSize * 10d) / 10 + "Kb";
		} else {
			cache = Math.floor(folderSize * 10d) / 10 + "b";
		}
		return cache;
	}

	/** 退出登录 */
	private void loginOut() {
		new AlertDialog.Builder(this)
				.setTitle(R.string.hint)
				.setMessage(R.string.login_out_exit_dialog_message)
				.setNegativeButton(R.string.record_camera_cancel_dialog_yes,
						new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								mConfigDao.removeAll();
								mConfigDao.setBoolean("first_start_app", false);
								taskManager.closeAllActivity();
								startAnimActivity(MainActivity.class);
								finish();
							}
						})
				.setPositiveButton(R.string.record_camera_cancel_dialog_no,
						null).setCancelable(false).show();
	}

}
