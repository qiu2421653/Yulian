package com.njkj.yulian.ui.gui.advert;

import java.util.List;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.njkj.yulian.MainApplication;
import com.njkj.yulian.R;
import com.njkj.yulian.entity.AdvertEntity;
import com.njkj.yulian.ui.activity.topic.TopicRecommenActivity;
import com.njkj.yulian.ui.gui.advert.salvage.RecyclingPagerAdapter;
import com.njkj.yulian.utils.DevUtils;
import com.squareup.picasso.Picasso;

/**
 * Created by Sai on 15/7/29.
 */
public class CBPageAdapter<T> extends RecyclingPagerAdapter {
	protected static final String TAG = "CBPageAdapter";

	protected List<AdvertEntity> mDatas;
	protected CBViewHolderCreator holderCreator;
	private LayoutInflater mInflater;

	public CBPageAdapter(CBViewHolderCreator holderCreator,
			List<AdvertEntity> datas) {
		this.holderCreator = holderCreator;
		this.mDatas = datas;
		mInflater = LayoutInflater.from(MainApplication.getContext());
	}

	@Override
	public View getView(int position, View view, ViewGroup container) {
		ViewHolder viewHolder = null;
		if (view == null) {
			// 找到布局
			view = mInflater.inflate(R.layout.activity_advert_item, null);
			viewHolder = new ViewHolder(view);
			view.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) view.getTag();
		}
		viewHolder.iv_thumb.setTag(position);

		Picasso.with(MainApplication.getContext())
				.load((String) mDatas.get(position).url)
				.error(R.drawable.empty_photo)
				.resize(DevUtils.dip2px(MainApplication.getContext(), 300),
						DevUtils.dip2px(MainApplication.getContext(), 150))
				.centerCrop().placeholder(R.drawable.empty_photo)
				.into(viewHolder.iv_thumb);
	
		viewHolder.iv_thumb.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				// TODO
				Integer tag = (Integer) view.getTag();
				String topicId = mDatas.get(tag).infoId;
				Intent intent = new Intent(MainApplication.getContext(),
						TopicRecommenActivity.class);
				intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				intent.putExtra("topicId", topicId);
				MainApplication.getContext().startActivity(intent);
			}
		});
		return view;
	}

	@Override
	public int getCount() {
		if (mDatas == null)
			return 0;
		return mDatas.size();
	}

	class ViewHolder {
		ImageView iv_thumb;

		public ViewHolder(View view) {
			iv_thumb = (ImageView) view.findViewById(R.id.iv_advert);
		}
	}

	/**
	 * @param <T>
	 *            任何你指定的对象
	 */
	// public interface Holder<T> {
	// View createView(Context context);
	//
	// void UpdateUI(Context context, int position, T data);
	// }
}
