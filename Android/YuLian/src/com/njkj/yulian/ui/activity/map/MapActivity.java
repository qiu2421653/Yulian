package com.njkj.yulian.ui.activity.map;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BaiduMap.OnMapClickListener;
import com.baidu.mapapi.map.BaiduMap.OnMapStatusChangeListener;
import com.baidu.mapapi.map.BaiduMap.OnMarkerClickListener;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapPoi;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MarkerOptions.MarkerAnimateType;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationConfiguration.LocationMode;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.poi.PoiNearbySearchOption;
import com.baidu.mapapi.search.poi.PoiSearch;
import com.baidu.mapapi.search.poi.PoiSortType;
import com.njkj.yulian.MainApplication;
import com.njkj.yulian.R;
import com.njkj.yulian.controller.MapController;
import com.njkj.yulian.core.cache.ImageDownloader;
import com.njkj.yulian.core.callback.SimpleCallback;
import com.njkj.yulian.core.lib.volley.VolleyError;
import com.njkj.yulian.core.lib.volley.toolbox.ImageLoader.ImageContainer;
import com.njkj.yulian.core.lib.volley.toolbox.ImageLoader.ImageListener;
import com.njkj.yulian.entity.LatlngEntity;
import com.njkj.yulian.entity.RetEntity;
import com.njkj.yulian.ui.activity.OtherLoveActivity;

/**
 * ==============================
 * 
 * @author Qiu
 * 
 * @Package com.njkj.yulian.ui.fragment.search
 * 
 * @Description:地图展示
 * 
 * @date 2016-6-7 上午11:18:05
 * 
 * @version 1.0 ==============================
 */
public class MapActivity extends BaseBitMapActivity implements OnClickListener {

	private MapView mMapView; // 一个显示地图的视图（View）
	private BaiduMap mBaiduMap; // BaiduMap 地图对象的操作方法与接口
	private Button location_resh;// 回到原位

	private LatLng mCurrentLatLng;// 当前经纬度坐标
	private PoiSearch mPoiSearch; // poi检索核心类
	private boolean isSearch = true; // 设置是否开始搜索
	private boolean isEnd = false; // 设置是否退出程序
	private ArrayList<LatlngEntity> latlngEntities;// poi点集合

	private int currentPage = 0; // 查询分页当前页
	private int pageCount = 10; // 默认显示10条

	private MapController mapController;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search_map_fragment);
		initViews();
		initData();
		initOnClick();
	}

	protected void initViews() {
		location_resh = (Button) findViewById(R.id.location_resh);
		// 地图初始化
		mMapView = (MapView) findViewById(R.id.mMapView);
		// 地图显示事件监听器。 该接口监听地图显示事件，用户需要实现该接口以处理相应事件。
		mBaiduMap = mMapView.getMap();
	}

	protected void initData() {
		mapController = new MapController();
		// poi点集合
		latlngEntities = new ArrayList<LatlngEntity>();
		// poi搜索
		mPoiSearch = PoiSearch.newInstance();
		// 百度图片
		baiNor = BitmapDescriptorFactory
				.fromResource(R.drawable.comment_emo_like_press);
		getCurrentLatlng();
	}

	// 取得当前坐标点
	public void getCurrentLatlng() {
		try {
			if (TextUtils.isEmpty(mConfigDao.getString("latitude"))
					|| TextUtils.isEmpty(mConfigDao.getString("longitude"))) {
				requestLocation(true);
			} else {
				// 从xml中拿到当前的坐标点
				mCurrentLatLng = new LatLng(Double.parseDouble(mConfigDao
						.getString("latitude")), Double.parseDouble(mConfigDao
						.getString("longitude")));
				init();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void init() {
		// 初始化
		initMap();
		// 百度地图监听事件
		initMapClickEvent();
		// 百度地图点击事件
		initMarkerClickEvent();
		// TODO 延时先搜索一次
		handler.postDelayed(new Runnable() {
			@Override
			public void run() {
				getNearUsers();
			}
		}, 1500);
	}

	protected void initOnClick() {
		location_resh.setOnClickListener(this);
	}

	@Override
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.location_resh:
			location_resh();
			break;
		}
	}

	@Override
	public void onDestroy() {
		// 在activity执行onDestroy时执行mMapView.onDestroy()，实现地图生命周期管理
		mMapView.onDestroy();
		handler.removeCallbacksAndMessages(null);
		super.onDestroy();
	}

	@Override
	public void onResume() {
		super.onResume();
		// 在activity执行onResume时执行mMapView. onResume ()，实现地图生命周期管理
		mMapView.onResume();
	}

	@Override
	public void onPause() {
		super.onPause();
		// 在activity执行onPause时执行mMapView. onPause ()，实现地图生命周期管理
		mMapView.onPause();
	}

	/**
	 * 初始化一些参数
	 * */
	public void initMap() {
		// 设置地图的缩放等级zoom地图缩放级别 3~20
		mBaiduMap.setMapStatus(MapStatusUpdateFactory
				.newMapStatus(new MapStatus.Builder().target(mCurrentLatLng)
						.zoom(18.f).build()));
		// 开启定位图层
		mBaiduMap.setMyLocationEnabled(true);
		// 定位图层显示方式_普通态： 更新定位数据时不对地图做任何操作
		LocationMode mCurrentMode = LocationMode.NORMAL;
		// 设置中心点为默认
		mBaiduMap.setMyLocationConfigeration(new MyLocationConfiguration(
				mCurrentMode, true, null));
		// 我的坐标点
		MyLocationData locData = new MyLocationData.Builder().accuracy(100)// 获取定位精度
				// 此处设置开发者获取到的方向信息，顺时针0-360
				.direction(0).latitude(mCurrentLatLng.latitude)// 获取纬度坐标
				.longitude(mCurrentLatLng.longitude).build();// 获取精度坐标

		mBaiduMap.setMyLocationData(locData);

		mBaiduMap.animateMapStatus(MapStatusUpdateFactory
				.newLatLng(mCurrentLatLng));
	}

	// 百度地图事件监听(滑动|点击)
	public void initMapClickEvent() {
		// 地图事件改变监听
		mBaiduMap.setOnMapStatusChangeListener(new OnMapStatusChangeListener() {
			@Override
			public void onMapStatusChange(MapStatus mapStatus) {
			}

			@Override
			public void onMapStatusChangeFinish(MapStatus mapStatus) {
				float endZoom = mapStatus.zoom;
				// 得到缩放等级
				float zoom = mapStatus.zoom;
				if (zoom < 11) {
					isSearch = false;
				} else {
					isSearch = true;
				}
			}

			@Override
			public void onMapStatusChangeStart(MapStatus mapStatus) {
				// 停止刷新
				isSearch = false;
			}
		});
		// 地图事件点击监听
		mBaiduMap.setOnMapClickListener(new OnMapClickListener() {
			@Override
			public void onMapClick(LatLng arg0) {
			}

			@Override
			public boolean onMapPoiClick(MapPoi arg0) {
				return false;
			}
		});
	}

	/**
	 * 初始化marker监听事件
	 * */
	public void initMarkerClickEvent() {
		// 对Marker的点击
		mBaiduMap.setOnMarkerClickListener(new OnMarkerClickListener() {
			@Override
			public boolean onMarkerClick(final Marker marker) {

				// TODO 获得marker中的数据
				LatlngEntity bean = (LatlngEntity) marker.getExtraInfo().get(
						"poiInfo");
				LatLng latLng = new LatLng(bean.latitude, bean.longitude);
				// 移动过去
				mBaiduMap.animateMapStatus(MapStatusUpdateFactory
						.newLatLng(latLng));
				// 跳转
				Intent intent = new Intent(MapActivity.this,
						OtherLoveActivity.class);
				intent.putExtra("userID", bean.userId);
				startActivity(intent);
				return true;
			}
		});
	}

	/**
	 * 回到原位
	 * */
	public void location_resh() {
		mBaiduMap.animateMapStatus(MapStatusUpdateFactory
				.newLatLng(mCurrentLatLng));
	}

	/**
	 * 设置poi参数(附近搜索参数)
	 */
	private PoiNearbySearchOption setPoiOption(LatLng latlng, String parkInfo,
			int number, int radius, int pageNum) {
		// PoiSearch需要设置相关参数,比如关键字,距离等
		PoiNearbySearchOption pnso = new PoiNearbySearchOption();
		pnso.pageCapacity(number);// 设置每页容量，默认为每页10条
		pnso.keyword(parkInfo);// 检索关键字
		pnso.location(latlng);// 检索位置
		pnso.radius(radius);// 设置检索的半径范围
		pnso.pageNum(pageNum);// 分页编号
		pnso.sortType(PoiSortType.distance_from_near_to_far);// 搜索结果排序规则
		return pnso;
	}

	/**
	 * @Title: getNearUsers
	 * @Description: 获取附近的人
	 * @param
	 * @return void
	 * @throws
	 */
	private void getNearUsers() {
		String userId = mConfigDao.getString("userID");
		mapController.getNearUsers(getString(R.string.FsGetNearUses),
				pageCount, currentPage, userId, mCurrentLatLng.latitude,
				mCurrentLatLng.longitude,
				getResources().getInteger(R.integer.poiRadius),
				new SimpleCallback() {
					@Override
					public void onCallback(Object data) {
						if (data == null) {
							Toast.makeText(MainApplication.getContext(),
									getString(R.string.error), 0).show();
						} else {
							RetEntity<LatlngEntity> entity = (RetEntity<LatlngEntity>) data;
							if (entity.success) {
								if (entity.result.outDTO != null) {
									addInfosOverlay(entity.result.outDTO);
								}
							} else {
								Toast.makeText(MainApplication.getContext(),
										getString(R.string.error), 0).show();
							}
						}

					}
				});
	}

	/**
	 * 添加Marker
	 */
	public void addInfosOverlay(List<LatlngEntity> infos) {
		for (int i = 0; i < infos.size(); i++) {
			getBimap(infos.get(i));
		}
	}

	// TODO
	private void getBimap(final LatlngEntity entity) {
		int random = (int) (Math.random() * 10 + 1);
		String url = entity.hPic;

		ImageDownloader.getInstance().getImageLoader()
				.get(url, new ImageListener() {
					@Override
					public void onErrorResponse(VolleyError error) {
					}

					@Override
					public void onResponse(ImageContainer response,
							boolean isImmediate) {
						Bitmap bitmap = response.getBitmap();
						if (bitmap != null) {
							Message msg = handler.obtainMessage();
							Bundle data = new Bundle();
							data.putParcelable("bitmap", bitmap);
							data.putSerializable("entity", entity);
							msg.setData(data);
							handler.sendMessage(msg);
						}
					}
				});
	}

	Handler handler = new Handler() {
		@Override
		public void handleMessage(android.os.Message msg) {
			Bundle data = msg.getData();
			Bitmap bitmap = (Bitmap) data.get("bitmap");
			LatlngEntity entity = (LatlngEntity) data.getSerializable("entity");
			setMarker(entity, bitmap);
		};
	};

	/**
	 * @Title: setMarker
	 * @Description: 设置坐标marker
	 * @param @param entity
	 * @param @param bitmap
	 * @return void
	 * @throws
	 */
	private void setMarker(LatlngEntity entity, Bitmap bitmap) {
		LatLng latLng = new LatLng(entity.latitude, entity.longitude);
		// TODO 图标
		OverlayOptions overlayOptions = new MarkerOptions().position(latLng)
				.icon(getBitmapDescript(bitmap))
				.animateType(MarkerAnimateType.grow);
		Marker marker = (Marker) (mBaiduMap.addOverlay(overlayOptions));
		marker.setZIndex(5);

		MarkerOptions markerOptions = new MarkerOptions();

		Bundle bundle = new Bundle();
		bundle.putSerializable("poiInfo", entity);
		marker.setExtraInfo(bundle);
	}

}
