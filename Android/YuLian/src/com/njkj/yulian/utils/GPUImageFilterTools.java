/*
 * Copyright (C) 2012 CyberAgent
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

package com.njkj.yulian.utils;

import java.util.LinkedList;
import java.util.List;

import jp.co.cyberagent.android.gpuimage.GPUImageBrightnessFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageColorInvertFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageContrastFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageEmbossFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageExposureFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageFilterGroup;
import jp.co.cyberagent.android.gpuimage.GPUImageGaussianBlurFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageGrayscaleFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageHighlightShadowFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageHueFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageLaplacianFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageMonochromeFilter;
import jp.co.cyberagent.android.gpuimage.GPUImagePixelationFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageSaturationFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageSepiaFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageSharpenFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageSketchFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageSmoothToonFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageSobelEdgeDetection;
import jp.co.cyberagent.android.gpuimage.GPUImageSobelThresholdFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageToonFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageVignetteFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageWhiteBalanceFilter;
import android.content.Context;
import android.graphics.PointF;

/**
 * ==============================
 * 
 * @author Qiu
 * 
 * @Package com.njkj.yulian.utils
 * 
 * @Description:滤镜配置
 * 
 * @version 1.0 ==============================
 */
public class GPUImageFilterTools {
	final static FilterList adJustList; // 饱和度之类的
	final static FilterList filtersList;// 滤镜

	static {
		adJustList = new FilterList();
		adJustList.addFilter("亮度", FilterType.BRIGHTNESS);
		adJustList.addFilter("对比", FilterType.CONTRAST);
		adJustList.addFilter("饱和", FilterType.SATURATION);
		adJustList.addFilter("暗角", FilterType.VIGNETTE);
		adJustList.addFilter("色温", FilterType.WHITE_BALANCE);
		adJustList.addFilter("色调", FilterType.HUE);
		adJustList.addFilter("锐化", FilterType.SHARPEN);
		adJustList.addFilter("阴影", FilterType.HIGHLIGHT_SHADOW);
		adJustList.addFilter("曝光", FilterType.EXPOSURE);

		filtersList = new FilterList();
		filtersList.addFilter("原图", FilterType.BRIGHTNESS);
		filtersList.addFilter("模糊", FilterType.GAUSSIAN);// 高斯模糊
		filtersList.addFilter("底片", FilterType.INVERT);
		filtersList.addFilter("马赛克", FilterType.PIXELATION);
		filtersList.addFilter("怀旧", FilterType.SEPIA);
		filtersList.addFilter("灰度", FilterType.GRAYSCALE);
	}

	// 选择饱和度
	public static void chooseFont(final Context context, final int item,
			final OnGpuImageFilterChosenListener listener) {
		listener.onGpuImageFilterChosenListener(createFilterForType(context,
				adJustList.filters.get(item)));
	}

	// 选择滤镜
	public static void chooseFilter(final Context context, final int item,
			final OnGpuImageFilterChosenListener listener) {
		listener.onGpuImageFilterChosenListener(createFilterForType(context,
				filtersList.filters.get(item)));
	}

	private static GPUImageFilter createFilterForType(final Context context,
			final FilterType type) {
		switch (type) {
		case MONOCHROME:
			// 单色
			return new GPUImageMonochromeFilter(1.0f, new float[] { 0.6f,
					0.45f, 0.3f, 1.0f });
		case SMOOTHTOON:
			// 光滑
			return new GPUImageSmoothToonFilter();
		case SOBELTHRESHOLD:
			return new GPUImageSobelThresholdFilter();
		case SOBEL_EDGE_DETECTION:
			// 霓虹
			return new GPUImageSobelEdgeDetection();
		case GRAYSCALE:
			// 灰度
			return new GPUImageGrayscaleFilter();
		case SEPIA:
			// 怀旧
			return new GPUImageSepiaFilter();
		case PIXELATION:
			// 马赛克
			GPUImagePixelationFilter gpuImagePixelationFilter = new GPUImagePixelationFilter();
			gpuImagePixelationFilter.setPixel(10.0f);
			return gpuImagePixelationFilter;
		case INVERT:
			// 底片
			return new GPUImageColorInvertFilter();
		case TOON:
			// 卡通
			return new GPUImageToonFilter();
		case GAUSSIAN:
			// 高斯模糊
			return new GPUImageGaussianBlurFilter();
		case SKETCHFILTER:
			// 素描
			return new GPUImageSketchFilter();
		case EMBOSS:
			// 浮雕
			List<GPUImageFilter> emFilter = new LinkedList<GPUImageFilter>();
			emFilter.add(new GPUImageLaplacianFilter());
			emFilter.add(new GPUImageEmbossFilter(0.2f));
			return new GPUImageFilterGroup(emFilter);
		case CONTRAST:
			// 对比
			return new GPUImageContrastFilter(1.0f);
		case HUE:
			// 色调
			return new GPUImageHueFilter(90.0f);
		case BRIGHTNESS:
			// 亮度
			return new GPUImageBrightnessFilter(0.5f);
		case SHARPEN:
			// 锐化
			GPUImageSharpenFilter sharpness = new GPUImageSharpenFilter();
			sharpness.setSharpness(2.0f);
			return sharpness;
		case SATURATION:
			// 饱和
			return new GPUImageSaturationFilter(1.0f);
		case EXPOSURE:
			// 曝光
			return new GPUImageExposureFilter();
		case HIGHLIGHT_SHADOW:
			// 阴影
			return new GPUImageHighlightShadowFilter(0.0f, 1.0f);
		case WHITE_BALANCE:
			// 色温
			return new GPUImageWhiteBalanceFilter(5000.0f, 0.0f);
		case VIGNETTE:
			// 暗角
			PointF centerPoint = new PointF();
			centerPoint.x = 0.5f;
			centerPoint.y = 0.5f;
			return new GPUImageVignetteFilter(centerPoint, new float[] { 0.0f,
					0.0f, 0.0f }, 0.3f, 0.75f);

		default:
			throw new IllegalStateException("No filter of that type!");
		}

	}

	public interface OnGpuImageFilterChosenListener {
		void onGpuImageFilterChosenListener(GPUImageFilter filter);
	}

	private enum FilterType {
		SMOOTHTOON, MONOCHROME, THREE_X_THREE_CONVOLUTION, SEPIA, GRAYSCALE, SOBEL_EDGE_DETECTION, SOBELTHRESHOLD, TOON, SKETCHFILTER, GAUSSIAN, INVERT, PIXELATION, EMBOSS, BRIGHTNESS, CONTRAST, SATURATION, VIGNETTE, WHITE_BALANCE, HUE, SHARPENHIGHLIGHT_SHADOW, EXPOSURE, SHARPEN, HIGHLIGHT_SHADOW
	}

	private static class FilterList {
		public List<String> names = new LinkedList<String>();
		public List<FilterType> filters = new LinkedList<FilterType>();

		public void addFilter(final String name, final FilterType filter) {
			names.add(name);
			filters.add(filter);
		}
	}

	public static class FilterAdjuster {
		private final Adjuster<? extends GPUImageFilter> adjuster;

		public FilterAdjuster(final GPUImageFilter filter) {
			if (filter instanceof GPUImageExposureFilter) {
				// 曝光
				adjuster = new ExposureAdjuster().filter(filter);
			} else if (filter instanceof GPUImageContrastFilter) {
				// 对比
				adjuster = new ContrastAdjuster().filter(filter);
			} else if (filter instanceof GPUImageBrightnessFilter) {
				// 亮度
				adjuster = new BrightnessAdjuster().filter(filter);
			} else if (filter instanceof GPUImageHueFilter) {
				// 色调
				adjuster = new HueAdjuster().filter(filter);
			} else if (filter instanceof GPUImageSaturationFilter) {
				// 饱和
				adjuster = new SaturationAdjuster().filter(filter);
			} else if (filter instanceof GPUImageHighlightShadowFilter) {
				// 阴影
				adjuster = new HighlightShadowAdjuster().filter(filter);
			} else if (filter instanceof GPUImageWhiteBalanceFilter) {
				// 色温
				adjuster = new WhiteBalanceAdjuster().filter(filter);
			} else if (filter instanceof GPUImageVignetteFilter) {
				// 暗角
				adjuster = new VignetteAdjuster().filter(filter);
			} else {
				adjuster = null;
			}
		}

		public void adjust(final int percentage) {
			if (adjuster != null) {
				adjuster.adjust(percentage);
			}
		}

		private abstract class Adjuster<T extends GPUImageFilter> {
			private T filter;

			@SuppressWarnings("unchecked")
			public Adjuster<T> filter(final GPUImageFilter filter) {
				this.filter = (T) filter;
				return this;
			}

			public T getFilter() {
				return filter;
			}

			public abstract void adjust(int percentage);

			protected float range(final int percentage, final float start,
					final float end) {
				return (end - start) * percentage / 100.0f + start;
			}

			protected int range(final int percentage, final int start,
					final int end) {
				return (end - start) * percentage / 100 + start;
			}
		}

		// 色调
		private class HueAdjuster extends Adjuster<GPUImageHueFilter> {
			@Override
			public void adjust(final int percentage) {
				getFilter().setHue(range(percentage, 0.0f, 360.0f));
			}
		}

		// 对比
		private class ContrastAdjuster extends Adjuster<GPUImageContrastFilter> {
			@Override
			public void adjust(final int percentage) {
				getFilter().setContrast(range(percentage, 0.5f, 3.0f));
			}
		}

		// 亮度
		private class BrightnessAdjuster extends
				Adjuster<GPUImageBrightnessFilter> {
			@Override
			public void adjust(final int percentage) {
				getFilter().setBrightness(range(percentage, -0.8f, 0.8f));
			}
		}

		// 饱和
		private class SaturationAdjuster extends
				Adjuster<GPUImageSaturationFilter> {
			@Override
			public void adjust(final int percentage) {
				getFilter().setSaturation(range(percentage, 0.0f, 2.0f));
			}
		}

		// 曝光
		private class ExposureAdjuster extends Adjuster<GPUImageExposureFilter> {
			@Override
			public void adjust(final int percentage) {
				getFilter().setExposure(range(percentage, -2.0f, 2f));
			}
		}

		// 阴影
		private class HighlightShadowAdjuster extends
				Adjuster<GPUImageHighlightShadowFilter> {
			@Override
			public void adjust(final int percentage) {
				getFilter().setShadows(range(percentage, 0.0f, 1.0f));
				getFilter().setHighlights(range(percentage, 0.0f, 1.0f));
			}
		}

		// 色温
		private class WhiteBalanceAdjuster extends
				Adjuster<GPUImageWhiteBalanceFilter> {
			@Override
			public void adjust(final int percentage) {
				getFilter().setTemperature(range(percentage, 2000.0f, 8000.0f));
				// getFilter().setTint(range(percentage, -100.0f, 100.0f));
			}
		}

		// 暗角
		private class VignetteAdjuster extends Adjuster<GPUImageVignetteFilter> {
			@Override
			public void adjust(final int percentage) {
				getFilter().setVignetteStart(range(percentage, 0.0f, 0.75f));
			}
		}

	}
}
