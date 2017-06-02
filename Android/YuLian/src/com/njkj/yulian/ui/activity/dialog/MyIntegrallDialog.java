package com.njkj.yulian.ui.activity.dialog;

import com.njkj.yulian.R;
import com.njkj.yulian.ui.activity.PayDetailActivity;
import com.njkj.yulian.ui.activity.StartActivity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MyIntegrallDialog extends Dialog implements
      android.view.View.OnClickListener {
	Context context;
	View view;
	Button myintegrall_disimiss,myintegrall_submit;//取消
	EditText myintegrall_edit;
	public MyIntegrallDialog(Context context, int theme) {
		super(context, theme);
		this.context = context;
	}
	 @Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		initView();
	}
	 private void initView() {
			view = LayoutInflater.from(context).inflate(
					R.layout.myintegralldialog, null);
			myintegrall_edit=(EditText) view.findViewById(R.id.myintegrall_edit);
			myintegrall_disimiss=(Button) view.findViewById(R.id.myintegrall_disimiss);
			myintegrall_submit=(Button) view.findViewById(R.id.myintegrall_submit);
			myintegrall_disimiss.setOnClickListener(this);
			myintegrall_submit.setOnClickListener(this);
			setContentView(view);
			setCanceledOnTouchOutside(false);// 点击dialog外面dialog不消失
	 }
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.myintegrall_submit:
			dismiss();
			Intent intent=new Intent(context,PayDetailActivity.class);
			intent.putExtra("number",myintegrall_edit.getText().toString().trim());
			context.startActivity(intent);
			break;
		case R.id.myintegrall_disimiss:
			dismiss();
			break;
		default:
			break;
		}
	}

}
