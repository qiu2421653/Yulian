package com.njkj.yulian.ui.adapter.pic;

import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.njkj.yulian.R;
import com.squareup.picasso.Picasso;

/**
 * ==============================
 * 
 * @author Qiu
 * 
 * @Package com.njkj.yulian.ui.adapter
 * 
 * @Description:图片选择(单张)
 * 
 * @date 2016-3-22 下午2:28:26
 * 
 * @version 1.0 ==============================
 * @param <T>
 */
public class PicFilterAdapter<T> extends BaseAdapter {

	protected LayoutInflater mInflater;
	protected Context mContext;
	protected List<T> mDatas;

	private static final int TYPE_ONE = 0, TYPE_TWO = 1, TYPE_COUNT = 2;
	ViewHolder viewHolder;
	ViewDrawHolder viewDrawHolder;
	/**
	 * 文件夹路径
	 */
	private String mDirPath;

	/**
	 * 用户选择的图片，存储为图片的完整路径
	 */
	public static String mSelectedImage;

	public interface OnPicFilterCallBack {
		void onSel(String mSelectedImage);

		void onCam();
	}

	public PicFilterAdapter(Context context, List<T> mDatas, String dirPath) {
		this.mContext = context;
		this.mInflater = LayoutInflater.from(mContext);
		this.mDatas = mDatas;
		this.mDirPath = dirPath;
	}

	private OnPicFilterCallBack onPicSelCallBack;

	public OnPicFilterCallBack getOnPicSelCallBack() {
		return onPicSelCallBack;
	}

	public void setOnPicFilterCallBack(OnPicFilterCallBack onPicSelCallBack) {
		this.onPicSelCallBack = onPicSelCallBack;
	}

	@Override
	public int getCount() {
		return mDatas.size() + 1;
	}

	@Override
	public Object getItem(int position) {
		return null;
	}

	@Override
	public long getItemId(int position) {
		return position;
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
	public View getView(final int position, View convertView, ViewGroup parent) {
		// 对应位置类型
		int type = getItemViewType(position);
		if (convertView == null) {
			switch (type) {
			case TYPE_ONE:
				// 加载view
				convertView = mInflater.inflate(
						R.layout.activity_picsel_take_item, null);
				viewDrawHolder = new ViewDrawHolder(convertView);
				convertView.setTag(viewDrawHolder);
				break;
			case TYPE_TWO:
				// 加载view
				convertView = mInflater.inflate(
						R.layout.activity_picsel_grid_item, null);
				viewHolder = new ViewHolder(convertView);
				convertView.setTag(viewHolder);
				break;
			}
		} else {
			switch (type) {
			case TYPE_ONE:
				viewDrawHolder = (ViewDrawHolder) convertView.getTag();
				break;
			case TYPE_TWO:
				viewHolder = (ViewHolder) convertView.getTag();
				break;
			}
		}
		switch (type) {
		case TYPE_ONE:
			// 拍照点击事件
			viewDrawHolder.id_item_image
					.setOnClickListener(new OnClickListener() {
						@Override
						public void onClick(View view) {
							onPicSelCallBack.onCam();
						}
					});
			break;
		case TYPE_TWO:
			String uri = "file://" + mDirPath + "/" + mDatas.get(position - 1);
//			ImageLoader.getInstance().displayImage(uri,
//					viewHolder.id_item_image);

			Picasso.with(mContext)
					.load(uri)
					.placeholder(R.drawable.empty_photo)
					.config(Bitmap.Config.RGB_565)
					.error(R.drawable.empty_photo)
					.into(viewHolder.id_item_image);
			// 设置ImageView的点击事件
			viewHolder.id_item_image.setOnClickListener(new OnClickListener() {
				// 选择，则将图片变暗，反之则反
				@Override
				public void onClick(View v) {
					mSelectedImage = mDirPath + "/" + mDatas.get(position - 1);
					// viewHolder.id_item_image.setColorFilter(Color
					// .parseColor("#d0dddfea"));
					// 传递------------>
					if (onPicSelCallBack != null) {
						onPicSelCallBack.onSel(mSelectedImage);
					}
				}
			});
			break;
		}

		return convertView;
	}

	// 选照片
	class ViewHolder {
		ImageView id_item_image;
		ImageButton id_item_select;

		public ViewHolder(View view) {
			id_item_image = (ImageView) view.findViewById(R.id.id_item_image);
			id_item_select = (ImageButton) view
					.findViewById(R.id.id_item_select);
		}
	}

	// 拍照
	class ViewDrawHolder {
		ImageView id_item_image;

		public ViewDrawHolder(View view) {
			id_item_image = (ImageView) view.findViewById(R.id.id_item_image);
		}
	}

}
