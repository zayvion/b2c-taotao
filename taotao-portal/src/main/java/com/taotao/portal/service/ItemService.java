package com.taotao.portal.service;

import com.taotao.protal.pojo.ItemInfo;

public interface ItemService {

	public ItemInfo getItemById(Long itemId);
	public String getItemDescById(Long itemId);
	public String getItemParam(Long itemId);

}