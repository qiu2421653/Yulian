package com.njkj.yulian.utils;

import android.graphics.Bitmap;
import android.media.FaceDetector;
import android.util.Log;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import com.njkj.yulian.core.callback.SimpleCallback;

/**
 * Created by wangyang on 15/7/27.
 */

public class ImageUtil extends FileUtil {

//	public static void saveBitmap(String path, Bitmap bmp) {
//		//long currentTime = System.currentTimeMillis();
//		saveBitmap(bmp, path);
//	}

	public static void saveBitmap(Bitmap bmp, String filename,SimpleCallback callback) {
		try {
			FileOutputStream fileout = new FileOutputStream(filename);
			BufferedOutputStream bufferOutStream = new BufferedOutputStream(
					fileout);
			bmp.compress(Bitmap.CompressFormat.JPEG, 100, bufferOutStream);
			bufferOutStream.flush();
			bufferOutStream.close();
			callback.onCallback("success");
		} catch (IOException e) {
			e.printStackTrace();
			callback.onCallback("fail");
			return;
		}
		
	}

	public static class FaceRects {
		public int numOfFaces; // 实际检测出的人脸数
		public FaceDetector.Face[] faces; // faces.length >= numOfFaces
	}

	public static FaceRects findFaceByBitmap(Bitmap bmp) {
		return findFaceByBitmap(bmp, 1);
	}

	public static FaceRects findFaceByBitmap(Bitmap bmp, int maxFaces) {

		if (bmp == null) {
			return null;
		}

		Bitmap newBitmap = bmp;

		// 人脸检测API 仅支持 RGB_565 格式当图像. (for now)
		if (newBitmap.getConfig() != Bitmap.Config.RGB_565) {
			newBitmap = newBitmap.copy(Bitmap.Config.RGB_565, false);
		}

		FaceRects rects = new FaceRects();
		rects.faces = new FaceDetector.Face[maxFaces];

		try {
			FaceDetector detector = new FaceDetector(newBitmap.getWidth(),
					newBitmap.getHeight(), maxFaces);
			rects.numOfFaces = detector.findFaces(newBitmap, rects.faces);
		} catch (Exception e) {
			return null;
		}

		if (newBitmap != bmp) {
			newBitmap.recycle();
		}
		return rects;
	}

}
