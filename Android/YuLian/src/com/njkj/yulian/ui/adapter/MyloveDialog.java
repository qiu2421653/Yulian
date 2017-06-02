package com.njkj.yulian.ui.adapter;

import com.njkj.yulian.R;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;

public class MyloveDialog  extends Dialog{
	private Context context;
	private int position;
	public MyloveDialog(Context context,int position,int theme) {
		super(context,theme);
		this.context=context;
		this.position=position;
	}
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mylovedialog);
	}

}
