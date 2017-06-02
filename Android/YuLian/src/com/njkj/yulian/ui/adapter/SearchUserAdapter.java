package com.njkj.yulian.ui.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.njkj.yulian.R;
import com.njkj.yulian.constant.DataContant;
import com.njkj.yulian.dao.ConfigDao;
import com.njkj.yulian.entity.SearchUserEntity;
import com.njkj.yulian.ui.adapter.HomeAdapter.OnForkCallBack;
import com.njkj.yulian.ui.gui.CircleImageView;
import com.njkj.yulian.ui.gui.MultiImageView;
import com.njkj.yulian.ui.gui.MultiImageView.OnItemClickListener;
import com.squareup.picasso.Picasso;

/**
 * ==============================
 * 
 * @author Qiu
 * 
 * @Package com.njkj.yulian.ui.adapter
 * 
 * @Description:搜索 (用户)
 * 
 * @date 2016-4-5 上午11:24:59
 * 
 * @version 1.0 ==============================
 */
public class SearchUserAdapter extends BaseAdapter {

	protected static final String TAG = "SearchUserAdapter";

	private ArrayList<SearchUserEntity> recommend;
	private LayoutInflater mInflater;
	private Context mContext;
	ViewHistoryHolder viewHolder;
	boolean isRecommend = true;

	public interface OnForkUserCallBack {
		void onFork(int position);
	}

	public OnForkUserCallBack onForkCallBack;

	public void setOnForkCallBack(OnForkUserCallBack onForkCallBack) {
		this.onForkCallBack = onForkCallBack;
	}

	public SearchUserAdapter(Context ctx, ArrayList<SearchUserEntity> recommend) {
		this.mContext = ctx;
		this.recommend = recommend;
		this.mInflater = LayoutInflater.from(mContext);
	}

	@Override
	public int getCount() {
		return recommend.size();

	}

	@Override
	public Object getItem(int position) {
		return recommend.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	public void setState(boolean isRecommend) {
		this.isRecommend = isRecommend;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			// 找到布局
			convertView = mInflater.inflate(R.layout.item_search_user, null);
			viewHolder = new ViewHistoryHolder(convertView);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHistoryHolder) convertView.getTag();
		}
		// 推荐用户
		if (recommend.size() > 0) {
			if (isRecommend) {
				// 提示头
				if (position == 0) {
					// 头显示
					viewHolder.rl_head.setVisibility(View.VISIBLE);
					// 改变头文字
					viewHolder.tv_show.setText(R.string._search_recommend);
				} else {
					// 头隐藏
					viewHolder.rl_head.setVisibility(View.GONE);
				}
				if (recommend.get(position).isFork.equals("1")) {
					// 已关注
					viewHolder.iv_fork.setVisibility(View.GONE);
				}else{
					String userId=ConfigDao.getInstance().getString("userID");
					if(recommend.get(position).userID.equals(userId)){
						//用户自己
						viewHolder.iv_fork.setVisibility(View.GONE);
					}
				}
				viewHolder.tv_nickName
						.setText(recommend.get(position).nickName);
				if (!TextUtils.isEmpty(recommend.get(position).hPic)) {
					Picasso.with(mContext).load(recommend.get(position).hPic)
							.placeholder(R.drawable.empty_photo)
							.config(Bitmap.Config.RGB_565)
							.error(R.drawable.empty_photo)
							.into(viewHolder.avatar);
				} else {
					viewHolder.avatar.setImageResource(R.drawable.logo);
				}
			} else {
				// 搜索的用户
				viewHolder.rl_head.setVisibility(View.GONE);
			}
		}
		// }
		viewHolder.iv_fork.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				view.setVisibility(View.GONE);
				onForkCallBack.onFork(position);
			}
		});
		return convertView;
	}

	class ViewHistoryHolder {
		TextView tv_nickName;
		TextView tv_show;
		CircleImageView avatar;
		ImageView iv_fork;
		ImageView gray_line;
		RelativeLayout rl_head;

		// MultiImageView mv_image;

		public ViewHistoryHolder(View view) {
			tv_nickName = (TextView) view.findViewById(R.id.tv_nickName);
			tv_show = (TextView) view.findViewById(R.id.tv_show);
			avatar = (CircleImageView) view.findViewById(R.id.avatar);
			iv_fork = (ImageView) view.findViewById(R.id.iv_fork);
			gray_line = (ImageView) view.findViewById(R.id.gray_line);
			rl_head = (RelativeLayout) view.findViewById(R.id.rl_head);
			// mv_image = (MultiImageView) view.findViewById(R.id.mv_image);
		}
	}

}
