package com.njkj.yulian.ui.activity.image;

import java.io.File;
import java.util.LinkedList;

import jp.co.cyberagent.android.gpuimage.GPUImage.OnPictureSavedListener;
import jp.co.cyberagent.android.gpuimage.GPUImageFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageFilterGroup;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.SystemClock;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;
import android.widget.Toast;

import com.js.photosdk.filter.GPUImageView;
import com.njkj.yulian.MainApplication;
import com.njkj.yulian.R;
import com.njkj.yulian.core.lib.event.EventBus;
import com.njkj.yulian.entity.EventEntity.Adjust;
import com.njkj.yulian.entity.EventEntity.Better;
import com.njkj.yulian.entity.EventEntity.EditAdjust;
import com.njkj.yulian.entity.EventEntity.Filters;
import com.njkj.yulian.entity.FilterEntity;
import com.njkj.yulian.ui.activity.ClipImageActivity;
import com.njkj.yulian.ui.activity.upload.UploadInfoActivity;
import com.njkj.yulian.ui.fragment.filter.AdjustFragment;
import com.njkj.yulian.ui.fragment.filter.BetterFragment;
import com.njkj.yulian.ui.fragment.filter.EditAdjustFragment;
import com.njkj.yulian.ui.fragment.filter.FilterFragment;
import com.njkj.yulian.utils.CLog;
import com.njkj.yulian.utils.FileUtil;
import com.njkj.yulian.utils.GPUImageFilterTools.FilterAdjuster;

/**
 * ==============================
 * 
 * @author Qiu
 * 
 * @Package com.njkj.yulian.ui.activity.upload
 * 
 * @Description:图片编辑
 * 
 * @date 2016-4-13 上午9:54:27
 * 
 * @version 1.0 ==============================
 */
public class ImageEditActivity extends FragmentActivity implements
		OnClickListener, OnPictureSavedListener {
	/* 添加水印图片 */
	private static final int PHOTO_ADD_WATERMARK_DATA = 3032;
	/* 选取图片 */
	public static final int REQUEST_CODE_PICK_IMAGE = 1;

	private static final String TAG = "ImageEditActivity";

	ImageView[] imagebuttons;
	TextView[] textviews;
	GPUImageView gpuimage;
	RelativeLayout main_content;
	FilterAdjuster mFilterAdjuster;

	BetterFragment betterFragment;// 推荐
	AdjustFragment adjustFragment;// 饱和度
	FilterFragment filterFragment;// 滤镜
	// StickerFragment stickerFragment;// 水印
	EditAdjustFragment editAdjustment;// 调整滤镜值
	protected ProgressDialog mProgressDialog;

	FragmentManager fm;
	Uri currUri;
	int currentPosition = 0;// 当前位置
	boolean isExit = false;// 退出
	boolean isSticker = true;// 贴纸|文字
	boolean isFirst = true;// 第一次进入
	String fileName = "yulian" + ".jpg";
	String saveName;

	// filterGroup
	LinkedList<GPUImageFilter> emFilter = new LinkedList<GPUImageFilter>();
	public static LinkedList<FilterEntity> filterList = new LinkedList<FilterEntity>();

	Better mBetter = new Better();
	Filters filters = new Filters();

	private GPUImageFilterGroup gpuImageFilterGroup = new GPUImageFilterGroup();// filterGroup
	private int filterIndex;// 指定下标

	private final int CROP_RESULT_CODE = 3;// 文字页面回调的时候用
	String stringExtra;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_image_edit);
		initView();
		initData();
		initListener();
	}

	private void initView() {
		imagebuttons = new ImageView[5];
		imagebuttons[0] = (ImageView) findViewById(R.id.iv_better);
		imagebuttons[1] = (ImageView) findViewById(R.id.iv_filter);
		imagebuttons[2] = (ImageView) findViewById(R.id.iv_sticker);
		imagebuttons[3] = (ImageView) findViewById(R.id.iv_font_bar);
		imagebuttons[4] = (ImageView) findViewById(R.id.iv_adjust_bar);

		textviews = new TextView[5];
		textviews[0] = (TextView) findViewById(R.id.tv_better);
		textviews[1] = (TextView) findViewById(R.id.tv_filter);
		textviews[2] = (TextView) findViewById(R.id.tv_sticker);
		textviews[3] = (TextView) findViewById(R.id.tv_font_bar);
		textviews[4] = (TextView) findViewById(R.id.tv_adjust_bar);
		main_content = (RelativeLayout) findViewById(R.id.main_content);
		// gpuimage = (GPUImageView) findViewById(R.id.gpuimage);
		LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT,
				LayoutParams.MATCH_PARENT);
		gpuimage = new GPUImageView(getApplicationContext());
		gpuimage.setLayoutParams(params);
		main_content.addView(gpuimage);
	}

	private void initData() {
		EventBus.getDefault().register(this);
		fm = this.getSupportFragmentManager();
		String path = getIntent().getStringExtra("path");
		if (TextUtils.isEmpty(path)) {
			// 开始选取照片
			pickPicture();
			isFirst = true;
		} else {
			// 设置滤镜图片
			gpuimage.setImage(new File(path));
			isFirst = false;
			showProgress(null, getString(R.string.loading), -1);

			new Handler().postDelayed(new Runnable() {
				@Override
				public void run() {
					hideProgress();
				}
			}, 1500);

		}

		// 设置默认滤镜
		mBetter.filterEntity = new FilterEntity(new GPUImageFilter());
		filters.filterEntity = new FilterEntity(new GPUImageFilter());
		// 首位是亮度调节的
		emFilter.add(0, mBetter.filterEntity.gpuFilter);
		// 次位是滤镜调节
		emFilter.add(1, filters.filterEntity.gpuFilter);
	}

	private void initListener() {

		findViewById(R.id.better).setOnClickListener(this);
		findViewById(R.id.filter).setOnClickListener(this);
		findViewById(R.id.sticker).setOnClickListener(this);
		findViewById(R.id.font_bar).setOnClickListener(this);
		findViewById(R.id.adjust_bar).setOnClickListener(this);

		findViewById(R.id.btn_back).setOnClickListener(this);
		findViewById(R.id.save_btn).setOnClickListener(this);
	}

	@Override
	public void onClick(View view) {
		imagebuttons[currentPosition].setSelected(false);
		textviews[currentPosition].setTextColor(getResources().getColor(
				R.color.common_tv));

		switch (view.getId()) {
		case R.id.save_btn:
			// TODO 保存 弹出个进度条呢...
			showProgress(null, getString(R.string.saving), -1);
			saveName = SystemClock.currentThreadTimeMillis() + ".jpg";
			isExit = true;
			gpuimage.saveToPictures("YuLian", saveName, this);
			break;
		case R.id.btn_back:
			// 返回
			finish();
			break;
		case R.id.better:
			currentPosition = 0;
			// 选择推荐
			if (betterFragment == null) {
				betterFragment = new BetterFragment();
			}
			fm.beginTransaction().replace(R.id.men_view, betterFragment)
					.commit();
			break;
		case R.id.filter:
			// 滤镜
			currentPosition = 1;
			if (filterFragment == null) {
				filterFragment = new FilterFragment();
			}
			fm.beginTransaction().replace(R.id.men_view, filterFragment)
					.commit();
			break;
		case R.id.sticker:
			currentPosition = 2;
			isSticker = true;
			// TODO 没有隐藏
			gpuimage.saveToPictures("YuLian", fileName, this);
			break;
		case R.id.font_bar:
			currentPosition = 3;
			isSticker = false;
			// TODO 没有隐藏
			gpuimage.saveToPictures("YuLian", fileName, this);
			break;
		case R.id.adjust_bar:
			currentPosition = 4;
			// 亮度饱和度...
			if (adjustFragment == null) {
				adjustFragment = new AdjustFragment();
			}
			fm.beginTransaction().replace(R.id.men_view, adjustFragment)
					.commit();
			break;
		}
		imagebuttons[currentPosition].setSelected(true);
		textviews[currentPosition].setTextColor(getResources().getColor(
				R.color.darkSkyBlue));
	}

	@Override
	public void onPictureSaved(Uri uri) {
		currUri = uri;
		// TODO 成功后回到上级页面
		// 结束进度条
		if (isExit) {
			File path = Environment
					.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
			File file = new File(path, "YuLian" + "/" + saveName);
			Intent intent = new Intent();
			intent.putExtra("path", file.getAbsolutePath().toString());
			if (isFirst) {
				// TODO 首次进入的需要启动上传信息页
				intent.setClass(ImageEditActivity.this,
						UploadInfoActivity.class);
				startActivity(intent);
			} else {
				setResult(UploadInfoActivity.FILTER, intent);
			}
			hideProgress();
			finish();
		} else {
			if (isSticker) {
				// 贴纸
				Intent intent = new Intent(this, AddWatermarkActivity.class);
				intent.putExtra("filepath", FileUtil.getRealFilePath(
						MainApplication.getContext(), uri));
				startActivityForResult(intent, PHOTO_ADD_WATERMARK_DATA);
			} else {
				// 文字
				ClipImageActivity.startActivity(this, FileUtil.getRealFilePath(
						MainApplication.getContext(), uri), CROP_RESULT_CODE);
			}

		}
	}

	// 饱和滤镜效果
	public void onEventMainThread(Adjust adjust) {
		if (!emFilter.contains(adjust.filterEntity.gpuFilter)) {
			emFilter.add(adjust.filterEntity.gpuFilter);
			filterList.add(adjust.filterEntity);
		}
		filterIndex = filterList.indexOf(adjust.filterEntity);
		if (editAdjustment == null)
			editAdjustment = new EditAdjustFragment(gpuimage, emFilter);
		// 传递滤镜
		editAdjustment.setFilter(filterList.get(filterIndex));
		fm.beginTransaction().replace(R.id.men_view, editAdjustment).commit();
	}

	// 推荐滤镜效果(OK)
	public void onEventMainThread(Better better) {
		this.mBetter = better;
		// 只有一个实例
		emFilter.remove(0);
		emFilter.add(0, mBetter.filterEntity.gpuFilter);
		updateFilterGroup();
	}

	// TODO 滤镜效果
	public void onEventMainThread(Filters filters) {
		this.filters = filters;
		String filterName = filters.filterEntity.filterName;
		emFilter.remove(1);
		if (!"原图".equals(filterName))
			emFilter.add(1, filters.filterEntity.gpuFilter);
		else
			emFilter.add(1, new GPUImageFilter());
		updateFilterGroup();
	}

	// 饱和度编辑
	public void onEventMainThread(EditAdjust adjust) {

		if (adjust.isCancel) {
			if (adjust.filterEntity.isFirst) {
				filterList.remove(filterIndex);
			} else {
				int indexFilter = filterList.indexOf(adjust.filterEntity);
				adjust.filterAdjuster
						.adjust((int) filterList.get(indexFilter).progress);
			}
		} else {
			if (!emFilter.contains(adjust.filterEntity.gpuFilter)) {
				// 加进去
				emFilter.add(adjust.filterEntity.gpuFilter);
			} else {
				// 替换
				int index = emFilter.indexOf(adjust.filterEntity.gpuFilter);
				emFilter.remove(index);
				emFilter.add(index, adjust.filterEntity.gpuFilter);
			}
			// 进度
			adjust.filterAdjuster.adjust((int) adjust.filterEntity.progress);
		}
		updateFilterGroup();
		fm.beginTransaction().replace(R.id.men_view, adjustFragment).commit();
	}

	// 更新混合
	private void updateFilterGroup() {
		// 恢复默认
		gpuimage.setFilter(new GPUImageFilter());
		gpuImageFilterGroup.setFilters(emFilter);
		gpuimage.setFilter(gpuImageFilterGroup);
		// gpuimage.requestRender();
	}

	// 相册选取图片
	private void pickPicture() {
		Intent photoPickerIntent = new Intent(this, PicFilterActivity.class);
		startActivityForResult(photoPickerIntent, REQUEST_CODE_PICK_IMAGE);
	}

	@Override
	public void onActivityResult(final int requestCode, final int resultCode,
			final Intent data) {
		if (resultCode == RESULT_CANCELED) {
			finish();
			return;
		}
		switch (requestCode) {
		case REQUEST_CODE_PICK_IMAGE:
			if (resultCode == RESULT_OK) {
				try {
					currUri = data.getData();
					stringExtra = data.getStringExtra("filePath");
					if (!TextUtils.isEmpty(stringExtra)) {
						gpuimage.setImage(new File(stringExtra));
					} else {
						finish();
					}
				} catch (Exception e) {
					e.printStackTrace();
					Toast.makeText(this, "Error: 未能打开图片", Toast.LENGTH_LONG)
							.show();
				}
			}
			break;
		case PHOTO_ADD_WATERMARK_DATA:
			// 添加水印
			imagebuttons[2].setSelected(false);
			textviews[2].setTextColor(getResources()
					.getColor(R.color.common_tv));
			if (!TextUtils.isEmpty(data.getStringExtra("filePath"))) {
				// 水印图片
				gpuimage.setImage(currUri);
			}
			break;
		case CROP_RESULT_CODE:
			// gpuimage
			// 添加文字
			imagebuttons[3].setSelected(false);
			textviews[3].setTextColor(getResources()
					.getColor(R.color.common_tv));
			if (!TextUtils.isEmpty(data.getStringExtra("crop_image"))) {
				String height = data.getStringExtra("height");
				CLog.e(TAG, "height:" + height);
				if (!TextUtils.isEmpty(height)) {
					// gpuimage = null;
					main_content.removeAllViews();
					// gpuimage = new GPUImageView(getApplicationContext());
					LayoutParams params = new LayoutParams(
							LayoutParams.MATCH_PARENT, Integer.parseInt(height));
					gpuimage.setLayoutParams(params);

					main_content.addView(gpuimage);

					// 水印图片
					gpuimage.setImage(currUri);
				}

			}
			break;
		}
	}

	public ProgressDialog showProgress(String title, String message, int theme) {
		if (mProgressDialog == null) {
			if (theme > 0)
				mProgressDialog = new ProgressDialog(this, theme);
			else
				mProgressDialog = new ProgressDialog(this);
			mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
			mProgressDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
			mProgressDialog.setCanceledOnTouchOutside(false);// 不能取消
			mProgressDialog.setIndeterminate(true);// 设置进度条是否不明确
		}

		if (!TextUtils.isEmpty(title))
			mProgressDialog.setTitle(title);
		mProgressDialog.setMessage(message);
		mProgressDialog.show();
		return mProgressDialog;
	}

	public void hideProgress() {
		if (mProgressDialog != null) {
			mProgressDialog.dismiss();
		}
	}

	@Override
	protected void onDestroy() {
		EventBus.getDefault().unregister(this);
		super.onDestroy();
	}

}
