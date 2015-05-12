package com.github.gaoyangthu.core.util;

/**
 * 使用JDK版本的zip读写工具类
 *
 * Author: gaoyangthu
 * Date: 2014/4/14
 * Time: 10:54
 */

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

public class ZipUtils {
	/**
	 * 将文件压缩为 zip 文件
	 * @param zipPath 输出zip文件
	 * @param dir 输入文件或文件夹
	 */
	public static void zip(String zipPath, String dir) {
		FileOutputStream fos = null;
		ZipOutputStream zipout = null;
		try {
			fos = new FileOutputStream(zipPath); // 文件输出流
			zipout = new ZipOutputStream(fos); // zip 输出流
			File file = new File(dir);
			zip(zipout, file, ""); // 压缩文件
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (zipout != null) {
					zipout.close();
				}
				if (fos != null) {
					fos.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}

	/**
	 * 将文件 file 写入到 zip 输出流中
	 * @param out :ZipOutputStream
	 * @param file :File
	 * @param base :String
	 */
	private static void zip(ZipOutputStream out, File file, String base)
			throws IOException {
		if (file.isDirectory()) {
			base += file.getName() + "/";
			ZipEntry entry = new ZipEntry(base); // 创建一个目录条目 [以 / 结尾]
			out.putNextEntry(entry); // 向输出流中写入下一个目录条目
			File[] fileArr = file.listFiles(); // 递归写入目录下的所有文件
			for (File f : fileArr) {
				zip(out, f, base);
			}
		} else if (file.isFile()) {
			base += file.getName();
			ZipEntry entry = new ZipEntry(base); // 创建一个文件条目
			out.putNextEntry(entry); // 向输出流中写入下一个文件条目
			FileInputStream in = new FileInputStream(file); // 写入文件内容
			int data = in.read(); // 为了防止出现乱码
			while (data != -1) { // 此处按字节流读取和写入
				out.write(data);
				data = in.read();
			}
			in.close();
		}
	}

	/**
	 * 解压 zip 文件
	 * @param zipPath zip文件路径
	 * @param outdir 输出文件夹
	 */
	public static void unzip(String zipPath, String outdir) {
		FileInputStream fis = null;
		ZipInputStream zipin = null;
		try {
			fis = new FileInputStream(zipPath); // 文件输入流
			zipin = new ZipInputStream(fis); // zip 输入流
			ZipEntry entry = null; // 文件条目
			entry = zipin.getNextEntry();
			while (entry != null) {
				if (entry.isDirectory()) {
					String name = entry.getName();
					name = name.substring(0, name.length() - 1);
					File dir = new File(outdir + File.separator + name);
					dir.mkdir(); // 创建目录
				} else {
					String name = entry.getName();
					File file = new File(outdir + File.separator + name);
					file.createNewFile(); // 创建文件
					FileOutputStream fos = new FileOutputStream(file);
					int data = zipin.read();
					while (data != -1) {
						fos.write(data);
						data = zipin.read();
					}
					fos.flush();
					fos.close();
				}
				entry = zipin.getNextEntry();
			}// end while
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (zipin != null) {
					zipin.close();
				}
				if (fis != null) {
					fis.close();
				}
			} catch (Exception e) {

			}
		}

	}// end unzip
}
