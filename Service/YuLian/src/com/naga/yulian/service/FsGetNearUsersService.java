package com.naga.yulian.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.naga.yulian.dao.FsGetNearUsersMapper;
import com.naga.yulian.vo.FsGetNearUsersInVo;
import com.naga.yulian.vo.FsGetNearUsersOutVo;

/**
 * 获取附近的人
 * 
 * @author Qiu
 *
 */
@Service("FsGetNearUsersService")
public class FsGetNearUsersService {

	@Autowired
	private FsGetNearUsersMapper fsGetNearUsersMapper;
	//[38.82041980138961, 121.47552743384686, 38.91027219861039, 121.59092656615313]
	public List<FsGetNearUsersOutVo> getNearUsersByDis(FsGetNearUsersInVo vo) {
		double[] around = getAround(vo.getLatitude(), vo.getLongitude(), vo.getRadius());
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("minLat", around[0]);
		map.put("minLon", around[1]);
		map.put("maxLat", around[2]);
		map.put("maxLon", around[3]);
		map.put("userId", vo.getUserId());
		map.put("lat", vo.getLatitude());
		map.put("lon", vo.getLongitude());
		return fsGetNearUsersMapper.getNearUsersByDis(map);
	}

	/**
	 * 生成以中心点为中心的四方形经纬度
	 * 
	 * @param lat
	 *            纬度
	 * @param lon
	 *            精度
	 * @param raidus
	 *            半径（以米为单位）
	 * @return
	 */
	private double[] getAround(double lat, double lon, int raidus) {
		Double latitude = lat;
		Double longitude = lon;

		Double degree = (24901 * 1609) / 360.0;
		double raidusMile = raidus;

		Double dpmLat = 1 / degree;
		Double radiusLat = dpmLat * raidusMile;
		Double minLat = latitude - radiusLat;
		Double maxLat = latitude + radiusLat;

		Double mpdLng = degree * Math.cos(latitude * (Math.PI / 180));
		Double dpmLng = 1 / mpdLng;
		Double radiusLng = dpmLng * raidusMile;
		Double minLng = longitude - radiusLng;
		Double maxLng = longitude + radiusLng;
		return new double[] { minLat, minLng, maxLat, maxLng };
	}

}
