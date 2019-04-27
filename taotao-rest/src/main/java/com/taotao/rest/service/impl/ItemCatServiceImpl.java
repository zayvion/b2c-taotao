package com.taotao.rest.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.alibaba.druid.sql.visitor.functions.If;
import com.fasterxml.jackson.annotation.JsonTypeInfo.Id;
import com.taotao.common.utils.JsonUtils;
import com.taotao.mapper.TbItemCatMapper;
import com.taotao.pojo.TbItemCat;
import com.taotao.pojo.TbItemCatExample;
import com.taotao.pojo.TbItemCatExample.Criteria;
import com.taotao.rest.dao.JedisClient;
import com.taotao.rest.pojo.CatNode;
import com.taotao.rest.pojo.CatResult;
import com.taotao.rest.service.ItemCatService;

/**
 * 分类服务实现类
 * @author Zayvion
 * @date Apr 7, 2019
 */
@Service
public class ItemCatServiceImpl implements ItemCatService {

	@Autowired
	private TbItemCatMapper itemCatMapper;
	@Value("${INDEX_ITEMCAT_REDIS_KEY}")
    private String INDEX_ITEMCAT_REDIS_KEY;
	@Autowired
	private JedisClient jedisClient;

	@Override
	public CatResult getItemCatList() {
		CatResult catResult=new CatResult();
		 try {
			  String result = jedisClient.get(INDEX_ITEMCAT_REDIS_KEY);
			if(!StringUtils.isBlank(result)){
			List<CatNode>resultlist=JsonUtils.jsonToList(result,CatNode.class);
			//查询分类列表
			catResult.setData(resultlist);
			return catResult;
}
			}catch(Exception e){
			e.printStackTrace();
			}
			 
		 catResult.setData(getCatList(0));
		 
		return catResult;
	}
	
	/**
	 * 查询分页列表
	 * @Title getCatList
	 * @param @param parentId
	 * @param @return 
	 * @return List<?>
	 */
	private List<?> getCatList(long parentId){
		//创建查询条件
		TbItemCatExample example = new TbItemCatExample();
		Criteria criteria = example.createCriteria();
		criteria.andParentIdEqualTo(parentId);
		//执行查询
		List<TbItemCat> list = itemCatMapper.selectByExample(example);
		//返回list
		List resultList = new ArrayList<>();
		//像list中添加节点
		int count = 0;
		for (TbItemCat tbItemCat : list) {
			// 判断是否为父节点
			if (tbItemCat.getIsParent()) {
				CatNode catNode = new CatNode();
				if (parentId == 0) {
					catNode.setName(
							"<a href='/products/"+tbItemCat.getId()+".html'>"+tbItemCat.getName()+"</a>");
				} else {
					catNode.setName(tbItemCat.getName());
				}
				catNode.setUrl("/products/" + tbItemCat.getId() + ".html");
				catNode.setItem(getCatList(tbItemCat.getId()));

				resultList.add(catNode);
				count ++;
				//第一层只取14条
				if (parentId == 0 && count>=14) {
					break;
				}
			//如果是叶子节点
			} else {
				resultList.add("/products/"+tbItemCat.getId()+".html|"+ tbItemCat.getName()); 
			}
		}
		 try{
			// 把list转换成字符串
			String cacheString = JsonUtils.objectToJson(resultList);
			jedisClient.set(INDEX_ITEMCAT_REDIS_KEY, cacheString);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return resultList;
	}
	

}