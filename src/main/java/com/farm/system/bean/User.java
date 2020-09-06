package com.farm.system.bean;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

import com.alibaba.fastjson.annotation.JSONField;
import com.farm.utils.DateUtils;

public class User implements Serializable{

	private static final long serialVersionUID = 1L;

	/**
	 * 主键id
	 */
	private Long id;

	private String pwd;

	private String salt;

	private String userNo;

	private String userName;

	private String phoneNo;

	private String sex;
	@JSONField(format="yyyy-MM-dd HH:mm:ss")
	private LocalDateTime birth;

	private String email;

	private Integer pwdErrorTime;

	private String status;

	private String avatar;
	@JSONField(format="yyyy-MM-dd HH:mm:ss")
	private LocalDateTime createTime;

	private String createUser;
	@JSONField(format="yyyy-MM-dd HH:mm:ss")
	private LocalDateTime modifyTime;

	private String modifyUser;
	
	private List<Role> roles;

	private List<Farm> farms;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public String getSalt() {
		return salt;
	}

	public void setSalt(String salt) {
		this.salt = salt;
	}

	public String getUserNo() {
		return userNo;
	}

	public void setUserNo(String userNo) {
		this.userNo = userNo;
	}

	public String getPhoneNo() {
		return phoneNo;
	}

	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public LocalDateTime getBirth() {
		return birth;
	}

	public void setBirth(LocalDateTime birth) {
		this.birth = birth;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Integer getPwdErrorTime() {
		return pwdErrorTime;
	}

	public void setPwdErrorTime(Integer pwdErrorTime) {
		this.pwdErrorTime = pwdErrorTime;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
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

	public List<Role> getRoles() {
		return roles;
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}

	public List<Farm> getFarms() {
		return farms;
	}

	public void setFarms(List<Farm> farms) {
		this.farms = farms;
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("id = ");
		sb.append(this.id);
		sb.append(",pwd = ");
		sb.append(this.pwd);
		sb.append(",userNo = ");
		sb.append(this.userNo);
		sb.append(",phoneNo = ");
		sb.append(this.phoneNo);
		sb.append(",sex = ");
		sb.append(this.sex);
		sb.append(",birth = ");
		
		try {
			sb.append(DateUtils.date2String("yyyy-MM-dd HH:mm:ss",this.birth));
		}catch(Exception e) {
			e.printStackTrace();
		}
		sb.append(",email = ");
		sb.append(this.email);
		sb.append(",pwdErrorTime = ");
		sb.append(this.pwdErrorTime);
		sb.append(",status = ");
		sb.append(this.status);
		sb.append(",avatar = ");
		sb.append(this.avatar);
		sb.append(",createTime = ");
		try {
			sb.append(DateUtils.date2String("yyyy-MM-dd HH:mm:ss",this.createTime));
		}catch(Exception e) {
			e.printStackTrace();
		}
		sb.append(",createUser = ");
		sb.append(this.createUser);
		sb.append(",modifyTime = ");
		try {
			sb.append(DateUtils.date2String("yyyy-MM-dd HH:mm:ss",this.modifyTime));
		}catch(Exception e) {
			e.printStackTrace();
		}
		sb.append(",modifyUser = ");
		sb.append(this.modifyUser);
		
		return sb.toString();
	}
}
