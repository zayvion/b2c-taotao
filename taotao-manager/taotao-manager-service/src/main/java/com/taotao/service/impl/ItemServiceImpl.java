package com.taotao.service.impl;

import java.util.Date;
import java.util.List;

import javax.management.loading.PrivateClassLoader;

import org.aspectj.weaver.reflect.IReflectionWorld;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sun.jdi.LongValue;
import com.taotao.common.pojo.EUDataGridResult;
import com.taotao.common.utils.IDUtils;
import com.taotao.common.utils.TaotaoResult;
import com.taotao.mapper.TbItemDescMapper;
import com.taotao.mapper.TbItemMapper;
import com.taotao.mapper.TbItemParamItemMapper;
import com.taotao.pojo.TbItem;
import com.taotao.pojo.TbItemDesc;
import com.taotao.pojo.TbItemDescExample;
import com.taotao.pojo.TbItemExample;
import com.taotao.pojo.TbItemExample.Criteria;
import com.taotao.pojo.TbItemParamItem;
import com.taotao.service.ItemService;

/**
 * 
 * @author Zayvion
 * @date Mar 27, 2019
 * @Description:商品管理实现类
 *
 */
@Service
public class ItemServiceImpl implements ItemService {

	@Autowired
	private TbItemMapper itemMapper;
	@Autowired
	private TbItemDescMapper itemDescMapper;
	@Autowired
	private TbItemParamItemMapper itemParamItemMapper;

	@Override
	public TbItem getItemById(long itemId) {	

		// TbItem item = itemMapper.selectByPrimaryKey(itemId);
		// 添加查询条件
		TbItemExample example = new TbItemExample();
		Criteria criteria = example.createCriteria();
		criteria.andIdEqualTo(itemId);
		List<TbItem> list = itemMapper.selectByExample(example);
		if (list != null && list.size() > 0) {
			TbItem item = list.get(0);
			return item;
		}
		return null;
	}

	/**
	 * 商品列表查询
	 * @Title getItemList   
	 * @param @param page
	 * @param @param rows
	 * @param @return      
	 * @return      
	 * @throws
	 */
	public EUDataGridResult getItemList(int page, int rows) {
		// 查询商品列表
		TbItemExample example = new TbItemExample();
		// 分页处理
		PageHelper.startPage(page, rows);
		List<TbItem> list = itemMapper.selectByExample(example);
		// 创建一个返回值对象
		EUDataGridResult result = new EUDataGridResult();
		result.setRows(list);
		// 取记录总条数
		PageInfo<TbItem> pageInfo = new PageInfo<>(list);
		result.setTotal(pageInfo.getTotal());
		return result;
	}

	/**
	 * 新增商品
	 * @Title createItem   
	 * @param @param item
	 * @param @return      
	 * @return      
	 * @throws
	 */
	@Override
	public TaotaoResult createItem(TbItem item, String desc, String itemParam) throws Exception {
		// item补全
		long itemId = IDUtils.genItemId();
		item.setId(itemId);
		item.setStatus((byte) 1);
		item.setCreated(new Date());
		item.setUpdated(new Date());
		// 插入到数据库
		itemMapper.insert(item);
		TaotaoResult result = insertItemDesc(itemId, desc);
		if (result.getStatus() != 200) {
			throw new Exception();
		}
		//添加商品规格参数
		result = saveItemParamItem(itemId, itemParam);
		if (result.getStatus() != 200) {
			throw new Exception();
		}
		return TaotaoResult.ok();
	}
	/**
	 * 添加商品规格，该方法在createItem()中被调用
	 * @Title saveItenParamItem   
	 * @param @param itemId
	 * @param @param itemParam
	 * @param @return      
	 * @return TaotaoResult
	 */
	private TaotaoResult saveItemParamItem(Long itemId , String itemParam) {
		TbItemParamItem paramItem = new TbItemParamItem();
		paramItem.setItemId(itemId);
		paramItem.setParamData(itemParam);
		paramItem.setUpdated(new Date());
		paramItem.setCreated(new Date());
		itemParamItemMapper.insert(paramItem);
		return TaotaoResult.ok();
	}

	/**
	 * 添加商品描述
	 * @Title insertItemDesc   
	 * @param @param itemId
	 * @param @param desc
	 * @param @return      
	 * @return TaotaoResult
	 */
	private TaotaoResult insertItemDesc(long itemId , String desc) {
		TbItemDesc tbItemDesc = new TbItemDesc();
		tbItemDesc.setItemId(itemId);
		tbItemDesc.setItemDesc(desc);
		tbItemDesc.setCreated(new Date());
		tbItemDesc.setUpdated(new Date());
		itemDescMapper.insert(tbItemDesc);
		return TaotaoResult.ok();
	}

	/**
	 * 根据商品id查询商品描述，将查询结果封装到TaotaoResult中 
	 * @Title getItemDesc   
	 * @param @param itemId
	 * @param @return      
	 */
	@Override
	public TaotaoResult getItemDesc(Long itemId) {
		TbItemDesc itemDesc = itemDescMapper.selectByPrimaryKey(itemId);
		return TaotaoResult.ok(itemDesc);
	}

	/**
	 * 更新商品
	 * @Title updateItem   
	 * @param @param item
	 * @param @param desc
	 * @param @return      
	 */
	@Override
	public TaotaoResult updateItem(TbItem item, String desc) {
		// 根据商品id，更新商品表，条件更新
		TbItemExample itemExample = new TbItemExample();
		Criteria criteria = itemExample.createCriteria();
		criteria.andIdEqualTo(item.getId());
		itemMapper.updateByExampleSelective(item, itemExample);

		// 根据商品id，更新商品描述表，条件更新
		TbItemDesc itemDesc = new TbItemDesc();
		itemDesc.setItemDesc(desc);
		TbItemDescExample itemDescExample = new TbItemDescExample();
		com.taotao.pojo.TbItemDescExample.Criteria createCriteria = itemDescExample.createCriteria();
		createCriteria.andItemIdEqualTo(item.getId());
		itemDescMapper.updateByExampleSelective(itemDesc, itemDescExample);

		return TaotaoResult.ok();
	}

	/**
	 * 删除商品
	 * @Title deleteItemById   
	 * @param @param itemId
	 * @param @return      
	 */
	@Override
	public TaotaoResult deleteItemById(long itemId) {
		// 添加查询条件
		TbItemExample example = new TbItemExample();
		Criteria criteria = example.createCriteria();
		criteria.andIdEqualTo(itemId);
		itemMapper.deleteByExample(example);
		return TaotaoResult.ok();
	}

	/**
	 * 下架商品
	 * @Title updateItemStatusInstock   
	 * @param @param ids
	 * @param @return      
	 */
	@Override
	public TaotaoResult updateItemStatusInstock(List<Long> ids) {
		TbItem item = new TbItem();
		item.setStatus((byte) 2);

		for (Long id : ids) {
			// 创建查询条件，根据id更新
			TbItemExample example = new TbItemExample();
			Criteria criteria = example.createCriteria();
			criteria.andIdEqualTo(id);
			itemMapper.updateByExampleSelective(item, example);
		}
		return TaotaoResult.ok();
	}

	/**
	 * 上架商品
	 * @Title updateItemStatusReshelf   
	 * @param @param ids
	 * @param @return      
	 */
	@Override
	public TaotaoResult updateItemStatusReshelf(List<Long> ids) {
		TbItem item = new TbItem();
		item.setStatus((byte) 1);

		for (Long id : ids) {
			// 创建查询条件，根据id更新
			TbItemExample example = new TbItemExample();
			Criteria criteria = example.createCriteria();
			criteria.andIdEqualTo(id);
			itemMapper.updateByExampleSelective(item, example);
		}
		return TaotaoResult.ok();
	}


}
