package com.taotao.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.taotao.common.utils.JsonUtils;
import com.taotao.service.PictureService;

/**
 * 图片上传控制类
 * @author Zayvion
 * @date Mar 29, 2019
 */
@Controller
public class PIctureController {
	@Autowired
	private PictureService pictureService;
	
	@RequestMapping("/pic/upload")
	@ResponseBody
	public String  pivtureUpload(MultipartFile uploadFile) {
		Map result = pictureService.upLoadPicture(uploadFile);
		//为了保证兼容性，将json转为String传回
		String json = JsonUtils.objectToJson(result);
		return json;
	}

}
