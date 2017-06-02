package com.njkj.yulian.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;

public class FileUtils {
	/**
	 * 读取表情配置文件
	 * 
	 * @param context
	 * @return
	 */
	public static List<String> getEmojiFile(Context context) {
		try {
			List<String> list = new ArrayList<String>();
			InputStream in = context.getResources().getAssets().open("emoji");// �ļ�����Ϊrose.txt
			BufferedReader br = new BufferedReader(new InputStreamReader(in,
					"UTF-8"));
			String str = null;
			while ((str = br.readLine()) != null) {
				list.add(str);
			}

			return list;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static FileUtils newInstance() {
		return new FileUtils();
	}

	private String SDPATH;
	private String FILESPATH;
	private Context mContext;

	public String getSDPATH() {
		return SDPATH;
	}

	public void setSDPATH(String value) {
		SDPATH = value;
	}

	public String getFILESPATH() {
		return FILESPATH;
	}

	public FileUtils() {
		SDPATH = Environment.getExternalStorageDirectory() + "/";
	}

	public FileUtils(Context context) {
		this();
		mContext = context;
		FILESPATH = mContext.getFilesDir().getPath() + "//";
	}

	// TODO ============基本功能============

	public boolean isFileExists(String fileName) {
		if (fileName == null || (fileName = fileName.trim()).equals("")) {
			return false;
		}
		File file = new File(fileName);
		return isFileExists(file);
	}

	public boolean isFileExists(File file) {
		if (file == null)
			return false;
		return file.exists();
	}

	public boolean isDirectory(File file) {
		if (file == null)
			return false;
		return file.isDirectory();
	}

	public boolean isDirectory(String fileName) {
		if (fileName == null || (fileName = fileName.trim()).equals("")) {
			return false;
		}
		File file = new File(fileName);
		return isDirectory(file);
	}

	public boolean isFile(File file) {
		if (file == null)
			return false;
		return file.isFile();
	}

	public boolean isFile(String fileName) {
		if (fileName == null || (fileName = fileName.trim()).equals("")) {
			return false;
		}
		File file = new File(fileName);
		return isFile(file);
	}

	public boolean createOrExistsFolder(File file) {
		if (file == null)
			return false;
		boolean result = false;

		if (isFileExists(file) && isDirectory(file)) {
			// 如果file存在且是文件夹，返回true
			return true;
		}
		// 如果文件夹不存在，创建文件夹
		if (file.mkdirs()) {
			// 创建成功返回true
			result = true;
		} else {
			// 创建失败返回false
			result = false;
		}
		return result;
	}

	public boolean createOrExistsFolder(String fileName) {
		if (fileName == null || (fileName = fileName.trim()).equals("")) {
			return false;
		}
		File file = new File(fileName);
		return createOrExistsFolder(file);
	}

	public boolean createOrExistsFile(File file) {
		if (file == null)
			return false;
		boolean result = false;
		if (isFileExists(file) && isFile(file)) {
			// 判断文件是否存在且为文件，如果存在结果为true
			return true;
		}
		// 如果文件不存在，创建文件
		// 先创建文件夹，否则不会成功
		File parentFile = file.getParentFile();
		if (!createOrExistsFolder(parentFile)) {
			// 如果父文件夹创建不成功，返回false
			return false;
		}
		try {
			if (file.createNewFile()) {
				// 创建成功返回true
				result = true;
			} else {
				// 创建失败返回false
				result = false;
			}
		} catch (IOException e) {
			e.printStackTrace();
			result = false;
		}
		return result;
	}

	public boolean createOrExistsFile(String fileName) {
		if (fileName == null || (fileName = fileName.trim()).equals("")) {
			return false;
		}
		File file = new File(fileName);
		return createOrExistsFile(file);
	}

	public boolean createFileByDeleteOldFileIfNeeded(File file) {
		if (file == null)
			return false;
		boolean result = false;
		if (isFileExists(file) && isFile(file)) {
			// 如果文件存在且是文件，则删除文件
			if (!file.delete()) {
				// 如果文件删除不成功,返回false
				return false;
			}
		}
		// 现在文件不存在了，创建文件
		return createOrExistsFile(file);
	}

	public boolean createFileByDeleteOldFileIfNeeded(String fileName) {
		if (fileName == null || (fileName = fileName.trim()).equals("")) {
			return false;
		}
		File file = new File(fileName);
		return createFileByDeleteOldFileIfNeeded(file);
	}

	public boolean moveFilesTo(File srcDir, File destDir) {
		if (srcDir == null)
			return false;
		if (destDir == null)
			return false;
		if (!isFileExists(srcDir) || !isDirectory(srcDir)) {
			// 如果源目录不存在或源目录不是文件夹，返回false
			return false;
		}
		if (!isFileExists(destDir) || !isDirectory(destDir)) {
			// 如果目标目录不存在或者目标目录不是文件夹，则创建目标目录
			if (!createOrExistsFolder(destDir)) {
				// 如果创建不成功
				return false;
			}
		}
		// 现在源目录和目标目录都准备好了
		File[] srcDirFiles = srcDir.listFiles();
		for (int i = 0; i < srcDirFiles.length; i++) {
			if (srcDirFiles[i].isFile()) {
				File oneDestFile = new File(destDir.getPath() + "//"
						+ srcDirFiles[i].getName());
				moveFileTo(srcDirFiles[i], oneDestFile);
				delFile(srcDirFiles[i]);
			} else if (srcDirFiles[i].isDirectory()) {
				File oneDestFile = new File(destDir.getPath() + "//"
						+ srcDirFiles[i].getName());
				moveFilesTo(srcDirFiles[i], oneDestFile);
				delDir(srcDirFiles[i]);
			}
		}
		return true;
	}

	public boolean moveFileTo(File srcFile, File destFile) {
		if (srcFile == null)
			return false;
		if (destFile == null)
			return false;
		boolean iscopy = copyFileTo(srcFile, destFile);
		if (!iscopy)
			return false;
		if (!delFile(srcFile)) {
			return false;
		}
		return true;
	}

	public boolean delFile(File file) {
		if (file == null)
			return false;
		if (!isFileExists(file)) {
			// 如果文件不存在，直接返回true
			return true;
		}
		if (file.isDirectory())
			// 如果是文件夹，返回false
			return false;
		return file.delete();
	}

	public boolean delDir(File dir) {
		if (dir == null)
			return false;
		if (!isFileExists(dir)) {
			// 如果文件不存在,直接返回true
			return true;
		}
		if (!isDirectory(dir)) {
			// 如果文件不是文件夹，返回false
			return false;
		}
		// 现在文件存在且是文件夹
		for (File file : dir.listFiles()) {
			if (file.isFile()) {
				if (!delFile(file)) {
					// 如果删除文件失败，返回false
					return false;
				}
			} else if (file.isDirectory()) {
				// 递归,如果删除文件夹失败，返回false
				if (!delDir(file)) {
					return false;
				}
			}
		}
		if (!dir.delete()) {
			// 如果删除文件夹失败，返回false
			return false;
		}
		return true;
	}

	public boolean copyFileTo(File srcFile, File destFile) {
		if (srcFile == null)
			return false;
		if (destFile == null)
			return false;
		if (!isFileExists(srcFile)) {
			return false;
		}

		if (!isFile(srcFile)) {
			return false;
		}
		if (isFileExists(destFile) && !isFile(destFile))
			// 如果目标文件存在，且不是文件，返回false
			return false;

		// 先创建文件夹，否则不会成功
		File parentFile = destFile.getParentFile();
		if (!createOrExistsFolder(parentFile)) {
			// 如果目标文件的父文件夹创建不成功，返回false
			return false;
		}

		FileInputStream fis = null;
		FileOutputStream fos = null;
		boolean result = false;
		try {
			fis = new FileInputStream(srcFile);
			fos = new FileOutputStream(destFile);
			int readLen = 0;
			byte[] buf = new byte[1024];
			while ((readLen = fis.read(buf)) != -1) {
				fos.write(buf, 0, readLen);
			}
			fos.flush();
			result = true;
		} catch (Exception e) {
			e.printStackTrace();
			result = false;
		} finally {
			try {
				fos.close();
				fis.close();
			} catch (Exception e) {
				e.printStackTrace();
				result = false;
			}

		}

		return result;
	}

	public boolean copyFilesTo(File srcDir, File destDir) {
		if (srcDir == null)
			return false;
		if (destDir == null)
			return false;

		if (!isFileExists(srcDir) || !isDirectory(srcDir)) {
			// 如果源目录不存在或源目录不是文件夹，返回false
			return false;
		}

		if (!isFileExists(destDir) || !isDirectory(destDir)) {
			// 如果目标目录不存在或者目标目录不是文件夹，则创建目标目录
			if (!createOrExistsFolder(destDir)) {
				// 如果创建不成功
				return false;
			}
		}

		// 现在源目录和目标目录都准备好了
		File[] srcFiles = srcDir.listFiles();
		for (int i = 0; i < srcFiles.length; i++) {
			if (srcFiles[i].isFile()) {
				// 获得目标文件
				File destFile = new File(destDir.getPath() + "//"
						+ srcFiles[i].getName());
				copyFileTo(srcFiles[i], destFile);
			} else if (srcFiles[i].isDirectory()) {
				File theDestDir = new File(destDir.getPath() + "//"
						+ srcFiles[i].getName());
				copyFilesTo(srcFiles[i], theDestDir);
			}
		}
		return true;
	}

	public boolean writeToFileFromInputStream(String fileName, InputStream is) {
		if (fileName == null || (fileName = fileName.trim()).equals("")) {
			return false;
		}

		// 先创建文件夹，否则不会成功
		File parentFile = new File(fileName).getParentFile();
		if (!createOrExistsFolder(parentFile)) {
			// 如果父文件夹创建不成功，返回false
			return false;
		}

		boolean result = false;
		File file = null;
		OutputStream os = null;
		try {
			file = new File(fileName);
			os = new FileOutputStream(file);
			byte buffer[] = new byte[4 * 1024];
			int length = 0;
			while ((length = is.read(buffer)) != -1) {
				os.write(buffer, 0, length);
			}
			os.flush();
			result = true;
		} catch (Exception e) {
			e.printStackTrace();
			file = null;
			result = false;
		} finally {
			try {
				os.close();

			} catch (Exception e2) {
				e2.printStackTrace();
				file = null;

			}
		}
		return result;
	}

	public OutputStream writeFile(String fileName) throws IOException {
		File file = new File(fileName);

		// 先创建文件夹，否则不会成功
		File parentFile = file.getParentFile();
		createOrExistsFolder(parentFile);
		FileOutputStream fos = new FileOutputStream(file);
		return fos;
	}

	public OutputStream appendFile(String fileName) throws IOException {
		File file = new File(fileName);
		// 先创建文件夹，否则不会成功
		File parentFile = file.getParentFile();
		createOrExistsFolder(parentFile);
		FileOutputStream fos = new FileOutputStream(file, true);
		return fos;
	}

	public InputStream readFile(String fileName) throws IOException {
		File file = new File(fileName);
		FileInputStream fis = new FileInputStream(file);
		return fis;
	}

	public boolean isSDCardMounted() {
		return Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED);
	}

	public boolean isSDFileExists(String fileName) {
		File file = new File(SDPATH + fileName);
		return file.exists();
	}

	public boolean createOrExistsSDFile(String fileName) {
		File file = new File(SDPATH + fileName);
		return createOrExistsFile(file);
	}

	public boolean createSDDir(String dirName) {
		File file = new File(SDPATH + dirName);
		return createOrExistsFolder(file);
	}

	public boolean Write2SDFromInputStream(String fileName, InputStream is) {
		String fileName2 = SDPATH + fileName;
		return writeToFileFromInputStream(fileName2, is);
	}

	public boolean delSDFile(String fileName) {
		File file = new File(SDPATH + fileName);
		return delFile(file);
	}

	public boolean delSDDir(String dirName) {
		File dir = new File(SDPATH + dirName);
		return delDir(dir);
	}

	public boolean renameSDFile(String oldfileName, String newFileName) {
		File oleFile = new File(SDPATH + oldfileName);
		File newFile = new File(SDPATH + newFileName);
		return moveFileTo(oleFile, newFile);
	}

	public boolean copySDFileTo(String srcFileName, String destFileName) {
		File srcFile = new File(SDPATH + srcFileName);
		File destFile = new File(SDPATH + destFileName);
		return copyFileTo(srcFile, destFile);
	}

	public boolean copySDFilesTo(String srcDirName, String destDirName) {
		File srcDir = new File(SDPATH + srcDirName);
		File destDir = new File(SDPATH + destDirName);
		return copyFilesTo(srcDir, destDir);
	}

	public boolean moveSDFileTo(String srcFileName, String destFileName)
			throws IOException {
		File srcFile = new File(SDPATH + srcFileName);
		File destFile = new File(SDPATH + destFileName);
		return moveFileTo(srcFile, destFile);
	}

	public boolean moveSDFilesTo(String srcDirName, String destDirName) {
		File srcDir = new File(SDPATH + srcDirName);
		File destDir = new File(SDPATH + destDirName);
		return moveFilesTo(srcDir, destDir);
	}

	public OutputStream writeSDFile(String fileName) throws IOException {
		String filename2 = SDPATH + fileName;
		return writeFile(filename2);
	}

	public OutputStream appendSDFile(String fileName) throws IOException {
		String filename2 = SDPATH + fileName;
		return appendFile(filename2);
	}

	public InputStream readSDFile(String fileName) throws IOException {
		String filename2 = SDPATH + fileName;
		return readFile(filename2);
	}

	public boolean createOrExistsPrivateFile(String fileName) {
		File file = new File(FILESPATH + fileName);
		return createOrExistsFile(file);
	}

	public boolean createOrExistsPrivateFolder(String dirName) {
		File dir = new File(FILESPATH + dirName);
		return createOrExistsFolder(dir);
	}

	public boolean delDataFile(String fileName) {
		File file = new File(FILESPATH + fileName);
		return delFile(file);
	}

	public boolean delDataDir(String dirName) {
		File file = new File(FILESPATH + dirName);
		return delDir(file);
	}

	public boolean copyDataFileTo(String srcFileName, String destFileName) {
		File srcFile = new File(FILESPATH + srcFileName);
		File destFile = new File(FILESPATH + destFileName);
		return copyFileTo(srcFile, destFile);
	}

	public boolean copyDataFilesTo(String srcDirName, String destDirName) {
		File srcDir = new File(FILESPATH + srcDirName);
		File destDir = new File(FILESPATH + destDirName);
		return copyFilesTo(srcDir, destDir);
	}

	public boolean moveDataFileTo(String srcFileName, String destFileName) {
		File srcFile = new File(FILESPATH + srcFileName);
		File destFile = new File(FILESPATH + destFileName);
		return moveFileTo(srcFile, destFile);
	}

	public boolean moveDataFilesTo(String srcDirName, String destDirName) {
		File srcDir = new File(FILESPATH + srcDirName);
		File destDir = new File(FILESPATH + destDirName);
		return moveFilesTo(srcDir, destDir);
	}

	public OutputStream wirtePrivateFile(String fileName) throws IOException {
		OutputStream os = mContext.openFileOutput(fileName,
				Context.MODE_WORLD_WRITEABLE);
		return os;
	}

	public OutputStream appendPrivateFile(String fileName) throws IOException {
		OutputStream os = mContext
				.openFileOutput(fileName, Context.MODE_APPEND);
		return os;
	}

	public InputStream readPrivateFile(String fileName) throws IOException {
		InputStream is = mContext.openFileInput(fileName);
		return is;
	}

	/**
	 * 获取存贮文件的文件夹路径
	 * 
	 * @return
	 */
	public static File createFolders() {
		File baseDir;
		if (android.os.Build.VERSION.SDK_INT < 8) {
			baseDir = Environment.getExternalStorageDirectory();
		} else {
			baseDir = Environment
					.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
		}
		if (baseDir == null)
			return Environment.getExternalStorageDirectory();
		// File aviaryFolder = new File(baseDir, FOLDER_NAME);
		File aviaryFolder = baseDir;
		if (aviaryFolder.exists())
			return aviaryFolder;
		if (aviaryFolder.isFile())
			aviaryFolder.delete();
		if (aviaryFolder.mkdirs())
			return aviaryFolder;
		return Environment.getExternalStorageDirectory();
	}

	public static File getEmptyFile(String name) {
		File folder = FileUtils.createFolders();
		if (folder != null) {
			if (folder.exists()) {
				File file = new File(folder, name);
				return file;
			}
		}
		return null;
	}

	/**
	 * 删除指定文件
	 * 
	 * @param path
	 * @return
	 */
	public static boolean deleteFileNoThrow(String path) {
		File file;
		try {
			file = new File(path);
		} catch (NullPointerException e) {
			return false;
		}

		if (file.exists()) {
			return file.delete();
		}
		return false;
	}

	/**
	 * 保存图片
	 * 
	 * @param bitName
	 * @param mBitmap
	 */
	public static String saveBitmap(String bitName, Bitmap mBitmap) {
		File baseFolder = createFolders();
		File f = new File(baseFolder.getAbsolutePath(), bitName);
		FileOutputStream fOut = null;
		try {
			f.createNewFile();
			fOut = new FileOutputStream(f);
		} catch (IOException e) {
			e.printStackTrace();
		}
		mBitmap.compress(Bitmap.CompressFormat.PNG, 100, fOut);
		try {
			fOut.flush();
			fOut.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return f.getAbsolutePath();
	}

	// 获取文件夹大小
	public static long getFolderSize(File file) throws Exception {
		long size = 0;
		try {
			File[] fileList = file.listFiles();
			for (int i = 0; i < fileList.length; i++) { // 如果下面还有文件
				if (fileList[i].isDirectory()) {
					size = size + getFolderSize(fileList[i]);
				} else {
					size = size + fileList[i].length();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return size;
	}

	/** * 格式化单位 * * @param size * @return */
	public static String getFormatSize(double size) {
		double kiloByte = size / 1024d;
		int megaByte = (int) (kiloByte / 1024d);
		return megaByte + "MB";
	}

	/**
	 * 
	 * @Description:
	 * @Author 11120500
	 * @Date 2013-4-25
	 */
	public static boolean isConnect(Context context) {
		try {
			ConnectivityManager connectivity = (ConnectivityManager) context
					.getSystemService(Context.CONNECTIVITY_SERVICE);
			if (connectivity != null) {
				NetworkInfo info = connectivity.getActiveNetworkInfo();
				if (info != null && info.isConnected()) {
					if (info.getState() == NetworkInfo.State.CONNECTED) {
						return true;
					}
				}
			}
		} catch (Exception e) {

		}
		return false;
	}

}
