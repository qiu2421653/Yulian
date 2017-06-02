/*
 * Copyright 2013 Niek Haarman
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.njkj.yulian.ui.adapter.swing;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

/**
 * 一个实现animation类,适用于摆动从底部动画视图。
 */
public class SwingBottomInAnimationAdapter extends SingleAnimationAdapter {

	private final long mAnimationDelayMillis;
	private final long mAnimationDurationMillis;

	public SwingBottomInAnimationAdapter(BaseAdapter baseAdapter) {
		this(baseAdapter, DEFAULTANIMATIONDELAYMILLIS,
				DEFAULTANIMATIONDURATIONMILLIS);
	}

	public SwingBottomInAnimationAdapter(BaseAdapter baseAdapter,
			long animationDelayMillis) {
		this(baseAdapter, animationDelayMillis, DEFAULTANIMATIONDURATIONMILLIS);
	}

	public SwingBottomInAnimationAdapter(BaseAdapter baseAdapter,
			long animationDelayMillis, long animationDurationMillis) {
		super(baseAdapter);
		mAnimationDelayMillis = animationDelayMillis;
		mAnimationDurationMillis = animationDurationMillis;
	}

	@Override
	protected long getAnimationDelayMillis() {
		return mAnimationDelayMillis;
	}

	@Override
	protected long getAnimationDurationMillis() {
		return mAnimationDurationMillis;
	}

	@SuppressLint("NewApi") @Override
	protected Animator getAnimator(ViewGroup parent, View view) {
		return ObjectAnimator.ofFloat(view, "translationY", 500, 0);
	}

}
