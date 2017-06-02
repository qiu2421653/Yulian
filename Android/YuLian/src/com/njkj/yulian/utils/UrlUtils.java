package com.njkj.yulian.utils;

import android.os.Environment;

public class UrlUtils {
	public static final  String PATH_NAME="login.jpg";//图片名
	 public  static final String SAVE_REAL_PATH = Environment.getExternalStorageDirectory() + "/download_test/";//保存的确切位置
	 public static final String httpPersonPath="https://apicn.faceplusplus.com/v2/person/create";//创建pserson
	 public static final String delete="https://apicn.faceplusplus.com/v2/person/delete";//删除pserson——name
	 public static final String path = "http://apicn.faceplusplus.com/v2/detection/detect";//获取图片的face——id
	 public static final String ADDPERSON="https://apicn.faceplusplus.com/v2/person/add_face";//添加一张人脸
	 public static final String GETSESSION="https://apicn.faceplusplus.com/v2/info/get_session";//获取训练结果
	 public static final String TRAINVERIFY="https://apicn.faceplusplus.com/v2/train/verify";//开始训练
	 public static final String RECONGNTION="https://apicn.faceplusplus.com/v2/recognition/verify";//验证
	 public static final String DETECTION="http://apicn.faceplusplus.com/v2/detection/detect";//查看人脸的信息用于返回face——id
	 public static final String API_KEY="96ba63328948c7b5772c1b65cb1cae48";    
	 public static final String API_SECRET="swt_M802UeQz089qYsIVTCl98kfnkX8z";
	 public static final String PSERSONNAME="Loginname";//face++创建一个person名
	 public static final String GROUPNAME="Groupname";//face++创建一个group
	 public static final String TAG="Login";//face++ Person相关的tag
	 public static final String SHARENAME="loginmessage";
	 public static final String SHARETYPE="logintype";
	 
}
