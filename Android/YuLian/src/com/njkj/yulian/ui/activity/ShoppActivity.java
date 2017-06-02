package com.njkj.yulian.ui.activity;

import com.njkj.yulian.R;
import com.njkj.yulian.ui.fragment.MallFragment;
import com.njkj.yulian.ui.fragment.OrderFragment;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;

public class ShoppActivity extends BaseActivity{
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_shopp);
		setHeaderLeftText();
		setHeaderBackground1(R.color.white);
		setHeaderTitle("我的订单");
		OrderFragment fragment=new OrderFragment();
		FragmentTransaction ft = this.getSupportFragmentManager()
				.beginTransaction();
		ft.add(R.id.shopp_fragment, fragment);
		ft.commit();
	}
}
