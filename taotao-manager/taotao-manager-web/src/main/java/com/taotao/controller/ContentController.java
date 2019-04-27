package com.taotao.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taotao.common.pojo.EUDataGridResult;
import com.taotao.common.utils.TaotaoResult;
import com.taotao.pojo.TbContent;
import com.taotao.service.ContentService;

@Controller
@RequestMapping("/content")
public class ContentController {

	@Autowired
	private ContentService contentService ;
	/**
	 * 内容管理列表
	 * @Title getContentList   
	 * @param categoryId
	 * @param page
	 * @param rows
	 * @return EUDataGridResult
	 * @throws
	 */
	@RequestMapping("/query/list")
	@ResponseBody
	public EUDataGridResult getContentList(long categoryId, int page, int rows) {
		EUDataGridResult content = contentService.getContentList(categoryId, page, rows);
		return content;
	}
	
	@RequestMapping("/save")
	@ResponseBody
	public TaotaoResult insertContent(TbContent content) {
		TaotaoResult result = contentService.insertContent(content);
		return result;
	}

}
