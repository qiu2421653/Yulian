package com.njkj.yulian.ui.activity;

import java.util.ArrayList;
import java.util.List;

import com.njkj.yulian.R;
import com.njkj.yulian.entity.AddressEntity;
import com.njkj.yulian.ui.adapter.AddAddressAdapter;

import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class AddaddressActivity  extends Activity implements OnClickListener,OnItemClickListener{
	private ListView addaddress_listview;
	private List<AddressEntity> list;
	ImageView goodaddress_left;
	Button address_bottom;
	
	
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.add_address);
		addaddress_listview=(ListView) findViewById(R.id.addaddress_listview);
		goodaddress_left=(ImageView) findViewById(R.id.goodaddress_left);
		address_bottom=(Button) findViewById(R.id.address_bottom);
		address_bottom.setOnClickListener(this);
		goodaddress_left.setOnClickListener(this);
		addaddress_listview.setOnItemClickListener(this);
		list=new ArrayList<AddressEntity>();
		for(int i=0;i<2;i++){
			AddressEntity entity=new AddressEntity();
			if(i==0){
			entity.setAddress("辽宁省大连市沙河口区纳米大厦1101");
			entity.setName("张三");
			entity.setPhone("13354287777");
			}else{
				entity.setAddress("辽宁省大连市沙河口区弘基书香园1822");
				entity.setName("李四");
				entity.setPhone("13354286993");
			}
			list.add(entity);
		}
		addaddress_listview.setAdapter(new AddAddressAdapter(this,list));
	}
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.goodaddress_left:
			finish();
			break;
		case R.id.address_bottom:
			startActivity(new Intent(this,EditAddress.class));
			break;
		default:
			break;
		}
	}
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		Intent intents=new Intent(this,EditAddress.class);
		Bundle  bundle=new Bundle();
		bundle.putSerializable("address", list.get(position));
		intents.putExtras(bundle);
		startActivity(intents);
	}
}
