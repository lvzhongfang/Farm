package com.farm.file;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.farm.system.controller.UserController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.alibaba.fastjson.JSONObject;
import com.farm.utils.FileUtils;

@RestController
@RequestMapping(value="file")
public class FileUpLoadContorller {

	private static Logger logger = LoggerFactory.getLogger(FileUpLoadContorller.class);

	@Value("${file.uploadFolder}")
	private String uploadFolder;
	
	//@ApiOperation("上传图片，多文件")
	@PostMapping("/upload")
	@ResponseBody
	public Object upload(HttpServletRequest request1,MultipartHttpServletRequest request) throws IOException {
		String ctxPath = request1.getSession().getServletContext().getRealPath("/");
		logger.info("context path is {}", ctxPath);
		List<MultipartFile> files = request.getFiles("file");
		if (files.size() > 0) {
			for (MultipartFile multipartFile : files) {
				logger.debug("file name is {}", multipartFile);
				handleFileUpload(multipartFile,request);
			}
		}
		return "";
	}


	/**
	 * 单一文件
	 * @param file
	 * @param request
	 * @return
	 */
	@RequestMapping("/singleUpload")
	@ResponseBody
	public JSONObject handleFileUpload(@RequestParam("file") MultipartFile file,HttpServletRequest request) {
		
		JSONObject ret = new JSONObject();
		
		if (!file.isEmpty()) {
			String saveFileName = file.getOriginalFilename();
			//File saveFile = new File(request.getSession().getServletContext().getRealPath("/") + saveFileName);

			/*
			 * if (!saveFile.getParentFile().exists()) { saveFile.getParentFile().mkdirs();
			 * }
			 */
			try {
				/*
				 * BufferedOutputStream out = new BufferedOutputStream(new
				 * FileOutputStream(saveFile)); out.write(file.getBytes()); out.flush();
				 * out.close();
				 */
				String fullName = FileUtils.writeFile(file.getInputStream(),uploadFolder,saveFileName);
				ret.put("msg", "success");
				ret.put("code", "0000");
				ret.put("fileName", saveFileName);
				ret.put("fileUrl", "http://localhost:8082/img/" + saveFileName);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
				ret.put("msg", "上传失败");
				ret.put("code", "0001");
			} catch (IOException e) {
				e.printStackTrace();
				ret.put("msg", "上传失败");
				ret.put("code", "0001");
			} catch (Exception e) {
				e.printStackTrace();
				ret.put("msg", "上传失败");
				ret.put("code", "0001");
			}
		} else {
			ret.put("msg", "上传失败，因为文件为空.");
			ret.put("code", "0001");
		}
		
		return ret;
	}
}
