package com.taotao.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taotao.common.pojo.EUTreeNode;
import com.taotao.common.utils.TaotaoResult;
import com.taotao.service.ContentCategoryService;

/**
 * 内容分类管理
 * @author Zayvion
 * @date Apr 8, 2019
 */
@Controller
@RequestMapping("/content/category")
public class ContentCategoryController {
	@Autowired
	private ContentCategoryService contentCategoryService;
	/**
	 * 展示节点
	 * @Title getContentCatList   
	 * @param @param parentId
	 * @param @return 
	 * @return List<EUTreeNode>
	 * @throws
	 */
	@RequestMapping("/list")
	@ResponseBody
	public List<EUTreeNode> getContentCatList(@RequestParam(value = "id", defaultValue = "0") Long parentId) {
		List<EUTreeNode> list = contentCategoryService.getCategoryList(parentId);
		return list;
		}
	
	/**
	 * 创建新的内容节点
	 * @Title createContentCategory   
	 * @param @param parentId
	 * @param @param name
	 * @param @return 
	 * @return TaotaoResult
	 * @throws
	 */
	@RequestMapping("/create")
	@ResponseBody
	public TaotaoResult createContentCategory(Long parentId, String name) {
		TaotaoResult result = contentCategoryService.insertContentCategory(parentId, name);
		return result;
	}
	
	/**
	 * 删除内容节点
	 * @Title deleteContentCategory   
	 * @param @param id
	 * @param @return 
	 * @return TaotaoResult
	 * @throws
	 */
	@RequestMapping("/delete")
	@ResponseBody
	public TaotaoResult deleteContentCategory( Long id) {
		TaotaoResult result = contentCategoryService.deleteContentCategoryAndChild(id);
		return result;
	}

	@RequestMapping("/update")
	@ResponseBody
	public TaotaoResult updateContentCategory(Long id, String name) {
		TaotaoResult result = contentCategoryService.updateContentCategoryAndChild(id, name);
		return result;
}
	}
