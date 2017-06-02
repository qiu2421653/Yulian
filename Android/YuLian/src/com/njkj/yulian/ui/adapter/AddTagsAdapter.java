package com.njkj.yulian.ui.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Bitmap;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.njkj.yulian.MainApplication;
import com.njkj.yulian.R;
import com.njkj.yulian.entity.CommentEntity;
import com.njkj.yulian.entity.TagEntity;
import com.njkj.yulian.ui.adapter.HomeAdapter.OnForkCallBack;
import com.njkj.yulian.ui.gui.CircleImageView;
import com.squareup.picasso.Picasso;

/**
 * ==============================
 * 
 * @author Qiu
 * 
 * @Description: 添加标签
 * 
 * @date 下午7:09:24
 * 
 * @version V1.0 ==============================
 * 
 */
public class AddTagsAdapter extends BaseAdapter {

	protected static final String TAG = "AddTagsAdapter";

	private ArrayList<TagEntity> tagDTO;
	private LayoutInflater mInflater;
	private Context mContext;

	public AddTagsAdapter(ArrayList<TagEntity> tagDTO) {
		this.mContext = MainApplication.getContext();
		this.tagDTO = tagDTO;
		mInflater = LayoutInflater.from(mContext);
	}

	@Override
	public int getCount() {
		return (tagDTO == null ? 0 : tagDTO.size());
	}

	@Override
	public Object getItem(int position) {
		return tagDTO.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder viewHolder;
		if (convertView == null) {
			// 找到布局
			convertView = mInflater.inflate(R.layout.item_addtag, null);
			viewHolder = new ViewHolder(convertView);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		viewHolder.tv_title.setText(tagDTO.get(position).tag);
		viewHolder.img_selector.setSelected(tagDTO.get(position).isSelected);
		return convertView;
	}

	class ViewHolder {
		TextView tv_title;
		ImageView img_selector;

		public ViewHolder(View view) {
			tv_title = (TextView) view.findViewById(R.id.tv_title);
			img_selector = (ImageView) view.findViewById(R.id.img_selector);
		}
	}

}
