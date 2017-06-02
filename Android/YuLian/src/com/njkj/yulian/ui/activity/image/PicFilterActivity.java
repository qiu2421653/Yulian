package com.njkj.yulian.ui.activity.image;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.widget.GridView;
import android.widget.PopupWindow.OnDismissListener;
import android.widget.Toast;

import com.njkj.yulian.R;
import com.njkj.yulian.constant.ReqCode;
import com.njkj.yulian.entity.ImageFloder;
import com.njkj.yulian.ui.adapter.pic.PicFilterAdapter;
import com.njkj.yulian.ui.adapter.pic.PicFilterAdapter.OnPicFilterCallBack;
import com.njkj.yulian.ui.adapter.pic.PicSelAdapter;
import com.njkj.yulian.ui.gui.camera.CameraActivity;
import com.njkj.yulian.ui.gui.picsel.ListImageDirPopupWindow;
import com.njkj.yulian.ui.gui.picsel.ListImageDirPopupWindow.OnImageDirSelected;
import com.njkj.yulian.utils.DevUtils;
import com.njkj.yulian.utils.FileUtils;

/**
 * ==============================
 * 
 * @author Qiu
 * 
 * @Package com.njkj.yulian.ui.activity
 * 
 * @Description:选择图片
 * 
 * @date 2016-3-23 上午11:07:42
 * 
 * @version 1.0 ==============================
 */
public class PicFilterActivity extends Activity implements OnImageDirSelected {
	public static final int REQUEST_PICS_CODE = 201;
	public static final int RESULT_PICS_OK_CODE = 202;
	public static final String RESULT_PICS_ARRAY = "RESULT_PICS_ARRAY";
	private ProgressDialog mProgressDialog;
	public static int CURCOUNT = 1;// 当前图片数量
	/**
	 * 存储文件夹中的图片数量
	 */
	private int mPicsSize;
	/**
	 * 图片数量最多的文件夹
	 */
	private File mImgDir;
	/**
	 * 所有的图片
	 */
	private List<String> mImgs;

	private GridView mGirdView;
	private PicFilterAdapter mAdapter;

	private View ly_top;
	private File tmpFile;
	private String tmpPath;
	private static PicFilterActivity instance;
	/**
	 * 临时的辅助类，用于防止同一个文件夹的多次扫描
	 */
	private HashSet<String> mDirPaths = new HashSet<String>();

	/**
	 * 扫描拿到所有的图片文件夹
	 */
	private List<ImageFloder> mImageFloders = new ArrayList<ImageFloder>();

	int totalCount = 0;
	private int mScreenHeight;
	private ListImageDirPopupWindow mListImageDirPopupWindow;

	private Handler mHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			mProgressDialog.dismiss();
			// 为View绑定数据
			data2View();
			// 初始化展示文件夹的popupWindw
			initListDirPopupWindw();
		}
	};

	// 图片选择回调
	private OnPicFilterCallBack onPicSelCallBack = new OnPicFilterCallBack() {
		@Override
		public void onSel(String mSelectedImage) {
			if (!TextUtils.isEmpty(mSelectedImage)) {
				// 拿到图片
				Intent okData = new Intent();
				okData.putExtra("filePath", mSelectedImage);
				setResult(RESULT_OK, okData);
				finish();
			}
		}

		@Override
		public void onCam() {
			// TODO 启动自己的渲染相机
			// 跳转到自定义相机页面,调用系统的小米，酷派会出错(自动杀死Activity)
			Intent intent = new Intent(PicFilterActivity.this,
					CameraActivity.class);
			// 照相机(ReqCode=101)
			startActivityForResult(intent, ReqCode.REQ_UPLOAD_PHOTO_CAMERA);
		}
	};

	/**
	 * 为View绑定数据
	 */
	private void data2View() {
		if (mImgDir == null) {
			Toast.makeText(getApplicationContext(), "没有扫描到图片",
					Toast.LENGTH_SHORT).show();
			return;
		}

		// mImgs = Arrays.asList(mImgDir.list());
		mImgs = Arrays.asList(mImgDir.list(new FilenameFilter() {
			@Override
			public boolean accept(File dir, String filename) {
				if (filename.endsWith(".jpg") || filename.endsWith(".png")
						|| filename.endsWith(".jpeg"))
					return true;
				return false;
			}
		}));
		/**
		 * 可以看到文件夹的路径和图片的路径分开保存，极大的减少了内存的消耗；
		 */
		mAdapter = new PicFilterAdapter(getApplication(), mImgs,
				mImgDir.getAbsolutePath());
		mAdapter.setOnPicFilterCallBack(onPicSelCallBack);
		mGirdView.setAdapter(mAdapter);
		mAdapter.notifyDataSetChanged();
	};

	/**
	 * 初始化展示文件夹的popupWindw
	 */
	private void initListDirPopupWindw() {
		View view = LayoutInflater.from(getApplicationContext()).inflate(
				R.layout.activity_picsel_list_dir, null);
		mListImageDirPopupWindow = new ListImageDirPopupWindow(
				LayoutParams.MATCH_PARENT,
				(int) (mScreenHeight - DevUtils.dip2px(this, 110)),
				mImageFloders, view);

		mListImageDirPopupWindow.setOnDismissListener(new OnDismissListener() {
			@Override
			public void onDismiss() {
				// 设置背景颜色变暗
				WindowManager.LayoutParams lp = getWindow().getAttributes();
				lp.alpha = 1.0f;
				getWindow().setAttributes(lp);
			}
		});
		// 设置选择文件夹的回调
		mListImageDirPopupWindow.setOnImageDirSelected(this);
		view.findViewById(R.id.id_cancel).setOnClickListener(
				new View.OnClickListener() {
					@Override
					public void onClick(View arg0) {
						// TODO Auto-generated method stub
						mListImageDirPopupWindow.dismiss();
					}
				});
	}

	public static File getSDCardDir(Context context, String dirName) {
		FileUtils fileUtils = new FileUtils();
		if (!fileUtils.isSDCardMounted()) {
			return context.getFilesDir();
		}
		if (!fileUtils.isDirectory(fileUtils.getSDPATH() + dirName)) {
			fileUtils.createSDDir(dirName);
		}
		return new File(fileUtils.getSDPATH() + dirName);
	}

	public static PicFilterActivity getInstance() {
		return instance;
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		mHandler.removeCallbacksAndMessages(null);
		instance = null;
		super.onDestroy();
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		instance = this;
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_picfilter);
		ArrayList<String> pics = getIntent().getStringArrayListExtra(
				RESULT_PICS_ARRAY);
		if (pics != null) {
			if (PicSelAdapter.mSelectedImage == null) {
				PicSelAdapter.mSelectedImage = new ArrayList<String>();
			}
			PicSelAdapter.mSelectedImage.clear();
			PicSelAdapter.mSelectedImage.addAll(pics);

		} else {
			if (PicSelAdapter.mSelectedImage != null) {
				PicSelAdapter.mSelectedImage.clear();
			}
		}

		DisplayMetrics outMetrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(outMetrics);
		mScreenHeight = outMetrics.heightPixels;
		initView();
		if (ContextCompat.checkSelfPermission(this,
				Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
		}
		getImages();
		initEvent();
		// 获取图片地址
		ArrayList<String> piclist = (ArrayList<String>) getIntent()
				.getSerializableExtra("piclist");
		if (piclist != null) {
			PicSelAdapter.mSelectedImage = piclist;
		}
	}

	/**
	 * 利用ContentProvider扫描手机中的图片，此方法在运行在子线程中 完成图片的扫描，最终获得jpg最多的那个文件夹
	 */
	private void getImages() {
		if (!Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED)) {
			Toast.makeText(this, "暂无外部存储", Toast.LENGTH_SHORT).show();
			return;
		}
		// 显示进度条
		mProgressDialog = ProgressDialog.show(this, null, "正在加载...");

		new Thread(new Runnable() {
			@Override
			public void run() {
				String firstImage = null;
				Uri mImageUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
				ContentResolver mContentResolver = PicFilterActivity.this
						.getContentResolver();

				// 只查询jpeg和png的图片
				Cursor mCursor = mContentResolver.query(mImageUri, null,
						MediaStore.Images.Media.MIME_TYPE + "=? or "
								+ MediaStore.Images.Media.MIME_TYPE + "=?",
						new String[] { "image/jpeg", "image/png" },
						MediaStore.Images.Media.DATE_MODIFIED);

				Log.e("TAG", mCursor.getCount() + "");
				while (mCursor.moveToNext()) {
					// 获取图片的路径
					String path = mCursor.getString(mCursor
							.getColumnIndex(MediaStore.Images.Media.DATA));

					Log.e("TAG", path);
					Uri uri = Uri.parse(path);
					// 拿到第一张图片的路径
					if (firstImage == null)
						firstImage = path;
					// 获取该图片的父路径名
					File parentFile = new File(path).getParentFile();
					if (parentFile == null)
						continue;
					String dirPath = parentFile.getAbsolutePath();
					ImageFloder imageFloder = null;
					// 利用一个HashSet防止多次扫描同一个文件夹（不加这个判断，图片多起来还是相当恐怖的~~）
					if (mDirPaths.contains(dirPath)) {
						continue;
					} else {
						mDirPaths.add(dirPath);
						// 初始化imageFloder
						imageFloder = new ImageFloder();
						imageFloder.setDir(dirPath);
						imageFloder.setFirstImagePath(path);
					}

					String[] list = parentFile.list(new FilenameFilter() {
						@Override
						public boolean accept(File dir, String filename) {
							if (filename.endsWith(".jpg")
									|| filename.endsWith(".png")
									|| filename.endsWith(".jpeg"))
								return true;
							return false;
						}
					});
					int picSize = 0;
					if (list != null) {
						picSize = list.length;
					}
					totalCount += picSize;

					imageFloder.setCount(picSize);
					mImageFloders.add(imageFloder);

					if (picSize > mPicsSize) {
						mPicsSize = picSize;
						mImgDir = parentFile;
					}
				}
				mCursor.close();

				// 扫描完成，辅助的HashSet也就可以释放内存了
				mDirPaths = null;

				// 通知Handler扫描图片完成
				mHandler.sendEmptyMessage(0x110);
			}
		}).start();
	}

	/**
	 * 初始化View
	 */
	private void initView() {
		mGirdView = (GridView) findViewById(R.id.id_gridView);
		findViewById(R.id.id_cancel).setOnClickListener(
				new View.OnClickListener() {
					@Override
					public void onClick(View arg0) {
						finish();
					}
				});
	}

	private void initEvent() {
		ly_top = findViewById(R.id.ly_top);
		/**
		 * 为底部的布局设置点击事件，弹出popupWindow
		 */
		findViewById(R.id.sel_pic).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				mListImageDirPopupWindow
						.setAnimationStyle(R.style.anim_popup_dir);
				mListImageDirPopupWindow.showAsDropDown(ly_top, 0, 0);

				// 设置背景颜色变暗
				WindowManager.LayoutParams lp = getWindow().getAttributes();
				lp.alpha = .3f;
				getWindow().setAttributes(lp);
			}
		});
	}

	@Override
	public void selected(ImageFloder floder) {
		mImgDir = new File(floder.getDir());
		mImgs = Arrays.asList(mImgDir.list(new FilenameFilter() {
			@Override
			public boolean accept(File dir, String filename) {
				if (filename.endsWith(".jpg") || filename.endsWith(".png")
						|| filename.endsWith(".jpeg"))
					return true;
				return false;
			}
		}));
		/**
		 * 可以看到文件夹的路径和图片的路径分开保存，极大的减少了内存的消耗；
		 */
		mAdapter = new PicFilterAdapter(getApplicationContext(), mImgs,
				mImgDir.getAbsolutePath());
		mAdapter.setOnPicFilterCallBack(onPicSelCallBack);
		mGirdView.setAdapter(mAdapter);
		mListImageDirPopupWindow.dismiss();

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == Activity.RESULT_CANCELED)
			return;
		if (resultCode == PicFilterActivity.REQUEST_PICS_CODE) {
		}
		// 照相功能
		if (requestCode == ReqCode.REQ_UPLOAD_PHOTO_CAMERA) {
			if (data != null) {
				String path = data.getStringExtra("path");
				if (!TextUtils.isEmpty(path)) {
					// 拿到图片
					Intent okData = new Intent();
					okData.putExtra("filePath", path);
					setResult(RESULT_OK, okData);
					finish();
				}
			}
		}
	}

	int count;

	public File getTmpFile() {
		File dir = getSDCardDir(this, "mljia");
		if (dir != null) {
			tmpFile = new File(dir, "tmp.jpg" + count);
			if (tmpFile != null) {
				tmpPath = tmpFile.getAbsolutePath();
				count++;
				if (count > CURCOUNT) {
					count = 0;
				}
				return tmpFile;
			}
		}
		return null;
	}

	public static Bitmap comPressBitmap(String path, int w, int h) {
		Bitmap tmpBitmap = null;
		try {
			tmpBitmap = createNewBitmapAndCompressByFile(path,
					new int[] { w, h });
		} catch (RuntimeException e) {
		} catch (Exception e) {
		}
		return tmpBitmap;
	}

	public static Bitmap createNewBitmapAndCompressByFile(String filePath,
			int wh[]) {
		int offset = 100;
		File file = new File(filePath);
		int raye = readPictureDegree(filePath);

		long fileSize = file.length();
		if (200 * 1024 < fileSize && fileSize <= 1024 * 1024)
			offset = 90;
		else if (1024 * 1024 < fileSize)
			offset = 85;

		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true; // 为true里只读图片的信息，如果长宽，返回的bitmap为null
		options.inPreferredConfig = Bitmap.Config.ARGB_8888;
		options.inDither = false;
		/**
		 * 计算图片尺寸 //TODO 按比例缩放尺寸
		 */
		BitmapFactory.decodeFile(filePath, options);

		int bmpheight = options.outHeight;
		int bmpWidth = options.outWidth;
		int inSampleSize = bmpheight / wh[1] > bmpWidth / wh[0] ? bmpheight
				/ wh[1] : bmpWidth / wh[0];
		// if(bmpheight / wh[1] < bmpWidth / wh[0]) inSampleSize = inSampleSize
		// * 2 / 3;//TODO 如果图片太宽而高度太小，则压缩比例太大。所以乘以2/3
		if (inSampleSize > 1)
			options.inSampleSize = inSampleSize;// 设置缩放比例
		options.inJustDecodeBounds = false;

		InputStream is = null;
		try {
			is = new FileInputStream(file);
		} catch (FileNotFoundException e) {
			return null;
		}
		Bitmap bitmap = null;
		try {
			bitmap = BitmapFactory.decodeStream(is, null, options);
		} catch (OutOfMemoryError e) {
			System.gc();
			bitmap = null;
		}

		if (offset == 100)
			return bitmap;// 缩小质量
		ByteArrayOutputStream baos = new ByteArrayOutputStream();

		if (bitmap != null) {
			bitmap.compress(Bitmap.CompressFormat.JPEG, offset, baos);
		}
		byte[] buffer = baos.toByteArray();
		options = null;
		if (buffer.length >= fileSize)
			return bitmap;
		try {
			return BitmapFactory.decodeByteArray(buffer, 0, buffer.length);
		} catch (RuntimeException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (raye != 0) {
			bitmap = rotaingImageView(-raye, bitmap);
		}
		return bitmap;
	}

	/**
	 * @desc <pre>
	 * 旋转图片
	 * </pre>
	 * @author Weiliang Hu
	 * @date 2013-9-18
	 * @param angle
	 * @param bitmap
	 * @return
	 */
	public static Bitmap rotaingImageView(int angle, Bitmap bitmap) {
		// 旋转图片 动作
		Matrix matrix = new Matrix();
		matrix.postRotate(angle);
		// 创建新的图片
		Bitmap resizedBitmap = Bitmap.createBitmap(bitmap, 0, 0,
				bitmap.getWidth(), bitmap.getHeight(), matrix, true);
		bitmap.recycle();
		return resizedBitmap;
	}

	/**
	 * @desc <pre>
	 * 获取图片旋转的角度
	 * </pre>
	 * @author Weiliang Hu
	 * @date 2013-9-18
	 * @param path
	 * @return
	 */
	public static int readPictureDegree(String path) {
		int degree = 0;
		try {
			ExifInterface exifInterface = new ExifInterface(path);
			int orientation = exifInterface.getAttributeInt(
					ExifInterface.TAG_ORIENTATION,
					ExifInterface.ORIENTATION_NORMAL);
			switch (orientation) {
			case ExifInterface.ORIENTATION_ROTATE_90:
				degree = 90;
				break;
			case ExifInterface.ORIENTATION_ROTATE_180:
				degree = 180;
				break;
			case ExifInterface.ORIENTATION_ROTATE_270:
				degree = 270;
				break;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return degree;
	}

}
