package com.taotao.common.pojo;

import javax.management.loading.PrivateClassLoader;

/**
 * EasyUI树形控件节点格式
 * @author Zayvion
 * @date Mar 28, 2019
 */
public class EUTreeNode {
	private long id ;
	private String text ;
	private String state ;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	

}
