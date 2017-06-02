package com.njkj.yulian.ui.gui.camera;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.hardware.Camera;
import android.hardware.Camera.PictureCallback;
import android.hardware.Camera.ShutterCallback;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.ScaleAnimation;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.njkj.yulian.R;
import com.njkj.yulian.constant.ReqCode;
import com.njkj.yulian.utils.BitmapUtils;
import com.njkj.yulian.utils.CLog;
import com.njkj.yulian.utils.FileUtil;

public class CameraActivity extends Activity implements View.OnTouchListener,
		OnClickListener {
	private final String TAG = "CameraActivity";
	public final String CAMERA_PATH_VALUE1 = "PHOTO_PATH";
	public final String CAMERA_PATH_VALUE2 = "PATH";
	public final String CAMERA_TYPE = "CAMERA_TYPE";
	public final String CAMERA_RETURN_PATH = "return_path";

	private int PHOTO_SIZE_W = 1000;
	private int PHOTO_SIZE_H = 1000;
	public final int CAMERA_TYPE_2 = 2;

	private CameraPreview preview;
	private Camera camera;
	private Context mContext;
	private View focusIndex;
	private ImageView flashBtn;
	private int mCurrentCameraId = 0; // 1是前置 0是后置
	private SurfaceView mSurfaceView;
	private int type = 1; // 引用的矩形框

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mContext = this;
		setContentView(R.layout.activity_camera_guide);
		type = getIntent().getIntExtra(CAMERA_TYPE, CAMERA_TYPE_2);
	}

	private void initView() {
		focusIndex = (View) findViewById(R.id.focus_index);
		flashBtn = (ImageView) findViewById(R.id.flash_view);
		mSurfaceView = (SurfaceView) findViewById(R.id.surfaceView);
	}

	private void InitData() {
		// 初始化预览页面
		preview = new CameraPreview(this, mSurfaceView);
		preview.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
				LayoutParams.MATCH_PARENT));
		((FrameLayout) findViewById(R.id.layout)).addView(preview);
		preview.setKeepScreenOn(true);// 屏幕高亮
		mSurfaceView.setOnTouchListener(this);// 触摸监听
	}

	private Handler handler = new Handler();

	/**
	 * 拍照
	 */
	private void takePhoto() {
		try {
			camera.takePicture(shutterCallback, rawCallback, jpegCallback);
		} catch (Throwable t) {
			t.printStackTrace();
			Toast.makeText(getApplication(), "拍照失败，请重试！", Toast.LENGTH_LONG)
					.show();
			try {
				camera.startPreview();
			} catch (Throwable e) {

			}
		}
	}

	@Override
	protected void onResume() {
		super.onResume();
		initView();
		InitData();
		// 得到摄像头数量
		int numCams = Camera.getNumberOfCameras();
		if (numCams > 0) {
			try {
				mCurrentCameraId = 0;
				// 启动摄像头类型(前置|后置)
				camera = Camera.open(mCurrentCameraId);
				// 开启预览画面
				camera.startPreview();
				preview.setCamera(camera);
				// 自动对焦
				preview.reAutoFocus();
			} catch (RuntimeException ex) {
				ex.printStackTrace();
				Toast.makeText(mContext, "未发现相机", Toast.LENGTH_LONG).show();
				setResult(0);
				finish();
			}
		}

	}

	@Override
	protected void onPause() {
		if (camera != null) {
			camera.stopPreview();
			preview.setCamera(null);
			camera.release();
			camera = null;
			preview.setNull();
		}
		super.onPause();

	}

	/**
	 * 重置
	 */
	private void resetCam() {
		camera.startPreview();
		preview.setCamera(camera);
	}

	/* 按快门瞬间会执行这里的代码 */
	ShutterCallback shutterCallback = new ShutterCallback() {
		public void onShutter() {
		}
	};

	/* 如需要处理 raw 则在这里 写代码 */
	PictureCallback rawCallback = new PictureCallback() {
		public void onPictureTaken(byte[] data, Camera camera) {
		}
	};

	// 当拍照后 存储 jpg 文件到 sd卡
	PictureCallback jpegCallback = new PictureCallback() {
		public void onPictureTaken(byte[] data, Camera camera) {
			new SaveImageTask(data).execute();
			resetCam();
		}
	};

	/**
	 * 触摸屏主要是对焦框
	 */
	@Override
	public boolean onTouch(View v, MotionEvent event) {
		try {
			if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
				preview.pointFocus(event);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		RelativeLayout.LayoutParams layout = new RelativeLayout.LayoutParams(
				focusIndex.getLayoutParams());
		layout.setMargins((int) event.getX() - 60, (int) event.getY() - 60, 0,
				0);

		focusIndex.setLayoutParams(layout);
		focusIndex.setVisibility(View.VISIBLE);

		ScaleAnimation sa = new ScaleAnimation(3f, 1f, 3f, 1f,
				ScaleAnimation.RELATIVE_TO_SELF, 0.5f,
				ScaleAnimation.RELATIVE_TO_SELF, 0.5f);
		sa.setDuration(800);
		focusIndex.startAnimation(sa);
		handler.postAtTime(new Runnable() {
			@Override
			public void run() {
				focusIndex.setVisibility(View.INVISIBLE);
			}
		}, 800);
		return false;
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.camera_flip_view:
			// 切换摄像头
			switchCamera();
			break;

		case R.id.flash_view:
			// 闪光灯
			turnLight(camera);
			break;

		case R.id.action_button:
			// 拍照
			takePhoto();
			break;
		}
	}

	// 处理拍摄的照片
	private class SaveImageTask extends AsyncTask<Void, Void, String> {
		private byte[] data;

		SaveImageTask(byte[] data) {
			this.data = data;
		}

		@Override
		protected String doInBackground(Void... params) {
			// Write to SD Local
			String path = "";
			try {
				path = saveToSDCard(data);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
			}
			return path;
		}

		@Override
		protected void onPostExecute(String path) {
			super.onPostExecute(path);
			if (!TextUtils.isEmpty(path)) {
				// 地址
				Log.d("DemoLog", "path=" + path);
				// 返回到上一级
				Intent intent = new Intent();
				intent.putExtra("path", path);
				setResult(ReqCode.REQ_UPLOAD_PHOTO_CAMERA, intent);
				finish();
			} else {
				Toast.makeText(getApplication(), "拍照失败，请稍后重试！",
						Toast.LENGTH_LONG).show();
				setResult(0);
				finish();
			}
		}
	}

	/**
	 * 将拍下来的照片存放在SD卡中
	 */
	@SuppressLint("NewApi")
	public String saveToSDCard(byte[] data) throws IOException {
		Bitmap croppedImage;
		// 获得图片大小
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;// 只读边,不读内容

		Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length,
				options);// 将图片读进来

		// PHOTO_SIZE = options.outHeight > options.outWidth ? options.outWidth
		// : options.outHeight;
		PHOTO_SIZE_W = options.outWidth;
		PHOTO_SIZE_H = options.outHeight;

		float hh = 800f;//
		float ww = 480f;//
		int be = 1;
		if (PHOTO_SIZE_W > PHOTO_SIZE_H && PHOTO_SIZE_W > ww) {
			be = (int) (PHOTO_SIZE_W / ww);
		} else if (PHOTO_SIZE_W < PHOTO_SIZE_H && PHOTO_SIZE_H > hh) {
			be = (int) (PHOTO_SIZE_H / hh);
		}
		if (be <= 0)
			be = 1;
		options.inSampleSize = be;// 设置采样率
		options.inPurgeable = true;// 同时设置才会有效
		options.inInputShareable = true;// 。当系统内存不够时候图片自动被回收
		options.inJustDecodeBounds = false;
		// 创建个bitmap(原始bitmap大图)
		bitmap = BitmapFactory.decodeByteArray(data, 0, data.length, options);

		// 旋转90度
		Matrix m = new Matrix();
		m.setRotate(90, bitmap.getWidth() / 2, bitmap.getHeight() / 2);
		if (mCurrentCameraId == 1) {
			m.postScale(1, -1);
		}
		// 终极图片
		Bitmap rotatedImage = Bitmap.createBitmap(bitmap, 0, 0,
				bitmap.getWidth(), bitmap.getHeight(), m, true);
		String imagePath = "";
		try {
			imagePath = saveToFile(rotatedImage);
		} catch (Exception e) {

		}
		// 释放掉
		rotatedImage.recycle();
		rotatedImage = null;
		return imagePath;
	}

	// 保存图片文件
	public static String saveToFile(Bitmap croppedImage) {
		// 图片缓存地址
		String addPhotsPath = null;
		try {
			addPhotsPath = FileUtil.getDiskCacheDir(String.valueOf("yulian"))
					.getAbsolutePath();
		} catch (Exception e) {
			e.printStackTrace();
		}
		// 保存到本地缓存
		BitmapUtils.compressBmpToFile(croppedImage, addPhotsPath);
		return addPhotsPath;
	}

	/**
	 * 闪光灯开关 开->关->自动
	 * 
	 * @param mCamera
	 */
	private void turnLight(Camera mCamera) {
		if (mCamera == null || mCamera.getParameters() == null
				|| mCamera.getParameters().getSupportedFlashModes() == null) {
			return;
		}
		Camera.Parameters parameters = mCamera.getParameters();
		String flashMode = mCamera.getParameters().getFlashMode();
		List<String> supportedModes = mCamera.getParameters()
				.getSupportedFlashModes();
		if (Camera.Parameters.FLASH_MODE_OFF.equals(flashMode)
				&& supportedModes.contains(Camera.Parameters.FLASH_MODE_ON)) {// 关闭状态
			parameters.setFlashMode(Camera.Parameters.FLASH_MODE_ON);
			mCamera.setParameters(parameters);
			flashBtn.setImageResource(R.drawable.camera_flash_on);
		} else if (Camera.Parameters.FLASH_MODE_ON.equals(flashMode)) {// 开启状态
			if (supportedModes.contains(Camera.Parameters.FLASH_MODE_AUTO)) {
				parameters.setFlashMode(Camera.Parameters.FLASH_MODE_AUTO);
				flashBtn.setImageResource(R.drawable.camera_flash_auto);
				mCamera.setParameters(parameters);
			} else if (supportedModes
					.contains(Camera.Parameters.FLASH_MODE_OFF)) {
				parameters.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);
				flashBtn.setImageResource(R.drawable.camera_flash_off);
				mCamera.setParameters(parameters);
			}
		} else if (Camera.Parameters.FLASH_MODE_AUTO.equals(flashMode)
				&& supportedModes.contains(Camera.Parameters.FLASH_MODE_OFF)) {
			parameters.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);
			mCamera.setParameters(parameters);
			flashBtn.setImageResource(R.drawable.camera_flash_off);
		}
	}

	// 切换前后置摄像头
	private void switchCamera() {
		mCurrentCameraId = (mCurrentCameraId + 1) % Camera.getNumberOfCameras();
		CLog.d(TAG, "mCurrentCameraId:" + mCurrentCameraId);
		if (camera != null) {
			camera.stopPreview();
			preview.setCamera(null);
			camera.setPreviewCallback(null);
			camera.release();
			camera = null;
		}
		try {
			camera = Camera.open(mCurrentCameraId);
			camera.setPreviewDisplay(mSurfaceView.getHolder());
			InitData();
			preview.setCamera(camera);
			camera.startPreview();
		} catch (Exception e) {
			e.printStackTrace();
			Toast.makeText(mContext, "未发现相机", Toast.LENGTH_LONG).show();
			setResult(0);
			finish();
		}

	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
			setResult(0);
			finish();
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}
}
