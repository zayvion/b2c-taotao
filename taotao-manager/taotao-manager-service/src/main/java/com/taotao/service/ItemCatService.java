package com.taotao.service;

import java.util.List;

import com.taotao.common.pojo.EUTreeNode;

public interface ItemCatService {
	public List<EUTreeNode> getCatList(long parentId);

}
