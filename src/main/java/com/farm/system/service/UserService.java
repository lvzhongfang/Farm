package com.farm.system.service;

import java.util.List;
import java.util.Map;

import com.farm.system.bean.User;

public interface UserService {

	public User findByNo(String userNo);
	
	/**
	 * 通过userNo获取user信息及user的权限信息
	 * <p>Title: findByNoWitchPermission</p>
	 * <p>Description: </p>
	 * @param userNo
	 * @return
	 */
	public Map<String,List<String>> findRolesAndPermisByNo(String userNo);
	
	public Long add(User u);
	
	public User get(Long id);
	
	public int update(Map<String,Object> map);
	
	public int del(Long id);
	
	public List<User> list(Map<String,Object> map);
	
	/**
	 * 分页查询
	 * @param map
	 * @return
	 */
	public List<User> find(Map<String,Object> map);
	
	/**
	 * 获取MD5加密salt
	 * @param userNo
	 * @return
	 */
	public String getSalt(String userNo);
	
	public Integer count(Map<String,Object> map);
}
