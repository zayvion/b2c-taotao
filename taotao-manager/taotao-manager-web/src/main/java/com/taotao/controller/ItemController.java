 package com.taotao.controller;

import java.util.List;

import org.omg.CosNaming.IstringHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sun.tools.corba.se.idl.StringGen;
import com.taotao.common.pojo.EUDataGridResult;
import com.taotao.common.utils.JsonUtils;
import com.taotao.common.utils.TaotaoResult;
import com.taotao.pojo.TbItem;
import com.taotao.service.ItemService;

@Controller
public class ItemController {
	@Autowired
	private ItemService itemService;

	@RequestMapping("/item/{itemId}")
	@ResponseBody
	public TbItem getItemById(@PathVariable Long itemId) {
		TbItem tbItem = itemService.getItemById(itemId);
		return tbItem;
	}

	/**
	 * 查询所有商品
	 * @Title getItemList
	 * @param @param page
	 * @param @param rows
	 * @param @return
	 * @return EUDataGridResult
	 */
	@RequestMapping("/item/list")
	@ResponseBody
	public EUDataGridResult getItemList(Integer page, Integer rows) {
		EUDataGridResult result = itemService.getItemList(page, rows);
		return result;
	}

	@RequestMapping(value = "/item/save", method = RequestMethod.POST)
	@ResponseBody
	public TaotaoResult createItem(TbItem item , String desc , String itemParams) throws Exception {
		TaotaoResult result = itemService.createItem(item, desc,itemParams);
		return result; 
	}

	/**
	 * 根据商品id，获取商品描述
	 * @param itemId
	 * @return
	 */
	@RequestMapping(value = "/item/desc/{itemId}" )
	@ResponseBody
	public TaotaoResult getItemDesc(@PathVariable Long itemId) {
		TaotaoResult result = itemService.getItemDesc(itemId);
		return result;
	}

	/**
	 * 更新（修改）商品
	 * 
	 * @param item
	 * @param desc
	 * @return
	 */
	@RequestMapping("/item/update")
	@ResponseBody
	public TaotaoResult updateItem(TbItem item, String desc) {
		TaotaoResult result = itemService.updateItem(item, desc);
		return TaotaoResult.ok();
	}

	/**
	 * 删除商品
	 * @param item
	 * @param desc
	 * @return
	 */
	@RequestMapping("/item/delete")
	@ResponseBody
	public TaotaoResult deleteItem(@RequestParam(value = "ids")List<Long> ids) {
		for (Long id : ids) {
			TaotaoResult result = itemService.deleteItemById(id);
		}
		return TaotaoResult.ok();
	}

	/**
	 * 下架商品
	 * @param ids
	 * @param method
	 * @return
	 */
	@RequestMapping("/item/instock")
	@ResponseBody
	public TaotaoResult updateItemStatusToInstock(@RequestParam(value = "ids") List<Long>ids) {
		TaotaoResult result = itemService.updateItemStatusInstock(ids);
		return result;
	}
	
	@RequestMapping("/item/reshelf")
	@ResponseBody
	public TaotaoResult updateItemStatusToReshelf(@RequestParam(value = "ids") List<Long>ids) {
		TaotaoResult result = itemService.updateItemStatusReshelf(ids);
		return result;
	}
}
