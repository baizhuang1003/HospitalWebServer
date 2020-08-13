package com.tianyuan.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.tianyuan.core.AjaxResult;
import com.tianyuan.core.Common;

/**
 * 文件图片上传
 * @author BZ 2020年8月7日17:32:26
 *
 */
@RestController
public class FileManageController extends BaseHospitalController {

	//kindeditor 上传
		@PostMapping("file/kind")
		public String kindUpload(MultipartHttpServletRequest multiRequest, HttpServletRequest request) throws IOException {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
			String resultPath = "";
			Iterator<String> filesNames = multiRequest.getFileNames();
			String resultMessage = "";
			while (filesNames.hasNext()) {
				String fn = filesNames.next();
				MultipartFile file = multiRequest.getFile(fn);
				if (!file.isEmpty()) {
					String ext = ".png";
					String fileName = file.getOriginalFilename();

					if (fileName != null && !fileName.equals("")) {
						if (fileName.lastIndexOf(".") > -1) {
							ext = fileName.substring(fileName.lastIndexOf("."));
							if (ext == null || ext.equals(""))
								ext = ".png";
						} else {
							ext = ".png";
						}
					}
					resultPath = "/files/kind/" + sdf.format(new Date()) + "/" + Common.getUUID() + ext;
					File dir = getRealFile(resultPath);
					InputStream is = file.getInputStream();

					if (is.available() > 1024 * 1024 * 1024) {
						is.close();
						resultMessage = "文件不能大于1024MB";
						break;
					}

					FileOutputStream fos = new FileOutputStream(dir.getAbsolutePath());
					byte[] buffer = new byte[1024];
					int len = 0;
					while ((len = is.read(buffer)) != -1)
						fos.write(buffer, 0, len);
					is.close();
					fos.close();
					break;
				}
			}
			
			if (resultMessage.equals("")) return getKindSuccess(resultPath, "success");
			else return getKindError(resultMessage);
			
		}
		private String getKindSuccess(String url,String msg) {
			return "{\"error\":0,\"url\":\""+url+"\",\"message\":\""+msg+"\"}";
		}
		private String getKindError(String msg) {
			return "{\"error\":-1,\"url\":\"\",\"message\":\""+msg+"\"}";
		}
		
		// 上传图片
		@RequestMapping(value = "file/image", method = RequestMethod.POST)
		@ResponseBody
		public AjaxResult imageUpload(MultipartHttpServletRequest multiRequest, HttpServletRequest request)
				throws IOException {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
			String resultPath = "";
			Iterator<String> filesNames = multiRequest.getFileNames();

			String resultMessage = "";

			while (filesNames.hasNext()) {
				String fn = filesNames.next();
				MultipartFile file = multiRequest.getFile(fn);
				if (!file.isEmpty()) {
					String ext = ".png";
					String fileName = file.getOriginalFilename();

					if (fileName != null && !fileName.equals("")) {
						if (fileName.lastIndexOf(".") > -1) {
							ext = fileName.substring(fileName.lastIndexOf("."));
							if (ext == null || ext.equals(""))
								ext = ".png";
						} else {
							ext = ".png";
						}
					}
					resultPath = "/files/images/" + sdf.format(new Date()) + "/" + Common.getUUID() + ext;
					File dir = getRealFile(resultPath);
					InputStream is = file.getInputStream();

					if (is.available() > 4 * 1024 * 1024) {
						is.close();
						resultMessage = "图片文件不能大于4MB";
						break;
					}

					FileOutputStream fos = new FileOutputStream(dir.getAbsolutePath());
					byte[] buffer = new byte[1024];
					int len = 0;
					while ((len = is.read(buffer)) != -1)
						fos.write(buffer, 0, len);
					is.close();
					fos.close();
					break;
				}
			}
			if (resultMessage.equals(""))
				return onSuccess(resultPath, "success");
			else
				return onFailed(resultMessage);
		}
		
		// 上传文件
		@RequestMapping(value = "file/other", method = RequestMethod.POST)
		@ResponseBody
		public AjaxResult otherUpload(MultipartHttpServletRequest multiRequest, HttpServletRequest request)
				throws IOException {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
			String resultPath = "";
			Iterator<String> filesNames = multiRequest.getFileNames();

			String resultMessage = "";

			while (filesNames.hasNext()) {
				String fn = filesNames.next();
				MultipartFile file = multiRequest.getFile(fn);
				if (!file.isEmpty()) {
					String ext = ".png";
					String fileName = file.getOriginalFilename();

					if (fileName != null && !fileName.equals("")) {
						if (fileName.lastIndexOf(".") > -1) {
							ext = fileName.substring(fileName.lastIndexOf("."));
							if (ext == null || ext.equals(""))
								ext = ".png";
						} else {
							ext = ".png";
						}
					}
					resultPath = "/files/other/" + sdf.format(new Date()) + "/" + Common.getUUID() + ext;
					File dir = getRealFile(resultPath);
					InputStream is = file.getInputStream();

					if (is.available() > 10 * 1024 * 1024) {
						is.close();
						resultMessage = "文件不能大于10MB";
						break;
					}

					FileOutputStream fos = new FileOutputStream(dir.getAbsolutePath());
					byte[] buffer = new byte[1024];
					int len = 0;
					while ((len = is.read(buffer)) != -1)
						fos.write(buffer, 0, len);
					is.close();
					fos.close();
					break;
				}
			}
			if (resultMessage.equals(""))
				return onSuccess(resultPath, "success");
			else
				return onFailed(resultMessage);
		}
}
