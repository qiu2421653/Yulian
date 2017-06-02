package com.njkj.yulian.ui.gui;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnKeyListener;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.njkj.yulian.R;

/**
 * 自定义弹窗
 * */
public class CustomDialog {

	public static final int LEFT_BUTTON_CLICK = 12345;
	public static final int RIGHT_BUTTON_CLICK = 54321;

	/**
	 * 
	 * @param context
	 * @param title
	 * @param leftString
	 * @param rightString
	 * @param listener
	 * @return
	 */
	public static Dialog createCommonCustomDialog(Context context,
			String title, String leftString, String rightString,
			final DialogInterface.OnClickListener listener) {
		return createCommonCustomDialog(R.layout.layout_custom_dialog, context,
				title, leftString, rightString, listener);
	}

	public static Dialog createCommonCustomDialog(int layoutId,
			Context context, String title, String leftString,
			String rightString, final DialogInterface.OnClickListener listener) {
		final Dialog dialog = new Dialog(context, R.style.dialog_common);
		LayoutInflater inflater = (LayoutInflater) LayoutInflater.from(context);
		View contentView = null;

		contentView = inflater.inflate(layoutId, null);

		final LinearLayout leftButton = (LinearLayout) contentView
				.findViewById(R.id.btn_left);
		LinearLayout rightButton = (LinearLayout) contentView
				.findViewById(R.id.btn_right);
		TextView tvLeft = (TextView) contentView.findViewById(R.id.tv_left);
		TextView tvRight = (TextView) contentView.findViewById(R.id.tv_right);
		final TextView titleText = (TextView) contentView
				.findViewById(R.id.tv_title);
		titleText.setText(title);
		if (leftString != null) {
			tvLeft.setText(leftString);
		} else {
			leftButton.setVisibility(View.GONE);
			rightButton
					.setBackgroundResource(R.drawable.sel_btn_bg_custom_dialog);
		}
		if (rightString != null) {
			tvRight.setText(rightString);
		} else {
			rightButton.setVisibility(View.GONE);
			leftButton
					.setBackgroundResource(R.drawable.sel_btn_bg_custom_dialog);
		}
		if (rightString == null || leftString == null) {
			contentView.findViewById(R.id.sp_line).setVisibility(View.GONE);
		}

		View.OnClickListener btnOnClickListener = new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (listener != null) {
					switch (v.getId()) {
					case R.id.btn_left:
						listener.onClick(dialog, LEFT_BUTTON_CLICK);
						break;
					case R.id.btn_right:
						listener.onClick(dialog, RIGHT_BUTTON_CLICK);
						break;
					}
				}
			}
		};

		leftButton.setOnClickListener(btnOnClickListener);
		rightButton.setOnClickListener(btnOnClickListener);
		dialog.setContentView(contentView);
		dialog.setCanceledOnTouchOutside(false);
		dialog.setOnKeyListener(new OnKeyListener() {
			@Override
			public boolean onKey(DialogInterface dialog, int keyCode,
					KeyEvent event) {
				if (keyCode == 84) {// HTC的搜索实体键
					return true;
				}
				return false;
			}
		});
		return dialog;
	}

	public static void setDialogTitle(Dialog dialog, String title) {
		if (dialog == null) {
			return;
		}

		final TextView titleText = (TextView) dialog
				.findViewById(R.id.tv_title);
		if (titleText != null) {
			titleText.setText(title);
		}
	}

}
