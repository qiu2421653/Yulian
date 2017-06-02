package com.njkj.yulian.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import com.njkj.yulian.R;
import com.njkj.yulian.controller.UserController;
import com.njkj.yulian.core.callback.SimpleCallback;
import com.njkj.yulian.entity.RetEntity;
import com.njkj.yulian.entity.UserEntity;

public class YJFKActivity extends BaseActivity implements OnClickListener {
	private EditText yjfk_edit;
	private Button submit;
	private UserController userController;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_yjfk);
		setHeaderTitle(R.string.yjfk);
		setHeaderLeftText();
		initView();
	}

	private void initView() {
		yjfk_edit = (EditText) findViewById(R.id.yjfk_edit);
		submit = (Button) findViewById(R.id.yjfk_submit);
		submit.setOnClickListener(this);
		userController = new UserController();
	}

	@Override
	public void onClick(View v) {
		super.onClick(v);
		switch (v.getId()) {
		case R.id.yjfk_submit:
			if (!"".equals(yjfk_edit.getText().toString())) {
				addFeedback(yjfk_edit.getText().toString());
			} else
				showShortToast("请填写内容后提交");
			break;

		default:
			break;
		}
	}

	// 意见反馈
	private void addFeedback(String feedback) {
		String userID = mConfigDao.getString("userID");
		showDialog();
		userController.addFeedback(getString(R.string.FsAddFeedBack), userID,
				feedback, new SimpleCallback() {
					@Override
					public void onCallback(Object data) {
						if (data == null) {
							showShortToast(getString(R.string.error));
						} else {
							RetEntity<UserEntity> entity = (RetEntity<UserEntity>) data;
							if (entity.success) {
								showShortToast("提交成功!");
								hideProgress();
								finish();
							} else {
								showShortToast(entity.exceptions.get(0).message);
							}
						}
						hideProgress();
					}
				});
	}
}
