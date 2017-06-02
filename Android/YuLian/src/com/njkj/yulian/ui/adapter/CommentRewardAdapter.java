package com.njkj.yulian.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.text.Spannable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TextView.BufferType;

import com.njkj.yulian.MainApplication;
import com.njkj.yulian.R;
import com.njkj.yulian.dao.ConfigDao;
import com.njkj.yulian.entity.TopicDetailEntity;
import com.njkj.yulian.ui.activity.AddConcernActivity;
import com.njkj.yulian.ui.activity.ImagePagerActivity;
import com.njkj.yulian.ui.gui.CircleImageView;
import com.njkj.yulian.ui.gui.MultiImageView;
import com.njkj.yulian.ui.gui.MyListView;
import com.njkj.yulian.utils.SmileUtils;
import com.squareup.picasso.Picasso;

/**
 * ==============================
 * 
 * @author Qiu
 * 
 * @Package com.njkj.yulian.ui.adapter
 * 
 * @Description:主题详细+评论
 * 
 * @date 2016-3-28 上午9:39:16
 * 
 * @version 1.0 ==============================
 */
public class CommentRewardAdapter extends BaseAdapter {

	protected static final String TAG = "CommentAdapter";

	private LayoutInflater mInflater;
	private Context mContext;
	private TopicDetailEntity detailEntity;

	ViewTitleHolder viewTitleHolder;
	ViewItemHolder viewItemHolder;

	OnClickListener replyToCommentListener;
	OnClickListener replyToReplyListener;
	private static final int TYPE_ONE = 0, TYPE_TWO = 1, TYPE_COUNT = 2;

	public CommentRewardAdapter(Context ctx, TopicDetailEntity detailEntity,
			OnClickListener replyToCommentListener,
			OnClickListener replyToReplyListener) {
		this.mContext = ctx;
		this.detailEntity = detailEntity;
		this.replyToCommentListener = replyToCommentListener;
		this.replyToReplyListener = replyToReplyListener;
		mInflater = LayoutInflater.from(mContext);
	}

	@Override
	public int getCount() {
		if (detailEntity.comments != null)
			return detailEntity.comments.size() + 1;
		else
			return 1;
	}

	/** 该方法返回多少个不同的布局 */
	@Override
	public int getViewTypeCount() {
		return TYPE_COUNT;
	}

	/** 根据position返回相应的Item */
	@Override
	public int getItemViewType(int position) {
		if (position == 0)
			return TYPE_ONE;
		else
			return TYPE_TWO;
	}

	@Override
	public Object getItem(int position) {
		return null;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// 对应位置类型
		int type = getItemViewType(position);
		if (convertView == null) {
			switch (type) {
			case TYPE_ONE:
				// 加载view
				convertView = mInflater.inflate(R.layout.activity_topic_reward,
						null);
				viewTitleHolder = new ViewTitleHolder(convertView);
				convertView.setTag(viewTitleHolder);
				break;
			case TYPE_TWO:
				// 加载view
				convertView = mInflater.inflate(R.layout.layout_parent, null);
				viewItemHolder = new ViewItemHolder(convertView);
				convertView.setTag(viewItemHolder);
				break;
			}
		} else {
			switch (type) {
			case TYPE_ONE:
				viewTitleHolder = (ViewTitleHolder) convertView.getTag();
				break;
			case TYPE_TWO:
				viewItemHolder = (ViewItemHolder) convertView.getTag();
				break;
			}
		}
		switch (type) {
		case TYPE_ONE:
			// 上面的头部信息
			// 点赞人头像
			FavorAdapter adapter = new FavorAdapter(detailEntity.forks);
			viewTitleHolder.gridview.setAdapter(adapter);
			viewTitleHolder.gridview
					.setOnItemClickListener(new OnGridViewListener());
			viewTitleHolder.gridview.setVisibility(View.VISIBLE);

			if (!TextUtils.isEmpty(detailEntity.hPic)) {
				// TODO
				Picasso.with(mContext).load(detailEntity.hPic)
						.config(Bitmap.Config.RGB_565)
						.placeholder(R.drawable.empty_photo)
						.error(R.drawable.empty_photo)
						.into(viewTitleHolder.iv_icon);
			}
			viewTitleHolder.iv_icon.setTag(position);
			viewTitleHolder.iv_icon.setOnClickListener(new onMyClick());

			// 用户Id一样不显示
			if (ConfigDao.getInstance().getString("userID")
					.equals(detailEntity.userId))
				viewTitleHolder.btn_reward.setVisibility(View.GONE);
			else
				viewTitleHolder.btn_reward.setVisibility(View.VISIBLE);

			viewTitleHolder.tv_nickName.setText(detailEntity.nickName);
			viewTitleHolder.tv_createTime.setText(detailEntity.createTime);
			if (!TextUtils.isEmpty(detailEntity.tag))
				viewTitleHolder.tv_topic_tag.setText("#" + detailEntity.tag
						+ "#");

			// 设置内容
			Spannable span = SmileUtils.getSmiledText(mContext,
					detailEntity.content);

			viewTitleHolder.tv_createDescription.setText(span,
					BufferType.SPANNABLE);

			viewTitleHolder.tv_comnumber
					.setText(detailEntity.comments != null ? detailEntity.comments
							.size() + ""
							: String.valueOf(0));
			// 图片
			if (detailEntity.urlList != null && detailEntity.urlList.size() > 0) {
				viewTitleHolder.imgViewStub.setVisibility(View.VISIBLE);
				viewTitleHolder.imgViewStub.setList(detailEntity.urlList);
				viewTitleHolder.imgViewStub
						.setOnItemClickListener(new MultiImageView.OnItemClickListener() {
							@Override
							public void onItemClick(View view, int position) {
								// ImagePagerActivity.imageSize = new ImageSize(
								// view.getWidth(), view.getHeight());
								ImagePagerActivity.startImagePagerActivity(
										mContext, detailEntity.urlList,
										position);
							}
						});
			}
			// 打赏
			viewTitleHolder.btn_reward
					.setOnClickListener(new OnClickListener() {
						@Override
						public void onClick(View v) {
							onRewardCallBack.onReward();
						}
					});

			break;
		case TYPE_TWO:
			// 名
			viewItemHolder.tv_nickName.setText(detailEntity.comments
					.get(position - 1).nickName);
			// 设置时间标签
			viewItemHolder.tv_time.setText(detailEntity.comments
					.get(position - 1).timeLag);
			viewItemHolder.tv_createDesc.setText(detailEntity.comments
					.get(position - 1).comment);
			if (!TextUtils
					.isEmpty(detailEntity.comments.get(position - 1).hPic)) {
				Picasso.with(MainApplication.getContext())
						.load(detailEntity.comments.get(position - 1).hPic)
						.placeholder(R.drawable.empty_photo)
						.config(Bitmap.Config.RGB_565)
						.error(R.drawable.empty_photo)
						.into(viewItemHolder.iv_icon);
			} else {
				viewItemHolder.iv_icon.setImageResource(R.drawable.logo);
			}

			// 判断当前评论是否有回复
			if (detailEntity.comments.get(position - 1).replyList != null
					&& detailEntity.comments.get(position - 1).replyList.size() != 0) {
				viewItemHolder.lv_user_comment_replys
						.setAdapter(new CommentReplyAdapter(
								position - 1,
								detailEntity.comments.get(position - 1).replyList,
								replyToReplyListener));
				viewItemHolder.lv_user_comment_replys.setVisibility(View.VISIBLE);
			}else{
				viewItemHolder.lv_user_comment_replys.setVisibility(View.GONE);
			}
			viewItemHolder.rl_group.setTag(position - 1);
			viewItemHolder.rl_group.setOnClickListener(replyToCommentListener);// 点击事件
			break;
		}
		return convertView;
	}

	class ViewTitleHolder {
		GridView gridview;
		MultiImageView imgViewStub;
		CircleImageView iv_icon;
		TextView tv_nickName;
		TextView tv_createTime;
		TextView tv_createDescription;
		TextView tv_comnumber;
		TextView tv_topic_tag;
		Button btn_reward;

		public ViewTitleHolder(View view) {
			gridview = (GridView) view.findViewById(R.id.gridview);
			imgViewStub = (MultiImageView) view.findViewById(R.id.imgViewStub);
			iv_icon = (CircleImageView) view.findViewById(R.id.iv_icon);
			tv_nickName = (TextView) view.findViewById(R.id.tv_nickName);
			tv_createTime = (TextView) view.findViewById(R.id.tv_createTime);
			tv_createDescription = (TextView) view
					.findViewById(R.id.tv_createDescription);
			tv_comnumber = (TextView) view.findViewById(R.id.tv_comnumber);
			tv_topic_tag = (TextView) view.findViewById(R.id.tv_topic_tag);
			btn_reward = (Button) view.findViewById(R.id.btn_reward);
		}
	}

	class ViewItemHolder {
		CircleImageView iv_icon;
		RelativeLayout rl_group;
		TextView tv_nickName;
		TextView tv_time;
		TextView tv_createDesc;
		MyListView lv_user_comment_replys;

		public ViewItemHolder(View view) {
			iv_icon = (CircleImageView) view.findViewById(R.id.iv_icon);
			tv_nickName = (TextView) view.findViewById(R.id.tv_nickName);
			tv_time = (TextView) view.findViewById(R.id.tv_time);
			tv_createDesc = (TextView) view.findViewById(R.id.tv_createDesc);
			rl_group = (RelativeLayout) view.findViewById(R.id.rl_group);
			lv_user_comment_replys = (MyListView) view
					.findViewById(R.id.lv_user_comment_replys);
		}
	}

	// 跳转到关注页面
	class OnGridViewListener implements OnItemClickListener {
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			Intent intent = new Intent(mContext, AddConcernActivity.class);
			intent.putExtra("infoId", detailEntity.infoId);
			intent.putExtra("userId", detailEntity.userId);
			intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			mContext.startActivity(intent);
		}
	}

	class onMyClick implements OnClickListener {

		@Override
		public void onClick(View v) {
			int position = (Integer) v.getTag();
			mCallBack.onHpicClick(position);
		}

	}

	/**
	 * 打赏
	 * */
	public interface OnRewardCallBack {
		void onReward();
	}

	public OnRewardCallBack onRewardCallBack;

	public void setOnRewardCallBack(OnRewardCallBack onRewardCallBack) {
		this.onRewardCallBack = onRewardCallBack;
	}

	/**
	 * 点击头像
	 */
	public interface onHpicCallBack {

		void onHpicClick(int position);
	}

	public onHpicCallBack mCallBack;

	public void setmCallBack(onHpicCallBack mCallBack) {
		this.mCallBack = mCallBack;
	}

}
