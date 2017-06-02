package com.naga.common.util;

import org.springframework.beans.factory.annotation.Value;

/**
 * Spring的工具类，用来获得properties配置文件中的property信息
 */
public class SystemConfigUtil {
	
    /** 权限 存放token的Cookie名  */
    @Value("${system.authority.tokenName}")
    private String tokenName = null;

    /**
     * @return the tokenName
     */
    public String getTokenName() {
        return tokenName;
    }
    
    /** 权限 URL中参数的替换符  */
    @Value("${system.authority.url.paramMark}")
    private String paramMark = null;
	/**
     * @return the paramMark
     */
    public String getParamMark() {
        return paramMark;
    }
    /**
     * 销售报表模板下载路径
     */
    @Value("${serverModuleFileUrl}")
    private String serverModuleFile = null;
    /**
     * 销售报表模板上传路径
     */
    @Value("${userFileUploadUrl}")
    private String userFileUpload = null;
    
    @Value("${salePassWord}")
    private String salePassWord=null;
    
	//默认路径
	@Value("${system.upload.basepath}")
	private String basepath = null;
	//帖子图片路径
	@Value("${system.upload.topicpath}")
	private String topicpath = null;
	//外网路径
	@Value("${system.upload.webpath}")
	private String webpath = null;
	// 个人头像照片路径
	@Value("${system.upload.basepath.userfhotourl}")
	private String userfhotourl = null;
	// 银行卡照片路径
	@Value("${system.upload.basepath.bankfhotourl}")
	private String bankfhotourl = null;
	// 身份证正面图片
	@Value("${system.upload.basepath.idcardposurl}")
	private String idcardposurl = null;
	// 附件
	@Value("${system.upload.basepath.attachment}")
	private String attachment = null;
	// 附件
	@Value("${system.upload.basepath.others}")
	private String others = null;
	//照片显示路径
	@Value("${system.upload.fileServerUrl}")
	private String fileServerUrl = null;

	public String getFileServerUrl() {
		return fileServerUrl;
	}
    public String getBasepath() {
		return basepath;
	}
    public String getTopicpath() {
    	return topicpath;
    }
    public String getWebpath() {
    	return webpath;
    }
	public String getUserfhotourl() {
		return userfhotourl;
	}
	public String getBankfhotourl() {
		return bankfhotourl;
	}
	public String getIdcardposurl() {
		return idcardposurl;
	}
	public String getAttachment() {
		return attachment;
	}
	public String getOthers() {
		return others;
	}
	public String getSalePassWord() {
		return salePassWord;
	}
	public  String getServerModuleFile() {
		return serverModuleFile;
	}
	public String getUserFileUpload() {
		return userFileUpload;
	}
    
}