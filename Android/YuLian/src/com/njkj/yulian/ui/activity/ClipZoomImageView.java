package com.njkj.yulian.ui.activity;

import com.njkj.yulian.R;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.Paint.Align;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.text.Layout.Alignment;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.FloatMath;
import android.util.Log;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.ScaleGestureDetector.OnScaleGestureListener;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewTreeObserver;
import android.widget.ImageView;

/**
 * 缩放图片的View
 * 
 * @ClassName: ClipZoomImageView
 * @Description:
 * @author xiechengfa2000@163.com
 * @date 2015-5-10 下午6:20:41
 */
public class ClipZoomImageView extends ImageView implements
		OnScaleGestureListener, OnTouchListener,
		ViewTreeObserver.OnGlobalLayoutListener {
	public static float SCALE_MAX = 4.0f;
	private static float SCALE_MID = 2.0f;
	  private static final int ZOOM = 2;  
	  private static final int NONE = 0;  
	  int mode=0;
	/**
	 * 初始化时的缩放比例，如果图片宽或高大于屏幕，此�?将小�?
	 */
	private float initScale = 1.0f;
	public boolean once = true;

	/**
	 * 用于存放矩阵�?个�?
	 */
	private final float[] matrixValues = new float[9];

	/**
	 * 缩放的手势检�?
	 */
	private ScaleGestureDetector mScaleGestureDetector = null;
	public final Matrix mScaleMatrix = new Matrix();

	/**
	 * 用于双击�?��
	 */
	private GestureDetector mGestureDetector;
	public boolean isAutoScale;
	public boolean isScale=true;

	private int mTouchSlop;

	private float mLastX;
	private float mLastY;

	private boolean isCanDrag;
	private int lastPointerCount;
	/**
	 * 水平方向与View的边�?
	 */
	private int mHorizontalPadding;
	private Context context;
	float oldDist;
	 PointF mid = new PointF();  
	    Matrix savedMatrix = new Matrix();  
	    Matrix mScaleMatrix1 = new Matrix();

	public ClipZoomImageView(Context context) {
		this(context, null);
	}

	public ClipZoomImageView(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context=context;
		setScaleType(ScaleType.MATRIX);
		mGestureDetector = new GestureDetector(context,
				new SimpleOnGestureListener() {
					@Override
					public boolean onDoubleTap(MotionEvent e) {
						if (isAutoScale == true)
							return true;

						float x = e.getX();
						float y = e.getY();
						if (getScale() < SCALE_MID) {
							ClipZoomImageView.this.postDelayed(
									new AutoScaleRunnable(SCALE_MID, x, y), 16);
							isAutoScale = true;
						} else {
							ClipZoomImageView.this.postDelayed(
									new AutoScaleRunnable(initScale, x, y), 16);
							isAutoScale = true;
						}

						return true;
					}
				});
		mScaleGestureDetector = new ScaleGestureDetector(context, this);
		this.setOnTouchListener(this);
	}

	/**
	 * 自动缩放的任�?
	 * 
	 */
	private class AutoScaleRunnable implements Runnable {
		static final float BIGGER = 1.07f;
		static final float SMALLER = 0.93f;
		private float mTargetScale;
		private float tmpScale;

		/**
		 * 缩放的中�?
		 */
		private float x;
		private float y;

		/**
		 * 传入目标缩放值，根据目标值与当前值，判断应该放大还是缩小
		 * 
		 * @param targetScale
		 */
		public AutoScaleRunnable(float targetScale, float x, float y) {
			this.mTargetScale = targetScale;
			this.x = x;
			this.y = y;
			if (getScale() < mTargetScale) {
				tmpScale = BIGGER;
			} else {
				tmpScale = SMALLER;
			}

		}

		@Override
		public void run() {
			// 进行缩放
			mScaleMatrix.postScale(tmpScale, tmpScale, x, y);
			checkBorder();
			setImageMatrix(mScaleMatrix);

			final float currentScale = getScale();
			// 如果值在合法范围内，继续缩放
			if (((tmpScale > 1f) && (currentScale < mTargetScale))
					|| ((tmpScale < 1f) && (mTargetScale < currentScale))) {
				ClipZoomImageView.this.postDelayed(this, 16);
			} else
			// 设置为目标的缩放比例
			{
				final float deltaScale = mTargetScale / currentScale;
				mScaleMatrix.postScale(deltaScale, deltaScale, x, y);
				checkBorder();
				setImageMatrix(mScaleMatrix);
				isAutoScale = false;
			}

		}
	}

	@Override
	public boolean onScale(ScaleGestureDetector detector) {
		if(isScale==true){
		float scale = getScale();
		float scaleFactor = detector.getScaleFactor();

		if (getDrawable() == null)
			return true;

		/**
		 * 缩放的范围控�?
		 */
		if ((scale < SCALE_MAX && scaleFactor > 1.0f)
				|| (scale > initScale && scaleFactor < 1.0f)) {
			/**
			 * �?��值最小�?判断
			 */
			if (scaleFactor * scale < initScale) {
				scaleFactor = initScale / scale;
			}
			if (scaleFactor * scale > SCALE_MAX) {
				scaleFactor = SCALE_MAX / scale;
			}
			/**
			 * 设置缩放比例
			 */
			mScaleMatrix.postScale(scaleFactor, scaleFactor,
					detector.getFocusX(), detector.getFocusY());
			checkBorder();
			setImageMatrix(mScaleMatrix);
		}
		}
		return true;
	}

	/**
	 * 根据当前图片的Matrix获得图片的范�?
	 * 
	 * @return
	 */
	private RectF getMatrixRectF() {
		Matrix matrix = mScaleMatrix;
		RectF rect = new RectF();
		Drawable d = getDrawable();
		if (null != d) {
			rect.set(0, 0, d.getIntrinsicWidth(), d.getIntrinsicHeight());
			matrix.mapRect(rect);
		}
		return rect;
	}

	@Override
	public boolean onScaleBegin(ScaleGestureDetector detector) {
		return true;
	}

	@Override
	public void onScaleEnd(ScaleGestureDetector detector) {
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		float  oldRotation = 0;
		if (mGestureDetector.onTouchEvent(event))
			return true;
		mScaleGestureDetector.onTouchEvent(event);

		float x = 0, y = 0;
		// 拿到触摸点的个数
		final int pointerCount = event.getPointerCount();
		// 得到多个触摸点的x与y均�?
		for (int i = 0; i < pointerCount; i++) {
			x += event.getX(i);
			y += event.getY(i);
			if(pointerCount==2){
			}
		}
		x = x / pointerCount;
		y = y / pointerCount;

		/**
		 * 每当触摸点发生变化时，重置mLasX , mLastY
		 */
		if (pointerCount != lastPointerCount) {
			isCanDrag = false;
			mLastX = x;
			mLastY = y;
		}

		lastPointerCount = pointerCount;
		switch (event.getAction()& MotionEvent.ACTION_MASK) {
		case MotionEvent.ACTION_POINTER_DOWN:
			 mode=ZOOM;
			 oldRotation = rotation(event);  
			 midPoint(mid, event);  
			break;
		case MotionEvent.ACTION_MOVE:
			float dx = x - mLastX;
			float dy = y - mLastY;

			if (!isCanDrag) {
				isCanDrag = isCanDrag(dx, dy);
			}
			if (isCanDrag) {
				if (getDrawable() != null) {

					RectF rectF = getMatrixRectF();
					// 如果宽度小于屏幕宽度，则禁止左右移动
					if (rectF.width() <= getWidth() - mHorizontalPadding * 2) {
						dx = 0;
					}

					// 如果高度小雨屏幕高度，则禁止上下移动
					if (rectF.height() <= getHeight() - getHVerticalPadding()
							* 2) {
						dy = 0;
					}
					mScaleMatrix.postTranslate(dx, dy);
					checkBorder();
					setImageMatrix(mScaleMatrix);
				}
			}
			mLastX = x;
			mLastY = y;
			break;

		case MotionEvent.ACTION_UP:
		case MotionEvent.ACTION_CANCEL:
			lastPointerCount = 0;
			break;
		 case MotionEvent.ACTION_POINTER_UP:  
	            mode = NONE;  
	            break;
		}

		return true;
	}
	
	 // 触碰两点间距离  
    private float spacing(MotionEvent event) {  
        float x = event.getX(0) - event.getX(1);  
        float y = event.getY(0) - event.getY(1);  
        return (float) Math.sqrt(x * x + y * y);  
    }  
	
	// 取手势中心点  
    private void midPoint(PointF point, MotionEvent event) {  
        float x = event.getX(0) + event.getX(1);  
        float y = event.getY(0) + event.getY(1);  
        point.set(x / 2, y / 2);  
    }  
	
	 // 取旋转角度  
    private float rotation(MotionEvent event) {  
        double delta_x = (event.getX(0) - event.getX(1));  
        double delta_y = (event.getY(0) - event.getY(1));  
        double radians = Math.atan2(delta_y, delta_x);  
        return (float) Math.toDegrees(radians);  
    }  
	
	/**
	 * 获得当前的缩放比�?
	 * 
	 * @return
	 */
	public final float getScale() {
		mScaleMatrix.getValues(matrixValues);
		return matrixValues[Matrix.MSCALE_X];
	}

	@Override
	protected void onAttachedToWindow() {
		super.onAttachedToWindow();
		getViewTreeObserver().addOnGlobalLayoutListener(this);
	}

	@Override
	protected void onDetachedFromWindow() {
		super.onDetachedFromWindow();
		getViewTreeObserver().removeGlobalOnLayoutListener(this);
	}

	/**
	 * 垂直方向与View的边�?
	 */
	// private int getHVerticalPadding();

	@Override
	public void onGlobalLayout() {
		if (once) {
			Drawable d = getDrawable();
			if (d == null)
				return;
			// 垂直方向的边�?
			// getHVerticalPadding() = (getHeight() - (getWidth() - 2 *
			// mHorizontalPadding)) / 2;

			int width = getWidth();
			int height = getHeight();
			// 拿到图片的宽和高
			int drawableW = d.getIntrinsicWidth();
			int drawableH = d.getIntrinsicHeight();
			float scale = 1.0f;

			int frameSize = getWidth() - mHorizontalPadding * 2;

			// 大图
			if (drawableW > frameSize && drawableH < height) {
				scale = 1.0f * height / drawableH;
			} else if (drawableH > height && drawableW < frameSize) {
				scale = 1.0f * frameSize / drawableW;
			} else if (drawableW > frameSize && drawableH > height) {
				float scaleW = frameSize * 1.0f / drawableW;
				float scaleH = height * 1.0f / drawableH;
				scale = Math.max(scaleW, scaleH);
			}

			// 太小的图片放大处�?
			if (drawableW < frameSize && drawableH > height) {
				scale = 1.0f * frameSize / drawableW;
			} else if (drawableH < height && drawableW > frameSize) {
				scale = 1.0f * height / drawableH;
			} else if (drawableW < frameSize && drawableH < height) {
				float scaleW = 1.0f * frameSize / drawableW;
				float scaleH = 1.0f * height / drawableH;
				scale = Math.max(scaleW, scaleH);
			}

			initScale = scale;
			SCALE_MID = initScale * 2;
			SCALE_MAX = initScale * 4;			
			mScaleMatrix.postTranslate((width - drawableW) / 2,
					(height - drawableH) / 2);
			mScaleMatrix.postScale(scale, scale, getWidth() / 2,
					getHeight() / 2);

			// 图片移动至屏幕中�?
			setImageMatrix(mScaleMatrix);
			once = false;
		}
	}

	 public static int dip2px(Context context, float dpValue) {  
	        final float scale = context.getResources().getDisplayMetrics().density;  
	        return (int) (dpValue * scale + 0.5f);  
	    }  
	
	/**
	 * 剪切图片，返回剪切后的bitmap对象
	 * 
	 * @return
	 */
	public Bitmap clip(int height) {
		Bitmap bitmap = Bitmap.createBitmap(getWidth(), getHeight(),
				Bitmap.Config.ARGB_8888);
		Canvas canvas = new Canvas(bitmap);
		draw(canvas);
		return Bitmap.createBitmap(bitmap, mHorizontalPadding,
				mHorizontalPadding, getWidth() - 2 * mHorizontalPadding,
				height);
	}
    // 加水印 也可以加文字
    public  Bitmap watermarkBitmap(int height,String title,String title_fanyi,int top) {
    	Bitmap bitmap = Bitmap.createBitmap(getWidth(), getHeight(),
				Bitmap.Config.ARGB_8888);
		Canvas canvas = new Canvas(bitmap);
		draw(canvas);
		Bitmap zzz=Bitmap.createBitmap(bitmap, mHorizontalPadding,
				mHorizontalPadding, getWidth() - 2 * mHorizontalPadding,
				height);
        Canvas cv = new Canvas(zzz);
        cv.drawBitmap(zzz, 0, 0, null);// 在 0，0坐标开始画入src    
        //加入文字
        if(title!=null)
        {
            String familyName ="宋体";
            Typeface font = Typeface.create(familyName,Typeface.BOLD);            
            TextPaint textPaint=new TextPaint();
            textPaint.setColor(Color.WHITE);
            textPaint.setTypeface(font);
            textPaint.setTextSize(Float.parseFloat(getResources().getString(R.string.text_size)));
            //这里是自动换行的
           /* StaticLayout layout = new StaticLayout(title,textPaint,zzz.getWidth(),Alignment.ALIGN_CENTER,1.0F,0.0F,true);
            layout.draw(cv);*/
            textPaint.setTextAlign(Align.CENTER);//text内容从文字中间开始绘画
            cv.drawText(title,zzz.getWidth()/2,top-textPaint.descent()-textPaint.ascent()+30,textPaint); //x代表从横坐标哪里开始绘画  y 代表绘画的y轴
            
            String familyName1 ="宋体";
            Typeface font1 = Typeface.create(familyName,Typeface.BOLD);            
            TextPaint textPaint1=new TextPaint();
            textPaint.setColor(Color.WHITE);
            textPaint.setTypeface(font);
            textPaint.setTextSize(Float.parseFloat(getResources().getString(R.string.text_size)));
            //这里是自动换行的
           /* StaticLayout layout = new StaticLayout(title,textPaint,zzz.getWidth(),Alignment.ALIGN_CENTER,1.0F,0.0F,true);
            layout.draw(cv);*/
            textPaint.setTextAlign(Align.CENTER);//text内容从文字中间开始绘画
            cv.drawText(title_fanyi,zzz.getWidth()/2,top-textPaint.descent()-2*textPaint.ascent()+40,textPaint); //x代表从横坐标哪里开始绘画  y 代表绘画的y轴
        }
        cv.save(Canvas.ALL_SAVE_FLAG);// 保存
        cv.restore();// 存储
        return zzz;
    }
	
	/**
	 * 边界�?��
	 */
	private void checkBorder() {
		RectF rect = getMatrixRectF();
		float deltaX = 0;
		float deltaY = 0;

		int width = getWidth();
		int height = getHeight();

		// 如果宽或高大于屏幕，则控制范�?; 这里�?.001是因为精度丢失会产生问题，但是误差一般很小，�?��我们直接加了�?��0.01
		if (rect.width() + 0.01 >= width - 2 * mHorizontalPadding) {
			if (rect.left > mHorizontalPadding) {
				deltaX = -rect.left + mHorizontalPadding;
			}

			if (rect.right < width - mHorizontalPadding) {
				deltaX = width - mHorizontalPadding - rect.right;
			}
		}

		if (rect.height() + 0.01 >= height - 2 * getHVerticalPadding()) {
			if (rect.top > getHVerticalPadding()) {
				deltaY = -rect.top + getHVerticalPadding();
			}

			if (rect.bottom < height - getHVerticalPadding()) {
				deltaY = height - getHVerticalPadding() - rect.bottom;
			}
		}

		mScaleMatrix.postTranslate(deltaX, deltaY);
	}

	/**
	 * 是否是拖动行�?
	 * 
	 * @param dx
	 * @param dy
	 * @return
	 */
	private boolean isCanDrag(float dx, float dy) {
		return Math.sqrt((dx * dx) + (dy * dy)) >= mTouchSlop;
	}

	public void setHorizontalPadding(int mHorizontalPadding) {
		this.mHorizontalPadding = mHorizontalPadding;
	}

	private int getHVerticalPadding() {
		
//		return (getHeight() - (getWidth() - 2 * mHorizontalPadding)) / 2;
		return 0;
	}
}
