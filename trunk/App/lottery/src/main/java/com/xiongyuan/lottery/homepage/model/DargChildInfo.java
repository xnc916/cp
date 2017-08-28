package com.xiongyuan.lottery.homepage.model;


/**
 *
 * 类: DargChildInfo <p>
 * 描述: 子item显示 <p>
 * 作者: wedcel wedcel@gmail.com<p>
 * 时间: 2015年8月25日 下午5:24:04 <p>
 */
public class DargChildInfo {

	private int id;
	private String name;
	private String content;
	private int resIconId;

	public DargChildInfo() {
		// TODO Auto-generated constructor stub
	}


	public DargChildInfo(int id, String name,String content, int resIconId) {
		super();
		this.id = id;
		this.name = name;
		this.content=content;
		this.resIconId=resIconId;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public int getResIconId() {
		return resIconId;
	}

	public void setResIconId(int resIconId) {
		this.resIconId = resIconId;
	}

	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}



}
