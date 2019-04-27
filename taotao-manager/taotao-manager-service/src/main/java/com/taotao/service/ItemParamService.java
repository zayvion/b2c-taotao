package com.taotao.service;

import com.taotao.common.pojo.EUDataGridResult;
import com.taotao.common.utils.TaotaoResult;
import com.taotao.pojo.TbItemParam;

/**
 * 商品规格管理Service
 * @author Zayvion
 * @date Apr 1, 2019
 */
public interface ItemParamService {

	public EUDataGridResult getItemParamList(int page,int rows);
	public TaotaoResult getItemParamByCid(long cid); 
	public TaotaoResult insertItemParam(TbItemParam tbItemParam);
	public TaotaoResult deleteItemParam(long id);
}
