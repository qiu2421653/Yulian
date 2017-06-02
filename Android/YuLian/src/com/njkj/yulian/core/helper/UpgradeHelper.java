package com.njkj.yulian.core.helper;

import java.io.File;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.text.TextUtils;
import android.widget.Toast;

import com.njkj.yulian.MainApplication;
import com.njkj.yulian.core.task.ActivityTaskManager;
import com.njkj.yulian.core.task.DownloadTask;
import com.njkj.yulian.dao.ConfigDao;
import com.njkj.yulian.ui.gui.CustomDialog;
import com.njkj.yulian.utils.FileUtil;

/**
 * 版本更新
 */
public class UpgradeHelper {

	private Dialog mNorDialog;
	private ProgressDialog mProgressDialog;
	private Context mAct;
	private ActivityTaskManager taskManager;

	/**
	 * 
	 */
	public UpgradeHelper(Context act) {
		taskManager = ActivityTaskManager.getInstance();
		this.mAct = act;
		if (mProgressDialog == null) {
			mProgressDialog = new ProgressDialog(mAct);
			mProgressDialog.setMessage("正在下载安装包...");
			mProgressDialog.setIndeterminate(true);
			mProgressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
			mProgressDialog.setCancelable(false);
		}
	}

	// 更新
	public void confNorUpdate(String tips, boolean needUpdate) {
		if (TextUtils.isEmpty(tips))
			tips = "发现新版本，请更新后使用";
		// 强制更新
		if (needUpdate) {
			if (mNorDialog == null) {
				mNorDialog = CustomDialog.createCommonCustomDialog(mAct, tips,
						"取消", "确定", needClickListener);
			}
		} else {
			if (mNorDialog == null) {
				mNorDialog = CustomDialog.createCommonCustomDialog(mAct, tips,
						"取消", "确定", mDlgClickListener);
			}
		}
		mNorDialog.setTitle(tips);
		mNorDialog.setCancelable(false);
		if (!((Activity) mAct).isFinishing())
			mNorDialog.show();
	}

	// 强制更新
	protected DialogInterface.OnClickListener needClickListener = new android.content.DialogInterface.OnClickListener() {
		@Override
		public void onClick(DialogInterface dialog, int which) {
			dialog.dismiss();
			if (dialog.equals(mNorDialog)) {
				switch (which) {
				// 退出
				case CustomDialog.LEFT_BUTTON_CLICK:
					try {
						taskManager.closeAllActivity();
					} catch (Exception e) {
						e.printStackTrace();
					}
					System.exit(0);
					break;
				case CustomDialog.RIGHT_BUTTON_CLICK:
					// 更新
					download();
					break;
				}
			}
		}
	};
	// 更新
	protected DialogInterface.OnClickListener mDlgClickListener = new android.content.DialogInterface.OnClickListener() {
		@Override
		public void onClick(DialogInterface dialog, int which) {
			dialog.dismiss();
			if (dialog.equals(mNorDialog)) {
				switch (which) {
				case CustomDialog.LEFT_BUTTON_CLICK:
					break;
				case CustomDialog.RIGHT_BUTTON_CLICK:
					download();
					break;
				}
			}
		}
	};

	// 下载
	private void download() {
		String str1 = ConfigDao.getInstance().getString("new_verison_url");
		String str2 = null;
		if ((!TextUtils.isEmpty(str1))
				&& (Environment.getExternalStorageState()
						.equals(Environment.MEDIA_MOUNTED)))
			str2 = FileUtil.getFileName(str1);
		System.out.println("str2:"+str2);
		try {
			File localFile = new File(
					Environment.getExternalStorageDirectory(), str2);
			if (localFile != null)
				download(str1, localFile.getAbsolutePath());
			return;
		} catch (Exception localException) {
			localException.printStackTrace();
		}
	}
	private void download(String url, final String path) {
		if (TextUtils.isEmpty(url) || TextUtils.isEmpty(path))
			return;

		File f = new File(path);
		if (f.exists()) {
			f.delete();
		}
		new DownloadTask(path, new DownloadTask.DownloadCallback() {

			@Override
			public void onStart() {
				mProgressDialog.show();
			}

			@Override
			public void onProgress(Integer... progress) {
				mProgressDialog.setIndeterminate(false);
				mProgressDialog.setMax(100);
				mProgressDialog.setProgress(progress[0]);
			}

			@Override
			public void onFinished(String result) {
				if (result != null) {
					Toast.makeText(MainApplication.getContext(),
							"下载异常: " + result, 0).show();

				} else {
					Toast.makeText(MainApplication.getContext(), "下载完成", 0)
							.show();
					installNormal(mAct, path);
					((Activity) mAct).finish();
				}
				mProgressDialog.dismiss();
			}

			@Override
			public void onCancelled() {
				mProgressDialog.dismiss();
			}
		}).execute(url);
	}

	public boolean installNormal(Context context, String filePath) {
		Intent i = new Intent(Intent.ACTION_VIEW);
		File file = new File(filePath);
		if (file == null || !file.exists() || !file.isFile()
				|| file.length() <= 0) {
			return false;
		}
		i.setDataAndType(Uri.parse("file://" + filePath),
				"application/vnd.android.package-archive");
		i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		context.startActivity(i);
		return true;
	}
}
