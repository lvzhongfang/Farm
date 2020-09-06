/**  
 *@Copyright:Copyright (c) 2016 - 2100  
 *@Company:SJS  
 */  
package com.farm.utils;  

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.time.LocalDateTime;

import javax.servlet.http.HttpServletResponse;


/**  
 *@Title:  
 *@Description:  
 *@Author:Administrator  
 *@Since:2016年10月4日  
 *@Version:1.1.0  
 */
public class FileUtils {
	public static final int BUFFER_SIZE = 100;	//缓冲大小

	/**
	 * 保存附件
	 * @param file
	 * @param fileName
	 * @param custNo 公司编号
	 * @param jobNo job编号
	 * @return  
	 * @throws Exception 
	 * @Description:
	 */
	public static String writeFile(InputStream input,String uploadFolder,String fileName) throws Exception {

		String path = "";

		try {
			String uploadFileDir = uploadFolder;
			System.out.println("uploadFileDir -> " + uploadFileDir);
			//文件主目录
			File baseFile = new File(uploadFileDir + File.separator + "avatar");
			if(!baseFile.exists()) {
				baseFile.mkdir();
			}

			String absolutePath = baseFile.getAbsolutePath();
			String today = DateUtils.date2String("YYYYMMdd", LocalDateTime.now());
			//根据公司编码存放文件
			//String filePath = absolutePath + File.separator + today;
			String filePath = absolutePath;
			File targetFile = new File(filePath);
			if(!targetFile.exists()){
				targetFile.mkdir();
			}

			//备份文件,如果当前文件已经存在则备份已经存在的文件
			backupFile(fileName, targetFile);
			//写文件到服务器
			path = filePath + File.separator + fileName;
			writeUploadFileToServer(input,path);

		} catch (IOException e) {
			e.printStackTrace();
		}

		return path;
	}

	public static void backupFile(String fileName,File file) throws Exception{

		if(file != null && file.isDirectory()){

			String path = file.getAbsolutePath();

			File f = new File(path + File.separator + fileName);
			if(f.exists()){
				File backFile = new File(path + File.separator + "backup");
				if(!backFile.exists())
					backFile.mkdir();

				String today = DateUtils.date2String("YYYYMMdd", LocalDateTime.now());
				copyFile(f,path + File.separator + "backup" + File.separator + today + "-" + fileName);
				//File destFile = new File(path + File.separator + today + "-" + fileName);
				//f.renameTo(destFile);
				f.delete();
			}
		}
	}

	public static void copyFile(File sourceFile,String destFile) throws IOException{
		//写文件到本地磁盘
		InputStream is = new BufferedInputStream(new FileInputStream(sourceFile), BUFFER_SIZE);
		OutputStream os = new BufferedOutputStream(new FileOutputStream(destFile), BUFFER_SIZE);
		int b;
		byte[] buffer = new byte[BUFFER_SIZE];
		while ((b = is.read(buffer, 0, BUFFER_SIZE)) != -1) {
			os.write(buffer, 0, b);
		}
		is.close();
		os.close();
	}

	public static void writeUploadFileToServer(InputStream input, String destFile) throws IOException {
		//写文件到本地磁盘
		InputStream is = input;
		OutputStream os = new BufferedOutputStream(new FileOutputStream(destFile), BUFFER_SIZE);
		int b;
		byte[] buffer = new byte[BUFFER_SIZE];
		while ((b = is.read(buffer, 0, BUFFER_SIZE)) != -1) {
			os.write(buffer, 0, b);
		}
		is.close();
		os.close();
	}

	public static void writeUploadFileToServer(File srcFile, String destFile) throws IOException {
		//写文件到本地磁盘
		InputStream is = new BufferedInputStream(new FileInputStream(srcFile), BUFFER_SIZE);
		OutputStream os = new BufferedOutputStream(new FileOutputStream(destFile), BUFFER_SIZE);
		int b;
		byte[] buffer = new byte[BUFFER_SIZE];
		while ((b = is.read(buffer, 0, BUFFER_SIZE)) != -1) {
			os.write(buffer, 0, b);
		}
		is.close();
		os.close();
	}

	public static void download(HttpServletResponse response, String realFileName,
			String fileName) {
		response.setHeader("Content-disposition", "attachment; filename=" + fileName);
		response.setHeader("Content-Type", "application/octet-stream");
		BufferedInputStream bis = null;
		BufferedOutputStream bos = null;
		try {
			bis = new java.io.BufferedInputStream(new FileInputStream(new java.io.File(realFileName)));
			bos = new java.io.BufferedOutputStream(response.getOutputStream());
			byte[] buff = new byte[2048];
			int bytesRead;
			while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {
				bos.write(buff, 0, bytesRead);
			}
		} catch (final java.io.IOException e) {
			System.out.println("&sup3;&ouml;&Iuml;&Ouml;IOException." + e);
		} finally {
			try {
				if (bis != null)
					bis.close();
				if (bos != null)
					bos.close();
			} catch (IOException e) {
				//e.printStackTrace();
			}
		}
	}

	public static void download(HttpServletResponse response, String realFileName,
			String fileName, String characterEncoding) {

		try {
			fileName = new String(fileName.getBytes(characterEncoding), "UTF-8");
		} catch (Exception ex) {
			System.out.println("set download fileName characterEncoding fail!!!");
		}
		download(response, realFileName, fileName);
	}

	public static void download(HttpServletResponse response, InputStream inputStream,
			String fileName) {

		response.setHeader("Content-disposition", "attachment; filename=" + fileName);
		response.setHeader("Content-Type", "application/octet-stream");
		BufferedInputStream bis = null;
		BufferedOutputStream bos = null;
		try {
			bis = new java.io.BufferedInputStream(inputStream);
			bos = new java.io.BufferedOutputStream(response.getOutputStream());
			byte[] buff = new byte[2048];
			int bytesRead;
			while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {
				bos.write(buff, 0, bytesRead);
			}
		} catch (final java.io.IOException e) {
			System.out.println("&sup3;&ouml;&Iuml;&Ouml;IOException." + e);
		} finally {
			try {
				if (bis != null)
					bis.close();
				if (bos != null)
					bos.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public static void download(HttpServletResponse response, InputStream inputStream,
			String fileName, String characterEncoding) {

		try {
			fileName = new String(fileName.getBytes(characterEncoding), "ISO8859-1");
		} catch (Exception ex) {
			System.out.println("set download fileName characterEncoding fail!!!");
		}
		download(response, inputStream, fileName);
	}

	public static void main(String [] args){

	}
}
