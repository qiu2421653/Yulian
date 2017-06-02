package com.njkj.yulian.ui.activity;


import com.njkj.yulian.R;
import com.njkj.yulian.entity.AddressEntity;
import com.njkj.yulian.ui.gui.wheel.OnWheelChangedListener;
import com.njkj.yulian.ui.gui.wheel.WheelView;
import com.njkj.yulian.ui.gui.wheel.adapters.ArrayWheelAdapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

public class EditAddress extends BaseActivity implements OnClickListener{
	View view;
	TextView editaddress;//选择省市区
	AddressPopUpWindow popwindow;
	AddressEntity address;
	EditText editaddress_name,editaddress_phone;//姓名
	LinearLayout delete_address;//删除地址
	Button moren_address;//设置默认地址
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_editaddress);
		initView();
		Intent intent=getIntent();
			address=(AddressEntity) intent.getSerializableExtra("address");
			if(address!=null){
			setHeaderTitle("修改收货地址");
			editaddress.setText(address.getAddress());
			editaddress_name.setText(address.getName());
			editaddress_phone.setText(address.getPhone());
			delete_address.setVisibility(View.VISIBLE);
			moren_address.setVisibility(View.VISIBLE);
			setHeaderRightText("编辑");
		}else{
			setHeaderTitle("新建收货地址");
			delete_address.setVisibility(View.GONE);
			moren_address.setVisibility(View.GONE);
			setHeaderRightText("保存");
		}
		setHeaderLeftText();
		setHeaderBackground1(R.color.white);
	}
	private void initView() {
		editaddress_name=(EditText) findViewById(R.id.editaddress_name);
		editaddress_phone=(EditText) findViewById(R.id.editaddress_phone);
		editaddress=(TextView) findViewById(R.id.editaddress);
		delete_address=(LinearLayout) findViewById(R.id.delete_address);
		moren_address=(Button) findViewById(R.id.moren_address);
		editaddress.setOnClickListener(this);
		delete_address.setOnClickListener(this);
		moren_address.setOnClickListener(this);
		
	}
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.title_left1:
			finish();
			break;
		case R.id.title_right_text:
			Toast.makeText(this, "添加地址成功", Toast.LENGTH_SHORT).show();
			finish();
			break;
		case R.id.editaddress:
			popwindow=new AddressPopUpWindow(this,editaddress);
			break;
		case R.id.personaladdress_submit://确定
			editaddress.setText(mCurrentProviceName+mCurrentCityName+mCurrentDistrictName);
			popwindow.dismiss();
			break;
		case R.id.personaladdress_qx://取消
			popwindow.dismiss();
			break;
		case R.id.delete_address://删除地址
			Toast.makeText(this, "删除地址成功", Toast.LENGTH_SHORT).show();
			finish();
			break;
		case R.id.moren_address:
			finish();
			Toast.makeText(this, "设置默认地址成功", Toast.LENGTH_SHORT).show();
			break;
		default:
			break;
		}
	}
	
	public class AddressPopUpWindow extends PopupWindow implements OnWheelChangedListener{
		private WheelView mViewProvince;
		private WheelView mViewCity;
		private WheelView mViewDistrict;
		public AddressPopUpWindow(Context context, View parent){
			view = View
					.inflate(context, R.layout.personal_address, null);
			view.startAnimation(AnimationUtils.loadAnimation(context,
					R.anim.fade_ins));
			view.findViewById(R.id.personaladdress_submit).setOnClickListener(EditAddress.this);
			view.findViewById(R.id.personaladdress_qx).setOnClickListener(EditAddress.this);
			setWidth(LayoutParams.FILL_PARENT);
			setHeight(LayoutParams.FILL_PARENT);
			setBackgroundDrawable(new BitmapDrawable());
			setFocusable(true);
			setOutsideTouchable(true);
			setContentView(view);
			showAtLocation(parent, Gravity.BOTTOM, 0, 0);
			update();
			setUpViews();
			setUpListener();
			setUpData();
		}
		
		private void setUpViews() {
			mViewProvince = (WheelView)view.findViewById(R.id.id_province);
			mViewCity = (WheelView)view.findViewById(R.id.id_city);
			mViewDistrict = (WheelView)view.findViewById(R.id.id_district);
		}
		
		private void setUpListener() {
	    	// 添加change事件
	    	mViewProvince.addChangingListener(this);
	    	// 添加change事件
	    	mViewCity.addChangingListener(this);
	    	// 添加change事件
	    	mViewDistrict.addChangingListener(this);
	    }
		
		private void setUpData() {
			initProvinceDatas();
			mViewProvince.setViewAdapter(new ArrayWheelAdapter<String>(EditAddress.this, mProvinceDatas));
			// 设置可见条目数量
			mViewProvince.setVisibleItems(5);
			mViewCity.setVisibleItems(5);
			mViewDistrict.setVisibleItems(5);
			updateCities();
			updateAreas();
		}

		@Override
		public void onChanged(WheelView wheel, int oldValue, int newValue) {
			// TODO Auto-generated method stub
			if (wheel == mViewProvince) {
				updateCities();
			} else if (wheel == mViewCity) {
				updateAreas();
			} else if (wheel == mViewDistrict) {
				mCurrentDistrictName = (mDistrictDatasMap.get(mCurrentCityName)[newValue]);
				mCurrentZipCode = (mZipcodeDatasMap.get(mCurrentDistrictName));
			}
		}

		/**
		 * 根据当前的市，更新区WheelView的信息
		 */
		private void updateAreas() {
			int pCurrent = mViewCity.getCurrentItem();
			mCurrentCityName = mCitisDatasMap.get(mCurrentProviceName)[pCurrent];
			String[] areas = mDistrictDatasMap.get(mCurrentCityName);

			if (areas == null) {
				areas = new String[] { "" };
			}
			mViewDistrict.setViewAdapter(new ArrayWheelAdapter<String>(EditAddress.this, areas));
			mViewDistrict.setCurrentItem(0);
		}

		/**
		 * 根据当前的省，更新市WheelView的信息
		 */
		private void updateCities() {
			int pCurrent = mViewProvince.getCurrentItem();
			mCurrentProviceName = mProvinceDatas[pCurrent];
			String[] cities = mCitisDatasMap.get(mCurrentProviceName);
			if (cities == null) {
				cities = new String[] { "" };
			}
			mViewCity.setViewAdapter(new ArrayWheelAdapter<String>(EditAddress.this, cities));
			mViewCity.setCurrentItem(0);
			updateAreas();
		}
	}

}
