package com.taotao.service;

import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

/**
 * 图片上传Service
 * @author Zayvion
 * @date Mar 29, 2019
 */
public interface PictureService {
	public Map upLoadPicture(MultipartFile upLoadFile);

}
