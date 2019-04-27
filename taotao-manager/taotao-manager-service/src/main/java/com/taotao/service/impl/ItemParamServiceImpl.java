package com.taotao.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.taotao.common.pojo.EUDataGridResult;
import com.taotao.common.utils.TaotaoResult;
import com.taotao.mapper.TbItemParamExMapper;
import com.taotao.mapper.TbItemParamItemMapper;
import com.taotao.mapper.TbItemParamMapper;
import com.taotao.pojo.TbItem;
import com.taotao.pojo.TbItemExample;
import com.taotao.pojo.TbItemParam;
import com.taotao.pojo.TbItemParamExample;
import com.taotao.pojo.TbItemParamExample.Criteria;
import com.taotao.pojo.TbItemParamItem;
import com.taotao.pojo.TbItemParamItemExample;
import com.taotao.pojo.TbItemParamModel;
import com.taotao.service.ItemParamService;

/**
 * 商品规格管理实现类
 * @author Zayvion
 * @date Apr 1, 2019
 */
@Service
public class ItemParamServiceImpl implements ItemParamService{

	@Autowired
	private TbItemParamMapper tbItemParam;
	@Autowired
	private TbItemParamExMapper tbItemParamExMapper;
	/**
	 * 显示规格参数列表
	 * @Title getItemParamList   
	 * @param @param page
	 * @param @param rows
	 * @param @return      
	 */
	@Override
	public EUDataGridResult getItemParamList(int page, int rows) {
	
		// 分页处理
		PageHelper.startPage(page, rows);
		//使用自定义的方法查询
		List<TbItemParamModel> list = tbItemParamExMapper.selectItemParamlist();
		PageInfo<TbItemParamModel> pageInfo = new PageInfo<>(list);
		// 创建一个返回值对象
		EUDataGridResult result = new EUDataGridResult();
		result.setRows(list);
		// 取记录总条数
		result.setTotal(pageInfo.getTotal());
		return result;
	}
	
	/**
	 * 根据cid查询规格参数模板
	 * @Title getItemParamByCid   
	 * @param @param cid
	 * @return TaotaoResult  
	 */
	@Override
	public TaotaoResult getItemParamByCid(long cid) {
		TbItemParamExample example = new TbItemParamExample();
		Criteria criteria = example.createCriteria();
		criteria.andItemCatIdEqualTo(cid);
		List<TbItemParam> list = tbItemParam.selectByExampleWithBLOBs(example);
		//判断是否查询到结果
		if (list != null && list.size() > 0) {
			return TaotaoResult.ok(list.get(0));
		}
		
		return TaotaoResult.ok(null);
	}

	/**
	 * 插入新的规格参数
	 * @Title insertItemParam   
	 * @param @param tbItemParam
	 * @param @return TaotaoResult     
	 */
	@Override
	public TaotaoResult insertItemParam(TbItemParam tbItemParam) {
		//补全pojo
		tbItemParam.setCreated(new Date());
		tbItemParam.setUpdated(new Date());
		//插入数据
		this.tbItemParam.insert(tbItemParam);
		return TaotaoResult.ok() ;
	}

	
	/**
	 * 
	 * @Title deleteItemParam   
	 * @param @param id
	 * @param @return
	 */
	@Override
	public TaotaoResult deleteItemParam(long id) {
		TbItemParamExample example = new TbItemParamExample();
		Criteria criteria = example.createCriteria();
		criteria.andIdEqualTo(id);
		tbItemParam.deleteByExample(example);
		return TaotaoResult.ok();
	}

}

	
	

