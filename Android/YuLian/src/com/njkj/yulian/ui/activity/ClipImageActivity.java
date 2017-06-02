package com.njkj.yulian.ui.activity;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.PixelFormat;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.NinePatchDrawable;
import android.media.ExifInterface;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.njkj.yulian.R;
import com.njkj.yulian.ui.activity.image.ImageEditActivity;
import com.njkj.yulian.ui.fragment.ExampleFragment;
import com.njkj.yulian.ui.fragment.ExampleFragment2;
import com.njkj.yulian.ui.fragment.ExampleFragment3;
import com.njkj.yulian.utils.CLog;

/**
 * 裁剪图片的Activity
 * 
 * @ClassName: CropImageActivity
 * @Description:
 * @author xiechengfa2000@163.com
 * @date 2015-5-8 下午3:39:22
 */
@SuppressLint("NewApi")
public class ClipImageActivity extends Activity implements OnClickListener {
	public static final String RESULT_PATH = "crop_image";
	private static final String KEY = "path";
	private static final String TAG = "ClipImageActivity";

	private TextView title_16, title_3, title_1;// 16:9,3:4,1:1
	Bitmap bitmap;
	ExampleFragment newFragment = new ExampleFragment();
	ExampleFragment2 newFragment2 = new ExampleFragment2();
	ExampleFragment3 newFragment3 = new ExampleFragment3();
	private int i = 1;

	public Bitmap getBitmap() {
		return bitmap;
	}

	public static void startActivity(Activity activity, String path, int code) {
		Intent intent = new Intent(activity, ClipImageActivity.class);
		intent.putExtra(KEY, path);
		activity.startActivityForResult(intent, code);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.crop_image_layout);
		title_16 = (TextView) findViewById(R.id.title_16);
		title_3 = (TextView) findViewById(R.id.title_3);
		title_1 = (TextView) findViewById(R.id.title_1);
		title_16.setOnClickListener(this);
		title_3.setOnClickListener(this);
		title_1.setOnClickListener(this);
		String path = getIntent().getStringExtra(KEY);

		// 有的系统返回的图片是旋转了，有的没有旋转，所以处理
		int degreee = readBitmapDegree(path);
		bitmap = createBitmap(path);
		if (bitmap != null) {
			if (degreee == 0) {
			} else {
				bitmap = rotateBitmap(degreee, bitmap);
			}
		} else {
			finish();
		}
		getFragmentManager().beginTransaction()
				.replace(R.id.fragment, newFragment) // 替换Fragment，实现切换
				.commit();
		findViewById(R.id.okBtn).setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.okBtn:
			Bitmap bitmap = null;
			if (i == 1) {
				bitmap = newFragment.getFragmentView().clip(
						newFragment.getFragmentHeight(),
						newFragment.text.getText().toString(),
						newFragment.text_fanyi.getText().toString(),
						newFragment.getTop());
			} else if (i == 2) {
				bitmap = newFragment2.getFragmentView2().clip(
						newFragment2.getFragmentHeight(),
						newFragment2.text.getText().toString(),
						newFragment2.text_fanyi.getText().toString(),
						newFragment2.getTop());
			} else if (i == 3) {
				bitmap = newFragment3.getFragmentView3().clip(
						newFragment3.getFragmentHeight(),
						newFragment3.text.getText().toString(),
						newFragment3.text_fanyi.getText().toString(),
						newFragment3.getTop());
			}

			String path = Environment
					.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
					+ "/" + "YuLian/yulian.jpg";
			saveBitmap(bitmap, path);
			Intent intent = new Intent();
			intent.putExtra(RESULT_PATH, path);
			if (i == 1)
				intent.putExtra("height", newFragment.getHeight() + "");
			else if (i == 2)
				intent.putExtra("height", newFragment2.getHeight() + "");
			else if (i == 3)
				intent.putExtra("height", newFragment3.getHeight() + "");
			CLog.e(TAG, "height:" + newFragment.getHeight());
			setResult(RESULT_OK, intent);
			finish();
			break;
		case R.id.title_16:
			i = 1;
			getFragmentManager().beginTransaction()
					.replace(R.id.fragment, newFragment) // 替换Fragment，实现切换
					.commit();
			break;
		case R.id.title_3:
			i = 2;
			getFragmentManager().beginTransaction()
					.replace(R.id.fragment, newFragment2) // 替换Fragment，实现切换
					.commit();
			break;
		case R.id.title_1:
			i = 3;
			getFragmentManager().beginTransaction()
					.replace(R.id.fragment, newFragment3) // 替换Fragment，实现切换
					.commit();
			break;
		default:
			break;
		}
	}

	Bitmap drawable2Bitmap(Drawable drawable) {
		if (drawable instanceof BitmapDrawable) {
			return ((BitmapDrawable) drawable).getBitmap();
		} else if (drawable instanceof NinePatchDrawable) {
			Bitmap bitmap = Bitmap
					.createBitmap(
							drawable.getIntrinsicWidth(),
							drawable.getIntrinsicHeight(),
							drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888
									: Bitmap.Config.RGB_565);
			Canvas canvas = new Canvas(bitmap);
			drawable.setBounds(0, 0, drawable.getIntrinsicWidth(),
					drawable.getIntrinsicHeight());
			drawable.draw(canvas);
			return bitmap;
		} else {
			return null;
		}
	}

	private void saveBitmap(Bitmap bitmap, String path) {
		File f = new File(path);
		if (f.exists()) {
			f.delete();
		}

		FileOutputStream fOut = null;
		try {
			f.createNewFile();
			fOut = new FileOutputStream(f);
			bitmap.compress(Bitmap.CompressFormat.PNG, 100, fOut);
			fOut.flush();
		} catch (IOException e1) {
			e1.printStackTrace();
		} finally {
			try {
				if (fOut != null)
					fOut.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 创建图片
	 * 
	 * @param path
	 * @return
	 */
	private Bitmap createBitmap(String path) {
		if (path == null) {
			return null;
		}

		BitmapFactory.Options opts = new BitmapFactory.Options();
		opts.inSampleSize = 1;
		opts.inJustDecodeBounds = false;// 这里一定要将其设置回false，因为之前我们将其设置成了true
		opts.inPurgeable = true;
		opts.inInputShareable = true;
		opts.inDither = false;
		opts.inPurgeable = true;
		FileInputStream is = null;
		Bitmap bitmap = null;
		try {
			is = new FileInputStream(path);
			bitmap = BitmapFactory.decodeFileDescriptor(is.getFD(), null, opts);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (is != null) {
					is.close();
					is = null;
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		return bitmap;
	}

	// 读取图像的旋转度
	private int readBitmapDegree(String path) {
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

	// 旋转图片
	private Bitmap rotateBitmap(int angle, Bitmap bitmap) {
		// 旋转图片 动作
		Matrix matrix = new Matrix();
		matrix.postRotate(angle);
		// 创建新的图片
		Bitmap resizedBitmap = Bitmap.createBitmap(bitmap, 0, 0,
				bitmap.getWidth(), bitmap.getHeight(), matrix, false);
		return resizedBitmap;
	}

}
