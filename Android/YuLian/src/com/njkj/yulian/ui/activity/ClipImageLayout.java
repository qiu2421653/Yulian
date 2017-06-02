package com.njkj.yulian.ui.activity;

import com.js.photosdk.operate.ImageObject;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.widget.RelativeLayout;

/**
 * @ClassName: ClipImageLayout
 * @Description:
 * @author xiechengfa2000@163.com
 * @date 2015-5-10 下午10:22:24
 */
public class ClipImageLayout extends RelativeLayout {
	private ClipZoomImageView mZoomImageView;
	private int mHorizontalPadding = 0;// 框左右的边距，这里左右边距为0，为�?��屏幕宽度的正方形�?
	ImageObject  imageObject;
	private int num=0;
	public ClipImageLayout(Context context){
		this(context,null);
	}
	public ClipImageLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
		mZoomImageView = new ClipZoomImageView(context);
//		mClipImageView = new ClipImageBorderView(context);
		imageObject=new ImageObject();
		android.view.ViewGroup.LayoutParams lp = new LayoutParams(
				android.view.ViewGroup.LayoutParams.MATCH_PARENT,
				android.view.ViewGroup.LayoutParams.MATCH_PARENT);

		this.addView(mZoomImageView, lp);
//		this.addView(mClipImageView, lp);

		// 计算padding的px
		mHorizontalPadding = (int) TypedValue.applyDimension(
				TypedValue.COMPLEX_UNIT_DIP, mHorizontalPadding, getResources()
						.getDisplayMetrics());
		mZoomImageView.setHorizontalPadding(mHorizontalPadding);
//		mClipImageView.setHorizontalPadding(mHorizontalPadding);
	}

	public void setImageDrawable(Drawable drawable) {
		mZoomImageView.setImageDrawable(drawable);
	}

	public void setImageBitmap(Bitmap bitmap) {
		mZoomImageView.setImageBitmap(bitmap);
	}

	/**
	 * 对外公布设置边距的方�?单位为dp
	 * 
	 * @param mHorizontalPadding
	 */
	public void setHorizontalPadding(int mHorizontalPadding) {
		this.mHorizontalPadding = mHorizontalPadding;
	}
	
	public void xuanzhuan(int width,int height){
		num+=1;
		mZoomImageView.mScaleMatrix.postRotate(90,width,height);
		mZoomImageView.setImageMatrix(mZoomImageView.mScaleMatrix);
		if(num!=0&&num%4==0){
			mZoomImageView.isAutoScale=false;
			mZoomImageView.isScale=true;
		}else{
			mZoomImageView.isAutoScale=true;
			mZoomImageView.isScale=false;
		}
		
		
	}
	/**
	 * 裁切图片
	 * 
	 * @return
	 */
	public Bitmap clip(int height,String text,String text_fanyi,int top) {
		return mZoomImageView.watermarkBitmap(height,text,text_fanyi,top);
	}
}
