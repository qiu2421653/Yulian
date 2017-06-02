package com.njkj.yulian.ui.activity;

import java.io.IOException;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.hardware.Camera;
import android.hardware.Camera.PreviewCallback;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;

import com.faceplusplus.api.FaceDetecter;
import com.faceplusplus.api.FaceDetecter.Face;
import com.njkj.yulian.R;
import com.njkj.yulian.utils.FaceMask;
import com.njkj.yulian.utils.UrlUtils;

@SuppressWarnings("deprecation")
@SuppressLint("NewApi")
public class CameraPreview extends CameraBaseActivity implements Callback,
		PreviewCallback, OnClickListener {
	private boolean type=false;
//	boolean aa=true;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.camerapreview);
		setHeaderTitle(R.string.person);
		setHeaderLeftText();
		Intent intent=getIntent();
		type=intent.getBooleanExtra("type",false);
		if(type==true){
			setHeaderTitle(R.string.face_person);
		}
		initView();
	}
	//初始化操作
	private void initView() {
		camerasurface = (SurfaceView) findViewById(R.id.camera_preview);
		but = (Button) findViewById(R.id.camerapreview_submit);
		if(type==true){
			but.setText("点击登录");
			}	
		but.setOnClickListener(this);
		mask = (FaceMask) findViewById(R.id.mask);
		LayoutParams para = new LayoutParams(480, 800);
		handleThread = new HandlerThread("dt");
		handleThread.start();
		detectHandler = new Handler(handleThread.getLooper());
		para.addRule(RelativeLayout.CENTER_IN_PARENT);
		camerasurface.getHolder().addCallback(this);
		camerasurface.setKeepScreenOn(true);

		facedetecter = new FaceDetecter();
		if (!facedetecter.init(this, UrlUtils.API_KEY)) {
			Log.e("diff", "有错误 ");
		}
		facedetecter.setTrackingMode(true);
	}

	@Override
	protected void onResume() {
		super.onResume();
		camera = Camera.open(1);
		Camera.Parameters para = camera.getParameters();
		para.setPreviewSize(width, height);
		camera.setParameters(para);
	}

	@Override
	protected void onPause() {
		super.onPause();
		if (camera != null) {
			camera.setPreviewCallback(null);
			camera.stopPreview();
			camera.release();
			finish();
		}
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		facedetecter.release(this);
		handleThread.quit();
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {

		try {
			camera.setPreviewDisplay(holder);
		} catch (IOException e) {
			e.printStackTrace();
		}
		camera.setDisplayOrientation(90);
		camera.startPreview();
		camera.setPreviewCallback(this);
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {

	}
	/**
	 * 相机回调方法
	 * 
	 */
	@Override
	public void onPreviewFrame(final byte[] data, Camera camera) {
		camera.setPreviewCallback(null);
		detectHandler.post(new Runnable() {

			@Override
			public void run() {
				byte[] ori = new byte[width * height];
				int is = 0;
				for (int x = width - 1; x >= 0; x--) {

					for (int y = height - 1; y >= 0; y--) {

						ori[is] = data[y * width + x];

						is++;
					}

				}
				final Face[] faceinfo = facedetecter.findFaces(ori, height,
						width);
				ori1 = data;
				runOnUiThread(new Runnable() {

					@Override
					public void run() {
						mask.setFaceInfo(faceinfo);
					}
				});
				/*try {
					if(aa==true){
					Thread.sleep(2000);
					getSSID(type);
					aa=false;
					}
				} catch (InterruptedException e) {
					e.printStackTrace();
				}*/
				CameraPreview.this.camera
						.setPreviewCallback(CameraPreview.this);
			}
		});
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.camerapreview_submit:
				getSSID(type);
			break;
		/*case R.id.delete:
			deletePersonHttp();
		break;*/
		case R.id.title_left1:
			finish();
			break;
		default:
			break;
		}
	}

}
