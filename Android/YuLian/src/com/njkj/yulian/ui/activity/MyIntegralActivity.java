package com.njkj.yulian.ui.activity;

import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.njkj.yulian.R;
import com.njkj.yulian.controller.GoldController;
import com.njkj.yulian.core.callback.SimpleCallback;
import com.njkj.yulian.entity.GoldEntity;
import com.njkj.yulian.entity.RetEntity;

/***
 * 我的积分
 * 
 * @author fx
 * 
 */
public class MyIntegralActivity extends BaseActivity implements OnClickListener {
	private LinearLayout myintegral_sc, myintegral_top, myintegral_personal;// 商城,积分排行,完善个人信息
	private TextView myintegral_gz;// 积分规则
	TextView myintegral_play;// 去充值
	GoldController goldController;
	TextView myintegral_yb, myintegral_jf, myintegral_dz, myintegral_pl;// 禹币，积分,点赞,评论

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.myintegral);
		setHeaderTitle("禹币积分");
		setHeaderLeftText();
		setHeaderRightText("明细");
		initView();
		goldController = new GoldController();
		getGold();
	}

	private void initView() {
		myintegral_yb = (TextView) findViewById(R.id.myintegral_yb);
		myintegral_jf = (TextView) findViewById(R.id.myintegral_jf);
		myintegral_dz = (TextView) findViewById(R.id.myintegral_dz);
		myintegral_pl = (TextView) findViewById(R.id.myintegral_pl);
		myintegral_sc = (LinearLayout) findViewById(R.id.myintegral_sc);
		myintegral_top = (LinearLayout) findViewById(R.id.myintegral_top);
		myintegral_play = (TextView) findViewById(R.id.myintegral_play);
		myintegral_play.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
		myintegral_personal = (LinearLayout) findViewById(R.id.myintegral_personal);
		myintegral_gz = (TextView) findViewById(R.id.myintegral_gz);
		myintegral_sc.setOnClickListener(this);
		myintegral_top.setOnClickListener(this);
		myintegral_personal.setOnClickListener(this);
		myintegral_gz.setOnClickListener(this);
		myintegral_play.setOnClickListener(this);

	}

	@Override
	public void onClick(View v) {
		super.onClick(v);
		switch (v.getId()) {
		case R.id.myintegral_sc:
			showShortToast("等级不够,努力升级哟");
			// startAnimActivity(ShoppActivity.class);
			break;
		case R.id.myintegral_top:
			if (checkLogin()) {
				startAnimActivity(MyIntegralTopActivity.class);
			}
			break;
		case R.id.myintegral_personal:
			if (checkLogin()) {
				startAnimActivity(PersonalActivity.class);
			}
			break;
		case R.id.myintegral_gz:
			startAnimActivity(RuleActivity.class);
			break;
		case R.id.title_right_text:// 明细页面
			if (checkLogin()) {
				startAnimActivity(MyIntegralDetail.class);
			}
			break;
		case R.id.myintegral_play:
			startAnimActivity(MyIntegralPay.class);
			break;
		default:
			break;
		}
	}

	// 获得禹币积分
	private void getGold() {
		String userID = mConfigDao.getString("userID");
		showDialog();
		goldController.reqGetGold(getString(R.string.FsGetGold), userID,
				new SimpleCallback() {
					@Override
					public void onCallback(Object data) {
						if (data == null) {
							showShortToast(getString(R.string.error));
						} else {
							RetEntity<GoldEntity> entity = (RetEntity<GoldEntity>) data;
							if (entity.success == true) {
								// TODO
								GoldEntity result = entity.result;
								myintegral_yb.setText(result.currency);
								myintegral_jf.setText(result.score);
								myintegral_pl.setText(result.totalReply);
								myintegral_dz.setText(result.totalPraise);
								if (result.isAll.equals("1")) {
									myintegral_personal
											.setVisibility(View.GONE);
								}
							} else {
								showShortToast(entity.exceptions.get(0).message);
							}
						}
						hideProgress();
					}
				});
	}
}
