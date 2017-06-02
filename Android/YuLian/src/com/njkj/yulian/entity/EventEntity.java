package com.njkj.yulian.entity;

import com.njkj.yulian.utils.GPUImageFilterTools.FilterAdjuster;

/**
 * ==============================
 * 
 * @author Qiu
 * 
 * @Package com.njkj.yulian.entity
 * 
 * @Description:EventBus
 * 
 * @date 2016-4-14 上午11:29:50
 * 
 * @version 1.0 ==============================
 */
public class EventEntity extends BaseEntity {

	// 饱和度编辑
	public static class EditAdjust {
		public FilterEntity filterEntity;
		public FilterAdjuster filterAdjuster;
		public boolean isCancel; // 取消

		public EditAdjust() {

		}

		public EditAdjust(FilterEntity filterEntity) {
			this.filterEntity = filterEntity;
		}
	}

	// 推荐
	public static class Better {
		public FilterEntity filterEntity;

		public Better() {
		}

		public Better(FilterEntity filterEntity) {
			this.filterEntity = filterEntity;
		}
	}

	// 滤镜
	public static class Filters {
		public FilterEntity filterEntity;

		public Filters() {
		}

		public Filters(FilterEntity filterEntity) {
			this.filterEntity = filterEntity;
		}
	}

	// 饱和度...
	public static class Adjust {
		public FilterEntity filterEntity;

		public Adjust() {
		}

		public Adjust(FilterEntity filterEntity) {
			this.filterEntity = filterEntity;
		}
	}

	// 在线视频
	public static class NetVideo {
		public boolean isEdit;

		public NetVideo(boolean isEdit) {
			this.isEdit = isEdit;
		}
	}

	// 本地视频
	public static class LocVideo {
		public boolean isEdit;

		public LocVideo(boolean isEdit) {
			this.isEdit = isEdit;
		}
	}

}
