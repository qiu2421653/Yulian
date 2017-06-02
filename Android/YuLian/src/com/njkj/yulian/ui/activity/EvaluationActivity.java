package com.njkj.yulian.ui.activity;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import com.njkj.yulian.R;
import com.njkj.yulian.entity.DrrEntity;
import com.njkj.yulian.ui.adapter.GridAdapter;
import com.njkj.yulian.utils.Bimp;
import com.njkj.yulian.utils.EvaluationUtils;

import android.app.Activity;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;

import android.provider.MediaStore;
import android.text.Editable;
import android.text.Selection;
import android.text.Spannable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.view.ViewTreeObserver.OnPreDrawListener;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.ScrollView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class EvaluationActivity extends BaseActivity implements OnItemClickListener {

	private GridView gridview;
	private GridAdapter adapter;

	private EditText comment_content;
	private String temp;
	private Button selectimg_bt_content_type, selectimg_bt_search;
	private ScrollView activity_selectimg_scrollView;
	private HorizontalScrollView selectimg_horizontalScrollView;

	private List<String> categoryList;
	private String key="purl";//拍照的url路径
	private float dp;

	public List<Bitmap> bmp = new ArrayList<Bitmap>();
	public List<DrrEntity> drr = new ArrayList<DrrEntity>();

	protected void onCreate(Bundle savedInstanceState) { 
		super.onCreate(savedInstanceState);
		setContentView(R.layout.evaluation_activity);
		setHeaderBackground(R.color.white);
		setHeaderTitle("评价");
		setHeaderLeftText();
		Init();
	}

	List<String> urList = new ArrayList<String>();

	public void Init() {
		dp = getResources().getDimension(R.dimen.dp);
		comment_content = (EditText) findViewById(R.id.comment_content);
		comment_content.setFocusable(true);
		comment_content.setFocusableInTouchMode(true); 


		selectimg_horizontalScrollView = (HorizontalScrollView) findViewById(R.id.selectimg_horizontalScrollView);
		gridview = (GridView) findViewById(R.id.noScrollgridview);
		gridview.setSelector(new ColorDrawable(Color.TRANSPARENT));
		gridviewInit();
		comment_content.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				// TODO Auto-generated method stub
				temp = s.toString();
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub

			}

			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub

			}
		});

		activity_selectimg_scrollView = (ScrollView) findViewById(R.id.activity_selectimg_scrollView);
		activity_selectimg_scrollView.setVerticalScrollBarEnabled(false);
	}

	public void gridviewInit() {
		adapter = new GridAdapter(this,bmp);
		adapter.setSelectedPosition(0);
		int size = 0;
		if (bmp.size() < 6) {
			size = bmp.size() + 1;
		} else {
			size = bmp.size();
		}
		LayoutParams params = gridview.getLayoutParams();
		final int width = size * (int) (dp * 9.4f);
		params.width = width;
		gridview.setLayoutParams(params);
		gridview.setColumnWidth((int) (dp * 9.4f));
		gridview.setStretchMode(GridView.NO_STRETCH);
		gridview.setNumColumns(size);
		gridview.setAdapter(adapter);
		gridview.setOnItemClickListener(this);

		selectimg_horizontalScrollView.getViewTreeObserver()
				.addOnPreDrawListener(// 绘制完毕
						new OnPreDrawListener() {
							public boolean onPreDraw() {
								selectimg_horizontalScrollView.scrollTo(width,
										0);
								selectimg_horizontalScrollView
										.getViewTreeObserver()
										.removeOnPreDrawListener(this);
								return false;
							}
						});
	}

	protected void onPause() {
		// TODO Auto-generated method stub
		// temp=comment_content.getText().toString().trim();
		super.onPause();
	}


	public class PopupWindows extends PopupWindow {

		public PopupWindows(Context mContext, View parent) {

			View view = View
					.inflate(mContext, R.layout.item_popupwindows, null);
			view.startAnimation(AnimationUtils.loadAnimation(mContext,
					R.anim.fade_ins));
			LinearLayout ll_popup = (LinearLayout) view
					.findViewById(R.id.ll_popup);

			setWidth(LayoutParams.FILL_PARENT);
			setHeight(LayoutParams.FILL_PARENT);
			setBackgroundDrawable(new BitmapDrawable());
			setFocusable(true);
			setOutsideTouchable(true);
			setContentView(view);
			showAtLocation(parent, Gravity.BOTTOM, 0, 0);
			update();

			Button bt1 = (Button) view
					.findViewById(R.id.item_popupwindows_camera);
			Button bt2 = (Button) view
					.findViewById(R.id.item_popupwindows_Photo);
			Button bt3 = (Button) view
					.findViewById(R.id.item_popupwindows_cancel);
			bt1.setOnClickListener(new OnClickListener() {
				public void onClick(View v) {
					photo();
					dismiss();
				}
			});
			bt2.setOnClickListener(new OnClickListener() {
				public void onClick(View v) {
					Intent i = new Intent(
							// 相册
							Intent.ACTION_PICK,
							android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
					startActivityForResult(i, RESULT_LOAD_IMAGE);
					dismiss();
				}
			});
			bt3.setOnClickListener(new OnClickListener() {
				public void onClick(View v) {
					dismiss();
				}
			});

		}
	}

	private static final int TAKE_PICTURE = 0;
	private static final int RESULT_LOAD_IMAGE = 1;
	private static final int CUT_PHOTO_REQUEST_CODE = 2;
	private static final int SELECTIMG_SEARCH = 3;
	private String path = "";
	private Uri photoUri;

	public void photo() {
		try {
			Intent openCameraIntent = new Intent(
					MediaStore.ACTION_IMAGE_CAPTURE);

			String sdcardState = Environment.getExternalStorageState();
			String sdcardPathDir = android.os.Environment
					.getExternalStorageDirectory().getPath() + "/tempImage/";
			File file = null;
			if (Environment.MEDIA_MOUNTED.equals(sdcardState)) {
				// 有sd卡，是否有myImage文件夹
				File fileDir = new File(sdcardPathDir);
				if (!fileDir.exists()) {
					fileDir.mkdirs();
				}
				// 是否有headImg文件
				file = new File(sdcardPathDir + System.currentTimeMillis()
						+ ".JPEG");
			}
			if (file != null) {
				path = file.getPath();
				photoUri = Uri.fromFile(file);
				inputSharePreference(key,photoUri.toString());
				openCameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);

				startActivityForResult(openCameraIntent, TAKE_PICTURE);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch (requestCode) {
		case TAKE_PICTURE:
			if (drr.size() < 6 && resultCode == -1) {// 拍照
				startPhotoZoom(Uri.parse(getSharePreference(key)));
			}
			break;
		case RESULT_LOAD_IMAGE:
			if (drr.size() < 6 && resultCode == RESULT_OK && null != data) {// 相册返回
				Uri uri = data.getData();
				if (uri != null) {
					startPhotoZoom(uri);
				}
			}
			break;
		case CUT_PHOTO_REQUEST_CODE:
			if (resultCode == RESULT_OK && null != data) {// 裁剪返回
				Bitmap bitmap = Bimp.getLoacalBitmap(drr.get(drr.size() - 1).getUrl());
				PhotoActivity.bitmap.add(bitmap);
				bitmap = Bimp.createFramedPhoto(480, 480, bitmap,
						(int) (dp * 1.6f));
				bmp.add(bitmap);
				gridviewInit(); 
			}
			break;
		case SELECTIMG_SEARCH:
			if (resultCode == RESULT_OK && null != data) {
				String text = "#" + data.getStringExtra("topic") + "#";
				text = comment_content.getText().toString() + text;
				comment_content.setText(text);

				comment_content.getViewTreeObserver().addOnPreDrawListener(
						new OnPreDrawListener() {
							public boolean onPreDraw() {
								comment_content.setEnabled(true);
								// 设置光标为末尾
								CharSequence cs = comment_content.getText();
								if (cs instanceof Spannable) {
									Spannable spanText = (Spannable) cs;
									Selection.setSelection(spanText,
											cs.length());
								}
								comment_content.getViewTreeObserver()
										.removeOnPreDrawListener(this);
								return false;
							}
						});

			}

			break;
		}
	}

	private void startPhotoZoom(Uri uri) {
		try {
			// 获取系统时间 然后将裁剪后的图片保存至指定的文件夹
			SimpleDateFormat sDateFormat = new SimpleDateFormat(
					"yyyyMMddhhmmss");
			String address = sDateFormat.format(new java.util.Date());
			if (!EvaluationUtils.isFileExist("")) {
				EvaluationUtils.createSDDir("");

			}
			DrrEntity entity=new DrrEntity();
			entity.setUrl(EvaluationUtils.SDPATH + address + ".JPEG");
			drr.add(entity);
			Uri imageUri = Uri.parse("file:///sdcard/formats/" + address
					+ ".JPEG");

			final Intent intent = new Intent("com.android.camera.action.CROP");

			// 照片URL地址
			intent.setDataAndType(uri, "image/*");

			intent.putExtra("crop", "true");
			intent.putExtra("aspectX", 1);
			intent.putExtra("aspectY", 1);
			intent.putExtra("outputX", 480);
			intent.putExtra("outputY", 480);
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

	protected void onDestroy() {
		EvaluationUtils.deleteDir(EvaluationUtils.SDPATH);
		EvaluationUtils.deleteDir(EvaluationUtils.SDPATH1);
		// 清理图片缓存
		for (int i = 0; i < bmp.size(); i++) {
			bmp.get(i).recycle();
		}
		for (int i = 0; i < PhotoActivity.bitmap.size(); i++) {
			PhotoActivity.bitmap.get(i).recycle();
		}
		PhotoActivity.bitmap.clear();
		bmp.clear();
		drr.clear();
		super.onDestroy();
	}

	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		((InputMethodManager) getSystemService(INPUT_METHOD_SERVICE))
				.hideSoftInputFromWindow(this
						.getCurrentFocus().getWindowToken(),
						InputMethodManager.HIDE_NOT_ALWAYS);
		if (arg2 == bmp.size()) {
			String sdcardState = Environment.getExternalStorageState();
			if (Environment.MEDIA_MOUNTED.equals(sdcardState)) {
				new PopupWindows(this, gridview);
			} else {
				Toast.makeText(getApplicationContext(), "sdcard已拔出，不能选择照片",
						Toast.LENGTH_SHORT).show();
			}
		} else {
			Intent intent = new Intent(this,
					PhotoActivity.class);

			intent.putExtra("ID", arg2);
			startActivity(intent);
		}
	}

}
