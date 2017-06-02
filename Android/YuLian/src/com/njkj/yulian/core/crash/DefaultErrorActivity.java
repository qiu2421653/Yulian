/*
 * Copyright 2015 Eduard Ereza Martínez
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 *
 * You may obtain a copy of the License at
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.njkj.yulian.core.crash;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.njkj.yulian.R;
import com.njkj.yulian.ui.activity.MainActivity;

public final class DefaultErrorActivity extends Activity {

	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_error_activity);

		// Close/restart button logic:
		// If a class if set, use restart.
		// Else, use close and just finish the app.
		// It is recommended that you follow this logic if implementing a custom
		// error activity.
		Button restartButton = (Button) findViewById(R.id.customactivityoncrash_error_activity_restart_button);

		final Class<? extends Activity> restartActivityClass = CustomActivityOnCrash
				.getRestartActivityClassFromIntent(getIntent());

		// final Class restartActivityClass = MainActivity.class;

		final CustomActivityOnCrash.EventListener eventListener = CustomActivityOnCrash
				.getEventListenerFromIntent(getIntent());

		if (restartActivityClass != null) {
			restartButton.setText(R.string.restart_app);
			restartButton.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					Intent intent = new Intent(DefaultErrorActivity.this,
							restartActivityClass);
					CustomActivityOnCrash.restartApplicationWithIntent(
							DefaultErrorActivity.this, intent, eventListener);
				}
			});
		} else {
			restartButton.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					CustomActivityOnCrash.closeApplication(
							DefaultErrorActivity.this, eventListener);
				}
			});
		}

		int defaultErrorActivityDrawableId = CustomActivityOnCrash
				.getDefaultErrorActivityDrawableIdFromIntent(getIntent());
		ImageView errorImageView = ((ImageView) findViewById(R.id.customactivityoncrash_error_activity_image));
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
			errorImageView.setImageDrawable(getResources().getDrawable(
					defaultErrorActivityDrawableId, getTheme()));
		} else {
			// noinspection deprecation
			errorImageView.setImageDrawable(getResources().getDrawable(
					defaultErrorActivityDrawableId));
		}
	}

}
