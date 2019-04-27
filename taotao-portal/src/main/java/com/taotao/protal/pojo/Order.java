package com.taotao.protal.pojo;

import java.util.List;

import com.taotao.pojo.TbOrder;
import com.taotao.pojo.TbOrderItem;
import com.taotao.pojo.TbOrderShipping;

public class Order extends TbOrder {
	private List<TbOrderItem> oderItems;
	private TbOrderShipping orderShipping;
	public List<TbOrderItem> getOderItems() {
		return oderItems;
	}
	public void setOderItems(List<TbOrderItem> oderItems) {
		this.oderItems = oderItems;
	}
	public TbOrderShipping getOrderShipping() {
		return orderShipping;
	}
	public void setOrderShipping(TbOrderShipping orderShipping) {
		this.orderShipping = orderShipping;
	}
	

}
