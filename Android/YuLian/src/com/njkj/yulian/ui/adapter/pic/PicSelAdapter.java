package com.njkj.yulian.ui.adapter.pic;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.njkj.yulian.R;
import com.njkj.yulian.ui.activity.upload.PicSelActivity;
import com.njkj.yulian.utils.DevUtils;
import com.squareup.picasso.Picasso;

/**
 * ==============================
 * 
 * @author Qiu
 * 
 * @Package com.njkj.yulian.ui.adapter
 * 
 * @Description:多图片选择
 * 
 * @date 2016-3-22 下午2:28:26
 * 
 * @version 1.0 ==============================
 */
public class PicSelAdapter extends BaseAdapter {

	public interface OnPicSelCallBack {
		void onCam();

		void onSel(ArrayList<String> mSelectedImage);
	}

	private OnPicSelCallBack onPicSelCallBack;

	public OnPicSelCallBack getOnPicSelCallBack() {
		return onPicSelCallBack;
	}

	public void setOnPicSelCallBack(OnPicSelCallBack onPicSelCallBack) {
		this.onPicSelCallBack = onPicSelCallBack;
	}

	/**
	 * 用户选择的图片，存储为图片的完整路径
	 */
	public static ArrayList<String> mSelectedImage = new ArrayList<String>();
	/**
	 * 文件夹路径
	 */
	private String mDirPath;
	private Context mContext;
	protected LayoutInflater mInflater;
	protected List<String> mDatas;

	private static final int TYPE_ONE = 0, TYPE_TWO = 1, TYPE_COUNT = 2;
	ViewHolder viewHolder;
	ViewDrawHolder viewDrawHolder;

	public PicSelAdapter(Context context, List<String> mDatas, String dirPath) {
		this.mContext = context;
		this.mDirPath = dirPath;
		this.mInflater = LayoutInflater.from(context);
		this.mDatas = mDatas;
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
				// viewHolder = new ViewHolder(convertView, position);
				viewHolder = new ViewHolder();
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
			final ImageView id_item_image = (ImageView) convertView
					.findViewById(R.id.id_item_image);
			final ImageButton id_item_select = (ImageButton) convertView
					.findViewById(R.id.id_item_select);

			// 设置no_pic
			id_item_image.setImageResource(R.drawable.pictures_no);
			id_item_image.setColorFilter(null);
			// 设置no_selected
			id_item_select.setImageResource(0);

			String uri = "file://" + mDirPath + "/" + mDatas.get(position - 1);

			Picasso.with(mContext)
					.load(uri)
					.placeholder(R.drawable.empty_photo)
					.config(Bitmap.Config.RGB_565)
					.resize(DevUtils.dip2px(mContext, 120),
							DevUtils.dip2px(mContext, 120)).centerCrop()
					.error(R.drawable.empty_photo).into(id_item_image);

			// 设置ImageView的点击事件
			id_item_image.setOnClickListener(new OnClickListener() {
				// 选择，则将图片变暗，反之则反
				@Override
				public void onClick(View v) {
					// 已经选择过该图片
					if (mSelectedImage.contains(mDirPath + "/"
							+ mDatas.get(position - 1))) {
						mSelectedImage.remove(mDirPath + "/"
								+ mDatas.get(position - 1));
						// TODO -图片显示异常
						id_item_select.setImageResource(0);
						id_item_image.setColorFilter(null);
					} else
					// 未选择该图片
					{
						if (mSelectedImage.size() >= PicSelActivity.CURCOUNT) {
							Toast.makeText(v.getContext(),
									"图片已经超过" + PicSelActivity.MAX_SIZE + "张",
									Toast.LENGTH_SHORT).show();
							return;
						}
						mSelectedImage.add(mDirPath + "/"
								+ mDatas.get(position - 1));
						// TODO 选中后改变图层
						id_item_select
								.setImageResource(R.drawable.pictures_selected);
						id_item_image.setColorFilter(Color
								.parseColor("#d0dddfea"));
					}
					if (onPicSelCallBack != null) {
						onPicSelCallBack.onSel(mSelectedImage);
					}
				}
			});
			/**
			 * 已经选择过的图片，显示出选择过的效果
			 */
			if (mSelectedImage.contains(mDirPath + "/"
					+ mDatas.get(position - 1))) {
				id_item_select.setImageResource(R.drawable.pictures_selected);
				id_item_image.setColorFilter(Color.parseColor("#d0dddfea"));
			}
			break;
		}
		return convertView;
	}

	// 选照片
	class ViewHolder {
		// ImageView id_item_image;
		// ImageButton id_item_select;

		// public ViewHolder(View view, int position) {
		// id_item_image = (ImageView) view.findViewById(R.id.id_item_image);
		// id_item_select = (ImageButton) view
		// .findViewById(R.id.id_item_select);
		// id_item_image.setTag(position);
		// id_item_select.setTag(position);
		// }
	}

	// 拍照
	class ViewDrawHolder {
		ImageView id_item_image;

		public ViewDrawHolder(View view) {
			id_item_image = (ImageView) view.findViewById(R.id.id_item_image);
		}
	}

}
