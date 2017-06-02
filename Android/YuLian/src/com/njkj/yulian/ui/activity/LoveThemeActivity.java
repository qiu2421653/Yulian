package com.njkj.yulian.ui.activity;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.provider.MediaStore;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.njkj.yulian.R;
import com.njkj.yulian.constant.ReqCode;
import com.njkj.yulian.controller.LoveContraller;
import com.njkj.yulian.controller.TakePhotoController;
import com.njkj.yulian.core.callback.SimpleCallback;
import com.njkj.yulian.entity.LoveEntity;
import com.njkj.yulian.entity.RetEntity;
import com.njkj.yulian.utils.AttPathUtils;
import com.njkj.yulian.utils.Bimp;
import com.njkj.yulian.utils.BitmapUtils;
import com.njkj.yulian.utils.FileUtilImage;

public class LoveThemeActivity extends BaseActivity implements OnClickListener {
	private LinearLayout lovetheme_img;
	private EditText lovetheme_center;
	private Button lovetheme_submit;
	private ImageView mylovetheme_img;
	private final int RESULT_LOAD_IMAGE = 1;
	private final int CUT_PHOTO_REQUEST_CODE = 2;
	protected String drr;// 图片裁剪以后的url地址路径
	private boolean type = false;
	LoveContraller contraller;
	Uri imageUri;// 图片路径
	private ArrayList<String> compresslist = new ArrayList<String>(); // 压缩后图片地址集合
	TakePhotoController takePhotoController;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_lovetheme);
		setHeaderTitle(R.string.lovetheme);
		setHeaderLeftText();
		initView();

	}

	private void initView() {
		contraller = new LoveContraller();
		lovetheme_img = (LinearLayout) findViewById(R.id.lovetheme_img);
		mylovetheme_img = (ImageView) findViewById(R.id.mylovetheme_img);
		lovetheme_center = (EditText) findViewById(R.id.lovetheme_center);
		lovetheme_submit = (Button) findViewById(R.id.lovetheme_submit);
		lovetheme_img.setOnClickListener(this);
		mylovetheme_img.setOnClickListener(this);
		lovetheme_submit.setOnClickListener(this);
		takePhotoController = new TakePhotoController();
	}

	@Override
	public void onClick(View v) {
		super.onClick(v);
		switch (v.getId()) {
		case R.id.lovetheme_submit:// 发布
			if (!"".equals(lovetheme_center.getText().toString())) {
				if (type == true) {
					if (!"".equals(lovetheme_center.getText().toString().trim()))
						compressImageFromFile();
				} else {
					showShortToast("请填写完详细信息后发布");
				}
			} else {
				showShortToast("请填写完详细信息后发布");
				break;
			}

			break;
		case R.id.mylovetheme_img:
			Intent i1 = new Intent(
					// 相册
					Intent.ACTION_PICK,
					android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
			startActivityForResult(i1, RESULT_LOAD_IMAGE);
			break;
		case R.id.lovetheme_img:// 上传图片
			Intent i = new Intent(
					// 相册
					Intent.ACTION_PICK,
					android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
			startActivityForResult(i, RESULT_LOAD_IMAGE);
			break;

		default:
			break;
		}
	}

	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		switch (requestCode) {
		case RESULT_LOAD_IMAGE:
			if (resultCode == RESULT_OK && null != data) {// 相册返回
				Uri uri = data.getData();
				if (uri != null) {
					startPhotoZoom(uri);
				}
				type = true;
			}
			break;
		case CUT_PHOTO_REQUEST_CODE:
			if (resultCode == RESULT_OK && null != data) {// 裁剪返回
				Bitmap bitmap = Bimp.getLoacalBitmap(drr);// 压缩图片
				mylovetheme_img.setImageBitmap(bitmap);
				type = true;
			}
			break;

		}
	}

	// 调用系统的图片剪裁工具
	protected void startPhotoZoom(Uri uri) {
		try {
			// 获取系统时间 然后将裁剪后的图片保存至指定的文件夹
			SimpleDateFormat sDateFormat = new SimpleDateFormat(
					"yyyyMMddhhmmss");
			String address = sDateFormat.format(new java.util.Date());
			if (!FileUtilImage.isFileExist("")) {
				FileUtilImage.createSDDir("");

			}
			drr = FileUtilImage.SDPATH + address + ".JPEG";
			imageUri = Uri.parse("file:///sdcard/formats/" + address + ".JPEG");

			final Intent intent = new Intent("com.android.camera.action.CROP");

			// 照片URL地址
			intent.setDataAndType(uri, "image/*");

			intent.putExtra("crop", "true");
			intent.putExtra("aspectX", 9);
			intent.putExtra("aspectY", 4);
			/*
			 * intent.putExtra("outputX", 1920); intent.putExtra("outputY",
			 * 855);
			 */
			// 输出路径
			intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
			// 输出格式
			intent.putExtra("outputFormat",
					Bitmap.CompressFormat.JPEG.toString());
			// 不启用人脸识别
			intent.putExtra("noFaceDetection", false);
			intent.putExtra("return-data", false);
			startActivityForResult(intent, CUT_PHOTO_REQUEST_CODE);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void getThemeSubmit() {
		String userID = mConfigDao.getString("userID");

		List<File> files = new ArrayList<File>();
		if (compresslist.size() > 0) {
			for (int j = 0; j < compresslist.size(); j++) {
				files.add(new File(compresslist.get(j).toString()));
			}
		}
		contraller.getSubmit(getString(R.string.FsCreateLove), files, userID,
				lovetheme_center.getText().toString().trim(),
				new SimpleCallback() {

					@Override
					public void onCallback(Object data) {
						if (data == null) {
							showShortToast(getString(R.string.error));
						} else {
							RetEntity<LoveEntity> entity = (RetEntity<LoveEntity>) data;
							if (entity.success) {
								showShortToast("发布成功");
								setResult(ReqCode.REQ_ADDTHEME);
								finish();
							} else {
								showShortToast(entity.exceptions.get(0).message);
							}
						}
						hideProgress();
					}
				});
	}

	// 压缩图片
	private void compressImageFromFile() {
		String path = takePhotoController.getFilePathFromUri(imageUri,
				LoveThemeActivity.this);
		showDialog();
		new compressImageTask().execute(path);
	}

	/**
	 * 压缩图片
	 * */
	private class compressImageTask extends AsyncTask<String, Void, Bitmap> {
		@Override
		protected Bitmap doInBackground(String... params) {
			// 地址
			String path = params[0];
			// 通过图片全地址压缩创建个bitMap
			return BitmapUtils.compressImageFromFile(path);
		}

		@Override
		protected void onPostExecute(Bitmap result) {
			super.onPostExecute(result);
			if (result == null) {
				return;
			}
			// 放到缓存里
			File path = AttPathUtils.getInternalDir();
			File file = new File(path, SystemClock.currentThreadTimeMillis()
					+ ".jpg");
			// 保存到本地缓存
			boolean compressBmpToFile = BitmapUtils.compressBmpToFile(result,
					file.getAbsolutePath().toString());
			if (compressBmpToFile) {
				compresslist.add(file.getAbsolutePath().toString());
			}
			if (compresslist.size() == 1) {
				handler.sendEmptyMessage(0);
			}
		}
	}

	Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			getThemeSubmit();
		}
	};

}
