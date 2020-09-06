package com.farm.system.bean;

import java.time.LocalDateTime;

import com.alibaba.fastjson.annotation.JSONField;

public class Operation {

	private Long id;
	
	private String operNo;
	
	private String pOperNo;
	
	private String operName;
	
	private String urlPrefix;
	
	@JSONField(format="yyyy-MM-dd HH:mm:ss")
	private LocalDateTime createTime;

	private String createUser;
	@JSONField(format="yyyy-MM-dd HH:mm:ss")
	private LocalDateTime modifyTime;

	private String modifyUser;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getOperNo() {
		return operNo;
	}

	public void setOperNo(String operNo) {
		this.operNo = operNo;
	}

	public String getpOperNo() {
		return pOperNo;
	}

	public void setpOperNo(String pOperNo) {
		this.pOperNo = pOperNo;
	}

	public String getOperName() {
		return operName;
	}

	public void setOperName(String operName) {
		this.operName = operName;
	}

	public String getUrlPrefix() {
		return urlPrefix;
	}

	public void setUrlPrefix(String urlPrefix) {
		this.urlPrefix = urlPrefix;
	}

	public LocalDateTime getCreateTime() {
		return createTime;
	}

	public void setCreateTime(LocalDateTime createTime) {
		this.createTime = createTime;
	}

	public String getCreateUser() {
		return createUser;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}

	public LocalDateTime getModifyTime() {
		return modifyTime;
	}

	public void setModifyTime(LocalDateTime modifyTime) {
		this.modifyTime = modifyTime;
	}

	public String getModifyUser() {
		return modifyUser;
	}

	public void setModifyUser(String modifyUser) {
		this.modifyUser = modifyUser;
	}
}
