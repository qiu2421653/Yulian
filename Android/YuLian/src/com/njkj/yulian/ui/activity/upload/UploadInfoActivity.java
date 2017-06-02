package com.njkj.yulian.ui.activity.upload;

import java.io.File;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.support.v4.view.ViewPager;
import android.text.Spannable;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.njkj.yulian.MainApplication;
import com.njkj.yulian.R;
import com.njkj.yulian.constant.ReqCode;
import com.njkj.yulian.controller.TopicController;
import com.njkj.yulian.core.callback.SimpleCallback;
import com.njkj.yulian.entity.LoveTagEntity;
import com.njkj.yulian.entity.RetEntity;
import com.njkj.yulian.entity.TagEntity;
import com.njkj.yulian.entity.UserEntity;
import com.njkj.yulian.ui.activity.BaseActivity;
import com.njkj.yulian.ui.activity.image.ImageEditActivity;
import com.njkj.yulian.ui.adapter.ExpressionAdapter;
import com.njkj.yulian.ui.adapter.ExpressionPagerAdapter;
import com.njkj.yulian.ui.gui.ExpandGridView;
import com.njkj.yulian.utils.AttPathUtils;
import com.njkj.yulian.utils.BitmapUtils;
import com.njkj.yulian.utils.CLog;
import com.njkj.yulian.utils.DevUtils;
import com.njkj.yulian.utils.DisplayUtils;
import com.njkj.yulian.utils.SmileUtils;
import com.squareup.picasso.Picasso;

/**
 * ==============================
 * 
 * @author Qiu
 * 
 * @Package com.naga.love.ui.activity.info
 * 
 * @Description:上传信息页
 * 
 * @date 2016-3-17 上午9:42:14
 * 
 * @version 1.0 ==============================
 */
public class UploadInfoActivity extends BaseActivity {
	protected static final String TAG = "UploadInfoActivity";

	public static final Integer FILTER = 101;//

	private static final int SUCCESS = 1;

	private ImageView iv_picture, iv_emoji, iv_tag; // 选择图片

	private View more; // 更多选择

	private EditText et_contant; // 内容区域

	private ViewPager expressionViewpager;// 表情viewpager
	private LinearLayout emojiIconContainer;
	private LinearLayout picContainer;

	private InputMethodManager manager; // 输入法管理

	private ArrayList<String> reslist; // 表情list表情

	private ArrayList<LoveTagEntity> taglist; // 标签list表情地址集合

	private ArrayList<String> piclist = new ArrayList<String>(); // 图片地址集合

	private ArrayList<String> compresslist = new ArrayList<String>(); // 压缩后图片地址集合

	private View addPic; // 追加图片

	private int position = 0;// 图片对应集合中的位置

	private TagEntity tag;// 打的标签
	private String content = "";// 回复的内容

	TopicController topicController;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_upload_info);
		setHeaderTitle(R.string.app_name);
		setHeaderRightText(R.string._sendinfo);
		setHeaderLeftText();
		initViews();
		initData();
		initOnClick();

	}

	protected void initViews() {
		iv_picture = (ImageView) findViewById(R.id.iv_picture);
		iv_emoji = (ImageView) findViewById(R.id.iv_emoji);
		iv_tag = (ImageView) findViewById(R.id.iv_tag);

		et_contant = (EditText) findViewById(R.id.et_contant);
		more = findViewById(R.id.more);
		// 表情符号
		expressionViewpager = (ViewPager) findViewById(R.id.vPager);
		emojiIconContainer = (LinearLayout) findViewById(R.id.ll_face_container);
		// 图片内容
		picContainer = (LinearLayout) findViewById(R.id.ll_pic_container);
	}

	protected void initData() {
		topicController = new TopicController();
		String path = getIntent().getStringExtra("path");
		if (!TextUtils.isEmpty(path)) {
			piclist.add(position, path);
			onSel(piclist);
			showPicture();
		}
		manager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
		findViewById(R.id.title_right).setBackgroundColor(Color.TRANSPARENT);
		// 设置表情信息
		setEmojiInfos();
		// 设置标签信息
		// setTags();
		// 获取可用标签
		// getUserTags();
	}

	protected void initOnClick() {
		iv_picture.setOnClickListener(this);
		iv_emoji.setOnClickListener(this);
		et_contant.setOnClickListener(this);
		iv_tag.setOnClickListener(this);
	}

	@Override
	public void onClick(View view) {
		if (view.getId() == R.id.title_left1) {
			// 取消
			finish();
		} else if (view.getId() == R.id.title_right_text) {
			// 发送
			content = et_contant.getText().toString();
			compresslist.clear();
			if (!TextUtils.isEmpty(content)) {
				if (tag != null) {
					if (piclist.size() == 0) {
						showShortToast("请选择一张图片");
					} else {
						// 压缩图片
						compressImageFromFile();
					}
				} else {
					if (piclist.size() == 0) {
						upLoadImage();
					} else {
						// 压缩图片
						compressImageFromFile();
					}
				}
			} else {
				showShortToast("请输入文章内容");
			}
		} else if (view.getId() == R.id.iv_picture) {
			// 显示选择图片
			// 如果当前图片集合!=null&&>0,则显示图片
			if (piclist != null && piclist.size() > 0) {
				more();
			} else {
				hideKeyboard();
				// 如果没有图片的话,跳转到图片选择页(传入当前图片数)
				Intent intent = new Intent(getApplicationContext(),
						PicSelActivity.class);
				intent.putExtra("picCount",
						piclist == null ? 0 : piclist.size());
				startActivityForResult(intent,
						PicSelActivity.RESULT_PICS_OK_CODE);
			}
		} else if (view.getId() == R.id.iv_emoji) {
			// 点击显示表情框
			more.setVisibility(View.VISIBLE);
			// 图片的
			picContainer.setVisibility(View.GONE);
			// 表情栏
			emojiIconContainer.setVisibility(View.VISIBLE);
			// loveTag.setVisibility(View.GONE);
			hideKeyboard();
		} else if (view.getId() == R.id.et_contant) {
			// 下方的表情|图片栏不显示;
			more.setVisibility(View.GONE);
			iv_emoji.setVisibility(View.VISIBLE);
			emojiIconContainer.setVisibility(View.GONE);
			picContainer.setVisibility(View.GONE);
		} else if (view.getId() == R.id.et_contant) {
			// 点击文字输入框
			more.setVisibility(View.GONE);
			iv_emoji.setVisibility(View.VISIBLE);
		} else if (view.getId() == R.id.iv_tag) {
			// 点击标签页
			// more.setVisibility(View.VISIBLE);
			iv_emoji.setVisibility(View.VISIBLE);
			picContainer.setVisibility(View.GONE);
			// 图片的
			// 表情栏
			emojiIconContainer.setVisibility(View.GONE);
			// loveTag.setVisibility(View.VISIBLE);
			hideKeyboard();
			startActivityForResult(new Intent(mContext, AddTagActivity.class),
					ReqCode.REQ_ADDTAG);

		}
	}

	/**
	 * 显示或隐藏图片显示页
	 * 
	 * @param view
	 */
	private void more() {
		if (more.getVisibility() == View.GONE) {
			hideKeyboard();
			iv_emoji.setVisibility(View.VISIBLE);
			more.setVisibility(View.VISIBLE);
			// loveTag.setVisibility(View.GONE);
			picContainer.setVisibility(View.VISIBLE);
			emojiIconContainer.setVisibility(View.GONE);
		} else {
			if (emojiIconContainer.getVisibility() == View.VISIBLE) {
				emojiIconContainer.setVisibility(View.GONE);
				picContainer.setVisibility(View.VISIBLE);
				iv_emoji.setVisibility(View.VISIBLE);
			} else {
				more.setVisibility(View.GONE);
			}
		}
	}

	// 显示图片展示页
	private void showPicture() {
		hideKeyboard();
		more.setVisibility(View.VISIBLE);
		picContainer.setVisibility(View.VISIBLE);
		emojiIconContainer.setVisibility(View.GONE);
	}

	// 设置表情信息
	private void setEmojiInfos() {
		// 表情list
		reslist = getExpressionRes(35);
		// 初始化表情viewpager
		List<View> views = new ArrayList<View>();
		View gv1 = getGridChildView(1);
		View gv2 = getGridChildView(2);
		views.add(gv1);
		views.add(gv2);
		expressionViewpager.setAdapter(new ExpressionPagerAdapter(views));
		et_contant.requestFocus();
	}

	// 表情文件名
	public ArrayList<String> getExpressionRes(int getSum) {
		ArrayList reslist = new ArrayList<String>();
		for (int x = 1; x <= getSum; x++) {
			String filename = "ee_" + x;
			reslist.add(filename);
		}
		return reslist;
	}

	/**
	 * 获取表情的gridview的子view
	 * 
	 * @param i
	 * @return
	 */
	private View getGridChildView(int i) {
		View view = View.inflate(this, R.layout.expression_gridview, null);
		ExpandGridView gv = (ExpandGridView) view.findViewById(R.id.gridview);
		List<String> list = new ArrayList<String>();
		if (i == 1) {
			List<String> list1 = reslist.subList(0, 20);
			list.addAll(list1);
		} else if (i == 2) {
			list.addAll(reslist.subList(20, reslist.size()));
		}
		list.add("delete_expression");
		final ExpressionAdapter expressionAdapter = new ExpressionAdapter(this,
				1, list);
		gv.setAdapter(expressionAdapter);
		gv.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				String filename = expressionAdapter.getItem(position);
				CLog.e(TAG, "filename:" + filename);
				try {
					// 不是删除键，显示表情
					if (filename != "delete_expression") {
						// 这里用的反射，所以混淆的时候不要混淆SmileUtils这个类
						@SuppressWarnings("rawtypes")
						Class clz = Class
								.forName("com.njkj.yulian.utils.SmileUtils");
						Field field = clz.getField(filename);
						// 添加到EditText
						Spannable smiledText = SmileUtils.getSmiledText(
								UploadInfoActivity.this,
								(String) field.get(null));
						CLog.e(TAG, "smiledText:" + smiledText);

						// 显示这个,发送应该用filename??
						et_contant.append(smiledText);
					} else { // 删除文字或者表情
						if (!TextUtils.isEmpty(et_contant.getText())) {
							int selectionStart = et_contant.getSelectionStart();// 获取光标的位置
							if (selectionStart > 0) {
								String body = et_contant.getText().toString();
								String tempStr = body.substring(0,
										selectionStart);
								int i = tempStr.lastIndexOf("[");// 获取最后一个表情的位置
								if (i != -1) {
									CharSequence cs = tempStr.substring(i,
											selectionStart);
									if (SmileUtils.containsKey(cs.toString()))
										et_contant.getEditableText().delete(i,
												selectionStart);
									else
										et_contant.getEditableText().delete(
												selectionStart - 1,
												selectionStart);
								} else {
									et_contant.getEditableText().delete(
											selectionStart - 1, selectionStart);
								}
							}
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		return view;
	}

	// TODO 给LinearLayout 添加子视图(图片)
	protected void onSel(final ArrayList<String> mSelectedImage) {
		if (mSelectedImage == null) {
			return;
		}
		picContainer.removeAllViews();
		for (int i = 0; i < mSelectedImage.size(); i++) {
			final String uri = mSelectedImage.get(i);
			// 添加图片视图(加入的图片)
			View child = getLayoutInflater().inflate(
					R.layout.activity_picsel_bottom_item, null);
			ImageView img = (ImageView) child.findViewById(R.id.id_item_image);

			Picasso.with(MainApplication.getContext())
					.load("file://" + uri)
					.placeholder(R.drawable.empty_photo)
					.config(Bitmap.Config.RGB_565)
					.resize(DevUtils.dip2px(MainApplication.getContext(), 120),
							DevUtils.dip2px(MainApplication.getContext(), 120))
					.centerCrop().error(R.drawable.empty_photo).into(img);
			// 父布局
			LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
					DisplayUtils.dip2px(77), DisplayUtils.dip2px(77));
			child.setLayoutParams(params);

			RelativeLayout.LayoutParams paramsr = new RelativeLayout.LayoutParams(
					DisplayUtils.dip2px(57), DisplayUtils.dip2px(57));
			paramsr.topMargin = DisplayUtils.dip2px(10);
			paramsr.leftMargin = DisplayUtils.dip2px(10);
			img.setLayoutParams(paramsr);
			img.setId(i);
			img.setTag(uri);
			child.findViewById(R.id.id_item_close).setOnClickListener(
					new OnClickListener() {
						@Override
						public void onClick(View view) {
							// 从父控件中移除
							picContainer.removeView((View) view.getParent());
							// 集合中移除
							mSelectedImage.remove(uri);
							if (addPic == null) {
								// 添加追加图片按钮
								addButton(mSelectedImage);
							}
						}
					});
			img.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					position = v.getId();
					// TODO 点击图片进行编辑
					Intent intent = new Intent(mContext,
							ImageEditActivity.class);
					intent.putExtra("path", String.valueOf(v.getTag()));
					startActivityForResult(intent, UploadInfoActivity.FILTER);
				}
			});
			// 添加到LinearLayout里去
			picContainer.addView(child);
		}
		// 添加追加图片按钮
		addButton(mSelectedImage);
	}

	// 追加添加图片按钮
	private void addButton(ArrayList<String> mSelectedImage) {
		if (mSelectedImage.size() < 6) {
			addPic = getLayoutInflater().inflate(
					R.layout.activity_picsel_sel_item, null);
			RelativeLayout.LayoutParams paramsr = new RelativeLayout.LayoutParams(
					DisplayUtils.dip2px(57), DisplayUtils.dip2px(57));
			paramsr.topMargin = DisplayUtils.dip2px(10);
			paramsr.leftMargin = DisplayUtils.dip2px(10);
			addPic.setLayoutParams(paramsr);
			picContainer.addView(addPic);
			addPic.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					// TODO 应该把图片地址带过去;需要到图片选择页面判断追加
					Intent intent = new Intent(mContext, PicSelActivity.class);
					intent.putExtra("piclist", piclist);
					startActivityForResult(intent,
							PicSelActivity.RESULT_PICS_OK_CODE);
				}
			});
		} else {
			addPic = null;
		}
	}

	Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			upLoadImage();
		}
	};

	/**
	 * 上传图片到服务器
	 * */
	private void upLoadImage() {
		showDialog();
		String userId = mConfigDao.getString("userID");
		String code = tag == null ? "" : tag.code;
		List<File> files = new ArrayList<File>();
		if (compresslist.size() > 0) {
			for (int j = 0; j < compresslist.size(); j++) {
				files.add(new File(compresslist.get(j).toString()));
			}
		}
		topicController.upLoadImage(getString(R.string.FsSetTopic), files,
				userId, content, code, new SimpleCallback() {
					@Override
					public void onCallback(Object data) {
						if (data == null) {
							showShortToast(getResources().getString(
									R.string.error));
						} else {
							RetEntity<UserEntity> entity = (RetEntity<UserEntity>) data;
							if (entity.success) {
								showShortToast("上传成功");
								finish();
							} else {
								showShortToast(entity.getErrorMsg());
							}
						}
						hideProgress();
					}
				});
	}

	// 压缩图片(启动了6个子线程)
	private void compressImageFromFile() {
		if (piclist != null && piclist.size() > 0) {
			for (int i = 0; i < piclist.size(); i++) {
				showDialog();
				hideKeyboard();
				new compressImageTask().execute(piclist.get(i));
			}
		}
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
			if (compresslist.size() == piclist.size()) {
				handler.sendEmptyMessage(SUCCESS);
			}
		}
	}

	/**
	 * 隐藏软键盘
	 */
	private void hideKeyboard() {
		if (getWindow().getAttributes().softInputMode != WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN) {
			if (getCurrentFocus() != null)
				manager.hideSoftInputFromWindow(getCurrentFocus()
						.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == Activity.RESULT_CANCELED) {
			return;
		}
		if (resultCode == PicSelActivity.RESULT_PICS_OK_CODE) {
			if (data != null) {
				piclist = (ArrayList<String>) data
						.getSerializableExtra(PicSelActivity.RESULT_PICS_ARRAY);
				if (piclist != null) {
					if (piclist.size() > 0) {
						showShortToast("共选择了:" + piclist.size() + "张图片");
						onSel(piclist);
						showPicture();
					} else {
						showShortToast("未选择图片");
					}
				} else {
					showShortToast("未选择图片");
				}

			}
		} else if (resultCode == UploadInfoActivity.FILTER) {
			// 图片滤镜处理
			// 拿到返回的图片地址
			String path = data.getStringExtra("path");
			piclist.remove(position);
			piclist.add(position, path);
			onSel(piclist);
			showPicture();
		} else if (resultCode == ReqCode.REQ_ADDTAG) {
			// 添加标签
			tag = (TagEntity) data.getSerializableExtra("tag");
		}
	}

}
