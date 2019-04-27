package com.taotao.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.taotao.common.pojo.EUTreeNode;
import com.taotao.common.utils.TaotaoResult;
import com.taotao.mapper.TbContentCategoryMapper;
import com.taotao.pojo.TbContentCategory;
import com.taotao.pojo.TbContentCategoryExample;
import com.taotao.pojo.TbContentCategoryExample.Criteria;
import com.taotao.service.ContentCategoryService;

/**
 * 内容分类管理
 * @author Zayvion
 * @date Apr 8, 2019
 */
@Service
public class getCategoryListImpl implements ContentCategoryService {

	@Autowired
	private TbContentCategoryMapper contentCategoryMapper;
	
	@Override
	public List<EUTreeNode> getCategoryList(long parentId) {
		//根据parentId查询列表
		TbContentCategoryExample example = new TbContentCategoryExample();
		Criteria criteria = example.createCriteria();
		criteria.andParentIdEqualTo(parentId);
		//执行查询
		List<TbContentCategory> list = contentCategoryMapper.selectByExample(example);
		List<EUTreeNode> resultList = new ArrayList<>();
		for (TbContentCategory tbContentCategory : list) {
			//创建一个节点
			EUTreeNode node = new EUTreeNode();
			node.setId(tbContentCategory.getId());
			node.setText(tbContentCategory.getName());
			node.setState(tbContentCategory.getIsParent()?"closed":"open");
			resultList.add(node);
		}
		return resultList;
	}

	/**
	 * 插入内容分类
	 * @Title insertContentCategory   
	 * @param @param parentId
	 * @param @param name
	 * @param @return
	 */
	@Override
	public TaotaoResult insertContentCategory(long parentId, String name) {

		// 创建一个pojo
		TbContentCategory contentCategory = new TbContentCategory();
		contentCategory.setName(name);
		contentCategory.setIsParent(false);
		// '状态。可选值:1(正常),2(删除)',
		contentCategory.setStatus(1);
		contentCategory.setParentId(parentId);
		contentCategory.setSortOrder(1);
		contentCategory.setCreated(new Date());
		contentCategory.setUpdated(new Date());
		// 添加记录
		contentCategoryMapper.insert(contentCategory);
		// 查看父节点的isParent列是否为true，如果不是true改成true
		TbContentCategory parentCat = contentCategoryMapper.selectByPrimaryKey(parentId);
		// 判断是否为true
		if (!parentCat.getIsParent()) {
			parentCat.setIsParent(true);
			// 更新父节点
			contentCategoryMapper.updateByPrimaryKey(parentCat);
		}
		// 返回结果
		return TaotaoResult.ok(contentCategory);
	}

	/**
	 * 删除内容分类
	 * @Title removeContentCategory   
	 * @param @param parentId
	 * @param @param id
	 * @return TaotaoResult
	 */
	@Override
	public TaotaoResult deleteContentCategoryAndChild(long id) {
		//取出id这一条的记录
		TbContentCategory category = contentCategoryMapper.selectByPrimaryKey(id);
		//判断是否为父节点
		if (category.getIsParent()) {
			//获得该节点下所有子节点
			List<TbContentCategory> nodeList = getChildNodeList(id);
			for (TbContentCategory tbContentCategory : nodeList) {
				deleteCatgory(tbContentCategory.getId());
			}
		}
		//判断父节点下是否还有其他孩子节点
		if (getChildNodeList(category.getParentId()).size()== 1) {
			//没有则标记为子节点
			TbContentCategory parentCategory = contentCategoryMapper.selectByPrimaryKey(category.getParentId());
			parentCategory.setIsParent(false);
			contentCategoryMapper.updateByPrimaryKey(parentCategory);
		}
		contentCategoryMapper.deleteByPrimaryKey(id);
		return TaotaoResult.ok();
	}
	
	/**
	 * 获得父节点下所有子节点
	 * @Title getChildList   
	 * @param @param id
	 * @param @return 
	 * @return List<TbContentCategory>
	 */
	private List<TbContentCategory> getChildNodeList(long id ) {
		TbContentCategoryExample example = new TbContentCategoryExample();
		Criteria criteria = example.createCriteria();
		criteria.andParentIdEqualTo(id);
		
		return contentCategoryMapper.selectByExample(example);
	}
	
	/**
	 * 递归删除节点
	 * @Title deleteCatgory   
	 * @param @param id
	 * @param @return 
	 * @return TaotaoResult
	 * @throws
	 */
	private TaotaoResult	 deleteCatgory(long id ) {
		deleteContentCategoryAndChild(id);
		
		return TaotaoResult.ok();
	}

	/**
	 * 修改（重命名）节点
	 * @Title updateContentCategoryAndChild   
	 * @param @param id
	 * @param @param name
	 * @return TaotaoResult
	 */
	@Override
	public TaotaoResult updateContentCategoryAndChild(long id, String name) {
		TbContentCategory category = contentCategoryMapper.selectByPrimaryKey(id);
		category.setName(name);
		contentCategoryMapper.updateByPrimaryKey(category);
		
		return TaotaoResult.ok();
	}

}
