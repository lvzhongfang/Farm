package com.farm.system.bean;

import java.time.LocalDateTime;
import java.util.List;

import com.alibaba.fastjson.annotation.JSONField;

public class Permission {

	private Long id;
	
	private String permNo;
	
	private String permType;
	
	@JSONField(format="yyyy-MM-dd HH:mm:ss")
	private LocalDateTime createTime;

	private String createUser;
	
	@JSONField(format="yyyy-MM-dd HH:mm:ss")
	private LocalDateTime modifyTime;

	private String modifyUser;
	
	private List<Menu> menus;
	
	private List<Operation> operations;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getPermNo() {
		return permNo;
	}

	public void setPermNo(String permNo) {
		this.permNo = permNo;
	}

	public String getPermType() {
		return permType;
	}

	public void setPermType(String permType) {
		this.permType = permType;
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

	public List<Menu> getMenus() {
		return menus;
	}

	public void setMenus(List<Menu> menus) {
		this.menus = menus;
	}

	public List<Operation> getOperations() {
		return operations;
	}

	public void setOperations(List<Operation> operations) {
		this.operations = operations;
	}
}
