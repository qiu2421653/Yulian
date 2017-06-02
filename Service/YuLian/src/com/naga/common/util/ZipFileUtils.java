package com.naga.common.util;

import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.CRC32;
import java.util.zip.CheckedOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.imageio.ImageIO;

public class ZipFileUtils {
	static final int BUFFER = 8192;
	/**
	 * 执行压缩操作
	 * 
	 * @param srcPathName
	 *            被压缩的文件/文件夹
	 */
	public void compressExe(String srcPathName,String PathName) {
		File file = new File(srcPathName);
		if (!file.exists()) {
			throw new RuntimeException(srcPathName + "不存在！");
		}
		try {
			FileOutputStream fileOutputStream = new FileOutputStream(PathName);
			CheckedOutputStream cos = new CheckedOutputStream(fileOutputStream, new CRC32());
			ZipOutputStream out = new ZipOutputStream(cos);
			String basedir = "";
			compressByType(file, out, basedir);
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("执行压缩操作时发生异常:" + e);
			throw new RuntimeException(e);
		}
	}
	  /**
     * 删除空目录
     * @param dir 将要删除的目录路径
     */
    public void doDeleteEmptyDir(String dir) {
        boolean success = (new File(dir)).delete();
        if (success) {
            System.out.println("Successfully deleted empty directory: " + dir);
        } else {
            System.out.println("Failed to delete empty directory: " + dir);
        }
    }

    /**
     * 递归删除目录下的所有文件及子目录下所有文件
     * @param dir 将要删除的文件目录
     * @return boolean Returns "true" if all deletions were successful.
     *                 If a deletion fails, the method stops attempting to
     *                 delete and returns "false".
     */
	public boolean deleteDir(File dir) {
        if (dir.isDirectory()) {
            String[] children = dir.list();
            //递归删除目录中的子目录下
            for (int i=0; i<children.length; i++) {
                boolean success = deleteDir(new File(dir, children[i]));
                if (!success) {
                    return false;
                }
            }
        }
        // 目录此时为空，可以删除
        return dir.delete();
    }
	/**
	 * 判断是目录还是文件，根据类型（文件/文件夹）执行不同的压缩方法
	 * 
	 * @param file
	 * @param out
	 * @param basedir
	 */
	private void compressByType(File file, ZipOutputStream out, String basedir) {
		/* 判断是目录还是文件 */
		if (file.isDirectory()) {
			System.out.println("压缩：" + basedir + file.getName());
			this.compressDirectory(file, out, basedir);
		} else {
			System.out.println("压缩：" + basedir + file.getName());
			this.compressFile(file, out, basedir);
		}
	}

	/**
	 * 压缩一个目录
	 * 
	 * @param dir
	 * @param out
	 * @param basedir
	 */
	private void compressDirectory(File dir, ZipOutputStream out, String basedir) {
		if (!dir.exists()) {
			return;
		}

		File[] files = dir.listFiles();
		for (int i = 0; i < files.length; i++) {
			/* 递归 */
			compressByType(files[i], out, basedir + dir.getName() + "/");
		}
	}

	/**
	 * 压缩一个文件
	 * 
	 * @param file
	 * @param out
	 * @param basedir
	 */
	private void compressFile(File file, ZipOutputStream out, String basedir) {
		if (!file.exists()) {
			return;
		}
		try {
			BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file));
			ZipEntry entry = new ZipEntry(basedir + file.getName());
			out.putNextEntry(entry);
			int count;
			byte data[] = new byte[BUFFER];
			while ((count = bis.read(data, 0, BUFFER)) != -1) {
				out.write(data, 0, count);
			}
			bis.close();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 图片拼接
	 * 
	 * @param files
	 *            要拼接的文件列表
	 * @param type2
	 *            纵向拼接
	 * @return
	 */
	public static InputStream merge(String[] files, int type) {
		ByteArrayInputStream in=null;
		try {
			/* 1 读取第一张图片 */
			File fileOne = new File(files[0]);
			BufferedImage imageFirst = ImageIO.read(fileOne);
			int width = imageFirst.getWidth();// 图片宽度
			int height = imageFirst.getHeight();// 图片高度
			int[] imageArrayFirst = new int[width * height];// 从图片中读取RGB
			imageArrayFirst = imageFirst.getRGB(0, 0, width, height, imageArrayFirst, 0, width);
			/* 1 对第二张图片做相同的处理 */
			File fileTwo = new File(files[1]);
			BufferedImage imageSecond = ImageIO.read(fileTwo);
			int width1 = imageSecond.getWidth();// 图片宽度
			int height2 = imageSecond.getHeight();// 图片高度
			int[] imageArraySecond = new int[width1 * height2];
			imageArraySecond = imageSecond.getRGB(0, 0, width1, height2, imageArraySecond, 0, width1);
			int ww = width > width1 ? width : width1;
			// 生成新图片
			BufferedImage imageResult = new BufferedImage(ww, height2 + height, BufferedImage.TYPE_INT_RGB);
			int k = 0;
			for (int i = 0; i < height; i++) {
				for (int j = 0; j < ww; j++) {
					if (width > j) {
						imageResult.setRGB(j, i, imageArrayFirst[k]);
						k++;
					} else {
						imageResult.setRGB(j, i, -328966);
					}
				}
			}
			int k1 = 0;
			for (int i1 = 0; i1 < height2; i1++) {
				for (int j1 = 0; j1 < ww; j1++) {
					if (width1 > j1) {
						imageResult.setRGB(j1, i1 + height, imageArraySecond[k1]);
						k1++;
					} else {
						imageResult.setRGB(j1, i1 + height, -328966);
					}

				}
			}
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			ImageIO.write(imageResult, "jpg", out);// 写图片
			in= new ByteArrayInputStream(out.toByteArray());
			return in;
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			try {
				in.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return null;
	}

}
