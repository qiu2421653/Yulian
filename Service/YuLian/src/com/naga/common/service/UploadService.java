package com.naga.common.service;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.naga.common.dao.UpldFileMngMapper;
import com.naga.common.entity.UpldFileMng;
import com.naga.common.util.DateUtil;
import com.naga.common.util.MyCommonUtil;
import com.naga.common.util.SystemConfigUtil;
import com.naga.common.util.UploadUtil;
import com.naga.common.vo.UploadFile;

/**
 * 上传图片共同方法
 * 
 * @author 江風成 2016-4-12
 *
 */
@Service("UploadService")
public class UploadService {
	@Autowired
	private SystemConfigUtil systemConfigUtil;
	@Autowired
	private UpldFileMngMapper upldFileMngMapper;

	
	/**
	 * 获取文件服务器地址
	 * @return
	 */
	public String getFileServerUrl() {
		return systemConfigUtil.getFileServerUrl();
	}
	/**
	 * 上传图片
	 * @param request
	 * @param type
	 * @param stfNum
	 * @param files
	 * @return
	 */
	
	public List<String> saveFile(HttpServletRequest request, String type, String stfNum, MultipartFile[]  files) {
		List<String> urls = new ArrayList<String>();
		List<UploadFile> list = UploadUtil.uploadFiles(files, request, systemConfigUtil.getBasepath(),buildSubDir(type));
		if (list != null && list.size() > 0) {
			String time = DateUtil.getDateStr();
			for (UploadFile file : list) {
				if (file != null) {
					UpldFileMng ufm = new UpldFileMng();
					ufm.setoId(MyCommonUtil.makeUUID());
					ufm.setGenFileNm(file.getFilePath());
					ufm.setUpldFileNm(file.getFileRealName());
					ufm.setDelFlg("0");
					ufm.setCrtdTm(time);
					ufm.setCrtdUsr(stfNum);
					ufm.setUpdtTm(time);
					ufm.setUpdtUsr(stfNum);
					upldFileMngMapper.insert(ufm);
					urls.add(file.getFilePath());
				} else {
					urls.add("");
				}
			}
		}
		return urls;
	}

	/**
	 * 讀取圖片
	 * 
	 * @param response
	 * @param request
	 * @return
	 */
	public void urlRead(HttpServletResponse response, HttpServletRequest request) {
		UploadUtil.readImge(response, request, systemConfigUtil.getBasepath());
	}
	/**
	 * 下载并删除logs
	 * 
	 * @param response
	 * @param request
	 * @return
	 */
	public void delLogs(HttpServletResponse response, HttpServletRequest request) {
		UploadUtil.delRepImport(response, request, systemConfigUtil.getBasepath());
	}
	/**
	 * 获取配置的功能模块目录地址
	 * 
	 * @param type
	 * @return
	 */
	private String buildSubDir(String type) {
		if ("userfhotourl".equals(type)) {
			type = systemConfigUtil.getUserfhotourl();
		} else if ("bankfhotourl".equals(type)) {
			type = systemConfigUtil.getBankfhotourl();
		} else if ("idcardpospic".equals(type)) {
			type = systemConfigUtil.getIdcardposurl();
		} else if ("attachment".equals(type)) {
			type = systemConfigUtil.getAttachment();
		} else {
			type = systemConfigUtil.getOthers();
		}
		return type;
	}

}