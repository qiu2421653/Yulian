package com.njkj.yulian.ui.activity.dialog;

import com.njkj.yulian.R;
import com.njkj.yulian.utils.Isdialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class CaptionDialog extends Dialog implements android.view.View.OnClickListener{
	private Button caption_submit;
	Isdialog isdialog;
	EditText edittext;
	public CaptionDialog(Context context,int theme,Isdialog isdialog) {
		super(context,theme);
		this.isdialog=isdialog;
	}
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_caption);
		caption_submit=(Button) findViewById(R.id.caption_submit);
		edittext=(EditText) findViewById(R.id.caption_edittext);
		caption_submit.setOnClickListener(this);
	}
	@Override
	public void onClick(View v) {
		isdialog.result(true,edittext.getText().toString());
		dismiss();
	}
}
