package com.njkj.yulian.ui.activity.upload;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.njkj.yulian.R;
import com.njkj.yulian.constant.ReqCode;
import com.njkj.yulian.controller.TagController;
import com.njkj.yulian.controller.TopicController;
import com.njkj.yulian.core.callback.SimpleCallback;
import com.njkj.yulian.entity.RetEntity;
import com.njkj.yulian.entity.TagEntity;
import com.njkj.yulian.ui.activity.BaseActivity;
import com.njkj.yulian.ui.adapter.AddTagsAdapter;

/**
 * ==============================
 * 
 * @author Qiu
 * 
 * @Description: 添加标签
 * 
 * @date 下午7:09:36
 * 
 * @version V1.0 ==============================
 * 
 */
public class AddTagActivity extends BaseActivity implements OnItemClickListener {

	TagController tagController;
	ListView lv_tags;
	AddTagsAdapter adapter;
	ArrayList<TagEntity> tagDTO;

	int lastPostion = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_tag);
		setHeaderTitle(R.string.add_tag);
		// setHeaderRightText(R.string._createTag);
		setHeaderLeftText();
		setHeaderRightText(getString(R.string.record_camera_cancel_dialog_yes));
		initViews();
		initData();
		initOnClick();
	}

	private void initViews() {
		lv_tags = (ListView) findViewById(R.id.lv_tags);

	}

	private void initData() {
		tagController = new TagController();
		getUserTags();

	}

	private void initOnClick() {
		lv_tags.setOnItemClickListener(this);
		// findViewById(R.id.title_left1).setOnClickListener(this);
		findViewById(R.id.title_right_text).setOnClickListener(this);
	}

	@Override
	public void onClick(View view) {
		super.onClick(view);
		switch (view.getId()) {
		case R.id.title_left1:
			setResult(Activity.RESULT_CANCELED);
			finish();
			break;
		case R.id.title_right_text:
			Intent intent = new Intent();
			intent.putExtra("tag", tagDTO.get(lastPostion));
			setResult(ReqCode.REQ_ADDTAG, intent);
			finish();
			break;
		}
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View view, int position,
			long arg3) {
		tagDTO.get(lastPostion).isSelected = false;
		tagDTO.get(position).isSelected = true;
		adapter.notifyDataSetChanged();
		lastPostion = position;
	}

	/**
	 * 
	 * @Title: getUserTags
	 * @Description: 获取可用标签
	 * @param userId
	 * @return void
	 * @throws
	 */
	private void getUserTags() {
		String userID = mConfigDao.getString("userID");
		showDialog();
		tagController.getUsedTags(getString(R.string.FsGetUsedTags), userID,
				new SimpleCallback() {
					@Override
					public void onCallback(Object data) {
						if (data == null) {
							showShortToast(getString(R.string.error));
						} else {
							RetEntity<TagEntity> entity = (RetEntity<TagEntity>) data;
							if (entity.result.tagDTO != null) {
								setMessage(entity.result.tagDTO);
							}
						}
						hideProgress();
					}
				});
	}

	/**
	 * 设置信息
	 * 
	 * @param tagDTO
	 */
	private void setMessage(ArrayList<TagEntity> tagDTO) {
		if (tagDTO == null) {
			return;
		}
		if (tagDTO.isEmpty()) {
			return;
		}
		if (this.tagDTO == null) {
			this.tagDTO = tagDTO;
			tagDTO.get(0).isSelected = true;
		} else
			this.tagDTO.addAll(tagDTO);

		if (adapter == null) {
			adapter = new AddTagsAdapter(this.tagDTO);
			lv_tags.setAdapter(adapter);
		} else {
			adapter.notifyDataSetChanged();
		}
	}

	@Override
	protected void onActivityResult(int request, int result, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(request, result, data);
		if (result == Activity.RESULT_CANCELED) {
			return;
		}
		if (result == ReqCode.REQ_NEWTAG) {
			TagEntity tag = (TagEntity) data.getSerializableExtra("tag");
			tagDTO.get(lastPostion).isSelected = false;

			tagDTO.add(tag);
			lastPostion = tagDTO.size() - 1;
			tagDTO.get(lastPostion).isSelected = true;
			adapter.notifyDataSetChanged();
		}
	}
}
