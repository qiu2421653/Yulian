package com.njkj.yulian.ui.activity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.njkj.yulian.MainApplication;
import com.njkj.yulian.R;
import com.njkj.yulian.core.lib.photoview.PhotoView;
import com.njkj.yulian.core.lib.photoview.PhotoViewAttacher;
import com.njkj.yulian.entity.PictureEntity;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.Picasso;

/**
 * ==============================
 * 
 * @author Qiu
 * 
 * @Package com.njkj.yulian.ui.activity
 * 
 * @Description:图片预览
 * 
 * @date 2016-3-25 下午2:40:20
 * 
 * @version 1.0 ==============================
 */
public class ImagePagerActivity extends BaseActivity {
	public static final String INTENT_IMGURLS = "imgurls";
	public static final String INTENT_POSITION = "position";
	private List<View> guideViewList = new ArrayList<View>();
	private LinearLayout guideGroup;
	private static PhotoViewAttacher mAttacher;// 手势缩放

	public static void startImagePagerActivity(Context context,
			List<PictureEntity> imgUrls, int position) {
		Intent intent = new Intent(context, ImagePagerActivity.class);
		intent.putExtra(INTENT_IMGURLS, (Serializable) imgUrls);
		intent.putExtra(INTENT_POSITION, position);
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		context.startActivity(intent);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_imagepager);
		setHeaderTitle(R.string.app_name);
		setHeaderBackground(R.color.blue1);
		setHeaderLeftText();
		ViewPager viewPager = (ViewPager) findViewById(R.id.pager);
		guideGroup = (LinearLayout) findViewById(R.id.guideGroup);

		int startPos = getIntent().getIntExtra(INTENT_POSITION, 0);
		List<PictureEntity> imgUrls = (List<PictureEntity>) getIntent()
				.getSerializableExtra(INTENT_IMGURLS);
		ArrayList<String> datas = new ArrayList<String>();
		for (PictureEntity entity : imgUrls) {
			datas.add(entity.url);
		}
		ImageAdapter mAdapter = new ImageAdapter(this);
		mAdapter.setDatas(datas);
		viewPager.setAdapter(mAdapter);
		viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
			@Override
			public void onPageScrolled(int position, float positionOffset,
					int positionOffsetPixels) {
			}

			@Override
			public void onPageSelected(int position) {
				for (int i = 0; i < guideViewList.size(); i++) {
					guideViewList.get(i).setSelected(
							i == position ? true : false);
				}
			}

			@Override
			public void onPageScrollStateChanged(int state) {

			}
		});
		viewPager.setCurrentItem(startPos);

		addGuideView(guideGroup, startPos, datas);
	}

	private void addGuideView(LinearLayout guideGroup, int startPos,
			ArrayList<String> imgUrls) {
		if (imgUrls != null && imgUrls.size() > 0) {
			guideViewList.clear();
			for (int i = 0; i < imgUrls.size(); i++) {
				View view = new View(this);
				view.setBackgroundResource(R.drawable.selector_guide_bg);
				view.setSelected(i == startPos ? true : false);
				LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
						getResources().getDimensionPixelSize(
								R.dimen.gudieview_width), getResources()
								.getDimensionPixelSize(R.dimen.gudieview_heigh));
				layoutParams.setMargins(10, 0, 0, 0);
				guideGroup.addView(view, layoutParams);
				guideViewList.add(view);
			}
		}
	}

	private static class ImageAdapter extends PagerAdapter {

		private List<String> datas = new ArrayList<String>();
		private LayoutInflater inflater;
		private Context context;

		public void setDatas(List<String> datas) {
			if (datas != null)
				this.datas = datas;
		}

		public ImageAdapter(Context context) {
			this.context = context;
			this.inflater = LayoutInflater.from(context);
		}

		@Override
		public int getCount() {
			if (datas == null)
				return 0;
			return datas.size();
		}

		@Override
		public Object instantiateItem(ViewGroup container, final int position) {
			View view = inflater.inflate(R.layout.item_pager_image, container,
					false);
			if (view != null) {
				final PhotoView imageView = (PhotoView) view
						.findViewById(R.id.image);
				// 预览imageView
				final String imgurl = datas.get(position);

				Picasso.with(MainApplication.getContext())
						.load(imgurl)
						.placeholder(R.drawable.empty_photo)
						.config(Bitmap.Config.RGB_565)
						.memoryPolicy(MemoryPolicy.NO_CACHE,
								MemoryPolicy.NO_STORE)
						// .resize(DevUtils.getScreenWidth(), 400).centerCrop()
						.error(R.drawable.empty_photo).into(imageView);

				// mAttacher = new PhotoViewAttacher(imageView);
				// mAttacher.update();
				container.addView(view, 0);
			}
			return view;
		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			container.removeView((View) object);
		}

		@Override
		public boolean isViewFromObject(View view, Object object) {
			return view.equals(object);
		}

		@Override
		public void restoreState(Parcelable state, ClassLoader loader) {
		}

		@Override
		public Parcelable saveState() {
			return null;
		}

	}
}
