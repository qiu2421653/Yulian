package com.njkj.yulian.ui.activity.dialog;

import com.njkj.yulian.R;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class MyPlayDialog extends Dialog implements
		android.view.View.OnClickListener {
	Context context;
	private EditText play_password;
	Button play_disimiss, play_submit;// 取消 支付
	ImageView play_num1, play_num2, play_num3, play_num4, play_num5, play_num6;
	View view;

	public MyPlayDialog(Context context, int theme) {
		super(context, theme);
		this.context = context;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		initView();
		dismiss();
	}

	private void initView() {
		view = LayoutInflater.from(context).inflate(
				R.layout.address_play_dialog, null);
		play_num1 = (ImageView) view.findViewById(R.id.play_num1);
		play_num2 = (ImageView) view.findViewById(R.id.play_num2);
		play_num3 = (ImageView) view.findViewById(R.id.play_num3);
		play_num4 = (ImageView) view.findViewById(R.id.play_num4);
		play_num5 = (ImageView) view.findViewById(R.id.play_num5);
		play_num6 = (ImageView) view.findViewById(R.id.play_num6);
		play_disimiss = (Button) view.findViewById(R.id.play_disimiss);
		play_submit = (Button) view.findViewById(R.id.play_submit);
		play_submit.setOnClickListener(this);
		play_disimiss.setOnClickListener(this);
		play_password = (EditText) view.findViewById(R.id.play_password);
		play_password.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				if (s.length() == 0) {
					play_num1.setImageResource(R.color.white);
					play_num2.setImageResource(R.color.white);
					play_num3.setImageResource(R.color.white);
					play_num4.setImageResource(R.color.white);
					play_num5.setImageResource(R.color.white);
					play_num6.setImageResource(R.color.white);

				} else if (s.length() == 1) {
					play_num1.setImageResource(R.drawable.play_bgblack);
					play_num2.setImageResource(R.color.white);
					play_num3.setImageResource(R.color.white);
					play_num4.setImageResource(R.color.white);
					play_num5.setImageResource(R.color.white);
					play_num6.setImageResource(R.color.white);

				} else if (s.length() == 2) {
					play_num1.setImageResource(R.drawable.play_bgblack);
					play_num2.setImageResource(R.drawable.play_bgblack);
					play_num3.setImageResource(R.color.white);
					play_num4.setImageResource(R.color.white);
					play_num5.setImageResource(R.color.white);
					play_num6.setImageResource(R.color.white);

				} else if (s.length() == 3) {
					play_num1.setImageResource(R.drawable.play_bgblack);
					play_num2.setImageResource(R.drawable.play_bgblack);
					play_num3.setImageResource(R.drawable.play_bgblack);
					play_num4.setImageResource(R.color.white);
					play_num5.setImageResource(R.color.white);
					play_num6.setImageResource(R.color.white);
				} else if (s.length() == 4) {
					play_num1.setImageResource(R.drawable.play_bgblack);
					play_num2.setImageResource(R.drawable.play_bgblack);
					play_num3.setImageResource(R.drawable.play_bgblack);
					play_num4.setImageResource(R.drawable.play_bgblack);
					play_num5.setImageResource(R.color.white);
					play_num6.setImageResource(R.color.white);
				} else if (s.length() == 5) {
					play_num1.setImageResource(R.drawable.play_bgblack);
					play_num2.setImageResource(R.drawable.play_bgblack);
					play_num3.setImageResource(R.drawable.play_bgblack);
					play_num4.setImageResource(R.drawable.play_bgblack);
					play_num5.setImageResource(R.drawable.play_bgblack);
					play_num6.setImageResource(R.color.white);
				} else if (s.length() == 6) {
					play_num1.setImageResource(R.drawable.play_bgblack);
					play_num2.setImageResource(R.drawable.play_bgblack);
					play_num3.setImageResource(R.drawable.play_bgblack);
					play_num4.setImageResource(R.drawable.play_bgblack);
					play_num5.setImageResource(R.drawable.play_bgblack);
					play_num6.setImageResource(R.drawable.play_bgblack);
				}
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
			}

			@Override
			public void afterTextChanged(Editable s) {
			}
		});
		setContentView(view);
		setCanceledOnTouchOutside(false);// 点击dialog外面dialog不消失
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.play_disimiss:// 取消
			dismiss();
			break;
		case R.id.play_submit:// 支付
			Toast.makeText(context, "支付成功", Toast.LENGTH_SHORT).show();
			dismiss();
			break;
		default:
			break;
		}
	}

}
