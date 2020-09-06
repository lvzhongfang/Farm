package com.farm.system.bean;

import java.time.LocalDateTime;
import java.util.List;

import com.alibaba.fastjson.annotation.JSONField;

public class Farm {

	private Long id;
	
	private String farmNo;
	
	private String farmName;
	
	private String identityNo;
	
	private Integer idType;
	
	private String cityNo;
	
	private String addr;
	
	private String phoneNo;
	
	@JSONField(format="yyyy-MM-dd HH:mm:ss")
	private LocalDateTime createTime;

	private String createUser;
	
	@JSONField(format="yyyy-MM-dd HH:mm:ss")
	private LocalDateTime modifyTime;

	private String modifyUser;
	
	private List<User> users;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFarmNo() {
		return farmNo;
	}

	public void setFarmNo(String farmNo) {
		this.farmNo = farmNo;
	}

	public String getFarmName() {
		return farmName;
	}

	public void setFarmName(String farmName) {
		this.farmName = farmName;
	}

	public String getIdentityNo() {
		return identityNo;
	}

	public void setIdentityNo(String identityNo) {
		this.identityNo = identityNo;
	}

	public Integer getIdType() {
		return idType;
	}

	public void setIdType(Integer idType) {
		this.idType = idType;
	}

	public String getCityNo() {
		return cityNo;
	}

	public void setCityNo(String cityNo) {
		this.cityNo = cityNo;
	}

	public String getAddr() {
		return addr;
	}

	public void setAddr(String addr) {
		this.addr = addr;
	}

	public String getPhoneNo() {
		return phoneNo;
	}

	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
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

	public List<User> getUsers() {
		return users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}
	
	
}
