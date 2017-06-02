package com.njkj.yulian.ui.activity;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.hardware.Camera;
import android.os.Handler;
import android.os.HandlerThread;
import android.view.SurfaceView;
import android.widget.Button;
import android.widget.Toast;

import com.faceplusplus.api.FaceDetecter;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.njkj.yulian.utils.FaceMask;
import com.njkj.yulian.utils.UrlUtils;

public class CameraBaseActivity extends BaseActivity {
	SurfaceView camerasurface = null;
	FaceMask mask = null;
	Camera camera = null;
	HandlerThread handleThread = null;
	Handler detectHandler = null;
	Runnable detectRunnalbe = null;
	int width = 480;
	int height = 320;
	FaceDetecter facedetecter = null;
	Button but;
	byte[] ori1;
	String session_id,face_id;
	ProgressDialog dialog;
	/**
	 * face++创建person方法
	 * 
	 * @param api_key
	 * @param api_secret
	 * @param personName
	 * @param groupName
	 */
	public void createPersonHttp(String api_key, String api_secret) {
		AsyncHttpClient client = new AsyncHttpClient();
		RequestParams params = new RequestParams();
		params.put("api_key", api_key);
		params.put("api_secret", api_secret);
		params.put("tag", UrlUtils.TAG);
		params.put("person_name", UrlUtils.PSERSONNAME);

		client.post(UrlUtils.httpPersonPath, params,
				new JsonHttpResponseHandler() {
			@Override
			public void onSuccess(int statusCode, Header[] headers,
					JSONObject response) {
				super.onSuccess(statusCode, headers, response);
				if(statusCode==200){
					addPersonHttp(UrlUtils.API_KEY,UrlUtils.API_SECRET,face_id,UrlUtils.PSERSONNAME);//face_id,person_name
				}
			}
				@Override
				public void onFailure(int statusCode, Header[] headers,
								Throwable throwable, JSONObject errorResponse) {
							super.onFailure(statusCode, headers, throwable, errorResponse);
							if(statusCode==453){
								addPersonHttp(UrlUtils.API_KEY,UrlUtils.API_SECRET,face_id,UrlUtils.PSERSONNAME);
							}else{
								if(dialog.isShowing()){
									dialog.dismiss();
								}
								Toast.makeText(CameraBaseActivity.this, "服务器异常,请稍后重试",
									Toast.LENGTH_SHORT).show();
							}
						}
				});

	}
	/**
	 * 删除已经创建的person——name
	 *
	 *
	 */
	
	public void deletePersonHttp() {
		AsyncHttpClient client = new AsyncHttpClient();
		RequestParams params = new RequestParams();
		params.put("api_key", UrlUtils.API_KEY);
		params.put("api_secret", UrlUtils.API_SECRET);
		params.put("person_name", UrlUtils.PSERSONNAME);
		client.post(UrlUtils.delete, params,
				new AsyncHttpResponseHandler() {
					
					@Override
					public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
							deleteSharePreference();
							Toast.makeText(CameraBaseActivity.this, "删除成功",
									Toast.LENGTH_SHORT).show();
							dialog.dismiss();
					}
					
					@Override
					public void onFailure(int arg0, Header[] arg1, byte[] arg2, Throwable arg3) {
						dialog.dismiss();
						Toast.makeText(CameraBaseActivity.this, "服务器异常,请稍后重试",
								Toast.LENGTH_SHORT).show();
					}
					@Override
					public void onProgress(int bytesWritten, int totalSize) {
						super.onProgress(bytesWritten, totalSize);
							dialog.show();
							}
				});

	}
	/**
	 * 
	 * 录入人脸，供下次登陆进行校验
	 * @param api_key
	 * @param api_secret
	 * @param personName
	 * @param groupName
	 */
	public void addPersonHttp(String api_key, String api_secret,
			String face_id, String person_name) {
		AsyncHttpClient client = new AsyncHttpClient();
		RequestParams params = new RequestParams();
		params.put("api_key", api_key);
		params.put("api_secret", api_secret);
		params.put("face_id", face_id);
		params.put("person_name",person_name);
		client.post(UrlUtils.ADDPERSON, params,
				new JsonHttpResponseHandler() {
			@Override
			public void onSuccess(int statusCode, Header[] headers,
					JSONObject response) {
				super.onSuccess(statusCode, headers, response);
				if(statusCode==200){
						trainVerify(UrlUtils.API_KEY,UrlUtils.API_SECRET);
				}
			}
				@Override
						public void onFailure(int statusCode, Header[] headers,
								Throwable throwable, JSONObject errorResponse) {
							super.onFailure(statusCode, headers, throwable, errorResponse);
							if(dialog.isShowing()){
								dialog.dismiss();
							}
							Toast.makeText(CameraBaseActivity.this, "服务器异常,请稍后重试",
									Toast.LENGTH_SHORT).show();
						}
				});

	}
	/**
	 * 
	 * 开始训练
	 * @param api_key
	 * @param api_secret
	 * @param personName
	 */
	public void trainVerify(String api_key, String api_secret) {
		AsyncHttpClient client = new AsyncHttpClient();
		RequestParams params = new RequestParams();
		params.put("api_key", api_key);
		params.put("api_secret", api_secret);
		params.put("person_name", UrlUtils.PSERSONNAME);

		client.post(UrlUtils.TRAINVERIFY, params,
				new JsonHttpResponseHandler() {
			@Override
			public void onSuccess(int statusCode, Header[] headers,
					JSONObject response) {
				super.onSuccess(statusCode, headers, response);
				if(statusCode==200){
				 getPserson(UrlUtils.API_KEY,UrlUtils.API_SECRET);
				}
			}
             @Override
            		public void onFailure(int statusCode, Header[] headers,
            				Throwable throwable, JSONObject errorResponse) {
            			super.onFailure(statusCode, headers, throwable, errorResponse);
            			if(dialog.isShowing()){
            				dialog.dismiss();
            			}
            			Toast.makeText(CameraBaseActivity.this, "服务器异常,请稍后重试",
								Toast.LENGTH_SHORT).show();
            		}
				});

	}
	/**
	 * 
	 * 结束训练
	 * @param api_key
	 * @param api_secret
	 * @param session_id
	 */
	public void getPserson(String api_key, String api_secret) {
		AsyncHttpClient client = new AsyncHttpClient();
		RequestParams params = new RequestParams();
		params.put("api_key", api_key);
		params.put("api_secret", api_secret);
		params.put("session_id", session_id);

		client.post(UrlUtils.GETSESSION, params,
				new AsyncHttpResponseHandler() {
					@Override
					public void onSuccess(int arg0, Header[] arg1, byte[] data) {
						inputSharePreference();
						if(dialog.isShowing()){
							dialog.dismiss();
						}
							Toast.makeText(CameraBaseActivity.this, "录入信息成功",
									Toast.LENGTH_SHORT).show();
							finish();
					}

					@Override
					public void onFailure(int error, Header[] arg1,
							byte[] arg2, Throwable arg3) {
						if(dialog.isShowing()){
							dialog.dismiss();
						}
						Toast.makeText(CameraBaseActivity.this, "服务器异常,请稍后重试",
								Toast.LENGTH_SHORT).show();
					}
				});

	}
	
	/**
	 * 
	 * 验证
	 * @param api_key
	 * @param api_secret
	 * @param session_id
	 */
	public void recongntion(String api_key, String api_secret) {
		AsyncHttpClient client = new AsyncHttpClient();
		RequestParams params = new RequestParams();
		params.put("api_key", api_key);
		params.put("api_secret", api_secret);
		params.put("face_id", face_id);
		params.put("person_name", UrlUtils.PSERSONNAME);

		client.post(UrlUtils.RECONGNTION, params,
				new JsonHttpResponseHandler() {
			@Override
			public void onSuccess(int statusCode, Header[] headers,
					JSONObject response) {
				super.onSuccess(statusCode, headers, response);
				try {
					JSONObject obj = new JSONObject(response.toString());
					boolean type=obj.getBoolean("is_same_person");
					String xx=obj.getString("confidence");
					if(dialog.isShowing()){
						dialog.dismiss();
					}
					inputSharePreference();
					 Intent intent = new Intent();  
		             intent.putExtra("type", type);  
		             setResult(1,intent);  
		             finish();
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
			   @Override
					public void onFailure(int statusCode, Header[] headers,
							Throwable throwable, JSONObject errorResponse) {
						super.onFailure(statusCode, headers, throwable, errorResponse);
						if(dialog.isShowing()){
							dialog.dismiss();
						}
						Toast.makeText(CameraBaseActivity.this, "服务器异常,请稍后重试",
								Toast.LENGTH_SHORT).show();
					}
				});

	}

	// 保存图片
	public static void saveFile(Bitmap bm, String fileName, String path)
			throws IOException {
		String subForder = UrlUtils.SAVE_REAL_PATH + path;
		File foder = new File(subForder);
		if (!foder.exists()) {
			foder.mkdirs();
		}
		File myCaptureFile = new File(subForder, fileName);
		if (!myCaptureFile.exists()) {
			myCaptureFile.createNewFile();
		}
		BufferedOutputStream bos = new BufferedOutputStream(
				new FileOutputStream(myCaptureFile));
		bm.compress(Bitmap.CompressFormat.JPEG, 80, bos);
		bos.flush();
		bos.close();
	}

	/**
	 * 
	 * 将string类型data数据转换成rgb类型
	 * 
	 * @param data
	 * @param width
	 * @param height
	 * @return
	 */
	public static Bitmap rawByteArray2RGBABitmap2(byte[] data, int width,
			int height) {
		int frameSize = width * height;
		int[] rgba = new int[frameSize];

		for (int i = 0; i < height; i++)
			for (int j = 0; j < width; j++) {
				int y = (0xff & ((int) data[i * width + j]));
				int u = (0xff & ((int) data[frameSize + (i >> 1) * width
						+ (j & ~1) + 0]));
				int v = (0xff & ((int) data[frameSize + (i >> 1) * width
						+ (j & ~1) + 1]));
				y = y < 16 ? 16 : y;

				int r = Math.round(1.164f * (y - 16) + 1.596f * (v - 128));
				int g = Math.round(1.164f * (y - 16) - 0.813f * (v - 128)
						- 0.391f * (u - 128));
				int b = Math.round(1.164f * (y - 16) + 2.018f * (u - 128));

				r = r < 0 ? 0 : (r > 255 ? 255 : r);
				g = g < 0 ? 0 : (g > 255 ? 255 : g);
				b = b < 0 ? 0 : (b > 255 ? 255 : b);

				rgba[i * width + j] = 0xff000000 + (b << 16) + (g << 8) + r;
			}

		Bitmap bmp = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565);
		bmp.setPixels(rgba, 0, width, 0, 0, width, height);
		return bmp;
	}

	// 图片旋转90度
	public Bitmap rotaingImageView(int angle, Bitmap bitmap) {
		// 旋转图片 动作
		Matrix matrix = new Matrix();
		matrix.postRotate(angle);
		// 创建新的图片
		return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(),
				bitmap.getHeight(), matrix, true);
	}

	/**
	 * 获取face——id
	 * 
	 */
public  void getSSID(final boolean status) {
	Bitmap bitmap = rawByteArray2RGBABitmap2(ori1, width, height);
	Bitmap bitmap1 = rotaingImageView(-90, bitmap);
	try {
		saveFile(bitmap1, UrlUtils.PATH_NAME, UrlUtils.SAVE_REAL_PATH);
	} catch (IOException e) {
		e.printStackTrace();
	}
	AsyncHttpClient client = new AsyncHttpClient();
	RequestParams params = new RequestParams();
	File file = new File(UrlUtils.SAVE_REAL_PATH + UrlUtils.SAVE_REAL_PATH
			+ UrlUtils.PATH_NAME);
	params.put("api_key", UrlUtils.API_KEY);
	params.put("api_secret", UrlUtils.API_SECRET);
	try {
		params.put("img",file);
	} catch (FileNotFoundException e) {
		e.printStackTrace();
	}
	params.put("attribute","glass,pose,gender,age,race,smiling");
	client.post(UrlUtils.path, params, new JsonHttpResponseHandler(){
		@Override
		public void onSuccess(int statusCode, Header[] headers,
				JSONObject response) {
			super.onSuccess(statusCode, headers, response);
			if(statusCode==200){
				JSONObject obj;
				try {
					obj = new JSONObject(response.toString());
					JSONArray array=obj.getJSONArray("face");
					if(array.length()!=0){
						face_id=array.getJSONObject(0).getString("face_id");
						session_id=obj.getString("session_id");
						if(status==true){
							recongntion(UrlUtils.API_KEY,UrlUtils.API_SECRET);
						}else{
							createPersonHttp(UrlUtils.API_KEY,UrlUtils.API_SECRET);
						}
					}else{
						if(dialog.isShowing()){
							dialog.dismiss();
						}
						Toast.makeText(CameraBaseActivity.this, "未识别请重新录入",
								Toast.LENGTH_SHORT).show();
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		}
		@Override
		public void onFailure(int statusCode, Header[] headers,
				Throwable throwable, JSONObject errorResponse) {
			super.onFailure(statusCode, headers, throwable, errorResponse);
			if(dialog==null){
				dialog=new ProgressDialog(CameraBaseActivity.this);
				if(dialog.isShowing()){
					dialog.dismiss();
				}
			}
			Toast.makeText(CameraBaseActivity.this, "服务器异常,请稍后重试",
					Toast.LENGTH_SHORT).show();
		}
		@Override
		public void onProgress(int bytesWritten, int totalSize) {
			super.onProgress(bytesWritten, totalSize);
			if(dialog==null){
				dialog=new ProgressDialog(CameraBaseActivity.this);
					dialog.show();
			}else{
				dialog.show();
			}
		}
	});
}
	
}













