package com.taotao.service;

import java.util.List;

import com.taotao.common.pojo.EUDataGridResult;
import com.taotao.common.utils.TaotaoResult;
import com.taotao.pojo.TbItem;
/**
 * 
 * @author Zayvion
 * @date Mar 27, 2019
 * @Description:商品管理Service
 *
 */
public interface ItemService {
	public TbItem getItemById(long itemId);
	public EUDataGridResult getItemList(int page,int rows);
	public TaotaoResult createItem(TbItem item , String desc , String itemParam) throws Exception;
	public TaotaoResult getItemDesc(Long itemId);
	public TaotaoResult updateItem(TbItem item, String desc);
	public TaotaoResult deleteItemById(long ids);
	public TaotaoResult updateItemStatusInstock(List<Long> ids);
	public TaotaoResult updateItemStatusReshelf(List<Long> ids);
	
}
