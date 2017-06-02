package com.njkj.yulian.ui.activity;

import java.util.ArrayList;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;

import com.njkj.yulian.MainApplication;
import com.njkj.yulian.R;
import com.njkj.yulian.controller.UserController;
import com.njkj.yulian.core.callback.SimpleCallback;
import com.njkj.yulian.dao.ConfigDao;
import com.njkj.yulian.entity.ReportEntity;
import com.njkj.yulian.entity.RetEntity;
import com.njkj.yulian.entity.UserEntity;
import com.njkj.yulian.ui.adapter.ReportAdapter;

/**
 * ==============================
 * 
 * @author Qiu
 * 
 * @Package com.njkj.yulian
 * 
 * @Description:举报
 * 
 * @date 2016-5-12 下午3:57:40
 * 
 * @version 1.0 ==============================
 */
public class ReportActivity extends Activity implements OnItemClickListener {
	ListView swipe_target;
	ReportAdapter adapter;
	UserController userController;
	String topicId;
	ArrayList<ReportEntity> reportList;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_report);
		initViews();
		initData();
	}

	private void initViews() {
		swipe_target = (ListView) findViewById(R.id.swipe_target);
	}

	private void initData() {
		// TODO
		userController = new UserController();
		topicId = getIntent().getStringExtra("topicId");
		swipe_target.setOnItemClickListener(this);
		getReportList();
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		reqReportTopic(reportList.get(position).type);
	}

	/** 获得举报内容 */
	private void getReportList() {
		showProgress(null, getString(R.string.loading), -1);

		userController.getReportList(getString(R.string.FsEnumInfo),
				new SimpleCallback() {
					@Override
					public void onCallback(Object data) {
						if (data == null) {
							Toast.makeText(MainApplication.getContext(),
									getString(R.string.error), 0).show();
						} else {
							RetEntity<ReportEntity> entity = (RetEntity<ReportEntity>) data;
							if (entity.success) {
								setMessage(entity.result.fsGetEnumInfoVo);
							} else {
								Toast.makeText(MainApplication.getContext(),
										entity.exceptions.get(0).message, 0)
										.show();
								finish();
							}
						}
						hideProgress();
					}

				});
	}

	private void setMessage(ArrayList<ReportEntity> list) {
		if (reportList == null) {
			reportList = list;
		}
		adapter = new ReportAdapter(MainApplication.getContext(), list);
		swipe_target.setAdapter(adapter);

	}

	/** 举报帖子 */
	private void reqReportTopic(String type) {
		String userID = ConfigDao.getInstance().getString("userID");
		showProgress(null, getString(R.string.loading), -1);

		userController.reqReportTopic(getString(R.string.FsReportTopic),
				userID, topicId, type, new SimpleCallback() {
					@Override
					public void onCallback(Object data) {
						if (data == null) {
							Toast.makeText(MainApplication.getContext(),
									getString(R.string.error), 0).show();
						} else {
							RetEntity<UserEntity> entity = (RetEntity<UserEntity>) data;
							if (entity.success) {
								Toast.makeText(MainApplication.getContext(),
										"举报成功!", 0).show();
								finish();
							} else {
								Toast.makeText(MainApplication.getContext(),
										entity.exceptions.get(0).message, 0)
										.show();
							}
						}
						hideProgress();
					}
				});
	}

	protected ProgressDialog mProgressDialog;

	public ProgressDialog showProgress(String title, String message, int theme) {
		if (mProgressDialog == null) {
			if (theme > 0)
				mProgressDialog = new ProgressDialog(this, theme);
			else
				mProgressDialog = new ProgressDialog(this);
			mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
			mProgressDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
			mProgressDialog.setCanceledOnTouchOutside(false);// 不能取消
			mProgressDialog.setIndeterminate(true);// 设置进度条是否不明确
		}

		if (!TextUtils.isEmpty(title))
			mProgressDialog.setTitle(title);
		mProgressDialog.setMessage(message);
		mProgressDialog.show();
		return mProgressDialog;
	}

	public void hideProgress() {
		if (mProgressDialog != null) {
			mProgressDialog.dismiss();
		}
	}
}
