package com.taotao.portal.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taotao.portal.service.ItemService;
import com.taotao.protal.pojo.ItemInfo;

/**
 * 商品详情页面Controller
 * @author Zayvion
 * @date Apr 20, 2019
 */
@Controller
public class ItemController {
	
	@Autowired
	private ItemService itemService;
	
	
	/**
	 * 商品基本信息展示
	 * @Title showItem   
	 * @param itemId 商品id
	 * @param model
	 * @return String
	 */
	@RequestMapping("/item/{itemId}")
	public String showItem(@PathVariable Long itemId, Model model) {
		ItemInfo item = itemService.getItemById(itemId);
		model.addAttribute("item", item);
		
		return "item";
	}
	
	/**
	 * 商品描述展示
	 * @Title getItemDesc   
	 * @param itemId
	 * @return String
	 * @throws
	 */
	@RequestMapping(value="/item/desc/{itemId}", produces=MediaType.TEXT_HTML_VALUE+";charset=utf-8")
	@ResponseBody
	public String getItemDesc(@PathVariable Long itemId) {
		String string = itemService.getItemDescById(itemId);
		return string;
	}
	
	/**
	 * 商品规格参数展示
	 * @Title getItemParam   
	 * @param itemId
	 * @return String
	 */
	@RequestMapping(value="/item/param/{itemId}", produces=MediaType.TEXT_HTML_VALUE+";charset=utf-8")
	@ResponseBody
	public String getItemParam(@PathVariable Long itemId) {
		String string = itemService.getItemParam(itemId);
		return string;
	}





}
