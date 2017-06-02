package com.njkj.yulian.ui.activity;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.njkj.yulian.R;
import com.njkj.yulian.controller.TakePhotoController;
import com.njkj.yulian.controller.UserController;
import com.njkj.yulian.core.callback.SimpleCallback;
import com.njkj.yulian.entity.RetEntity;
import com.njkj.yulian.entity.UserEntity;
import com.njkj.yulian.ui.gui.ActionSheet;
import com.njkj.yulian.ui.gui.ActionSheet.ActionSheetListener;
import com.njkj.yulian.ui.gui.ActionSheet.Builder;
import com.njkj.yulian.ui.gui.CircularImage;
import com.njkj.yulian.ui.gui.wheel.OnWheelChangedListener;
import com.njkj.yulian.ui.gui.wheel.WheelView;
import com.njkj.yulian.ui.gui.wheel.adapters.ArrayWheelAdapter;
import com.njkj.yulian.utils.Bimp;
import com.njkj.yulian.utils.FileUtilImage;
import com.squareup.picasso.Picasso;

public class PersonalActivity extends BaseActivity implements OnClickListener,
		OnFocusChangeListener {
	private TextView face_certification;// 去认证
	private CircularImage personal_img;// 头像
	private boolean FACE_type;
	private EditText personal_name, personal_age, personal_professional,
			personal_company, personal_school;// 昵称,年龄,职业,公司,地址,学校
	private TextView personal_address;
	protected final int TAKE_PICTURE = 1;// 照相
	protected final int RESULT_LOAD_IMAGE = 2;// 选图片
	protected final int CUT_PHOTO_REQUEST_CODE = 3;// 剪裁
	protected final int FACERESULTCODE = 4;// face++认证
	protected String drr;// 图片裁剪以后的url地址路径
	private View view;
	private Button btn_submit;
	private AddressPopUpWindow popwindow;
	private TextView personal_dj;// 等级
	private TextView personal_sex;// 性别
	private LinearLayout ll_nickName;
	private TextView personal_dqjf, personal_zjf;// 当前积分
	private ProgressBar pb_score;
	Uri imageUri;// 图片路径
	UserController userController;

	private Builder mSheetBuilderSex; // 性别弹窗
	private Builder mSheetBuilderPic; // 图片弹窗
	String sex = "0";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.personalcenter);
		setHeaderTitle(R.string.personal_center);
		setHeaderLeftText();
		initView();
		userController = new UserController();
		getUserInfo();
	}

	private void initView() {
		ll_nickName = (LinearLayout) findViewById(R.id.ll_nickName);
		btn_submit = (Button) findViewById(R.id.btn_submit);
		personal_dj = (TextView) findViewById(R.id.personal_dj);
		personal_dqjf = (TextView) findViewById(R.id.personal_dqjf);
		personal_zjf = (TextView) findViewById(R.id.personal_zjf);
		personal_img = (CircularImage) findViewById(R.id.personal_img);
		personal_name = (EditText) findViewById(R.id.personal_name);
		personal_sex = (TextView) findViewById(R.id.personal_sex);
		personal_age = (EditText) findViewById(R.id.personal_age);
		personal_professional = (EditText) findViewById(R.id.personal_professional);
		personal_company = (EditText) findViewById(R.id.personal_company);
		personal_address = (TextView) findViewById(R.id.personal_address);
		personal_school = (EditText) findViewById(R.id.personal_school);
		face_certification = (TextView) findViewById(R.id.face_certification);
		pb_score = (ProgressBar) findViewById(R.id.pb_score);

		personal_address.setOnClickListener(this);
		face_certification.setOnClickListener(this);
		personal_img.setOnClickListener(this);
		personal_sex.setOnClickListener(this);
		btn_submit.setOnClickListener(this);
		personal_name.setOnFocusChangeListener(this);

	}

	@Override
	public void onFocusChange(View v, boolean hasFocus) {
		if (hasFocus) {
			// 昵称获取焦点监听
			btn_submit.setVisibility(View.VISIBLE);
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_submit:
			if (FACE_type == true) {
				face_certification.setClickable(false);
				face_certification.setText("已认证");
			} else {
				face_certification.setClickable(true);
				face_certification.setText("未认证");
			}
			editUserInfo();// 提交编辑信息
			btn_submit.setVisibility(View.VISIBLE);
			break;
		case R.id.title_left1:
			finish();
			break;
		case R.id.personal_sex:
			// 性别
			showSexDialog();
			btn_submit.setVisibility(View.VISIBLE);
			break;
		case R.id.personal_img:// 头像
			String sdcardState = Environment.getExternalStorageState();
			if (Environment.MEDIA_MOUNTED.equals(sdcardState)) {
				// new PopupWindows(PersonalActivity.this, personal_img);
				showPicDialog();
			} else {
				Toast.makeText(getApplicationContext(), "sdcard已拔出，不能选择照片",
						Toast.LENGTH_SHORT).show();
			}
			btn_submit.setVisibility(View.VISIBLE);
			break;
		case R.id.face_certification:
			Intent intent = new Intent(PersonalActivity.this,
					CameraPreview.class);
			startActivityForResult(intent, FACERESULTCODE);
			break;
		case R.id.personal_address:
			popwindow = new AddressPopUpWindow(this, personal_address);
			break;
		case R.id.personaladdress_submit:// 确定
			personal_address.setText(mCurrentProviceName + mCurrentCityName
					+ mCurrentDistrictName);
			popwindow.dismiss();
			break;
		case R.id.personaladdress_qx:// 取消
			popwindow.dismiss();
			break;
		default:
			break;
		}
	}

	private String path = "";
	private Uri photoUri;

	// 照相
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
				openCameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);

				startActivityForResult(openCameraIntent, TAKE_PICTURE);
			}

		} catch (Exception e) {
			e.printStackTrace();
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

	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		switch (requestCode) {
		case TAKE_PICTURE:
			if (resultCode == -1) {// 拍照
				/*
				 * //取得返回的Uri,基本上选择照片的时候返回的是以Uri形式，但是在拍照中有得机子呢Uri是空的，所以要特别注意 Uri
				 * mImageCaptureUri = data.getData();
				 * //返回的Uri不为空时，那么图片信息数据都会在Uri中获得。如果为空，那么我们就进行下面的方式获取 if
				 * (mImageCaptureUri != null) { Bitmap image; try {
				 * //这个方法是根据Uri获取Bitmap图片的静态方法 image =
				 * MediaStore.Images.Media.getBitmap(this.getContentResolver(),
				 * mImageCaptureUri); if (image != null) {
				 * personal_img.setImageBitmap(image); } } catch (Exception e) {
				 * e.printStackTrace(); } } else { Bundle extras =
				 * data.getExtras(); if (extras != null) {
				 * //这里是有些拍照后的图片是直接存放到Bundle中的所以我们可以从这里面获取Bitmap图片 Bitmap image
				 * = extras.getParcelable("data"); if (image != null) {
				 * personal_img.setImageBitmap(image); } } }
				 */
				startPhotoZoom(photoUri);
			}
			break;
		case RESULT_LOAD_IMAGE:
			if (resultCode == RESULT_OK && null != data) {// 相册返回
				Uri uri = data.getData();
				if (uri != null) {
					startPhotoZoom(uri);
				}
			}
			break;
		case CUT_PHOTO_REQUEST_CODE:
			if (resultCode == RESULT_OK && null != data) {// 裁剪返回
				Bitmap bitmap = Bimp.getLoacalBitmap(drr);// 压缩图片
				System.out.println("drr:" + drr);
				personal_img.setImageBitmap(bitmap);
			}
			break;
		case FACERESULTCODE:
			if (resultCode == RESULT_OK) {
				showShortToast(data.getStringExtra("faceid"));
			}

			break;

		}
	}

	@Override
	protected void onDestroy() {
		FileUtilImage.deleteDir(FileUtilImage.SDPATH);
		FileUtilImage.deleteDir(FileUtilImage.SDPATH1);
		super.onDestroy();
	}

	public class AddressPopUpWindow extends PopupWindow implements
			OnWheelChangedListener {
		private WheelView mViewProvince;
		private WheelView mViewCity;
		private WheelView mViewDistrict;

		public AddressPopUpWindow(Context context, View parent) {
			view = View.inflate(context, R.layout.personal_address, null);
			view.startAnimation(AnimationUtils.loadAnimation(context,
					R.anim.fade_ins));
			view.findViewById(R.id.personaladdress_submit).setOnClickListener(
					PersonalActivity.this);
			view.findViewById(R.id.personaladdress_qx).setOnClickListener(
					PersonalActivity.this);
			setWidth(LayoutParams.FILL_PARENT);
			setHeight(LayoutParams.FILL_PARENT);
			setBackgroundDrawable(new BitmapDrawable());
			setFocusable(true);
			setOutsideTouchable(true);
			setContentView(view);
			showAtLocation(parent, Gravity.BOTTOM, 0, 0);
			update();
			setUpViews();
			setUpListener();
			setUpData();
		}

		private void setUpViews() {
			mViewProvince = (WheelView) view.findViewById(R.id.id_province);
			mViewCity = (WheelView) view.findViewById(R.id.id_city);
			mViewDistrict = (WheelView) view.findViewById(R.id.id_district);
		}

		private void setUpListener() {
			// 添加change事件
			mViewProvince.addChangingListener(this);
			// 添加change事件
			mViewCity.addChangingListener(this);
			// 添加change事件
			mViewDistrict.addChangingListener(this);
		}

		private void setUpData() {
			initProvinceDatas();
			mViewProvince.setViewAdapter(new ArrayWheelAdapter<String>(
					PersonalActivity.this, mProvinceDatas));
			// 设置可见条目数量
			mViewProvince.setVisibleItems(5);
			mViewCity.setVisibleItems(5);
			mViewDistrict.setVisibleItems(5);
			updateCities();
			updateAreas();
		}

		@Override
		public void onChanged(WheelView wheel, int oldValue, int newValue) {
			// TODO Auto-generated method stub
			if (wheel == mViewProvince) {
				updateCities();
			} else if (wheel == mViewCity) {
				updateAreas();
			} else if (wheel == mViewDistrict) {
				mCurrentDistrictName = (mDistrictDatasMap.get(mCurrentCityName)[newValue]);
				mCurrentZipCode = (mZipcodeDatasMap.get(mCurrentDistrictName));
			}
		}

		/**
		 * 根据当前的市，更新区WheelView的信息
		 */
		private void updateAreas() {
			int pCurrent = mViewCity.getCurrentItem();
			mCurrentCityName = mCitisDatasMap.get(mCurrentProviceName)[pCurrent];
			String[] areas = mDistrictDatasMap.get(mCurrentCityName);

			if (areas == null) {
				areas = new String[] { "" };
			}
			mViewDistrict.setViewAdapter(new ArrayWheelAdapter<String>(
					PersonalActivity.this, areas));
			mViewDistrict.setCurrentItem(0);
		}

		/**
		 * 根据当前的省，更新市WheelView的信息
		 */
		private void updateCities() {
			int pCurrent = mViewProvince.getCurrentItem();
			mCurrentProviceName = mProvinceDatas[pCurrent];
			String[] cities = mCitisDatasMap.get(mCurrentProviceName);
			if (cities == null) {
				cities = new String[] { "" };
			}
			mViewCity.setViewAdapter(new ArrayWheelAdapter<String>(
					PersonalActivity.this, cities));
			mViewCity.setCurrentItem(0);
			updateAreas();
		}
	}

	/** 获取用户信息 */
	private void getUserInfo() {
		String userID = mConfigDao.getString("userID");
		showDialog();
		userController.getUserInfo(getString(R.string.FsGetUserInfo), userID,
				new SimpleCallback() {
					@Override
					public void onCallback(Object data) {
						if (data == null) {
							showShortToast(getString(R.string.error));
						} else {
							RetEntity<UserEntity> entity = (RetEntity<UserEntity>) data;
							if (entity.success) {
								Picasso.with(mContext).load(entity.result.hPic)
										.placeholder(R.drawable.logo)
										.config(Bitmap.Config.RGB_565)
										.error(R.drawable.logo)
										.into(personal_img);

								pb_score.setMax(Integer
										.parseInt(entity.result.end));
								pb_score.setProgress(Integer
										.parseInt(entity.result.point));

								personal_dj.setText(entity.result.levelname);// 等级
								personal_dqjf
										.setText("(" + entity.result.point);// 当前积分
								personal_zjf.setText("/" + entity.result.end
										+ ")");// 总积分
								personal_name.setText(entity.result.name);// 昵称
								if ("1".equals(entity.result.sex)) {
									personal_sex.setText("男");// 性别
								} else if ("2".equals(entity.result.sex)) {
									personal_sex.setText("女");// 性别
								} else {
									personal_sex.setText("未知");// 性别
								}
								if ("true".equals(entity.result.face)) {
									FACE_type = true;
									face_certification.setClickable(false);
									face_certification.setText("已认证");
								} else {
									FACE_type = false;
									face_certification.setClickable(true);
									face_certification.setText("未认证");
								}
							} else {
								showShortToast(entity.exceptions.get(0).message);
							}
						}
						hideProgress();
					}
				});
	}

	// 弹出窗口选择图片
	private void showPicDialog() {
		if (mSheetBuilderPic == null) {
			// dialog弹窗
			mSheetBuilderPic = ActionSheet
					.createBuilder(this, getSupportFragmentManager())
					.setCancelButtonTitle("取消")
					.setOtherButtonTitles("拍照", "相册中选取")
					.setCancelableOnTouchOutside(true)
					.setListener(new ActionSheetListener() {
						// 选择的性别
						@Override
						public void onDismiss(ActionSheet actionSheet,
								boolean isCancel) {
						}

						@Override
						public void onOtherButtonClick(ActionSheet actionSheet,
								int index) {
							switch (index) {
							case 0:
								photo();
								break;
							case 1:
								Intent i = new Intent(
										// 相册
										Intent.ACTION_PICK,
										android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
								startActivityForResult(i, RESULT_LOAD_IMAGE);
								break;
							}
						}
					});
		}
		mSheetBuilderPic.show();
	}

	// 弹出窗口选择性别
	private void showSexDialog() {
		if (mSheetBuilderSex == null) {
			// dialog弹窗
			mSheetBuilderSex = ActionSheet
					.createBuilder(this, getSupportFragmentManager())
					.setCancelButtonTitle("取消").setOtherButtonTitles("男", "女")
					.setCancelableOnTouchOutside(true)
					.setListener(new ActionSheetListener() {
						// 选择的性别
						String selectSex = "";

						@Override
						public void onDismiss(ActionSheet actionSheet,
								boolean isCancel) {
						}

						@Override
						public void onOtherButtonClick(ActionSheet actionSheet,
								int index) {
							switch (index) {
							case 0:
								selectSex = "男";
								break;
							case 1:
								selectSex = "女";
								break;
							}
							personal_sex.setText(selectSex);
						}
					});
		}
		mSheetBuilderSex.show();
	}

	/** 编辑用户信息 */
	private void editUserInfo() {
		// TODO 异常
		String userID = mConfigDao.getString("userID");
		List<File> file = new ArrayList<File>();
		final TakePhotoController takePhotoController = new TakePhotoController();
		if (imageUri != null)
			file.add(new File(takePhotoController.getFilePathFromUri(imageUri,
					PersonalActivity.this)));
		showDialog();
		if ("".equals(personal_sex.getText().toString().trim()))
			sex = "0";
		else if ("男".equals(personal_sex.getText().toString().trim())) {
			sex = "1";
		} else if ("女".equals(personal_sex.getText().toString().trim())) {
			sex = "2";
		} else if ("未知".equals(personal_sex.getText().toString().trim())) {
			sex = "0";
		} else {
			sex = "0";
		}
		userController.editUserInfo(getString(R.string.FsEditUserInfo), userID,
				personal_name.getText().toString().trim(), sex, file,
				new SimpleCallback() {
					@Override
					public void onCallback(Object data) {
						if (data == null) {
							showShortToast(getString(R.string.error));
						} else {
							RetEntity<UserEntity> entity = (RetEntity<UserEntity>) data;
							if (entity.success) {
								showShortToast("信息提交成功");
								mConfigDao.setString("nickName", personal_name
										.getText().toString().trim());
								mConfigDao.setString("sex", sex);
								if(!TextUtils.isEmpty(entity.result.hPic)){
									mConfigDao.setString("hPic", entity.result.hPic);
								}
								btn_submit.setVisibility(View.GONE);
								ll_nickName.setFocusable(true);
								ll_nickName.setFocusableInTouchMode(true);
								ll_nickName.requestFocus();
							} else {
								showShortToast(entity.exceptions.get(0).message);
							}
						}
						hideProgress();
					}
				});
	}

	/**
	 * face++认证 FsValidFace
	 */
	private void faceCertification(String id) {
		String userID = mConfigDao.getString("userID");
		showDialog();
		userController.faceCertification(getString(R.string.FsValidFace),
				userID, id, new SimpleCallback() {

					@Override
					public void onCallback(Object data) {
						if (data == null) {
							showShortToast(getString(R.string.error));
						} else {
							RetEntity<UserEntity> entity = (RetEntity<UserEntity>) data;
							if (entity.success) {
								showShortToast("认证成功");
							} else {
								showShortToast(entity.exceptions.get(0).message);
							}
						}
						hideProgress();
					}
				});
	}

}
