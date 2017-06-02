package com.njkj.yulian.ui.fragment;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.njkj.yulian.R;
import com.njkj.yulian.ui.activity.ClipImageLayout;
import com.njkj.yulian.ui.activity.ClipImageActivity;
import android.R.color;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;

@SuppressLint("NewApi")
public class ExampleFragment2 extends Fragment implements OnClickListener {
	View view;
	int width;
	Button addtext, right, xuanzhuan;
	ImageView imageview;
	LinearLayout bottom_text;
	ClipImageLayout clipImageLayout;
	public TextView text;
	public TextView text_fanyi;
	int top = 0;
	int z = 0;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// 获取屏幕大小
		WindowManager wm = getActivity().getWindowManager();
		width = wm.getDefaultDisplay().getWidth();
		int height = wm.getDefaultDisplay().getHeight();
		view = LayoutInflater.from(getActivity()).inflate(R.layout.num1, null);
		text = (TextView) view.findViewById(R.id.text);
		text_fanyi = (TextView) view.findViewById(R.id.text_fanyi);
		bottom_text=(LinearLayout) view.findViewById(R.id.bottom_text);
		Bitmap bitmap = ((ClipImageActivity)getActivity()).getBitmap();
		clipImageLayout = (ClipImageLayout) view.findViewById(R.id.num1);
		addtext = (Button) view.findViewById(R.id.addtext);
		right = (Button) view.findViewById(R.id.right);
		xuanzhuan = (Button) view.findViewById(R.id.xuanzhuan1);
		xuanzhuan.setOnClickListener(this);
		imageview = (ImageView) view.findViewById(R.id.imageview);
		imageview.setImageBitmap(bitmap);
		imageview.setLayoutParams(new FrameLayout.LayoutParams(
				FrameLayout.LayoutParams.WRAP_CONTENT, width / 3 * 4));
		imageview.setScaleType(ScaleType.CENTER_INSIDE);
		right.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				switch (event.getAction() & MotionEvent.ACTION_MASK) {
				case MotionEvent.ACTION_DOWN:
					clipImageLayout.setVisibility(View.GONE);
					imageview.setVisibility(View.VISIBLE);
					break;
				case MotionEvent.ACTION_UP:
					clipImageLayout.setVisibility(View.VISIBLE);
					imageview.setVisibility(View.GONE);
					break;
				default:
					break;
				}
				return false;
			}
		});
		addtext.setOnClickListener(this);
		clipImageLayout.setLayoutParams(new FrameLayout.LayoutParams(width,
				width / 3*4));
		top = width / 3*4 - 100;
		clipImageLayout.setImageBitmap(bitmap);
		bottom_text.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				float y = 0;
				switch (event.getAction() & MotionEvent.ACTION_MASK) {
				case MotionEvent.ACTION_DOWN:
					y = event.getRawY();
					break;
				case MotionEvent.ACTION_MOVE:
					moveViewByLayout(bottom_text, (int) event.getRawX(),
							(int) (event.getRawY() - y));
					Log.i("yyyyyy", event.getRawY() - y + "");
					break;
				case MotionEvent.ACTION_UP:
					z = (int) (y - event.getRawY());
					if (top == width / 3 * 4 - view.getHeight() - 1) {
						if (z > 0) {
							top = 0;
						} else {
							top = width / 3 * 4 - view.getHeight() - 1;
						}
					}
					break;
				default:
					break;
				}
				return true;
			}
		});
		return view;
	}
	public int getHeight(){
		return width/3*4;
	}
	
	// 获取状态栏高度

	public int getStatusBarHeight() {
		int result = 0;
		int resourceId = getResources().getIdentifier("status_bar_height",
				"dimen", "android");
		if (resourceId > 0) {
			result = getResources().getDimensionPixelSize(resourceId);
		}
		return result;
	}

	/**
	 * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
	 */
	public static int dip2px(Context context, float dpValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dpValue * scale + 0.5f);
	}

	public void moveViewByLayout(View view, int rawX, int rawY) {
		int left = 0;
		int shang = rawY - dip2px(getActivity(), getStatusBarHeight())
				- view.getHeight() / 2;
		if (shang > 1 && shang < (width / 3 * 4 - view.getHeight() - 1)) {
			int width = left + view.getWidth();
			int height = shang + view.getHeight();
			Log.i("xxxxxxx", shang + "");
			top = shang;
			Log.i("height", view.getHeight() + "");
			view.layout(left, shang, width, height);
		} else if (shang > width / 3 * 4 - view.getHeight() - 1) {
			top = width / 3 * 4 - view.getHeight() - 1;
		}
	}

	public int getTop() {
		return top;
	}

	public ClipImageLayout getFragmentView2() {
		return (ClipImageLayout) view.findViewById(R.id.num1);
	}

	public int getFragmentHeight() {
		return width / 3 * 4;
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.xuanzhuan1:
			this.getFragmentView2().xuanzhuan(width / 2, width / 3 * 4 / 2);
			break;
		default:
			addfont();
			break;
		}
	}

	private void addfont() {
		final EditText editText = new EditText(getActivity());
		new AlertDialog.Builder(getActivity()).setView(editText)
				.setPositiveButton("确定", new DialogInterface.OnClickListener() {
					@SuppressLint("NewApi")
					public void onClick(DialogInterface dialog, int which) {
						String string = editText.getText().toString();
						intitData(string);
					}
				}).show();
	}

	private String intitData(final String string) {
		String url ="http://api.fanyi.baidu.com/api/trans/vip/translate";
		String appid="20160416000018885";
		String salt=(int)(Math.random())+"";
		String sign=md5(appid+string+salt+"5oXaRp2IMXNlGKhnBVmX");
		RequestParams params = new RequestParams();
		params.put("q",string);
		params.put("from","auto");
		params.put("to","en");
		params.put("appid",appid);
		params.put("salt",salt);
		params.put("sign",sign);

		new AsyncHttpClient().post(url, params,new JsonHttpResponseHandler(){
			@Override
			public void onSuccess(int statusCode, Header[] headers,
					JSONObject response) {
				super.onSuccess(statusCode, headers, response);
				JSONObject obj;
				try {
					obj = new JSONObject(response.toString());
					JSONArray result=obj.getJSONArray("trans_result");
					text.setText(string);
					text_fanyi.setText(result.getJSONObject(0).getString("dst"));
				} catch (JSONException e) {
					e.printStackTrace();
				}				
			}
			@Override
			public void onFailure(int statusCode, Header[] headers,
					Throwable throwable, JSONObject errorResponse) {
				super.onFailure(statusCode, headers, throwable, errorResponse);
			}
		});
		return "";
	}
	
	public static String md5(String string) {
	    byte[] hash;
	    try {
	        hash = MessageDigest.getInstance("MD5").digest(string.getBytes("UTF-8"));
	    } catch (NoSuchAlgorithmException e) {
	        throw new RuntimeException("Huh, MD5 should be supported?", e);
	    } catch (UnsupportedEncodingException e) {
	        throw new RuntimeException("Huh, UTF-8 should be supported?", e);
	    }

	    StringBuilder hex = new StringBuilder(hash.length * 2);
	    for (byte b : hash) {
	        if ((b & 0xFF) < 0x10) hex.append("0");
	        hex.append(Integer.toHexString(b & 0xFF));
	    }
	    return hex.toString();
	}
}







