package com.njkj.yulian.ui.gui.smoothTab;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.njkj.yulian.R;

public class SmoothTabTitle extends HorizontalScrollView {

	private LayoutInflater mLayoutInflater;
	private ViewPager pager;
	private final PageListener pageListener = new PageListener();
	private int currentPosition = 0;
	private float currentPositionOffset = 0f;
	private LinearLayout tabsContainer;
	private int tabCount;
	private LinearLayout.LayoutParams defaultTabLayoutParams;
	private Rect selectedRect;
	private Drawable selected;
	private int scrollOffset = 10;
	private int lastScrollX = 0;
	private TextDrawable[] drawables;// TextDrawable是一个可以画Text的Drawable对象

	public SmoothTabTitle(Context context) {
		this(context, null);
	}

	public SmoothTabTitle(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public SmoothTabTitle(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		initView(context);
	}

	private void initView(Context context) {
		mLayoutInflater = LayoutInflater.from(context);
		drawables = new TextDrawable[3];
		int i = 0;
		while (i < drawables.length) {
			drawables[i] = new TextDrawable(getContext());
			i++;
		}
		selectedRect = new Rect();
		// scrollview的子控件高度值不足scrollview高度的时候,定义android:layout_height="fill_parent"是不起作用的，
		// 必须加上fillviewport属性，当子控件的高度值大于scrollview的高度时，这个标签就没有任何意义了。
		setFillViewport(true);
		// 设置view是否更改，如果开发者用自定义的view，重写ondraw（）应该将调用此方法设置为false，这样程序会调用自定义的布局。
		setWillNotDraw(false);
		// HorizontalScrollView不能放多个子控件 ,只能放一个,用一个子控件去容纳多个
		tabsContainer = new LinearLayout(context);
		tabsContainer.setOrientation(LinearLayout.HORIZONTAL);
		tabsContainer.setLayoutParams(new LayoutParams(
				LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
		addView(tabsContainer);

		DisplayMetrics dm = getResources().getDisplayMetrics();
		// scrollOffset的默认值转变为标准尺寸COMPLEX_UNIT_DIP,10dip
		scrollOffset = (int) TypedValue.applyDimension(
				TypedValue.COMPLEX_UNIT_DIP, scrollOffset, dm);

		defaultTabLayoutParams = new LinearLayout.LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT);
		// 绘制高亮区域作为选择指示器
		selected = getResources().getDrawable(R.drawable.bg_title_selected);
	}

	public void setViewPager(ViewPager pager) {
		this.pager = pager;
		if (pager.getAdapter() == null) {
			throw new IllegalStateException(
					"ViewPager does not have adapter instance.");
		}
		pager.setOnPageChangeListener(pageListener);
		notifyDataSetChanged();
	}

	// 当附加在ViewPager适配器上的数据发生变化时,应该调用该方法通知TitleBar刷新数据
	public void notifyDataSetChanged() {
		tabsContainer.removeAllViews();
		tabCount = pager.getAdapter().getCount();
		for (int i = 0; i < tabCount; i++) {
			addTab(i, pager.getAdapter().getPageTitle(i).toString());
		}
	}

	private void addTab(final int position, String title) {
		ViewGroup tab = (ViewGroup) mLayoutInflater.inflate(
				R.layout.title_tab_item, this, false);
		TextView tabText = (TextView) tab.findViewById(R.id.tab_text);
		tabText.setText(title);
		tabText.setGravity(Gravity.CENTER);
		tabText.setSingleLine();
		tabText.setFocusable(true);
		tab.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				pager.setCurrentItem(position);
			}
		});

		tabsContainer.addView(tab, position, defaultTabLayoutParams);
	}

	@Override
	public void draw(Canvas canvas) {
		super.draw(canvas);
		calculateSelectedRect(selectedRect);
		if (selected != null) {
			selected.setBounds(selectedRect);
			selected.draw(canvas);
		}
		int i = 0;
		while (i < tabsContainer.getChildCount()) {
			// 循环画3个textview,确保i不超过当期位置的上一个和下一个
			if (i < currentPosition - 1 || i > currentPosition + 1) {
				i++;
			} else {
				ViewGroup childTab = (ViewGroup) tabsContainer.getChildAt(i);
				TextView tab_text = (TextView) childTab
						.findViewById(R.id.tab_text);
				if (tab_text != null) {
					TextDrawable textDrawable = drawables[i - currentPosition
							+ 1];
					// int save = canvas.save();
					// calculateSelectedRect(selectedRect);
					canvas.clipRect(selectedRect);// 只有在这个区域内的才显示，其他内容被裁剪掉 不显示
					textDrawable.setText(tab_text.getText());
					textDrawable.setTextSize(0, tab_text.getTextSize());
					textDrawable.setTextColor(getResources().getColor(
							R.color.white));
					// 根据tab_text的位置计算textDrawable的字体应该在什么位置
					int left = childTab.getLeft()
							+ tab_text.getLeft()
							+ (tab_text.getWidth() - textDrawable
									.getIntrinsicWidth()) / 2
							+ getPaddingLeft();
					int top = childTab.getTop()
							+ tab_text.getTop()
							+ (tab_text.getHeight() - textDrawable
									.getIntrinsicHeight()) / 2
							+ getPaddingTop();
					int right = textDrawable.getIntrinsicWidth() + left;
					int bottom = textDrawable.getIntrinsicHeight() + top;

					textDrawable.setBounds(left, top, right, bottom);
					textDrawable.draw(canvas);
					// canvas.restoreToCount(save);
				}
				i++;
			}
		}
	}

	// 计算滑动过程中矩形高亮区域的上下左右位置
	private void calculateSelectedRect(Rect rect) {
		ViewGroup currentTab = (ViewGroup) tabsContainer
				.getChildAt(currentPosition);
		TextView currentTabText = (TextView) currentTab
				.findViewById(R.id.tab_text);

		float left = (float) (currentTab.getLeft() + currentTabText.getLeft());
		float width = ((float) currentTabText.getWidth()) + left;
		// 如果当前位置偏移>0 或者当前位置是最后一个,因为是draw方法不停调用这个方法,所以下面的括号内实现了平滑滚动选中框
		if (currentPositionOffset > 0f && currentPosition < tabCount - 1) {
			ViewGroup nextTab = (ViewGroup) tabsContainer
					.getChildAt(currentPosition + 1);
			TextView next_text = (TextView) nextTab.findViewById(R.id.tab_text);

			float next_left = (float) (nextTab.getLeft() + next_text.getLeft());
			left = left * (1.0f - currentPositionOffset) + next_left
					* currentPositionOffset;
			width = width * (1.0f - currentPositionOffset)
					+ currentPositionOffset
					* (((float) next_text.getWidth()) + next_left);
		}
		// 新的rect位置
		int newLeft = (int) (left + getPaddingLeft());
		int newTop = getPaddingTop() + currentTab.getTop()
				+ currentTabText.getTop();
		int newRight = (int) (width + getPaddingLeft());
		int newBottom = currentTab.getTop() + getPaddingTop()
				+ currentTabText.getTop() + currentTabText.getHeight();
		rect.set(newLeft, newTop, newRight, newBottom);
	}

	// 滑动到某个子视图
	private void scrollToChild(int position, int offset) {

		if (tabCount == 0) {
			return;
		}
		calculateSelectedRect(selectedRect);
		int newScrollX = lastScrollX;
		if (selectedRect.left < getScrollX() + scrollOffset) {
			newScrollX = selectedRect.left - scrollOffset;
		} else if (selectedRect.right > getScrollX() + getWidth()
				- scrollOffset) {
			newScrollX = selectedRect.right - getWidth() + scrollOffset;
		}
		if (newScrollX != lastScrollX) {
			lastScrollX = newScrollX;
			scrollTo(newScrollX, 0);
		}
	}

	// 计算滚动到最右边范围
	private int getScrollRange() {
		int range = getChildAt(0).getWidth() - getWidth() + getPaddingLeft()
				+ getPaddingRight();
		return getChildCount() > 0 ? Math.max(0, range) : 0;
	}

	private class PageListener implements OnPageChangeListener {

		@Override
		public void onPageScrolled(int position, float positionOffset,
				int positionOffsetPixels) {
			currentPosition = position;
			currentPositionOffset = positionOffset;

			scrollToChild(position, (int) (positionOffset * tabsContainer
					.getChildAt(position).getWidth()));

			invalidate();

		}

		@Override
		public void onPageScrollStateChanged(int state) {
			// if (state == ViewPager.SCROLL_STATE_IDLE) {
			// if (pager.getCurrentItem() == 0) {
			// // 滑动到最左边
			// scrollTo(0, 0);
			// }
			// else if (pager.getCurrentItem() == tabCount - 1) {
			// // 滑动到最右边
			// scrollTo(getScrollRange(), 0);
			// }
			// else {
			// scrollToChild(pager.getCurrentItem(), 0);
			// }
			// }
		}

		@Override
		public void onPageSelected(int position) {

		}

	}
}
