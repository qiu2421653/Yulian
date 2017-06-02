package com.njkj.yulian.utils;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.Uri;
import android.provider.Settings.Secure;
import android.telephony.TelephonyManager;

import com.njkj.yulian.MainApplication;

public class AppUtil {

	/**
	 * 打电话
	 * */
	public static void callTelphone(Context context, String tel) {
		try {
			Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:"
					+ tel));
			context.startActivity(intent);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 获取手机型号 SM-G900FD
	 * */
	public static String getModelName() {
		return android.os.Build.MODEL; // GT-I9300
	}

	/**
	 * IMEI
	 * */
	public static String getIMEI() {
		TelephonyManager telephonyManager = (TelephonyManager) MainApplication
				.getContext().getSystemService(Context.TELEPHONY_SERVICE);
		String imei = telephonyManager.getDeviceId();
		// 如果没有imei码 取机器码
		if (imei == null || imei.length() <= 0) {
			// 取机器码
			imei = getAndroidId();
		}
		return imei;
	}

	/**
	 * 机器码
	 * */
	public static String getAndroidId() {
		String android_id = Secure.getString(MainApplication.getContext()
				.getContentResolver(), Secure.ANDROID_ID);
		return android_id;
	}

	/**
	 * 获取版本名
	 * 
	 * @return 当前应用的版本名:1.0
	 */
	public static double getVersionName() {
		try {
			PackageManager manager = MainApplication.getContext()
					.getPackageManager();
			PackageInfo pkg = manager.getPackageInfo(MainApplication
					.getContext().getPackageName(), 0);
			return Double.parseDouble(pkg.versionName);
		} catch (NameNotFoundException e) {
			return 1.0;
		}
	}

	/**
	 * 获取版本号
	 * 
	 * @return 当前应用的版本号:1
	 */
	public static int getVersionCode() {
		try {
			PackageManager manager = MainApplication.getContext()
					.getPackageManager();
			PackageInfo pkg = manager.getPackageInfo(MainApplication
					.getContext().getPackageName(), 0);
			return pkg.versionCode;
		} catch (Exception e) {
			return 1;
		}
	}

	/**
	 * 获取包名
	 * 
	 * @return com.hkkj.didipark
	 */
	public static String getPackageInfo() {
		try {
			String pkName = MainApplication.getContext().getPackageName();
			return pkName;
		} catch (Exception e) {
		}
		return null;
	}

}
