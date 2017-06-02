package com.naga.common.util;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.HandlerMapping;

import com.naga.common.vo.UploadFile;

/**
 * 文件上传工具
 * 
 * @author HHB
 */
public class UploadUtil {

	private static final List<String> IMAGE_TYPE = Arrays.asList(new String[] { "JPG", "JPEG", "PNG", "BMP", "GIF",
			"TIFF", "PSD", "SVG", "PCX", "DXF", "WMF", "EMF", "LIC", "FLI", "FLC", "EPS", "TGA", "WEBP" });

	private static final List<String> VIDEO_TYPE = Arrays
			.asList(new String[] { "AIFF", "AVI", "MOV", "MPEG", "MPG", "QT", "RAM", "VIV", "DAT", "RA", "RM", "RMVB",
					"ASF", "WMV", "ISO", "BIN", "IMG", "TAO", "DAO", "CIF", "FCD", "VCD", "SVCD", "DVD", "MPE", "MPEG1",
					" MPEG2", "MPEG3", "MPEG4", "VOB", "BUP", "IFO", "MP4", "3GP", "ASF", "DIVX", "MKV" });

	private static final List<String> AUDIO_TYPE = Arrays.asList(new String[] { "WMA", "MP3", "WAV", "MID", "MP1",
			"MP2", "MIDI", "AIF", "ST3", "XT", "S3M", "FAR", "669", "PAE", "FLAC" });

	private static final String IMAGES = "images";
	private static final String VIDEOS = "videos";
	private static final String AUDIOS = "audios";
	private static final String OTHERS = "others";

	public static List<UploadFile> uploadFiles(MultipartFile[] files, HttpServletRequest request, String uploadBasepath,
			String subDir) {
		List<UploadFile> list = new ArrayList<UploadFile>();
		if (files != null && files.length > 0) {
			for (MultipartFile uploadFile : files) {
				list.add(uploadFile(uploadFile, request, uploadBasepath, subDir));
			}
		}
		return list;
	}

	public static List<String> uploadFiles(MultipartFile[] files, String uploadBasepath, HttpServletRequest request,
			String subDir) {
		List<String> list = new ArrayList<String>();
		if (files != null && files.length > 0) {
			for (MultipartFile uploadFile : files) {
				list.add(uploadFile(uploadFile, uploadBasepath, request, subDir));
			}
		}
		return list;
	}

	/**
	 * 文件上传，成功返回文件属性，否则返回null
	 * 
	 * @param uploadFile
	 * @return
	 */
	public static UploadFile uploadFile(MultipartFile uploadFile, HttpServletRequest request, String uploadBasepath,
			String subDir) {
		if (uploadFile == null || uploadFile.isEmpty()) {
			return null;
		}
		UploadFile uf = null;
		String filePath = uploadFile(uploadFile, uploadBasepath, request, subDir);
		BufferedImage src = null;
		try {
			src = javax.imageio.ImageIO.read(uploadFile.getInputStream());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        int height = src.getHeight(null); // 得到源图高
        int width = src.getWidth(null); // 得到源图高
		if (StringUtils.isNotBlank(filePath)) {
			uf = new UploadFile();
			uf.setFilePath(filePath);
			uf.setFileRealName(uploadFile.getOriginalFilename());
			uf.setFileSize(uploadFile.getSize());
			uf.setFileType(uploadFile.getContentType() == null ? "" : uploadFile.getContentType());
			uf.setAddTime(new Date());
			uf.setHeight(height);
			uf.setWidth(width);
		}
		return uf;
	}

	/**
	 * 文件上传，成功返回文件路径，否则返回 ""
	 * 
	 * @param uploadFile
	 * @return
	 */
	public static String uploadFile(MultipartFile uploadFile, String uploadBasepath, HttpServletRequest request,
			String subDir) {
		String filePath = "";
		if (uploadFile == null || uploadFile.isEmpty()) {
			return filePath;
		}
		try {
			filePath = buildFilePath(uploadFile, subDir);
			File file = new File(uploadBasepath + filePath);
			FileUtils.copyInputStreamToFile(uploadFile.getInputStream(), file);
		} catch (IOException e) {
			filePath = "";
		}
		return filePath;
	}

	private static String getBasePath(HttpServletRequest request, String uploadBasepath) {
		uploadBasepath = StringUtils.isBlank(uploadBasepath) ? "" : uploadBasepath.trim().replaceAll("\\\\", "/");
		String webProjectRealPath = "";
		String basePath = "";
		if (webProjectRealPath.endsWith("/")) {
			if (!uploadBasepath.startsWith("/")) {
				basePath = webProjectRealPath + uploadBasepath;
			} else {
				basePath = webProjectRealPath.substring(0, webProjectRealPath.lastIndexOf("/")) + uploadBasepath;
			}
		} else {
			if (uploadBasepath.startsWith("/")) {
				basePath = webProjectRealPath + uploadBasepath;
			} else {
				basePath = webProjectRealPath + uploadBasepath.substring(1);
			}
		}
		return basePath;
	}

	/*
	 * 这么做是为了防止URL中包含中文或者特殊字符（/等）时，匹配不了的问题
	 */
	public static String extractPathFromPattern(final HttpServletRequest request) {
		String path = (String) request.getAttribute(HandlerMapping.PATH_WITHIN_HANDLER_MAPPING_ATTRIBUTE);
		String bestMatchPattern = (String) request.getAttribute(HandlerMapping.BEST_MATCHING_PATTERN_ATTRIBUTE);
		return new AntPathMatcher().extractPathWithinPattern(bestMatchPattern, path);
	}
	
	/**
	 * 读取本地图片
	 * 
	 * @param response
	 * @param uploadBasepath
	 * @param url
	 * @return 
	 * @return
	 */
	public static String returnPath(HttpServletResponse response, HttpServletRequest request, String uploadBasepath) {
		return getBasePath(request, uploadBasepath);
	}

	/**
	 * 读取本地图片
	 * 
	 * @param response
	 * @param uploadBasepath
	 * @param url
	 * @return
	 */
	public static void readImge(HttpServletResponse response, HttpServletRequest request, String uploadBasepath) {
		ByteArrayOutputStream bytestream = null;
		OutputStream os = null;
		int ch = -1;
		try {
			os = response.getOutputStream();
			@SuppressWarnings("resource")
			InputStream ins = new FileInputStream(
					getBasePath(request, uploadBasepath) + extractPathFromPattern(request));
			// 转二进制
			bytestream = new ByteArrayOutputStream();
			while ((ch = ins.read()) != -1) {
				bytestream.write(ch);
			}
			os.write(bytestream.toByteArray());
		} catch (IOException e) {
			e.printStackTrace();
		} finally {// HHB 2016-04-19 修改异常处理结构
			try {
				if (bytestream != null) {
					bytestream.close();
				}
				if (os != null) {
					os.close();
				}
				bytestream = null;
				os = null;
			} catch (Exception e) {
			}
		}
	}

	/**
	 * 删除本地导入错误信息log文件
	 * 
	 * @param response
	 * @param uploadBasepath
	 * @param url
	 * @return
	 */
	public static void delRepImport(HttpServletResponse response, HttpServletRequest request, String uploadBasepath) {
		ByteArrayOutputStream bytestream = null;
		OutputStream os = null;
		File dellog = new File(getBasePath(request, uploadBasepath) + extractPathFromPattern(request));
		int ch = -1;
		try {
			os = response.getOutputStream();
			@SuppressWarnings("resource")
			InputStream ins = new FileInputStream(
					getBasePath(request, uploadBasepath) + extractPathFromPattern(request));
			// 转二进制
			bytestream = new ByteArrayOutputStream();
			while ((ch = ins.read()) != -1) {
				bytestream.write(ch);
			}
			os.write(bytestream.toByteArray());
			ins.close();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {// HHB 2016-04-19 修改异常处理结构
			try {
				
				if (bytestream != null) {
					bytestream.close();
				}
				if (os != null) {
					os.close();
				}
				if (dellog != null) {
					dellog.delete();
				}
				bytestream = null;
				os = null;
			} catch (Exception e) {
			}
		}
	}

	private static final String buildFilePath(MultipartFile uploadFile, String subDir) {
		String fileRealName = uploadFile.getOriginalFilename();
		String fileType = fileRealName.substring(fileRealName.lastIndexOf(".") + 1, fileRealName.length());
		String dateStr = new SimpleDateFormat("yyyyMMdd").format(new Date());
		String filePath = buildSubDir(subDir) + dateStr + "/"
				+ UUID.randomUUID().toString() + "." + fileType;
		return filePath;
	}

	private static String buildSubDir(String subDir) {
		if (subDir == null || "".equals(subDir.trim())) {
			return "";
		}
		while (subDir.trim().indexOf("\\\\") != -1 || subDir.trim().indexOf("//") != -1) {
			subDir = subDir.trim().replaceAll("\\\\", "/").replace("//", "/");
		}
		if (subDir.startsWith("/")) {
			subDir = subDir.replaceFirst("/", "");
		}
		if (!subDir.endsWith("/")) {
			subDir = subDir + "/";
		}
		return subDir;
	}

	private static final String getFirstDirByFileType(String fileType) {
		if (fileType != null) {
			fileType = fileType.trim().toUpperCase();
			if (IMAGE_TYPE.indexOf(fileType) >= 0) {
				return IMAGES;
			}
			if (AUDIO_TYPE.indexOf(fileType) >= 0) {
				return AUDIOS;
			}
			if (VIDEO_TYPE.indexOf(fileType) >= 0) {
				return VIDEOS;
			}
		}
		return OTHERS;
	}

    /**
     * 获取图片宽度
     * @param file  图片文件
     * @return 宽度
     */
    public static int getImgWidth(File file) {
        InputStream is = null;
        BufferedImage src = null;
        int ret = -1;
        try {
            is = new FileInputStream(file);
            src = javax.imageio.ImageIO.read(is);
            ret = src.getWidth(null); // 得到源图宽
            is.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ret;
    }
      
      
    /**
     * 获取图片高度
     * @param uploadFile  图片文件
     * @return 高度
     */
    public static int getImgHeight(String uploadFile) {
        InputStream is = null;
        BufferedImage src = null;
        int ret = -1;
        try {
            is = new FileInputStream(uploadFile);
            src = javax.imageio.ImageIO.read(is);
            ret = src.getHeight(null); // 得到源图高
            is.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ret;
    }
}
